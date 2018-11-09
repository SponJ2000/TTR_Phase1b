package gamePresenters;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;
import com.obfuscation.ttr_phase1b.gameViews.IPlayerInfoView;

import communication.GameColor;
import communication.GameMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import communication.City;
import communication.Player;
import communication.Result;
import communication.Route;
import communication.Ticket;
import model.FakeModel;
import model.IGameModel;
import model.ModelFacade;

public class GamePresenter implements IGamePresenter {

    private static String TAG = "gamePres";

    private static int changeIndex = 0;

    private IPlayerInfoView playerInfoView;
    private IGameView view;
    private OnShowListener listener;
    private IGameModel model;

    private ITurnState state;

    public GamePresenter(IGameView view, OnShowListener listener) {
        this.view = view;
        view.setPresenter(this);
        this.listener = listener;
        model = ModelFacade.getInstance();
//        model = FakeModel.getInstance();

        state = new NotTurn(this);
    }

    public void setState(ITurnState state){
        this.state = state;
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
//        Result r = model.claimRoute(route, player);
//        if(r.isSuccess()) {
//            view.updateRoute(route);
//        }

        state.selectRoute(route);
    }

    @Override
    public void showPlayerInfo(IPlayerInfoView view) {
        if(view == null) {
            this.listener.onShow(Shows.playerInfo);
        }else {
            Log.d(TAG, "showPlayerInfo: " + model.getPlayers());
            playerInfoView = view;
            playerInfoView.setPresenter(this);
            playerInfoView.setPlayers(model.getPlayers());
            playerInfoView.updateUI();
        }
    }

    @Override
    public void onChange(Activity activity) {
        switch (changeIndex) {
            case 0:
                Toast.makeText(activity, "update player points", Toast.LENGTH_SHORT).show();
                model.addPoints(8);
                break;
            case 1:
                Toast.makeText(activity, "add train cards", Toast.LENGTH_SHORT).show();
                model.chooseCard(2);
                break;
            case 2:
                Toast.makeText(activity, "remove train cards", Toast.LENGTH_SHORT).show();
                model.useCards(GameColor.GREEN, 1);
                break;
            case 3:
                Toast.makeText(activity, "add tickets", Toast.LENGTH_SHORT).show();
                ArrayList<Ticket> tickets = new ArrayList<>();
                tickets.add(new Ticket(new City("berlin",0,0), new City("helsinki",0,0), 8));
                tickets.add(new Ticket(new City("berlin",0,0), new City("london",0,0), 12));
                model.addTickets(tickets);
                break;
            case 4:
                Toast.makeText(activity, "remove tickets", Toast.LENGTH_SHORT).show();
                model.removeTicket(1);
                model.removeTicket(1);
                break;
            case 5:
                Toast.makeText(activity, "update opponent cards and tickets", Toast.LENGTH_SHORT).show();
                model.updateOpponent();
                break;
            case 6:
                Toast.makeText(activity, "update face cards and deck", Toast.LENGTH_SHORT).show();
                model.updateFaceCards();
                break;
            case 7:
                Toast.makeText(activity, "add claimed route", Toast.LENGTH_SHORT).show();
                List<Route> routes = model.getMap().getRoutes();
                Route r = routes.get(ThreadLocalRandom.current().nextInt(0, routes.size()));

                Player player = model.getPlayers().get(ThreadLocalRandom.current().nextInt(0, model.getPlayers().size()));
                if( model.claimRoute(r, player).isSuccess()) {
                    view.updateRoute(r);
                }
                break;
            case 8:
                if (model.isMyTurn()) {
                    Toast.makeText(activity, "Not your turn", Toast.LENGTH_SHORT).show();
                    model.setMyTurn(false);
                    view.setIsTurn(false);
                } else {
                    Toast.makeText(activity, "Your turn!", Toast.LENGTH_SHORT).show();
                    model.setMyTurn(true);
                    view.setIsTurn(true);
                }


        }
        ++changeIndex;
        changeIndex %= 9;
        updateInfo(true);
    }

    public void showMap() {
    }

    @Override
    public void chooseCard(int index) {
//        model.chooseCard(index);
        if (index == 0) {
            state.selectDeck();
        }
        else {
            state.selectFaceUp(index);
        }
    }

    @Override
    public void onBack() {
        this.listener.onShow(Shows.map);
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


class ITurnState {

    void selectFaceUp(int index){}
    void selectDeck(){}
    void selectTicketsButton(){}
    void selectTicket(Object ticket){}
    void deselectTicket(Object ticket){}
    void requestTickets(){}
    void selectRoute(Route route){}
    void deselectRoute(){}
    void claimRoute(Route route){}
}

class NotTurn extends ITurnState {

    private GamePresenter wrapper;

    public NotTurn(GamePresenter wrapper) {
        this.wrapper = wrapper;
    }
}

class TurnNoSelection extends ITurnState {

    private GamePresenter wrapper;

    public TurnNoSelection(GamePresenter wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void selectFaceUp(int index) {
        //TODO: check if locomotive
        //TODO: update hand and deck
        wrapper.setState(new TurnOneCard(wrapper));
    }

    @Override
    public void selectDeck() {
        //TODO: update hand and deck
        wrapper.setState(new TurnOneCard(wrapper));
    }

    @Override
    public void selectTicketsButton() {
        wrapper.setState(new TurnNoTickets(wrapper));
    }

    @Override
    public void selectRoute(Route route) {
        wrapper.setState(new TurnRouteSelected(wrapper));
    }
}

class TurnNoTickets extends ITurnState {

    private GamePresenter wrapper;

    public TurnNoTickets(GamePresenter wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void selectTicket(Object ticket) {
        wrapper.setState(new TurnYesTickets(wrapper));
    }
}

class TurnYesTickets extends ITurnState {

    private GamePresenter wrapper;

    public TurnYesTickets(GamePresenter wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void selectTicket(Object ticket) {
        //TODO: add ticket to selected tickets
    }

    @Override
    public void deselectTicket(Object ticket) {
        //TODO: check if you've got any tickets left
        wrapper.setState(new TurnNoTickets(wrapper));
    }

    @Override
    void requestTickets() {
        //TODO: send request for tickets, then update UI
        wrapper.setState(new NotTurn(wrapper));
    }
}

class TurnRouteSelected extends ITurnState {

    private GamePresenter wrapper;

    public TurnRouteSelected(GamePresenter wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void deselectTicket(Object ticket) {
        wrapper.setState(new TurnNoSelection(wrapper));
    }

    @Override
    public void selectRoute(Route route) {

    }

    @Override
    public void deselectRoute() {

    }

    @Override
    public void claimRoute(Route route) {
        //TODO: check if you have enough cards
        //TODO: if so, send request to the server and update UI
        wrapper.setState(new NotTurn(wrapper));
    }
}

class TurnOneCard extends ITurnState {

    private GamePresenter wrapper;

    public TurnOneCard(GamePresenter wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void selectFaceUp(int index) {
        //TODO: Check if locomotive
        //TODO: If not, send request, update UI
        wrapper.setState(new NotTurn(wrapper));
    }

    @Override
    public void selectDeck() {
        //TODO: send request to server, update UI
        wrapper.setState(new NotTurn(wrapper));
    }
}