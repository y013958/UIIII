package com.study.android.mytry.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationDetail;
import com.study.android.mytry.challenge_private.CreationQrCode;
import com.study.android.mytry.challenge_private.CreationSecond;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    HashMap<String, String> map = new HashMap<>();

    private static final int PUBLIC_CREATION_FINISH = 1;
    private static final int PRIVATE_CREATION_FINISH = 2;
    private static final int QR_CODE_SUCESS = 4;
    private static final int MYPAGE_MEMO_FINISH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabMenu);

        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction fragmentTransaction;

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("request : "+requestCode);
        System.out.println("result : "+resultCode);

        if (requestCode == PUBLIC_CREATION_FINISH) {
            Log.d("yejin", "Main 오니 안오니 public");
            if (resultCode == RESULT_OK) {
                Log.d("yejin", "Main 오니 안오니");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                fragmentTransaction.replace(R.id.fragment_creation, new com.study.android.mytry.challenge_public.CreationMain()).addToBackStack(null).commitAllowingStateLoss();
            }

        } else if (requestCode == PRIVATE_CREATION_FINISH) {
            Log.d("yejin", "Main 오니 안오니1");
            if (resultCode == RESULT_OK) {
                Log.d("lecture", "Main 들어오니");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                fragmentTransaction.replace(R.id.fragment_creation, new com.study.android.mytry.challenge_private.CreationMain()).addToBackStack(null).commitAllowingStateLoss();
            }
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(MainActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(MainActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());

                    Log.d("yejin",obj.getString("num"));
                    map.put("num",obj.getString("num"));
                    map.put("title",obj.getString("title"));
                    map.put("category",obj.getString("category"));
                    map.put("type",obj.getString("type"));
                    map.put("frequency",obj.getString("frequency"));
                    map.put("start",obj.getString("start"));
                    map.put("end",obj.getString("end"));
                    map.put("fee",obj.getString("fee"));
                    map.put("detail",obj.getString("detail"));
                    map.put("first_image",obj.getString("first_image"));
                    map.put("state",obj.getString("state"));
                    map.put("public",obj.getString("public"));
                    map.put("exp",obj.getString("exp"));
                    map.put("along",obj.getString("along"));
                    map.put("host",obj.getString("host"));

                    // 여기서 intent뿌리기 - putExtra에 챌린지 번호 넣어서 넘기기
                    Intent intent = new Intent(MainActivity.this, CreationDetail.class);
                    intent.putExtra("hashmap",map);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                    Log.d("yejin", result.getContents());
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public interface onKeyBackPressedListener{
        void onBackKey();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed(){
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        }else{
            super.onBackPressed();
        }
    }

}
