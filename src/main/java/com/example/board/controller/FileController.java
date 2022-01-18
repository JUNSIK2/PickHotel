package com.example.board.controller;

import com.example.board.model.BoardVo;
import com.example.board.model.MapVoForApi;
import com.example.board.model.PostVo;
import com.example.board.model.ReviewVo;
import com.example.board.service.*;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Transactional
@Controller
public class FileController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private PostService postService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private MapServiceForApi mapServiceForApi;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/uploadFile", method = RequestMethod.POST)
    public String registerFiles(HttpServletRequest request, @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                @RequestParam(value = "videos", required = false) List<MultipartFile> videos,
                                @RequestParam(value = "bordNo", required = false, defaultValue = "1") int boardNo,
                                @RequestParam(value = "subject", required = false) String subject,
                                @RequestParam(value = "content", required = false) String content,
                                @RequestParam(value = "tag", required = false) String tag,
                                @RequestParam(value = "address_name", required = false) String address_name,
                                @RequestParam(value = "category_group_code", required = false, defaultValue = "noValue") String category_group_code,
                                @RequestParam(value = "category_group_name", required = false, defaultValue = "noValue") String category_group_name,
                                @RequestParam(value = "category_name", required = false, defaultValue = "noValue") String category_name,
                                @RequestParam(value = "distance", required = false, defaultValue = "noValue") String distance,
                                @RequestParam(value = "id", required = false, defaultValue = "noValue") String id,
                                @RequestParam(value = "phone", required = false, defaultValue = "noValue") String phone,
                                @RequestParam(value = "place_name", required = false, defaultValue = "noValue") String place_name,
                                @RequestParam(value = "place_url", required = false, defaultValue = "noValue") String place_url,
                                @RequestParam(value = "road_address_name", required = false, defaultValue = "noValue") String road_address_name,
                                @RequestParam(value = "x", required = false, defaultValue = "noValue") String x,
                                @RequestParam(value = "y", required = false, defaultValue = "noValue") String y,
                                @RequestParam(value = "rateLoc", required = false, defaultValue = "0") int rateLoc,
                                @RequestParam(value = "rateClean", required = false, defaultValue = "0") int rateClean,
                                @RequestParam(value = "rateComu", required = false, defaultValue = "0") int rateComu,
                                @RequestParam(value = "rateChip", required = false, defaultValue = "0") int rateChip,
                                @RequestParam(value = "visitDate", required = false) String visitDate,
                                @RequestParam(value = "recommendPlace", required = false) String recommendPlace,
                                @RequestParam(value = "notRecommendPerson", required = false) String notRecommendPerson, @AuthenticationPrincipal UserAccount userAccount) throws Exception {
        HttpSession session = request.getSession();

        // 세션가져올 준비
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        // 게시글
        String newContent = content;
        PostVo postVo = new PostVo();
        postVo.setWriterNo(memNo);
        postVo.setBoardNo(boardNo);
        postVo.setSubject(subject);
        postVo.setContent(newContent);
        postVo.setTag(tag);

        int postNo = postService.registerPost(postVo);
        session.setAttribute("boardNo", boardNo);

        BoardVo board = this.boardService.retrieveBoard(boardNo);
        if (board.getType().equals("basic")) {

        } else {
            // 숙소 정보, 리뷰 정보
            if (address_name.trim().equals("")) {

            } else {
                MapVoForApi mapVoForApi = new MapVoForApi();
                mapVoForApi.setAddress_name(address_name);
                mapVoForApi.setCategory_group_code(category_group_code);
                mapVoForApi.setCategory_group_name(category_group_name);
                mapVoForApi.setCategory_name(category_name);
                mapVoForApi.setDistance(distance);
                mapVoForApi.setId(id);
                mapVoForApi.setPhone(phone);
                mapVoForApi.setPlace_name(place_name);
                mapVoForApi.setPlace_url(place_url);
                mapVoForApi.setRoad_address_name(road_address_name);
                mapVoForApi.setX(x);
                mapVoForApi.setY(y);

                String registerMapUri = this.mapServiceForApi.registerMap(mapVoForApi);
                String strMapNo = registerMapUri.substring(registerMapUri.lastIndexOf("/") + 1);
                int mapNo = Integer.parseInt(strMapNo);

                ReviewVo review = new ReviewVo();

                if (visitDate.trim().equals("")) {
                    visitDate = null;
                }

                review.setPostNo(postNo);
                review.setRoomNo(mapNo);
                review.setRateLoc(rateLoc);
                review.setRateClean(rateClean);
                review.setRateComu(rateComu);
                review.setRateChip(rateChip);
                review.setVisitDate(visitDate);
                review.setRecommendPlace(recommendPlace);
                review.setNotRecommendPerson(notRecommendPerson);
                this.reviewService.registerReview(review);
            }
        }


        if (board.getUsePhoto() == 1) {
            // 이미지
            if (images != null) {
                for (MultipartFile file : images) {
                    String fileName = null;
                    if (!file.getOriginalFilename().isEmpty()) {
                        fileName = fileUploadService.restore(file, postNo, 1);
                    } else {
                        fileName = "default.jpg";
                    }
                }
            }
        }


        if (board.getUseVideo() == 1) {
            // 동영상
            if (videos != null) {
                for (MultipartFile file : videos) {
                    String fileName = null;
                    if (!file.getOriginalFilename().isEmpty()) {
                        fileName = fileUploadService.restore(file, postNo, 2);
                    } else {
                        fileName = "default.mp4";
                    }

                }
            }
        }
        this.boardService.reviseBoardPost(boardNo, 1);

        //회원 게시글 갯수 증가
        this.memberService.reviseBoardCount(memNo, 1);

        return "redirect:/board/" + boardNo + "/post/" + postVo.getPostNo();
    }

//    private String convert(String oldStr) {
//        String newStr = oldStr.replace("'", "''");
//        newStr = newStr.replace("<", "&lt;");
//        newStr = newStr.replace(">", "&gt;");
//        newStr = newStr.replace("\n", "<br />");
//        return newStr;
//    }
}

















