package com.yz.project.OriginReader;

import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.dianru.sdk.AdLoader;
import com.yz.project.OriginReader.daqindiguo.R;
import com.yz.project.OriginReader.util.PreferencesUtil;
import com.yz.project.OriginReader.util.ReadUtil;
import com.yz.project.OriginReader.widget.ProgressView;
import com.yz.project.OriginReader.widget.ReadView;

public class OriginReaderActivity extends YZActivity {
	private ReadView rv;
	
	ReadUtil mReadUtil;
	
	private int mLine;

	private String mFileName;
	
	private AudioManager mAudioManager;
	
	private ProgressView mProgressView;
	
	private long mFileLen;;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        
        rv = (ReadView) findViewById(R.id.tv);
        mProgressView = (ProgressView) findViewById(R.id.pv);
        
        int w = getIntent().getIntExtra("w",getWindowManager().getDefaultDisplay().getWidth()) - rv.getPaddingLeft() - rv.getPaddingRight();
        int h = getIntent().getIntExtra("h",getWindowManager().getDefaultDisplay().getHeight()) - rv.getPaddingBottom() - rv.getPaddingTop();
        h = (int) (h - getResources().getDimension(R.dimen.ad_height));
        w = w - rv.getLineHeight() / 2;
        mLine = h / rv.getLineHeight();
        
    	mFileName = getIntent().getStringExtra("fileName");
    	long offset = getIntent().getLongExtra("offset", -1);
        
        mReadUtil = new ReadUtil(this, mFileName, rv.getPaint(), w);
        try {
			AssetFileDescriptor f = getAssets().openFd(mFileName);
			mFileLen = f.getLength();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        if(offset != -1){
        	mReadUtil.setStartOffset(offset);
        }
        
		try {
			setText(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try{
			if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
				setText(true);
			}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
				setText(false);
			}else if(keyCode == KeyEvent.KEYCODE_BACK){
				PreferencesUtil.saveOffset(this, mReadUtil.getStartOffset());
				PreferencesUtil.setFileName(this, mFileName);
				Intent intent = new Intent(this, CatalogActivity.class);
				intent.putExtra("h", getIntent().getIntExtra("h", -1));
				intent.putExtra("w", getIntent().getIntExtra("w", -1));
				startActivity(intent);
				finish();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return true;
	}

	float startX = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			startX = event.getX();
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			float distance = event.getX() - startX;
			try{
				if(distance > 20){
//					rv.setTextList(mReadUtil.getLinesTxt(mLine, false));
					setText(false);
				}else if(distance < -20){
//					rv.setTextList(mReadUtil.getLinesTxt(mLine, true));
					setText(true);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				
		}
		return super.onTouchEvent(event);
	}
	

    @Override
    protected void onDestroy() {
    	AdLoader.destroy();
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    	super.onDestroy();
    }
    private void setText(boolean forward) throws IOException{
		rv.setTextList(mReadUtil.getLinesTxt(mLine,forward));
		System.out.println(mReadUtil.getStartOffset() + "," + mFileLen);
		mProgressView.setPercent((double)mReadUtil.getStartOffset() / (double)mFileLen);
    }
}









