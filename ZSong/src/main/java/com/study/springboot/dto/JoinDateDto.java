package com.study.springboot.dto;


import java.sql.Timestamp;

import lombok.Data;

@Data
public class JoinDateDto {
	private Timestamp member_joindate;
	private int count;
	private int sum;
	private int tcount;
	private int kcount;
}
