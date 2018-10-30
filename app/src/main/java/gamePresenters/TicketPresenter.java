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
        this.listener = listener;
//        this.model = ModelFacade.getInstance();
        this.model = FakeModel.getInstance();
    }

    @Override
    public void onFinish(List<Ticket> tickets) {
        model.chooseTickets(tickets);
        listener.onBack();
    }

    @Override
    public void updateInfo(Object result) {
        view.setTickets(model.getTicketsToChoose());
        view.updateUI();
    }

}
