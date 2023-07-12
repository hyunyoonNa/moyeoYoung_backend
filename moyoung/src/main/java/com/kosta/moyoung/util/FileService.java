package com.kosta.moyoung.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void fileUpload(MultipartFile file);
	
	void fileView(String imgName,OutputStream out);
}
