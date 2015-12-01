package com.lengjiye.clock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lengjiye.clock.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * MainActivity 的适配器
 * Created by lz on 2015/1/22.
 */
public class MainAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<String> strings;

    public MainAdapter(Context mContext, List<String> strings) {
        this.mContext = mContext;
        this.strings = strings;
        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.main_lv_item, null);
            ViewUtils.inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        if (i == 0) {
            holder.ivTimeNode.setBackgroundResource(R.drawable.time_node_top);
        } else if (getCount() > 1 && i == getCount() - 1) {
            holder.ivTimeNode.setBackgroundResource(R.drawable.time_node_bottom);
        } else {
            holder.ivTimeNode.setBackgroundResource(R.drawable.time_node_middle);
        }
        holder.tvTime.setText(strings.get(i));
        return view;
    }

    private class Holder {
        @ViewInject(R.id.tv_time)
        private TextView tvTime;
        @ViewInject(R.id.iv_time_node)
        private ImageView ivTimeNode;
    }
}
