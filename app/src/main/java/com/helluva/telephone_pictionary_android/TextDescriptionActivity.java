package com.helluva.telephone_pictionary_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class TextDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        Button nextButton = (Button) this.findViewById(R.id.next_button_text1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textDescription = (EditText) findViewById(R.id.description_field);
                String description = textDescription.getText().toString();

                // Reset errors.
                textDescription.setError(null);

                if (TextUtils.isEmpty(description)) {
                    textDescription.setError("You gotta enter a phrase to continue yah fool");

                } else {
                    String captionMessage = "provideCaption:" + description;
                    ((ApplicationState) getApplicationContext()).sendMessage(captionMessage);

                    Intent i = new Intent(TextDescriptionActivity.this, WaitActivity.class);
                    TextDescriptionActivity.this.startActivity(i);
                }
            }
        });

        // Set a key listener callback so that someone can submit a description with the "enter" key
        EditText textDescription = (EditText) findViewById(R.id.description_field);
        textDescription.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        EditText textDescription = (EditText) findViewById(R.id.description_field);
                        String description = textDescription.getText().toString();

                        // Reset errors.
                        textDescription.setError(null);

                        if (TextUtils.isEmpty(description)) {
                            textDescription.setError("You must enter both fields to continue");

                        } else {
                            String captionMessage = "provideCaption:" + description;
                            ((ApplicationState) getApplicationContext()).sendMessage(captionMessage);

                            Intent i = new Intent(TextDescriptionActivity.this, WaitActivity.class);
                            TextDescriptionActivity.this.startActivity(i);
                        }
                    }
                    return true;
                }
                return false;
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
