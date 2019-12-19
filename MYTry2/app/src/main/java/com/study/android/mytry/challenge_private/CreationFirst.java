package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationFirst extends AppCompatActivity {

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

    Button submit;

    HashMap<String, String> map = new HashMap<>();

    public static CreationFirst creationFirst;
    private int CREATION_FINISH = 2;
    private static final int PRIVATE_CREATION_FINISH = 2;

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_first);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();


        String url = local_url+"/yejin/private_modify";
        String msg = "?host="+userid;

        Log.d("lecture", msg);
        url = url +msg;
        Log.d("lecture", url);

        title = (EditText) findViewById(R.id.private_name_input);

        check_capability = (CheckBox)findViewById(R.id.check_private_capability);
        check_capability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_health.setChecked(false);
                    check_relationship.setChecked(false);
                    check_life.setChecked(false);
                    check_assets.setChecked(false);
                    check_hobby.setChecked(false);
                }
            }
        });

        check_health = (CheckBox)findViewById(R.id.check_private_health);
        check_health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_capability.setChecked(false);
                    check_relationship.setChecked(false);
                    check_life.setChecked(false);
                    check_assets.setChecked(false);
                    check_hobby.setChecked(false);
                }
            }
        });

        check_relationship = (CheckBox)findViewById(R.id.check_private_relationship);
        check_relationship.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_capability.setChecked(false);
                    check_health.setChecked(false);
                    check_life.setChecked(false);
                    check_assets.setChecked(false);
                    check_hobby.setChecked(false);
                }
            }
        });

        check_life = (CheckBox)findViewById(R.id.check_private_life);
        check_life.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_capability.setChecked(false);
                    check_health.setChecked(false);
                    check_relationship.setChecked(false);
                    check_assets.setChecked(false);
                    check_hobby.setChecked(false);
                }
            }
        });

        check_assets = (CheckBox)findViewById(R.id.check_private_assets);
        check_assets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_capability.setChecked(false);
                    check_health.setChecked(false);
                    check_relationship.setChecked(false);
                    check_life.setChecked(false);
                    check_hobby.setChecked(false);
                }
            }
        });

        check_hobby = (CheckBox)findViewById(R.id.check_private_hobby);
        check_hobby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_capability.setChecked(false);
                    check_health.setChecked(false);
                    check_relationship.setChecked(false);
                    check_life.setChecked(false);
                    check_assets.setChecked(false);
                }
            }
        });

        check_camera = (CheckBox)findViewById(R.id.check_type_camera);
        check_camera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_gallery.setChecked(false);
                    check_voice.setChecked(false);
                    check_map.setChecked(false);
                    check_movie.setChecked(false);
                }
            }
        });

        check_gallery = (CheckBox)findViewById(R.id.check_type_gallery);
        check_gallery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_camera.setChecked(false);
                    check_voice.setChecked(false);
                    check_map.setChecked(false);
                    check_movie.setChecked(false);
                }
            }
        });

        check_voice = (CheckBox)findViewById(R.id.check_type_audio);
        check_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_camera.setChecked(false);
                    check_gallery.setChecked(false);
                    check_map.setChecked(false);
                    check_movie.setChecked(false);
                }
            }
        });

        check_map = (CheckBox)findViewById(R.id.check_type_map);
        check_map.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_camera.setChecked(false);
                    check_gallery.setChecked(false);
                    check_voice.setChecked(false);
                    check_movie.setChecked(false);
                }
            }
        });

        check_movie = (CheckBox)findViewById(R.id.check_type_movie);
        check_movie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_camera.setChecked(false);
                    check_gallery.setChecked(false);
                    check_voice.setChecked(false);
                    check_map.setChecked(false);
                }
            }
        });

        submit = (Button) findViewById(R.id.first_next);

        creationFirst = CreationFirst.this;
    }


    public void onFirstNext(View v){
        Intent intent = new Intent(getApplicationContext(), CreationSecond.class);
        map.put("title",title.getText().toString());

        // 카테고리 선택
        if(check_capability.isChecked()){
            map.put("category","역량");
        } else if(check_health.isChecked()){
            map.put("category","건강");
        } else if(check_relationship.isChecked()){
            map.put("category","관계");
        } else if(check_life.isChecked()){
            map.put("category","자산");
        } else if(check_assets.isChecked()){
            map.put("category","생활");
        } else if(check_hobby.isChecked()){
            map.put("category","취미");
        }else {
        }

        if(check_camera.isChecked()){
            map.put("type","카메라");
        } else if(check_gallery.isChecked()){
            map.put("type","갤러리");
        } else if( check_voice.isChecked()){
            map.put("type","녹음");
        } else if(check_map.isChecked()){
            map.put("type","지도");
        } else if(check_movie.isChecked()){
            map.put("type","영화");
        }else {
        }

        intent.putExtra("hashmap",map);
        startActivityForResult(intent, CREATION_FINISH);
    }

    public void onBackPressed(View v){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("yejin", String.valueOf(requestCode));
        if (requestCode == PRIVATE_CREATION_FINISH) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(CreationFirst.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

}
