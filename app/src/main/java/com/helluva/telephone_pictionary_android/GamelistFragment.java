package com.helluva.telephone_pictionary_android;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by cal on 2/25/17.
 */

public class GamelistFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gamelist_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> list = new ArrayList<>();

        GamelistActivity activity = (GamelistActivity) this.getActivity();
        this.adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, activity.content);
        this.getListView().setAdapter(this.adapter);
        this.adapter.setNotifyOnChange(true);

        this.getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GamelistActivity activity = (GamelistActivity) this.getActivity();
        activity.confirmJoinSession(position);
    }
}
