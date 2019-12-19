package com.study.android.mytry.certification;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.study.android.mytry.login.Intro.local_url;

public class Certificate_voice extends AppCompatActivity {
    private static final String tag="certificate";
    private static String RECORDED_FILE;

    MediaPlayer player;
    MediaRecorder recorder;

    // 권한 체크 후 사용자에 의해 취소되었다면 다시 요청
    String[] permissions= new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    TextView challenge_title;
    EditText comment;
    Button submit_btn;
    String userid;
    String challenge_num;
    Button back_pressed;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_voice);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "recorded.mp3");
        RECORDED_FILE = file.getAbsolutePath();

        if (!checkPermissions()) {
            Toast.makeText(getApplicationContext(),
                    "권한 설정을 해주셔야 앱이 정상 동작합니다.",
                    Toast.LENGTH_LONG).show();
        }

        challenge_title = findViewById(R.id.certification_voice_title);

        comment = (EditText)findViewById(R.id.certification_voice_editText);
        submit_btn = (Button)findViewById(R.id.certification_voice_submit_btn);

        back_pressed = (Button) findViewById(R.id.certification_voice_back_btn);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        Intent intent = getIntent();
        String tempTitle = intent.getExtras().getString("title");
        challenge_title.setText(tempTitle);
        challenge_num = intent.getExtras().getString("num");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadClicked();
            }
        });

    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    1 );
            return false;
        }
        return true;
    }

    public void onBtn1Clicked(View v) {
        if(recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }

        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(RECORDED_FILE);

        try {
            Toast.makeText(getApplicationContext(), "녹음을 시작합니다.",
                    Toast.LENGTH_LONG).show();

            recorder.prepare();
            recorder.start();
        } catch (Exception ex) {
            Log.d(tag, "Exception:", ex);
        }
    }

    public void onBtn2Clicked(View v) {
        if(recorder == null)
            return;

        recorder.stop();
        recorder.release();
        recorder = null;

        Toast.makeText(getApplicationContext(), "녹음이 중지되었습니다.",
                Toast.LENGTH_LONG).show();
    }

    public void onBtn3Clicked(View v) {
        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }

        Toast.makeText(getApplicationContext(), "녹음된 파일을 재생합니다.",
                Toast.LENGTH_LONG).show();
        try {
            player = new MediaPlayer();

            player.setDataSource(RECORDED_FILE);
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.d(tag, "Audio play failed.", e);
        }
    }

    public void onBtn4Clicked(View v) {
        if(player == null)
            return;

        Toast.makeText(getApplicationContext(), "재생이 중지되었습니다.",
                Toast.LENGTH_LONG).show();

        player.stop();
        player.release();
        player = null;
    }

    public void uploadClicked(){

        url = local_url + "/selee/feed_insert";
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String sysdate = sdfNow.format(date);

        String msg = "?member_id="+userid+
                "&challenge_num="+challenge_num+
                "&challenge_title="+challenge_title.getText().toString()+
                "&feed_update_time="+sysdate+
                "&feed_image="+challenge_num+"/"+userid+"/"+sysdate+
                "&feed_comment="+comment.getText().toString();

        url = url +msg;
        Log.d("certificate", url);

        Certificate_voice.NetworkTask networkTask = new Certificate_voice.NetworkTask(url, null);
        networkTask.execute();

        Toast.makeText(Certificate_voice.this,"성공적으로 인증을 마쳤습니다.",Toast.LENGTH_LONG).show();
        finish();
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //   tv_outPut.setText(s);
        }
    }
}
