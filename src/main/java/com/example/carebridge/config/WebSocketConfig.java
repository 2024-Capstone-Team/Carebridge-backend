package com.example.carebridge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 설정을 담당하는 설정 클래스
 * WebSocket을 통한 실시간 양방향 통신을 가능하게 합니다.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커가 구독자들에게 메시지를 전달할 때 사용할 prefix 설정
        // /topic: 일대다 메시징
        // /queue: 일대일 메시징
        config.enableSimpleBroker("/topic", "/queue");

        // 클라이언트가 메시지를 보낼 때 사용할 prefix 설정
        config.setApplicationDestinationPrefixes("/app");

        // 개인 메시지를 위한 사용자 prefix 설정
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트 등록
        // withSockJS(): WebSocket을 지원하지 않는 브라우저를 위한 대체 옵션 제공
        registry.addEndpoint("/ws-chat")
                .setAllowedOrigins("*")  // CORS 설정
                .withSockJS();
    }
}