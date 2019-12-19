package com.study.springboot.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.JoinDateDto;
import com.study.springboot.dto.ManagerDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.SimpleBbsDto;

@Mapper
public interface ISimpleBbsDao {

	public List<SimpleBbsDto> listDao(String id);
	public String UserJoin(String id, String pw, String name, String nicname);
	public int Manager_Login_Date(String id);
	public List<ManagerDto> login(String id, String pw);
	
	public ArrayList<MemberDto> AllMember(int end, int start);
	public ArrayList<MemberDto> FindMemberId(int end, int start, String text);
	public ArrayList<MemberDto> FindMemberName(int end, int start, String text);
	
	public ArrayList<MemberDto> BanAllMember(int end, int start);
	public ArrayList<MemberDto> BanFindMemberId(int end, int start, String text);
	//기간으로 바꿔
	public ArrayList<MemberDto> BanFindMemberName(int end, int start, String text);

	public ArrayList<MemberDto> WithdrawAllMember(int end, int start);
	public ArrayList<MemberDto> WithdrawFindMemberId(int end, int start, String text);
	public ArrayList<MemberDto> WithdrawFindMemberName(int end, int start, String text);	
	
	
	public int MemberCount();
	public int BannMemberCount();
	public int WithdrawMemberCount();
	
	public ArrayList<ManagerDto> AllManager(int end, int start);
	public int ManagerCount();
	
	
	public void ban(String id, String content);
	public void Unban(String id);
	
	public void Withdraw(String id);
	public void UnWithdraw(String id);
	
	
	public void grade_change(String id, String grade);
	
	public ArrayList<JoinDateDto> join_date();
	public int join_count(Timestamp date);
	public int join_tcount(Timestamp date);
	public int join_kcount(Timestamp date);
	
}
