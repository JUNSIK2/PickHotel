package com.example.grade.controller;


import com.example.alarm.AlarmMsg;
import com.example.alarm.model.AlarmVo;
import com.example.alarm.service.AlarmService;
import com.example.grade.model.GradeUpVo;
import com.example.grade.service.GradeUpService;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GradeUpAdminController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private GradeUpService gradeUpService;

    @Autowired
    private AlarmService alarmService;

    // ######## 등업 신청 건 관리 ########
    // 등업신청 건 목록조회(관리자)
    @GetMapping("/intranet/member/gradeup_list")
    public String list(Model model) {
        List<GradeUpVo> gradeUps = this.gradeUpService.selectAllGradeUps();
        model.addAttribute("GradeUps", gradeUps);
        return "page/intranet/member_gradeup_list";
    }

    // 등업신청 건 목록조회 ajax
    @ResponseBody
    @PostMapping("/intranet/member/gradeup_list")
    public List<GradeUpVo> gradeUpList() {
        List<GradeUpVo> gradeUps = this.gradeUpService.selectAllGradeUps();
        return gradeUps;
    }

    // 등업신청 건 상태 변경
    @ResponseBody
    @PostMapping("/intranet/member/gradeup_update")
    public List<AlarmVo> updateGradeUp(@RequestParam(value = "gradenoList") List<Integer> gradenos,
                                       @RequestParam("gradeupstate") String gradeupstate) {

        List<AlarmVo> alarms = new ArrayList<>();
        AlarmVo alarm = new AlarmVo();
        String alarmSend = "true";

        for (int gradeno : gradenos) {
            // 해당 등업 신청건의 정보 가져오기
            GradeUpVo gradeUpVo = gradeUpService.selectOneGradeUp(gradeno);
            int memNo = gradeUpVo.getMemNo();
            int aftergrade = gradeUpVo.getAftergrade();

            //알림 정보 준비
            String alarmType = "3";
            String alarmMsg = "";
            int alarmGetMemNo = memNo;

            if (gradeupstate.equals("accept")) {//등업 신청 수락 버튼 클릭
                // 해당 회원이 x등급으로 등업이 가능한 조건인지 확인 할 것

                // 회원 등급 변경
                memberService.reviseMemberGrade(memNo, aftergrade);

                // 등업 건 승인 처리
                gradeUpService.acceptGradeUp(gradeno);

                //알림 메세지 설정
                alarmMsg = aftergrade + AlarmMsg.GRADEUP.getValue();

            } else if (gradeupstate.equals("reset")) { //등업 신청 거절 버튼 클릭
                /// 등업 건 거절 처리
                gradeUpService.resetGradeUp(gradeno);

                //알림 메세지 설정
                alarmMsg = AlarmMsg.GRADEUPFAIL.getValue();
            }

            //알림 셋팅 발송
            alarm.setType(alarmType);
            alarm.setContent(alarmMsg);
            alarm.setMemNo(alarmGetMemNo);
            alarm.setIsSend(alarmSend);
            alarm.setResult("OK");

            //알림 db저장
            this.alarmService.registerAlarm(alarm);

            alarms.add(alarm);
        }

        return alarms;
    }
}
