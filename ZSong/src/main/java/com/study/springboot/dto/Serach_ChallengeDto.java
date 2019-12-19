package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Serach_ChallengeDto {
	private int challenge_num;
	private String challenge_title;
	private String challenge_category;
	private String challenge_type;
	private String challenge_frequency;
	private Timestamp challenge_start;
	private Timestamp challenge_end;
	private int challenge_fee;
	private String challenge_time;
	private String challenge_detail;
	private String challenge_first_image;
	private String challenge_state;
	private String challenge_public;
	private int challenge_exp;
	private Timestamp challenge_date;
	private String challenge_host;
	private String challenge_code;


}

