package com.helluva.telephone_pictionary_android;

import java.util.ArrayList;

/**
 * Created by cal on 2/25/17.
 */

public class GameSession {

    String name;

    Player owner;
    ArrayList<Player> players;


    public GameSession(String name, Player owner) {
        this.name = name;
        this.owner = owner;

        this.players = new ArrayList<>();
        this.players.add(owner);
    }

    public GameSession() {
        this.name = null;
        this.owner = null;
        this.players = null;
    }

}
