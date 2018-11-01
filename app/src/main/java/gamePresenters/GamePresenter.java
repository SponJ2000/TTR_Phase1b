package gamePresenters;

import android.util.Log;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;
import com.obfuscation.ttr_phase1b.gameViews.IPlayerInfoView;

import communication.GameMap;
import communication.Player;
import communication.Result;
import communication.Route;
import model.FakeModel;
import model.IGameModel;
import model.ModelFacade;

public class GamePresenter implements IGamePresenter {

    private static String TAG = "obfuscate";

    private IPlayerInfoView playerInfoView;
    private IGameView view;
    private OnShowListener listener;
    private IGameModel model;

    public GamePresenter(IGameView view, OnShowListener listener) {
        this.view = view;
        view.setPresenter(this);
        this.listener = listener;
//        model = ModelFacade.getInstance();
        model = FakeModel.getInstance();
    }

    @Override
    public void updateInfo(Object result) {
        view.setPlayer(model.getPlayer());
        view.setDeckSize(model.getDeckSize());
        view.setIsTurn(model.isMyTurn());
        view.setCards(model.getCards());
        view.setFaceCards(model.getFaceCards());
//        view.setMap(model.getMap());
        view.setTickets(model.getTickets());
        view.updateUI();
        if(playerInfoView != null) {
            playerInfoView.setPlayers(model.getPlayers());
            playerInfoView.updateUI();
        }
    }

    @Override
    public void update() {
        model.updateCards();
        model.updateFaceCards();
        model.updateTickets();
    }

    @Override
    public GameMap getMap() {
        return model.getMap();
    }

    @Override
    public void selectRoute(Route route, Player player) {
        Result r = model.claimRoute(route, player);
        if(r.isSuccess()) {
            view.updateRoute(route);
        }
    }

    @Override
    public void showPlayerInfo(IPlayerInfoView view) {
        playerInfoView = view;
        if(playerInfoView != null) {
            playerInfoView.setPlayers(model.getPlayers());
            playerInfoView.updateUI();
        }

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
