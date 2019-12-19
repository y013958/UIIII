package com.study.springboot.dto;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberDto {
	private int member_no;
	private String member_id;
	private String	member_pw;
	private String	member_name;
	private String	member_nickname;
	private String	member_account;
	private String	member_introduction;
	private String	member_blacklist;
	private Timestamp	member_black_date;
	private String	member_black_reason;
	private Timestamp	member_withdraw;
	private Timestamp	member_joindate;
	private Timestamp	member_last_access;
	private int	member_exp;
	
	private String	member_grade;
	private String	member_profile_image;
	private int	member_public;
	private int	member_capability;
	private int	member_health;
	private int	member_relationship;
	private int	member_assets;
	private int	member_life;
	private int	member_hobby;
	private String	member_token;
}
