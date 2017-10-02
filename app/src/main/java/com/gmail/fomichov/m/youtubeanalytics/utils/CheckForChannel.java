package com.gmail.fomichov.m.youtubeanalytics.utils;

import com.gmail.fomichov.m.youtubeanalytics.json.json_search.SearchResponse;

public class CheckForChannel {
    private static final String BADCHANNEL = "#ChannelNotFound";

    public static String lookupChannel(SearchResponse searchResponse) {

        String channelId = BADCHANNEL;
        if (searchResponse.pageInfo.totalResults == 0) return channelId;
        for (int i = 0; i < searchResponse.items.size(); i++) {
            if (searchResponse.items.get(i).id.kind.equals("youtube#channel")) {
                channelId = searchResponse.items.get(i).id.channelId;
                break;
            }
        }
        return channelId;
    }
}

