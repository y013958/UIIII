package com.study.android.mytry.cash;

import android.app.Activity;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;


public class Cash_deposit extends Activity {

    String input_cash1;
    EditText ed_cash;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cash_deposit);

        Button requset = findViewById(R.id.cash);
        ed_cash = findViewById(R.id.edcash);

        GlobalApplication myApp = (GlobalApplication)getApplication();

        ed_cash=findViewById(R.id.edcash);

        Intent intent = getIntent();
        data = intent.getStringExtra("data");

        requset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_cash1 = ed_cash.getText().toString();

                if (Integer.parseInt(input_cash1) < 5000 || input_cash1 == "") {
                    Log.d("input_cash", "충전 금액 판단");
                    Log.d("input_cash", input_cash1);

                    Toast.makeText(Cash_deposit.this,"5000원 이상 충전 가능합니다.", Toast.LENGTH_SHORT).show();

                } else {

                        //데이터 전달하기
                        Intent intent = new Intent();
                        intent.putExtra("result",input_cash1);
                        setResult(RESULT_OK, intent);

                        //액티비티(팝업) 닫기
                        finish();


                    Log.d("input_cash", "결제 불러옴");
                }
            }
        });


    }
    //확인 버튼 클릭
    public void mOnClose(View v){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}

