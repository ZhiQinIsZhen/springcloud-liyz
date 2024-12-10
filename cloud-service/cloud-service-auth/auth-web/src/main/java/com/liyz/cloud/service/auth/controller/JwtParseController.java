package com.liyz.cloud.service.auth.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.liyz.cloud.common.api.annotation.Anonymous;
import com.liyz.cloud.common.exception.CommonExceptionCodeEnum;
import com.liyz.cloud.common.exception.RemoteServiceException;
import com.liyz.cloud.common.feign.bo.auth.AuthUserBO;
import com.liyz.cloud.common.feign.bo.jwt.AuthJwtBO;
import com.liyz.cloud.common.feign.constant.Device;
import com.liyz.cloud.common.feign.constant.LoginType;
import com.liyz.cloud.common.feign.dto.auth.AuthUserDTO;
import com.liyz.cloud.common.feign.result.Result;
import com.liyz.cloud.common.util.DateUtil;
import com.liyz.cloud.common.util.PatternUtil;
import com.liyz.cloud.common.util.constant.CommonConstant;
import com.liyz.cloud.service.auth.model.AuthJwtDO;
import com.liyz.cloud.service.auth.service.AuthJwtService;
import com.liyz.cloud.service.auth.util.JwtUtil;
import com.liyz.cloud.service.staff.feign.StaffAuthFeignService;
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
import org.springframework.web.bind.annotation.*;

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
@Anonymous
@RestController
@RequestMapping("/jwt")
public class JwtParseController {

    private final static String CLAIM_DEVICE = "device";

    @Resource
    private AuthJwtService authJwtService;
    @Resource
    private StaffAuthFeignService staffAuthFeignService;

    @Operation(summary = "解析token")
    @GetMapping("/parseToken")
    public Result<AuthUserBO> parseToken(@RequestParam("token") String token, @RequestParam("clientId") String clientId) {
        AuthJwtDO authJwtDO = authJwtService.getByClientId(clientId);
        if (Objects.isNull(authJwtDO)) {
            log.error("解析token失败, 没有找到该应用下jwt配置信息，clientId：{}", clientId);
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (StringUtils.isBlank(token) || !token.startsWith(authJwtDO.getJwtPrefix())) {
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        final String authToken = token.substring(authJwtDO.getJwtPrefix().length()).trim();
        Claims unSignClaims;
        try {
            unSignClaims = this.parseClaimsJws(authToken);
        } catch (Exception e) {
            log.error("解析token失败", e);
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setUsername(unSignClaims.getSubject());
        authUserDTO.setDevice(Device.getByType(unSignClaims.get(CLAIM_DEVICE, Integer.class)));
        Result<AuthUserBO> result = staffAuthFeignService.loadByUsername(authUserDTO);
        if (!CommonExceptionCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            return Result.error(result.getCode(), result.getMessage());
        }
        AuthUserBO authUser = result.getData();
        if (Objects.isNull(authUser)) {
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        Claims claims = this.parseClaimsJws(authToken,
                Joiner.on(CommonConstant.DEFAULT_PADDING).join(authJwtDO.getSigningKey(), authUser.getSalt()));
        if (authJwtDO.getOneOnline() && Objects.nonNull(authUser.getCheckTime())
                && claims.getNotBefore().compareTo(authUser.getCheckTime()) < 0) {
            throw new RemoteServiceException(CommonExceptionCodeEnum.OTHERS_LOGIN);
        }
        if (!clientId.equals(claims.getAudience().stream().findFirst().orElse(StringUtils.EMPTY))) {
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        if (DateUtil.date().compareTo(claims.getExpiration()) > 0) {
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_TIMEOUT);
        }
        Result<List<AuthUserBO.AuthGrantedAuthorityBO>> resultAuthority = staffAuthFeignService.authorities(authUserDTO);
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

    @Operation(summary = "生成token")
    @PostMapping("/generateToken")
    public Result<AuthJwtBO> generateToken(@RequestBody AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getClientId())) {
            log.error("创建token失败，原因 : clientId is blank");
            throw new RemoteServiceException(CommonExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthJwtDO authJwtDO = authJwtService.getByClientId(authUser.getClientId());
        if (Objects.isNull(authJwtDO)) {
            log.error("生成token失败, 没有找到该应用下jwt配置信息，clientId : {}", authUser.getClientId());
            throw new RemoteServiceException(CommonExceptionCodeEnum.LOGIN_ERROR);
        }

        return Result.success(
                AuthJwtBO
                        .builder()
                        .jwtPrefix(authJwtDO.getJwtPrefix())
                        .token(JwtUtil.builder()
                                .id(authUser.getAuthId().toString())
                                .subject(authUser.getUsername())
                                .audience().add(authUser.getClientId()).and()
                                .expiration(new Date(System.currentTimeMillis() + authJwtDO.getExpiration() * 1000))
                                .notBefore(authUser.getCheckTime())
                                .claim(CLAIM_DEVICE, authUser.getDevice().getType())
                                .signWith(
                                        SignatureAlgorithm.forName(authJwtDO.getSignatureAlgorithm()),
                                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(Joiner
                                                .on(CommonConstant.DEFAULT_PADDING)
                                                .join(authJwtDO.getSigningKey(), authUser.getSalt())))
                                )
                                .compact())
                        .build()
        );
    }

    @Operation(summary = "获取失效时间")
    @GetMapping("/getExpiration")
    public Result<Long> getExpiration(@RequestParam("token") final String token) {
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
            throw new RemoteServiceException(CommonExceptionCodeEnum.AUTHORIZATION_FAIL);
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
