package com.example.ljs.mytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrayerDetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    private ImageView Detail_imageView1;
    private ImageView Detail_imageView2;
    private ImageView Detail_imageView3;

    private TextView Detail_Title;
    private TextView Detail_Description;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private String Receive_title;

    private TextView Compass;
    private TextView Mat;
    private TextView PrayerCostume;
    private TextView Quran;
    private TextView operatingTime;

    private DatabaseReference databaseReference;
    private ChildEventListener child;
    private ListView text_ListView;
    private EditText review;
    private static Button m_button;
    private String msg;
    private ArrayAdapter<String> text_adapter;
    List<Object> Array_text = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prayer_detail);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        initDatabase();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String getTime = dateFormat.format(date);

        text_ListView = findViewById(R.id.text_listView);
        m_button = findViewById(R.id.transfer);
        review = findViewById(R.id.review);

        text_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, new ArrayList<String>());
        text_ListView.setAdapter(text_adapter);

        //구글 맵
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        //intent 선언
        Intent intent = getIntent();
        String dir = intent.getStringExtra("dir");

        //View 불러오기
        Detail_imageView1 = findViewById(R.id.Detail_imageView1);
        Detail_imageView2 = findViewById(R.id.Detail_imageView2);
        Detail_imageView3 = findViewById(R.id.Detail_imageView3);

        Detail_Title = findViewById(R.id.Detail_Title);
        Detail_Description = findViewById(R.id.Detail_Description);

        //이미지 url 넘겨받아와서 띄우기
        String Receive_url1 = intent.getStringExtra("url1");
        Glide.with(this).load(Receive_url1).into(Detail_imageView1);
        String Receive_url2 = intent.getStringExtra("url2");
        Glide.with(this).load(Receive_url2).into(Detail_imageView2);
        String Receive_url3 = intent.getStringExtra("url3");
        Glide.with(this).load(Receive_url3).into(Detail_imageView3);

        //이미지 title 넘겨받아와서 띄우기
        Receive_title = intent.getStringExtra("title");
        Detail_Title.setText(Receive_title);

        String Receive_Compass = intent.getStringExtra("Compass");
        String Receive_Mat = intent.getStringExtra("Mat");
        String Receive_PrayerCostume = intent.getStringExtra("PrayerCostume");
        String Receive_Quran = intent.getStringExtra("Quran");
        String Receive_operatingTime = intent.getStringExtra("operatingTime");

        String Receive_description = intent.getStringExtra("description");
        Detail_Description.setText(Receive_description);

        Compass = findViewById(R.id.Compass);
        Mat = findViewById(R.id.Mat);
        PrayerCostume = findViewById(R.id.Prayercostume);
        Quran = findViewById(R.id.Quran);
        operatingTime = findViewById(R.id.Operatingtime);

        Compass.setText(Receive_Compass);
        Mat.setText(Receive_Mat);
        PrayerCostume.setText(Receive_PrayerCostume);
        Quran.setText(Receive_Quran);
        operatingTime.setText(Receive_operatingTime);

        m_button.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                msg = review.getText().toString();
                databaseReference.push().setValue(getTime + " " + auth.getCurrentUser().getDisplayName() + " : " + msg);
                review.setText("");
            }
        });

        databaseReference = database.getReference().child(dir).child(Receive_title).child("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //name_adapter.clear();
                text_adapter.clear();

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {

                    String msg2 = messageData.getValue().toString();

                    Array_text.add(msg2);
                    text_adapter.add(msg2);
                }

                text_adapter.notifyDataSetChanged();
                text_ListView.setSelection(text_adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initDatabase() {

        databaseReference = database.getReference().child("Message");

        child = new ChildEventListener(){


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(child);
    }

    @Override
    protected  void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(child);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng PrayerRoom = new LatLng(getIntent().getDoubleExtra("lat",0.0),getIntent().getDoubleExtra("lon",0.0));
        googleMap.addMarker(new MarkerOptions().position(PrayerRoom).title("PrayerRoom"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PrayerRoom,15));
    }
}