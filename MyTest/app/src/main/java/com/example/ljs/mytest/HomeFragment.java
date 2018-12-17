package com.example.ljs.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
    prayerAPIVO prayer= new prayerAPIVO();
    TextView textView;
    Handler mHandler = null;
    ImageView imageView_food;
    ImageView imageView_mosque;
    ImageView imageView_facilities;
    ImageView imageView_seoul;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.TimeText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prayer.getCheckflag()==1) {
                    startActivity(new Intent(getContext(), prayTime.class));
                }
            }
        });


        imageView_food = (ImageView) view.findViewById(R.id.imageView1);
        imageView_mosque = (ImageView) view.findViewById(R.id.imageView2);
        imageView_facilities = (ImageView) view.findViewById(R.id.imageView3);
        imageView_seoul = (ImageView) view.findViewById(R.id.imageView4);
        imageView_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FoodActivity.class));
            }
        });
        imageView_mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PrayerActivity.class));
            }
        });
        imageView_facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FacilitiesActivity.class));
            }
        });
        imageView_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TourActivity.class));
            }
        });
        mHandler = new Handler();
        textView.setText(getCurrentTime());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {

                }
                if(getActivity() == null) return;
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {

                       try{
                           textView.setText(getCurrentTime());

                       }
                       catch(Exception e){

                       }
                   }
               });
            }}
        });
        t.start();

        // Inflate the layout for this fragment
        //textView.setText(getCurrentTime());
       // System.out.println();
        return view;
    }
    private String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("HH:mm");
        final String str = dayTime.format(new Date(time));
        return str;
    }

}

