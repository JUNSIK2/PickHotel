package com.example.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapResponseForApi {
    List<Link> links;
    List<MapVoForApi> content;
}
