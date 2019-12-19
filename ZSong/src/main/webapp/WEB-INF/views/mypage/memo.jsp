<%@page import="com.study.springboot.dao.MyPageDao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.MypageMemoDto"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	ArrayList<MypageMemoDto> dto = (ArrayList<MypageMemoDto>) request.getAttribute("list");

	for (int i = 0; i < dto.size(); i++) {
		JSONObject jObject = new JSONObject();

		jObject.put("memo_no", dto.get(i).getMemo_no());
		jObject.put("memo_title", dto.get(i).getMemo_title());
		jObject.put("memo_content", dto.get(i).getMemo_content());
		jObject.put("memo_id", dto.get(i).getMemo_userid());
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
		jObject.put("memo_date", date.format(dto.get(i).getMemo_date()));

		jArray.add(i, jObject);
	}
	jsonMain.put("List", jArray);
	out.println(jsonMain);
%>