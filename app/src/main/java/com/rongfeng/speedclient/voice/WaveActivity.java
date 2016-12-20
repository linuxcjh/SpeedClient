package com.rongfeng.speedclient.voice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.components.WaveDrawable;

public class WaveActivity extends AppCompatActivity {

    private ImageView mImageView;
    private WaveDrawable mWaveDrawable;
    private SeekBar mLevelSeekBar;
    private SeekBar mAmplitudeSeekBar;
    private SeekBar mSpeedSeekBar;
    private SeekBar mLengthSeekBar;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_layout);

        mImageView = (ImageView) findViewById(R.id.image);
        mWaveDrawable = new WaveDrawable(this, R.drawable.nav_bg);

        mImageView.setImageDrawable(mWaveDrawable);

        mLevelSeekBar = (SeekBar) findViewById(R.id.level_seek);
        mLevelSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWaveDrawable.setLevel(progress);
                Log.d("wave","Level-- "+progress+"");
            }
        });
//
        mAmplitudeSeekBar = (SeekBar) findViewById(R.id.amplitude_seek);
        mAmplitudeSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWaveDrawable.setWaveAmplitude(progress);
                Log.d("wave","Amplitude-- "+progress+"");

            }
        });
//
        mLengthSeekBar = (SeekBar) findViewById(R.id.length_seek);
        mLengthSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWaveDrawable.setWaveLength(progress);
                Log.d("wave","Length-- "+progress+"");

            }
        });
//
        mSpeedSeekBar = (SeekBar) findViewById(R.id.speed_seek);
        mSpeedSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWaveDrawable.setWaveSpeed(progress);
                Log.d("wave","Speed-- "+progress+"");

            }
        });
//
        mRadioGroup = (RadioGroup) findViewById(R.id.modes);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final boolean indeterminate = checkedId == R.id.rb_yes;
                setIndeterminateMode(indeterminate);
            }
        });
        setIndeterminateMode(mRadioGroup.getCheckedRadioButtonId() == R.id.rb_yes);

//        ImageView imageView2 = (ImageView) findViewById(R.id.image2);
//        WaveDrawable chromeWave = new WaveDrawable(this, R.drawable.chrome_logo);
//        imageView2.setImageDrawable(chromeWave);
//
//        // Set customised animator here
////        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
////        animator.setRepeatMode(ValueAnimator.REVERSE);
////        animator.setRepeatCount(ValueAnimator.INFINITE);
////        animator.setDuration(4000);
////        animator.setInterpolator(new AccelerateDecelerateInterpolator());
////        chromeWave.setIndeterminateAnimator(animator);
//        chromeWave.setIndeterminate(true);
//
//        View view = findViewById(R.id.view);
//        int color = getResources().getColor(R.color.colorAccent);
//        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(color));
//        view.setBackground(colorWave);
//        colorWave.setIndeterminate(true);

    }

    private void setIndeterminateMode(boolean indeterminate) {
        mWaveDrawable.setIndeterminate(indeterminate);
        mLevelSeekBar.setEnabled(!indeterminate);

        if (!indeterminate) {
            mWaveDrawable.setLevel(mLevelSeekBar.getProgress());
        }
        mWaveDrawable.setWaveAmplitude(mAmplitudeSeekBar.getProgress());
        mWaveDrawable.setWaveLength(mLengthSeekBar.getProgress());
        mWaveDrawable.setWaveSpeed(mSpeedSeekBar.getProgress());
    }

    private static class SimpleOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Nothing
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Nothing
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Nothing
        }
    }
}
