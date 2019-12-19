package com.study.android.mytry.mypage;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import static com.study.android.mytry.login.Intro.local_url;

public class MemoInsert extends AppCompatActivity {

    EditText textTitle;
    EditText textContent;
    Button sendBtn;

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_calendar_main);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        textTitle = findViewById(R.id.memo_title);
        textContent = findViewById(R.id.memo_content);
        sendBtn = findViewById(R.id.memo_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/yejin/mypage_memo_insert";
                url = url + "?member_id=" + userid +
                        "&memo_title="+ textTitle.getText().toString()+
                        "&memo_content="+textContent.getText().toString();
                Log.d("lecture", url);

                MemoNetworkTask memoNetworkTask = new MemoNetworkTask(url, null);
                memoNetworkTask.execute();

                finish();
            }
        });
    }

    // 내 메모 추가
    public class MemoNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MemoNetworkTask(String url, ContentValues values) {

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
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

        }
    }
}
