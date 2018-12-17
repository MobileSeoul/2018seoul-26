package com.example.ljs.mytest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ljs.mytest.ListVO.ListVO;

import com.example.ljs.mytest.QuranDetail;
import com.example.ljs.mytest.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();
    @Override
    public int getCount() {

        return listVO.size();
    }
    //리스트뷰에 데이터 삽입
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        System.out.println("123124");
        //position = ListView의 위치
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }
            ImageView image = (ImageView)
                    convertView.findViewById(R.id.img);
            TextView title =(TextView)
            convertView.findViewById(R.id.title);
            TextView Context = (TextView)
                    convertView.findViewById(R.id.context);

            ListVO listViewItem = listVO.get(position);
//아이템 내 각 위젯에 데이터 반영
            image.setImageDrawable(listViewItem.getImg());
            title.setText(listViewItem.getTitle());
            Context.setText(listViewItem.getContext());

            //리스트뷰 클릭 이벤트
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view){
                    //pos가 리스트 순서
                    //view.getContext();
                   Intent intent = new Intent(view.getContext(),QuranDetail.class);
                   view.getContext().startActivity(intent);
                    //Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public Object getItem(int position){
        return listVO.get(position);
    }

    public void addVO(Drawable icon, String title, String desc){
    ListVO item =new ListVO();
        item.setImg(icon);
        item.setTitle(title);
        item.setContext(desc);

        listVO.add(item);
    }
}
