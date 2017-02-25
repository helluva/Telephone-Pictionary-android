package com.helluva.telephone_pictionary_android;

import java.io.Serializable;

/**
 * Created by cal on 2/25/17.
 */

public class Player implements Serializable {

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
