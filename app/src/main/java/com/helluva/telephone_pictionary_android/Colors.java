package com.helluva.telephone_pictionary_android;

import android.graphics.Color;

public enum Colors {
    BLACK("Black"),
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue");

    //may need to make public????
    private final String selectedColor;

    Colors(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String toString() {
        return selectedColor;
    }

}
