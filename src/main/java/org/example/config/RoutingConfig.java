package org.example.config;

import org.example.handler.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RoutingConfig {

@Bean
public RouterFunction<ServerResponse> routes(GreetingHandler handler) {
    return RouterFunctions.route()
            .POST("/greetings", handler::createGreeting)
            .GET("/greetings", handler::getAllGreetings)
            .GET("/greetings/{id}", handler::getGreetingById)
            .GET("/numbers/stream", handler::streamRandomNumbers)
            .DELETE("/delete/{id}", handler ::delete)
            .build();
}
}
