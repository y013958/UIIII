<%@page import="com.study.springboot.dto.CountDto"%>
<%@page import="com.study.springboot.dao.SeleeDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.CertificateDto" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
SeleeDao dao;
JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();

String id = getInitParameter("member_id");
String num;

ArrayList<CertificateDto> dto = (ArrayList<CertificateDto>)request.getAttribute("list");
ArrayList<CountDto> count = (ArrayList<CountDto>)request.getAttribute("count");

for(int i = 0 ; i < dto.size() ; i++)
{
   JSONObject jObject = new JSONObject();
   
    jObject.put("challenge_num", dto.get(i).getChallenge_num());
   jObject.put("certificate_check", dto.get(i).getCertificate_check());
   jObject.put("challenge_type", dto.get(i).getChallenge_type());
    jObject.put("challenge_title", dto.get(i).getChallenge_title());
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    jObject.put("challenge_start", date.format(dto.get(i).getChallenge_start()));
    jObject.put("challenge_end", date.format(dto.get(i).getChallenge_end()));
    jObject.put("challenge_frequency", dto.get(i).getChallenge_frequency());
    jObject.put("challenge_time", dto.get(i).getChallenge_time());
    jObject.put("challenge_first_image", dto.get(i).getChallenge_first_image());
    jObject.put("all_count", count.get(i).getAll_count()); 
    jObject.put("check_count", count.get(i).getCheck_count()); 
    
    System.out.println(count.get(i).getAll_count() + "    " + count.get(i).getCheck_count());
    System.out.println(date.format(dto.get(i).getChallenge_start()) + "    " + date.format(dto.get(i).getChallenge_end()));
    
    jArray.add(i, jObject);
}
 jsonMain.put("List", jArray);
 out.println(jsonMain); 
%>