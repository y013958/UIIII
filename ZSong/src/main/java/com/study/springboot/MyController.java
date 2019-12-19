package com.study.springboot;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.dao.ISimpleBbsDao;
import com.study.springboot.dto.JoinDateDto;
import com.study.springboot.dto.ManagerDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.PageInfo;
import com.study.springboot.dto.SimpleBbsDto;

@Controller
public class MyController {

	@Autowired
	ISimpleBbsDao dao;
	
	List<SimpleBbsDto> list;
	List<ManagerDto> info;
	List<MemberDto> member;
	List<ManagerDto> manager;
	ArrayList<JoinDateDto> joindate;
	
//	int listCount = 5;
//	int pageCount = 5;
	
//	@RequestMapping("/")
//	public @ResponseBody String root() throws Exception{
//		System.out.println("루트");
//		return "/login";
//	}
	
	
	@RequestMapping("/")
	public String root() throws Exception{
		System.out.println("루트");

		return "/login";
	}
	
	@RequestMapping("/account_home")
	public String test(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		
		joindate = dao.join_date();
		

		for(int i=0 ; i < joindate.size() ; i++)
		{
			int count = dao.join_count(joindate.get(i).getMember_joindate());
			joindate.get(i).setSum(count);
			joindate.get(i).setTcount(dao.join_tcount(joindate.get(i).getMember_joindate()));
			joindate.get(i).setKcount(dao.join_kcount(joindate.get(i).getMember_joindate()));
			
			System.out.println(dao.join_tcount(joindate.get(i).getMember_joindate()) + "   "+ dao.join_kcount(joindate.get(i).getMember_joindate()));

		}
		
		model.addAttribute("joindate", joindate);
		return "/account/account_home";
	}

	
	@RequestMapping("/list")
	public String userlistPage(Model model) {
//		System.out.println("리스트");
//		model.addAttribute("list", dao.listDao());
		return "/list";
	}
	
	@RequestMapping("/login")
	public String loginPage(Model model) {
		return "/login";
	}
	
		
	  @RequestMapping("/join")
	  public String UserJoin(HttpServletRequest request) {
	   System.out.println("join");
	      
	    String id = request.getParameter("id");
	     String pw = request.getParameter("pw");
	     String name = request.getParameter("name");
	     String nickname = request.getParameter("nickname");
	      
	     System.out.println(id);
	     System.out.println(pw);
	     System.out.println(name);
	     System.out.println(nickname);
	     
	    String result = dao.UserJoin(id, pw, name, nickname);
	          
		return "/join";
	   }
	   
	
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
	
		return "/home";
	}
	
	@RequestMapping("/loginOk")	//로그인확인
	public String loginOk(HttpServletRequest request,HttpServletResponse response,
						  @RequestParam("id") String id,
						  @RequestParam("pw") String pw,
						  Model model) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = null;
		session = request.getSession();
		
		PrintWriter writer = response.getWriter();
		
		info = dao.login(id, pw);
				
		if(info.size() == 1)
		{
			session.setAttribute("id", id);
			session.setAttribute("name", id);
			session.setAttribute("grade", id);
			
			model.addAttribute("id", id);
			model.addAttribute("pw", pw);
			System.out.println(info.get(0).getManager_name());
			System.out.println(info.get(0).getManager_grade());
			model.addAttribute("name", info.get(0).getManager_name());
			model.addAttribute("grade", info.get(0).getManager_grade());
			
			//최종 접속 시간 업데이트
			dao.Manager_Login_Date(id);
			
		}
		else {
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
			writer.println("	alert(\"아이디 비밀번호를 다시 확인해 주세요.\");");
			writer.println("	document.location.href=\"login\";");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
			
//			return "/login";
		}
		
		return "redirect:home";
	}
	
	@RequestMapping("/logoutOk")	//로그아웃
	public void logout(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = null;
		session = request.getSession();
		session.invalidate();
		
		PrintWriter writer = response.getWriter();
		
		writer.println("<html><head></head><body>");
		writer.println("<script language=\"javascript\">");
		writer.println("	alert(\"로그아웃 되었습니다.\");");
		writer.println("	document.location.href=\"login\";");
		writer.println("</script>");
		writer.println("</body></html>");
		writer.close();
	}
	
	
	@RequestMapping("/member")	//멤버관리 페이지
	public String memberlistPage(HttpServletRequest request, Model model) {
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.MemberCount();
		
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
		
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			if(option.equals("아이디"))
				member = dao.FindMemberId(end, start, text);
			else
				member = dao.FindMemberName(end, start, text);
		}
		else
		{
			member = dao.AllMember(end, start);
		}
		
	
		model.addAttribute("member", member);
	
		return "/account/member";
	}
	
	
	@RequestMapping("/manager")		//관리자 페이지
	public String managerlistPage(HttpServletRequest request, Model model) {
//		System.out.println("리스트");

		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		
		int listCount = 5;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.ManagerCount();
		
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
		
		manager = dao.AllManager(end, start);
		
		model.addAttribute("manager", manager);

		return "/account/manager";
	}
	
	
	@RequestMapping("/banMember")	//정지 멤버관리 페이지
	public String stop(HttpServletRequest request, Model model) {
		System.out.println("리스트");
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");		
		
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 7; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.BannMemberCount();
		
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
		
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			if(option.equals("아이디"))
				member = dao.BanFindMemberId(end, start, text);
			else
				member = dao.BanFindMemberName(end, start, text);
		}
		else
		{
			member = dao.BanAllMember(end, start);
		}
		
	
		model.addAttribute("member", member);
	
		return "/account/stop";
	}
	
	
	@RequestMapping("/withdraw")	//탈퇴 멤버 관리
	public String withdraw(HttpServletRequest request, Model model) {
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
		
		System.out.println(nPage);
		System.out.println(option + text);
		
		
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 7; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.WithdrawMemberCount();
		
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
		
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			if(option.equals("아이디"))
				member = dao.BanFindMemberId(end, start, text);
			else
				member = dao.BanFindMemberName(end, start, text);
		}
		else
		{
			member = dao.WithdrawAllMember(end, start);
		}
	
		model.addAttribute("member", member);
	
		return "/account/withdraw";
	}
	
	
	
	@RequestMapping("/stop_content")		// 라이트박스 페이지
	public void stop_contentPage(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String id = request.getParameter("idx");
		
		
		StringBuffer result = new StringBuffer("");
//		result.append("{\"result\":[");
//		result.append("{\"value\": \"" + no + "\"}]");
		result.append("<h4>정지 사유</h4>\r\n" +
				"대상 아이디 : "+ id +"\r\n <br/>" + 
				"		<textarea rows=\"19\" cols=\"52\" style=\"margin-top: 5px;\" id=\"content\"></textarea>\r\n" + 
				"		<input type=\"button\" class=\"btn btn-dark\" style=\"padding: .2rem .45rem; font-size: 0.8rem;\" value=\"처리\" onclick=\"ban_regist('"+id+"')\"/>\r\n" + 
				"		<input type=\"button\" class=\"btn btn-dark\" style=\"padding: .2rem .45rem; font-size: 0.8rem;\" value=\"취소\" onclick=\"closeContent()\"/> ");
		
		
		response.getWriter().write(result.toString());
		
//		return "/account/stop_content";
	}
	
	@RequestMapping("/ban_access")	//정지 기능
	public void ban_access(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		
		StringBuffer result = new StringBuffer("");

		if(content.length() <= 0)
		{
			result.append("0");
		}
		else
		{	
			result.append("1");
			dao.ban(id, content);
		}
		
		response.getWriter().write(result.toString());	
	}
	
	@RequestMapping("/unban")	//정지 해제
	public void unban(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String id = request.getParameter("id");
		
		dao.Unban(id);
		
		StringBuffer result = new StringBuffer("");
		
		result.append("1");		
		response.getWriter().write(result.toString());	
	}
	
	
	@RequestMapping("/grade_change")	// 관리자 권한 변경
	public void grade_change(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String id = request.getParameter("id");
		String grade = request.getParameter("grade");
		
		String arr[] = id.split(" ");
		
		for(int i = 0 ; i < arr.length ; i++) {
			dao.grade_change(arr[i], grade);
		}
		
		StringBuffer result = new StringBuffer("");
		
		
		if(arr.length >= 1) {
			result.append("1");
		}else {
			result.append("0");
		}
		
		response.getWriter().write(result.toString());	
	}
	
	
	@RequestMapping("/withdraw_access")	//정지 기능
	public void withdraw_access(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String id = request.getParameter("id");
		String option = request.getParameter("option");
		
		if(option.equals("1")) {
			dao.Withdraw(id);
		}else {
			dao.UnWithdraw(id);
		}
		
		StringBuffer result = new StringBuffer("");

		result.append("1");
		response.getWriter().write(result.toString());	
	}
	
}
