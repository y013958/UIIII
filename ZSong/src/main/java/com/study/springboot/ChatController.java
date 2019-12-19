package com.study.springboot;




import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.ChatDao;
import com.study.springboot.dto.ChatDTO;
import com.study.springboot.dto.ManagerDto;



@Controller
public class ChatController {
	
	@Autowired
	ChatDao dao;
	
	ArrayList<ChatDTO> chatlist;
	ArrayList<ManagerDto> managerlist;
	
	@RequestMapping("/box")
	public String box(Model model) {
		System.out.println("박스");
		return "/box";
	}
	
	@RequestMapping("/box_list")
	public String box_list(Model model) {
		System.out.println("박스");
		return "/box_list";
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		
		System.out.println("박스");
		return "/index";
	}
	
	
	
	@RequestMapping("/chatBox")
	public void chatBox(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("chatBox");
		
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");

		if(userID == null || userID.equals("") )
		{
			response.getWriter().write("0");
		}else {
			try {
				userID = URLDecoder.decode(userID, "UTF-8");
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
			}

		}
	}
	
	
	public String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		chatlist = dao.getBox(userID);
		
		
		for(int i = 0 ; i < chatlist.size(); i++ ) {
			ChatDTO x = chatlist.get(i);
			for(int j = 0 ; j < chatlist.size(); j++) {
				ChatDTO y = chatlist.get(j);
				if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
					if(x.getChatID() < y.getChatID())
					{
						chatlist.remove(x);
						i--;
						break;
					}else {
						chatlist.remove(y);
					}
				}
			}
		}
		
		
		if(chatlist.size() == 0 ) return "";
		for(int i = chatlist.size() - 1 ; i >= 0 ; i--) {
			String unread = "";
			String profile = "";
			if(userID.equals(chatlist.get(i).getToID())) {
				unread = dao.getUnreadChat(chatlist.get(i).getFromID(), userID) + "";
				if(unread.equals("0")) unread = "";
			}
			if(userID.equals(chatlist.get(i).getToID())){
//				profile = new MemberDAO().getprofilePath(chatlist.get(i).getFromID());
				profile = "/img/profile.png";
			}else {
//				profile = new MemberDAO().getprofilePath(chatlist.get(i).getToID());
				profile = "/img/profile.png";
			}
			result.append("[{\"value\": \"" + chatlist.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"},");
			result.append("{\"value\": \"" + unread + "\"},");
			result.append("{\"value\": \"" + profile + "\"}]");
			if(i != 0) result.append(",");
		}
		result.append("], \"last\":\"" + chatlist.get(chatlist.size() - 1).getChatID() + "\"}");
		return result.toString();
	}
	


	
	@RequestMapping("/chatUnread")
	public void chatUnread(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("chatUnread");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		if(userID == null || userID.equals("") )
		{
			response.getWriter().write("0");
		}else {
			userID = URLDecoder.decode(userID, "UTF-8");
			response.getWriter().write(dao.getAllUnreadChat(userID) + "");
		}
	}
	
	
	
	
	
	
	@RequestMapping("/chatListServlet")
	public void chatListServlet(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("chatListServlet");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		
		System.out.println(fromID + " " + toID + " " + listType);
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null || listType.equals(""))
		{
			response.getWriter().write("");
		}
		else if(listType.equals("ten"))
		{	
			response.getWriter().write(getTen(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8")));
		}
		else {
			try {
				response.getWriter().write(getID(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8"), listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	
	
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		chatlist = dao.getChatListByRecent(fromID, toID, 100);
		if(chatlist.size() == 0 ) return "";
		for(int i = 0 ; i < chatlist.size() ; i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatlist.get(chatlist.size() - 1).getChatID() + "\"}");
		dao.readChat(fromID, toID);
		return result.toString();
	}

	public String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		chatlist = dao.getChatListById(fromID, toID, chatID);
		if(chatlist.size() == 0 ) return "";
		for(int i = 0 ; i < chatlist.size() ; i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatlist.get(chatlist.size() - 1).getChatID() + "\"}");
		dao.readChat(fromID, toID);
		return result.toString();
	}
	
	
	
	

	@RequestMapping("/chatSubmitServlet")
	public void chatSubmitServlet(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("chatSubmitServlet");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
	
		System.out.println(fromID + " " + toID + " " + chatContent);
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || chatContent == null || chatContent.equals(""))
		{
			response.getWriter().write("0");
		}else if(fromID.equals(toID)) {
			response.getWriter().write("-1");
		}
		else {
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			response.getWriter().write(dao.submit(fromID, toID, chatContent) + "");
		}
	}
	
	
	
	@RequestMapping("/userCheck")
	public void userCheck(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("userCheck");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");

		if(userID == null || userID.equals(""))
			response.getWriter().write("-1");
		
		if(dao.findID(userID) == 1)
		{
			response.getWriter().write(0 + "");
		}else {
			response.getWriter().write(-1 + "");
		}	
	}
	
	
	@RequestMapping("/manager_list")
	public void manager_list(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("manager_list");
		
		StringBuffer result = new StringBuffer("");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		
		System.out.println(userID + "dasddasdad");
				
		managerlist = dao.manager_list(userID);
		

		
		result.append("{\"result\":[");
		
		for(int i = 0 ; i < managerlist.size() ; i++) {
			result.append("[{\"value\": \"" + managerlist.get(i).getManager_id() + "\"},");
			result.append("{\"value\": \"" + managerlist.get(i).getManager_grade() + "\"}]");
			if(i != managerlist.size() - 1) result.append(",");
		}
		result.append("]}");
		
		response.getWriter().write(result.toString());
		
	}
	
	
}
