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

        String gameName = getIntent().getStringExtra("game_name");

//        TextView playerTitle = (TextView) this.findViewById(R.id.players_title);
//        playerTitle.setText("Players in \"" + gameName + "\"");

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

        Button startGameButton = (Button) this.findViewById(R.id.start_game_button);

        if (playerIsHost) {
            startGameButton.setVisibility(View.VISIBLE);

//            layout.addView(startGame);

            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ApplicationState)getApplicationContext()).sendMessage("startGame");
                }
            });

        } else {
            startGameButton.setVisibility(View.INVISIBLE);
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
