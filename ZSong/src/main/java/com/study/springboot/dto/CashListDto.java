package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CashListDto {
	private int list_cash_num;
	private String list_member_id;
	private int list_cash_content;
	private Timestamp list_cash_date;
	private int list_cash_state;
	private int list_cash_check;
}
