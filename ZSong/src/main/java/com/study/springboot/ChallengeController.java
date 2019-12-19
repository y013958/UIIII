package com.study.springboot;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.ReverbType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.Web_ChallengeDao;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.PageInfo;
import com.study.springboot.dto.RecordDto;

@Controller
public class ChallengeController {

	@Autowired
	Web_ChallengeDao dao;
	
	List<ChallengeDto> challenge;
	List<MemberDto> member;
	List<RecordDto> Record;
	
	@RequestMapping("/register_challenge")
	public String register_challenge(HttpServletRequest request, Model model){
		
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.AllWaitchallengeCount();
				
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

		
		if( option == null ) {
			challenge = dao.All_RegisterWait_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.All_RegisterWait_challenge(end, start);
		}else {
			if(option.equals("카테고리")) {
				challenge = dao.FindRegisterWait_challenge_title(end, start, text);
			}else if(option.equals("제목")) {
				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
			}else if(option.equals("날짜")) {
				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
				//수정
			}else if(option.equals("구분")) {
				option = "challenge_public";
				if(text.equals("공개"))
					text = "0";
				else
					text = "1";
				
				challenge = dao.FindRegisterWait_challenge_public(end, start, text);
			}
		}
		
		model.addAttribute("challenge", challenge);

		return "/challenge/register_challenge";
	}
	
	
	@RequestMapping("/vote_public_challenge")
	public String vote_public_challenge(HttpServletRequest request, Model model){
		
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");

		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.public_challenge_count();
		 
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

		
		if( option == null ) {
			challenge = dao.public_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.public_challenge(end, start);
		}else {
			if(option.equals("카테고리")) {
//				challenge = dao.FindRegisterWait_challenge_title(end, start, text);
			}else if(option.equals("제목")) {
//				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
			}else if(option.equals("날짜")) {
//				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
				//수정
			}else if(option.equals("구분")) {
				option = "challenge_public";
				if(text.equals("공개"))
					text = "0";
				else
					text = "1";
				
//				challenge = dao.FindRegisterWait_challenge_public(end, start, text);
			}
		}
		
		
		for(int i=0 ; i < challenge.size() ; i++) {
			challenge.get(i).setCount(dao.challenge_vote_count(challenge.get(i).getChallenge_num()));
		}
		
		model.addAttribute("challenge", challenge);

		return "/challenge/vote_public_challenge";
	}
	
	
	@RequestMapping("/end_challenge")
	public String end_challenge(HttpServletRequest request, Model model){
		
		return "/challenge/end_challenge";
	}
	
	
	@RequestMapping("/challenge_content")
	public String challenge_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		ChallengeDto dto = dao.challenge_content(num);
		model.addAttribute("challenge", dto);
		
		if(dto.getChallenge_public().equals("0"))
		{
			model.addAttribute("vote_comment", dao.challenge_content_vote_comment(num));
		}else if(dto.getChallenge_public().equals("1")) {
			
			return "/challenge/challenge_private_content";
		}
		return "/challenge/challenge_content";
	}
	
	
	@RequestMapping("/challenge_vote_content")
	public String challenge_vote_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		ChallengeDto dto = dao.public_challenge_content(num);
		model.addAttribute("challenge", dto);
		model.addAttribute("vote_comment", dao.challenge_content_vote_comment(num));

		return "/challenge/challenge_vote_content";
	}
	
	
	
	@RequestMapping("/approval_public_challenge")
	public void approval_public_challenge(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		dao.approve_public_challenge(num);
		
		StringBuffer result = new StringBuffer("");
		result.append("1");
		response.getWriter().write(result.toString());
	}
	
	

	
	@RequestMapping("/challenge_content_popup")
	public String challenge_content_popup(HttpServletRequest request, Model model){
			
		int num = Integer.parseInt(request.getParameter("num"));
		
		model.addAttribute("challenge", dao.challenge_vote_content(num));
		
		return "/challenge/challenge_content_popup";
	}
	
	
	@RequestMapping("/approval_content")		// 라이트박스 페이지
	public void approval_content(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		String type = request.getParameter("check");
		String title = request.getParameter("title");
		String frequency = request.getParameter("frequency");
		String date = request.getParameter("date");
		String arrDate[] = date.split(" - ");
		int fee = Integer.parseInt(request.getParameter("money"));
		fee = fee*10000;
		String time = request.getParameter("time");
		String detail = request.getParameter("detail");
		String expStr = request.getParameter("exp");
		int exp = Integer.parseInt(expStr.replace("exp", ""));
		
		
		arrDate[0]=arrDate[0].replace("/", "-");
		arrDate[1]=arrDate[1].replace("/", "-");
		
		arrDate[0]=arrDate[0]+" 00:00:00.0";
		arrDate[1]=arrDate[1]+" 00:00:00.0";
		
		
		Timestamp start=null;
		Timestamp end=null;	
		
        start = java.sql.Timestamp.valueOf(arrDate[0]);
        end = java.sql.Timestamp.valueOf(arrDate[1]);
		
	
	    System.out.println(num+" "+ name+" "+ title+" "+ type+" "+ frequency+" "+ start+" "+ end+" "+ fee+" "+ time+" "+ detail +" "+ exp);
	    
	    dao.ChallengeApproval(num, name, title, type, frequency, start, end, fee, time, detail, exp);
	    
		StringBuffer result = new StringBuffer("");

		result.append("1");
		
		response.getWriter().write(result.toString());
		
//		return "/account/stop_content";
	}
	
	@RequestMapping("/approval_private_content")		// 라이트박스 페이지
	public void approval_private_content(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		Timestamp start = null;
		Timestamp end = null;
		
	        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};

	        StringBuffer sb = new StringBuffer();
	        Random rn = new Random();

	        for( int i = 0 ; i < 10 ; i++ ){
	            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
	        }

	        String code = "try-"+sb.toString();
		
		dao.PrivateChallengeApproval(num, code);
	    
		StringBuffer result = new StringBuffer("");
		result.append("1");
		
		response.getWriter().write(result.toString());
	}
	
	
	@RequestMapping("/ongoing_challenge")
	public String ongoing_challenge(HttpServletRequest request, Model model){
			
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.AllOngoingchallengeCount();
				
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
		
		if( option == null ) {
			challenge = dao.All_Ongoing_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.All_Ongoing_challenge(end, start);
		}else {
			if(option.equals("카테고리")) {
				challenge = dao.FindOngoing_challenge_title(end, start, text);
			}else if(option.equals("제목")) {
				challenge = dao.FindOngoing_challenge_name(end, start, text);
			}else if(option.equals("날짜")) {
				challenge = dao.FindOngoing_challenge_name(end, start, text);
				//수정
			}else if(option.equals("구분")) {
				option = "challenge_public";
				if(text.equals("공개"))
					text = "0";
				else
					text = "1";
				
				challenge = dao.FindOngoing_challenge_public(end, start, text);
			}
		}
		
		
		model.addAttribute("challenge", challenge);
		
		return "/challenge/ongoing_challenge";
	}
	
	
	
	@RequestMapping("/reward_challenge")
	public String reward_challenge(HttpServletRequest request, Model model){
			
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.reward_challenge_count();
				
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
		
		if( option == null ) {
			challenge = dao.reward_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.reward_challenge(end, start);
		}else {
//			if(option.equals("카테고리")) {
//				challenge = dao.FindOngoing_challenge_title(end, start, text);
//			}else if(option.equals("제목")) {
//				challenge = dao.FindOngoing_challenge_name(end, start, text);
//			}else if(option.equals("날짜")) {
//				challenge = dao.FindOngoing_challenge_name(end, start, text);
//				//수정
//			}else if(option.equals("구분")) {
//				option = "challenge_public";
//				if(text.equals("공개"))
//					text = "0";
//				else
//					text = "1";
//				
//				challenge = dao.FindOngoing_challenge_public(end, start, text);
//			}
		}
		
		
		model.addAttribute("challenge", challenge);
		
		return "/challenge/reward_challenge";
	}
	
	
	
	
	@RequestMapping("/ongoing_challenge_content")
	public String ongoing_challenge_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		ChallengeDto dto = dao.ongoing_challenge_content(num);
		model.addAttribute("challenge", dto);
		
		
		return "/challenge/ongoing_challenge_content";
	}
	
	
	@RequestMapping("/read_participant")
	public void read_participant(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer result = new StringBuffer("");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		System.out.println(num);
		member = dao.read_participant(num);

		result.append("{\"result\":[");
		
		for(int i = 0 ; i < member.size() ; i++) {
//			result.append("[{\"value\": \"" + member.get(i).getMember_nickname() + "\"},");
//			result.append("{\"value\": \"" + member.get(i).getMember_profile_image() + "\"}]");
			result.append("[{\"value\": \"" + member.get(i).getMember_nickname() + "\"},");
			result.append("{\"value\": \"" + member.get(i).getMember_profile_image() + "\"}]");
			if(i != member.size() - 1) 
				result.append(",");
		}
//		result.append("], \"last\":\"" + "\"}");
//		result.append("" + "\"}");
		result.append("]}");
		
		response.getWriter().write(result.toString());
		
		System.out.println(result);
		System.out.println("결과 확인");
	}
	

	@RequestMapping("/read_imagelist")
	public void read_imagelist(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer result = new StringBuffer("");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		System.out.println(num);
//		member = dao.read_participant(num);
		
//		response.getWriter().write(result.toString());

		result.append("{\"result\":[");
		
//		for(int i = 0 ; i < member.size() ; i++) {
			result.append("[{\"value\": \"" + "치민" + "\"},");
			result.append("{\"value\": \"" + "ㅇㅇㅇㅇㅇㅇㅇㅇ" + "\"},");
			result.append("{\"value\": \"" + "/img/aaa.jpg" + "\"},");
			result.append("{\"value\": \"" + "12:00" + "\"}]");
//			if(i != member.size() - 1) 
				result.append(",");
				result.append("[{\"value\": \"" + "송" + "\"},");
				result.append("{\"value\": \"" + "ㅅㅅㅅㅅㅅㅅㅅㅅ" + "\"},");
				result.append("{\"value\": \"" + "/img/aaa.jpg" + "\"},");
				result.append("{\"value\": \"" + "15:00" + "\"}]");
//		}
		result.append("]}");
		
		response.getWriter().write(result.toString());

	}
	
	
	
	@RequestMapping(value = "/fcmtest")
	public void fcmtest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String id = request.getParameter("id");
//		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
	    String tokenId = dao.get_token(id);
	    String title="FCM 테스트";
//	    String content="호예... 된당  잔다..";
	  
	    FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM(tokenId, title, content, "");
	  
//	    return "test";
	}
	
	@RequestMapping(value = "/fcmall")
	public void fcmall(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
//		String title = request.getParameter("title");
		String content = request.getParameter("content");		
		
//	    String tokenId = dao.get_token(id);
	    String title="FCM 테스트";
//	    String content="호예... 된당  잔다..";
	  
	    FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM("", title, content, "all");
	  
//	    return "test";
	}
	
	@RequestMapping(value = "/fcmChallenge")
	public void fcmChallenge(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
//		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("no");
		
	    String title="FCM 테스트";
	  
	    FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM("", title, content, no);
	  
//	    return "test";
	}
	
	
	@RequestMapping("/token_save")
	public void token_save(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") String id, @RequestParam("token") String token) throws Exception {
	
		System.out.println("들어옴");
		dao.save_token(id, token);
	}
	
	
	@RequestMapping("/reward_challenge_content")
	public String reward_challenge_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		ChallengeDto dto = dao.ongoing_challenge_content(num);
		
		Record = dao.GetRecord(num);
		

		
		model.addAttribute("challenge", dto);
		model.addAttribute("Record", Record);
		return "/challenge/reward_challenge_content";
	}
	
	
	@RequestMapping("/ok")
	public void ok(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		ChallengeDto dto = dao.ongoing_challenge_content(num);
		
		Record = dao.GetRecord(num);
		
		int total = Record.size() * Record.get(0).getChallenge_fee();
		int low = 0;
		int count = 0;
		
		for(int i = 0 ; i < Record.size() ; i++) {
			
			System.out.println(Record.get(i).getCertificate_count() + " " + Record.get(i).getAll_count());
			double per = (double)Record.get(i).getCertificate_count() / (double)Record.get(i).getAll_count() ;
			double perint = ((Math.round(per*1000)/1000.0));
			
			System.out.println("퍼센트 : " + perint);
			
			int money = (int) Math.round(perint*Record.get(i).getChallenge_fee());
			System.out.println(money);
			low += money;
		
			if(perint==1)
				count++;
		}
		
		int result = total - low;
		System.out.println("먹은돈 : " + result);
		
		
		System.out.println("달성자 수 : "+count);
		
		int reward = (int) ((result*0.8) / count);
		System.out.println("1인당 상금 : "+reward);
		System.out.println("때먹은 수수료 : " + (result-reward*count));
		
		//recode에 요금 넣기		
		//recode에 상금 넣기

	}
	
}
