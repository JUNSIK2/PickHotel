package com.example.note.controller;

import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.service.MemberService;
import com.example.note.model.NoteVo;
import com.example.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.*;


@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private MemberService memberService;

    // 받은 쪽지
    @GetMapping("/member/note/Receivelist")
    public String noteReceiveList(Model model, @ModelAttribute("params") NoteVo params, @AuthenticationPrincipal UserAccount userAccount) {
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        params.setNoteGetMbNo(memNo);

        List<NoteVo> noteList = this.noteService.retrieveReceiveNoteList(params);
        if(noteList.size() == 0 ){
            noteList = null;
        } else {
            for (NoteVo note : noteList) {
                String getMbNick = (this.memberService.retrieveMember(note.getNoteGetMbNo())).getId();
                String sendMbNick = (this.memberService.retrieveMember(note.getNoteSendMbNo())).getId();
                if(note.getNoteCon().length() > 10){
                    note.setNoteCon(note.getNoteCon().substring(0,10) + "...");
                }
                note.setNoteGetMbNick(getMbNick);
                note.setNoteSendMbNick(sendMbNick);
            }
        }
        model.addAttribute("noteList", noteList);
        return "page/note_receive_list";
    }

    // 보낸 쪽지
    @GetMapping("/member/note/Sendlist")
    public String noteSendList(Model model, @ModelAttribute("params") NoteVo params, @AuthenticationPrincipal UserAccount userAccount){
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        params.setNoteSendMbNo(memNo);

        List<NoteVo> noteList = this.noteService.retrieveSendNoteList(params);
        if(noteList.size() == 0) {
            noteList = null;
        } else {
            for (NoteVo note : noteList){
                int getMbNo = this.noteService.retrieveOneGetMbNo(memNo, note.getNoteNo());
                String getMbNick = (this.memberService.retrieveMember(getMbNo)).getId();
                String sendMbNick = (this.memberService.retrieveMember(note.getNoteSendMbNo())).getId();
                if(note.getNoteCon().length() > 10){
                    note.setNoteCon(note.getNoteCon().substring(0,10) + "...");
                }
                note.setNoteGetMbNick(getMbNick);
                note.setNoteSendMbNick(sendMbNick);
            }
        }
        model.addAttribute("noteList", noteList);

        return "page/note_send_list";
    }

    // 보관함
    @GetMapping("/member/note/Savelist")
    public String noteSaveList(Model model, @ModelAttribute("params") NoteVo params, @AuthenticationPrincipal UserAccount userAccount){
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        params.setNoteSendMbNo(memNo);
        params.setNoteGetMbNo(memNo);

        List<NoteVo> noteList = this.noteService.retrieveSaveNoteList(params);
        if(noteList.size() == 0){
            noteList = null;
        } else {
            for (NoteVo note : noteList){
                String getMbNick = (this.memberService.retrieveMember(note.getNoteGetMbNo())).getNick();
                String sendMbNick = (this.memberService.retrieveMember(note.getNoteSendMbNo())).getNick();
                note.setNoteGetMbNick(getMbNick);
                note.setNoteSendMbNick(sendMbNick);
            }
        }
        model.addAttribute("noteList", noteList);

        return "page/note_save_list";
    }

    // 쪽지 상세보기
    @GetMapping("/member/note/Detailnote/{noteNo}/{noteGetMbNick}/{noteSendMbNick}/{noteDateTime}/{noteType}")
    public String noteDetail(@PathVariable("noteNo") Integer noteNo, @PathVariable("noteGetMbNick") String noteGetMbNick,
            @PathVariable("noteSendMbNick") String noteSendMbNick,
            @PathVariable("noteDateTime") String noteDateTime,@PathVariable("noteType") int noteType ,
            @AuthenticationPrincipal UserAccount userAccount,Model model){
        NoteVo note = new NoteVo();
        note.setNoteNo(noteNo);

        List<Integer> getMemberNos = this.noteService.retrieveMbNos(noteNo);
        List<String> memberIds = new ArrayList<>();

        for(Integer i : getMemberNos){
            memberIds.add(this.memberService.retrieveMember(i).getId());
        }
        String arr[] = memberIds.toArray(new String[0]);
        String str = String.join(", ", arr);

        note.setNoteCon(this.noteService.retrieveDetailNote(noteNo));
        note.setNoteGetMbNick(noteGetMbNick);
        note.setNoteSendMbNick(noteSendMbNick);
        note.setNoteDateTime(noteDateTime);
        model.addAttribute("note", note);
        model.addAttribute("noteType", noteType);
        model.addAttribute("str", str);
        return "page/note_detail";
    }

    // 쪽지 쓰기 폼
    @GetMapping("/member/note/Writenote")
    public String noteWrite(){
        return "page/note_write";
    }

    // 쪽지 쓰기
    @PostMapping("/member/note/write")
    public String write(@AuthenticationPrincipal UserAccount userAccount, @RequestParam("getMbIds") String getMbIds,
                        @RequestParam("noteCon") String noteCon){

        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        getMbIds = getMbIds.replaceAll(" ", "");
        getMbIds = getMbIds.replaceAll("\\p{Z}", "");
        String[] getMbIdArray = getMbIds.split(",");
        List<String> mbIdArray = Arrays.asList(getMbIdArray);

        List<Integer> mbNos = new ArrayList<>();

        int mbNo;

        for(String mbId : mbIdArray){
            mbNo = this.noteService.retrieveMbNo(mbId);
            mbNos.add(mbNo);
        }
        this.noteService.registerNote(memNo, noteCon, mbNos);

        return "redirect:/member/note/Sendlist";
    }

    // 쪽지 보관함에 저장
    @PostMapping("/member/note/save")
    public String save(@RequestParam("noteNo") List<Integer> noteNos, @RequestParam("noteType") Integer noteType,
                       @AuthenticationPrincipal UserAccount userAccount){

        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        if(noteType == 1){
            for(Integer noteNo : noteNos){
                this.noteService.reviseSaveRetrieveNotes(memNo, noteNo);
            }
            return "redirect:/member/note/Savelist";
        }

        if(noteType == 2){
            for(Integer noteNo : noteNos){
                this.noteService.reviseSaveSendNotes(memNo, noteNo);
            }
            return "redirect:/member/note/Savelist";
        }

        return "redirect:/error";
    }

    // 쪽지 삭제
    @PostMapping("/member/note/delete")
    public String delete(@RequestParam("noteNo") List<Integer> noteNos, @RequestParam("noteType") Integer noteType,
                         @AuthenticationPrincipal UserAccount userAccount){

        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();


        if(noteType == 1){
            for(int noteNo : noteNos){
                this.noteService.removeReceiveNotes(memNo, noteNo);
            }
            return "redirect:/member/note/Receivelist";
        }

        if(noteType == 2){
            for(int noteNo : noteNos){
                this.noteService.removeSendNotes(memNo, noteNo);
            }
            return "redirect:/member/note/Sendlist";
        }

        if(noteType == 3){
            for(int noteNo : noteNos){
                this.noteService.removeSaveSendNotes(memNo, noteNo);
                this.noteService.removeSaveReceiveNotes(memNo, noteNo);
            }
            return "redirect:/member/note/Savelist";
        }
        return "redirect:/error";
    }

    // 회원 유효성 체크
    @ResponseBody
    @GetMapping("/member/note/checkMbId")
    public Map checkMbId(@RequestParam("getMbIds") String getMbIds){
        Map<String, List<String>> map = new HashMap<>();

        getMbIds = getMbIds.replaceAll(" ", "");
        getMbIds = getMbIds.replaceAll("\\p{Z}", "");
        String[] getMbIdArray = getMbIds.split(",");
        List<String> mbIdArray = Arrays.asList(getMbIdArray);

        List<String> notExsitsMbIds = new ArrayList<String>();

        for(String i : mbIdArray){
            int check = this.noteService.checkMbId(i);
            if(check == 0){
                notExsitsMbIds.add(i);
            }
        }

        map.put("mbIdArray", mbIdArray);
        map.put("notExsitsMbIds", notExsitsMbIds);

        return map;
    }

}
