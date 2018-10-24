package gamePresenters;

import com.obfuscation.ttr_phase1b.activity.IPresenter;

public interface IMenuPresenter extends IPresenter {
    void goBack();
    void logout();
    void onGameSelection();
}
