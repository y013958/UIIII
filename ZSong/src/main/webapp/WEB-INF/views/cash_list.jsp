<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.json.simple.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.study.springboot.dto.CashListDto"%>

<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	ArrayList<CashListDto> array = (ArrayList<CashListDto>) request.getAttribute("cashlist");

	for (int i = 0; i < array.size(); i++) {

		JSONObject jObject = new JSONObject();

		jObject.put("list_cash_num", array.get(i).getList_cash_num());
		jObject.put("list_member_id", array.get(i).getList_member_id());
		jObject.put("list_cash_content", array.get(i).getList_cash_content());
	    SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		jObject.put("list_cash_date", date.format(array.get(i).getList_cash_date()));
		jObject.put("list_cash_state", array.get(i).getList_cash_state());
		jObject.put("list_cash_check", array.get(i).getList_cash_check());
		jArray.add(i, jObject);
	}
	jsonMain.put("cashlist", jArray);
	out.println(jsonMain);
%>
