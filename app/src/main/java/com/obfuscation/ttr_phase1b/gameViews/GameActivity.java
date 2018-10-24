package com.obfuscation.ttr_phase1b.gameViews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import gamePresenters.GamePresenter;
import gamePresenters.IChatPresenter;
import gamePresenters.IGamePresenter;
import gamePresenters.IMenuPresenter;
import gamePresenters.IScorePresenter;
import gamePresenters.ITicketPresenter;
import gamePresenters.Shows;

public class GameActivity extends AppCompatActivity implements IGamePresenter.OnShowListener,
        IChatPresenter.OnBackListener, IScorePresenter.OnBackListener,
        ITicketPresenter.OnBackListener, IMenuPresenter.OnBackListener,
        IMenuPresenter.OnGameSelectListener, IMenuPresenter.OnLogoutListener {

    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = GameFragment.newInstance();
            PresenterFacade.getInstance().setCurrentFragment(new GamePresenter((IGameView) fragment, this));
            fm.beginTransaction().add(R.id.container, fragment).commit();
            Log.d(TAG, "Loaded the game fragment");
        }
    }

    @Override
    public void onShow(Shows show) {

    }

    @Override
    public void onBack() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = GameFragment.newInstance();
        PresenterFacade.getInstance().setCurrentFragment( new GamePresenter((IGameView) fragment, this) );
        fm.beginTransaction().add(R.id.container, fragment).commit();
        Log.d(TAG, "Loaded the game fragment back in");
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onGameSelect() {

    }

}
