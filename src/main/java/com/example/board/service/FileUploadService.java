package com.example.board.service;

import com.example.board.model.AttachVo;
import com.example.board.persistent.AttachDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

@Service
public class FileUploadService {
    private static final String SAVE_PATH = "/upload";
    private static final String PREFIX_URL = "/upload/";

    @Autowired
    private AttachDao attachDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public String restore(MultipartFile multipartFile, int postNo, int fileType) {
        String url = null;

        try {
            String originalFileName = multipartFile.getOriginalFilename();
            String extName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
            Long size = multipartFile.getSize();

            // 서버에서 저장 할 파일 이름
            String systemFileName = getSaveFileName(extName);

            int fileSize = size.intValue();
            AttachVo attach = new AttachVo(postNo, originalFileName, systemFileName, fileSize, fileType);
            attachDao.insertPostAttach(attach);

            // file저장
            writeFile(multipartFile, systemFileName);
            url = systemFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return url;
    }

    // 현재 시간을 기준으로 파일 이름 생성
    private String getSaveFileName(String extName) {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName;

        return fileName;
    }

    // 파일을 실제로 write 하는 메서드
    private boolean writeFile(MultipartFile multipartFile, String systemFileName) throws IOException {
        boolean result = false;
        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + systemFileName);
        fos.write(data);
        fos.close();

        return result;
    }
}
