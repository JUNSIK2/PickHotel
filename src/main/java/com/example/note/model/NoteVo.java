package com.example.note.model;

import com.example.common.paging.CommonVo;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoteVo extends CommonVo {
    private int noteNo;
    private String noteCon;
    private int noteGetMbNo;
    private int noteSendMbNo;
    private int noteGetMbDelState;
    private int noteGetMbSaveState;
    private int noteGetMbReadState;
    private int noteSendMbDelState;
    private int noteSendMbSaveState;
    private String noteDateTime;
    private int noteIdentifySendGetCode;
    private String noteGetMbNick;
    private String noteSendMbNick;
}