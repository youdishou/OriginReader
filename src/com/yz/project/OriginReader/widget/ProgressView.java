package com.yz.project.OriginReader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ProgressView extends View{
	private double mPercent = 0;
	private Paint mPaint;
	
	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		mPaint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		mPaint.setColor(Color.RED);
		canvas.drawRect(0, 0, (int)(getMeasuredWidth() * mPercent), getMeasuredHeight(), mPaint);
		System.out.println(getMeasuredWidth() * mPercent);
	}
	
	public void setPercent(double percent){
		mPercent = percent;
		invalidate();
	}

}
