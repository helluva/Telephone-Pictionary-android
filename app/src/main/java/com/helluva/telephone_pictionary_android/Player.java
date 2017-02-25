package com.helluva.telephone_pictionary_android;

/**
 * Created by cal on 2/25/17.
 */

public abstract class Player {

    String username;

    public Player() {
        this.username = null;
    }

    public Player(String username) {
        this.username = username;
    }

    public boolean hasSeen(int trainID) {
        return false;
    }

}
