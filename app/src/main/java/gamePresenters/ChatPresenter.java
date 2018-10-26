package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IChatView;

public class ChatPresenter implements IChatPresenter {

    private IChatView view;
    private IChatPresenter.OnBackListener listener;

    public ChatPresenter(IChatView view, IChatPresenter.OnBackListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Override
    public void updateInfo(Object result) {
        view.updateUI();
    }

    @Override
    public void goBack() {
        this.listener.onBack();
    }

    @Override
    public void onSend() {

    }

}
