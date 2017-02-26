package com.helluva.telephone_pictionary_android;

import android.graphics.Color;

public enum Colors {
    BLACK("Black", Color.BLACK),
    BLUE("Blue", Color.BLUE),
    CYAN("Cyan", Color.parseColor("#00CCCC")),
    GREEN("Green", Color.GREEN),
    YELLOW("Yellow", Color.YELLOW),
    ORANGE("Orange", Color.parseColor("#FF8000")),
    RED("Red", Color.RED),
    GREY("Grey", Color.GRAY);

    
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
