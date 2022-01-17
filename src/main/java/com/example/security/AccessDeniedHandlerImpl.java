package com.example.security;

import com.example.grade.model.SiteGradeVo;
import com.example.grade.service.SiteGradeService;
import com.example.member.model.RoleResourcesVo;
import com.example.member.persistent.RoleResourceDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
    작성자 : 김소진
    작성일 : 2022-01-12
    내용 : 스프링 시큐리티에서 권한 없는 페이지 접근시 켜짐, 오류 핸들러
*/
@Configuration
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Autowired
    RoleResourceDao roleResourceDao;

    @Autowired
    SiteGradeService siteGradeService;

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            throws IOException, ServletException {
        //요청 주소 확인
        String getRequestURI = req.getRequestURI();

        //시큐리티 Role목록 조회
        List<RoleResourcesVo> roleResources = roleResourceDao.getRoleResources();
        String roleName = "";
        for (RoleResourcesVo roleResource : roleResources) {
            //Role에 저장된 주소 가져옴
            String resourceName = roleResource.getResourceName();
            //주소를 앞부터 /**까지 잘라냄, 예) /intranet/** -> /intranet
            String resourceNameCut = resourceName.substring(0, resourceName.lastIndexOf("/"));
            //현재 주소가 Role에 저장된 주소와 같은 row의 Role이름을 가져옴
            if (getRequestURI.matches("^" + resourceNameCut + ".*")) {
                roleName = roleResource.getRoleName();
            }
        }

        //관리자만 접근 가능한 페이지인 경우
        if (roleName.equals("ROLE_ADMIN")) {
            req.setAttribute("msg", "관리자 접근권한 없는 사용자입니다.");
            //권한 없음 페이지로 이동
            req.getRequestDispatcher("/denine").forward(req, res);
        } else {//등급이 부족하여 접근하지 못하는 페이지인 경우(게시판)
            System.out.println("응?");
            //Role이름에서 숫자만 추출해서 해당 게시판에 접근 가능한 등급을 가져옴, 예) ROLE_GRADE4 -> 4
            int boardGrade = Integer.parseInt(roleName.replaceAll("[^0-9]", ""));

            //사이트 등급 설정에서 4등급에 지정된 등급명을 조회해온다.
            SiteGradeVo siteGrade = siteGradeService.retriveSiteGrade(boardGrade);
            req.setAttribute("boardGradeName", siteGrade.getMemGradeName());

            req.setAttribute("msg", "게시판 접근 권한이 없습니다.");

            //게시판 접근 등급 페이지로 이동
            req.getRequestDispatcher("/member/site_grade").forward(req, res);
        }
    }
}

