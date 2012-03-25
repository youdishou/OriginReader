package com.yz.project.OriginReader;

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
					Thread.sleep(1000);
					View v = findViewById(R.id.iv_index);
					int width = v.getMeasuredWidth();
					int height = v.getMeasuredHeight();
					Intent intent = new Intent(IndexActivity.this,OriginReaderActivity.class);
					intent.putExtra("h", height);
					intent.putExtra("w", width);
					System.out.println(width+","+height);
					System.out.println(v.getWidth()+","+v.getHeight());
					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
