package com.helluva.telephone_pictionary_android;

public enum Sizes {
    SMALL("Small", 2f),
    DEFAULT("Medium", 7f),
    LARGE("Large", 15f),
    HUGE("Huge", 50f);


    private final String selectedSizeName;
    private float selectedSize;

    Sizes(String selectedSizeName, float selectedSize) {
        this.selectedSizeName = selectedSizeName;
        this.selectedSize = selectedSize;
    }

    public String toString() {
        return selectedSizeName;
    }

    public float getSize() {
        return selectedSize;
    }
    public void setSize(float newSize) {
        this.selectedSize = newSize;
    }
}

