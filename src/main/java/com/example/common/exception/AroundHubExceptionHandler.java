package com.example.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
    작성자 : 김소진
    작성일 : 2021-12-28
    내용 : JSON으로 연결되는 오류 처리 (사용 안함)
*/

@Slf4j
//@RestControllerAdvice
public class AroundHubExceptionHandler {

    //@ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.info("advice 내 exceptionHandler");

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

//    //@ExceptionHandler(value = AroundHubRuntimeException.class)
//    //AroundHubException가 실행되었을 때, 아래 코드가 작동됨
//    public ResponseEntity<Map<String, String>> ExceptionHandler(AroundHubRuntimeException e) {
//        log.info("ExceptionHandler 실행");
//
//        //header의 vo같은 거 셋팅해줌
//        HttpHeaders responseHeaders = new HttpHeaders();
//
//        //header로 response할 body내용 셋팅
//        Map<String, String> map = new HashMap<>();
//
//        //AroundHubException에서 받아온 getHttpStatusType을 error type 키값에 저장
//        map.put("error type", e.getHttpStatusType());
//        map.put("error code",
//                Integer.toString(e.getHttpStatusCode())); // Map<String, Object>로 설정하면 toString 불필요
//        map.put("message", e.getMessage());
//
//        return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
//    }
}