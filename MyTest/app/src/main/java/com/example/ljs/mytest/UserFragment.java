package com.example.ljs.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class UserFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    private TextView nameTextView;
    private TextView emailTextView;
    private ImageView imageView;
    private Button logout_button;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        auth = FirebaseAuth.getInstance();
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        imageView = view.findViewById(R.id.imageView);


        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(100);
                    }
                    catch(InterruptedException e)
                    {

                    }
                    if(getActivity() == null) return;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try{
                                nameTextView.setText(auth.getCurrentUser().getDisplayName());
                                emailTextView.setText(auth.getCurrentUser().getEmail());
                            }
                            catch(Exception e){

                            }
                        }
                    });
                }}
        });
        t.start();

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if(id == R.id.nav_food){
                startActivity(new Intent(getActivity(), FoodActivity.class));
            }
            else if(id==R.id.nav_pray){
                startActivity(new Intent(getActivity(), PrayerActivity.class));
            }
            else if(id == R.id.nav_fac){
                startActivity(new Intent(getActivity(), FacilitiesActivity.class));
            }
            else if (id == R.id.nav_tour){
                startActivity(new Intent(getActivity(), TourActivity.class));
            }
            else if (id == R.id.nav_logout){
                auth.signOut();
                auth.signOut();
                LoginManager.getInstance().logOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
            return true;

    }
}
