package com.example.board.controller;

import com.example.board.model.BoardGroupVo;
import com.example.board.model.BoardVo;
import com.example.board.service.BoardGroupService;
import com.example.board.service.BoardService;
import com.example.grade.model.SiteGradeVo;
import com.example.grade.service.SiteGradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class BoardAdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardGroupService boardGroupService;

    @Autowired
    private SiteGradeService siteGradeService;

    // ######## 게시판 그룹 관리 ########
    @GetMapping("/intranet/boardgroup_list")
    public String boardGroupList() {
        return "page/intranet/boardgroup_list";
    }

    // ######## 게시판 관리 ########
    // 게시판 만들기 폼
    @GetMapping("/intranet/board/write")
    public String boardWrite(Model model) {
        List<BoardGroupVo> boardGroupList = boardGroupService.retrieveBoardGroupList();
        model.addAttribute("boardGroupList", boardGroupList);

        //현재 사용중인 사이트 등급 목록
        List<SiteGradeVo> useSiteGrades = siteGradeService.retriveSiteGradeToUser();
        model.addAttribute("useSiteGrades", useSiteGrades);

        return "page/intranet/board_write";
    }

    // ######## 게시판 등록 ########
    @PostMapping("/intranet/board/add")
    public String boardAdd(@Valid BoardVo board,
                           @RequestParam(value = "usePhoto", defaultValue = "0") int usePhoto,
                           @RequestParam(value = "useVideo", defaultValue = "0") int useVideo,
                           @RequestParam(value = "useComment", defaultValue = "0") int useComment) {

        board.setUsePhoto(usePhoto);
        board.setUseVideo(useVideo);
        board.setUseComment(useComment);

        // 게시판 정보 추가
        boardService.registerBoard(board);

        return "redirect:/intranet/board/list";
    }

    // ######## 게시판 목록 조회 ########
    @GetMapping("/intranet/board/list")
    public String boardList(@ModelAttribute("params") BoardVo params, Model model) {
        List<BoardVo> boardList = boardService.retrievePageBoardList(params);
        model.addAttribute("boardList", boardList);

        return "page/intranet/board_list";
    }

    // 게시판 상세 조회(수정 폼 보기)
    @GetMapping({"/intranet/board/{boardNo}", "/intranet/board/"})
    public String boardDetail(@PathVariable int boardNo, Model model) {
        BoardVo board = boardService.retrieveBoard(boardNo);
        model.addAttribute("board", board);

        List<BoardGroupVo> boardGroupList = boardGroupService.retrieveBoardGroupList();
        model.addAttribute("boardGroupList", boardGroupList);

        //현재 사용중인 사이트 등급 목록
        List<SiteGradeVo> useSiteGrades = siteGradeService.retriveSiteGradeToUser();
        model.addAttribute("useSiteGrades", useSiteGrades);

        return "page/intranet/board_modify";
    }

    // 게시판 수정하기 버튼 눌렀을때
    @PostMapping("/intranet/board/modify")
    public String boardModify(BoardVo board,
                              @RequestParam(value = "usePhoto", defaultValue = "0") int usePhoto,
                              @RequestParam(value = "useVideo", defaultValue = "0") int useVideo,
                              @RequestParam(value = "useComment", defaultValue = "0") int useComment) {

        board.setUsePhoto(usePhoto);
        board.setUseVideo(useVideo);
        board.setUseComment(useComment);
        boardService.reviseBoard(board);

        return "redirect:/intranet/board/list";
    }

    // 게시판 삭제하기 버튼 눌렀을때
    @ResponseBody
    @GetMapping("/intranet/board_delete/{boardNo}")
    public String boardDelete(@PathVariable int boardNo) {
        BoardVo board = boardService.retrieveBoard(boardNo);
        int postCount = board.getPostCount();
        System.out.println("=====>>>>" + postCount);
        if (postCount != 0) {
            return "failed";
        } else {
            boardService.removeBoard(boardNo);
            return "success";
        }
    }

    // ################# 게시판 그룹 시작 ####################
    // 게시판 그룹 만들기 폼
    @GetMapping("/intranet/board_group/write")
    public String boardGroupWrite() {

        return "page/intranet/board_group_write";
    }

    // ######## 게시판 그룹 등록 ########
    @PostMapping("/intranet/board_group/add")
    public String boardGroupAdd(@RequestParam("groupName") String groupName) {
        // 게시판 정보 추가
        BoardGroupVo boardGroup = new BoardGroupVo();
        boardGroup.setGroupName(groupName);
        boardGroupService.registerBoardGroup(boardGroup);
        return "redirect:/intranet/board_group_list";
    }


    // ######## 게시판 그룹 목록 조회 ########
    @GetMapping("/intranet/board_group_list")
    public String boardGroupList(@ModelAttribute("params") BoardGroupVo params, Model model) {
        System.out.println("call boardGroupList");
        List<BoardGroupVo> boardGroupList = boardGroupService.retrievePageBoardGroupList(params);

        for (BoardGroupVo boardGroup : boardGroupList) {
            int groupNo = boardGroup.getGroupNo();
            int boardCount = boardGroupService.retrieveBoardGroupCount(groupNo);
            boardGroup.setBoardCount(boardCount);
        }
        // DB에 없으니까 여기서 게시판 그룹에 속한 게시판 개수 세줌
        model.addAttribute("boardGroupList", boardGroupList);

        return "page/intranet/board_group_list";
    }


    // 게시판 그룹 상세 조회(수정 폼 보기)
    @GetMapping("/intranet/board_group/{groupNo}")
    public String boardGroupDetail(@PathVariable int groupNo, Model model) {
        BoardGroupVo boardGroup = boardGroupService.retrieveBoardGroup(groupNo);

        model.addAttribute("boardGroup", boardGroup);

        return "page/intranet/board_group_modify";
    }

    // 게시판 그룹 수정하기 버튼 눌렀을때
    @PostMapping("/intranet/board_group/modify")
    public String boardGroupModify(BoardGroupVo boardGroup) {

        boardGroupService.reviseBoardGroup(boardGroup);

        return "redirect:/intranet/board_group_list";
    }

    // 게시판 그룹 삭제하기 버튼 눌렀을때
    @ResponseBody
    @GetMapping("/intranet/board_group/delete")
    public String boardGroupDelete(@RequestParam int groupNo) {
        int boardCount = boardGroupService.retrieveBoardGroupCount(groupNo);

        System.out.println("boardCount" + boardCount);
        System.out.println("=====>>>>" + boardCount);
        if (boardCount != 0) {
            return "failed";
        } else {
            boardGroupService.removeBoardGroup(groupNo);
            return "success";
        }
    }

}
