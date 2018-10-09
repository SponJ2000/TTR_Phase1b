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
        if(mCurrentFragment.getClass() == GameListFragment.class) {
            ModelFacade.getInstance().UpdateState(State.GAMELIST);
        }else if(mCurrentFragment.getClass() == LobbyFragment.class) {
            ModelFacade.getInstance().UpdateState(State.LOBBY);
        }
    }

    public void updateFragment(Object data) {
        this.mCurrentFragment.updateInfo(data);
    }

}
