package com.example.common.exception;

/*
    작성자 : 김소진
    작성일 : 2021-12-28
    내용 : AroundHubRuntimeException 사용 시, 오류 명을 관리하기 위한 Enum 클래스
*/

public class Constants {

    public enum ExceptionMsgClass {

        //상수 정의
        //[중요] 상수 유형에 따라 추가, 삭제 할 것
        NOTPOST("존재하는 게시글이 아닙니다."), NOTMEMBER("회원만 접근 가능합니다.");

        //상수를 사용하기 위한 셋팅
        private String exceptionMsgClass;

        //1
        ExceptionMsgClass(String exceptionMsgClass) {
            this.exceptionMsgClass = exceptionMsgClass;
        }

        //2
        public String getExceptionMsgClass() {
            return exceptionMsgClass;
        }

    }
}