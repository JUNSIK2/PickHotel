package com.example.common.community;

import com.example.alarm.model.AlarmVo;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 김소진
 * @Title : 실시간 알림 웹소켓 핸들러
 * @date : 2022. 01. 14
 */
@Component
@Slf4j
public class AlarmHandler extends TextWebSocketHandler {
    //로그인 한 전체
    //private static List<WebSocketSession> list = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 1대1
    Map<Integer, WebSocketSession> users = new ConcurrentHashMap<Integer, WebSocketSession>();

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer memNo = getMemberNo(session);
        users.put(memNo, session);
    }

    /* Client가 메세지 전송시 호출되는 메서드 */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        if (msg != null) {
            // Json객체 → Java객체
            AlarmVo alarm = objectMapper.readValue(msg, AlarmVo.class);

            // 접속중인 회원 목록중에 목표 회원에게 발송한다.
            WebSocketSession targetSession = users.get(alarm.getMemNo());  // 메시지를 받을 세션 조회
            // 목표 회원이 실시간 접속시 발송한다.
            if (targetSession != null) {
                TextMessage tmpMsg = new TextMessage(msg);
                targetSession.sendMessage(tmpMsg);
            }
        }
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer memNo = getMemberNo(session);
        users.remove(memNo);
    }

    // 에러 발생시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info(session.getId() + " 익셉션 발생: " + exception.getMessage());
    }

    // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
    private int getMemberNo(WebSocketSession session) {
        UserAccount userAccount = (UserAccount) ((UsernamePasswordAuthenticationToken) session.getPrincipal()).getPrincipal();
        MemberVo member = userAccount.getMember();
        return member.getMemNo();
    }
}