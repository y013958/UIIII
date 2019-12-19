package com.study.android.mytry.certification;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.study.android.mytry.login.Intro.local_url;

public class Certificate_movie extends AppCompatActivity {
    TextView challenge_title;
    EditText comment;
    Button submit_btn;
    String userid;
    String challenge_num;
    String url;
    Button back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_movie);

        challenge_title = findViewById(R.id.certification_movie_title);
        comment = (EditText)findViewById(R.id.certification_movie_editText);
        submit_btn = (Button)findViewById(R.id.certification_movie_submit_btn);

        back_pressed = (Button) findViewById(R.id.certification_movie_back_btn);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        Intent intent = getIntent();
        String tempTitle = intent.getExtras().getString("title");
        challenge_title.setText(tempTitle);
        challenge_num = intent.getExtras().getString("num");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadClicked();
            }
        });
    }

    public void uploadClicked(){

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String sysdate = sdfNow.format(date);

        url = local_url + "/selee/feed_insert";

        String msg = "?member_id="+userid+
                "&challenge_num="+challenge_num+
                "&challenge_title="+challenge_title.getText().toString()+
                "&feed_update_time="+sysdate+
                "&feed_image="+challenge_num+"/"+userid+"/"+sysdate+
                "&feed_comment="+comment.getText().toString();

        url = url +msg;
        Log.d("certificate", url);

        Certificate_movie.NetworkTask networkTask = new Certificate_movie.NetworkTask(url, null);
        networkTask.execute();

        Toast.makeText(Certificate_movie.this,"성공적으로 인증을 마쳤습니다.",Toast.LENGTH_LONG).show();

        finish();
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
}
