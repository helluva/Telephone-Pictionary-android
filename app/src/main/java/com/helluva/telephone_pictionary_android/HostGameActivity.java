package com.helluva.telephone_pictionary_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HostGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        Button createButton = (Button) this.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText player = (EditText) findViewById(R.id.player_name);
                String playerName = player.getText().toString();

                EditText game = (EditText) findViewById(R.id.game_name);
                String gameName = game.getText().toString();

                hostGame(playerName, gameName);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        //get current list of games
        final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        final DatabaseReference allSessions = firebase.getReference(GameSession.FB_SESSIONS_KEY);

        allSessions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> type = new GenericTypeIndicator<ArrayList<String>>() { };
                ArrayList<String> sessions = dataSnapshot.getValue(type);
                if (sessions == null) {
                    sessions = new ArrayList<>();
                }

                if (sessions.contains(gameName)) {
                    //error?
                } else {
                    sessions.add(gameName);
                    allSessions.setValue(sessions);

                    Player host = new Player(hostName);
                    GameSession session = new GameSession(gameName, host);

                    final DatabaseReference newSession = firebase.getReference(session.firebaseSessionKey());
                    newSession.setValue(session);

                    //view playerlist
                    Intent playerlist = new Intent(HostGameActivity.this, PlayerlistActivity.class);
                    playerlist.putExtra("gameSession", session);
                    HostGameActivity.this.startActivity(playerlist);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

}
