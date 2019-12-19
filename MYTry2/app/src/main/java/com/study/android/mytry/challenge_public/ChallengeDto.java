package com.study.android.mytry.challenge_public;

public class ChallengeDto {

    String num;
    String title;
    String category;
    String type;
    String detail;
    int like_count;
    int like_exist;

    public ChallengeDto(String num, String title, String category, String type, String detail, int like_count, int like_exist){
        super();
        this.num = num;
        this.title = title;
        this.category = category;
        this.type = type;
        this.detail = detail;
        this.like_count=like_count;
        this.like_exist=like_exist;
    }

    public ChallengeDto(){

    }
}
