<%@page import="com.study.springboot.dao.MyPageDao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.MyPageCdateDto" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%

JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();

ArrayList<MyPageCdateDto> dto = (ArrayList<MyPageCdateDto>)request.getAttribute("list");

for(int i = 0 ; i < dto.size() ; i++)
{
   JSONObject jObject = new JSONObject();
   
    jObject.put("challenge_num", dto.get(i).getChallenge_num());
   jObject.put("challenge_type", dto.get(i).getChallenge_type());
    jObject.put("challenge_title", dto.get(i).getChallenge_title());
//    jObject.put("challenge_time", dto.get(i).getChallenge_time());
    jObject.put("certificate_check", dto.get(i).getCertificate_check());
    SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
    jObject.put("certificate_date", date.format(dto.get(i).getCertificate_date()));

    jArray.add(i, jObject);
}
 jsonMain.put("List", jArray);
 out.println(jsonMain); 
%>