package com.gmail.fomichov.m.youtubeanalytics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.fomichov.m.youtubeanalytics.R;
import com.gmail.fomichov.m.youtubeanalytics.json_comments.Playlist;
import com.gmail.fomichov.m.youtubeanalytics.request.CommentsRequest;
import com.gmail.fomichov.m.youtubeanalytics.utils.MyLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MediaResonance extends Fragment {
    private String nextPageToken = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.frag_mediaresonance, container, false);

        // получаем айди всех видео по данному плейлисту
        List<Playlist> list = new ArrayList<>();
        CommentsRequest commentsRequest = new CommentsRequest("UU5s8uAm1UgUetX3exGGDZCw");
        int temp = 0;
        do {
            try {
                list.add(commentsRequest.getListIdVideo(nextPageToken));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextPageToken = list.get(temp).nextPageToken;
            MyLog.showLog(temp + " - " + nextPageToken);
            temp++;

        } while (nextPageToken != null);


        return myView;
    }

    public static MediaResonance newInstance() {
        MediaResonance fragment = new MediaResonance();
        return fragment;
    }
}
