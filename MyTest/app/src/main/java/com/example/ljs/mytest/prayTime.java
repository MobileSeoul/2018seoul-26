package com.example.ljs.mytest;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ljs.mytest.Adapter.ListViewAdapter;

public class prayTime extends AppCompatActivity {
TextView sampleTextView;
    private ListView listview;
    private ListViewAdapter adapter;
    private int[] img={R.drawable.fajr,R.drawable.beach_sunset,R.drawable.duhr2,R.drawable.prayman,
    R.drawable.sunset,R.drawable.praywoman,R.drawable.prayman2,R.drawable.praywoman2,R.drawable.night};
    private String[] Title={"Fajr","Sunrise","Dhuhr","Asr","Sunset","Maghrib","Isha","Imsak","Midnight"};;
    private String[] Context;
    prayerAPIVO prayer= new prayerAPIVO();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context = new String[9];
        setContentView(R.layout.activity_pray_time);

        adapter = new ListViewAdapter();
        listview =(ListView) findViewById(R.id.pray_List);
        listview.setAdapter(adapter);


        for(int i=0;i<Context.length;i++)
        {
            Context[i]=prayer.getPrayinList().get(0).get(Title[i]);
        }
        for(int i=0; i<img.length;i++)
        {

            adapter.addVO(ContextCompat.getDrawable(this,img[i]),Title[i],Context[i]);
        }
    }
}
