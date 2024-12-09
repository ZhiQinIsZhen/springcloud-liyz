package com.liyz.cloud.gateway.filter;

import com.liyz.cloud.common.util.PatternUtil;
import com.liyz.cloud.gateway.properties.AnonymousMappingProperties;
import com.liyz.cloud.gateway.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * Desc:Jwt全局过滤器
 * <p>
 *     gateway的filter中的顺序是由接口{@link Ordered}来决定的
 * </p>
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/27 16:44
 */
@Slf4j
@Component
@RefreshScope
@EnableConfigurationProperties(AnonymousMappingProperties.class)
public class GlobalJWTFilter implements GlobalFilter, Ordered {

    private final AnonymousMappingProperties properties;

    public GlobalJWTFilter(AnonymousMappingProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return ResponseUtil.response(response, "404", "NOT_FOUND");
        }
        String path = request.getURI().getPath();
        String clientId = route.getId();
        Set<String> mappingSet = properties.getServer().get(clientId);
        if (!CollectionUtils.isEmpty(mappingSet) && (mappingSet.contains(path) || PatternUtil.pathMatch(path, mappingSet))) {
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
