package com.helluva.telephone_pictionary_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cal on 2/25/17.
 */

public class GameSession implements Serializable {

    //important Firebase keys
    static String FB_SESSIONS_KEY = "ActiveSessions";

    //SessionState
    public enum SessionState implements Serializable {
        WaitingForPlayers, GameInProgress;
    }

    //GameSession
    String name;
    SessionState state;

    Player owner;
    List<Player> players;

    public GameSession(String name, Player owner) {
        this.state = SessionState.WaitingForPlayers;
        this.name = name;
        this.owner = owner;

        this.players = new ArrayList<>();
        this.players.add(owner);
    }

    public GameSession() {
        this.state = SessionState.WaitingForPlayers;
        this.name = null;
        this.owner = null;
        this.players = null;
    }

    public String firebaseSessionKey() {
        return this.name;
    }

}
