package com.liyz.cloud.common.feign.bo.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/13 15:03
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthJwtBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1394938733672550428L;

    @Schema(description = "token")
    private String token;

    @Schema(description = "jwt前缀")
    private String jwtPrefix;
}
