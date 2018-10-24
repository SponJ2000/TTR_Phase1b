package com.obfuscation.ttr_phase1b.gameViews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import gamePresenters.GamePresenter;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = GameFragment.newInstance();
            PresenterFacade.getInstance().setCurrentFragment(new GamePresenter(fragment));
            fm.beginTransaction().add(R.id.container, fragment).commit();
            Log.d(TAG, "Loaded the game fragment");
        }
    }
}
