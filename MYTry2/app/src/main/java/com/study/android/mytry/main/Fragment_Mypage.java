package com.study.android.mytry.main;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.study.android.mytry.R;
import com.study.android.mytry.cash.Kakao_cash;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.mypage.CalendarDialog;
import com.study.android.mytry.mypage.EventDecorator;
import com.study.android.mytry.mypage.OneDayDecorator;
import com.study.android.mytry.mypage.SaturdayDecorator;
import com.study.android.mytry.mypage.SingerAdapter;
import com.study.android.mytry.mypage.SingerItem;
import com.study.android.mytry.mypage.SundayDecorator;
import com.study.android.mytry.mypage.setup.SetupMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import static com.study.android.mytry.login.Intro.local_url;

public class Fragment_Mypage extends Fragment {
    private static final String TAG = "lecture";

    ImageView profileImage;
    TextView nickName;
    Button cashBtn;
    Button gradeBtn;
    Button followerBtn;
    Button followingBtn;
    Button setup;

    String userid;
    HashMap<String, String> mypage_map = new HashMap<>();

    private RadarChart chart;

    // 달력
    MaterialCalendarView materialCalendarView;
    public static SingerItem[] singerItems = new SingerItem[29];
    String shot_Day;
    ListView listView;
    CalendarDialog calendarDialog;
    int size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mypage_main, container, false);

        listView = rootView.findViewById(R.id.listview_alterdialog_list);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        nickName = rootView.findViewById(R.id.user_nickname);
        gradeBtn = rootView.findViewById(R.id.grade_button);
        followerBtn = rootView.findViewById(R.id.mypage_follower);
        followingBtn = rootView.findViewById(R.id.mypage_following);
        setup = rootView.findViewById(R.id.setup_button);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);

                fragmentTransaction.replace(R.id.mypage, new SetupMain()).addToBackStack(null).commit();
            }
        });

        cashBtn = rootView.findViewById(R.id.cash_inquiry_button);
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Kakao_cash.class);
                startActivity(intent);
            }
        });

        profileImage = (ImageView) rootView.findViewById(R.id.profile_image);

        String url = local_url + "/yejin/mypage_main_list";
        url = url + "?member_id=" + userid;
        Log.d("lecture", url);

        MemberNetworkTask networkTask = new MemberNetworkTask(url, null);
        networkTask.execute();

        nickName.setText(mypage_map.get("member_nickname"));
        //cashBtn.setText();
        gradeBtn.setText(mypage_map.get("member_grade"));
        //followerBtn.setText();
        //followingBtn.setText();

        //==================================================
        // Rader Chart 그리기!!!
        chart = rootView.findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.WHITE);

        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private final String[] mActivities = new String[]{"역량", "건강", "관계", "취미", "생활", "자산"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }

        });

        xAxis.setTextColor(Color.BLACK);

        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);

        //=========================================================
        // 달력 그리기
        materialCalendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

        // 오늘 날짜
        new ApiSimulator1().executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

                int DayLength = (int) (Math.log10(Day) + 1);
                int MonthLength = (int) (Math.log10(Day) + 1);

                if (DayLength == 1) {
                    shot_Day = Year + "/" + Month + "/" + "0" + Day;
                } else if (MonthLength == 1) {
                    shot_Day = Year + "/" + "0" + Month + "/" + Day;
                } else {
                    shot_Day = Year + "/" + Month + "/" + Day;
                }

                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();

                Toast.makeText(getActivity(), shot_Day, Toast.LENGTH_SHORT).show();

                // 다이얼로그 생성
                calendarDialog = new CalendarDialog(getActivity(), shot_Day);

                calendarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                calendarDialog.show();
            }
        });

        return rootView;
    }

    // 그래프 값 추가
    private void setData() {

        float mul = 80;
        float min = 20;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        float val1 = (float) (Integer.parseInt(mypage_map.get("member_capability")) * 0.1 * mul) + min;
        float val2 = (float) (Integer.parseInt(mypage_map.get("member_health")) * 0.1 * mul) + min;
        float val3 = (float) (Integer.parseInt(mypage_map.get("member_relationship")) * 0.1 * mul) + min;
        float val4 = (float) (Integer.parseInt(mypage_map.get("member_hobby")) * 0.1 * mul) + min;
        float val5 = (float) (Integer.parseInt(mypage_map.get("member_life")) * 0.1 * mul) + min;
        float val6 = (float) (Integer.parseInt(mypage_map.get("member_assets")) * 0.1 * mul) + min;

        entries1.add(new RadarEntry(val1));
        entries1.add(new RadarEntry(val2));
        entries1.add(new RadarEntry(val3));
        entries1.add(new RadarEntry(val4));
        entries1.add(new RadarEntry(val5));
        entries1.add(new RadarEntry(val6));

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(232, 148, 21));
        set1.setFillColor(Color.rgb(232, 148, 21));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);
        chart.invalidate();
    }


    // 회원정보 불러오기
    public class MemberNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MemberNetworkTask(String url, ContentValues values) {

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

                JSONArray MemberList = jsonObject.getJSONArray("List");

                if (MemberList != null) {
                    for (int i = 0; i < MemberList.length(); i++) {

                        JSONObject ListObject = MemberList.getJSONObject(i);

                        mypage_map.put("member_no", String.valueOf(ListObject.getInt("member_no")));
                        mypage_map.put("member_id", ListObject.getString("member_id"));
                        mypage_map.put("member_pw", ListObject.getString("member_pw"));
                        mypage_map.put("member_name", ListObject.getString("member_name"));
                        mypage_map.put("member_nickname", ListObject.getString("member_nickname"));
                        mypage_map.put("member_account", ListObject.getString("member_account"));
                        mypage_map.put("member_introduction", ListObject.getString("member_introduction"));
                        mypage_map.put("member_blacklist", ListObject.getString("member_blacklist"));
                        mypage_map.put("member_black_date", ListObject.getString("member_black_date"));
                        mypage_map.put("member_black_reason", ListObject.getString("member_black_reason"));
                        mypage_map.put("member_withdraw", ListObject.getString("member_withdraw"));
                        mypage_map.put("member_joindate", ListObject.getString("member_joindate"));
                        mypage_map.put("member_last_access", ListObject.getString("member_last_access"));
                        mypage_map.put("member_exp", ListObject.getString("member_exp"));
                        mypage_map.put("member_grade", ListObject.getString("member_grade"));
                        mypage_map.put("member_profile_image", ListObject.getString("member_profile_image"));
                        mypage_map.put("member_public", String.valueOf(ListObject.getInt("member_public")));
                        mypage_map.put("member_capability", String.valueOf(ListObject.getInt("member_capability")));
                        mypage_map.put("member_health", String.valueOf(ListObject.getInt("member_health")));
                        mypage_map.put("member_relationship", String.valueOf(ListObject.getInt("member_relationship")));
                        mypage_map.put("member_assets", String.valueOf(ListObject.getInt("member_assets")));
                        mypage_map.put("member_life", String.valueOf(ListObject.getInt("member_life")));
                        mypage_map.put("member_hobby", String.valueOf(ListObject.getInt("member_hobby")));

                        setData();
                    }

                    gradeBtn.setText("닭");
                    nickName.setText(mypage_map.get("member_nickname"));

                    if (mypage_map.get("member_grade").equals("0")) {
                        gradeBtn.setText("알");
                    } else if (mypage_map.get("member_grade").equals("1")) {
                        gradeBtn.setText("병아리");
                    } else if (mypage_map.get("member_grade").equals("2")) {
                    }

                    String imageurl = local_url + "/";
                    imageurl = imageurl + mypage_map.get("member_profile_image") + ".jpg";

                    FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(imageurl, null);
                    fileUploadNetworkTask.execute();

                    // 빨간 점 찍기 !!!!
                    String url2 = local_url + "/yejin/mypage_memo_select";
                    url2 = url2 + "?member_id=" + userid;
                    Log.d("lecture", url2);

//                    MemoNetworkTask memoNetworkTask = new MemoNetworkTask(url2, null);
//                    memoNetworkTask.execute();
//
//                    String url1 = local_url + "/yejin/mypage_challege_date";
//                    url1 = url1 + "?member_num=" + mypage_map.get("member_no");
//                    Log.d("lecture", url1);
//
//                    CertificateNetworkTask certificateNetworkTask = new CertificateNetworkTask(url1, null);
//                    certificateNetworkTask.execute();

                    if (getActivity().isFinishing()) {
                        return;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // 인증 날짜 정보 가져오기
    public class CertificateNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public CertificateNetworkTask(String url, ContentValues values) {

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

                    JSONArray ListArray = jsonObject.getJSONArray("List");

                    String[] challenge_date = new String[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        singerItems[size + i] = new SingerItem(ListObject.getInt("challenge_num"), ListObject.getString("challenge_title"), ListObject.getString("challenge_type"), ListObject.getString("certificate_date"));

                        challenge_date[i] = ListObject.getString("certificate_date");

                        System.out.println(challenge_date[i]);
                    }

                    new ApiSimulator(challenge_date).executeOnExecutor(Executors.newSingleThreadExecutor());

                    if (getActivity().isFinishing()) {
                        return;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 내 메모 가져오기
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

            try {
                if (json != null) {
                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray ListArray = jsonObject.getJSONArray("List");

                    String[] challenge_date = new String[ListArray.length()];

                    size = ListArray.length();

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        singerItems[i] = new SingerItem(ListObject.getInt("memo_no"), ListObject.getString("memo_title"), ListObject.getString("memo_content"), ListObject.getString("memo_date"));

                        challenge_date[i] = ListObject.getString("memo_date");

                        System.out.println(challenge_date[i]);
                    }

                    new ApiSimulator(challenge_date).executeOnExecutor(Executors.newSingleThreadExecutor());

                    if (getActivity().isFinishing()) {
                        return;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class FileUploadNetworkTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ContentValues values;

        public FileUploadNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap result; // 요청 결과를 저장할 변수.
            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap json) {
            super.onPostExecute(json);
            profileImage.setImageBitmap(json);
        }
    }

    //달력
    public class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result) {
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for (int i = 0; i < Time_Result.length; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split("/");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year, month - 1, dayy);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, getActivity()));
        }
    }


    // 오늘 날짜 체크
    public class ApiSimulator1 extends AsyncTask<Void, Void, List<CalendarDay>> {

        ApiSimulator1() {
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {

            ArrayList<CalendarDay> dates = new ArrayList<>();

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new OneDayDecorator(Color.RED, calendarDays, getActivity()));
        }
    }
}

