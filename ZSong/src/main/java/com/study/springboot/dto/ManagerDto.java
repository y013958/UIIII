package com.study.springboot.dto;

import lombok.Data;

@Data
public class ManagerDto {
	private int manager_no;
	private String manager_id;
	private String manager_pw;	
	private String manager_name;
	private String manager_grade;
	private String manager_access;
}
