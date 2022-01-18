package com.example.alarm.service;

import com.example.alarm.model.AlarmVo;

import java.util.List;

public interface AlarmService {
    // 알림을 발송한다
    void registerAlarm(AlarmVo alarm);

    // 회원의 알림 목록을 조회한다.
    List<AlarmVo> retrievAlarmList(int memNo);

    // 회원의 미열람 상태인 알림의 개수를 조회한다.
    int retrieveNoReadAlarmCount(int memNo);

    // 알림 읽음 처리
    void reviseReadAlarmList(int memNo);

    // 조건에 해당하는 알림을 삭제한다.
    void removeAlarm(int alarmNo);

    // 조건에 해당하는 알림을 전체 삭제한다.
    void removeAllAlarm(int memNo);

}
