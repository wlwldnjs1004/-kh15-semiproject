package com.kh.spring09.dto;

import java.text.DecimalFormat;
import java.text.Format;

import lombok.Data;

@Data
public class AttachmentDto {
private int attachmentNo;//스퀸스 번호
private String attachmentName;//파일명
private String attachmentType;//유형
private long attachmentSize;//파일크기

public String getAttachmentSizeString() {
	
	Format fmt = new DecimalFormat("#,##0.00");
	
	if(attachmentSize>=1024L*1024L*1024L) {//GB
		return fmt.format((double)attachmentSize/(1024L*1024L*1024L))+"GB";
	}
	else if(attachmentSize>=1024L*1024L){//MB
		return fmt.format((double)attachmentSize/(1024L*1024L))+"MB";
	}
	else if(attachmentSize>1024L) {//KB
		return fmt.format((double)attachmentSize/1024L)+"kB";
	}
	else {
		return fmt.format(attachmentSize)+"Byte";
	}
}

}
