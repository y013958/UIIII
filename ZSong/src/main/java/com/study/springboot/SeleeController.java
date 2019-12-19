package com.study.springboot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.SeleeDao;
import com.study.springboot.dto.CertificateDto;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CountDto;
import com.study.springboot.dto.MemberDto;

@Controller
@RequestMapping("/selee")
public class SeleeController {

   @Autowired
   SeleeDao dao;
   
   List<MemberDto> userlist;
   ArrayList<ChallengeDto> challengelist;
   ArrayList<CertificateDto> certificateList;
   ArrayList<CountDto> count;
   
   // 피드추가
   @RequestMapping("/feed_insert") 
   public void feed_insert(HttpServletRequest request, 
         @RequestParam("member_id") String member_id, 
         @RequestParam("challenge_num") String challenge_num,
         @RequestParam("challenge_title") String challenge_title, 
         @RequestParam("feed_update_time") String feed_update_time, 
         @RequestParam("feed_image") String feed_image, 
         @RequestParam("feed_comment") String feed_comment) {
      
      System.out.println("feed_insert");
      
      dao.feed_insert(member_id, challenge_num, challenge_title, feed_update_time, feed_image, feed_comment);
      dao.certificate_update(challenge_num, member_id);
   }
   
   // 인증화면
   @RequestMapping("/certificate_list")
   public String CertificateList(Model model, 
         @RequestParam("member_id") String member_id) {
      
      System.out.println("CertificateList");
      
      count  = new ArrayList<>();
      certificateList = new ArrayList<>();
      
      certificateList = dao.CertificateList(member_id);
      
      System.out.println(certificateList.size());
      for(int i = 0 ; i < certificateList.size() ; i++)
      {
         System.out.println("challenge_num : "+ certificateList.get(i).getChallenge_num());
         
         CountDto dto = new CountDto();
         
         dto.setAll_count(dao.CertificateList_count(certificateList.get(i).getChallenge_num() , member_id));
         dto.setCheck_count(dao.CertificateList_check_count(certificateList.get(i).getChallenge_num() , member_id));
         
         count.add(i, dto);
      }
      
      model.addAttribute("count", count);
      model.addAttribute("list", certificateList);
      return "/certificate_list";
   }   
   
   // 참가하기
   @RequestMapping("/record_insert") 
   public void record_insert(HttpServletRequest request, 
         @RequestParam("challenge_num") String challenge_num,
         @RequestParam("member_id") String member_id) {
      
      System.out.println("record_insert");
      
      ChallengeDto dto;
      
      dto = dao.selectChallenge(challenge_num);
      
      Timestamp challenge_start = dto.getChallenge_start();
      Timestamp challenge_end = dto.getChallenge_end();
      String challenge_frequency = dto.getChallenge_frequency();
      int challenge_fee = dto.getChallenge_fee();
      System.out.println("challenge_start, end : " + challenge_start+"  "+challenge_end);
      
      Date d_start = new Date(challenge_start.getTime());
      Date d_end = new Date(challenge_end.getTime());
      System.out.println("d_start, end : " + d_start+"  "+d_end);
      
      int term = doDiffOfDate(d_start, d_end);
      System.out.println("term : " + term);
      
      int all_count = countCheck(term, challenge_frequency);
      System.out.println("all_count : " + all_count);
      
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
      String start = sdf.format(challenge_start);
      String end = sdf.format(challenge_end);
      System.out.println("start, end : " + start +"  "+ end);
      
      dao.record_insert(challenge_num, member_id, all_count, challenge_fee);

      String certificate_date = null;
      try {
           if (challenge_frequency.equals("매일")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
            	System.out.println("매일_certificate_date : "+certificate_date);
                dao.certificate_insert(challenge_num, member_id, certificate_date);
                certificate_date = addDate(certificate_date,0,0,1);
             }
           } else if (challenge_frequency.equals("주중")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 5; j++) {
                	System.out.println("주중_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,1);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,2);
             }
           } else if (challenge_frequency.equals("월수금")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 3; j++) {
                	System.out.println("월수금_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,2);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,2);
             }
           } else if (challenge_frequency.equals("주말")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 2; j++) {
                	System.out.println("주말_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,1);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,5);
             }
           }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      
   }

   private static String addDate(String dt, int y, int m, int d) throws Exception  {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

      Calendar cal = Calendar.getInstance();
      Date date = (Date) format.parse(dt);
      cal.setTime(date);
        cal.add(Calendar.YEAR, y);      //년 더하기
        cal.add(Calendar.MONTH, m);      //년 더하기
        cal.add(Calendar.DATE, d);      //년 더하기

      return format.format(cal.getTime());
   }
   
   // 두날짜의 차이 구하기
   public int doDiffOfDate(Date start, Date end){

       // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
       long diff = end.getTime() - start.getTime();
       int diffDays = (int) (diff / ((24 * 60 * 60 * 1000))+1)/7;
       
       System.out.println("diffDays : "+diffDays);
       return diffDays;
   }

   public int countCheck(int term, String i_frequency) {
       int fre = 0;
       if (i_frequency.equals("매일")) {
           fre = 7;
       } else if (i_frequency.equals("주중")) {
           fre = 5;
       } else if (i_frequency.equals("월수금")) {
           fre = 3;
       } else if (i_frequency.equals("주말")) {
           fre = 2;
       }
       return fre * term;
   }
}