package com.yz.project.OriginReader.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;

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
	}
	
	public List<String> getLinesTxt(int line,boolean forward) throws IOException{
		LinkedList<String> data = new LinkedList<String>();
		InputStream is = mContext.getAssets().open(mTxtName);
		
		if(forward){
			offset(is, mOffset);
		}else{
			offset(is, mPreOffset - 1024 * 4);
		}
		int len = is.read(mBuffer);
		String str = new String(mBuffer, 0, len, "gbk");
		
		clipText(data, str, line, forward);
		
		return data;
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
	
	private void clipText(LinkedList<String> data,String str,int line,boolean forward){
		String[] strs = str.split(Constant.NEWLINE);
		
		for(String s : strs){
			if(s.length() <= 0){
				line--;
				if(line <= 0){
					return;
				}else{
					continue;
				}
			}
			
			int start = 0;
			int end = s.length();
			while(true){
				int index = mPaint.breakText(s,start,end, forward,mWidth, null);
				if(forward){
					data.add(s.substring(start, index));
					start += index;
				}else{
					data.addFirst(s.substring(index, end));
					end -= index;
				}
				line--;
				if(line <= 0){
					return;
				}
				
				if(start >= end || end <= start){
					break;
				}
			}
		}
	}
}
