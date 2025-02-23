package com.kh.spring09.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring09.Service.AttachmentService;
import com.kh.spring09.dao.AttachmentDao;
import com.kh.spring09.dto.AttachmentDto;
import com.kh.spring09.error.TargetNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/attachment")
public class FileDownloadController {

	@Autowired
	private AttachmentDao attachmentDao;
	
	@Autowired
	private AttachmentService attachmentService;

	@RequestMapping("/download")
public ResponseEntity<ByteArrayResource> download(
		  @RequestParam int attachmentNo) throws IOException {
		byte[]data=attachmentService.load(attachmentNo);
		AttachmentDto attachmentDto = attachmentDao.selectOne(attachmentNo);
//				CountryDto countryDto=countryDao.selectOne(countryNo);
//포장(Wrap)
ByteArrayResource resoutce=new ByteArrayResource(data);

	//반환
	return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
			.header(HttpHeaders.CONTENT_TYPE, attachmentDto.getAttachmentType())//알때
			//			.contentType(MediaType.APPLICATION_OCTET_STREAM)//대채 명령 존제
			.contentLength(attachmentDto.getAttachmentSize())
			.header(HttpHeaders.CONTENT_DISPOSITION,
					ContentDisposition.attachment()
					.filename(attachmentDto.getAttachmentName()
							, StandardCharsets.UTF_8)
					.build().toString()
					)
			.body(resoutce);
			
	}
}


