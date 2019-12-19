package com.study.springboot.yejin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.MyPageDao;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.MyPageCdateDto;
import com.study.springboot.dto.MypageMemoDto;

@Controller
@RequestMapping("/yejin")
public class MyPageController {
	
	@Autowired
	MyPageDao dao;
	
	ArrayList<MemberDto> mypagelist;
	ArrayList<MyPageCdateDto> challengelist;
	ArrayList<MypageMemoDto> memolist;
		
	@RequestMapping("/mypage_main_list")
	public String Mypage_Main_List(Model model, @RequestParam("member_id") String member_id) {
		
		mypagelist = dao.MyPageMainList(member_id);

		model.addAttribute("list", mypagelist);

		return "/mypage/member";
	}
	
	@RequestMapping("/mypage_challege_date")
	public String MyPageCertificateDate(Model model, @RequestParam("member_num") int member_num) {
		
		challengelist = dao.MyPageCertificateDate(member_num);
		
		model.addAttribute("list", challengelist);
		
		return "/mypage/certificate";
	}
	
	@RequestMapping("/mypage_memo_select")
	public String MyPageMemoList(Model model,
			@RequestParam("member_id") String member_id) {
		
		memolist = dao.MyPageMemo(member_id);
		
		model.addAttribute("list", memolist);
		
		return "/mypage/memo";
	}
	
	@RequestMapping("/mypage_memo_insert")
	public void MyPageMemoInsert(Model model, 
			@RequestParam("member_id") String member_id,
			@RequestParam("memo_title") String memo_title,
			@RequestParam("memo_content") String memo_content) {
		
		dao.MyPageMemoInsert(memo_title, memo_content, member_id );
				
	}
}
