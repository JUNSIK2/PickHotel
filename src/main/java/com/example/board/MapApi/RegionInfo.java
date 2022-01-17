package com.example.board.MapApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionInfo {
    String[] region;
    String keyword;
    String selected_region;
}
