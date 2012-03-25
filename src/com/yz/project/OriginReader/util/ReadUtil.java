package com.yz.project.OriginReader.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.yz.project.OriginReader.constant.Constant;

import android.content.Context;
import android.graphics.Paint;

public class ReadUtil {
	private String mTxtName;
	private Context mContext;
	
	private byte[] mBuffer;
	
	private Paint mPaint;
	
	private int mWidth;

	private long mStartOffset;
	private long mEndOffset;
	
	private String mEncoding = "gbk";
	
	public ReadUtil(Context c,String txtName, Paint p,int width){
		mTxtName = txtName;
		mContext = c;
		mPaint = p;
		mWidth = width;
	}
	
	public List<String> getLinesTxt(int line,boolean forward) throws IOException{
		LinkedList<String> data = new LinkedList<String>();
		InputStream is = mContext.getAssets().open(mTxtName);
		
		if(forward){
			offset(is, mEndOffset);
			mStartOffset = mEndOffset;
			mBuffer = new byte[1024 * 4];
		}else{
			if(offset(is, mStartOffset - 1024 * 4) == -1){
				mBuffer = new byte[(int) mStartOffset];
				System.out.println("in");
			}else{
				mBuffer = new byte[1024 * 4];
				System.out.println("out");
			}
			mEndOffset = mStartOffset;
		}
		int len = is.read(mBuffer);
		String str = new String(mBuffer, 0, len, mEncoding);

		clipText(data, str, line, forward);

		return data;
	}
	
	private long offset(InputStream is, long needOffset) throws IOException {
		if(needOffset <= 0){
			return -1;
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
			if(forward){
				data.add(Constant.NEWLINE);
			}else{
				data.addFirst(Constant.NEWLINE);
			}
			if(forward){//空的一行
				mEndOffset += 2;
			}else{
				mStartOffset -= 2;
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
				mEndOffset += lineStr.getBytes(mEncoding).length;
			}else{
				String lineStr = s.substring(end - len, end); 
				data.addFirst(lineStr);
				end -= len;
				mStartOffset -= lineStr.getBytes(mEncoding).length;
			}
			line--;
			
			if(start >= end || end <= start){//一行完结
				if(forward){
					mEndOffset += 2;
				}else{
					mStartOffset -= 2;
				}
				break;
			}
			if(line <= 0){
				return line;
			}
		}
		return line;
	}
	
	public void setStartOffset(long offset){
		mStartOffset = offset;
		mEndOffset = offset;
	}
	
	public long getStartOffset(){
		return mStartOffset;
	}
	
	public void setEncoding(String encoding){
		mEncoding = encoding;
	}
}
