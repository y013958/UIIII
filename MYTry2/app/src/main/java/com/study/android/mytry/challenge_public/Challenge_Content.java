package com.study.android.mytry.challenge_public;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static com.study.android.mytry.challenge_public.CreationMain.challenge_listView;
import static com.study.android.mytry.challenge_public.CreationMain.challenge_map;
import static com.study.android.mytry.login.Intro.local_url;

public class Challenge_Content extends Fragment implements MainActivity.onKeyBackPressedListener{

    Button back_pressed;
    TextView title;
    TextView category;
    TextView detail;

    Button comment_insert;
    EditText comment_text;
    Button votebtn;

    public static ListView comment_listView;
    CommentAdapter commentAdapter;

    String url;
    String userid;

    CommentNetworkTask networkTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.challenge_public_content, container, false);

        setHasOptionsMenu(true);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        back_pressed = (Button) rootView.findViewById(R.id.public_content_back);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setOnKeyBackPressedListener(null);
                //액티비티의 콜백을 직접호출
                activity.onBackPressed();
            }
        });

        title = (TextView) rootView.findViewById(R.id.public_content_name);
        category = (TextView) rootView.findViewById(R.id.public_content_category);
        detail = (TextView) rootView.findViewById(R.id.public_content_detail);

        title.setText(challenge_map.get("title"));
        category.setText(challenge_map.get("category"));
        detail.setText(challenge_map.get("detail"));
        challenge_map.put("user", userid);

        votebtn = (Button) rootView.findViewById(R.id.public_content_good);
        votebtn.setText(challenge_map.get("like_count")+" 개");
        votebtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String url = local_url+"/yejin/public_challenge_like";

                String msg = "?challenge_num=" + challenge_map.get("num") +
                        "&member_id=" + userid;
                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);

                VoteNetworkTask networkTask = new VoteNetworkTask(url, null);
                networkTask.execute();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                if (challenge_map.get("like_count").equals("1")) {
                    builder.setTitle("좋아요");

                    builder.setMessage("해당 챌린지 개설 완료시 알림이 갑니다.\n" +
                            "앱 내 푸쉬설정, 휴대폰 푸쉬설정이 전부 켜져있어야 알림 받을 수 있습니다.")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {

                                            votebtn.setText(challenge_map.get("like_count")+" 개");
                                        }
                                    });
                } else if (challenge_map.get("like_count").equals("0")) {
                    builder.setTitle("좋아요 취소");

                    builder.setMessage("챌린지 주제에 대한 좋아요가 취소되었습니다.\n" +
                            "챌린지 개설에 대한 알림을 원하실 경우 다시 좋아요를 눌러주세요.")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {

                                            votebtn.setText(challenge_map.get("like_count")+" 개");
                                        }
                                    });
                }

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        url = local_url+"/yejin/comment_list";


        Log.d("lecture", url);

        comment_text = (EditText) rootView.findViewById(R.id.public_comment_text);
        comment_listView = rootView.findViewById(R.id.content_comment_list);
        commentAdapter = new CommentAdapter(getActivity());

        networkTask = new CommentNetworkTask(url, null);
        networkTask.execute();

        comment_insert = (Button) rootView.findViewById(R.id.public_comment_btn);
        comment_insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                url = local_url+"/yejin/comment_insert";
                String msg = "?challenge_num=" + challenge_map.get("num") +
                        "&member_id=" + userid +
                        "&content=" + comment_text.getText().toString();

                Log.d("lecture", msg);
                url = url + msg;

                Log.d("lecture", url);

                networkTask = new CommentNetworkTask(url, null);
                networkTask.execute();
            }
        });


               return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.context_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Toast.makeText(getActivity(), "삭제", Toast.LENGTH_LONG).show();
                break;
            case R.id.complaint:
                Toast.makeText(getActivity(), "신고", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 통신
    public class CommentNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public CommentNetworkTask(String url, ContentValues values) {

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
            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                Log.d("lecture", "리스트 갯수  " + String.valueOf(ListArray.length()));

                Log.d("lecture", "commentAdapter 갯수  " + String.valueOf(commentAdapter.getCount()));
                Log.d("lecture", challenge_map.get("num"));

                int count = 0;

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    if (challenge_map.get("num").equals(String.valueOf(ListObject.getInt("challenge_num")))) {
                        count++;
                    }
                }

                // 생성
                if (count < commentAdapter.getCount() || commentAdapter.getCount() == 0) {
                    CommentItem[] commentItems = new CommentItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                        Log.d("lecture", ListObject.getString("challenge_num"));
                        Log.d("lecture", ListObject.getString("member_id"));
                        Log.d("lecture", ListObject.getString("comment_content"));
                        Log.d("lecture", ListObject.getString("commment_date"));
                        Log.d("lecture", String.valueOf(ListObject.getInt("commment_like_count")));
                        Log.d("lecture", String.valueOf("exist" + ListObject.getInt("commment_like_exist")));

                        if (challenge_map.get("num").equals(String.valueOf(ListObject.getInt("challenge_num")))) {
                            commentItems[i] = new CommentItem(ListObject.getInt("comment_num"), String.valueOf(ListObject.getString("member_id")), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"));

                            commentAdapter.addItem(commentItems[i]);
                        }
                    }
                    comment_listView.setAdapter(commentAdapter);
                    Log.d("lecture", "commentAdapter 갯수 " + String.valueOf(commentAdapter.getCount()));
                } else {
                    JSONObject ListObject = ListArray.getJSONObject(ListArray.length() - 1);

                    Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                    Log.d("lecture", ListObject.getString("challenge_num"));
                    Log.d("lecture", ListObject.getString("member_id"));
                    Log.d("lecture", ListObject.getString("comment_content"));
                    Log.d("lecture", ListObject.getString("commment_date"));
                    Log.d("lecture", String.valueOf(ListObject.getInt("commment_like_count")));

                    if (challenge_map.get("num").equals(ListObject.getString("challenge_num"))) {
                        CommentItem commentItem = new CommentItem(ListObject.getInt("comment_num"), String.valueOf(ListObject.getString("member_id")), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"));

                        commentAdapter.addItem(commentItem);

                        commentAdapter.notifyDataSetChanged();
                        comment_listView.setAdapter(commentAdapter);

                        Log.d("lecture", "리스트 갯수 " + String.valueOf(commentAdapter.getCount()));
                    }

                    // 생성
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class VoteNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public VoteNetworkTask(String url, ContentValues values) {

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
            SingerAdapter singerAdapter = new SingerAdapter(getContext());

            JSONObject jsonObject = null;
            try {

                jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    Log.d("lecture", ListObject.getString("challenge_num"));
                    Log.d("lecture", ListObject.getString("challenge_title"));
                    Log.d("lecture", ListObject.getString("challenge_category"));
                    Log.d("lecture", ListObject.getString("challenge_type"));
                    Log.d("lecture", ListObject.getString("challenge_detail"));

                    if (Integer.parseInt(challenge_map.get("num")) == ListObject.getInt("challenge_num")) {

                        challenge_map.put("like_count", String.valueOf(ListObject.getInt("challenge_like_count")));
                        challenge_map.put("like_exist", String.valueOf(ListObject.getInt("challenge_like_exist")));
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //메인뷰 액티비티의 뒤로가기 callback 붙이기
        ((MainActivity)context).setOnKeyBackPressedListener(this);

    }

    @Override
    public void onBackKey() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        //액티비티의 콜백을 직접호출
        activity.onBackPressed();
    }

}
