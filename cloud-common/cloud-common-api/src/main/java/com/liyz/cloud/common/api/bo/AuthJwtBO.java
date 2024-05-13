package com.liyz.cloud.common.api.bo;

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

    /**
     * token
     */
    private String token;

    /**
     * jwt前缀
     */
    private String jwtPrefix;
}
