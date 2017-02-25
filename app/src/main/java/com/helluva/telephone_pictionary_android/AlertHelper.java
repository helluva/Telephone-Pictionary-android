package com.helluva.telephone_pictionary_android;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by cal on 2/25/17.
 */

public class AlertHelper {

    private final Context context;
    private final String title;
    private final String positiveButtonString;

    public AlertHelper(Context context, String title, String positiveButtonString) {
        this.context = context;
        this.title = title;
        this.positiveButtonString = positiveButtonString;
    }

    public void displayWithCompletion(final AlertCompletion completion) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this.context);
        alertBuilder.setTitle(this.title);

        final EditText editText = new EditText(this.context);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        alertBuilder.setView(editText);

        alertBuilder.setPositiveButton(this.positiveButtonString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String response = editText.getText().toString();
                completion.receiveString(response);
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertBuilder.show();
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public interface AlertCompletion {
        public abstract void receiveString(String value);
    }

}
