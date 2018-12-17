package com.example.ljs.mytest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class QuranFragment extends Fragment {

    private ListView listview;
//    private ListViewAdapter adapter;
    ArrayAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private List<String> QuranDTO = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quran,container,false);

        final String Quran = "Quran";
        database = FirebaseDatabase.getInstance();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1, QuranDTO);

        listview = view.findViewById(R.id.List_view);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = adapter.getItem(i).toString();
                Intent intent= new Intent(getActivity(),QuranDetail.class);
                intent.putExtra("string",string);
                intent.putExtra("Quran",Quran);
                startActivity(intent);
            }
        });

        databaseReference = database.getReference().child("Quran");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QuranDTO.clear();

                for(DataSnapshot quranData : dataSnapshot.getChildren()) {
                    String key = quranData.getKey();
                    QuranDTO.add(key);


                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        // Inflate the layout for this fragment
        return view;

        //view.setOnClickListener();
    }
}

