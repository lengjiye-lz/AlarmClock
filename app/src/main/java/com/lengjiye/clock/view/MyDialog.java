package com.lengjiye.clock.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lengjiye.clock.R;

/**
 * Created by lz on 2015/1/28.
 */
public class MyDialog extends Dialog {

    private Context mContext;
    private LinearLayout layout;

    public MyDialog(Context context, int theme) {
        super(context, R.style.transparentFrameWindowStyle);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (LinearLayout) inflater.inflate(theme, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout);
        initView();
    }

    public void initView() {
        Window window = this.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = window.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        this.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        this.setCanceledOnTouchOutside(true);
    }

    public LinearLayout getDialogView() {
        return layout;
    }
}
