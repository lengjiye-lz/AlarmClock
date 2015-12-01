package com.lengjiye.clock.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;


/**
 * 自定义gridview适配器
 *
 * @author lz
 * @version 2014年7月1日
 *          <p/>
 *          增加自动设置为正方形的item的gridview的属性 add by wenjing.liu at 2014-7-22
 *          设置gridview的item为正方形，为了方便计算出每个item的width和height分为要注意五点 :
 *          1、在定义该gridview的adapter布局文件时，必须用将SquareLayout作为根view；
 *          2、需要在Java文件中设置setHorizontalSpacing
 *          ，即每个item之间的HorizontalSpacing（同布局文件的android
 *          :horizontalSpacing属性），不用在layout文件进行设置。
 *          3、需要在Java文件中设置setNumColumns,即每行的item的个数
 *          （同布局文件的android:numColumns属性），不用在layout文件进行设置；
 *          4、需要在Java文件中调用setColumnWidth
 *          ，传入的是Activity，该方法已经自动计算出每个item的width和heightsss
 *          增加自动设置为正方形的item的gridview的属性 add by wenjing.liu at 2014-7-22
 */
public class NoScrollGridView extends GridView {

    private int numColumns;
    private int horiSpace;
    private int columnWidth = 0;

    public NoScrollGridView(Context context) {
        super(context);
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));// 设置按下的背景为透明色
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
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

    /**
     * 自动计算出每个item的width和height
     *
     * @param activity
     */
    public void setColumnWidth(Activity activity) {
        int parentPaddingLeft = 0;
        int parentPaddingRight = 0;
        View parentView = null;

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        parentView = ((View) getParent());
        if (parentView != null) {
            parentPaddingLeft = parentView.getPaddingLeft();
            parentPaddingRight = parentView.getPaddingRight();
        }
        // 计算公式如下：(屏幕宽度-gridview的item之间的间隔-gridview的paddingleft and right)/num
        columnWidth = (screenWidth - horiSpace * (numColumns + 1) - getPaddingLeft() - getPaddingRight() - parentPaddingLeft - parentPaddingRight) / numColumns
                - 3;// -3 为了减少由于进行分辨率的转换引入的误差
        super.setColumnWidth(columnWidth);
    }

    @Override
    public int getColumnWidth() {
        return columnWidth;
    }

    // END:add by wenjing.liu for square gridview at 2014-7-22

    /**
     * 每个item之间的HorizontalSpacing（同布局文件的android:horizontalSpacing属性）
     *
     * @param padding
     */
    public void setHorizontalSpacing(int padding) {
        this.horiSpace = padding;
        super.setHorizontalSpacing(horiSpace);
    }

    /**
     * 每行的item的个数 （同布局文件的android:numColumns属性），不用再layout文件进行设置
     *
     * @param numColumns
     */
    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        super.setNumColumns(GridView.AUTO_FIT);
    }

}