package com.study.android.mytry.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.study.android.mytry.R;
import com.study.android.mytry.main.MainActivity;

import java.util.regex.Pattern;


public class Login extends AppCompatActivity {
    private static final String TAG="lecture";
    SessionCallback callback;

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    private String password = "";

    //define view objects
    private EditText editTextUserEmail;
    private Button buttonFind;
    private TextView textviewMessage;
    private ProgressDialog progressDialog;
    //define firebase object




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);
    }

    public void singUp(View view) {
        Intent intent = new Intent(Login.this, JoinActivity.class);
        startActivity(intent);
    }

    public void signIn(View view) {

        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if(isValidEmail() && isValidPasswd()) {
            loginUser(email, password);
        }
    }

    public void missingPw(View view ){
        Intent intent = new Intent(Login.this,Password.class);
        startActivity(intent);}

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 로그인
    private void loginUser(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                //전역 변수 설정
                                GlobalApplication myApp = (GlobalApplication)getApplication();
                                myApp.setGlobalString(editTextEmail.getText().toString());
                                Log.e("GlobalVariablesActivity", myApp.getGlobalString());


                                // 로그인 성공
                                Toast.makeText(Login.this,"환영합니다", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("userEmail",editTextEmail.getText().toString());
                                startActivity(intent);
                            }else {
                                Toast.makeText(Login.this, "이메일 인증을 진행해주세요.",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // 로그인 실패
                            Toast.makeText(Login.this,"입력하신 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //카카오톡 로그인
    public class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.d("lecture", "카카오톡 로그인 들어옴");

            UserManagement.requestMe(new MeResponseCallback(){
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
                        // finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    Log.d("lecture", "카카오톡 로그인 성공");
                    //로그인에 성공하면 로그인한 사용자의 일련번호,닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.


                    long number = userProfile.getId();
                    Log.e("UserProfil_1", userProfile.toString());
                    Log.e("UserProfile_2", userProfile.getId() + "");

                    email = String.valueOf(userProfile.getId());

                    //전역변수 설정
                    GlobalApplication myApp = (GlobalApplication)getApplication();
                    myApp.setGlobalString(email);
                    Log.e("GlobalVariablesActivity", myApp.getGlobalString());


                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                }
            });

        }
        // 세션 실패시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.d("lecture", "세션 연결실패");

        }
    }
    public void requestMe() {
        Log.d("lecture", "카카오톡 유저 정보");
        //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e(TAG, "error message=" + errorResult);
//                super.onFailure(errorResult);
            }
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(TAG, "onSessionClosed1 =" + errorResult);
            }
            @Override
            public void onNotSignedUp() {
                //카카오톡 회원이 아닐시
                Log.d(TAG, "onNotSignedUp ");
            }
            @Override
            public void onSuccess(UserProfile result) {
                Log.e("UserProfile", result.toString());
                Log.e("UserProfile", result.getId() + "");

            }
        });
    }


}
