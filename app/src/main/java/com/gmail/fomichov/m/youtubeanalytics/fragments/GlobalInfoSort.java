package com.gmail.fomichov.m.youtubeanalytics.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.gmail.fomichov.m.youtubeanalytics.R;
import com.gmail.fomichov.m.youtubeanalytics.adapters.RecyclerGlobalAdapter;
import com.gmail.fomichov.m.youtubeanalytics.dialogs.DialogArray;
import com.gmail.fomichov.m.youtubeanalytics.json_channel.ChannelYouTube;
import com.gmail.fomichov.m.youtubeanalytics.request.ChannelsRequest;
import com.gmail.fomichov.m.youtubeanalytics.utils.MyLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GlobalInfoSort extends Fragment {
    private ArrayList<String> channelIdArray;
    private List<ChannelYouTube> tubeList;
    private RecyclerGlobalAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_globalinfo_sort, container, false);
        channelIdArray = new ArrayList<>();
        tubeList = new ArrayList<>();
        Spinner spinner = (Spinner) view.findViewById(R.id.spTypeSort);
        Button btnAddArray = (Button) view.findViewById(R.id.btnAddArray);
        Button btnLoadExample = (Button) view.findViewById(R.id.btnLoadExample);

        btnAddArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogArray();
            }
        });

        btnLoadExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channelIdArray.clear();
                channelIdArray.add("UCt7sv-NKh44rHAEb-qCCxvA");
                channelIdArray.add("UCZzCY2vYd-B2mtk9ZYgbGug");
                channelIdArray.add("UCDbsY8C1eQJ5t6KBv9ds-ag");
                channelIdArray.add("UCnAmkiIpUXkVOY1A1r-zE6w");
                channelIdArray.add("UCmbzthMYaX8FAe_cZSrGMrA");
                channelIdArray.add("UC5s8uAm1UgUetX3exGGDZCw");
                channelIdArray.add("UCkp0Tc7ll67bChomTyB1ezQ");
                channelIdArray.add("UCHFNDph3zTYh8-hwofOVXsg");
                channelIdArray.add("UCSpU8Y1aoqBSAwh8DBpiM9A");
                channelIdArray.add("UCRP4EhX1Op-jL7D87PB3qhQ");
                startLoadData();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerGlobalAdapter(tubeList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.sort_array);
                switch (position) {
                    case 0:
                        Collections.sort(tubeList, new Comparator<ChannelYouTube>() {
                            public int compare(ChannelYouTube obj1, ChannelYouTube obj2) {
                                return obj1.items.get(0).snippet.title.compareToIgnoreCase(obj2.items.get(0).snippet.title);
                            }
                        });
                        break;
                    case 1:
                        Collections.sort(tubeList, new Comparator<ChannelYouTube>() {
                            public int compare(ChannelYouTube obj1, ChannelYouTube obj2) {
                                return obj1.items.get(0).snippet.publishedAt.compareToIgnoreCase(obj2.items.get(0).snippet.publishedAt);
                            }
                        });
                        break;
                    case 2:
                        Collections.sort(tubeList, new Comparator<ChannelYouTube>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public int compare(ChannelYouTube obj1, ChannelYouTube obj2) {
                                return Integer.compare(obj1.items.get(0).statistics.subscriberCount, obj2.items.get(0).statistics.subscriberCount);
                            }
                        });
                        break;
                    case 3:
                        Collections.sort(tubeList, new Comparator<ChannelYouTube>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public int compare(ChannelYouTube obj1, ChannelYouTube obj2) {
                                return Integer.compare(obj1.items.get(0).statistics.videoCount, obj2.items.get(0).statistics.videoCount);
                            }
                        });
                        break;
                    case 4:
                        Collections.sort(tubeList, new Comparator<ChannelYouTube>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public int compare(ChannelYouTube obj1, ChannelYouTube obj2) {
                                return Integer.compare(obj1.items.get(0).statistics.viewCount, obj2.items.get(0).statistics.viewCount);
                            }
                        });
                        break;
                }
                MyLog.showLog(choose[position]);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void startLoadData() {
        tubeList.clear();
        for (int i = 0; i < channelIdArray.size(); i++) {
            ChannelsRequest channelsRequest = new ChannelsRequest(channelIdArray.get(i));
            try {
                tubeList.add(channelsRequest.getObject());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(tubeList, new Comparator<ChannelYouTube>() {
            public int compare(ChannelYouTube obj1, ChannelYouTube obj2) {
                return obj1.items.get(0).snippet.title.compareToIgnoreCase(obj2.items.get(0).snippet.title);
            }
        });
        adapter.notifyDataSetChanged();
    }

    public static GlobalInfoSort newInstance() {
        GlobalInfoSort fragment = new GlobalInfoSort();
        return fragment;
    }

    private void showDialogArray() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        new DialogArray().show(fragmentTransaction, "dialog");
    }
}