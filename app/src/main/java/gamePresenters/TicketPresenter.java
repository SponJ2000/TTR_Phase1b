package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.ITicketView;

import model.IGameModel;
import model.ModelFacade;

public class TicketPresenter implements ITicketPresenter {

    private ITicketView view;
    private OnBackListener listener;
    private IGameModel model;

    public TicketPresenter(ITicketView view, OnBackListener listener) {
        this.view = view;
        this.listener = listener;
        this.model = ModelFacade.getInstance();
    }

    @Override
    public void onFinish() {
        listener.onBack();
    }

    @Override
    public void updateInfo(Object result) {
        view.setTickets(model.getTickets());
        view.updateUI();
    }

}
