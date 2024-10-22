package com.gangan.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.gangan.weather.R;
import com.gangan.weather.bean.FileBean;
import com.gangan.weather.databinding.ItemFileListBinding;
import com.gangan.weather.utils.DateUtils;

import java.util.List;

public class MyFileListRecyclerAdapter extends RecyclerView.Adapter<MyFileListRecyclerAdapter.ItemView>{
    private List<FileBean> list;
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

    public MyFileListRecyclerAdapter(List<FileBean> list, Context context) {
        this.list=list;
        this.context=context;
    }

    public void updateData(List<FileBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemFileListBinding binding = ItemFileListBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ItemView(binding);
    }

    @Override
    public void onBindViewHolder(final ItemView itemView, int position) {
        FileBean info = list.get(position);
        itemView.itemListBinding.tvName.setText(info.getName());
        itemView.itemListBinding.tvTime.setText(DateUtils.formatTime((int) (info.getSize()/1000)));
        itemView.itemListBinding.ivLogo.setImageDrawable(context.getDrawable(R.drawable.ic_audio));

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
    public static class ItemView extends RecyclerView.ViewHolder{

        ItemFileListBinding itemListBinding;
        public ItemView(ItemFileListBinding itemListBinding){
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
        }


    }

}