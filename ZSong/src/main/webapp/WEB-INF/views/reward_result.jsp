<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@page import="org.json.simple.*"%>

<%
   int result_total = (int)request.getAttribute("total");
   System.out.println(result_total);

   JSONObject jsonMain = new JSONObject();
   JSONArray jArray = new JSONArray();

   JSONObject jObject = new JSONObject();
 
   jObject.put("reward_total", result_total);

//    jArray.add(0, jObject);

//    jsonMain.put("List", jArray);

   out.println(jObject);

%>
