package com.rongfeng.speedclient.voice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 语音
 * 2016/1/13
 */
public class VoiceFragment extends BaseFragment {


    @Bind(R.id.note_tv)
    TextView noteTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_layout, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.note_tv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_tv:
                startActivity(new Intent(getActivity(), VoiceNoteActivity.class));
                break;
        }
    }
}
