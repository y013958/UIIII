package com.study.android.mytry.search;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.study.android.mytry.R;
import com.study.android.mytry.login.Login;
import com.study.android.mytry.login.Password;

public class guide extends AppCompatActivity{
    private static final String TAG = "lecture";
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_guide);


        fab = findViewById(R.id.loginfab1);
        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(guide.this, Login.class);
                startActivity(intent); // 다음화면으로 넘어가기
            }
        });
    }

}