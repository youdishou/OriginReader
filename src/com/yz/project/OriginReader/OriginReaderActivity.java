package com.yz.project.OriginReader;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
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
        
        try {
			tv.setText(mReadUtil.getTxt(true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try{
			if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
				tv.setText(mReadUtil.getTxt(true));
			}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
				tv.setText(mReadUtil.getTxt(false));
			}else if(keyCode == KeyEvent.KEYCODE_BACK){
				finish();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	
}









