package com.helluva.telephone_pictionary_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

import java.util.ArrayList;

public class HostGameActivity extends AppCompatActivity {

    private String gameName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HostGameActivity.this, MainActivity.class);
                HostGameActivity.this.startActivity(i);
            }
        });

        Intent i = getIntent();



        Button createButton = (Button) this.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText playerText = (EditText) findViewById(R.id.player_name);
                String playerName = playerText.getText().toString();

                EditText gameText = (EditText) findViewById(R.id.game_name);
                gameName = gameText.getText().toString();

                // Reset errors.
                playerText.setError(null);
                gameText.setError(null);

                if (TextUtils.isEmpty(playerName) || TextUtils.isEmpty(gameName)) {
                    playerText.setError("You must enter both fields to continue");

                } else {
                    hostGame(playerName, gameName);
                }
            }
        });

        // Set a key listener callback
        EditText gameText = (EditText) findViewById(R.id.game_name);
        gameText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        EditText playerText = (EditText) findViewById(R.id.player_name);
                        String playerName = playerText.getText().toString();

                        EditText gameText = (EditText) findViewById(R.id.game_name);
                        gameName = gameText.getText().toString();

                        // Reset errors.
                        playerText.setError(null);
                        gameText.setError(null);

                        if (TextUtils.isEmpty(playerName) || TextUtils.isEmpty(gameName)) {
                            playerText.setError("You must enter both fields to continue");

                        } else {
                            hostGame(playerName, gameName);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void hostGame(final String hostName, final String gameName) {

        String newGameMessage = "hostGame:" + gameName + "," + hostName;
        ((ApplicationState)getApplicationContext()).sendMessage(newGameMessage);

        Intent intent = new Intent(HostGameActivity.this, PlayerlistActivity.class);
        intent.putExtra("playerIsHost", true);
        intent.putExtra("game_name", gameName);
        this.startActivity(intent);

    }

}
