package com.study.android.mytry.cash;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;

import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import kr.co.bootpay.Bootpay;

import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;

import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

import static com.study.android.mytry.login.Intro.local_url;

public class Kakao_cash extends AppCompatActivity {

    private int stuck = 10;
    String result;
    String withdrawal_result;
    String Cash_reward;
    EditText ed_cash;
    String cash_deposit;
    TextView mileage;
    TextView mycash;
    String cash_user;
    NetworkTask networkTask;
    NetworkTask2 networkTask2;
    NetworkTask3 networkTask3;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;

    ListView listView;
    cash_singerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash);

        ed_cash = findViewById(R.id.edcash);

        GlobalApplication myApp = (GlobalApplication)getApplication();
        cash_user = myApp.getGlobalString();

        mileage = findViewById(R.id.mileage);
        ed_cash=findViewById(R.id.edcash);
        mileage.setText("0원");

        mycash =findViewById(R.id.mycash);
        mycash.setText("0원");

        String url = local_url+"/uzinee/cash_result";
        String msg = "?id="+cash_user;
        Log.d("lecture_frist", msg);
        url = url +msg;
        Log.d("llecture_frist", url);

        networkTask = new Kakao_cash.NetworkTask(url, null);
        networkTask.execute();


        String url2 = local_url+"/uzinee/total_reward";
        String msg2 = "?id="+cash_user;
        Log.d("lecture_frist", msg2);
        url2 = url2 +msg2;
        Log.d("llecture_frist", url2);

        networkTask3 = new Kakao_cash.NetworkTask3(url2, null);
        networkTask3.execute();


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab = findViewById(R.id.fab);
        fab1 =findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);

        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim();
            }
        });
        fab1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim();
                Intent intent = new Intent(Kakao_cash.this, Cash_deposit.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
            }
        });

        fab2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim();
                Intent intent = new Intent(Kakao_cash.this, Cash_withdrawal.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
            }
        });

        fab3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim();
                Intent intent = new Intent(Kakao_cash.this, Cash_reward.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
            }
        });


        BootpayAnalytics.init(this, "5de48cd80627a8002487cb1c");
        listView = findViewById(R.id.cash_list);
         adapter = new cash_singerAdapter(Kakao_cash.this);


        String url1 = local_url+"/uzinee/cash_list";
        String msg1 = "?id="+cash_user;
        Log.d("lecture_frist", msg1);
        url1 = url1 +msg1;
        Log.d("llecture_frist", url1);
        networkTask2 = new NetworkTask2(url1, null);
        networkTask2.execute();

    }

    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                if(data.getStringExtra("result")!=null) {
                    result = data.getStringExtra("result");
                    user_cash();
                    Log.d("uzinee", "입금");
                }
                if(data.getStringExtra("withdrawal_result")!=null) {
                    withdrawal_result=data.getStringExtra("withdrawal_result");
                    withdrawal_result_cash();
                    Log.d("uzinee", "출금");
                }if(data.getStringExtra("Cash_reward")!=null){
                    Cash_reward =data.getStringExtra("Cash_reward");
                    Cash_reward();
                    Log.d("uzinee", "상금전환");
                }
            }

        }
    }
    public void user_cash() {
        // 결제호출
        BootUser bootUser = new BootUser().setPhone("");
        BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});
        Bootpay.init(getFragmentManager())
                .setApplicationId("5de48cd80627a8002487cb1c") // 해당 프로젝트(안드로이드)의 application id 값
                .setContext(this)
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
                .setName("츄라이") // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호expire_month
                .setPrice(Integer.parseInt(result)) // 결제할 금액
                .addItem("츄라이", 1, "ITEM_CODE_MOUSE", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .addItem("츄라이", 2, "ITEM_CODE_KEYBOARD", 200) // 주문정보에 담길 상품정보, 통계를 위해 사용
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {

                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d("confirm", message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {

                        Log.d("done_money1231", "성공");
                        try {

                            JSONObject json = new JSONObject(message);
                            json.get("amount").toString();

                            Log.d("done_money", message);
                            Log.d("done_money", json.get("amount").toString());


                            GlobalApplication myApp = (GlobalApplication) getApplication();
                            Log.e("GlobalVariablesActivity", myApp.getGlobalString());

                            cash_deposit = json.getString("amount");
                            cash_user = myApp.getGlobalString();

                            Log.d("done_money1231", cash_deposit);
                            Log.d("done_money1231", cash_user);

                            if(mileage.getText().equals("0원")) {
                                Log.d("lecture", "충전한 적 없음");
                                String url = local_url+"/uzinee/cash";
                                String msg = "?id=" + cash_user + "&cash_deposit=" + cash_deposit;
                                url = url + msg;
                                Log.d("lecture", msg);
                                Log.d("lecture", url);
                                networkTask = new Kakao_cash.NetworkTask(url, null);
                                networkTask.execute();

                                Log.d("lecture", "충전내역1");
                                String url1 = local_url+"/uzinee/cash_deposit";
                                String msg1 = "?id=" + cash_user + "&cash_deposit=" + cash_deposit;
                                url1 = url1 + msg1;
                                Log.d("lecture", msg1);
                                Log.d("lecture", url1);
                                networkTask = new Kakao_cash.NetworkTask(url1, null);
                                networkTask.execute();
                                finish();
                                Intent intent = new Intent(Kakao_cash.this, Kakao_cash.class);
                                startActivity(intent);
                            }else{
                                Log.d("lecture", "이미 충전한 적 있음");
                                String url = local_url+"/uzinee/cal_cash";
                                String msg = "?id=" + cash_user + "&cash_deposit=" + cash_deposit;
                                Log.d("lecture", msg);
                                url = url + msg;
                                Log.d("lecture", url);
                                networkTask = new Kakao_cash.NetworkTask(url, null);
                                networkTask.execute();

                                Log.d("lecture", "충전내역22222");
                                String url1 =local_url+"/uzinee/cash_deposit";
                                String msg1 = "?id=" + cash_user + "&cash_deposit=" + cash_deposit;
                                url1 = url1 + msg1;
                                Log.d("lecture", msg1);
                                Log.d("lecture", url1);
                                networkTask = new Kakao_cash.NetworkTask(url1, null);
                                networkTask.execute();

                                finish();
                                Intent intent = new Intent(Kakao_cash.this, Kakao_cash.class);
                                startActivity(intent);
                            }
                        }
                        catch (JSONException e) {

                        }
                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d("ready", message);

                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {

                        Log.d("cancel", message);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d("KAKAO_error", message);
                    }
                })
                .onClose(
                        new CloseListener() { //결제창이 닫힐때 실행되는 부분
                            @Override
                            public void onClose(String message) {
                                Log.d("kakaoclose", "close");
                            }
                        })
                .show();

    }
    //상금을 마일리지로 전환
    public void Cash_reward(){
        //출금 호출
        Log.e("lecture", "상금 전환");
        GlobalApplication myApp = (GlobalApplication) getApplication();
        Log.e("GlobalVariablesActivity", myApp.getGlobalString());
        cash_user = myApp.getGlobalString();
        String url = local_url+"/uzinee/change_reward";
        String msg = "?id=" + cash_user + "&cash_deposit=" +Cash_reward;
        Log.d("lecture", msg);
        url = url + msg;
        Log.d("lecture", url);
        networkTask = new Kakao_cash.NetworkTask(url, null);
        networkTask.execute();


        Log.d("lecture", "상금내역");
        String url1 = local_url+"/uzinee/list_reward";
        String msg1 = "?id=" + cash_user + "&cash_deposit=" +Cash_reward;
        url1 = url1 + msg1;
        Log.d("lecture", msg1);
        Log.d("lecture", url1);
        networkTask = new Kakao_cash.NetworkTask(url1, null);
        networkTask.execute();

        finish();
        Intent intent = new Intent(Kakao_cash.this, Kakao_cash.class);
        startActivity(intent);


    }

    public void withdrawal_result_cash(){
        //출금 호출
        Log.e("lecture", "출금");
        GlobalApplication myApp = (GlobalApplication) getApplication();
        Log.e("GlobalVariablesActivity", myApp.getGlobalString());
        cash_user = myApp.getGlobalString();
        String url = local_url+"/uzinee/withdrawal";
        String msg = "?id=" + cash_user + "&cash_deposit=" +  withdrawal_result;
        Log.d("lecture", msg);
        url = url + msg;
        Log.d("lecture", url);
        networkTask = new Kakao_cash.NetworkTask(url, null);
        networkTask.execute();


        Log.d("lecture", "출금내역");
        String url1 = local_url+"/uzinee/cash_withdrawal";
        String msg1 = "?id=" + cash_user + "&cash_deposit=" + withdrawal_result;
        url1 = url1 + msg1;
        Log.d("lecture", msg1);
        Log.d("lecture", url1);
        networkTask = new Kakao_cash.NetworkTask(url1, null);
        networkTask.execute();
        Toast.makeText(Kakao_cash.this,"입금까지 하루정도 소요 되실 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(Kakao_cash.this, Kakao_cash.class);
        startActivity(intent);

    }
    // 통신
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
                    Log.d("ssssssettext", json.getString("cash_total"));
                    mileage.setText(json.getString("cash_total")+"원");

                }


            }   catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }


    // 통신
    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {
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
        protected void onPostExecute(String json) {

            super.onPostExecute(json);

            try {
                if (json != null) {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.d("ssssssettext2", "들어옴");
                    Log.d("ssssssettext2", json);
                    JSONArray ListArray = jsonObject.getJSONArray("cashlist");

                    Cash_SingerItem[] singerItems = new Cash_SingerItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {
                        JSONObject ListObject = ListArray.getJSONObject(i);

                        singerItems[i] = new Cash_SingerItem(String.valueOf(ListObject.getInt("list_cash_content")),
                                String.valueOf(ListObject.getInt("list_cash_check")),
                                ListObject.getString("list_cash_date"));
                        adapter.addItem(singerItems[i]);
                        Log.d("ssssssettext2", "들어옴111");
                    }
                    listView.setAdapter(adapter);
                    Log.d("ssssssettext2", "들어옴222");

                } else {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.cash_list__item, null);

                    TextView cash_date = view.findViewById(R.id.cash_date);
                    TextView cash_name = view.findViewById(R.id.cash_name);
                    TextView cash_content = view.findViewById(R.id.cash_content);

                    cash_date.setText(" ");
                    cash_name.setText(" ");
                    cash_content.setText(" ");

                    Log.d("ssssssettext2", "null임");

                }

            } catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }
        // 통신
        public class NetworkTask3 extends AsyncTask<Void, Void, String> {

            private String url;
            private ContentValues values;

            public NetworkTask3(String url, ContentValues values) {
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
                        Log.d("ssssssettext", json.getString("reward_total"));
                        mycash.setText(json.getString("reward_total")+"원");

                    }


                }   catch (JSONException e) {
                    Log.d("eeeeeeettext", "");
                    e.printStackTrace();
                }
            }
        }

    }
