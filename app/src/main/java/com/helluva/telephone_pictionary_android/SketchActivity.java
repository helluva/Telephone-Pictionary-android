package com.helluva.telephone_pictionary_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.agsw.FabricView.FabricView;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class SketchActivity extends AppCompatActivity {


    private Spinner colorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String description = getIntent().getStringExtra("text_description");

        TextView textDescription = (TextView) this.findViewById(R.id.description_text_view);
        textDescription.setText(description);

        Button nextButton = (Button) this.findViewById(R.id.next_button_sketch);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FabricView canvas = (FabricView) findViewById(R.id.fabricView);
                Bitmap image = canvas.getCanvasBitmap();
                String base64String = SketchActivity.encodeToBase64(image, Bitmap.CompressFormat.PNG, 100);

                System.out.println(base64String);

                Intent i = new Intent(SketchActivity.this, WaitActivity.class);



                SketchActivity.this.startActivity(i);
            }
        });



//OLD BUTTON WITH RED BACKGROUND STUFF
       /* Button redButton = (Button) this.findViewById(R.id.red_button);
        redButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        //redButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
        redButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FabricView canvas = (FabricView) findViewById(R.id.fabricView);
                canvas.setColor(Color.RED);
            }

        });
        */

        //COLOR SPINNER IMPLEMENTATION
        Spinner colorSpinner = (Spinner) findViewById(R.id.color_spinner);

        //for the new spinner
        ArrayAdapter<Colors> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Colors.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

         colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Colors newColor = Colors.values()[position];
                FabricView canvas = (FabricView) findViewById(R.id.fabricView);
                canvas.setColor(newColor.getColor());
            }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
             }
         });



        //SIZES SPINNER IMPLEMENTATION
        Spinner sizeSpinner = (Spinner) findViewById(R.id.size_spinner);

        //for the new spinner
        ArrayAdapter<Sizes> adapter2 = new ArrayAdapter(this,R.layout.spinner_size_item, Sizes.values());
        adapter.setDropDownViewResource(R.layout.spinner_size_item);
        sizeSpinner.setAdapter(adapter2);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sizes newSize = Sizes.values()[position];
                FabricView canvas = (FabricView) findViewById(R.id.fabricView);
                canvas.setSize(newSize.getSize());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
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


    //BITMAP HELPERS

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
