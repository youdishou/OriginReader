package com.yz.project.OriginReader;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.yz.project.OriginReader.constant.Constant;
import com.yz.project.OriginReader.util.PreferencesUtil;
import com.yz.project.OriginReader.util.ReadUtil;
import com.yz.project.OriginReader.widget.ReadView;

public class OriginReaderActivity extends Activity {
	private ReadView rv;
	
	ReadUtil mReadUtil;
	
	private int mLine;

	private String mFileName;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        rv = (ReadView) findViewById(R.id.tv);
        
        int w = getIntent().getIntExtra("w",getWindowManager().getDefaultDisplay().getWidth()) - rv.getPaddingLeft() - rv.getPaddingRight();
        int h = getIntent().getIntExtra("h",getWindowManager().getDefaultDisplay().getHeight()) - rv.getPaddingBottom() - rv.getPaddingTop();
        
        w = w - rv.getLineHeight() / 2;
        mLine = h / rv.getLineHeight();
        
    	mFileName = getIntent().getStringExtra("fileName");
    	long offset = getIntent().getIntExtra("offset", -1);
        
        mReadUtil = new ReadUtil(this, mFileName, rv.getPaint(), w);
        
        if(offset != -1){
        	mReadUtil.setStartOffset(offset);
        }
        
		try {

			List<String> tmp = mReadUtil.getLinesTxt(mLine, true);
			rv.setTextList(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try{
			if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
				List<String> tmp = mReadUtil.getLinesTxt(mLine, true);
				rv.setTextList(tmp);
			}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
				List<String> tmp = mReadUtil.getLinesTxt(mLine, false);
				rv.setTextList(tmp);
			}else if(keyCode == KeyEvent.KEYCODE_BACK){
				PreferencesUtil.saveOffset(this, mReadUtil.getStartOffset());
				PreferencesUtil.setFileName(this, mFileName);
				finish();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
}









