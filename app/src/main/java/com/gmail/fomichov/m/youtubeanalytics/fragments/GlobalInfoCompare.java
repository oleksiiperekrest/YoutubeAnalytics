package com.gmail.fomichov.m.youtubeanalytics.fragments;

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
import com.gmail.fomichov.m.youtubeanalytics.utils.MyDateUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class GlobalInfoCompare extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.frag_globalinfo_compare, container, false);
        final EditText etChannelIdOne = (EditText) myView.findViewById(R.id.etChannelIdOne);
        final EditText etChannelIdTwo = (EditText) myView.findViewById(R.id.etChannelIdTwo);
        final TextView tvChannelNameResultOne = (TextView) myView.findViewById(R.id.tvChannelNameResultOne);
        final TextView tvChannelNameResultTwo = (TextView) myView.findViewById(R.id.tvChannelNameResultTwo);
        final TextView tvDateCreationChannelResultOne = (TextView) myView.findViewById(R.id.tvDateCreationChannelResultOne);
        final TextView tvDateCreationChannelResultTwo = (TextView) myView.findViewById(R.id.tvDateCreationChannelResultTwo);
        final TextView tvNumberSubscribersResultOne = (TextView) myView.findViewById(R.id.tvNumberSubscribersResultOne);
        final TextView tvNumberSubscribersResultTwo = (TextView) myView.findViewById(R.id.tvNumberSubscribersResultTwo);
        final TextView tvNumberVideosResultOne = (TextView) myView.findViewById(R.id.tvNumberVideosResultOne);
        final TextView tvNumberVideosResultTwo = (TextView) myView.findViewById(R.id.tvNumberVideosResultTwo);
        final TextView tvNumberViewsResultOne = (TextView) myView.findViewById(R.id.tvNumberViewsResultOne);
        final TextView tvNumberViewsResultTwo = (TextView) myView.findViewById(R.id.tvNumberViewsResultTwo);
        final ImageView ivHighImageChannelOne = (ImageView) myView.findViewById(R.id.ivHighImageChannelOne);
        final ImageView ivHighImageChannelTwo = (ImageView) myView.findViewById(R.id.ivHighImageChannelTwo);
        Button btnGetResult = (Button) myView.findViewById(R.id.btnGetResult);

        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelsRequest channelsRequestOne = new ChannelsRequest(etChannelIdOne.getText().toString());
                ChannelsRequest channelsRequestTwo = new ChannelsRequest(etChannelIdTwo.getText().toString());
                try {
                    final ChannelYouTube tubeOne = channelsRequestOne.getObject();
                    final ChannelYouTube tubeTwo = channelsRequestTwo.getObject();
                    tvChannelNameResultOne.setText(tubeOne.items.get(0).snippet.title);
                    tvChannelNameResultTwo.setText(tubeTwo.items.get(0).snippet.title);
                    tvDateCreationChannelResultOne.setText(String.valueOf(MyDateUtils.convertStringToDate(tubeOne.items.get(0).snippet.publishedAt)));
                    tvDateCreationChannelResultTwo.setText(String.valueOf(MyDateUtils.convertStringToDate(tubeTwo.items.get(0).snippet.publishedAt)));
                    tvNumberSubscribersResultOne.setText(String.valueOf(tubeOne.items.get(0).statistics.subscriberCount));
                    tvNumberSubscribersResultTwo.setText(String.valueOf(tubeTwo.items.get(0).statistics.subscriberCount));
                    tvNumberVideosResultOne.setText(String.valueOf(tubeOne.items.get(0).statistics.videoCount));
                    tvNumberVideosResultTwo.setText(String.valueOf(tubeTwo.items.get(0).statistics.videoCount));
                    tvNumberViewsResultOne.setText(String.valueOf(tubeOne.items.get(0).statistics.viewCount));
                    tvNumberViewsResultTwo.setText(String.valueOf(tubeTwo.items.get(0).statistics.viewCount));
                    Picasso.with(getContext()).load(tubeOne.items.get(0).snippet.thumbnails.high.url).into(ivHighImageChannelOne);
                    Picasso.with(getContext()).load(tubeTwo.items.get(0).snippet.thumbnails.high.url).into(ivHighImageChannelTwo);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return myView;
    }






    public static GlobalInfoCompare newInstance() {
        GlobalInfoCompare fragment = new GlobalInfoCompare();
        return fragment;
    }
}
