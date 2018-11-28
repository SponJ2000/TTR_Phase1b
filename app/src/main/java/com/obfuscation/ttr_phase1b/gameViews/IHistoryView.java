package com.obfuscation.ttr_phase1b.gameViews;

import java.util.ArrayList;

import communication.GameHistory;

public interface IHistoryView extends IView {

    void setHistory(ArrayList<GameHistory> history);

}
