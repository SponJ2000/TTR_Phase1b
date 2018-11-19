package gamePresenters;

import android.app.Activity;
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
import model.FakeModel;
import model.IGameModel;
import model.ModelFacade;

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
        }if(model.isMyTurn()) {
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
        this.listener.onShow(Shows.map);
    }

    public void showMenu() {
        this.listener.onShow(Shows.menu);
    }

    public void selectTickets() {
        state.selectTicketsButton();
    }

    public void showChat() {
        this.listener.onShow(Shows.chat);
    }

    @Override
    public void sendToast(String toast) {
        view.sendToast(toast);
    }

    @Override
    public List<Card> playerChooseCards(int length) {
        return null;
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

        TurnNoSelection(GamePresenter wrapper) {
            this.wrapper = wrapper;
            isSelectOne = false;
        }

        @Override
        void selectFaceUp(int index) {
            if (wrapper.getModel().checkCard(index) == GameColor.LOCOMOTIVE) {
                Log.d(TAG, "selectFaceUp locomotive");
                wrapper.getModel().chooseCard(index);
            }
            else {
                Log.d(TAG, "selectFaceUp " + model.checkCard(index));
                wrapper.getModel().chooseCard(index);
                isSelectOne = true;
            }
        }

        @Override
        void selectDeck() {
            wrapper.getModel().chooseCard(-1);
            isSelectOne = true;
        }

        @Override
        void selectTicketsButton() {
            listener.onShow(Shows.tickets);
            wrapper.setState(new NotTurn(wrapper));
//            wrapper.setState(new TurnNoTickets(wrapper));
        }

        @Override
        void claimRoute(Route route, Player player) {
            //Check if a player has sufficient cards

            Object list = wrapper.getModel().checkRouteCanClaim(route.getColor(), route.getLength());

            if (list instanceof String) {
                wrapper.sendToast((String) list);
            }
            else {
                ArrayList<Card> cardsToUse;
                if (route.getColor() == GameColor.GREY) {
                    cardsToUse = (ArrayList<Card>) wrapper.playerChooseCards(route.getLength());
                }
                else {
                    cardsToUse = (ArrayList<Card>) list;
                }

                wrapper.getModel().claimRoute(route, player, cardsToUse);
            }
        }

        @Override
        void finish(Result result) {
            if(result.isSuccess()) {
                if(isSelectOne) {
                    Log.d(TAG, "to turnOneCard");
                    wrapper.setState(new TurnOneCard(wrapper));
                    isSelectOne = false;
                }else {
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

        public TurnOneCard(GamePresenter wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public void selectFaceUp(int index) {
            if (wrapper.getModel().checkCard(index) == GameColor.LOCOMOTIVE) {
                wrapper.sendToast("You can't select a locomotive card as your second choice.");
            }
            else {
                wrapper.getModel().chooseCard(index);
            }
        }

        @Override
        public void selectDeck() {
            wrapper.getModel().chooseCard(-1);
        }

        @Override
        void finish(Result result) {
            if(result.isSuccess()) {
                Log.d(TAG, "finish turn");
                model.endTurn();
                wrapper.setState(new NotTurn(wrapper));
            }else {
                wrapper.sendToast(result.getErrorInfo());
            }
        }
    }

}