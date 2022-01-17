package com.example.board.MapApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapResponse {
    List<MapVo> documents;
    MetaVo meta;
}
