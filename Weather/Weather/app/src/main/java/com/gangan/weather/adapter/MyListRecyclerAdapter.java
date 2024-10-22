package com.gangan.weather.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.gangan.weather.bean.EventDataBean;
import com.gangan.weather.databinding.ItemListBinding;
import com.gangan.weather.utils.Constant;
import com.gangan.weather.utils.DateUtils;

import java.util.List;

public class MyListRecyclerAdapter extends RecyclerView.Adapter<MyListRecyclerAdapter.ItemView>{
    private List<EventDataBean> list;
    Context context;
    private OnItemClickListener onItemClickListener;
    // 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    // 定义一个设置点击监听器的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public MyListRecyclerAdapter(List<EventDataBean> list, Context context) {
        this.list=list;
        this.context=context;
    }

    public void updateData(List<EventDataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ItemView(binding);
    }

    @Override
    public void onBindViewHolder(final ItemView itemView, int position) {

        EventDataBean info = list.get(position);
        itemView.itemListBinding.tvTitle.setText(Constant.scheduleStr[info.getClassify()]);

        itemView.itemListBinding.tvDescribe.setVisibility(TextUtils.isEmpty(info.getDescribe())?View.GONE:View.VISIBLE);
        itemView.itemListBinding.tvDescribe.setText(info.getDescribe());
        itemView.itemListBinding.tvTime.setText(DateUtils.getFormatHHmmss(info.getTime()));

        itemView.itemListBinding.llBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    int pos = itemView.getLayoutPosition();
                    onItemClickListener.onItemClick(itemView.itemView, pos);
                }
            }
        });
        itemView.itemListBinding.llBg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onItemClickListener != null) {
                    int pos = itemView.getLayoutPosition();
                    onItemClickListener.onItemLongClick(itemView.itemView, pos);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder
    public static class ItemView extends  RecyclerView.ViewHolder{

        ItemListBinding itemListBinding;
        public ItemView(ItemListBinding itemListBinding){
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
        }


    }

}