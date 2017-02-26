package com.helluva.telephone_pictionary_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //start listening to the node server
        ApplicationState appState = (ApplicationState) this.getApplicationContext();
        if (appState.outputStream == null) {
            appState.spinUpSocket();
        }



        //set up the activity
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button hostButton = (Button) this.findViewById(R.id.host_game_button);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HostGameActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        Button joinButton = (Button) this.findViewById(R.id.join_game_button);
        joinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gamelist = new Intent(MainActivity.this, GamelistActivity.class);
                MainActivity.this.startActivity(gamelist);
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

        Button startButton = (Button) this.findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TextDescriptionActivity.class);
                MainActivity.this.startActivity(i);
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

}
