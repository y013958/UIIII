package com.study.android.mytry.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.auth.Session;
import com.study.android.mytry.R;
import com.study.android.mytry.main.MainActivity;
import com.study.android.mytry.search.search_main;

public class Intro extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String user_email;

    public static String local_url = "http://192.168.219.139:8081";

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {

            Intent intent = null;
            //로그인이 되어 있지 않았을 경우
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                intent = new Intent(Intro.this,search_main.class);
                startActivity(intent); // 다음화면으로 넘어가기
                finish();
//                intent.putExtra("data", "Test Popup");
//                startActivityForResult(intent, 1);

            //로그인이 되었을 경우
            } else {

                    firebaseAuth = FirebaseAuth.getInstance();
                    user = firebaseAuth.getCurrentUser();
                    user_email = user.getEmail();

                if(user_email !=null) {
                    GlobalApplication myApp = (GlobalApplication) getApplication();
                    myApp.setGlobalString(user_email);
                    Log.e("GlobalVariablesActivity", myApp.getGlobalString());

                }
                else{
                    if (Session.getCurrentSession().checkAndImplicitOpen()) {
                        Log.e("GlobalVariablesActivity", "카카오 자동로그인");
                    } else {
                        Log.e("GlobalVariablesActivity", "카카오 자동로그인 실패");
                    }
                }

                Toast.makeText(Intro.this, "자동로그인 되셨습니다.", Toast.LENGTH_LONG).show();
                // 여기서 알아야함
                intent = new Intent(Intro.this,Logout.class);
//                intent = new Intent(Intro.this,Logout.class);
                startActivity(intent); // 다음화면으로 넘어가기
                finish(); // Activity 화면 제거
            }



        }

    };
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                //데이터 받기
//                String result = data.getStringExtra("result");
//                if(result.equals("gologin")){
//
//                    Intent intent = new Intent(Intro.this,Login.class);
//                    startActivity(intent); // 다음화면으로 넘어가기
//
//                }else{
//
//                    Intent intent = new Intent(Intro.this,MainActivity.class);
//                    startActivity(intent); // 다음화면으로 넘어가기
//                }
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_intro); // xml과 java소스를 연결
    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
// 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(r, 3000); // 4초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
// 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }
}
