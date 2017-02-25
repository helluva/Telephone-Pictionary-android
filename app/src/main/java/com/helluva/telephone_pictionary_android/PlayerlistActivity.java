package com.helluva.telephone_pictionary_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by cal on 2/25/17.
 */

public class PlayerlistActivity extends AppCompatActivity {

    ArrayList<String> content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playerlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((ApplicationState)getApplicationContext()).registerListenerForNodeMethod("playersInLobby", "playerlistListener", new ApplicationState.NodeCallback() {
            @Override
            public void receivedString(final String message) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] players = message.split(",");
                        content.removeAll(content);
                        for (String playerName : players) {
                            content.add(playerName);
                        }

                        PlayerlistFragment fragment = (PlayerlistFragment) PlayerlistActivity.this.getFragmentManager().findFragmentById(R.id.playerlist_fragment);
                        if (fragment == null) {
                            ((ApplicationState)getApplicationContext()).unregisterListenerNamed("playerlistListener");
                        } else {
                            fragment.adapter.notifyDataSetChanged();
                        }
                    }
                });


            }
        });

        ((ApplicationState)getApplicationContext()).sendMessage("requestRebroadcast:playersInLobby");

    }

}
