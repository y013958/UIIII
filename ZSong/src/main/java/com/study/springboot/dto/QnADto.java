package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class QnADto {
	private int question_num;
	private String member_id;
	private String manager_id;
	private String question_title;
	private String question_Content;
	private String question_Picture;
	private String question_answer;
	private String question_divide;
	private String question_state;
	private Timestamp question_date;
	private Timestamp anwser_date;
}
