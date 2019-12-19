package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ChatDTO {
	int chatID;
	String fromID;
	String toID;
	String chatContent;
	Timestamp chatTime;
	int chatread;
}
