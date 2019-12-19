package com.study.springboot.yejin;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/yejin")
public class FileUploadController {
	String filename = "";
	String fileUrl = "";
	String uploadpath = "D:\\finalProject\\file\\Image";

	@RequestMapping("/private_upload")
	public void upload(@RequestParam("uploadFile") MultipartFile uploadfile) {
		System.out.println("호출됨");
		if (!uploadfile.isEmpty()) {
			System.out.println("파일 있긴 하니");
			System.out.println("data size : " + uploadfile.getSize());

			// 첨부 파일 이름만 출력해주기
			filename = uploadfile.getOriginalFilename();

			String[] fileArray = filename.split("/");
			System.out.println(fileArray[fileArray.length - 1]);

			filename = fileArray[fileArray.length - 1];

			System.out.println("이름 이름  " + filename);

			try {
				// 원하는 디렉토리로 저장
				fileUrl = uploadpath + "\\" + filename;
				uploadfile.transferTo(new File(fileUrl));
				System.out.println(uploadpath + "\\" + filename);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}
}
