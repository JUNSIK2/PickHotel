package com.example.board.service;

import com.example.board.model.Link;
import com.example.board.model.MapResponseForApi;
import com.example.board.model.MapVoForApi;
import com.example.config.RestTemplateResponseErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("mapServiceForApi")
public class MapServiceForApi {
    private final String URI_MAPS = "http://54.180.153.61:80/api/maps";
    private final String URI_MAPS_MAPNO = "http://54.180.153.61:80/api/maps/{map_no}";
    private final String URI_MAP = "http://54.180.153.61:80/api/map";

    private final RestTemplate restTemplate;

    public MapServiceForApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

    // 숙소 생성
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public String registerMap(MapVoForApi mapVoForApi) throws Exception {
        URI uri = restTemplate.postForLocation(URI_MAP, mapVoForApi);

        return uri.toString();
    }

    // 숙소 1개 조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public MapVoForApi retrieveMap(int map_no) throws Exception {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("map_no", map_no);

        ResponseEntity<MapVoForApi> responseEntity = restTemplate.getForEntity(URI_MAPS_MAPNO, MapVoForApi.class, params);
        MapVoForApi mapVoForApi = new MapVoForApi();

        int statusCode = responseEntity.getStatusCodeValue();
        if (statusCode == 200) {
            mapVoForApi = responseEntity.getBody();
        }
        return mapVoForApi;
    }

    // 숙소 모두 조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public MapResponseForApi retrieveAllMaps() throws Exception {
        ResponseEntity<MapResponseForApi> responseEntity = restTemplate.getForEntity(URI_MAPS, MapResponseForApi.class);
        MapResponseForApi mapResponseForApi = responseEntity.getBody();

        List<Link> links = mapResponseForApi.getLinks();

        return mapResponseForApi;
    }

    //숙소 업데이트
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void modifyMap(MapVoForApi mapVoForApi) throws Exception {
        restTemplate.put(URI_MAP, mapVoForApi, MapVoForApi.class);
    }

    // 숙소 삭제
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void removeMap(int map_no) throws Exception {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("map_no", map_no);

        restTemplate.delete(URI_MAPS_MAPNO, params);
    }


}
