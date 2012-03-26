package com.yz.project.OriginReader;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.dianru.sdk.AdLoader;
import com.yz.project.OriginReader.daqindiguo.R;
import com.yz.project.OriginReader.util.PreferencesUtil;
import com.yz.project.OriginReader.util.ReadUtil;
import com.yz.project.OriginReader.widget.ReadView;

public class OriginReaderActivity extends Activity {
	private ReadView rv;
	
	ReadUtil mReadUtil;
	
	private int mLine;

	private String mFileName;
	
	private AudioManager mAudioManager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        mAudioManager.setStreamMute(AudioManager.STREAM_RING, false);
//        mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
//        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//        mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
//        mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
//        mAudioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);//.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        
        rv = (ReadView) findViewById(R.id.tv);
        
        int w = getIntent().getIntExtra("w",getWindowManager().getDefaultDisplay().getWidth()) - rv.getPaddingLeft() - rv.getPaddingRight();
        int h = getIntent().getIntExtra("h",getWindowManager().getDefaultDisplay().getHeight()) - rv.getPaddingBottom() - rv.getPaddingTop();
        h = (int) (h - getResources().getDimension(R.dimen.ad_height));
        System.out.println(h);
        w = w - rv.getLineHeight() / 2;
        mLine = h / rv.getLineHeight();
        
    	mFileName = getIntent().getStringExtra("fileName");
    	long offset = getIntent().getLongExtra("offset", -1);
        
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
					rv.setTextList(mReadUtil.getLinesTxt(mLine, false));
				}else if(distance < -20){
					rv.setTextList(mReadUtil.getLinesTxt(mLine, true));
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
}









