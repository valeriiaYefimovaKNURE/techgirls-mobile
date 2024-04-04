package com.example.techgirls.HelpClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.techgirls.Models.NewsData;
import com.example.techgirls.R;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    private ArrayList<NewsData> dataList;

    private Context context;
    LayoutInflater layoutInflater;

    public NewsAdapter(ArrayList<NewsData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(layoutInflater==null){
            layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view==null){
            view=layoutInflater.inflate(R.layout.grid_item,null);
        }
        ImageView gridImage=view.findViewById(R.id.gridImage);
        TextView gridTitle=view.findViewById(R.id.gridTitle);
        TextView gridCaption=view.findViewById(R.id.gridCaption);
        Glide.with(context).load(dataList.get(position).getDataImage()).into(gridImage);
        gridTitle.setText(dataList.get(position).getDataTitle());
        gridCaption.setText(dataList.get(position).getDataCaption());


        return view;
    }
}
