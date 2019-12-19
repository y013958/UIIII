package com.study.android.mytry.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SingerItemView extends LinearLayout {

    TextView title;
    TextView content;
   TextView textMonth;
   TextView textDate;
   TextView textDay;

     public SingerItemView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_alert_item,this, true);


         title = (TextView) findViewById(R.id.alert_list_title);
         content = (TextView) findViewById(R.id.alert_list_content);
         textMonth = (TextView) findViewById(R.id.alert_list_month);
         textDate = (TextView) findViewById(R.id.alert_list_date);
         textDay = (TextView) findViewById(R.id.alert_list_day);
    }

    public void setTitle(String str){
        title.setText(str);
    }
    public void setContent(String str){
        content.setText(str);
    }
    public void setDate(String str){
        String arrayStr[] =str.split("/");

        textMonth.setText(arrayStr[0]+"-"+arrayStr[1]);
        textDate.setText(arrayStr[2]);


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date nDate = dateFormat.parse(str);

            Calendar cal = Calendar.getInstance();
            cal.setTime(nDate);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK);

            switch (dayNum) {
                case 1:
                    textDay.setText("일");
                    break;
                case 2:
                    textDay.setText("월");
                    break;
                case 3:
                    textDay.setText("화");
                    break;
                case 4:
                    textDay.setText("수");
                    break;
                case 5:
                    textDay.setText("목");
                    break;
                case 6:
                    textDay.setText("금");
                    break;
                case 7:
                    textDay.setText("토");
                    break;

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
     }

}