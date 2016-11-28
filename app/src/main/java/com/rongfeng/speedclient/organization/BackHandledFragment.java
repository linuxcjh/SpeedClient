package com.rongfeng.speedclient.organization;

import android.os.Bundle;
import android.support.v4.app.Fragment;


public abstract class BackHandledFragment extends Fragment {

	protected BackHandledInterface mBackHandledInterface;
	
	/**
	 * ���м̳�BackHandledFragment�����඼�������������ʵ������Back�����º���߼�
	 * FragmentActivity��׽�������ؼ�����¼��������ѯ��Fragment�Ƿ����Ѹ��¼�
	 * ���û��Fragment��ϢʱFragmentActivity�Լ��Ż����Ѹ��¼�
	 */
	protected abstract boolean onBackPressed();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!(getActivity() instanceof BackHandledInterface)){
			throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
		}else{
			this.mBackHandledInterface = (BackHandledInterface)getActivity();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//����FragmentActivity����ǰFragment��ջ��
		mBackHandledInterface.setSelectedFragment(this);
	}
	
}
