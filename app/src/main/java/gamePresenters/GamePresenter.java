package gamePresenters;

import android.support.v4.app.Fragment;

import com.obfuscation.ttr_phase1b.activity.IPresenter;
import com.obfuscation.ttr_phase1b.gameViews.IGameView;

public class GamePresenter implements IGamePresenter {

    private IGameView view;

    public GamePresenter(IGameView view) {
        this.view = view;
    }


    @Override
    public void updateInfo(Object result) {

    }

    @Override
    public void showMenu() {

    }

    @Override
    public void showPlayerInfo() {

    }

    @Override
    public void showTickets() {

    }

    @Override
    public void showChat() {

    }

    @Override
    public void showMap() {

    }
}
