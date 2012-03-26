package com.yz.project.OriginReader;

import com.mobclick.android.MobclickAgent;

import android.app.Activity;

public class YZActivity extends Activity{

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
