package com.yz.project.OriginReader.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.widget.FrameLayout;

import com.yz.project.OriginReader.constant.Constant;

public class ReadUtil2 {
	private String mTxtName;
	private Context mContext;
	
	private byte[] mBuffer;
	
	private Paint mPaint;
	
	private int mWidth;
	private int mHeight;

	private long mOffset;
	private long mPreOffset;
	
	public ReadUtil2(Context c,String txtName, Paint p,int height,int width){
		mTxtName = txtName;
		mContext = c;
		mPaint = p;
		mWidth = width;
		mHeight = height;
		mBuffer = new byte[1024 * 4];
	}
	
	public List<String> getLinesTxt(int line,boolean forward) throws IOException{
		LinkedList<String> data = new LinkedList<String>();
		InputStream is = mContext.getAssets().open(mTxtName);
		
		if(forward){
			offset(is, mOffset);
			mPreOffset = mOffset;
		}else{
			if(offset(is, mPreOffset - 1024 * 4) == 0){
				mBuffer = new byte[(int) mPreOffset];
			}
			mOffset = mPreOffset;
			
		}
		int len = is.read(mBuffer);
		String str = new String(mBuffer, 0, len, "gbk");

		clipText(data, str, line, forward);
		
		return data;
	}
	
	private long offset(InputStream is, long needOffset) throws IOException {
		if(needOffset <= 0){
			return 0;
		}
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
	
	private void clipText(LinkedList<String> data,String str,int line,boolean forward) throws UnsupportedEncodingException{
		String[] strs = str.split(Constant.NEWLINE);
		
		if(forward){
			for(int i=0;i<strs.length;i++){
				line = clipOneLineText(data,forward,strs[i],line);
				if(line <= 0){
					break;
				}
			}
		}else{
			for(int i=strs.length - 1;i>=0;i--){
				line = clipOneLineText(data,forward,strs[i],line);
				if(line <= 0){
					break;
				}
			}
		}
	}
	
	private int clipOneLineText(LinkedList<String> data,boolean forward,String s,int line) throws UnsupportedEncodingException{
		
		if(line <= 0){
			return line;
		}
		
		if(s.length() <= 0){
			line--;
			data.add(Constant.NEWLINE);
			if(forward){//空的一行
				mOffset += 2;
			}else{
				mPreOffset -= 2;
			}
			return line;
		}
		
		int start = 0;
		int end = s.length();
		while(true){
			int len = mPaint.breakText(s,start,end, forward,mWidth, null);
			if(forward){
				String lineStr = s.substring(start, start + len);
				data.add(lineStr);
				start += len;
				mOffset += lineStr.getBytes("gbk").length;
			}else{
				String lineStr = s.substring(end - len, end); 
				data.addFirst(lineStr);
				end -= len;
				mPreOffset -= lineStr.getBytes("gbk").length;
			}
			line--;
			
			if(start >= end || end <= start){//一行完结
				if(forward){
					mOffset += 2;
				}else{
					mPreOffset -= 2;
				}
				break;
			}
			if(line <= 0){
				return line;
			}
		}
		return line;
	}
}
