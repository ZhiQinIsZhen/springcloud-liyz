package com.liyz.cloud.service.auth.feign;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.liyz.cloud.common.base.constant.AuthExceptionCodeEnum;
import com.liyz.cloud.common.base.constant.Device;
import com.liyz.cloud.common.base.constant.LoginType;
import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.common.exception.CommonExceptionCodeEnum;
import com.liyz.cloud.common.exception.RemoteServiceException;
import com.liyz.cloud.common.util.DateUtil;
import com.liyz.cloud.common.util.PatternUtil;
import com.liyz.cloud.common.util.constant.CommonConstant;
import com.liyz.cloud.service.auth.bo.AuthUserBO;
import com.liyz.cloud.service.auth.feign.staff.FeignStaffAuthService;
import com.liyz.cloud.service.auth.model.AuthJwtDO;
import com.liyz.cloud.service.auth.service.AuthJwtService;
import com.liyz.cloud.service.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/7 16:07
 */
@Slf4j
@Tag(name = "客户鉴权")
@RestController
@RequestMapping("/jwt")
public class FeignJwtParseController {

    private final static String CLAIM_DEVICE = "device";

    @Resource
    private AuthJwtService authJwtService;
    @Resource
    private FeignStaffAuthService feignStaffAuthService;

    @Operation(summary = "解析token")
    @GetMapping("/parseToken")
    public Result<AuthUserBO> parseToken(@RequestParam("token") String token, @RequestParam("clientId") String clientId) {
        AuthJwtDO authJwtDO = authJwtService.getByClientId(clientId);
        if (Objects.isNull(authJwtDO)) {
            log.error("解析token失败, 没有找到该应用下jwt配置信息，clientId：{}", clientId);
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (StringUtils.isBlank(token) || !token.startsWith(authJwtDO.getJwtPrefix())) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        final String authToken = token.substring(authJwtDO.getJwtPrefix().length()).trim();
        Claims unSignClaims;
        try {
            unSignClaims = this.parseClaimsJws(authToken);
        } catch (Exception e) {
            log.error("解析token失败,method:com.liyz.boot3.service.auth.provider.RemoteJwtParseServiceImpl.parseClaimsJws(java.lang.String)");
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        AuthUserBO authUserBO = new AuthUserBO();
        authUserBO.setUsername(unSignClaims.getSubject());
        authUserBO.setDevice(Device.getByType(unSignClaims.get(CLAIM_DEVICE, Integer.class)));
        Result<AuthUserBO> result = feignStaffAuthService.loadByUsername(authUserBO);
        if (!CommonExceptionCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            return Result.error(result.getCode(), result.getMessage());
        }
        AuthUserBO authUser = result.getData();
        if (Objects.isNull(authUser)) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        Claims claims = this.parseClaimsJws(authToken, Joiner.on(CommonConstant.DEFAULT_PADDING).join(authJwtDO.getSigningKey(), authUser.getSalt()));
        if (authJwtDO.getOneOnline() && Objects.nonNull(authUser.getCheckTime()) && claims.getNotBefore().compareTo(authUser.getCheckTime()) < 0) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.OTHERS_LOGIN);
        }
        if (!clientId.equals(claims.getAudience().stream().findFirst().orElse(StringUtils.EMPTY))) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (DateUtil.date().compareTo(claims.getExpiration()) > 0) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_TIMEOUT);
        }
        Result<List<AuthUserBO.AuthGrantedAuthorityBO>> resultAuthority = feignStaffAuthService.authorities(authUser);
        if (!CommonExceptionCodeEnum.SUCCESS.getCode().equals(resultAuthority.getCode())) {
            return Result.error(result.getCode(), result.getMessage());
        }
        return Result.success(
                AuthUserBO.builder()
                        .username(claims.getSubject())
                        .password(StringUtils.EMPTY)
                        .salt(StringUtils.EMPTY)
                        .loginType(LoginType.getByType(PatternUtil.checkMobileEmail(claims.getSubject())))
                        .device(Device.getByType(unSignClaims.get(CLAIM_DEVICE, Integer.class)))
                        .authId(Long.valueOf(claims.getId()))
                        .checkTime(claims.getNotBefore())
                        .roleIds(Lists.newArrayList())
                        .token(authToken)
                        .clientId(claims.getAudience().stream().findFirst().orElse(StringUtils.EMPTY))
                        .authorities(authJwtDO.getIsAuthority() ? resultAuthority.getData() : Lists.newArrayList())
                        .build()
        );
    }

    /**
     * 生成token
     *
     * @param authUser 用户信息
     * @return jwt token
     */
    public Result<Pair<String, String>> generateToken(AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getClientId())) {
            log.error("创建token失败，原因 : clientId is blank");
            throw new RemoteServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthJwtDO authJwtDO = authJwtService.getByClientId(authUser.getClientId());
        if (Objects.isNull(authJwtDO)) {
            log.error("生成token失败, 没有找到该应用下jwt配置信息，clientId : {}", authUser.getClientId());
            throw new RemoteServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        return Result.success(
                Pair.ofNonNull(authJwtDO.getJwtPrefix(),
                        JwtUtil.builder()
                                .id(authUser.getAuthId().toString())
                                .subject(authUser.getUsername())
                                .audience().add(authUser.getClientId()).and()
                                .expiration(new Date(System.currentTimeMillis() + authJwtDO.getExpiration() * 1000))
                                .notBefore(authUser.getCheckTime())
                                .claim(CLAIM_DEVICE, authUser.getDevice().getType())
                                .signWith(
                                        SignatureAlgorithm.forName(authJwtDO.getSignatureAlgorithm()),
                                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(Joiner.on(CommonConstant.DEFAULT_PADDING).join(authJwtDO.getSigningKey(), authUser.getSalt())))
                                )
                                .compact())
        );
    }

    /**
     * 获取失效时间
     *
     * @param token jwt token
     * @return 失效时间
     */
    public Result<Long> getExpiration(final String token) {
        return Result.success(this.parseClaimsJws(token).getExpiration().getTime());
    }

    /**
     * 解析token
     *
     * @param token jwt token
     * @param signingKey 签名
     * @return 解析后属性
     */
    private Claims parseClaimsJws(final String token, final String signingKey) {
        Claims claims;
        try {
            claims = JwtUtil.parser().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        return claims;
    }

    /**
     * 解析token
     *
     * @param token jwt token
     * @return 解析后属性
     */
    private Claims parseClaimsJws(final String token) {
        return JwtUtil.decode(token, DefaultClaims.class);
    }
}
