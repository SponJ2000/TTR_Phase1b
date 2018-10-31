package gamePresenters;

import android.util.Log;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;

import model.FakeModel;
import model.IGameModel;
import model.ModelFacade;

public class GamePresenter implements IGamePresenter {

    private static String TAG = "obfuscate";

    private IGameView view;
    private OnShowListener listener;
    private IGameModel model;

    public GamePresenter(IGameView view, OnShowListener listener) {
        this.view = view;
        view.setPresenter(this);
        this.listener = listener;
        model = ModelFacade.getInstance();
//        model = FakeModel.getInstance();
        view.setUsername(model.getUserName());
    }


    @Override
    public void updateInfo(Object result) {
        view.setIsTurn(model.isMyTurn());
        view.setCards(model.getCards());
        view.setFaceCards(model.getFaceCards());
        view.setMap(model.getMap());
        view.setTickets(model.getTickets());
        view.updateUI();
    }

    @Override
    public void update() {
        model.updateCards();
        model.updateFaceCards();
        model.updateTickets();
    }

    public void showPlayerInfo() {
    }

    public void showMap() {
    }

    @Override
    public void chooseCard(int index) {
        model.chooseCard(index);
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
