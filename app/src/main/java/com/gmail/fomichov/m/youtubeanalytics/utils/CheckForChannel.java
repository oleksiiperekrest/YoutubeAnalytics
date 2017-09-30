package com.gmail.fomichov.m.youtubeanalytics.utils;

import com.gmail.fomichov.m.youtubeanalytics.json_search.SearchResponse;

public class CheckForChannel {

    public static String checkForChannel(SearchResponse searchResponse) {

        String channelId = null;
        for (int i = 0; i < searchResponse.items.size(); i++) {
            if (searchResponse.items.get(i).id.kind.equals("youtube#channel")) {
                channelId = searchResponse.items.get(i).id.channelId;
                break;
            }
        }
        return channelId;
    }
}

