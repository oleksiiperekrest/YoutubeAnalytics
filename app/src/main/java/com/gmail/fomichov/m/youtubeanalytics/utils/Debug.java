package com.gmail.fomichov.m.youtubeanalytics.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.StringCodec;
import com.gmail.fomichov.m.youtubeanalytics.json_channel.ChannelYouTube;
import com.gmail.fomichov.m.youtubeanalytics.request.SearchRequest;

import java.util.concurrent.ExecutionException;

public class Debug {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ChannelYouTube channelYouTube =  JSON.parseObject(ChannelNotFoundJSON.json, ChannelYouTube.class);
        System.out.println();
        String idChannel = "UC4_bwov47DseacR1-ttTdOg";
        String id = CheckForChannel.lookupChannel(new SearchRequest(idChannel).getSearchResponse());
        System.out.println(id);
        idChannel = "boogie";
        id = CheckForChannel.lookupChannel(new SearchRequest(idChannel).getSearchResponse());
        System.out.println(id);
        idChannel = "boo";
        id = CheckForChannel.lookupChannel(new SearchRequest(idChannel).getSearchResponse());
        System.out.println(id);
        idChannel = "egor klenov";
        id = CheckForChannel.lookupChannel(new SearchRequest(idChannel).getSearchResponse());
        System.out.println(id);
        idChannel = "ghjkl;,'.fsdcxvhjabkln;msdxcv";
        id = CheckForChannel.lookupChannel(new SearchRequest(idChannel).getSearchResponse());
        System.out.println(id);

    }
}
