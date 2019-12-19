package com.study.springboot.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CommentDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.RecordDto;

@Mapper
public interface Web_ChallengeDao {
	
	public ArrayList<ChallengeDto> All_RegisterWait_challenge(int end, int start);
	public ChallengeDto challenge_content(int num);
	public ChallengeDto challenge_vote_content(int num);
	public int AllWaitchallengeCount();
	
	public int challenge_vote_count(int num);
	
	
	public ArrayList<ChallengeDto> FindRegisterWait_challenge_title(int end, int start, String text);
	public ArrayList<ChallengeDto> FindRegisterWait_challenge_name(int end, int start, String text);
	public ArrayList<ChallengeDto> FindRegisterWait_challenge_public(int end, int start, String text);
	
	public void ChallengeApproval(int num, String name, String title, String type, String frequency,
			Timestamp start, Timestamp end,	int fee, String time, String detail, int exp);
	public void PrivateChallengeApproval(int num, String code);
	
	public ArrayList<CommentDto> challenge_content_vote_comment(int num);

	public ArrayList<ChallengeDto> All_Ongoing_challenge(int end, int start);
	public int AllOngoingchallengeCount();
	
	public ArrayList<ChallengeDto> FindOngoing_challenge_title(int end, int start, String text);
	public ArrayList<ChallengeDto> FindOngoing_challenge_name(int end, int start, String text);
	public ArrayList<ChallengeDto> FindOngoing_challenge_public(int end, int start, String text);
	
	public void approve_public_challenge(int challenge_num);
	public ArrayList<ChallengeDto> public_challenge(int end, int start);
	public int public_challenge_count();
	public ChallengeDto public_challenge_content(int num);
	
	public ArrayList<ChallengeDto> reward_challenge(int end, int start);
	public int reward_challenge_count();
	
	
	public ChallengeDto ongoing_challenge_content(int num);
	
	public ArrayList<MemberDto> read_participant(int num);
	
	public void save_token(String id, String token);
	public String get_token(String id);
	
	public ArrayList<RecordDto> GetRecord(int challenge_num);
	
}
