package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;

public class GamePresenter implements IGamePresenter {

    private IGameView view;

    public GamePresenter(IGameView view) {
        this.view = view;
    }


    @Override
    public void updateInfo(Object result) {

    }

    public void showMenu() {

    }

    public void showPlayerInfo() {

    }

    public void showTickets() {

    }

    public void showChat() {

    }

    public void showMap() {

    }
}
