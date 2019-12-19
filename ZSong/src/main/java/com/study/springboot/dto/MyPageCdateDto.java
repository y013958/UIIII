package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MyPageCdateDto {	

	private int challenge_num;
	private String challenge_type;
	private String challenge_title;
	private String challenge_time;
	private int certificate_check;
	private Timestamp certificate_date;
}
