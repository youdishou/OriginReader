package com.yz.project.OriginReader.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.yz.project.OriginReader.R;

public class ReadUtil {
	private long mOffset;
	private final String FILE_NAME = "dadg.txt";
	private Activity mActivity;
	private Paint mPaint;
	
	private byte[] mBuffer;
	
	private int mHeight;
	private int mWidth;
	
	public ReadUtil(Activity a,Paint p,int offset){
		mActivity = a;
		mPaint = p;
		
		View v = a.findViewById(R.id.tv);
		View ll = a.findViewById(R.id.ll);
		
		Rect r = new Rect();
		v.getWindowVisibleDisplayFrame(r);
		
		mHeight = a.getWindowManager().getDefaultDisplay().getHeight() - r.top - ll.getPaddingLeft() - ll.getPaddingRight();
		mWidth = a.getWindowManager().getDefaultDisplay().getWidth() - ll.getPaddingTop() - ll.getPaddingBottom();
		System.out.println(mHeight);
		mOffset = offset;
	}
	
	public String getNextTxt() throws IOException{
		byte[] buffer = new byte[1024 * 4];
		InputStream is = mActivity.getAssets().open(FILE_NAME);
		
		offset(is, mOffset);
		
		int len = is.read(buffer);
		if((buffer[len - 1] & 0x80) > 0){
			len--;
		}
		String str = new String(buffer, 0, len, "gbk");

		int each_height = computeEachHeight(str);
		
		StringBuilder sb = clipText(is, str, each_height);
		return returnResult(sb);
	}

	private StringBuilder clipText(InputStream is, String str, int each_height)
			throws IOException {
		String[] strs = str.split(String.valueOf((char)10));
		StringBuilder sb = new StringBuilder();
		int height = 0;
		for(String s : strs){
			
			while(true){
				int index = mPaint.breakText(s, true, mWidth, null);
				sb.append(s.substring(0,index));
				
				height += each_height;
				if(height >= mHeight){
					return sb;
				}
				
				
				if(index >= s.length()){
					sb.append((char)10);
					break;
				}
				s = s.substring(index,s.length());
			}
		}
		is.close();
		return sb;
	}

	private int computeEachHeight(String str) {
		Rect r = new Rect();
		mPaint.getTextBounds(str, 0, str.length(), r);
		int EACH_HEIGHT = r.bottom - r.top;
		return EACH_HEIGHT;
	}

	private long offset(InputStream is, long needOffset) throws IOException {
		long offset;
		while(true){
			offset = is.skip(needOffset);
			needOffset -= offset;
			if(needOffset <= 0){
				break;
			}
		}
		return needOffset;
	}
	
	private String returnResult(StringBuilder sb){

		String result = sb.toString();
		try {
			mOffset += result.getBytes("gbk").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
