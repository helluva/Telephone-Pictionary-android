package com.helluva.telephone_pictionary_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Nate on 2/25/17.
 */

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        ((ApplicationState)getApplicationContext()).registerListenerForNodeMethod("nextCaption", "waitingForCaption", new ApplicationState.NodeCallback() {
            @Override
            public void receivedString(String message) {

                Intent draw = new Intent(WaitActivity.this, SketchActivity.class);
                draw.putExtra("text_description", message);
                WaitActivity.this.startActivity(draw);

            }
        });

        ((ApplicationState)getApplicationContext()).registerListenerForNodeMethod("nextImage", "waitingForImage", new ApplicationState.NodeCallback() {
            @Override
            public void receivedString(String message) {
                Intent nextRound = new Intent(WaitActivity.this, TextWithSketchActivity.class);

                System.out.println("base64: " + message);

                nextRound.putExtra("base64_image", message.trim());
                WaitActivity.this.startActivity(nextRound);
            }
        });


    }

    @Override
    public void onBackPressed() {
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
