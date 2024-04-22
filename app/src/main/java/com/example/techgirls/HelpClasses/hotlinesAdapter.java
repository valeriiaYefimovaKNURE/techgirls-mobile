package com.example.techgirls.HelpClasses;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techgirls.Models.hotlinesModel;
import com.example.techgirls.R;

import java.util.List;
/**
 * Adapter class for displaying hotlines information in a RecyclerView (hotlines).
 */
public class hotlinesAdapter extends RecyclerView.Adapter<MyViewHolder> {
    /** List of hotlines data to display. */
    private final List<hotlinesModel> dataHotlines;
    /**
     * Constructs a new HotlinesAdapter with the provided hotlines data.
     *
     * @param dataHotlines The list of hotlines data.
     */
    public hotlinesAdapter(List<hotlinesModel> dataHotlines) {
        this.dataHotlines = dataHotlines;
    }
    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new MyViewHolder that holds a View.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view);
    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(dataHotlines.get(position).getOrgName());
        if(TextUtils.isEmpty(dataHotlines.get(position).getPhones())){
            holder.phones.setVisibility(View.GONE);
        }
        else{
            holder.phones.setText(dataHotlines.get(position).getPhones());
            holder.phones.setVisibility(View.VISIBLE);
        }
        holder.auditorium.setText(dataHotlines.get(position).getAuditoria());
        if(TextUtils.isEmpty(dataHotlines.get(position).getSiteLink())){
            holder.siteLink.setVisibility(View.GONE);
        }else{
            holder.siteLink.setText(dataHotlines.get(position).getSiteLink());
            holder.siteLink.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(dataHotlines.get(position).getSocialLink())){
            holder.socialLinks.setVisibility(View.GONE);
        }else{
            holder.socialLinks.setText(dataHotlines.get(position).getSocialLink());
            holder.socialLinks.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(dataHotlines.get(position).getTelegram())){
            holder.telegramLink.setVisibility(View.GONE);
        }else{
            holder.telegramLink.setText(dataHotlines.get(position).getTelegram());
            holder.telegramLink.setVisibility(View.VISIBLE);
        }

    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dataHotlines.size();
    }
}
/**
 * ViewHolder class for holding views of hotlines items.
 */
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title, phones, auditorium, siteLink, socialLinks, telegramLink;
    /**
     * Constructs a new MyViewHolder.
     *
     * @param itemView The view held by this ViewHolder.
     */
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.orgName);
        phones=itemView.findViewById(R.id.phones);
        auditorium =itemView.findViewById(R.id.auditoria);
        siteLink=itemView.findViewById(R.id.site_link);
        socialLinks=itemView.findViewById(R.id.social_link);
        telegramLink=itemView.findViewById(R.id.telegram_link);
    }
}