package gamePresenters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.gameViews.IGameView;
import com.obfuscation.ttr_phase1b.gameViews.IPlayerInfoView;

import communication.Card;
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
        if(result != null && result.getClass().equals(Result.class)) {
            state.finish((Result) result);
        }
        view.setPlayer(model.getPlayer());
        if(model.getPlayer() == null) {
            Log.d(TAG, "user is null");
        }if(model.isMyTurn() && state.getClass().equals(NotTurn.class)) {
            setState(new TurnNoSelection(this));
        }
        if(playerInfoView == null) {
            view.setTurn(model.isMyTurn());
            view.setDeckSize(model.getDeckSize());
            view.setCards(model.getCards());
            view.setFaceCards(model.getFaceCards());
            view.setMap(model.getMap());
            view.setTickets(model.getTickets());
            view.updateUI();
        }else {
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
    public void showToast(String toast) {
        view.sendToast(toast);
    }

    @Override
    public void claimRoute(Route route, Player player) {
        state.claimRoute(route, player);
    }

    @Override
    public void showPlayerInfo(IPlayerInfoView view) {
        if(view == null) {
            this.listener.onShow(Shows.playerInfo, null);
        }else {
            Log.d(TAG, "showPlayerInfo: " + model.getPlayers());
            playerInfoView = view;
            playerInfoView.setPresenter(this);
            playerInfoView.setPlayers(model.getPlayers());
            playerInfoView.updateUI();
        }
    }


    public void onChange(Activity activity) {
        switch (changeIndex) {
            case 0:
                Toast.makeText(activity, "update mPlayer points", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(activity, "add claimed mRoute", Toast.LENGTH_SHORT).show();
                List<Route> routes = model.getMap().getRoutes();
                Route r = routes.get(ThreadLocalRandom.current().nextInt(0, routes.size()));

                Player player = model.getPlayers().get(ThreadLocalRandom.current().nextInt(0, model.getPlayers().size()));
                model.claimRoute(r, player, null);
                view.updateRoute(r);
                break;
            case 8:
                if (model.isMyTurn()) {
                    Toast.makeText(activity, "Not your turn", Toast.LENGTH_SHORT).show();
                    model.setMyTurn(false);
                    state = new NotTurn(this);
                } else {
                    Toast.makeText(activity, "Your turn!", Toast.LENGTH_SHORT).show();
                    model.setMyTurn(true);
                    state = new TurnNoSelection(this);
                }


        }
        ++changeIndex;
        changeIndex %= 9;
        updateInfo(true);
    }

    public void showMap() {
    }

    @Override
    public IGameModel getModel() {
        return model;
    }

    @Override
    public void chooseCard(int index) {
//        model.chooseCard(index);
        if (index == -1) {
            state.selectDeck();
        }
        else {
            state.selectFaceUp(index);
        }
    }

    @Override
    public void onBack() {
        this.listener.onShow(Shows.map, null);
    }

    public void showMenu() {
        this.listener.onShow(Shows.menu, null);
    }

    public void selectTickets() {
        state.selectTicketsButton();
    }

    public void showChat() {
        this.listener.onShow(Shows.chat, null);
    }

    @Override
    public void sendToast(String toast) {
        view.sendToast(toast);
    }



    class ITurnState {
        void selectFaceUp(int index){}
        void selectDeck(){}
        void selectTicketsButton(){}
        void claimRoute(Route route, Player player){}
        void finish(Result result){}
    }

    class NotTurn extends ITurnState {
        private GamePresenter wrapper;
        NotTurn(GamePresenter wrapper) {
            this.wrapper = wrapper;
        }
    }

    class TurnNoSelection extends ITurnState {

        private GamePresenter wrapper;
        private boolean isSelectOne;
        private boolean actionSelected;

        TurnNoSelection(GamePresenter wrapper) {
            this.wrapper = wrapper;
            isSelectOne = false;
            actionSelected = false;
        }

        @Override
        void selectFaceUp(int index) {
            Log.d(TAG, "selectFaceUp: " + model.checkCard(index));
            if (model.checkCard(index).equals(GameColor.LOCOMOTIVE)) {
                Log.d(TAG, "selectFaceUp locomotive");
                model.chooseCard(index);
                actionSelected = true;
                model.endTurn();
                wrapper.setState(new NotTurn(wrapper));
            }
            else {
                wrapper.getModel().chooseCard(index);
                isSelectOne = true;
                wrapper.setState(new TurnOneCard(wrapper));
            }
        }

        @Override
        void selectDeck() {
            model.chooseCard(-1);
            isSelectOne = true;
            wrapper.setState(new TurnOneCard(wrapper));

        }

        @Override
        public void selectTicketsButton() {
            listener.onShow(Shows.tickets, null);
            wrapper.setState(new NotTurn(wrapper));
            actionSelected = true;
//            wrapper.setState(new TurnNoTickets(wrapper));
        }

        @Override
        public void claimRoute(Route route, Player player) {
            //Check if a player has sufficient cards

            Object list = model.checkRouteCanClaim(route.getColor(), route.getLength());

            if (list instanceof String) {
                wrapper.sendToast((String) list);
            }
            else {
                ArrayList<Card> cardsToUse;
                if (route.getColor() == GameColor.GREY) {
                    Bundle args = new Bundle();
                    args.putSerializable("route", route);
                    args.putInt("cardsToSelect", route.getLength());
                    listener.onShow(Shows.cardSelect, args);
                    wrapper.setState(new NotTurn(wrapper));
                    return;
                }
                else {
                    cardsToUse = (ArrayList<Card>) list;
                }

                //Result result = wrapper.getModel().claimRoute(route, player, cardsToUse);
                model.claimRoute(route, player, cardsToUse);
                actionSelected = true;

                model.endTurn();
                wrapper.setState(new NotTurn(wrapper));

//                if(result.isSuccess()) {
//                    model.endTurn();
//                    wrapper.setState(new NotTurn(wrapper));
//                }
//                else {
//                    wrapper.sendToast(result.getErrorInfo());
//                }
            }
        }

        @Override
        void finish(Result result) {
            if(result.isSuccess()) {
                if(isSelectOne) {
                    Log.d(TAG, "to turnOneCard");
                    wrapper.setState(new TurnOneCard(wrapper));
                    isSelectOne = false;
                }else if(actionSelected) {
                    Log.d(TAG, "finish turn");
                    model.endTurn();
                    wrapper.setState(new NotTurn(wrapper));
                }
            }else {
                wrapper.sendToast(result.getErrorInfo());
            }
        }
    }

    class TurnOneCard extends ITurnState {

        private GamePresenter wrapper;
        private boolean actionSelected;

        public TurnOneCard(GamePresenter wrapper) {
            this.wrapper = wrapper;
            actionSelected = false;
        }

        @Override
        public void selectFaceUp(int index) {
            Log.d(TAG, "selectFaceUp: " + wrapper.getModel().checkCard(index));
            if (model.checkCard(index).equals(GameColor.LOCOMOTIVE)) {
                wrapper.sendToast("You can't select a locomotive card as your second choice.");
            }
            else {
                model.chooseCard(index);
                model.endTurn();
                wrapper.setState(new NotTurn(wrapper));
                actionSelected = true;
            }
        }

        @Override
        public void selectDeck() {
            wrapper.getModel().chooseCard(-1);
            model.endTurn();
            wrapper.setState(new NotTurn(wrapper));
            actionSelected = true;
        }

        @Override
        void finish(Result result) {
            if(result.isSuccess()) {
                if(actionSelected) {
                    Log.d(TAG, "finish turn");
                    model.endTurn();
                    wrapper.setState(new NotTurn(wrapper));
                }
            }else {
                wrapper.sendToast(result.getErrorInfo());
            }
        }
    }

}