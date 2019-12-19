package com.study.android.mytry.challenge_public;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;
import static com.study.android.mytry.challenge_public.CreationMain.challenge_listView;
import static com.study.android.mytry.challenge_public.CreationMain.challenge_map;
import static com.study.android.mytry.challenge_public.CreationMain.userid;
import static com.study.android.mytry.login.Intro.local_url;

public class SingerAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ListView listView;

    ArrayList<SingerItem> items = new ArrayList<>();

    public SingerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(SingerItem item) {
        items.add(item);
        System.out.println(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SingerItemView view = null;

        if (convertView == null) {
            view = new SingerItemView(context);
        } else {
            view = (SingerItemView) convertView;
        }

        final SingerItem item = items.get(position);
        view.setCategory(item.getCategory());
        view.setTitle(item.getTitle());

        // 투표 버튼 처리 (숫자, 버튼)
        final Button like_btn = view.findViewById(R.id.public_item_good);
        like_btn.setFocusable(false);
        like_btn.setBackgroundResource(R.drawable.challenge_like_btn);
        like_btn.setText(String.valueOf(item.getLikecount()) + " 개");

        like_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if (item.getLikeExist() == 0){
                    builder.setTitle("좋아요");

                    builder.setMessage("해당 챌린지 개설 완료시 알림이 갑니다.\n" +
                            "앱 내 푸쉬설정, 휴대폰 푸쉬설정이 전부 켜져있어야 알림 받을 수 있습니다.")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 프로그램을 종료한다

                                        }
                                    });
                } else if(item.getLikeExist() == 1){
                    builder.setTitle("좋아요 취소");

                    builder.setMessage("챌린지 주제에 대한 좋아요가 취소되었습니다.\n" +
                            "챌린지 개설에 대한 알림을 원하실 경우 다시 좋아요를 눌러주세요.")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            // 프로그램을 종료한다

                                        }
                                    });
                }
                AlertDialog dialog = builder.create();
                dialog.show();

                String url = local_url+"/yejin/public_challenge_like";
                String msg = "?challenge_num=" + item.getNum() +
                        "&member_id="+userid;
                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);

                NetworkTask1 networkTask = new NetworkTask1(url, null);
                networkTask.execute();

            }
        });


        return view;
    }

      // 통신 = insert, delete?
    public class NetworkTask1 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask1(String url, ContentValues values) {

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
            // textView.setText(json);
            SingerAdapter singerAdapter = new SingerAdapter(context);

            JSONObject jsonObject = null;
            try {

                jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                SingerItem[] singerItems = new SingerItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    Log.d("lecture", ListObject.getString("challenge_num"));
                    Log.d("lecture", ListObject.getString("challenge_title"));
                    Log.d("lecture", ListObject.getString("challenge_type"));
                    Log.d("lecture", ListObject.getString("challenge_type"));
                    Log.d("lecture", ListObject.getString("challenge_detail"));

                    singerItems[i] = new SingerItem(ListObject.getInt("challenge_num"), ListObject.getString("challenge_title"), ListObject.getString("challenge_type"), ListObject.getInt("challenge_like_count"), ListObject.getInt("challenge_like_exist"));

                    singerAdapter.addItem(singerItems[i]);
                }

                challenge_listView.setAdapter(singerAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
