package com.helluva.telephone_pictionary_android;

import android.graphics.Color;

public enum Colors {
    BLACK(Color.BLACK),
    RED(Color.RED),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE);

    //may need to make public????
    private final int selectedColor;

    Colors(int selectedColor) {
        this.selectedColor = selectedColor;
    }

}
