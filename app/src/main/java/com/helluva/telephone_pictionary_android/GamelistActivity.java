package com.helluva.telephone_pictionary_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

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

public class GamelistActivity extends AppCompatActivity {

    ArrayList<String> content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(GameSession.FB_SESSIONS_KEY);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<String>> type = new GenericTypeIndicator<ArrayList<String>>() { };
                ArrayList<String> sessions = dataSnapshot.getValue(type);
                if (sessions == null) {
                    sessions = new ArrayList<>();
                }

                GamelistActivity.this.content.removeAll(content);
                GamelistActivity.this.content.addAll(sessions);

                GamelistFragment fragment = (GamelistFragment) GamelistActivity.this.getFragmentManager().findFragmentById(R.id.gamelist_fragment);
                if (fragment == null) {
                    reference.removeEventListener(this);
                } else {
                    fragment.adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void confirmJoinSession(int index) {
        final String sessionName = content.get(index);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Join " + sessionName + "?");

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

                new AlertHelper(GamelistActivity.this, "Your Name", "Join").displayWithCompletion(new AlertHelper.AlertCompletion() {

                    @Override
                    public void receiveString(String playerName) {
                        joinSession(sessionName, playerName);
                    }

                });

            }

        });

        alertBuilder.show();

    }

    public void joinSession(String sessionName, final String playerName) {

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        final DatabaseReference sessionRef = firebase.getReference(sessionName);

        sessionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameSession session = dataSnapshot.getValue(GameSession.class);

                Player newPlayer = new Player(playerName);
                session.players.add(newPlayer);

                sessionRef.setValue(session);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

}
