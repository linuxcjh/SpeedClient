package com.rongfeng.speedclient.selectpicture;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.selectpicture.model.ImageFloder;

import java.util.List;

public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFloder> {
    private ListView mListDir;
    private SelectPicturesAdapter selectPicturesAdapter;

    public ListImageDirPopupWindow(int width, int height,
                                   List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);

        selectPicturesAdapter = new SelectPicturesAdapter(context, R.layout.list_dir_item, mDatas);
        mListDir.setAdapter(selectPicturesAdapter);

    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder, int position);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (mImageDirSelected != null) {
                    selectPicturesAdapter.setPosition(position);
                    mImageDirSelected.selected(mDatas.get(position),position);
                }
            }
        });
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
        // TODO Auto-generated method stub
    }

}

class SelectPicturesAdapter extends QuickAdapter<ImageFloder> {
    private int pos;

    public SelectPicturesAdapter(Context context, int layoutResId, List<ImageFloder> data) {
        super(context, layoutResId, data);
    }

    public void setPosition(int position) {
        this.pos = position;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ImageFloder item, int position) {
        helper.setText(R.id.id_dir_item_name, item.getName().replace("/", ""));
        ImageView imageView = helper.getView(R.id.id_dir_item_image);
        ImageView imageViewTip = helper.getView(R.id.tips_iv);
        Glide.with(context).load(item.getFirstImagePath()).placeholder(R.drawable.photo_none).error(R.drawable.photo_none).into(imageView);

        helper.setText(R.id.id_dir_item_count, item.getCount() + "张");

        if (position == pos) {
            imageViewTip.setVisibility(View.VISIBLE);
        } else {
            imageViewTip.setVisibility(View.GONE);
        }
    }
}
