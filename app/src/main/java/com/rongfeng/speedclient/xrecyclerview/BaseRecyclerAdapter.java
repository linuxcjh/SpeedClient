package com.rongfeng.speedclient.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder>{

    protected final Context context;

    protected final int layoutResId;

    protected List<T> data;

    private  OnItemClickViewListener onItemClickViewListener;

    public void setOnRecyclerViewListener(OnItemClickViewListener onItemClickViewListener) {
        this.onItemClickViewListener = onItemClickViewListener;
    }

    public BaseRecyclerAdapter(Context context, int layoutResId, List<T> data) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(layoutResId, parent, false);
        ViewHolder viewHolder =new ViewHolder(context,convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickViewListener!=null)
                    onItemClickViewListener.onItemClick(position,data.get(position));
            }
        });
        convert(holder,data.get(position),position);
    }


    protected abstract void convert(ViewHolder helper, T item,int position);


}
