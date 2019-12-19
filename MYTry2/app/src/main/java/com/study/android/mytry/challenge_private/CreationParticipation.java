package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationParticipation extends AppCompatActivity {

    EditText searchEdit;
    Button searchBtn;

    TextView searchResult;
    Button participateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_participation);

        searchEdit = findViewById(R.id.private_search_editText);
        searchBtn = findViewById(R.id.private_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/yejin/private_searchcode";
                String msg = "?code=" + searchEdit.getText();
                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);

                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
            }
        });
        searchResult = findViewById(R.id.private_search_result);
        participateBtn = findViewById(R.id.private_challenge_part);
    }

    public void onBackPressed(View v) {
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
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                System.out.println(json);
                JSONObject jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    String result = "챌린지 이름 : " + ListObject.getString("challenge_title") + "\n" +
                            "개설자 : " + ListObject.getString("challenge_host");
                    searchResult.setText(result);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
