package com.study.android.mytry.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationMain;


public class Fragment_Creation extends Fragment implements MainActivity.onKeyBackPressedListener {
    private static final String TAG = "lecture";

    Button public_btn;
    Button private_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.creation_main, container, false);

        // 공개 챌린지 main 화면
        public_btn=rootView.findViewById(R.id.public_challenge_btn);
        public_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //버튼 누르면 새로운 fragement
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);

                fragmentTransaction.replace(R.id.fragment_creation, new com.study.android.mytry.challenge_public.CreationMain()).addToBackStack(null).commit();
              }
        });

        // 비공개 챌린지 main 화면
        private_btn=rootView.findViewById(R.id.private_challenge_btn);
        private_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);

                fragmentTransaction.replace(R.id.fragment_creation, new com.study.android.mytry.challenge_private.CreationMain()).addToBackStack(null).commit();
            }
        });
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //메인뷰 액티비티의 뒤로가기 callback 붙이기
        ((MainActivity)context).setOnKeyBackPressedListener(this);

    }

    @Override
    public void onBackKey() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        //액티비티의 콜백을 직접호출
        activity.onBackPressed();
    }
}
