package com.study.android.mytry.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.login.Login;

public class PopupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_popup);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

    }

    //확인 버튼 클릭
    public void gologin(View v){
        Intent intent = new Intent(PopupActivity.this, Login.class);
        startActivity(intent); // 다음화면으로 넘어가기
        finish();
    }

    //확인 버튼 클릭
    public void golookaround(View v){
//
//        Intent intent = new Intent(PopupActivity.this, search_main.class);
//        startActivity(intent); // 다음화면으로 넘어가기
//        intent.putExtra("data", "Test Popup");
//        startActivityForResult(intent, 1);

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
