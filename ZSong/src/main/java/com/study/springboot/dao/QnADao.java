package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.QnADto;


@Mapper
public interface QnADao {
	
//	public ArrayList<ChallengeDto> All_RegisterWait_challenge(int end, int start);
//	public ChallengeDto challenge_content(int num);
//	public int AllWaitchallengeCount();
	
	
	public ArrayList<QnADto> All_WaitQnA(int end, int start);
	public int WaitQnACount();
	public void QnAsubmit(int num, String manager_id, String content);
	
	
	public ArrayList<QnADto> EndQnA(int end, int start);
	public int EndQnACount();
	
	
	public void AddComment(int question_num, String manager_id, String question_answer);
	public ArrayList<QnADto> SelectQnAComment(int num);
	
}
