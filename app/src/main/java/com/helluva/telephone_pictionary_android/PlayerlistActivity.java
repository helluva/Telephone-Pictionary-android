package com.helluva.telephone_pictionary_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        //load players

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

        //add button or label, depending on if the player is host
        LinearLayout layout = (LinearLayout) findViewById(R.id.playerlist_layout);

        boolean playerIsHost = getIntent().getBooleanExtra("playerIsHost", false);
        if (playerIsHost) {
            final Button startGame = new Button(this);
            startGame.setText("Start Game");
            startGame.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            startGame.setPadding(10,10,10,10);
            layout.addView(startGame);

            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ApplicationState)getApplicationContext()).sendMessage("startGame");
                }
            });

        } else {
            TextView waitingText = new TextView(this);
            waitingText.setText("Waiting for host to start game...");
            waitingText.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            waitingText.setPadding(10,10,10,10);
            layout.addView(waitingText);
        }


        ((ApplicationState)getApplicationContext()).registerListenerForNodeMethod("gameStarted", "playerlistGameStarted", new ApplicationState.NodeCallback() {
            @Override
            public void receivedString(String message) {

                PlayerlistActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PlayerlistActivity.this.startGame();
                    }
                });

            }
        });

    }

    public void startGame() {
        Intent i = new Intent(this, TextDescriptionActivity.class);
        this.startActivity(i);
    }

}
