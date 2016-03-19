package com.aniruddhc.music.models;

/**
 * Created by Aniruddh on 3/19/2016.
 */
public class SongModel {

    private long id;
    private String title;
    private String artist;


    public SongModel(long songID, String songTitle, String songArtist) {
        id = songID;
        title = songTitle;
        artist = songArtist;
    }

    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

}
