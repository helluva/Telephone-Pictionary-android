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
    GameSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playerlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //pull session from intent
        this.session = (GameSession) this.getIntent().getSerializableExtra("gameSession");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(session.firebaseSessionKey());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                PlayerlistActivity.this.session = (GameSession) dataSnapshot.getValue(GameSession.class);

                content.removeAll(content);
                for (Player p : PlayerlistActivity.this.session.players) {
                    content.add(p.username);
                }

                PlayerlistFragment fragment = (PlayerlistFragment) PlayerlistActivity.this.getFragmentManager().findFragmentById(R.id.playerlist_fragment);
                if (fragment == null) {
                    reference.removeEventListener(this);
                } else {
                    fragment.adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
