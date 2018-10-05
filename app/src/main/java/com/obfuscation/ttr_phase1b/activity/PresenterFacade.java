package com.obfuscation.ttr_phase1b.activity;

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
    }

    public void updateFragment(Object data) {
        this.mCurrentFragment.updateInfo(data);
    }

    public void onComplete(Object data) {
        this.mCurrentFragment.onComplete(data);
    }

}
