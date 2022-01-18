package com.example.common.community;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;
    private final AlarmHandler alarmHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //ws/chat 웹소켓 서버가 켜지면 chatHandler가 실행된다.
        registry.addHandler(chatHandler, "ws/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor()); // 핸드셰이크 요청을 인터셉트할 인터셉터

        //ws/alarm 웹소켓 서버가 켜지면 alarmHandler가 실행된다.
        registry.addHandler(alarmHandler, "member/ws/alarm") // 특정 URL에 웹소켓 핸들러를 매핑한다.
                .addInterceptors(new HttpSessionHandshakeInterceptor()); // 핸드셰이크 요청을 인터셉트할 인터셉터
    }

}