package com.helluva.telephone_pictionary_android;

public enum Sizes {
    SMALL("Small", 2f),
    DEFAULT("Medium", 7f),
    LARGE("Large", 15f);


    private final String selectedSizeName;
    private final float selectedSize;

    Sizes(String selectedColorName, float selectedColor) {
        this.selectedSizeName = selectedColorName;
        this.selectedSize = selectedColor;
    }

    public String toString() {
        return selectedSizeName;
    }

    public float getSize() {
        return selectedSize;
    }
}

