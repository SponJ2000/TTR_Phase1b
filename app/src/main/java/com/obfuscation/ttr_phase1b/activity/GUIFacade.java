package com.obfuscation.ttr_phase1b.activity;

import server.Result;

/**
 * Created by hao on 10/5/18.
 */

public class GUIFacade {
    private static GUIFacade guiFacade;

    public static GUIFacade getInstance(){
        if (guiFacade == null) {
            guiFacade = new GUIFacade();
        }

        return guiFacade;
    }

    public void onLogin(Result result) {

    }

    public void onRegister(Result result) {
    }

    public void onJoinGame(Result result) {
    }

    public void onCreateGame(Result result) {
    }

    public void onStartGame(Result result) {
    }

    public void onGetGameList(Result result) {
    }

    public void onGetPlayerList(Result result) {
    }
}
