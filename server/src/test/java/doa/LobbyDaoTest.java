package doa;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import communication.LobbyGame;
import communication.Player;
import dao.ILobbyDao;
import dao.tsv.TSVDaoFactory;

public class LobbyDaoTest {

    @Test
    public void TSVTest() {
        System.out.println("Start");

        ILobbyDao dao = new TSVDaoFactory().getLobbyDao();

        LobbyGame game = createGame();
        LobbyGame comp = createGame();
        assert(comp.equals(game));
        dao.addLobby(game.getGameID(), game);
        List<LobbyGame> games = dao.getLobbies();
        System.out.println("" + games.size());
        assert(games.size() == 1);
        System.out.println(game);
        System.out.println(games.get(0));
        assert(games.get(0).equals(game));

        System.out.println("No problems");
    }

    private LobbyGame createGame() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("bob"));
        players.add(new Player("babe"));
        players.add(new Player("buddy"));
        players.add(new Player("buns"));

        LobbyGame game = new LobbyGame("bob", "mygameid", players, 3);
        return game;
    }

}
