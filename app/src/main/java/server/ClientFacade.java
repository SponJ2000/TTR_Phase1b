package server;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import communication.Card;
import communication.Game;
import communication.GameClient;
import communication.IClient;
import communication.IPlayer;
import communication.Message;
import communication.Player;
import communication.PlayerOpponent;
import communication.PlayerUser;
import communication.Serializer;
import communication.Ticket;
import communication.GameColor;
import model.ModelFacade;
import model.ModelRoot;
import model.DisplayState;

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
                , new Object[] {"HELLO", new ArrayList<>(Arrays.asList(new Card(GameColor.BLUE), new Card(GameColor.BLACK)))});

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
    public void updateGameLobbyList(String gameID) {
        //sever will never call this command
    }

    @Override
    public void updateGame(String gameID) {
        //sever will never call this command
    }

    @Override
    public void initializeGame(GameClient gameClient) {
        try {
            ArrayList<Ticket> ticketsToChoose = ModelRoot.getInstance().getGame().getPlayerUser().getTicketToChoose();
            ModelRoot.getInstance().setGame(gameClient);
            if (ticketsToChoose != null) {
                if (ticketsToChoose.size() > 0) {

                    if (ModelRoot.getInstance().getGame() != null) {
                        if (ModelRoot.getInstance().getGame().getPlayerUser() != null) {
                            ModelRoot.getInstance().getGame().getPlayerUser().setTicketToChoose(ticketsToChoose);
                        }
                    }
                }
            }

            Game g = ModelRoot.getInstance().getGame();

            ModelRoot.getInstance().setDisplayState(DisplayState.GAME);
            ModelFacade.getInstance().getChoiceTickets();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    //it is only access the current
    public void updatePlayerPoints(String gameID, String plyerID, Integer points) {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            IPlayer p = g.getPlayerByUserName(plyerID);
            if (p != null) {
                ((Player) p).setPoint(points);
            }
        }
    }

    @Override
    public void updateTrainCards(String gameID, List<Card> trainCards) {
        System.out.println("UPDATE TRAIN CARD GETTING CALLED");
        System.out.println(trainCards.size());
        Serializer serializer = new Serializer();
        ArrayList<Card> cardD = new ArrayList<Card>();
        for(Object O: trainCards) {
            cardD.add(serializer.deserializeCard(O.toString()));
        }
        try {
            GameClient g = ModelRoot.getInstance().getGame();

            if (g != null) {
                PlayerUser p = g.getPlayerUser();
                if (p != null) {
                    p.setCards(cardD);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateTickets(String gameID, List<Ticket> tickets) {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            PlayerUser p = g.getPlayerUser();
            if (p != null) {
                p.setTicketToChoose((ArrayList<Ticket>) tickets);
            }
        }
    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, Integer cardNum) {
        ModelRoot m = ModelRoot.getInstance();
        m.getGame().getPlayerUser();
    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, Integer carNum) {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            IPlayer p = g.getPlayerByUserName(playerID);
            if (p != null) {
                ((PlayerOpponent) p).setTrainCarNum(carNum);
            }
        }
    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, Integer cardNum) {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            IPlayer p = g.getPlayerByUserName(playerID);
            if (p != null) {
                ((PlayerOpponent) p).setTicketNum(cardNum);
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


    public void updateTrainDeck(String gameID, List<Card> faceCards, Integer downCardNum) {
        Game g = ModelRoot.getInstance().getGame();
        Serializer serializer =  new Serializer();
        ArrayList<Card> cardD = new ArrayList<Card>();
        for(Object O: faceCards) {
            cardD.add(serializer.deserializeCard(O.toString()));
        }

        if (g != null) {
            g.setFaceUpTrainCarCards(cardD);

        }
    }


    //upate number of card in the deck
    @Override
    public void updateDestinationDeck(String gameID, Integer cardNum) {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            ModelRoot.getInstance().getGame().setTrainCardDeckSize(cardNum);

        }
    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            ((Player)g.getPlayerByUserName(playerID)).addRouteAsClaimed(routeID);
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
