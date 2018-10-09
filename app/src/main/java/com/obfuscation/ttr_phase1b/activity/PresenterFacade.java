package com.obfuscation.ttr_phase1b.activity;

import android.util.Log;

import model.ModelFacade;
import model.State;

public class PresenterFacade {

    private static final String TAG = "PresenterFacade";

    private static PresenterFacade SINGLETON = new PresenterFacade();

    private IPresenter mCurrentFragment;

    private PresenterFacade() {
    }

    public static PresenterFacade getInstance() {
        return SINGLETON;
    }

    public IPresenter getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(IPresenter mCurrentFragment) {
        this.mCurrentFragment = mCurrentFragment;
        Log.d(TAG, "fragment class: " + mCurrentFragment.getClass());
        if(mCurrentFragment.getClass() == GameListFragment.class) {
            Log.d(TAG, "changing to gamelist state");
            ModelFacade.getInstance().UpdateState(State.GAMELIST);
            Log.d(TAG, "finished");
        }else if(mCurrentFragment.getClass() == LobbyFragment.class) {
            Log.d(TAG, "changing to lobby state");
            ModelFacade.getInstance().UpdateState(State.LOBBY);
            Log.d(TAG, "finished");
        }
    }

    public void updateFragment(Object data) {
        this.mCurrentFragment.updateInfo(data);
    }

}
