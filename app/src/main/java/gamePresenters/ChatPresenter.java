package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IChatView;

import communication.Message;
import model.FakeModel;
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
        model = ModelFacade.getInstance();
        view.setMessages(model.getMessages());
        view.setUsername(model.getUserName());
//        model = FakeModel.getInstance();
    }

    @Override
    public void updateInfo(Object result) {
        view.setMessages(model.getMessages());
        view.setUsername(model.getUserName());
        view.updateUI();
    }

    @Override
    public void update() {
        model.updateMessages();
    }

    @Override
    public void goBack() {
        listener.onBack();
    }

    @Override
    public void onSend(Message message) {
        model.sendMessage(message);
    }

}
