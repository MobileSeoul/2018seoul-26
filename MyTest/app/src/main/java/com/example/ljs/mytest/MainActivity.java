package com.example.ljs.mytest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {

    HttpURLConnection urlConnection = null;
    URL url=null;
    private List<HashMap<String,String>> prayinList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        //HomeFragment tf = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        //View homeview = (View) getLayoutInflater().inflate(R.layout.fragment_home,null);

        prayinList = new ArrayList<HashMap<String,String>>();

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        new Thread(){
            prayerAPIVO prayer = new prayerAPIVO();
            String urldetail = "https://api.aladhan.com/timingsByAddress/" +
                    prayer.getCurrent_date() +
                    "?address=" +
                    prayer.getLocation() +
                    "&method=8&tune=2,3,4,5,2,3,4,5,-3";
            public void run(){
                String result;

                try {

                    url= new URL(urldetail);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    String line;
                    StringBuilder sb = new StringBuilder();

                    // 라인을 받아와 합친다.
                    while ((line = reader.readLine()) != null){
                        sb.append(line);
                    }

                    reader.close();
                    result = sb.toString().trim();
                }
                catch(Exception e){
                    e.printStackTrace();
                    result = e.toString();

                }finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
                if (jsonParser(result,prayer)){

                    Message message = mHandler.obtainMessage(101);
                    mHandler.sendMessage(message);
                }
            }
        }.start();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();



    }
    private final MainActivity.MyHandler mHandler = new MainActivity.MyHandler(this);
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    item.setChecked(true);
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_quran:
                    item.setChecked(true);

                    selectedFragment = new QuranFragment();
                    break;
                case R.id.navigation_compass:
                    item.setChecked(true);
                    selectedFragment = new CompassFragment();
                    break;
                case R.id.navigation_user:
                    item.setChecked(true);
                    selectedFragment = new UserFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }

    };



    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity mainactivity) {
            weakReference = new WeakReference<MainActivity>(mainactivity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainActivity mainactivity = weakReference.get();

            if (mainactivity != null) {
                switch (msg.what) {

                    case 101:

                        //mainactivity.progressDialog.dismiss();
                        //mainactivity.adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    }
    public boolean jsonParser(String jsonString,prayerAPIVO prayer){


        if (jsonString == null ) return false;
        try {
            HashMap<String, String> prayTimeKeyValue = new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.get("status").toString();
            if(status.equals("OK")){
                JSONObject J_Data = jsonObject.getJSONObject("data");
                JSONObject j_timings = J_Data.getJSONObject("timings");
                for(int i = 0; i < j_timings.names().length(); i++){
                    prayTimeKeyValue.put(j_timings.names().get(i).toString(),j_timings.get(j_timings.names().get(i).toString()).toString().toString());
                }
                prayinList.clear();
                prayinList.add(prayTimeKeyValue);
                prayer.setprayerTime(prayinList);
                if(!prayer.getPrayinList().isEmpty())
                    Log.d("godgod",prayer.getPrayinList().get(0).get("Fajr"));

            }else{

            }

            return true;
        } catch (JSONException e) {

            Log.d("apitest", e.toString() );
        }
        return false;
    }
}