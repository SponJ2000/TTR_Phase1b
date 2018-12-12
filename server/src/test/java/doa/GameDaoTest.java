package doa;

import com.obfuscation.server.GenericCommand;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import communication.Card;
import communication.GameColor;
import communication.GameFactory;
import communication.GameServer;
import communication.PlayerUser;
import communication.Ticket;
import dao.IGameDao;
import dao.tsv.TSVDaoFactory;

public class GameDaoTest {

    @Test
    public void TSVTest() {
        System.out.println("Start");

        IGameDao dao = new TSVDaoFactory().getGameDao();

        GameServer game = createGame();
        dao.addGame(game.getGameID(), game);
        List<GameServer> games = dao.getGames();
        assert(games.size() == 1);
        assert(games.get(0).equals(game));

        System.out.println("No problems");
    }

    private GameServer createGame() {
        ArrayList<PlayerUser> players = new ArrayList<>();
        players.add(new PlayerUser("bob"));
        players.add(new PlayerUser("babe"));
        players.add(new PlayerUser("buddy"));
        players.add(new PlayerUser("buns"));

        GameServer game = new GameServer();
        game.setGameID("my_game_id");
        game.setPlayers(players);
        game.setCurrentPlayer(players.get(0).getPlayerName());


        //Assign PlayerColors
        List<GameColor> colors = Arrays.asList(GameColor.PLAYER_BLACK, GameColor.PLAYER_BLUE,
                GameColor.PLAYER_PURPLE, GameColor.PLAYER_RED, GameColor.PLAYER_YELLOW);
        Collections.shuffle(colors);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setPlayerColor(colors.get(i));
        }

        //initialize traincards
        ArrayList<Card> trainCards = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Card locomotiveCard = new Card(GameColor.LOCOMOTIVE);
            trainCards.add(locomotiveCard);
        }
        for (int i = 0; i < 12; i++) {
            Card purpleCard = new Card(GameColor.PURPLE);
            Card blueCard = new Card(GameColor.BLUE);
            Card orangeCard = new Card(GameColor.ORANGE);
            Card whiteCard = new Card(GameColor.WHITE);
            Card greenCard = new Card(GameColor.GREEN);
            Card redCard = new Card(GameColor.RED);
            Card blackCard = new Card(GameColor.BLACK);
            Card yellowCard = new Card(GameColor.YELLOW);
            trainCards.add(purpleCard);
            trainCards.add(blueCard);
            trainCards.add(orangeCard);
            trainCards.add(whiteCard);
            trainCards.add(greenCard);
            trainCards.add(redCard);
            trainCards.add(blackCard);
            trainCards.add(yellowCard);
        }
        Collections.shuffle(trainCards);
        game.setTrainCards(trainCards);

        // set faceuptrain cards - if 3 are locomotives, then shuffle again
        ArrayList<Card> faceUpTrainCards = null;
        while (true) {
            faceUpTrainCards = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Card card = trainCards.get(0);
                faceUpTrainCards.add(card);
                trainCards.remove(0);
            }

            int counter = 0;
            for (int i = 0; i < 5; i++) {
                if (faceUpTrainCards.get(i).getColor() == GameColor.LOCOMOTIVE) {
                    counter++;
                }
            }
            if (counter >= 3) {
                //if locomotive is more than 3, discard them and shuffle again
                game.getDiscardDeck().addAll(faceUpTrainCards);
                faceUpTrainCards.clear();
            }
            else {
                break;
            }
        }
        game.setFaceUpTrainCarCards(faceUpTrainCards);

        ArrayList<Ticket> tickets = GameFactory.getAllTickets();
        Collections.shuffle(tickets);
        game.setTickets(tickets);

        //set player train cards
        tickets = game.getTickets();
        ArrayList<Ticket> playerTickets;
        ArrayList<Card> playerTrainCards;
        for (PlayerUser player : game.getPlayers()) {
            playerTrainCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card card = trainCards.get(0);
                playerTrainCards.add(card);
                trainCards.remove(0);
            }
            player.setCards(playerTrainCards);

            if (tickets.size() < 3) {
                playerTickets =  tickets;
            }else {
                playerTickets = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    playerTickets.add(tickets.get(0));
                    tickets.remove(0);
                }
            }
            player.setTickets(playerTickets);
        }

        return game;
    }

}
