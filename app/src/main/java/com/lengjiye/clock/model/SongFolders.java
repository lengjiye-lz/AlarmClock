package com.lengjiye.clock.model;

/**
 * 每个歌曲集合的属性
 *
 * @author lz
 * @version 2015-2-13
 */
public class SongFolders {
    private String songFolderId;
    private String songFolderName;
    private int songNum;
    private String songFolderPath;

    public String getSongFolderId() {
        return songFolderId;
    }

    public void setSongFolderId(String songFolderId) {
        this.songFolderId = songFolderId;
    }

    public String getSongFolderName() {
        return songFolderName;
    }

    public void setSongFolderName(String songFolderName) {
        this.songFolderName = songFolderName;
    }

    public int getSongNum() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public String getSongFolderPath() {
        return songFolderPath;
    }

    public void setSongFolderPath(String songFolderPath) {
        this.songFolderPath = songFolderPath;
    }
}
