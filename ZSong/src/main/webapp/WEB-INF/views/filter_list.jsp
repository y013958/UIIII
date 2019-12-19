<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.filterDto"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
   JSONObject jsonMain = new JSONObject();
   JSONArray jArray = new JSONArray();

   ArrayList<filterDto> array = (ArrayList<filterDto>) request.getAttribute("filter_list");
   
   for (int i = 0; i < array.size(); i++) {
      JSONObject jObject = new JSONObject();
      jObject.put("interest_context", array.get(i).getInterest_context());
  	jArray.add(i, jObject);
   }
   jsonMain.put("filter_list", jArray);
   out.println(jsonMain);
%>