package com.helluva.telephone_pictionary_android;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Gabor Siffel on 2/25/2017.
 */

public class Game {

    GameSession gameSession;

    ArrayList<Player> waitingPlayers;

    public Game() {
    }

    public void assignTrainsToWaitingPlayers() {

        int[] trainIDs = gameSession.getListOfAvailableTrains();

        for (int i : trainIDs) {
            for (Player player : waitingPlayers) {
                if (!player.hasSeen(i)) {
                    assignTrainToPlayer(i, player);
                }
            }
        }

    }

    public void assignTrainToPlayer(int trainID, Player p) {
        // idk
    }

}
