package com.lengjiye.clock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lengjiye.clock.R;
import com.lengjiye.clock.adapter.MainAdapter;
import com.lengjiye.clock.view.ActionBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 主Activity
 */
public class MainActivity extends BaseActivity {

    private Context mContext;
    @ViewInject(R.id.lv_clock_list)
    private ListView listView;
    @ViewInject(R.id.action_bar)
    private ActionBar actionBar;

    private List<String> strings;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        init();
        simulationData();
        adapter = new MainAdapter(mContext, strings);
        listView.setAdapter(adapter);
    }

    /**
     * 初始化
     */
    private void init() {
        mContext = this;
        actionBar.setLiftVisibility(View.INVISIBLE);
        actionBar.setRightText("添加");
    }

    /**
     * 模拟数据
     */
    private void simulationData() {
        strings = new ArrayList<String>();
        for(int i = 0; i<10;i++){
            strings.add("08:0" + i);
        }
    }

    /**
     * 加载本地数据库数据
     */
    private void loadClockData() {
    }

    @OnClick({R.id.btn_right, R.id.btn_lift})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_lift:
                break;
            case R.id.btn_right:
                startActivity(new Intent(mContext, AddClockActivity.class));
                break;
        }
    }

}
