package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.study.android.mytry.challenge_private.CreationMain.private_listView;
import static com.study.android.mytry.login.Intro.local_url;


public class SingerAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();
    LinearLayout linearLayout;

    HashMap<String, String> map = new HashMap<>();


    public SingerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(SingerItem item) {
        items.add(item);
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
        view.setState(item.getState());
        view.setTitle(item.getTitle());
        view.setAlongday(item.getAlongday());

        linearLayout = view.findViewById(R.id.private_item);

        if (item.getState().equals("승인 대기중")) {
            linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.xml_private_list1));
        } else if (item.getState().equals("승인됨")) {
            linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.xml_main_button_round));

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreationQrCode.class);

                    map.put("num", String.valueOf(item.getNum()));
                    map.put("title", item.getTitle());
                    map.put("category", item.getCategory());
                    map.put("type", item.getType());
                    map.put("frequency", item.getFrequency());
                    map.put("start", item.getStart());
                    map.put("end", item.getEnd());
                    map.put("fee", item.getFee());

                    map.put("detail", item.getDetail());
                    map.put("first_image", item.getFirst_image());
                    map.put("state", item.getState());
                    map.put("public", item.getPub());
                    map.put("exp", item.getExp());
                    map.put("along", item.getAlongday());
                    map.put("host", item.getHost());

                    intent.putExtra("hashmap", map);
                    context.startActivity(intent);
                }
            });

        } else if (item.getState().equals("진행중")) {
            linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.xml_private_list2));

            Intent intent = new Intent(context, CreationDetail.class);

            map.put("num", String.valueOf(item.getNum()));
            map.put("title", item.getTitle());
            map.put("category", item.getCategory());
            map.put("type", item.getType());
            map.put("frequency", item.getFrequency());
            map.put("start", item.getStart());
            map.put("end", item.getEnd());
            map.put("fee", item.getFee());

            map.put("detail", item.getDetail());
            map.put("first_image", item.getFirst_image());
            map.put("state", item.getState());
            map.put("public", item.getPub());
            map.put("exp", item.getExp());
            map.put("along", item.getAlongday());
            map.put("host", item.getHost());
            intent.putExtra("hashmap", map);
            context.startActivity(intent);

        } else if (item.getState().equals("완료")) {
            linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.xml_main_button_round));
        }

        //********************************
        //리스트뷰안의 버튼 클릭 이벤트 처리
        Button modify = view.findViewById(R.id.private_item_modify);
        modify.setFocusable(false);
        modify.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, CreationFirst.class);
                context.startActivity(intent);
            }
        });

        Button delete = view.findViewById(R.id.private_item_delete);
        delete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String url = local_url + "/yejin/private_delete";
                String msg = "?challenge_num=" + item.getNum()
                        + "&id=" + item.getHost();

                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);

                DeleteNetworkTask deleteNetworkTask = new DeleteNetworkTask(url, null);
                deleteNetworkTask.execute();

                String fee = item.getFee();
                String host = item.getHost();
                Log.d("lecture", "fee " + fee);
                Log.d("lecture", "host " + host);

                String url1 = local_url + "/uzinee/fee_delete";
                String msg1 = "?fee=" + fee + "&host=" + host;

                Log.d("lecture", msg1);
                url1 = url1 + msg1;
                Log.d("lecture", url1);
                deleteNetworkTask = new DeleteNetworkTask(url1, null);
                deleteNetworkTask.execute();

            }
        });

        Log.d("lecture", item.getFee());
        return view;
    }

    public class DeleteNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public DeleteNetworkTask(String url, ContentValues values) {

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

            if (json != null) {
                try {
                    System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray ListArray = jsonObject.getJSONArray("List");

                    SingerAdapter singerAdapter = new SingerAdapter(context);
                    SingerItem[] singerItems = new SingerItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        String alongday = ListObject.getString("challenge_start") + "/" + ListObject.getString("challenge_end");

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

                        singerAdapter.addItem(singerItems[i]);
                    }

                    private_listView.setAdapter(singerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
