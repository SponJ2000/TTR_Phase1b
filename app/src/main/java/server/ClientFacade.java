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
import communication.Serializer;
import communication.Ticket;
import communication.GameColor;
import model.ModelFacade;
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
    public void updateGameList(String gameID) {
        //sever will never call this command
    }

    @Override
    public void updateGame(String gameID) {
        //sever will never call this command
    }

    @Override
    public void initializeGame(Game game) {
        try {
            ArrayList<Ticket> tickets = ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).getTicketToChoose();
            ModelRoot.getInstance().setGame(game);
            if (tickets != null) {
                if (tickets.size() > 0) {

                    if (ModelRoot.getInstance().getGame() != null) {
                        if (ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()) != null) {
                            ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).setTicketToChoose(tickets);
                        }
                    }
                }
            }
            System.out.println("in printing game detail");

            Game g = ModelRoot.getInstance().getGame();

            List<Player> players = g.getPlayers();

            System.out.println("has player: " + players.size());
            for (Player p : players) {
                System.out.println("player get player color");
                System.out.println("layer color is: " + p.getPlayerColor());
                if (p.getPlayerColor() == null) {
                    System.out.println("player color is null");
                }
            }

            ModelRoot.getInstance().setState(State.GAME);
            ModelFacade.getInstance().getChoiceTickets();
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        System.out.println("UPDATE TRAIN CARD GETTING CALLED");
        System.out.println(trainCards.size());
        Serializer serializer = new Serializer();
        ArrayList<Card> cardD = new ArrayList<Card>();
        for(Object O: trainCards) {
            cardD.add(serializer.deserializeCard(O.toString()));
        }
        try {
            Game g = ModelRoot.getInstance().getGame();

            if (g != null) {
                Player p = g.getUserPlayer(ModelRoot.getInstance().getUserName());
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
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            Player p = g.getUserPlayer(ModelRoot.getInstance().getUserName());
            if (p != null) {
                p.setTicketToChoose((ArrayList<Ticket>) tickets);
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
