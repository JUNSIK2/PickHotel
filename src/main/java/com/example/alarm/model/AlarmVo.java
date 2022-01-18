package com.example.alarm.model;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class AlarmVo {
    private int no;
    @NonNull
    private String type;
    private int read;
    @NonNull
    private String content;
    private String url;
    private String creatDate;
    @NonNull
    private int memNo;
    //알림 발송 여부 - 이전 작업 작동 정상 유무
    private String result;
    //@NonNull
    //알림 발송 여부
    private String isSend;
}