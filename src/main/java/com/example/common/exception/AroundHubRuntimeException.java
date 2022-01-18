//package com.example.exception;
//
//
//import org.springframework.http.HttpStatus;
//
///*
//    작성자 : 김소진
//    작성일 : 2021-12-28
//    내용 : RuntimeException을 상속받아 UncheckedException으로써 사용한다.
//*/
//
//public class AroundHubRuntimeException extends RuntimeException {
//
//    //직렬화를 위한 코드
//    //직렬화란? 객체를 다른 환경에 저장했다가 나중에 재구성 할 수 있게 만드는 과정
//    //직렬화는 언제 사용할까? 객체의 상체를 영속해야할 필요가 있을때 (DB, 캐시, 메모리)
//    //private static final long serialVersionUID = 4663380430591151694L;
//
//    private Constants.ExceptionClass exceptionClass;
//    private HttpStatus httpStatus;
//
//    public AroundHubRuntimeException(Constants.ExceptionClass exceptionClass, HttpStatus httpStatus, String message) {
//        //부모클래스 Exception내 message를 사용하기 위함 = 부모 Exception 객체 message에 메세지 내용을 저장 함
//        super(exceptionClass.toString() + message);
//        //받아온 exceptionClass를 AroundHubException 객체 내 exceptionClass에 저장함
//        this.exceptionClass = exceptionClass;
//        //받아온 httpStatus를 AroundHubException 객체 내 httpStatus에 저장함
//        this.httpStatus = httpStatus;
//    }
//
//    public Constants.ExceptionClass getExceptionClass() {
//        return exceptionClass;
//    }
//
//    public int getHttpStatusCode() {
//        return httpStatus.value();
//    }
//
//    public String getHttpStatusType() {
//        return httpStatus.getReasonPhrase();
//    }
//
//    public HttpStatus getHttpStatus() {
//        return httpStatus;
//    }
//
//}