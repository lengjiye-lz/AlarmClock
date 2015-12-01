package com.lengjiye.clock.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lengjiye.clock.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 自定义actionbar
 * Created by lz on 2015/1/21.
 */
public class ActionBar extends LinearLayout{

    private LinearLayout barView;
    private Context mContext;
    private LayoutInflater inflater;

    @ViewInject(R.id.rl_actionbar_layout)
    private RelativeLayout rlActionBarLayout;
    @ViewInject(R.id.btn_lift)
    private Button btnLift;
    @ViewInject(R.id.btn_right)
    private Button btnRight;
    @ViewInject(R.id.tv_tittle)
    private TextView tvTittle;

    public ActionBar(Context context) {
        super(context);
        initLayout(context);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
    }

    private void initLayout(Context context) {
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        barView = (LinearLayout) inflater.inflate(R.layout.actionbar, null);
        if(!isInEditMode()){
            ViewUtils.inject(this, barView);
        }
        addView(barView);
    }

    public void setLayoutMarginTop(int top){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, top, 0, 0);
        rlActionBarLayout.setLayoutParams(layoutParams);
    }

    /**
     * 设置右边的字体
     * @param rightText
     */
    public void setRightText(String rightText){
        btnRight.setText(rightText);
    }

    public void setRightText(int rightText){
        btnRight.setText(rightText);
    }

    /**
     * 设置右边隐藏
     * @param visibility
     */
    public void setRightVisibility(int visibility){
        btnRight.setVisibility(visibility);
    }

    /**
     * 设置右边背景图片
     * @param background
     */
    public void setRightBackgroundResource(int background){
        btnRight.setBackgroundResource(background);
    }

    public void setRightBackground(Drawable background){
        btnRight.setBackground(background);
    }

    /**
     * 设置左边的字体
     * @param liftText
     */
    public void setLiftText(String liftText){
        btnLift.setText(liftText);
    }

    public void setLiftText(int liftText){
        btnLift.setText(liftText);
    }

    /**
     * 设置左边隐藏
     * @param visibility
     */
    public void setLiftVisibility(int visibility){
        btnLift.setVisibility(visibility);
    }

    /**
     * 设置左边背景图片
     * @param background
     */
    public void setLiftBackgroundResource(int background){
        btnLift.setBackgroundResource(background);
    }

    public void setLiftBackground(Drawable background){
        btnLift.setBackground(background);
    }

    /**
     * 设置标题
     * @param tittleText
     */
    public void setTittleText(String tittleText){
        tvTittle.setText(tittleText);
    }

    public void setTittleText(int tittleText){
        tvTittle.setText(tittleText);
    }

//    /**
//     * 设置监听事件
//     * @param listener
//     */
//    public void setOnClickListener(OnClickListener listener) {
//        btnLift.setOnClickListener(listener);
//        btnRight.setOnClickListener(listener);
//    }

}
