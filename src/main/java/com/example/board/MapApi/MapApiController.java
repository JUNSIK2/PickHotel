package com.example.board.MapApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MapApiController {

    @Autowired
    private MapService mapService;

    @GetMapping("/map/search")
    public Map maplist(@RequestParam(value = "query", defaultValue = "서울") String query,
                       @RequestParam(value = "page", defaultValue = "1") int page){
        MapResponse mapResponse = mapService.getInfo(query, page);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mapResponse", mapResponse);
        map.put("page", page);
        return map;
    }



}
