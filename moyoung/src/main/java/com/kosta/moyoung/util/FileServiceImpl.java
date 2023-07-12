package com.kosta.moyoung.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
	private String dir = "C:/resources/upload/";

	@Override
	public void fileUpload(MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				File dfile = new File(dir + fileName);
				file.transferTo(dfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
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