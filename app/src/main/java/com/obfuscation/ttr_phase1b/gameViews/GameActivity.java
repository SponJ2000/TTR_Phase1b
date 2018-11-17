package com.obfuscation.ttr_phase1b.gameViews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import gamePresenters.ChatPresenter;
import gamePresenters.GamePresenter;
import gamePresenters.IChatPresenter;
import gamePresenters.IGamePresenter;
import gamePresenters.IMenuPresenter;
import gamePresenters.ITicketPresenter;
import gamePresenters.MenuPresenter;
import gamePresenters.Shows;
import gamePresenters.TicketPresenter;

public class GameActivity extends AppCompatActivity implements IGamePresenter.OnShowListener,
        IChatPresenter.OnBackListener, ITicketPresenter.OnBackListener, IMenuPresenter.MenuListener {

    private static final String TAG = "GameActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = TicketFragment.newInstance();
            PresenterFacade.getInstance().setPresenter(new TicketPresenter((ITicketView) fragment, this));

            fm.beginTransaction().add(R.id.container, fragment).commit();
            Log.d(TAG, "Loaded the ticket fragment");
        }
    }

    @Override
    public void onShow(Shows show) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        switch (show) {
            case chat:
                fragment = ChatFragment.newInstance();
                PresenterFacade.getInstance().setPresenter( new ChatPresenter((IChatView) fragment, this) );
                fm.beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case tickets:
                fragment = TicketFragment.newInstance();
                PresenterFacade.getInstance().setPresenter( new TicketPresenter((ITicketView) fragment, this) );
                fm.beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case playerInfo:
                fragment = PlayerInfoDialogFragment.newInstance();
                ((IGamePresenter) PresenterFacade.getInstance().getPresenter()).showPlayerInfo((IPlayerInfoView) fragment);
                fm.beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case map:
                fragment = GameFragment.newInstance();
                PresenterFacade.getInstance().setPresenter( new GamePresenter((IGameView) fragment, this) );
                fm.beginTransaction().replace(R.id.container, fragment).commit();
                break;
        }
        Log.d(TAG, "showing a " + show + " fragment");
    }

    @Override
    public void onBack() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = GameFragment.newInstance();
        PresenterFacade.getInstance().setPresenter( new GamePresenter((IGameView) fragment, this) );
        fm.beginTransaction().replace(R.id.container, fragment).commit();
        Log.d(TAG, "Loaded the game fragment back in");
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onGameSelect() {

    }

}
