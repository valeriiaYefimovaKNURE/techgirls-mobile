package com.example.techgirls.HelpClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.techgirls.Models.GridItem;
import com.example.techgirls.R;

import java.util.ArrayList;
import java.util.Objects;

public class NewsAdapter extends ArrayAdapter<GridItem> {
    public NewsAdapter(@NonNull Context context, ArrayList<GridItem> gridItemArrayList) {
        super(context, 0, gridItemArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent,false);
        }
        GridItem gridItem=getItem(position);
        TextView Title=listItemView.findViewById(R.id.gridTitle);
        TextView Caption=listItemView.findViewById(R.id.gridCaption);
        ImageView image=listItemView.findViewById(R.id.gridImage);

        Title.setText(Objects.requireNonNull(gridItem).getTitle());
        Caption.setText(gridItem.getCaption());
        image.setImageResource(gridItem.getImageID());

        return listItemView;
    }
}
