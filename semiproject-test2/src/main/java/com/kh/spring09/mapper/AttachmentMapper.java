package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.AttachmentDto;


@Component
public class AttachmentMapper implements RowMapper<AttachmentDto>{

	@Override
	public AttachmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttachmentDto attachmentDto=new AttachmentDto();
		attachmentDto.setAttachmentNo(rs.getInt("attachment_no"));
		attachmentDto.setAttachmentName(rs.getString("attachment_name"));
		attachmentDto.setAttachmentType(rs.getString("attachment_type"));
		attachmentDto.setAttachmentSize(rs.getLong("attachment_size"));
		return attachmentDto;
	}

}
