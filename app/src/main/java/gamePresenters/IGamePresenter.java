package gamePresenters;

import android.app.Activity;
import android.os.Bundle;

import com.obfuscation.ttr_phase1b.activity.IPresenter;
import com.obfuscation.ttr_phase1b.gameViews.IPlayerInfoView;

import communication.GameMap;
import communication.Player;
import communication.Route;
import model.IGameModel;

public interface IGamePresenter extends IPresenter {

    void showMenu();
    void showPlayerInfo(IPlayerInfoView view);
    void selectTickets();
    void showTickets();
    void showChat();
    void showHistory();

    IGameModel getModel();
    void sendToast(String toast);

    /**
     *
     * @param index -1 for the deck, 0-4 for the face-up cards
     */
    void chooseCard(int index);
    void claimRoute(Route route, Player player);
    void onBack();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnShowListener {
        void onShow(Shows show, Bundle args);
    }

}
