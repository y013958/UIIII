package com.study.android.mytry.challenge_private;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CreationThrid extends AppCompatActivity {

    HashMap<String, String> map;

    Button nextweek;
    Button next2weeks;
    Button next3weeks;

    EditText alongweek;
    TextView endDay;

    String startdate="";

    public static CreationThrid creationThrid;
    private int CREATION_FINISH = 2;
    private static final int PRIVATE_CREATION_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_thrid);

        nextweek= (Button) findViewById(R.id.nextweek);
        next2weeks= (Button) findViewById(R.id.next2weeks);
        next3weeks= (Button) findViewById(R.id.next3weeks);

        nextweek.setText(getNextMonday());
        next2weeks.setText(get2WeeksMonday());
        next3weeks.setText(get3WeeksMonday());

        alongweek = (EditText) findViewById(R.id.alongWeek);
        endDay = (TextView) findViewById(R.id.endDay);

        alongweek.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                try {
                    endDay.setText("챌린지는 "+setEndDay()+" (일)에 종료됩니다.");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
                try {
                    endDay.setText("챌린지는 "+setEndDay()+" (일)에 종료됩니다.");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent=getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        creationThrid = CreationThrid.this;

    }

    // 챌린지 시작일 계산 (다음주, 2주뒤, 3주뒤)
    public String getNextMonday(){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy.MM.dd");

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, 7);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return formatter.format(c.getTime());
    }

    public String get2WeeksMonday(){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy.MM.dd");

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, 14);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return formatter.format(c.getTime());
    }

    public String get3WeeksMonday(){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy.MM.dd");

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, 21);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return formatter.format(c.getTime());
    }

    // 챌린지 종료일 출력
    public String setEndDay() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Calendar c = Calendar.getInstance();

        if(alongweek.getText().toString().equals("1")){
            Date inputDate = formatter.parse(startdate);
            c.setTime(inputDate);
            c.add(Calendar.DAY_OF_WEEK,7);
        }else if(alongweek.getText().toString().equals("2")){
            Date inputDate = formatter.parse(startdate);
            c.setTime(inputDate);
            c.add(Calendar.DAY_OF_WEEK,14);
        }else if(alongweek.getText().toString().equals("3")){
            Date inputDate = formatter.parse(startdate);
            c.setTime(inputDate);
            c.add(Calendar.DAY_OF_WEEK,21);
        }else if(alongweek.getText().toString().equals("4")){
            Date inputDate = formatter.parse(startdate);
            c.setTime(inputDate);
            c.add(Calendar.DAY_OF_WEEK,28);
        }

        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return formatter.format(c.getTime());
    }

    // 버튼 클릭 리스너들
    public void onNextClick(View v) {
        Log.d("lecture",getNextMonday());

        nextweek.setBackgroundResource(R.drawable.xml_main_btn_round);
        nextweek.setTextColor(Color.WHITE);

        next2weeks.setBackgroundResource(R.drawable.xml_edittext_round);
        next2weeks.setTextColor(Color.parseColor("#E99413"));

        next3weeks.setBackgroundResource(R.drawable.xml_edittext_round);
        next3weeks.setTextColor(Color.parseColor("#E99413"));

        startdate=getNextMonday();

        try {
            endDay.setText("챌린지는 "+setEndDay()+" (일)에 종료됩니다.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onNext2WeeksClick(View v) {
        Log.d("lecture",get2WeeksMonday());

        next2weeks.setBackgroundResource(R.drawable.xml_main_btn_round);
        next2weeks.setTextColor(Color.WHITE);

        nextweek.setBackgroundResource(R.drawable.xml_edittext_round);
        nextweek.setTextColor(Color.parseColor("#E99413"));

        next3weeks.setBackgroundResource(R.drawable.xml_edittext_round);
        next3weeks.setTextColor(Color.parseColor("#E99413"));


        startdate=get2WeeksMonday();

        try {
            endDay.setText("챌린지는 "+setEndDay()+" (일)에 종료됩니다.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onNext3WeeClick(View v) {
        Log.d("lecture",get3WeeksMonday());

        next3weeks.setBackgroundResource(R.drawable.xml_main_btn_round);
        next3weeks.setTextColor(Color.WHITE);

        nextweek.setBackgroundResource(R.drawable.xml_edittext_round);
        nextweek.setTextColor(Color.parseColor("#E99413"));

        next2weeks.setBackgroundResource(R.drawable.xml_edittext_round);
        next2weeks.setTextColor(Color.parseColor("#E99413"));


        startdate=get3WeeksMonday();

        try {
            endDay.setText("챌린지는 "+setEndDay()+" (일)에 종료됩니다.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onThridNext(View v){
        Intent intent = new Intent(getApplicationContext(), CreationFourth.class);

        map.put("start",startdate);
        try {
            map.put("end",setEndDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intent.putExtra("hashmap",map);
        startActivityForResult(intent, CREATION_FINISH);
    }

    // 뒤로가기 버튼
    public void onBackPressed(View v){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("yejin", String.valueOf(requestCode));
        if (requestCode == PRIVATE_CREATION_FINISH) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(CreationThrid.this, CreationSecond.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
