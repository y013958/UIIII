<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.Serach_ChallengeDto"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
   JSONObject jsonMain = new JSONObject();
   JSONArray jArray = new JSONArray();

   ArrayList<Serach_ChallengeDto> array = (ArrayList<Serach_ChallengeDto>) request.getAttribute("search_list");
   int[] existlist = (int[]) request.getAttribute("bookmakerlist");
   
   for (int i = 0; i < array.size(); i++) {
      JSONObject jObject = new JSONObject();

      jObject.put("challenge_num", array.get(i).getChallenge_num());
      jObject.put("challenge_title",array.get(i).getChallenge_title());
      jObject.put("challenge_category", array.get(i).getChallenge_category());
      jObject.put("challenge_type", array.get(i).getChallenge_type());
      jObject.put("challenge_frequency", array.get(i).getChallenge_frequency());
      if(array.get(i).getChallenge_start() != null){
         SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
          jObject.put("challenge_start", date.format(array.get(i).getChallenge_start()));
          jObject.put("challenge_end", date.format(array.get(i).getChallenge_end()));  
      } else {
          jObject.put("challenge_start", array.get(i).getChallenge_start());
          jObject.put("challenge_end", array.get(i).getChallenge_end());         
      }

      jObject.put("challenge_fee", array.get(i).getChallenge_fee());
      jObject.put("challenge_time", array.get(i).getChallenge_time());
      jObject.put("challenge_detail", array.get(i).getChallenge_detail());
      jObject.put("challenge_first_image", array.get(i).getChallenge_first_image());
      jObject.put("challenge_public", array.get(i).getChallenge_public());
      jObject.put("challenge_state", array.get(i).getChallenge_state());
      jObject.put("challenge_exp", array.get(i).getChallenge_exp());
     // jObject.put("challenge_date", array.get(i).getChallenge_date());
      jObject.put("challenge_host", array.get(i).getChallenge_host());

      jObject.put("bookmake_exist", existlist[i]);
   	  System.out.println("bookmake_exist"+existlist[i]);
      jArray.add(i, jObject);
   }
   jsonMain.put("search_list", jArray);
   out.println(jsonMain);
%>