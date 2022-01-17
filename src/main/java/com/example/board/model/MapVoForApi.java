package com.example.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVoForApi {
    int map_no;
    String address_name;
    String category_group_code;
    String category_group_name;
    String category_name;
    String distance;
    String id;
    String phone;
    String place_name;
    String place_url;
    String road_address_name;
    String x;
    String y;
    List<Link> links;
}
