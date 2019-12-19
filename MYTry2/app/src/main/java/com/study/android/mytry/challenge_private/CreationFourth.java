package com.study.android.mytry.challenge_private;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.CreationPage;
import com.study.android.mytry.main.MainActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

public class CreationFourth extends AppCompatActivity {

    HashMap<String, String> map;

    EditText start_time;
    EditText end_time;

    EditText entry_fee;

    public static CreationFourth creationFourth;
    private int CREATION_FINISH = 2;
    private static final int PRIVATE_CREATION_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_fourth);

        Intent intent = getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        start_time = (EditText) findViewById(R.id.start_time);
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreationFourth.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        start_time.setText(selectedHour + "시" + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Start Time");
                mTimePicker.show();
            }
        });

        end_time = (EditText) findViewById(R.id.end_time);
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreationFourth.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        end_time.setText(selectedHour + "시" + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("End Time");
                mTimePicker.show();
            }
        });

        entry_fee = (EditText) findViewById(R.id.entry_fee);

        creationFourth = CreationFourth.this;
    }

    public void onFourthNext(View v) {
        Intent intent = new Intent(getApplicationContext(), CreationFifth.class);

        map.put("time", start_time.getText().toString() + "-" + end_time.getText().toString());
        map.put("fee", entry_fee.getText().toString());

        intent.putExtra("hashmap", map);
        startActivityForResult(intent, CREATION_FINISH);
    }

    public void onBackPressed(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("yejin", String.valueOf(requestCode));
        if (requestCode == PRIVATE_CREATION_FINISH) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(CreationFourth.this, CreationThrid.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
