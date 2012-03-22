package com.yz.project.OriginReader.widget;

import com.yz.project.OriginReader.util.AppUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class ReadView extends TextView{
	private String[] mTxt;
	private int mLinePadding = 0;
	private int mLineHeight = 0;
	
	public ReadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLinePadding = AppUtil.dip2dx(getContext(), 2);	
		mLineHeight = AppUtil.dip2dx(getContext(), 20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		
//		int height = 0;
		for(int i=0; i<mTxt.length; i++){
			int y = mLineHeight * (i + 1);
			canvas.drawText(mTxt[i], getPaddingLeft(), y, getPaint());
		}
	}
	
	public void setText(String str){
		mTxt = str.split("\r\n");
		System.out.println(mTxt.length);
		invalidate();
	}
	
	private int getTextHeight(String str){
		Rect r = new Rect();
		getPaint().getTextBounds(str, 0, str.length(), r);
		return r.bottom - r.top;
	}
}
