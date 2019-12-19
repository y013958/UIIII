package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.search.search_SingerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationMain extends Fragment {

    Button back_pressed;
    public static ListView private_listView;

    SingerAdapter adapter;
    private int CREATION_FINISH = 2;
    private int QR_CODE_SUCESS =4;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    FloatingActionButton main_btn, qrcode_btn, create_btn, search_btn;

    private IntentIntegrator qrScan;
    String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.challenge_private_main, container, false);


        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);

        // 비공개 챌린지 생성
        main_btn = rootView.findViewById(R.id.private_floating_main);
        qrcode_btn = rootView.findViewById(R.id.private_floating_qrcode);
        create_btn = rootView.findViewById(R.id.private_floating_creation);
        search_btn = rootView.findViewById(R.id.private_floating_search);
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();

            }
        });

        qrScan = new IntentIntegrator(getActivity());

        qrcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                qrScan.setPrompt("Scanning...");

                qrScan.initiateScan();
            }
        });

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(getActivity(), CreationFirst.class);
                getActivity().startActivityForResult(intent, CREATION_FINISH);

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(getActivity(), CreationParticipation.class);
                getActivity().startActivityForResult(intent, CREATION_FINISH);
            }
        });

        back_pressed = (Button) rootView.findViewById(R.id.private_back_page);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        private_listView = rootView.findViewById(R.id.private_challenge_list);
        adapter = new SingerAdapter(getActivity());

        String url = local_url + "/yejin/private_list";
        url = url+"?id="+userid;
        Log.d("lecture", url);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        return rootView;
    }

    private void anim() {
        if (isFabOpen) {
            qrcode_btn.startAnimation(fab_close);
            create_btn.startAnimation(fab_close);
            search_btn.startAnimation(fab_close);
            qrcode_btn.setClickable(false);
            create_btn.setClickable(false);
            search_btn.setClickable(false);
            isFabOpen = false;
        } else {
            qrcode_btn.startAnimation(fab_open);
            create_btn.startAnimation(fab_open);
            search_btn.startAnimation(fab_open);
            qrcode_btn.setClickable(true);
            create_btn.setClickable(true);
            search_btn.setClickable(true);
            isFabOpen = true;
        }
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

                SingerItem[] singerItems = new SingerItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    String alongday = ListObject.getString("challenge_start")+"/"+ListObject.getString("challenge_end");

                    String str = "";
                    if (ListObject.getString("challenge_state").equals("0")) {
                        str = "승인 대기중";
                    } else if (ListObject.getString("challenge_state").equals("1")) {
                        str = "승인됨";
                    } else if (ListObject.getString("challenge_state").equals("2")) {
                        str = "진행중";
                    } else if (ListObject.getString("challenge_state").equals("3")) {
                        str = "완료";
                    }

                    singerItems[i] = new SingerItem(
                            ListObject.getInt("challenge_num"),
                            ListObject.getString("challenge_title"),
                            ListObject.getString("challenge_category"),
                            ListObject.getString("challenge_type"),
                            ListObject.getString("challenge_frequency"),
                            ListObject.getString("challenge_start"),
                            ListObject.getString("challenge_end"),
                            String.valueOf(ListObject.getInt("challenge_fee")),
//                                ListObject.getString("challenge_time"),
                            ListObject.getString("challenge_detail"),
                            ListObject.getString("challenge_first_image"),
                            str,
                            ListObject.getString("challenge_public"),
                            String.valueOf(ListObject.getInt("challenge_exp")),
                            alongday,
                            ListObject.getString("challenge_host"));

                    adapter.addItem( singerItems[i]);
                }

                private_listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}