package com.study.android.mytry.challenge_public;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationThrid;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationPage extends AppCompatActivity  {
    EditText title;

    CheckBox check_capability;
    CheckBox check_health;
    CheckBox check_relationship;
    CheckBox check_life;
    CheckBox check_assets;
    CheckBox check_hobby;

    CheckBox check_camera;
    CheckBox check_gallery;
    CheckBox check_voice;
    CheckBox check_map;
    CheckBox check_movie;

    EditText detail;

    Button submit;

    Button back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_public_creation);

        title = (EditText) findViewById(R.id.public_title_input);

        check_capability = (CheckBox) findViewById(R.id.check_capability);
        check_health = (CheckBox) findViewById(R.id.check_health);
        check_relationship = (CheckBox) findViewById(R.id.check_relationship);
        check_life = (CheckBox) findViewById(R.id.check_life);
        check_assets = (CheckBox) findViewById(R.id.check_assets);
        check_hobby = (CheckBox) findViewById(R.id.check_hobby);

        check_camera = (CheckBox) findViewById(R.id.check_camera);
        check_gallery = (CheckBox) findViewById(R.id.check_gallery);
        check_voice = (CheckBox) findViewById(R.id.check_voice);
        check_map = (CheckBox) findViewById(R.id.check_map);
        check_movie = (CheckBox) findViewById(R.id.check_movie);

        detail = (EditText) findViewById(R.id.challenge_public_detail);
        submit = (Button) findViewById(R.id.challenge_submit);

        back_pressed = (Button) findViewById(R.id.public_back_page);

    }

    public void onFinishClick(View v) {
        // 제목
        String check_category = "";
        // 카테고리 선택
        if (check_capability.isChecked()) {
            check_category = "역량";
        } else if (check_health.isChecked()) {
            check_category = "건강";
        } else if (check_relationship.isChecked()) {
            check_category = "관계";
        } else if (check_life.isChecked()) {
            check_category = "생활";
        } else if (check_assets.isChecked()) {
            check_category = "자산";
        } else if (check_hobby.isChecked()) {
            check_category = "취미";
        } else {
        }

        String check_type = "";
        // 인증방법 선택
        if (check_camera.isChecked()) {
            check_type = "카메라";
        } else if (check_gallery.isChecked()) {
            check_type = "갤러리";
        } else if (check_voice.isChecked()) {
            check_type = "녹음";
        } else if (check_map.isChecked()) {
            check_type = "지도";
        } else if (check_movie.isChecked()) {
            check_type = "영화";
        } else {
        }

        String url = local_url + "/yejin/public_create";
        String msg = "?title=" + title.getText().toString() +
                "&category=" + check_category +
                "&type=" + check_type +
                "&detail=" + detail.getText().toString();
        Log.d("lecture", msg);
        url = url + msg;
        Log.d("lecture", url);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        Toast.makeText(CreationPage.this, "챌린지 주제제안이 완료되었습니다.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(CreationPage.this, MainActivity.class);
        setResult(1, intent);
        finish();
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
        }
    }
}
