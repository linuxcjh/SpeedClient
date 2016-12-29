package com.rongfeng.speedclient.mine.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class MineTargetAdapter extends BaseRecyclerAdapter<MineTargetModel> {

    private boolean isEdit;


    public MineTargetAdapter(Context context, int layoutResId, List<MineTargetModel> data, boolean isEdit) {
        super(context, layoutResId, data);
        this.isEdit = isEdit;
    }

    public MineTargetAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, MineTargetModel model, final int position) {

        holder.setText(R.id.left_tv, model.getTargetMonth());
        final EditText editText = holder.getView(R.id.sales_target_tv);
        editText.setTag(position);
        if (!TextUtils.isEmpty(model.getMonthTarget())) {
            editText.setText(model.getMonthTarget());
        } else {
            editText.setText("");
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.get((int) editText.getTag()).setMonthTarget(s.toString());
            }
        });

    }

}
