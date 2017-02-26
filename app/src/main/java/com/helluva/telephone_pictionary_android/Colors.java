package com.helluva.telephone_pictionary_android;

import android.graphics.Color;

public enum Colors {
    BLACK("Black", Color.BLACK),
    RED("Red", Color.RED),
    GREEN("Green", Color.GREEN),
    BLUE("Blue", Color.BLUE);

    //may need to make public????
    private final String selectedColorName;
    private final int selectedColor;

    Colors(String selectedColorName,int selectedColor) {
        this.selectedColorName = selectedColorName;
        this.selectedColor = selectedColor;
    }

    public String toString() {
        return selectedColorName;
    }

    public int getColor() {
        return selectedColor;
    }

}
