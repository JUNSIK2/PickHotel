package com.example.board.controller;

import com.example.board.model.BoardVo;
import com.example.board.model.MapVoForApi;
import com.example.board.model.PostVo;
import com.example.board.model.ReviewVo;
import com.example.board.service.*;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Transactional
@Controller
public class ModifyFileController {
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

    @RequestMapping(value = "/member/uploadFileForModify", method = RequestMethod.POST)
    public String modifyFiles(HttpServletRequest request, @RequestParam(value = "images", required = false) List<MultipartFile> images,
                              @RequestParam(value = "postNo", required = false) int postNo,
                              @RequestParam(value = "videos", required = false) List<MultipartFile> videos,
                              @RequestParam(value = "bordNo", required = false, defaultValue = "1") Integer boardNo,
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
                              @RequestParam(value = "map_no", required = false, defaultValue = "0") Integer map_no,
                              @RequestParam(value = "rateLoc", required = false, defaultValue = "0") int rateLoc,
                              @RequestParam(value = "rateClean", required = false, defaultValue = "0") int rateClean,
                              @RequestParam(value = "rateComu", required = false, defaultValue = "0") int rateComu,
                              @RequestParam(value = "rateChip", required = false, defaultValue = "0") int rateChip,
                              @RequestParam(value = "visitDate", required = false) String visitDate,
                              @RequestParam(value = "recommendPlace", required = false) String recommendPlace,
                              @RequestParam(value = "notRecommendPerson", required = false) String notRecommendPerson, @AuthenticationPrincipal UserAccount userAccount) throws Exception {
        // 세션가져올 준비
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
            // 게시글
            String newContent = content;
            PostVo postVo = new PostVo();
            postVo.setPostNo(postNo);
            postVo.setSubject(subject);
            postVo.setContent(newContent);
            postVo.setTag(tag);
            this.postService.modifyPost(postVo);

            // 숙소 정보, 리뷰 정보
            BoardVo boardForUseCheck = this.boardService.retrieveBoardType(boardNo);
            if (boardForUseCheck.getType().equals("basic")) {

            } else {
                if (address_name.trim().equals("") || map_no == 0) {

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
                    mapVoForApi.setMap_no(map_no);

                    this.mapServiceForApi.modifyMap(mapVoForApi);

                    ReviewVo review = new ReviewVo();

                    if (visitDate.trim().equals("")) {
                        visitDate = null;
                    }

                    review.setPostNo(postNo);
                    review.setRateLoc(rateLoc);
                    review.setRateClean(rateClean);
                    review.setRateComu(rateComu);
                    review.setRateChip(rateChip);
                    review.setVisitDate(visitDate);
                    review.setRecommendPlace(recommendPlace);
                    review.setNotRecommendPerson(notRecommendPerson);
                    this.reviewService.modifyReview(review);
                }
            }
            // 파일
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
            return "redirect:/board/" + boardNo + "/post/" + postVo.getPostNo();
        }
    }

//    private String convert(String oldStr) {
//        String newStr = oldStr.replace("'", "''");
//        newStr = newStr.replace("<", "&lt;");
//        newStr = newStr.replace(">", "&gt;");
//        newStr = newStr.replace("\n", "<br />");
//        return newStr;
//    }
}
