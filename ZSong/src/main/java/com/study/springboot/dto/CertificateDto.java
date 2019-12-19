package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CertificateDto {

   private int challenge_num;
   private int certificate_check;
   private String challenge_type;
   private String challenge_title;
   private Timestamp challenge_start;
   private Timestamp challenge_end;
   private String challenge_frequency;
   private String challenge_time;
   private String challenge_first_image;
   
}