package com.study.android.mytry.login;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.UserInfo;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.study.android.mytry.R;

import java.net.URL;
import java.util.regex.Pattern;

import static com.study.android.mytry.login.Intro.local_url;

public class JoinActivity extends AppCompatActivity{
    private static final String TAG="lecture";
    SessionCallback callback;
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private EditText editTextnickname;
    private EditText editTextname;

    private String email = "";
    private String password = "";
    private String ch_password = "";
    private String user_name="";
    private String user_nickname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_join);

        //카카오톡 로그인
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        requestMe();

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);
        editTextPassword2 = findViewById(R.id.et_password2);
        editTextnickname = findViewById(R.id.nickname);
        editTextname = findViewById(R.id.et_name);
    }

    public void singUp(View view) {

        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        ch_password = editTextPassword2.getText().toString();
        user_name =editTextnickname.getText().toString();
        user_nickname =editTextname.getText().toString();


        if(isValidEmail() && isValidPasswd()) {
            if(password.equals(ch_password)) {
                createUser(email, password,user_name,user_nickname);
                Log.d("lecture", "회원가입");
            }
            else if(password!=ch_password){
                Toast.makeText(JoinActivity.this,"비밀번호를 일치하게 입력해주세요",Toast.LENGTH_LONG).show();
            }
        }
    }


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

    // 회원가입
    private void createUser(final String email, final String password,final String user_name,final String user_nickname) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    final String result;
                                    Log.d("lecture", "회원가입 들어옴");
                                    if(task.isSuccessful()){
                                        Log.d("lecture", password);
                                        Log.d("lecture", ch_password);
                                        Log.d("lecture", user_name);
                                        Log.d("lecture", user_nickname);

                                        String url = local_url+"/uzinee/join";
                                        String msg = "?id="+email+"&pw="+password+"&name="+user_name+"&nickname="+user_nickname;
                                        Log.d("lecture", msg);
                                        url = url +msg;
                                        Log.d("lecture", url);

                                        NetworkTask networkTask = new NetworkTask(url, null);
                                        networkTask.execute();

                                        Toast.makeText(JoinActivity.this,"회원가입 성공",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(JoinActivity.this,Login.class);
                                        startActivity(intent);
                                    } else{
                                        Toast.makeText(JoinActivity.this,"아이디는 이메일 형식으로 입력해주세요",Toast.LENGTH_LONG).show();
                                    }

                                }

                            });

                        }else{
                            Toast.makeText(JoinActivity.this,"비밀번호는 최소 4~16글자입니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // 통신
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //   tv_outPut.setText(s);
        }


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

                    Log.e("UserProfil_1", userProfile.toString());
                    Log.e("UserProfile_2", userProfile.getId() + "");

                    email = String.valueOf(userProfile.getId());
                    user_name = String.valueOf(userProfile.getNickname());
                    user_nickname =String.valueOf(userProfile.getNickname());
                    password ="Kakao";

                    Log.e("kakao(try)", email);
                    Log.e("kakao(try)", user_name);
                    Log.e("kakao(try)", user_nickname);
                    Log.e("kakao(try)", password);

                    String url = local_url+"/uzinee/join";
                    String msg = "?id="+email+"&pw="+password+"&name="+user_name+"&nickname="+user_nickname;
                    Log.d("lecture", msg);
                    url = url +msg;
                    Log.d("lecture", url);

                    NetworkTask networkTask = new NetworkTask(url, null);
                    networkTask.execute();

                    long number = userProfile.getId();

                    Intent intent = new Intent(JoinActivity.this, Logout.class);
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
