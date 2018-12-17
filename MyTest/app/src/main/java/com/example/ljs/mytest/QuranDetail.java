package com.example.ljs.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ljs.mytest.Adapter.ListViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuranDetail extends BaseActivity {
    private ListView listview;
    ArrayAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<String> QuranDTO = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quran);
        //adapter = new ListViewAdapter();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, QuranDTO);

        listview =(ListView) findViewById(R.id.List_view);
        listview.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String Quran = intent.getStringExtra("Quran");
        String string = intent.getStringExtra("string");

//        for(int i=0; i<img.length;i++)
//        {
//            adapter.addVO(ContextCompat.getDrawable(this,img[i]),Title[i],Context[i]);
//        }

        databaseReference = database.getReference().child("Quran").child(string);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QuranDTO.clear();

                int i=0;
                for(DataSnapshot quranData : dataSnapshot.getChildren()) {
                    String value = quranData.getValue().toString();
                    QuranDTO.add(value);


                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
}
