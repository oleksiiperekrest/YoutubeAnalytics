package com.gmail.fomichov.m.youtubeanalytics.request;

import android.app.ProgressDialog;

import com.alibaba.fastjson.JSON;
import com.gmail.fomichov.m.youtubeanalytics.MainActivity;
import com.gmail.fomichov.m.youtubeanalytics.json_channel.ChannelYouTube;
import com.gmail.fomichov.m.youtubeanalytics.utils.MyLog;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChannelsRequest {
    private final String HTTP_URL_PARSE = "https://www.googleapis.com/youtube/v3/channels";
    private String idChannel;

    public ChannelsRequest(String idChannel) {
        this.idChannel = idChannel;
    }

    // получаем string json
    private String getJson() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String json = null;
                HttpUrl.Builder urlBuilder = HttpUrl.parse(HTTP_URL_PARSE).newBuilder();
                urlBuilder.addQueryParameter("part", "snippet,contentDetails,statistics");
                urlBuilder.addQueryParameter("id", idChannel);
                urlBuilder.addQueryParameter("key", MainActivity.KEY_YOUTUBE_API);
                Request request = new Request.Builder()
                        .url(urlBuilder.build().toString())
                        .build();
                OkHttpClient client = new OkHttpClient();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    json = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        });
        new Thread(futureTask).start();
        return futureTask.get();
    }

    // получаем распарсенный обьект
    public ChannelYouTube getObject() throws ExecutionException, InterruptedException {
        FutureTask<ChannelYouTube> futureTask = new FutureTask<ChannelYouTube>(new Callable<ChannelYouTube>() {
            @Override
            public ChannelYouTube call() throws Exception {
                ChannelYouTube group = JSON.parseObject(getJson(), ChannelYouTube.class);
                return group;
            }
        });
        new Thread(futureTask).start();
        return futureTask.get();
    }
}

