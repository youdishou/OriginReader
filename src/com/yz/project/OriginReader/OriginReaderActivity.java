package com.yz.project.OriginReader;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yz.project.OriginReader.util.ReadUtil;

public class OriginReaderActivity extends Activity {
	private TextView tv;
	
	ReadUtil mReadUtil;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        mReadUtil = new ReadUtil(this, tv.getPaint(),0);
        
        tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					tv.setText(mReadUtil.getNextTxt());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
        
        try {
			tv.setText(mReadUtil.getNextTxt());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}









