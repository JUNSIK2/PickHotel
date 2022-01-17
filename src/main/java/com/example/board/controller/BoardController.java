package com.example.board.controller;

import com.example.board.MapApi.MetaVo;
import com.example.board.model.*;
import com.example.board.service.*;
import com.example.common.exception.Constants;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BoardController {


    @Autowired
    public MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PostService postService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private MapServiceForApi mapServiceForApi;

    // ########## 게시글 ########## //
    // 게시글 작성 폼
    @GetMapping("/board/{boardNo}/post/register")
    public String writeForm(@PathVariable("boardNo") Integer boardNo, @AuthenticationPrincipal UserAccount userAccount, Model model, HttpServletRequest req, HttpServletResponse res) {
        //비회원 접근할시 익셉션
        checkUser(userAccount, req, res);

        //게시판 목록을 통해 게시글을 작성하려 할때, 유입된 게시판에 작성이 선택된다.
        int defaultListNo = 0;
        if (boardNo == null) {
            defaultListNo = 1;
        } else {
            defaultListNo = boardNo;
        }
        BoardVo board = this.boardService.retrieveBoard(boardNo);

        List<BoardVo> boards = this.boardService.retrieveBoardList();
        Map<Integer, String> boardList = new HashMap<Integer, String>();

        for (BoardVo b : boards) {
            boardList.put(b.getBoardNo(), b.getTitle());
        }

        // request 영역에 디폴트 게시판 정보를 저장한다.
        model.addAttribute("defaultListNo", defaultListNo);
        // request 영역에 게시판 리스트 정보를 저장한다.
        model.addAttribute("boardList", boardList);
        // 게시판 타입을 저장한다.
        model.addAttribute("boardType", board.getType());

        return "page/post_write";

    }

    //게시글 작성 시 이미지, 동영상, 리뷰 사용 체크
    @ResponseBody
    @GetMapping("/board/{boardNo}/post/checkUse")
    public Map checkUse(@PathVariable("boardNo") int boardNo, @AuthenticationPrincipal UserAccount userAccount, HttpServletRequest req, HttpServletResponse res) {
        //비회원 접근할시 익셉션
        checkUser(userAccount, req, res);

        BoardVo board = this.boardService.retrieveBoard(boardNo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("board", board);

        return map;
    }

    // 게시글 목록
    @GetMapping("/boardList/{boardNo}")
    public String list(@PathVariable(name = "boardNo", required = false) Integer boardNo, Model model,
                       @ModelAttribute("params") PostVo params) {

        //게시판 ID로 해당 게시판 내 게시글 목록을 가져온다.
        List<PostVo> posts = this.postService.retrievePostList(params);
        if (posts.size() == 0) {
            posts = null;
        }
        model.addAttribute("posts", posts);

        //게시판 ID로 해당 게시판 정보를 가져온다
        BoardVo board = boardService.retrieveBoard(boardNo);
        model.addAttribute("board", board);

        return "page/post_list";
    }

    // 유형별 게시판 조회 - headr에서 사용
    @ResponseBody
    @GetMapping("/loadboardtype")
    public List<BoardVo> boardListByType(@RequestParam("type") String type) {
        List<BoardVo> boards = this.boardService.retrieveBoardListByType(type);
        return boards;
    }


    // 내가 작성한 게시글 목록
    @GetMapping("/member/room")
    public String myList(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        List<PostVo> posts = this.postService.retrieveMyPosts(memNo);
        model.addAttribute("posts", posts);

        return "page/member_room";
    }

    // 회원이 작성한 게시글 목록 (회원 정보 확인/회원이 작성한 글)
    @GetMapping("/member/{memNo}")
    public String memberWriteList(@PathVariable("memNo") int memNo, Model model) {

        List<PostVo> posts = this.postService.retrieveMyPosts(memNo);
        model.addAttribute("posts", posts);

        return "page/member_post_list";
    }

    // 게시글 태그로 전체 검색
    @GetMapping("/postListByTagName")
    public String retrievePostByTag(@RequestParam("encodedTagName") String encodedTagName, Model model) {

        List<PostVo> posts = this.postService.retrievePostByTag(encodedTagName);
        model.addAttribute("posts", posts);

        return "page/post_list_bytag";
    }

    // 게시글 상세보기
    @GetMapping("/board/{boardNo}/post/{postNo}")
    public String read(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("postNo") int postNo, @PathVariable("boardNo") int boardNo, Model model, HttpServletRequest req, HttpServletResponse response) {
        // ######### 게시글 상세정보 시작 ######### //
        PostVo post = null;
        PostVo test = new PostVo();
        test.setPostNo(postNo);
        test.setBoardNo(boardNo);

        if (postService.retrievePostSearch(test) != null) {
            post = this.postService.retrieveDetailBoard(postNo);
        }
        if (post == null) {
            throw new RuntimeException(Constants.ExceptionMsgClass.NOTPOST.getExceptionMsgClass());
        }

        List<AttachVo> attachVoList = this.attachService.retrievePostAttach(postNo);
        ReviewVo review = this.reviewService.retrieveReview(postNo);
        MapVoForApi mapVoForApi = new MapVoForApi();

        // tag값 배열로 셋팅
        String tag = post.getTag();
        if (tag != null && tag != "") {
            String[] tags = tag.split(",");
            post.setTags(tags);
        }

        try {
            mapVoForApi = this.mapServiceForApi.retrieveMap(review.getRoomNo());
            model.addAttribute("mapVoForApi", mapVoForApi);
        } catch (Exception exception) {

        }

        if (mapVoForApi == null) {
            model.addAttribute("mapVoForApi", mapVoForApi);
        }

        model.addAttribute("post", post);
        model.addAttribute("attachList", attachVoList);
        model.addAttribute("review", review);
        // ######### 게시글 상세정보 끝 ######### //

        // ######### 게시글 조회수 증가 시작 ######### //
        // 조회수 중복방지는 필요한 기능이지만, 완벽하게 차단해야 할만큼 중대한 사항은 아니라고 생각, 따라서 쿠키를 사용
        Cookie oldCookie = null;
        Cookie[] cookies = req.getCookies();

        //request에서 넘어온 쿠키가 있는 경우
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //이미 게시글을 1개 이상 본 기록이 있는 경우
                if (cookie.getName().equals("postView")) {
                    //게시글 조회 쿠키 기록을 가져온다.
                    oldCookie = cookie;
                }
            }
        }

        int memNo = 0;
        //동일한 컴퓨터에서 다른 아이디로 로그인하여 view했을때 조회수 증가를 위한 코드추가
        if (userAccount != null) {
            MemberVo member = userAccount.getMember();
            memNo = member.getMemNo();
        }

        //게시글 조회 기록이 있는 경우 (oldCookie = 이전 게시글 조회 기록)
        if (oldCookie != null) {
            log.info("oldCookie:{}", oldCookie.getValue());

            //현재 조회하려는 게시글을 본 쿠키 기록이 없는 경우
            if (!oldCookie.getValue().contains("[" + memNo + "-" + postNo + "]")) {
                //조회수를 올린다
                postService.upHitcount(postNo);
                // 현재 게시글을 조회했다고 쿠키에 추가한다.
                oldCookie.setValue(oldCookie.getValue() + "_[" + memNo + "-" + postNo + "]");
                //웹어플리케이션의 모든 URL 범위에서 전송할 수 있도록 Path를 설정해준다.
                oldCookie.setPath("/");
                //하루에 한번만 조회수가 올라가게 한다.
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else { //게시글 조회 기록이 없는 경우
            //조회수를 올린다.
            postService.upHitcount(postNo);
            //게시글 조회 기록 쿠키를 생성한다.
            Cookie newCookie = new Cookie("postView", "[" + memNo + "-" + postNo + "]");
            //웹어플리케이션의 모든 URL 범위에서 전송할 수 있도록 Path를 설정해준다.
            newCookie.setPath("/");
            //하루에 한번만 조회수가 올라가게 한다.
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
        // ######### 게시글 조회수 증가 끝 ######### //

        // ######해당 게시판에서 댓글 사용 여부 판단######
        int useComment = this.boardService.retrieveBoard(post.getBoardNo()).getUseComment();
        model.addAttribute("useComment", useComment);

        if (useComment != 0) {
            // ######### 댓글 목록 시작 ######### //
            List<CommentVo> comments = this.commentService.retrieveCommentList(postNo);
            model.addAttribute("comments", comments);
            // ######### 댓글 목록 조회 끝 ######### //
        }
        // ######해당 게시판에서 댓글 사용 여부 끝 ######### //

        return "page/post_detail";
    }


    // 내가 작성한 글 보기기 - 게시판 별 접근 권한 시큐리티를 거치지 않고 접근
    @GetMapping("/member/post/{postNo}")
    public String myPostRead(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("postNo") int postNo, Model model, HttpServletRequest req, HttpServletResponse response) {

        // 작성자 본인이거나 관리자 인지 권한 확인
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        int memberGrade = member.getGrade();

        // 작성된 게시글 작성자 id
        int writerNo = this.postService.retrieveDetailBoard(postNo).getWriterNo();

        // 작성자 본인이 아니고, 관리자도 아닌 경우
        if (memNo != writerNo && memberGrade != 5) {
            //권한 없음 페이지로 이동
            return "redirect:/denine";

        } else {
            // ######### 게시글 상세정보 시작 ######### //
            PostVo post = this.postService.retrieveDetailBoard(postNo);
            if (post == null) {
                throw new RuntimeException(Constants.ExceptionMsgClass.NOTPOST.getExceptionMsgClass());
            }

            List<AttachVo> attachVoList = this.attachService.retrievePostAttach(postNo);
            ReviewVo review = this.reviewService.retrieveReview(postNo);
            MapVoForApi mapVoForApi = new MapVoForApi();

            // tag값 배열로 셋팅
            String tag = post.getTag();
            if (tag != null && tag != "") {
                String[] tags = tag.split(",");
                post.setTags(tags);
            }

            try {
                mapVoForApi = this.mapServiceForApi.retrieveMap(review.getRoomNo());
                model.addAttribute("mapVoForApi", mapVoForApi);
            } catch (Exception exception) {

            }

            if (mapVoForApi == null) {
                model.addAttribute("mapVoForApi", mapVoForApi);
            }

            model.addAttribute("post", post);
            model.addAttribute("attachList", attachVoList);
            model.addAttribute("review", review);
            // ######### 게시글 상세정보 끝 ######### //


            // ######### 게시글 조회수 증가 시작 ######### //
            // 조회수 중복방지는 필요한 기능이지만, 완벽하게 차단해야 할만큼 중대한 사항은 아니라고 생각, 따라서 쿠키를 사용
            Cookie oldCookie = null;
            Cookie[] cookies = req.getCookies();

            //request에서 넘어온 쿠키가 있는 경우
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    //이미 게시글을 1개 이상 본 기록이 있는 경우
                    if (cookie.getName().equals("postView")) {
                        //게시글 조회 쿠키 기록을 가져온다.
                        oldCookie = cookie;
                    }
                }
            }

            //게시글 조회 기록이 있는 경우 (oldCookie = 이전 게시글 조회 기록)
            if (oldCookie != null) {
                log.info("oldCookie:{}", oldCookie.getValue());

                //현재 조회하려는 게시글을 본 쿠키 기록이 없는 경우
                if (!oldCookie.getValue().contains("[" + memNo + "-" + postNo + "]")) {
                    //조회수를 올린다
                    postService.upHitcount(postNo);
                    // 현재 게시글을 조회했다고 쿠키에 추가한다.
                    oldCookie.setValue(oldCookie.getValue() + "_[" + memNo + "-" + postNo + "]");
                    //웹어플리케이션의 모든 URL 범위에서 전송할 수 있도록 Path를 설정해준다.
                    oldCookie.setPath("/");
                    //하루에 한번만 조회수가 올라가게 한다.
                    oldCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(oldCookie);
                }
            } else { //게시글 조회 기록이 없는 경우
                //조회수를 올린다.
                postService.upHitcount(postNo);
                //게시글 조회 기록 쿠키를 생성한다.
                Cookie newCookie = new Cookie("postView", "[" + memNo + "-" + postNo + "]");
                //웹어플리케이션의 모든 URL 범위에서 전송할 수 있도록 Path를 설정해준다.
                newCookie.setPath("/");
                //하루에 한번만 조회수가 올라가게 한다.
                newCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(newCookie);
            }
            // ######### 게시글 조회수 증가 끝 ######### //

            // ######해당 게시판에서 댓글 사용 여부 판단######
            int useComment = this.boardService.retrieveBoard(post.getBoardNo()).getUseComment();
            model.addAttribute("useComment", useComment);

            if (useComment != 0) {
                // ######### 댓글 목록 시작 ######### //
                List<CommentVo> comments = this.commentService.retrieveCommentList(postNo);
                model.addAttribute("comments", comments);
                // ######### 댓글 목록 조회 끝 ######### //
            }
            // ######해당 게시판에서 댓글 사용 여부 끝 ######### //

        }
        return "page/post_detail";
    }

    // 카카오 맵에 정보 전달
    @ResponseBody
    @GetMapping("/findMapInfo")
    public Map giveMapinfo(@RequestParam(value = "postNo") int postNo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        PostVo post = this.postService.retrieveDetailBoard(postNo);
        if (post == null) {
            throw new RuntimeException(Constants.ExceptionMsgClass.NOTPOST.getExceptionMsgClass());
        }
        ReviewVo review = this.reviewService.retrieveReview(postNo);
        MapVoForApi mapVoForApi = new MapVoForApi();
        try {
            mapVoForApi = this.mapServiceForApi.retrieveMap(review.getRoomNo());
        } catch (Exception e) {

        }
        List<MapVoForApi> documents = new ArrayList<MapVoForApi>();
        documents.add(mapVoForApi);

        MetaVo meta = new MetaVo();
        meta.setTotal_count(1);

        map.put("documents", documents);
        map.put("meta", meta);

        return map;
    }


    // 게시글 수정폼
    @GetMapping("/member/post/{postNo}/modify")
    public String modifyFrom(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("postNo") int postNo, Model model, HttpServletRequest req, HttpServletResponse res) {

        //비회원 접근할시 익셉션
        checkUser(userAccount, req, res);

        // 작성자 본인이거나 관리자 인지 권한 확인
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        int memberGrade = member.getGrade();

        // 작성된 게시글 작성자 id
        int writerNo = this.postService.retrieveDetailBoard(postNo).getWriterNo();

        // 작성자 본인이 아니고, 관리자도 아닌 경우
        if (memNo != writerNo && memberGrade != 5) {
            //권한 없음 페이지로 이동
            return "redirect:/denine";

        } else { // 작성자 본인인 경우
            // 게시글 정보 가져오기
            PostVo post = this.postService.retrieveDetailBoard(postNo);
            ReviewVo review = this.reviewService.retrieveReview(postNo);
            List<AttachVo> attachVoList = this.attachService.retrievePostAttach(postNo);
            MapVoForApi mapVoForApi = new MapVoForApi();

            // 게시글 셋팅
            String content = post.getContent();
            content = content.replaceAll("\"", "\'");
            post.setContent(content);

            // tag값 배열로 셋팅
            String tag = post.getTag();
            if (tag != null && tag != "") {
                String[] tags = tag.split(",");
                post.setTags(tags);
            }

            try {
                model.addAttribute("mapNo", review.getRoomNo());
                mapVoForApi = this.mapServiceForApi.retrieveMap(review.getRoomNo());
                model.addAttribute("mapVoForApi", mapVoForApi);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mapVoForApi == null) {
                model.addAttribute("mapVoForApi", mapVoForApi);
            }

            if (attachVoList.size() == 0) {
                attachVoList = null;
            }

            //게시글 정보 model셋팅
            model.addAttribute("post", post);
            model.addAttribute("review", review);
            model.addAttribute("attachList", attachVoList);
            return "page/post_modify";
        }

    }

    // 선택한 파일 삭제
    @ResponseBody
    @GetMapping("/member/attach/delete/{attachNo}")
    public Map deleteFile(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("attachNo") int attachNo) {
        //비회원 접근할시 익셉션
        if (userAccount == null) {
            throw new RuntimeException(Constants.ExceptionMsgClass.NOTMEMBER.getExceptionMsgClass());
        }

        // 작성자 본인이거나 관리자 인지 권한 확인
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        int memberGrade = member.getGrade();

        // 작성된 게시글 작성자 id
        AttachVo attach = this.attachService.retrievePostAttachByAtNo(attachNo);
        int writerNo = this.postService.retrieveDetailBoard(attach.getPostNo()).getWriterNo();

        Map<String, String> map = new HashMap<String, String>();
        String success = "fail";

        // 작성자 본인이 아니고, 관리자도 아닌 경우
        if (memNo != writerNo && memberGrade != 5) {
            //권한 없음 페이지로 이동
            success = "fail";

        } else { // 작성자 본인인 경우
            this.attachService.removePostAttach(attachNo);
            success = "success";
        }

        map.put("success", success);
        return map;
    }


    // 게시글 삭제
    @GetMapping("/member/post/{postNo}/delete")
    public String delete(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("postNo") int postNo, HttpServletRequest req, HttpServletResponse res) {

        //비회원 접근할시 익셉션
        checkUser(userAccount, req, res);

        // 작성자 본인이거나 관리자 인지 권한 확인
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        int memberGrade = member.getGrade();

        // 작성된 게시글 작성자 id
        PostVo post = this.postService.retrieveDetailBoard(postNo);
        int boardNo = post.getBoardNo();
        int writerNo = post.getWriterNo();

        // 작성자 본인이 아니고, 관리자도 아닌 경우
        if (memNo != writerNo && memberGrade != 5) {
            //권한 없음 페이지로 이동
            return "redirect:/denine";

        } else { // 작성자 본인인 경우
            // 삭제 쿼리 실행
            this.postService.removePost(postNo, boardNo);

            //회원 게시글 갯수 감소
            this.memberService.reviseBoardCount(memNo, -1);

            return "redirect:/boardList/" + boardNo;
        }
    }

    //비회원 접근할시 익셉션 - 계층형 시큐리티를 사용중이기에 비회원이 write이상의 액션을 취하기 위해선 회원여부를 판단해야한다.
    private void checkUser(UserAccount userAccount, HttpServletRequest req, HttpServletResponse res) {
        if (userAccount == null) {
            try {
                res.sendRedirect("/login?prevPage=" + URLEncoder.encode(req.getRequestURI(), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}