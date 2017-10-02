package com.gmail.fomichov.m.youtubeanalytics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.fomichov.m.youtubeanalytics.R;
import com.gmail.fomichov.m.youtubeanalytics.json.json_playlist.Playlist;
import com.gmail.fomichov.m.youtubeanalytics.json.json_video.VideoList;
import com.gmail.fomichov.m.youtubeanalytics.request.CommentsRequest;
import com.gmail.fomichov.m.youtubeanalytics.utils.MyLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MediaResonance extends Fragment {
    private String nextPageToken = "";
    private List<VideoList> listIdVideoComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.frag_mediaresonance, container, false);

        List<Playlist> list = getListPlayList("UUZzCY2vYd-B2mtk9ZYgbGug");
        List<String> listVideoId = getListIdVideo(list);
        try {
            listIdVideoComment = new CommentsRequest().getVideoListId(listVideoId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyLog.showLog(String.valueOf(getCountComment(listIdVideoComment)));

        return myView;
    }

    // сводим все айди видео в один массив стрингов
    private List<String> getListIdVideo(List<Playlist> list) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).items.size(); j++) {
                stringList.add(list.get(i).items.get(j).contentDetails.videoId);
            }
        }
        return stringList;
    }

    // суммируем коменты
    private int getCountComment(List<VideoList> listIdVideoComment) {
        int count = 0;
        for (int i = 0; i < listIdVideoComment.size(); i++) {
            MyLog.showLog(listIdVideoComment.get(i).items.get(0).id + " " + listIdVideoComment.get(i).items.get(0).statistics.commentCount);
            count += listIdVideoComment.get(i).items.get(0).statistics.commentCount;
        }
        return count;
    }

    // получаем объекты в которых хранится айди видео
    private List<Playlist> getListPlayList(String idPlayList) {
        List<Playlist> list = new ArrayList<>();
        CommentsRequest commentsRequest = new CommentsRequest(idPlayList);
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
        return list;
    }


    public static MediaResonance newInstance() {
        MediaResonance fragment = new MediaResonance();
        return fragment;
    }
}
