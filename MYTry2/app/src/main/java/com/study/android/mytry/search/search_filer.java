package com.study.android.mytry.search;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.cash.Kakao_cash;
import com.study.android.mytry.cash.cash_singerAdapter;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class search_filer extends AppCompatActivity {
    private static final String TAG = "lecture";
    EditText inputsearch;
    Button filer_button;
    ListView listView;
    search_filter_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_filter);

        adapter = new search_filter_Adapter(search_filer.this);
        listView = findViewById(R.id.filter_list);
        inputsearch = findViewById(R.id.filer);
        filer_button = findViewById(R.id.filer_serach);

        String url = local_url+"/uzinee/filter_result";
        Log.d("llecture_frist", url);
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();


        //검색어 입력
        filer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = local_url+"/uzinee/insert_filter";
                Log.d("llecture_frist", url);
                String msg = "?filter="+inputsearch.getText();
                url = url +msg;
                Log.d("filter",url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

                Intent intent = new Intent(search_filer.this, search_filer_main.class);
                intent.putExtra("sendData", inputsearch.getText().toString());// 이 메서드를 통해 데이터를 전달합니다.

                startActivity(intent);
                finish();
            }

        });



    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            Log.d("done_money1231", "통과통과4");
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
            try {
                if(s != null) {
                    JSONObject json = new JSONObject(s);
                    Log.d("filter_json",""+json);
                    JSONArray ListArray = json.getJSONArray("filter_list");

                    search_filter_item[] filter_item = new search_filter_item[ListArray.length()];
                    Log.d("리스트 길이",""+ListArray.length());

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        filter_item[i] =
                        new search_filter_item(ListObject.getString("interest_context"));

                        adapter.addItem(filter_item[i]);
                        Log.d("ssssssettext2", "들어옴111");
                    }
                    listView.setAdapter(adapter);
                    Log.d("ssssssettext2", "들어옴222");

                } else {
                    Log.d("ssssssettext2", "null임");
                }

            } catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }

}