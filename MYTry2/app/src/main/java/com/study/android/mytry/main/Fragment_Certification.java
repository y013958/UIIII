package com.study.android.mytry.main;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.study.android.mytry.R;
import com.study.android.mytry.certification.CertificateAdapter;
import com.study.android.mytry.certification.CertificateDto;
import com.study.android.mytry.certification.CertificateItem;
import com.study.android.mytry.certification.Certificate_camera;
import com.study.android.mytry.certification.Certificate_detail;
import com.study.android.mytry.certification.Certificate_gallery;
import com.study.android.mytry.certification.Certificate_map;
import com.study.android.mytry.certification.Certificate_movie;
import com.study.android.mytry.certification.Certificate_voice;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.study.android.mytry.login.Intro.local_url;

public class Fragment_Certification extends Fragment {
    private static final String tag = "certificate";

    ListView c_list;

    ArrayList<CertificateDto> certificate;
    CertificateAdapter adapter;

    Button top_btn1;
    Button top_btn2;
    String url2;
    String userid;

    JSONObject ListObject2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.certification_main, container, false);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        c_list = view.findViewById(R.id.certification_listView);
        top_btn1 = view.findViewById(R.id.certification_top_btn1);
        top_btn2 = view.findViewById(R.id.certification_top_btn2);
        
        url2 = local_url + "/selee/certificate_list";
        String msg = "?member_id="+userid;
        url2 = url2+msg;
        Log.d("certificate", "url2 : "+url2);

        //인증가능 list출력(top_btn1)
        NetworkTask1 networkTask1 = new NetworkTask1(url2, null);
        networkTask1.execute();

        top_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //인증가능 list출력
                NetworkTask1 networkTask1 = new NetworkTask1(url2, null);
                networkTask1.execute();
            }
        });

        top_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //전체 list출력
                NetworkTask2 networkTask2 = new NetworkTask2(url2, null);
                networkTask2.execute();
            }
        });

        c_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("certificate", "type : "+certificate.get(position).type);

                Intent intent = null;

                if(certificate.get(position).check.equals("0")) {
                    if(certificate.get(position).type.equals("카메라")) {
                        intent = new Intent(
                                getActivity().getApplicationContext(), // 현재화면의 제어권자
                                Certificate_camera.class); // 다음넘어갈 화면
                    } else if(certificate.get(position).type.equals("갤러리")) {
                        intent = new Intent(
                                getActivity().getApplicationContext(), // 현재화면의 제어권자
                                Certificate_gallery.class); // 다음넘어갈 화면
                    } else if(certificate.get(position).type.equals("음성")) {
                        intent = new Intent(
                                getActivity().getApplicationContext(), // 현재화면의 제어권자
                                Certificate_voice.class); // 다음넘어갈 화면
                    }  else if(certificate.get(position).type.equals("지도")) {
                        intent = new Intent(
                                getActivity().getApplicationContext(), // 현재화면의 제어권자
                                Certificate_map.class); // 다음넘어갈 화면
                    } else { // type : 영화
                        intent = new Intent(
                                getActivity().getApplicationContext(), // 현재화면의 제어권자
                                Certificate_movie.class); // 다음넘어갈 화면
                    }
                } else {
                    intent = new Intent(
                            getActivity().getApplicationContext(), // 현재화면의 제어권자
                            Certificate_detail.class); // 다음넘어갈 화면
                }

                intent.putExtra("num", certificate.get(position).num);
                intent.putExtra("check", certificate.get(position).check);
                intent.putExtra("type", certificate.get(position).type);
                intent.putExtra("title", certificate.get(position).title);
                intent.putExtra("start", certificate.get(position).start);
                intent.putExtra("end", certificate.get(position).end);
                intent.putExtra("frequency", certificate.get(position).frequency);
                intent.putExtra("time", certificate.get(position).time);
                intent.putExtra("img", certificate.get(position).img);

                startActivity(intent);
            }
        });
        return view;
    }

    // 인증가능 list 통신
    public class NetworkTask1 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask1(String url, ContentValues values) {
            this.url = url;
            this.values = values;
            Log.d(tag, "통신111111");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            Log.d(tag, "resultttttt " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            certificate = new ArrayList<>();
            Log.d(tag, "jsonnnnnnnnnn : "+json);

            try {
                if(json !=null) {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray ListArray = jsonObject.getJSONArray("List");
                    Log.d("certificate", ListArray.toString());

                    CertificateItem[] certificateItems = new CertificateItem[ListArray.length()];
                    Log.d("certificate", String.valueOf(ListArray.length()));

                    for (int i = 0; i < ListArray.length(); i++) {

                        ListObject2 = ListArray.getJSONObject(i);

                        certificateItems[i] = new CertificateItem(
                                ListObject2.getString("challenge_num"),
                                ListObject2.getString("certificate_check"),
                                ListObject2.getString("challenge_type"),
                                ListObject2.getString("challenge_title"),
                                ListObject2.getString("challenge_start"),
                                ListObject2.getString("challenge_end"),
                                ListObject2.getString("challenge_frequency"),
                                ListObject2.getString("challenge_time"),
                                ListObject2.getString("challenge_first_image"),
                                ListObject2.getInt("all_count"),
                                ListObject2.getInt("check_count")
                        );

                        Log.d(tag, ListObject2.getString("challenge_start") + "    " + ListObject2.getString("challenge_end") + "   " + ListObject2.getString("challenge_frequency"));

                        if (ListObject2.getString("certificate_check").equals("0")) {
                            certificate.add(new CertificateDto(
                                    ListObject2.getString("challenge_num"),
                                    ListObject2.getString("certificate_check"),
                                    ListObject2.getString("challenge_type"),
                                    ListObject2.getString("challenge_title"),
                                    ListObject2.getString("challenge_start"),
                                    ListObject2.getString("challenge_end"),
                                    ListObject2.getString("challenge_frequency"),
                                    ListObject2.getString("challenge_time"),
                                    ListObject2.getString("challenge_first_image"),
                                    ListObject2.getInt("all_count"),
                                    ListObject2.getInt("check_count")));
                        }
                    }
                    adapter = new CertificateAdapter(
                            getActivity().getApplicationContext(), // 현재화면의 제어권자
                            R.layout.certification_main_item,  // 리스트뷰의 한행의 레이아웃
                            certificate);         // 데이터

                    c_list.setAdapter(adapter);
                } else{
                    Log.d("certificate", "Nulllllllll");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 전체인증
    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {
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

            certificate = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray ListArray = jsonObject.getJSONArray("List");
                Log.d("certificate", ListArray.toString());
                CertificateItem [] certificateItems = new CertificateItem[ListArray.length()];

                Log.d("certificate", String.valueOf(ListArray.length()));

                for (int i = 0; i < ListArray.length(); i++) {
                    ListObject2 = ListArray.getJSONObject(i);
                    certificateItems[i] = new CertificateItem(
                            ListObject2.getString("challenge_num"),
                            ListObject2.getString("certificate_check"),
                            ListObject2.getString("challenge_type"),
                            ListObject2.getString("challenge_title"),
                            ListObject2.getString("challenge_start"),
                            ListObject2.getString("challenge_end"),
                            ListObject2.getString("challenge_frequency"),
                            ListObject2.getString("challenge_time"),
                            ListObject2.getString("challenge_first_image"),
                            ListObject2.getInt("all_count"),
                            ListObject2.getInt("check_count")
                    );

                    certificate.add(new CertificateDto(
                            ListObject2.getString("challenge_num"),
                            ListObject2.getString("certificate_check"),
                            ListObject2.getString("challenge_type"),
                            ListObject2.getString("challenge_title"),
                            ListObject2.getString("challenge_start"),
                            ListObject2.getString("challenge_end"),
                            ListObject2.getString("challenge_frequency"),
                            ListObject2.getString("challenge_time"),
                            ListObject2.getString("challenge_first_image"),
                            ListObject2.getInt("all_count"),
                            ListObject2.getInt("check_count")));
                }

                adapter = new CertificateAdapter(
                        getActivity().getApplicationContext(), // 현재화면의 제어권자
                        R.layout.certification_main_item,  // 리스트뷰의 한행의 레이아웃
                        certificate);         // 데이터

                c_list.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}