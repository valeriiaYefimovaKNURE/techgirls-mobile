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
/**
 * Adapter class for displaying news items in a GridView (Publications).
 */
public class NewsAdapter extends BaseAdapter {

    /** List of news data to display. */
    private final ArrayList<NewsData> dataList;

    /** Context of the application. */
    private final Context context;

    /** LayoutInflater to inflate views. */
    LayoutInflater layoutInflater;

    /**
     * Constructs a new NewsAdapter with the provided news data and context.
     *
     * @param dataList The list of news data.
     * @param context The context of the application.
     */
    public NewsAdapter(ArrayList<NewsData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    /**
     * Returns the number of items in the adapter's data set.
     *
     * @return The number of items in the adapter's data set.
     */
    @Override
    public int getCount() {
        return dataList.size();
    }

    /**
     * Returns the data item at the specified position in the adapter's data set.
     *
     * @param position The position of the item within the adapter's data set.
     * @return The data item at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Returns the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set.
     * @return The row id associated with the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Returns a View for each news item in the GridView.
     *
     * @param position The position of the item within the adapter's data set.
     * @param view The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
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
