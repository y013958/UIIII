<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.MemberDto"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	ArrayList<MemberDto> array = (ArrayList<MemberDto>) request.getAttribute("list");

	for (int i = 0; i < array.size(); i++) {

		JSONObject jObject = new JSONObject();

		jObject.put("member_no", array.get(i).getMember_no());
		jObject.put("member_id", array.get(i).getMember_id());
		jObject.put("member_pw", array.get(i).getMember_pw());
		jObject.put("member_name", array.get(i).getMember_name());
		jObject.put("member_nickname", array.get(i).getMember_nickname());
		jObject.put("member_account", array.get(i).getMember_account());
		jObject.put("member_introduction", array.get(i).getMember_introduction());
		jObject.put("member_blacklist", array.get(i).getMember_blacklist());

		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
		if (array.get(i).getMember_black_date() != null)
			jObject.put("member_black_date", date.format(array.get(i).getMember_black_date()));
		else
			jObject.put("member_black_date", array.get(i).getMember_black_date());
		
		if (array.get(i).getMember_withdraw() != null)
			jObject.put("member_withdraw", date.format(array.get(i).getMember_withdraw()));
		else
			jObject.put("member_withdraw", array.get(i).getMember_withdraw());
		
		if (array.get(i).getMember_joindate() != null)
			jObject.put("member_joindate", date.format(array.get(i).getMember_joindate()));
		else
			jObject.put("member_joindate", array.get(i).getMember_joindate());
		
		if (array.get(i).getMember_last_access() != null)
			jObject.put("member_last_access", date.format(array.get(i).getMember_last_access()));
		else
			jObject.put("member_last_access", array.get(i).getMember_last_access());
		
		jObject.put("member_black_reason", array.get(i).getMember_black_reason());
		jObject.put("member_exp", array.get(i).getMember_exp());
		jObject.put("member_grade", array.get(i).getMember_grade());
		jObject.put("member_profile_image", array.get(i).getMember_profile_image());
		jObject.put("member_public", array.get(i).getMember_public());
		jObject.put("member_capability", array.get(i).getMember_capability());
		jObject.put("member_health", array.get(i).getMember_health());
		jObject.put("member_relationship", array.get(i).getMember_relationship());
		jObject.put("member_assets", array.get(i).getMember_assets());
		jObject.put("member_life", array.get(i).getMember_life());
		jObject.put("member_hobby", array.get(i).getMember_hobby());
		
		jArray.add(i, jObject);
	}

	jsonMain.put("List", jArray);
	out.println(jsonMain);
%>