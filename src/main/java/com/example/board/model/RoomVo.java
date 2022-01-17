package com.example.board.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomVo {
    private int no;

    private String roomName;
    private String roomDeco;
    private String roomAddress;
    private String systemFileName;
    private String originalFileName;
    private byte[] imageBytes;
    private List<Link> links;

}
