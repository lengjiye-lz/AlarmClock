package com.lengjiye.clock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lengjiye.clock.R;
import com.lengjiye.clock.model.SongInformation;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * ChooseMusicFolderActivity 的适配器
 * Created by lz on 2015-3-12.
 */
public class ChooseMusicAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<SongInformation> songInformations;

    public ChooseMusicAdapter(Context mContext, List<SongInformation> songInformations) {
        this.mContext = mContext;
        this.songInformations = songInformations;
        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songInformations.size();
    }

    @Override
    public Object getItem(int i) {
        return songInformations.get(i);
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
            view = inflater.inflate(R.layout.choose_music_item, null);
            ViewUtils.inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        SongInformation SongInformation = songInformations.get(i);
        holder.tvSongNo.setText("" + (i + 1));
        holder.tvSongName.setText(SongInformation.getSongName());
        holder.tvArtist.setText(SongInformation.getArtist());
        return view;
    }

    private class Holder {
        @ViewInject(R.id.tv_song_no)
        private TextView tvSongNo;
        @ViewInject(R.id.tv_song_name)
        private TextView tvSongName;
        @ViewInject(R.id.tv_artist)
        private TextView tvArtist;
    }
}
