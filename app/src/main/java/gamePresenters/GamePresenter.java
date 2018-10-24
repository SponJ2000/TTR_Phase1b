package gamePresenters;

import android.support.v4.app.Fragment;

import com.obfuscation.ttr_phase1b.activity.IPresenter;

public class GamePresenter implements IPresenter {

    private Fragment fragment;

    public GamePresenter(Fragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public void updateInfo(Object result) {

    }
}
