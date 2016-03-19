package com.aniruddhc.music.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aniruddhc.music.R;
import com.aniruddhc.music.adapters.SongsListAdapter;
import com.aniruddhc.music.models.SongModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aniruddh on 3/19/2016.
 */
public class SongsFragment extends Fragment {

    private List<SongModel> songList = new ArrayList<>();
    private SongsListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_main, container, false);

        //getting the song list
        setupSongsList(recyclerView);

        //sorting songs alphabatically
        Collections.sort(songList, new Comparator<SongModel>() {
            public int compare(SongModel a, SongModel b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        return recyclerView;
    }

    private void setupSongsList(RecyclerView recyclerView) {

        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new SongModel(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mAdapter = new SongsListAdapter(getActivity(), songList);
        recyclerView.setAdapter(mAdapter);
    }


}