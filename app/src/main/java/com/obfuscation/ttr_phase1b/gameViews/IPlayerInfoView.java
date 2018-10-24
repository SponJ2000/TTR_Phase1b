package com.obfuscation.ttr_phase1b.gameViews;

import java.util.List;

import communication.Player;

/**
 * Created by jalton on 10/24/18.
 */

public interface IPlayerInfoView {
    void updateUI();
    void setPlayers(List<Player> players);
}
