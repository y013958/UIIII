package com.study.android.mytry.certification;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

public class CertificateDto {

    public String num = "";
    public String check = "";
    public String type = "";
    public String title = "";
    public String start = "";
    public String end = "";
    public String frequency = "";
    public String time = "";
    public String img;
    public int count;
    public int check_count;


    public CertificateDto(String num , String check , String type , String title , String start , String end , String frequency , String time , String img , int count , int check_count) {
        this.num = num;
        this.check = check;
        this.type = type;
        this.title = title;
        this.start = start;
        this.end = end;
        this.frequency = frequency;
        this.time = time;
        this.img = img;
        this.count = count;
        this.check_count = check_count;
    }

    public CertificateDto() {}
}
