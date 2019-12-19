package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.MyPageCdateDto;
import com.study.springboot.dto.MypageMemoDto;

@Mapper	
public interface MyPageDao {
	
	public ArrayList<MemberDto> MyPageMainList(String member_id);
	public ArrayList<MyPageCdateDto> MyPageCertificateDate(int member_num);
	public void MyPageMemoInsert(String memo_title, String memo_content, String member_id);
	public ArrayList<MypageMemoDto> MyPageMemo(String userid);
}