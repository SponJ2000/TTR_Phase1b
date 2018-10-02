package com.obfuscation.ttr_phase1b.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.obfuscation.ttr_phase1b.R;

public class LoginActivity extends FragmentActivity implements LoginFragment.OnLoginListener,
        LobbyFragment.OnGameLeaveListener, GameListFragment.OnGameSelectListener,
        GameCreationFragment.OnGameCreationLister {

    private static final String TAG = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            fm.beginTransaction().add(R.id.container, fragment).commit();
            Log.d(TAG, "Loaded the login fragment");
        }
    }

    @Override
    public void onLogin() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = GameListFragment.newInstance();
        fm.beginTransaction().replace(R.id.container, fragment).commit();
        Log.d(TAG, "Loaded the game list fragment");
    }

    @Override
    public void onGameSelect(String selection) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        if(selection == "join") {
            fragment = LobbyFragment.newInstance();
            Log.d(TAG, "Loaded the lobby fragment");
        }else if(selection == "create") {
            fragment = GameCreationFragment.newInstance();
            Log.d(TAG, "Loaded the create game fragment");
        }
        fm.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onFinishCreating(String selection) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        if(selection == "create") {
            fragment = LobbyFragment.newInstance();
            Log.d(TAG, "Loaded the lobby fragment");
        }else if(selection == "cancel") {
            fragment = GameListFragment.newInstance();
            Log.d(TAG, "Loaded the game list fragment");
        }
        fm.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onGameLeave() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = GameListFragment.newInstance();
        fm.beginTransaction().replace(R.id.container, fragment).commit();
        Log.d(TAG, "Loaded the game list fragment");
    }

}
