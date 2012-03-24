package com.yz.project.OriginReader.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yz.project.OriginReader.util.AppUtil;

public class ReadView extends TextView{
	private List<String> mTxt;
	private int mLinePadding = 0;
	private int mLineHeight = 0;
	
	public ReadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLinePadding = AppUtil.dip2dx(getContext(), 2);	
		mLineHeight = AppUtil.dip2dx(getContext(), 20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(mTxt == null){
			return;
		}
		
		for(int i=0; i<mTxt.size(); i++){
			int y = mLineHeight * (i + 1);
			String tmp = mTxt.get(i);
			canvas.drawText(tmp, getPaddingLeft(), y, getPaint());
		}
	}
	
	public void setTextList(List<String> list){
		mTxt = list;
		invalidate();
	}
}
