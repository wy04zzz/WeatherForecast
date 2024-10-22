package com.gangan.weather.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.gangan.weather.MainActivity;
import com.gangan.weather.databinding.ItemWeather1hBinding;
import com.gangan.weather.http.api.WeatherApi;

import java.util.List;

public class MyWeather1HListRecyclerAdapter extends RecyclerView.Adapter<MyWeather1HListRecyclerAdapter.ItemView>{
    private List<WeatherApi.Bean.Forecast1hBean> list;
    Context context;
    private OnItemClickListener onItemClickListener;
    // 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }
    // 定义一个设置点击监听器的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public MyWeather1HListRecyclerAdapter(List<WeatherApi.Bean.Forecast1hBean> list, Context context) {
        this.list=list;
        this.context=context;
    }

    public void updateData(List<WeatherApi.Bean.Forecast1hBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemWeather1hBinding binding = ItemWeather1hBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ItemView(binding);
    }

    @Override
    public void onBindViewHolder(final ItemView itemView, int position) {

        WeatherApi.Bean.Forecast1hBean info = list.get(position);

        itemView.itemListBinding.tvDegree.setText(info.getDegree()+"°");
        itemView.itemListBinding.tvTime.setText(info.getUpdate_time().substring(8,10)+":00");
        Glide.with(context)
                .load(info.getWeather_url())
                .into(itemView.itemListBinding.ivPic);

        itemView.itemListBinding.llBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    int pos = itemView.getLayoutPosition();
                    onItemClickListener.onItemClick(itemView.itemView, pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder
    public static class ItemView extends  RecyclerView.ViewHolder{

        ItemWeather1hBinding itemListBinding;
        public ItemView(ItemWeather1hBinding itemListBinding){
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
        }


    }

}