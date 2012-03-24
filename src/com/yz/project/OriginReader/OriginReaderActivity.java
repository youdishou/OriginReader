package com.yz.project.OriginReader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.yz.project.OriginReader.util.ReadUtil2;
import com.yz.project.OriginReader.widget.ReadView;

public class OriginReaderActivity extends Activity {
	private ReadView tv;
	
	ReadUtil2 mReadUtil;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (ReadView) findViewById(R.id.tv);
        
        mReadUtil = new ReadUtil2(this, "dadg.txt", tv.getPaint(), 700, 450);
        
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try{
			if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
				List<String> tmp = mReadUtil.getLinesTxt(10, true);
				tv.setTextList(tmp);
				outList(tmp);
			}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
				List<String> tmp = mReadUtil.getLinesTxt(10, false);
				tv.setTextList(tmp);
				outList(tmp);
			}else if(keyCode == KeyEvent.KEYCODE_BACK){
				finish();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	private void outList(List<String> list){
//		System.out.println(list.size());
		for(String str : list){
			System.out.println(str+"");
		}
	}
}









