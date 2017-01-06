package com.rongfeng.speedclient.voice.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.voice.model.VoiceNoteModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.sql.Date;
import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class VoiceNoteAdapter extends BaseRecyclerAdapter<VoiceNoteModel> {

    private String keyWords;
    private static OnItemClickViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnItemClickViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public VoiceNoteAdapter(Context context, int layoutResId, List<VoiceNoteModel> data) {
        super(context, layoutResId, data);
    }

    public void setKeyWords(String keyWords) {

        this.keyWords = keyWords;
    }

    public VoiceNoteAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, VoiceNoteModel model, final int position) {

        LinearLayout rootLayout = holder.getView(R.id.root_layout);
        rootLayout.setTag(model);
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewListener.onItemClick(position, view.getTag());
            }
        });
        RelativeLayout dataLayout = holder.getView(R.id.data_layout);
        if (!TextUtils.isEmpty(model.getCreateTime())) {
            Date date = DateUtil.strToSQLDate(model.getCreateTime().split(" ")[0]);
            if (DateUtils.isToday(date.getTime())) {
                holder.setText(R.id.data_tv, "今天");
            } else {
                holder.setText(R.id.data_tv, model.getCreateTime().split(" ")[0]);
            }
            holder.setText(R.id.time_tv, model.getCreateTime().split(" ")[1]);


            if (position != 0 && !TextUtils.isEmpty(data.get(position - 1).getCreateTime())) {
                if (data.get(position).getCreateTime().split(" ")[0].equals(data.get(position - 1).getCreateTime().split(" ")[0])) {
                    dataLayout.setVisibility(View.GONE);
                } else {
                    dataLayout.setVisibility(View.VISIBLE);
                }
            }
            if (position == 0) {
                dataLayout.setVisibility(View.VISIBLE);
            }

        }

        TextView contentTv = holder.getView(R.id.content_tv);
        if (TextUtils.isEmpty(keyWords)) {
            contentTv.setText(model.getNoteContent());
        } else {
            SpannableString ss = new SpannableString(model.getNoteContent());
            int pos = model.getNoteContent().indexOf(keyWords);
            if (pos != -1) {
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AppConfig.getContext(), R.color.colorBlue)), pos, pos + keyWords.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentTv.setText(ss);
            }
        }


    }

}
