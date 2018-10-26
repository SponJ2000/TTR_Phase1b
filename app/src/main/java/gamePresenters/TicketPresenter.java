package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.ITicketView;

public class TicketPresenter implements ITicketPresenter {

    private ITicketView view;
    private OnBackListener listener;

    public TicketPresenter(ITicketView view, OnBackListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Override
    public void onFinish() {
        listener.onBack();
    }

    @Override
    public void updateInfo(Object result) {
        view.updateUI();
    }

}
