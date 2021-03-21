package com.example.ArticleAI.configurations;

import com.example.ArticleAI.configurations.handler.CustomHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/steps")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandshakeHandler());

        registry
                .addEndpoint("/steps")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .withSockJS();
    }
}