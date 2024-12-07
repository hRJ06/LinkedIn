package com.Hindol.LinkedIn.API_Gateway.Filter;

import com.Hindol.LinkedIn.API_Gateway.Service.JWTService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final JWTService jwtService;
    public AuthenticationFilter(JWTService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }
    @Override
    public GatewayFilter apply(Config config) {
       return (exchange, chain) -> {
           log.info("Login request : {}", exchange.getRequest().getURI());
           final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
           if(Objects.isNull(tokenHeader) || !tokenHeader.startsWith("Bearer")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                log.error("Authorization token header not found");
                return exchange.getResponse().setComplete();
           }
           final String token = tokenHeader.split("Bearer ")[1];
           try {
               String userId = jwtService.getUserIdFromToken(token);
               ServerWebExchange modifiedExchange = exchange
                       .mutate()
                       .request(req -> req.header("X-User-Id", userId))
                       .build();
               return chain.filter(modifiedExchange);
           }
           catch (JwtException jwtException) {
               log.error("JWT Exception : {}", jwtException.getLocalizedMessage());
               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               return exchange.getResponse().setComplete();
           }
       };

    }

    public static class Config {

    }
}
