package com.study.springboot.dao;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import com.study.springboot.dto.CertificateDto;
import com.study.springboot.dto.ChallengeDto;

@Mapper
public interface SeleeDao {

   public void feed_insert(String member_id, String challenge_num, String challenge_title, String feed_update_time, String feed_image, String feed_comment);
   public void certificate_update(String challenge_num, String member_id);
   
   public ArrayList<CertificateDto> CertificateList(String member_id);	
   public int CertificateList_count(int cnum, String id);
   public int CertificateList_check_count(int cnum, String id);
   
   public void record_insert(String challenge_num, String member_id, int all_count, int challenge_fee);
   public void certificate_insert(String Challenge_num, String member_id, String certificate_date);
   
   public ChallengeDto selectChallenge(String challenge_num);
}
