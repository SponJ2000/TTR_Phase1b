package gamePresenters;

import com.obfuscation.ttr_phase1b.activity.IPresenter;

public interface IGamePresenter extends IPresenter{

    void showMenu();
    void showPlayerInfo();
    void showTickets();
    void showChat();
    void showMap();
}
