<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.CommentDto" %>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();


ArrayList<CommentDto> array =(ArrayList<CommentDto>) request.getAttribute("list");
int[] countlist = (int[])request.getAttribute("countlist");
int[] existlist = (int[])request.getAttribute("existlist");


for(int i=0;i<array.size();i++){

	JSONObject jObject = new JSONObject();
	
	jObject.put("comment_num", array.get(i).getComment_num());
	jObject.put("challenge_num",array.get(i).getChallenge_num());
	jObject.put("member_id", array.get(i).getMember_id());
	jObject.put("comment_content", array.get(i).getComment_content());
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	jObject.put("commment_date", date.format(array.get(i).getCommment_date()));
	jObject.put("commment_like_count", countlist[i]);
	jObject.put("commment_like_exist", existlist[i]);
	
	jArray.add(i, jObject);
}
jsonMain.put("List", jArray);
out.println(jsonMain);
%>