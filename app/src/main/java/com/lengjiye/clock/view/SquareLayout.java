package com.lengjiye.clock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 正方形的布局
 * 
 * @author wenjing.liu
 * @version 2014-7-22
 */
public class SquareLayout extends RelativeLayout {
	public SquareLayout(Context context) {
		super(context);
	}

	public SquareLayout(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public SquareLayout(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
		// childern are just made to fill our space
		int childWidth = getMeasuredWidth();
		int childHeight = getMeasuredHeight();
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
