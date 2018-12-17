package com.example.ljs.mytest;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TourActivity extends AppCompatActivity{

    private RecyclerView recyclerView; // 전역변수로 지정

    private List<ImageDTO> imageDTOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food); //리사이클러뷰를 달아준다.
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview);

        //리사이클러뷰 정보 가져옴
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FoodRecyclerViewAdapter foodRecyclerViewAdapter = new FoodRecyclerViewAdapter();
        recyclerView.setAdapter(foodRecyclerViewAdapter);

        //데이터베이스를 읽어온다.
        database.getReference().child("Tour").addValueEventListener(new ValueEventListener() {
            //옵저버 패턴으로 글자 하나씩 바뀔때 마다 클라이언트에 알려주고 수정된 것을 자동으로 새로고침 함.(엄청편함)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                imageDTOs.clear();
                uidLists.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                    String uidKey = snapshot.getKey();
                    imageDTOs.add(imageDTO);
                    uidLists.add(uidKey);
                }

                foodRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    class FoodRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ((CustomViewHolder)holder).textView.setText(imageDTOs.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(imageDTOs.get(position).description);
            Glide.with(holder.itemView.getContext()).load(imageDTOs.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).likeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onLikeClicked(database.getReference().child("Tour").child(uidLists.get(position)));
                }
            });

            if(imageDTOs.get(position).likes.containsKey(auth.getCurrentUser().getUid())){
                ((CustomViewHolder)holder).likeButton.setImageResource(R.drawable.baseline_favorite_red_24);
            }

            else{
                ((CustomViewHolder)holder).likeButton.setImageResource(R.drawable.baseline_favorite_border_black_24);
            }
        }

        private void onLikeClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    ImageDTO imageDTO = mutableData.getValue(ImageDTO.class);
                    if(imageDTO == null){
                        return Transaction.success(mutableData);
                    }

                    if(imageDTO.likes.containsKey(auth.getCurrentUser().getUid())){
                        imageDTO.likeCount = imageDTO.likeCount - 1;
                        //Toast.makeText(FoodActivity.this, "좋아요가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                        imageDTO.likes.remove(auth.getCurrentUser().getUid());

                    } else {
                        imageDTO.likeCount = imageDTO.likeCount + 1;
                        //Toast.makeText(FoodActivity.this, "좋아요가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                        imageDTO.likes.put(auth.getCurrentUser().getUid(),true);

                    }

                    mutableData.setValue(imageDTO);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });
        }

        @Override
        public int getItemCount() {

            return imageDTOs.size();
        }

        //item_food에 있는 item들 가져옴
        private class CustomViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView;
            TextView textView2;
            ImageView likeButton;

            //final int position = recyclerView.getChildAdapterPosition(itemView);

            public CustomViewHolder(final View itemView) {
                super(itemView);


                imageView = itemView.findViewById(R.id.item_imageView);
                textView = itemView.findViewById(R.id.item_textView);
                textView2 = itemView.findViewById(R.id.item_textView2);
                likeButton = itemView.findViewById(R.id.likebutton_imageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    //버튼을 클릭했을 때의 반응, ctrl + space로 객체 찾고
                    @Override
                    public void onClick(View View) {
                        // 리사이클러뷰의 해당 포지션 위치 값 받아오는 부분
                        int position = recyclerView.getChildAdapterPosition(itemView);

                        String dir = "Tour";

                        //위도 경도 값 받아오기
                        Double lat = imageDTOs.get(position).lat;
                        Double lon = imageDTOs.get(position).lon;

                        String url1 = imageDTOs.get(position).subImage1;
                        String url2 = imageDTOs.get(position).subImage2;
                        String url3 = imageDTOs.get(position).subImage3;

                        textView = View.findViewById(R.id.item_textView);
                        String title = textView.getText().toString();

                        textView = View.findViewById(R.id.item_textView);
                        String description = imageDTOs.get(position).description;

                       String Explanation = imageDTOs.get(position).Explanation;
                       String fee = imageDTOs.get(position).fee;
                       String operatingTime = imageDTOs.get(position).operatingTime;
                       String phone = imageDTOs.get(position).phone;

                        Intent intent = new Intent(TourActivity.this,TourDetailActivity.class);

                        intent.putExtra("url1", url1);
                        intent.putExtra("url2", url2);
                        intent.putExtra("url3", url3);
                        intent.putExtra("title", title);
                        intent.putExtra("description", description);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lon",lon);
                        intent.putExtra("dir",dir);
                        intent.putExtra("Explanation", Explanation);
                        intent.putExtra("fee", fee);
                        intent.putExtra("operatingTime", operatingTime);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}