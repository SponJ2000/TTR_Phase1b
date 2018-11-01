package server;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import communication.Card;
import communication.Game;
import communication.IClient;
import communication.Message;
import communication.Player;
import communication.Ticket;
import communication.CardColor;
import model.ModelRoot;
import model.State;

/**
 * Created by hao on 10/25/18.
 */

public class ClientFacade implements IClient{

    //TEST
    public static void main(String[] args) {
        GenericCommand command = new GenericCommand(
                "server.ClientFacade"
                , "updateTrainCards"
                , new String[]{String.class.getName(), List.class.getName()}
                , new Object[] {"HELLO", new ArrayList<>(Arrays.asList(new Card(CardColor.BLUE), new Card(CardColor.BLACK)))});

        try {
            command.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static ClientFacade instance = new ClientFacade();
    public static ClientFacade getInstance() {
        return instance;
    }

    private ClientFacade() {

    }

    @Override
    public void updateGameList(String gameID) {
        //sever will never call this command
    }

    @Override
    public void updateGame(String gameID) {
        //sever will never call this command
    }

    @Override
    public void initializeGame(Game game) {
        ModelRoot.getInstance().setGame(game);
        ModelRoot.getInstance().getGame().setGameStarted(true);
        ModelRoot.getInstance().setState(State.GAME);
    }

    @Override
    //it is only access the current
    public void updatePlayerPoints(String gameID, String plyerID, Integer points) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            Player p = g.getPlayerbyID(plyerID);
            if (p != null) {
                p.setPoint(points);
            }
        }
    }

    @Override
    public void updateTrainCards(String gameID, List<Card> trainCards) {
        try {
            Game g = ModelRoot.getInstance().getGame();

            if (g != null) {
                Player p = g.getUserPlayer(ModelRoot.getInstance().getUserName());
                if (p != null) {
                    p.setCards((ArrayList<Card>) trainCards);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateTickets(String gameID, List<Ticket> tickets) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            Player p = g.getUserPlayer(ModelRoot.getInstance().getUserName());
            if (p != null) {
                p.setTickets((ArrayList<Ticket>) tickets);
            }
        }
    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, Integer cardNum) {
        ModelRoot m = ModelRoot.getInstance();
        m.getGame().getPlayerbyID(playerID).setCardNum(cardNum);
    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, Integer carNum) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            Player p = g.getPlayerbyID(playerID);
            if (p != null) {
                p.setTrainCarNum(carNum);
            }
        }
    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, Integer cardNum) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            Player p = g.getPlayerbyID(playerID);
            if (p != null) {
                p.setTicketNum(cardNum);
            }
        }
    }

    @Override
    public void updateTrainDeck(String gameID, ArrayList<Card> faceCards, Integer downCardNum) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            g.setFaceUpTrainCarCards(faceCards);
        }
    }

    //upate number of card in the deck
    @Override
    public void updateDestinationDeck(String gameID, Integer cardNum) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            ModelRoot.getInstance().getGame().setTicketsRemainNum(cardNum);

        }
    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            g.getPlayerbyID(playerID).addRouteAsClaimed(routeID);
        }
    }

    @Override
    public void updateChat(String gameID, Message m) {
        System.out.println("trying to insert"+ m.getText());
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            g.insertMessage(m);
            System.out.println("inserted a new chat"+ m.getText());
        }

        g = ModelRoot.getInstance().getGame();
        if (g != null) {
            System.out.println("message has size of:  " + g.getMessages().size());

        }
    }
}
