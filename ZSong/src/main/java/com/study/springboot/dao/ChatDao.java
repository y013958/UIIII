package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ChatDTO;
import com.study.springboot.dto.ManagerDto;


@Mapper
public interface ChatDao {
	
	public ArrayList<ChatDTO> getBox(String userId);
	public int getUnreadChat(String fromID, String toID);
	
	public int getAllUnreadChat(String userID);

	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number);
	public ArrayList<ChatDTO> getChatListById(String fromID, String toID, String chatID);
	
	public void readChat(String fromID, String toID);
	
	public int submit(String fromID, String toID, String chatContent);
	
	public int findID(String id);

	public ArrayList<ManagerDto> manager_list(String id);
	
//	public ArrayList<QnADto> All_WaitQnA(int end, int start);
//	public int WaitQnACount();
//	public void QnAsubmit(int num, String manager_id, String content);
//	
//	
//	public ArrayList<QnADto> EndQnA(int end, int start);
//	public int EndQnACount();
//	
//	
//	public void AddComment(int question_num, String manager_id, String question_answer);
//	public ArrayList<QnADto> SelectQnAComment(int num);
	
}
