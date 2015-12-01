package com.lengjiye.clock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lengjiye.clock.R;
import com.lengjiye.clock.model.SongFolders;
import com.lengjiye.clock.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * ChooseMusicActivity 的适配器
 * Created by lz on 2015-3-12.
 */
public class ChooseMusicFolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<SongFolders> songFolderses;

    public ChooseMusicFolderAdapter(Context mContext, List<SongFolders> songFolderses) {
        this.mContext = mContext;
        this.songFolderses = songFolderses;
        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songFolderses.size();
    }

    @Override
    public Object getItem(int i) {
        return songFolderses.get(i);
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
            view = inflater.inflate(R.layout.choose_music_folder_item, null);
            ViewUtils.inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        SongFolders songFolders = songFolderses.get(i);
        holder.tvSongFolderName.setText(StringUtils.isNotBlank(songFolders.getSongFolderName()) ? songFolders.getSongFolderName() : "");
        holder.tvSongNum.setText("" + songFolders.getSongNum());
        holder.tvSongPath.setText(StringUtils.isNotBlank(songFolders.getSongFolderPath()) ? songFolders.getSongFolderPath() : "");

        return view;
    }

    private class Holder {
        @ViewInject(R.id.tv_song_folder_name)
        private TextView tvSongFolderName;
        @ViewInject(R.id.tv_song_num)
        private TextView tvSongNum;
        @ViewInject(R.id.tv_song_path)
        private TextView tvSongPath;
    }
}
