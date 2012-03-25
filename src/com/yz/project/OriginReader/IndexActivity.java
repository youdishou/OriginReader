package com.yz.project.OriginReader;

import com.yz.project.OriginReader.util.PreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IndexActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					View v = findViewById(R.id.iv_index);
					int width = v.getMeasuredWidth();
					int height = v.getMeasuredHeight();
					Intent intent = new Intent();
					intent.putExtra("h", height);
					intent.putExtra("w", width);
					
					String fileName = PreferencesUtil.getFileName(IndexActivity.this);
					long offset = PreferencesUtil.getOffset(IndexActivity.this);
					
					if(offset == -1 || fileName == null){
						intent.setClass(IndexActivity.this, CatalogActivity.class);
					}else{
						intent.putExtra("offset", offset);
						intent.putExtra("fileName", fileName);
						intent.setClass(IndexActivity.this, OriginReaderActivity.class);
					}
					
					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
