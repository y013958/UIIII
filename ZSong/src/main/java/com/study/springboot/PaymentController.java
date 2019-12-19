package com.study.springboot;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.dao.PaymentDao;
import com.study.springboot.dto.CashListDto;
import com.study.springboot.dto.PageInfo;

@Controller
public class PaymentController {

	@Autowired
	PaymentDao dao;
	
	List<CashListDto> cash_mileage_list;

	
	//개인 충전내역은 사용자 계정 관리에 떠야할듯 함
	//마일리지 충전/출금 내역
	//출금 신청 내역 페이지
	//상ytyt금 전환 내역
	
	@RequestMapping("/cash_mileage")
	public String cash_mileage(HttpServletRequest request, Model model){
			
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.cash_mileage_list_count();
				
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = nPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;

		PageInfo pinfo = new PageInfo();
		
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		request.setAttribute("page", pinfo);
		nPage = pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		int start = (nPage-1)* listCount + 1;
		int end = (nPage-1)*listCount + listCount;
		
//		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
//		{
//			if(option.equals("아이디"))
//				challenge = dao.FindMemberId(end, start, text);
//			else
//				challenge = dao.FindMemberName(end, start, text);
//		}
//		else
//		{
			cash_mileage_list = dao.cash_mileage_list(end, start);
//		}
		
	
		model.addAttribute("cash_mileage_list", cash_mileage_list);
		

		
		
		return "/payment/cash_mileage";
	}
	
	
	@RequestMapping("/withdrawal_request")
	public String withdrawal_request(HttpServletRequest request, Model model){
		
		
	HttpSession session = null;
	session = request.getSession();
	
	int nPage = 1;
	
	nPage = Integer.parseInt(request.getParameter("page"));
	String option = request.getParameter("findoption");
	String text = request.getParameter("findtext");
			
	
	int listCount = 7;	//한페이지당 보여줄 게시물 갯수
	int pageCount = 5; //하단에 보여줄 리스트 갯수
	
	int totalCount = dao.withdrawal_request_list_count();
			
	int totalPage = totalCount / listCount;
	if(totalCount % listCount > 0)
		totalPage++;
	
	int myCurPage = nPage;
	if(myCurPage > totalPage)
		myCurPage = totalPage;
	if(myCurPage < 1)
		myCurPage = 1;
	
	int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
	
	int endPage = startPage + pageCount - 1;
	if(endPage > totalPage)
		endPage = totalPage;

	PageInfo pinfo = new PageInfo();
	
	pinfo.setTotalCount(totalCount);
	pinfo.setListCount(listCount);
	pinfo.setTotalPage(totalPage);
	pinfo.setCurPage(myCurPage);
	pinfo.setPageCount(pageCount);
	pinfo.setStartPage(startPage);
	pinfo.setEndPage(endPage);
	
	request.setAttribute("page", pinfo);
	nPage = pinfo.getCurPage();
	session.setAttribute("cpage", nPage);
	
	int start = (nPage-1)* listCount + 1;
	int end = (nPage-1)*listCount + listCount;
	
//	if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
//	{
//		if(option.equals("아이디"))
//			challenge = dao.FindMemberId(end, start, text);
//		else
//			challenge = dao.FindMemberName(end, start, text);
//	}
//	else
//	{
		cash_mileage_list = dao.withdrawal_request_list(end, start);
//	}
	

	model.addAttribute("cash_mileage_list", cash_mileage_list);

		return "/payment/withdrawal_request";
	}

	
	
	
}
