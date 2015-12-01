package com.lengjiye.clock.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.lidroid.xutils.util.LogUtils;

/**
 * 用于播放音频的类
 * Created by lz on 2015/3/27.
 */
public class MediaPlayerUtils {

    private static MediaPlayer mMediaPlayer;
    private static MediaPlayerListener mediaPlayerListener;

    /**
     * 开始播放
     *
     * @param audioUrl 歌曲Uri
     */
    public static void playAudio(Context mContext, Uri audioUrl) {
        try {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            } else {
                stopPlayer();
                // mMediaPlayer.release();
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mContext, audioUrl);
            mMediaPlayer.prepareAsync();
            // mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mMediaPlayer.release();
                    if (mediaPlayerListener != null) {
                        mediaPlayerListener.mediaPlayerCompletion(mMediaPlayer);
                    }
                }
            });

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    if (mediaPlayerListener != null) {
                        mediaPlayerListener.mediaPlayerStart(mediaPlayer);
                    }
                }
            });

            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                    if (percent < 100) {
//                        AppContextObject.shareInstance();
//                        Toast.makeText(AppContextObject.shareInstance().appContext, "正在加载", Toast.LENGTH_SHORT).show();
//                    }
                    LogUtils.d("percent:  " + percent);
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 停止播放
     */
    public static void stopPlayer() {
        if (mMediaPlayer != null) {
            try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    if (mediaPlayerListener != null) {
                        mediaPlayerListener.mediaPlayerStop(mMediaPlayer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否播放
     *
     * @return 是 true 否 false
     */
    public static boolean isPlay() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public interface MediaPlayerListener {
        /**
         * 该方法用于监听mediaplayer是不是通过调用stop方法进行停止播放
         */
        public void mediaPlayerStop(MediaPlayer mMediaPlayer);

        /**
         * 该方法用于监听mediaplayer是不是自动播放完成
         */
        public void mediaPlayerCompletion(MediaPlayer mMediaPlayer);

        /**
         * 该方法用于监听mediaplayer是不是开始播放
         */
        public void mediaPlayerStart(MediaPlayer mMediaPlayer);
    }

    public static void setMediaPlayerListener(MediaPlayerListener mediaPlayerListener) {
        MediaPlayerUtils.mediaPlayerListener = mediaPlayerListener;
    }
}
