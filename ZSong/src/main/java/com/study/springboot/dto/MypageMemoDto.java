package com.study.springboot.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class MypageMemoDto {

	private int memo_no;
	private String memo_title;
	private String memo_content;
	private String memo_userid;
	private Timestamp memo_date;
}
