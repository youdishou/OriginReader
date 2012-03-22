package com.yz.project.OriginReader;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.yz.project.OriginReader.util.ReadUtil;
import com.yz.project.OriginReader.widget.ReadView;

public class OriginReaderActivity extends Activity {
	private ReadView tv;
	
	ReadUtil mReadUtil;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (ReadView) findViewById(R.id.tv);
        mReadUtil = new ReadUtil(this, tv.getPaint(),0);
        
        try {
			tv.setText(mReadUtil.getTxt(true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
        System.out.println(getWindow().findViewById(Window.ID_ANDROID_CONTENT).getMeasuredHeight());
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









