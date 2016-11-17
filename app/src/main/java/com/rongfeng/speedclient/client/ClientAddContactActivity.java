package com.rongfeng.speedclient.client;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.ButterKnife;


/**
 * 添加联系人
 */
public class ClientAddContactActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_contact_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

    }


    /**
     * 切换客户类型
     *
     * @param currentTv
     * @param currentIv
     * @param targetTv
     * @param targetIv
     */

    private void switchClientStatus(TextView currentTv, ImageView currentIv, TextView targetTv, ImageView targetIv) {
        targetTv.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        targetTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        targetIv.setImageResource(R.drawable.addcustomer_type_select);

        currentIv.setImageResource(R.drawable.addcustomer_type);
        currentTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssistLight));
        currentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    }

    /**
     * 切换动画效果
     *
     * @param view
     * @param alpha
     * @param startScaleX
     * @param startScaleY
     * @param duration
     */
    private void fadeInView(View view, float alpha, float startScaleX, float startScaleY, int duration) {
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", alpha, 1f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", startScaleX, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", startScaleY, 1f);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorAlpha).with(animatorScaleX).with(animatorScaleY);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }


}
