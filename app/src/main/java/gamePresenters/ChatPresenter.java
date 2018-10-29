package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IChatView;

import communication.Message;
import model.IGameModel;
import model.ModelFacade;

public class ChatPresenter implements IChatPresenter {

    private IChatView view;
    private IChatPresenter.OnBackListener listener;
    private IGameModel model;

    public ChatPresenter(IChatView view, IChatPresenter.OnBackListener listener) {
        this.view = view;
        view.setPresenter(this);
        this.listener = listener;
        this.model = ModelFacade.getInstance();
    }

    @Override
    public void updateInfo(Object result) {
        view.setMessages(model.getMessages());
        view.updateUI();
    }

    @Override
    public void goBack() {
        this.listener.onBack();
    }

    @Override
    public void onSend(Message message) {

    }

}
