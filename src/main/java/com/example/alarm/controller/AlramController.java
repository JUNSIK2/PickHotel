package com.example.alarm.controller;

import com.example.alarm.model.AlarmVo;
import com.example.alarm.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class AlramController {

    @Autowired
    public AlarmService alarmService;

    // 알림을 발송한다
    @PostMapping("/alarm/send")
    public String registerAlarm(AlarmVo alarm) {
        this.alarmService.registerAlarm(alarm);

        String result = "OK";
        return result;
    }

    // 회원의 알림 목록을 조회한다.
    @PostMapping("/alarm/list")
    public List<AlarmVo> retrievAlarmList(int memNo) throws ParseException {

        //회원이 알림 목록을 전체 조회한다.
        List<AlarmVo> alarms = this.alarmService.retrievAlarmList(memNo);
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (AlarmVo alarm : alarms) {
            PrettyTime p = new PrettyTime();

            // 날짜 정보 가공
            String date = alarm.getCreatDate();
            Date javaDate = transFormat.parse(date);
            String creatDate = p.format(javaDate);
            alarm.setCreatDate(creatDate);

            //주소가 없는 경우 빈값 리턴
            if (alarm.getUrl() == null) {
                alarm.setUrl("");
            }
        }
        //알림 읽음 처리
        this.alarmService.reviseReadAlarmList(memNo);
        return alarms;
    }

    // 회원의 미열람 상태인 알림의 개수를 조회한다.
    @PostMapping("/alarm/noReadCount")
    public int retrieveNoReadAlarmCount(int memNo) {
        int result = this.alarmService.retrieveNoReadAlarmCount(memNo);
        return result;
    }

    // 조건에 해당하는 알림을 삭제한다.
    @PostMapping("/alarm/remove")
    public String removeAlarm(int alarmNo) {
        this.alarmService.removeAlarm(alarmNo);

        String result = "OK";
        return result;
    }

    // 조건에 해당하는 알림을 전체 삭제한다.
    @PostMapping("/alarm/removeAll")
    public String removeAllAlarm(int memNo) {
        this.alarmService.removeAllAlarm(memNo);

        String result = "OK";
        return result;
    }

}
