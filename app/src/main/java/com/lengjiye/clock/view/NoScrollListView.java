package com.lengjiye.clock.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * 自定义不能滑动的ListView
 *
 * @author lz
 * @version 2015-3-16
 */
public class NoScrollListView extends ListView {

    private int numColumns;
    private int horiSpace;
    private int columnWidth = 0;

    public NoScrollListView(Context context) {
        super(context);
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));// 设置按下的背景为透明色
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 设置不滚动
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}