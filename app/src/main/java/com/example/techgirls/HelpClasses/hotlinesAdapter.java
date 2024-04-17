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
import com.google.firebase.installations.Utils;

import java.util.List;

public class hotlinesAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<hotlinesModel> dataHotlines;

    public hotlinesAdapter(List<hotlinesModel> dataHotlines) {
        this.dataHotlines = dataHotlines;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(dataHotlines.get(position).getOrgName());
        holder.phones.setText(dataHotlines.get(position).getPhones());
        holder.auditoria.setText(dataHotlines.get(position).getAuditoria());
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

    @Override
    public int getItemCount() {
        return dataHotlines.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title, phones, auditoria, siteLink, socialLinks, telegramLink;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.orgName);
        phones=itemView.findViewById(R.id.phones);
        auditoria=itemView.findViewById(R.id.auditoria);
        siteLink=itemView.findViewById(R.id.site_link);
        socialLinks=itemView.findViewById(R.id.social_link);
        telegramLink=itemView.findViewById(R.id.telegram_link);
    }
}