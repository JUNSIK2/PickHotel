package com.example.alarm.persistent;

import com.example.alarm.model.AlarmVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("alarmDao")
public class AlarmDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    // 알림 정보를 등록한다
    public void insertAlarm(AlarmVo alarm) {
        this.sqlSessionTemplate.insert("AlarmDao.insertAlarm", alarm);
    }

    // 회원의 알림 목록을 조회한다.
    public List<AlarmVo> selectAlarmList(int memNo) {
        List<AlarmVo> alarms = this.sqlSessionTemplate.selectList("AlarmDao.selectAlarmList", memNo);
        return alarms;

    }

    // 회원의 미열람 상태인 알림의 개수를 조회한다.
    public int selectNoReadAlarmCount(int memNo) {
        int result = this.sqlSessionTemplate.selectOne("AlarmDao.selectNoReadAlarmCount", memNo);
        return result;

    }

    // 알림 읽음 상태로 변경한다.
    public void updateReadAlarmList(int memNo) {
        this.sqlSessionTemplate.update("AlarmDao.updateReadAlarmList", memNo);
    }

    // 조건에 해당하는 알림을 삭제한다.
    public void delectAlarm(int no) {
        this.sqlSessionTemplate.delete("AlarmDao.delectAlarm", no);
    }

    // 조건에 해당하는 알림을 전체 삭제한다.
    public void delectAllAlarm(int memNo) {
        this.sqlSessionTemplate.delete("AlarmDao.delectAllAlarm", memNo);
    }
}
