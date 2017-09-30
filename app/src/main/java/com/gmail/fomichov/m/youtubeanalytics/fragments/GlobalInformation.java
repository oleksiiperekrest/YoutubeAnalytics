package com.gmail.fomichov.m.youtubeanalytics.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.fomichov.m.youtubeanalytics.R;
import com.gmail.fomichov.m.youtubeanalytics.json_channel.ChannelYouTube;
import com.gmail.fomichov.m.youtubeanalytics.request.ChannelsRequest;
import com.gmail.fomichov.m.youtubeanalytics.utils.MyLog;

import java.util.concurrent.ExecutionException;

public class GlobalInformation extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.frag_globalinformation, container, false);
        final EditText etChannelId = (EditText) myView.findViewById(R.id.etChannelId);
        final TextView tvChannelNameResult = (TextView) myView.findViewById(R.id.tvChannelNameResult);
        final TextView tvDateCreationChannelResult = (TextView) myView.findViewById(R.id.tvDateCreationChannelResult);
        final TextView tvNumberSubscribersResult = (TextView) myView.findViewById(R.id.tvNumberSubscribersResult);
        final TextView tvNumberVideosResult = (TextView) myView.findViewById(R.id.tvNumberVideosResult);
        final TextView tvNumberViewsResult = (TextView) myView.findViewById(R.id.tvNumberViewsResult);
        final ImageView ivHighImageChaneel = (ImageView) myView.findViewById(R.id.ivHighImageChaneel);
        Button btnGetResult = (Button) myView.findViewById(R.id.btnGetResult);

        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelsRequest channelsRequest = new ChannelsRequest(etChannelId.getText().toString());
                try {
                    final ChannelYouTube tube = channelsRequest.getSingleObject();
                    tvChannelNameResult.setText(tube.items.get(0).snippet.title);
                    tvDateCreationChannelResult.setText(tube.items.get(0).snippet.publishedAt);
                    tvNumberSubscribersResult.setText(String.valueOf(tube.items.get(0).statistics.subscriberCount));
                    tvNumberVideosResult.setText(String.valueOf(tube.items.get(0).statistics.videoCount));
                    tvNumberViewsResult.setText(String.valueOf(tube.items.get(0).statistics.viewCount));

                    // ivHighImageChaneel.setImageURI(Uri.parse(tube.items.get(0).snippet.thumbnails.high.url));

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        return myView;
    }

    public static GlobalInformation newInstance() {
        GlobalInformation fragment = new GlobalInformation();
        return fragment;
    }

}
