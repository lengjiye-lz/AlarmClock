package com.lengjiye.clock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lengjiye.clock.R;
import com.lengjiye.clock.adapter.ChooseMusicAdapter;
import com.lengjiye.clock.model.SongInformation;
import com.lengjiye.clock.view.ActionBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择音乐
 *
 * @author lz
 * @version 2015-3-9
 */
public class ChooseMusicActivity extends BaseActivity {

    @ViewInject(R.id.action_bar)
    private ActionBar actionBar;
    @ViewInject(R.id.list_view)
    private ListView listView;


    private Context mContext;
    private List<SongInformation> songInformations;
    private ChooseMusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        ViewUtils.inject(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mContext = this;
        actionBar.setLiftVisibility(View.VISIBLE);
        actionBar.setRightVisibility(View.GONE);
        actionBar.setLiftText("返回");
        actionBar.setTittleText("选择音乐");
        Bundle bundle = getIntent().getExtras();
        songInformations = new ArrayList<SongInformation>();
        if (bundle != null) {
            songInformations.clear();
            songInformations = (List<SongInformation>) bundle.getSerializable("SongInformation");
        }
        listView.setAdapter(adapter);
        adapter = new ChooseMusicAdapter(mContext, songInformations);
        listView.setAdapter(adapter);
    }


    @OnItemClick(R.id.list_view)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SongInformation information = (SongInformation) adapterView.getItemAtPosition(i);
        if (information != null) {
            Intent intent = new Intent(mContext, AddClockActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("activity", "ChooseMusicActivity");
            bundle.putSerializable("SongInformation", information);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @OnClick({R.id.btn_lift})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_lift:
                finish();
                break;
        }
    }
}
