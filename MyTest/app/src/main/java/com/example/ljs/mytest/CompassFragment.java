package com.example.ljs.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CompassFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent=new Intent(getActivity(),CompassActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
        return inflater.inflate(R.layout.fragment_compass, container, false);

    }


}
