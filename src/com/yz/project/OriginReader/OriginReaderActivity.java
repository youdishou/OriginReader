package com.yz.project.OriginReader;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.yz.project.OriginReader.util.ReadUtil3;
import com.yz.project.OriginReader.widget.ReadView;

public class OriginReaderActivity extends Activity {
	private ReadView rv;
	
	ReadUtil3 mReadUtil;
	
	private int mLine;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        rv = (ReadView) findViewById(R.id.tv);
        
        int w = getIntent().getIntExtra("w",getWindowManager().getDefaultDisplay().getWidth()) - rv.getPaddingLeft() - rv.getPaddingRight();
        int h = getIntent().getIntExtra("h",getWindowManager().getDefaultDisplay().getHeight()) - rv.getPaddingBottom() - rv.getPaddingTop();
        
        w = w - rv.getLineHeight() / 2;
        System.out.println(w+","+h);
        mLine = h / rv.getLineHeight();
        mReadUtil = new ReadUtil3(this, "dadg.txt", rv.getPaint(), w);

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
//				outList(tmp);
			}else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
				List<String> tmp = mReadUtil.getLinesTxt(mLine, false);
				rv.setTextList(tmp);
//				outList(tmp);
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









