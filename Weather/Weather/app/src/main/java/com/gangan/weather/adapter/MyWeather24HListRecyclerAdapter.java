package com.gangan.weather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gangan.weather.databinding.ItemWeather1hBinding;
import com.gangan.weather.databinding.ItemWeather24hBinding;
import com.gangan.weather.http.api.WeatherApi;
import com.gangan.weather.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class MyWeather24HListRecyclerAdapter extends RecyclerView.Adapter<MyWeather24HListRecyclerAdapter.ItemView>{
    private List<WeatherApi.Bean.Forecast24hBeanX> list;
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

    public MyWeather24HListRecyclerAdapter(List<WeatherApi.Bean.Forecast24hBeanX> list, Context context) {
        this.list=list;
        this.context=context;
    }

    public void updateData(List<WeatherApi.Bean.Forecast24hBeanX> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemWeather24hBinding binding = ItemWeather24hBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ItemView(binding);
    }

    @Override
    public void onBindViewHolder(final ItemView itemView, int position) {

        WeatherApi.Bean.Forecast24hBeanX info = list.get(position);

        if (position == 0) {
            itemView.itemListBinding.tvTimeWeek.setText("昨天");
        } else if (position == 1) {
            itemView.itemListBinding.tvTimeWeek.setText("今天");
        } else {
            itemView.itemListBinding.tvTimeWeek.setText(DateUtils.getDayOfWeek(info.getTime()));
        }
        String date = info.getTime().replace("-", ".");
        itemView.itemListBinding.tvTimeDate.setText(date.substring(5,10));

        itemView.itemListBinding.tvAqi.setText(info.getAqi_name());
        itemView.itemListBinding.tvAqi.getShapeDrawableBuilder().setSolidColor(Color.parseColor(info.getAqi_level()==1?"#ff00CD66":info.getAqi_level()==2?"#ffFFCC33":"#ff99001A")).intoBackground();
        Glide.with(context)
                .load(info.getDay_weather_url())
                .into(itemView.itemListBinding.ivWeather);
        itemView.itemListBinding.tvWeather.setText(info.getDay_weather());
        itemView.itemListBinding.tvTemp.setText(info.getMax_degree()+"°/"+info.getMin_degree()+"°C");

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

        ItemWeather24hBinding itemListBinding;
        public ItemView(ItemWeather24hBinding itemListBinding){
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
        }


    }

}