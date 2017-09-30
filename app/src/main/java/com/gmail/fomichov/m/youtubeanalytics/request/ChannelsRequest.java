package com.gmail.fomichov.m.youtubeanalytics.request;

import com.alibaba.fastjson.JSON;
import com.gmail.fomichov.m.youtubeanalytics.MainActivity;
import com.gmail.fomichov.m.youtubeanalytics.json_channel.ChannelYouTube;
import com.gmail.fomichov.m.youtubeanalytics.utils.CheckForChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public ChannelsRequest() {
    }

    public ChannelsRequest(String idChannel) throws ExecutionException, InterruptedException {
        this.idChannel = idChannel;
//        this.idChannel = CheckForChannel.checkForChannel(new SearchRequest(idChannel).getChannelId());

    }

    public ChannelYouTube getSingleObject() throws ExecutionException, InterruptedException {
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
        return JSON.parseObject(futureTask.get(), ChannelYouTube.class);
    }

    public List<ChannelYouTube> getArrayObject(final List<String> channelIdArray) throws ExecutionException, InterruptedException {
        List<ChannelYouTube> tubeList = new ArrayList<>();
        List<FutureTask> taskList = new ArrayList<>();
        for (int i = 0; i < channelIdArray.size(); i++) {
            final int finalI = i;
            FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String json = null;
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(HTTP_URL_PARSE).newBuilder();
                    urlBuilder.addQueryParameter("part", "snippet,contentDetails,statistics");
                    urlBuilder.addQueryParameter("id", channelIdArray.get(finalI));
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
            taskList.add(futureTask);
            new Thread(futureTask).start();
        }
        for (FutureTask value : taskList) {
            tubeList.add(JSON.parseObject((String) value.get(), ChannelYouTube.class));
        }
        return tubeList;
    }
}

