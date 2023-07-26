package com.kosta.moyoung.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileServiceImpl implements FileService {
    private String dir;

    public FileServiceImpl() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            this.dir = "C:/resources/upload/";
        } else if (OS.contains("mac")) {
        	System.out.println(System.getProperty("user.home"));
            this.dir = "/Users/jeongsehun/Desktop/KOSTA/PROJECT3_FINAL/imgUpload/";
        } else {
            // Linux or other OS. You can add more else if blocks for other specific OS's
            this.dir = "/path/to/your/directory";
        }
    }

    @Override
    public void fileUpload(MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File dfile = new File(dir + fileName);
                // 디렉토리가 없는 경우 디렉토리 생성
                if(!dfile.getParentFile().exists()) {
                    dfile.getParentFile().mkdirs();
                }
                file.transferTo(dfile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("여기맞아요?"+dir);
        }
    }

    @Override 
    public void fileView(String imgName,OutputStream out) { 
        try{
            FileInputStream fis = new FileInputStream(dir + imgName);
            FileCopyUtils.copy(fis, out);
            out.flush();  
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}