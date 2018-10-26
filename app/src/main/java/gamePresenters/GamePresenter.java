package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;

public class GamePresenter implements IGamePresenter {

    private IGameView view;
    private OnShowListener listener;

    public GamePresenter(IGameView view, OnShowListener listener) {
        this.view = view;
        this.listener = listener;
    }


    @Override
    public void updateInfo(Object result) {
        view.updateUI();
    }

    public void showMenu() {
        this.listener.onShow(Shows.menu);
    }

    public void showPlayerInfo() {
        this.listener.onShow(Shows.playerInfo);
    }

    public void showTickets() {
        this.listener.onShow(Shows.tickets);
    }

    public void showChat() {
        this.listener.onShow(Shows.chat);
    }

    public void showMap() {
        this.listener.onShow(Shows.map);
    }

}
