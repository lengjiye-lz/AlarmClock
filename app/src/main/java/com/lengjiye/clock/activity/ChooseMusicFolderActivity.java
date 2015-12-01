package com.lengjiye.clock.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lengjiye.clock.R;
import com.lengjiye.clock.adapter.ChooseMusicFolderAdapter;
import com.lengjiye.clock.model.SongFolders;
import com.lengjiye.clock.model.SongInformation;
import com.lengjiye.clock.utils.StringUtils;
import com.lengjiye.clock.view.ActionBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择音乐文件夹
 *
 * @author lz
 * @version 2015-3-9
 */
public class ChooseMusicFolderActivity extends BaseActivity {

    @ViewInject(R.id.action_bar)
    private ActionBar actionBar;
    @ViewInject(R.id.internal_list_view)
    private ListView internalListView;
    @ViewInject(R.id.external_list_view)
    private ListView externalListView;


    private Context mContext;
    private QueryHandler queryHandler;
    private String num_of_songs = "num_of_songs";
    private List<SongFolders> internalSongFolders;
    private List<SongFolders> externalSongFolders;
    private ArrayList<SongInformation> internalSongInformation;
    private ArrayList<SongInformation> externalSongInformation;
    private ChooseMusicFolderAdapter internalAdapter;
    private ChooseMusicFolderAdapter externalAdapter;
    private static String INTERNAL = "internal";
    private static String EXTERNAL = "external";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music_folder);
        ViewUtils.inject(this);
        init();
        startQueryMusicFolder(INTERNAL);
        startQueryMusicFolder(EXTERNAL);
    }

    /**
     * 初始化
     */
    private void init() {
        mContext = this;
        actionBar.setLiftVisibility(View.VISIBLE);
        actionBar.setRightVisibility(View.GONE);
        actionBar.setLiftText("返回");
        actionBar.setTittleText("选择文件夹");
        queryHandler = new QueryHandler(getContentResolver());
        internalSongFolders = new ArrayList<SongFolders>();
        externalSongFolders = new ArrayList<SongFolders>();
        internalSongInformation = new ArrayList<SongInformation>();
        externalSongInformation = new ArrayList<SongInformation>();
        internalAdapter = new ChooseMusicFolderAdapter(mContext, internalSongFolders);
        internalListView.setAdapter(internalAdapter);
        externalAdapter = new ChooseMusicFolderAdapter(mContext, externalSongFolders);
        externalListView.setAdapter(externalAdapter);
    }

    /**
     * 检索歌曲文件夹
     *
     * @param uriString 歌曲数据库uri
     */
    private void startQueryMusicFolder(String uriString) {
        Uri uri = MediaStore.Files.getContentUri(uriString);
        String[] mProjection = new String[]{
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.PARENT,
                "count(" + MediaStore.Files.FileColumns.PARENT + ") as "
                        + num_of_songs};
        String mSelection = MediaStore.Files.FileColumns.MEDIA_TYPE + " = "
                + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + ") "
                + "group by (" + MediaStore.Files.FileColumns.PARENT;
        /**
         * startQuery(int token, Object cookie, ContentURI uri, String[] projection, String selection,
         * String[] selectionArgs, String sortOrder)
         * 参数说明 token:附件信息，可以在onQueryComplete(int token, Object cookie, Cursor cursor)方法中得到
         * 类似于标示符的东西
         * cookie:附件信息，可以在onQueryComplete(int token, Object cookie, Cursor cursor)方法中得到
         * 类似于标示符的东西
         * uri:数据库的URI
         * projection:要从MediaStore检索的列
         * selection:where子句
         * selectionArgs:(暂时不知道是什么作用)
         * sortOrder:排序方式
         */
        queryHandler.startQuery(0, uriString, uri, mProjection, mSelection, null, null);
    }

    /**
     * 检索文件夹歌曲文件
     *
     * @param uriString 歌曲数据库uri
     */
    private void startQueryMusic(String uriString, String parent) {
        Uri uri = MediaStore.Files.getContentUri(uriString);
        String[] mProjection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST};
        String mSelection = MediaStore.Files.FileColumns.PARENT + "=" + parent;
        /**
         * startQuery(int token, Object cookie, ContentURI uri, String[] projection, String selection,
         * String[] selectionArgs, String sortOrder)
         * 参数说明 token:附件信息，可以在onQueryComplete(int token, Object cookie, Cursor cursor)方法中得到
         * 类似于标示符的东西
         * cookie:附件信息，可以在onQueryComplete(int token, Object cookie, Cursor cursor)方法中得到
         * 类似于标示符的东西
         * uri:数据库的URI
         * projection:要从MediaStore检索的列
         * selection:where子句
         * selectionArgs:(暂时不知道是什么作用)
         * sortOrder:排序方式
         */
        queryHandler.startQuery(1, uriString, uri, mProjection, mSelection, null, null);
    }

    private class QueryHandler extends AsyncQueryHandler {
        private String filePath, folderPath, folderName;

        public QueryHandler(ContentResolver cR) {
            super(cR);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && token == 0) {
                // 遍历歌曲文件夹
                int index_data = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int index_num_of_songs = cursor.getColumnIndex(num_of_songs);
                if (INTERNAL.equals(cookie)) {
                    internalSongFolders.clear();
                } else {
                    externalSongFolders.clear();
                }
                while (cursor.moveToNext()) {
                    // 获取文件的路径
                    filePath = cursor.getString(index_data);
                    // 获取文件所属文件夹的路径
                    folderPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
                    // 获取文件所属文件夹的名称
                    folderName = folderPath.substring(folderPath.lastIndexOf(File.separator) + 1);
                    SongFolders songFolders = new SongFolders();
                    LogUtils.e("PARENT:" + cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.PARENT)));
                    songFolders.setSongFolderId(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.PARENT)));
                    songFolders.setSongNum(cursor.getInt(index_num_of_songs));
                    songFolders.setSongFolderPath(folderPath);
                    songFolders.setSongFolderName(folderName);
                    if (INTERNAL.equals(cookie)) {
                        internalSongFolders.add(songFolders);
                    } else {
                        externalSongFolders.add(songFolders);
                    }
                }
                if (INTERNAL.equals(cookie)) {
                    internalAdapter.notifyDataSetChanged();
                } else {
                    externalAdapter.notifyDataSetChanged();
                }
            } else if (cursor != null && token == 1) {
                if (INTERNAL.equals(cookie)) {
                    internalSongInformation.clear();
                } else {
                    externalSongInformation.clear();
                }
                // 遍历每个歌曲文件夹下的文件
                while (cursor.moveToNext()) {
                    SongInformation songInformation = new SongInformation();
                    songInformation.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                    songInformation.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                    songInformation.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                    songInformation.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                    songInformation.setSongName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                    songInformation.setAlbum(StringUtils.isBlank(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))) ?
                            "未知专辑" : cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                    songInformation.setArtist(StringUtils.isBlank(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))) ?
                            getMusicDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))) :
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                    if (INTERNAL.equals(cookie)) {
                        internalSongInformation.add(songInformation);
                    } else {
                        externalSongInformation.add(songInformation);
                    }
                }
                Bundle bundle = new Bundle();
                Message message = new Message();
                if (INTERNAL.equals(cookie)) {
                    bundle.putSerializable("SongInformation", internalSongInformation);
                    message.setData(bundle);
                    message.what = 1;
                    handler.sendMessage(message);
                } else {
                    bundle.putSerializable("SongInformation", externalSongInformation);
                    message.setData(bundle);
                    message.what = 1;
                    handler.sendMessage(message);
                }
            } else {
                LogUtils.e("没有数据");
            }
        }
    }

    // 获取歌手名字
    private String getMusicDisplayName(String displayName) {
        String s1[] = null;
        if (StringUtils.isNotBlank(displayName) && displayName.indexOf("_") != -1) {
            String s[] = displayName.split("_");
            s1 = s[1].split("\\.");
        } else {
            return "未知歌手";
        }
        return s1[0];
    }

    @OnItemClick({R.id.internal_list_view, R.id.external_list_view})
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.internal_list_view:
                SongFolders internalSongFolders = (SongFolders) adapterView.getItemAtPosition(i);
                startQueryMusic(INTERNAL, internalSongFolders.getSongFolderId());
                break;
            case R.id.external_list_view:
                SongFolders externalSongFolders = (SongFolders) adapterView.getItemAtPosition(i);
                startQueryMusic(EXTERNAL, externalSongFolders.getSongFolderId());
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(mContext, ChooseMusicActivity.class);
                    Bundle bundle = msg.getData();
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };

    @OnClick({R.id.btn_lift})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_lift:
                finish();
                break;
        }
    }
}
