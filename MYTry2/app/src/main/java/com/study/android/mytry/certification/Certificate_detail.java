package com.study.android.mytry.certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.study.android.mytry.R;

public class Certificate_detail extends AppCompatActivity {
    private static final String tag = "lecture";
    String i_num;
    String i_check;
    String i_type;
    String i_title;
    String i_start;
    String i_end;
    String i_frequency;
    String i_time;
    String i_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_detail);

        TextView title = (TextView)findViewById(R.id.certification_challenge_content_challenge_title);
        TextView term = (TextView)findViewById(R.id.certification_challenge_content_term);
        TextView day = (TextView)findViewById(R.id.certification_challenge_content_day);
        TextView money = (TextView)findViewById(R.id.certification_challenge_content_total_money);
        TextView challenger = (TextView)findViewById(R.id.certification_challenge_content_total_challenger);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        i_num = intent.getStringExtra("num");
        i_check = intent.getStringExtra("check");
        i_type = intent.getStringExtra("type");
        i_title = intent.getStringExtra("title");
        i_start = intent.getStringExtra("start");
        i_end = intent.getStringExtra("end");
        i_frequency = intent.getStringExtra("frequency");
        i_time = intent.getStringExtra("time");
        i_img = intent.getStringExtra("img");

        title.setText(i_title);
        term.setText(i_start+" - "+i_end);
        day.setText(i_frequency);
        money.setText("모인금액 : 구하자");
        challenger.setText("참여자수 : 구하자");

    }
}