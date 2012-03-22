package com.yz.project.OriginReader.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.yz.project.OriginReader.R;
import com.yz.project.OriginReader.constant.Constant;

public class ReadUtil {
	private long mOffset;
	private long mPreOffset;
	
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
		mOffset = offset;
	}
	
	public String getTxtArray(boolean next) throws IOException{
		byte[] buffer = new byte[1024 * 4];
		InputStream is = mActivity.getAssets().open(FILE_NAME);
		
		if(next){
			offset(is, mOffset);
		}else{
			offset(is, mPreOffset - 1024 * 4);
		}
		int len = is.read(buffer);
		String str = new String(buffer, 0, len, "gbk");
		
		StringBuilder sb = clipText(is, str, next);
		return returnResult(sb);
	}
	
	
	public String getTxt(boolean next) throws IOException{
		byte[] buffer = new byte[1024 * 4];
		InputStream is = mActivity.getAssets().open(FILE_NAME);
		
		if(next){
			offset(is, mOffset);
		}else{
			offset(is, mPreOffset - 1024 * 4);
		}
		int len = is.read(buffer);
//		if((buffer[len - 1] & 0x80) > 0){
//			len--;
//		}
		String str = new String(buffer, 0, len, "gbk");
		
		StringBuilder sb = clipText(is, str, next);
		return returnResult(sb);
	}
	
	private StringBuilder clipText(InputStream is, String str,boolean forward)
			throws IOException {
		int each_height = computeEachHeight(str);
		String[] strs = str.split(String.valueOf(Constant.NEWLINE));
		if(forward){
			return clipNextText(strs, each_height);
		}else{
			return clipPreText(strs,each_height);
		}
	}
	
	private StringBuilder clipPreText(String[] strs, int each_height) {
		return null;
	}

	private StringBuilder clipNextText(String[] strs,int each_height){
		StringBuilder sb = new StringBuilder();
		int height = 0;
		
		for(String s : strs){
			if(s.length() != 0){
				while(true){
					int index = mPaint.breakText(s, true, mWidth, null);
					height += each_height;
					if(height >= mHeight){
						return sb;
					}
	
					String lineStr = s.substring(0,index);
					sb.append(lineStr);
					sb.append(Constant.NEWLINE);
					mOffset -= 2;
					s = s.substring(index,s.length());
					
					if(s.length() == 0){
						mOffset += 2;
						break;
					}
				}
			}else{
				sb.append(Constant.NEWLINE);
			}
			height += each_height;
		}
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
//		System.out.println(result);
		try {
			mOffset += result.getBytes("gbk").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
