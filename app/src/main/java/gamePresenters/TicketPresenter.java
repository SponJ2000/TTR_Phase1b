package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.ITicketView;

import java.util.List;

import communication.Ticket;
import model.FakeModel;
import model.IGameModel;
import model.ModelFacade;

public class TicketPresenter implements ITicketPresenter {

    private ITicketView view;
    private OnBackListener listener;
    private IGameModel model;

    public TicketPresenter(ITicketView view, OnBackListener listener) {
        this.view = view;
        view.setPresenter(this);
        this.listener = listener;
//        model = ModelFacade.getInstance();
        model = FakeModel.getInstance();
    }

    @Override
    public void onFinish(List<Ticket> tickets) {
        model.chooseTickets(tickets);
        listener.onBack();
    }

    @Override
    public void updateInfo(Object result) {
        view.setTickets(model.getChoiceTickets());
        view.setIsTurn(model.isMyTurn());
        view.updateUI();
    }

    @Override
    public void update() {
        model.updateChoiceTickets();
    }

}
