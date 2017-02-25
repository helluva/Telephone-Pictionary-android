package com.helluva.telephone_pictionary_android;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Socket s = new Socket("172.16.100.122", 1337);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    System.out.println(reader.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();




        //set up the activity
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Activity activity = this;

        //when Host Button is clicked, request session name
        Button hostButton = (Button) this.findViewById(R.id.host_game_button);
        hostButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertHelper(activity, "Your Name", "Next").displayWithCompletion(new AlertHelper.AlertCompletion() {

                    @Override
                    public void receiveString(final String playerName) {
                        new AlertHelper(activity, "Game Name", "Create").displayWithCompletion(new AlertHelper.AlertCompletion() {

                            @Override
                            public void receiveString(String gameName) {
                                hostGame(playerName, gameName);
                            }

                        });
                    }

                });
            }

        });

        Button textViewButton = (Button) this.findViewById(R.id.text_description_button);
        textViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TextDescriptionActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        Button sketchButton = (Button) this.findViewById(R.id.sketch_button);
        sketchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SketchActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

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

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
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

}
