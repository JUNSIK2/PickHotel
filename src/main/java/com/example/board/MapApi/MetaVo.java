package com.example.board.MapApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaVo {
    Integer total_count;
    Integer pageable_count;
    Boolean is_end;
    RegionInfo same_name;
}
