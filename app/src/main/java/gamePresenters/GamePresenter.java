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
import model.IModelObservable;
import model.IModelObserver;
import model.ModelFacade;
import model.ModelRoot;

public class GamePresenter implements IGamePresenter {

    private static String TAG = "gamePres";

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
        state = new NotTurn(this);
    }

    public void setState(ITurnState state){
        this.state = state;
    }

    @Override
    public void updateInfo(Object result) {
        if(model.isGameEnded()) {
            listener.onShow(Shows.score, null);
        }

        if(result != null && (result instanceof Result)) {
            Result r = (Result) result;
            if (r.getErrorInfo().equals("from claim route")) {
                System.out.println("called update from claim route");
            }
        }
        if(result != null && result.getClass().equals(Result.class)) {
            state.finish((Result) result);
        }
        else if(result != null && result.getClass().equals(Route.class)){
            System.out.println("in update infor undate rout");
            view.setMap(model.getMap());
            view.updateRoute((Route) result);
        }
        view.setPlayer(model.getPlayer());
        if(model.getPlayer() == null) {
            Log.d(TAG, "user is null");
        }if(model.isMyTurn() && state.getClass().equals(NotTurn.class)) {
            setState(new TurnNoSelection(this));
        }else if(!model.isMyTurn() && !state.getClass().equals(NotTurn.class)) {
            setState(new NotTurn(this));
        }
        if(playerInfoView == null) {
            view.setLastTurn(model.isLastTurn());
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
            System.out.println("CLAIMED");
            Object list = model.checkRouteCanClaim(route);

            if (list instanceof String) {
                System.out.println("apprently it wont go to claim");
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

                System.out.println("trying to call model to claim route");
                actionSelected = true;
                model.claimRoute(route, player, cardsToUse);
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