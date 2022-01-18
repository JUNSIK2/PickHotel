package com.example.board.MapApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("mapService")
public class MapService {

    @Autowired
    private RestTemplate restTemplate;

    public MapResponse getInfo(String keyword, int page ){
        System.out.println("keyword : " + keyword);
        MapResponse mapResponse = new MapResponse();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 958f211ca3b696905af81baf6cd47314");
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "json", utf8);
        headers.setContentType(mediaType);

        Map<String, String> params = new HashMap<>();
        params.put("query", keyword);
        params.put("category_group_code", "AD5");

        HttpEntity entity = new HttpEntity(headers);

        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword + "&category_group_code=AD5";


//        위보단 아래의 방식으로 만드는 것이 일반적 하지만 utf-8 처리가 안되어 한글을 인식하지 못함
//        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("query", keyword)
//                .queryParam("category_group_code", "AD5")
//                .build()
//                .encode(StandardCharsets.UTF_8)
//                .toUriString();

        ResponseEntity<MapResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, MapResponse.class, params);
        mapResponse = responseEntity.getBody();

        List<MapVo> documents = mapResponse.getDocuments();
        for(MapVo document : documents){
            System.out.println("주소이름 : " + document.getAddress_name());
            System.out.println("그룹코드 : " +document.getCategory_group_code());
            System.out.println("그룹네임 : " +document.getCategory_group_name());
            System.out.println("카테고리네임 : " +document.getCategory_name());
            System.out.println("아이디 : " +document.getId());
            System.out.println("전화번호 : " +document.getPhone());
            System.out.println("장소이름 : " +document.getPlace_name());
            System.out.println("장소url : " +document.getPlace_url());
            System.out.println("도로명주소 : " +document.getRoad_address_name());
            System.out.println("X좌표 : " + document.getX());
            System.out.println("Y좌표 : " + document.getY());
        }

        MetaVo meta = mapResponse.getMeta();
        System.out.println("페이지끝확인" +meta.getIs_end());
        System.out.println("가능한페이지" +meta.getPageable_count());
        System.out.println("전체페이지" +meta.getTotal_count());

        RegionInfo regionInfo = meta.getSame_name();
        System.out.println("키워드" +regionInfo.getKeyword());
        System.out.println("지역" +regionInfo.getSelected_region());

        return mapResponse;
    }




}

