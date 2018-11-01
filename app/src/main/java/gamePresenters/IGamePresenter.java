package gamePresenters;

import android.app.Activity;

import com.obfuscation.ttr_phase1b.activity.IPresenter;
import com.obfuscation.ttr_phase1b.gameViews.IPlayerInfoView;

import communication.Player;

public interface IGamePresenter extends IPresenter{

    void showMenu();
    void showPlayerInfo(IPlayerInfoView view);
    void showTickets();
    void showChat();
    void showMap();
    void chooseCard(int index);

    void onChange(Activity activity);

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnShowListener {
        void onShow(Shows show);
    }

}
