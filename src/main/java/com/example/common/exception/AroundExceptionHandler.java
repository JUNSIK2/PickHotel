package com.example.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/*
    작성자 : 김소진
    작성일 : 2021-12-28
    내용 : 화면으로 연결되는 오류 처리
*/

@Slf4j
@ControllerAdvice
public class AroundExceptionHandler {

    //에러페이지 경로 지정
    private String VIEW_PATH = "/error/";

    /**
     * 별도로 설정하지 않은 오류인 경우 발생
     */
    @ExceptionHandler(Exception.class)
    public String eventErrorHandler(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("message", e.getMessage());

        return VIEW_PATH + "error";
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.info("MethodArgumentNotValidException오류!");

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(errors);
    }


//    /**
//     * 오류 발생지점을 정확히 특정하여 사용하고 싶은 경우 사용하는 커스텀 오류
//     * 알수 있는 정보 : 오류 발생 지점, 오류 메세지
//     */
//    @ExceptionHandler(value = AroundHubRuntimeException.class)
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