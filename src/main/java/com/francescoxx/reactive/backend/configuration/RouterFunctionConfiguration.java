package com.francescoxx.reactive.backend.configuration;

import com.francescoxx.reactive.backend.RouterHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RouterFunctionConfiguration {

    @Bean
    RouterFunction<?> routerFunction(RouterHandlers routerHandlers) {

        return RouterFunctions.route(RequestPredicates.GET("/rest/player/all"), routerHandlers::getAll)
                .andRoute(RequestPredicates.GET("/rest/player/{id}"), routerHandlers::getId)
                .andRoute(RequestPredicates.GET("/rest/player/{id}/timedEvents"), routerHandlers::getEvents)
                ;
    }
}
