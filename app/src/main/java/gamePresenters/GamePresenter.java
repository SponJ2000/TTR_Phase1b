package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;

import model.IGameModel;
import model.ModelFacade;

public class GamePresenter implements IGamePresenter {

    private IGameView view;
    private OnShowListener listener;
    private IGameModel model;

    public GamePresenter(IGameView view, OnShowListener listener) {
        this.view = view;
        this.listener = listener;
        this.model = ModelFacade.getInstance();
    }


    @Override
    public void updateInfo(Object result) {
        view.setCards(model.getCards());
        view.setMap(model.getMap());
        view.setPlayer(model.getPlayer());
        view.updateUI();
    }

    public void showPlayerInfo() {
    }

    public void showMap() {
    }

    public void showMenu() {
        this.listener.onShow(Shows.menu);
    }

    public void showTickets() {
        this.listener.onShow(Shows.tickets);
    }

    public void showChat() {
        this.listener.onShow(Shows.chat);
    }

}
