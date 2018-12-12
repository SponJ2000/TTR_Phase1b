package doa;

import org.junit.Test;

import communication.GameServer;
import dao.IGameDao;
import dao.tsv.TSVDaoFactory;

public class GameDaoTest {

    @Test
    public void TSVTest() {
        IGameDao dao = new TSVDaoFactory().getGameDao();

        GameServer game = new GameServer();
        game.setCurrentPlayer("bob");
        game.setCurrentPlayerIndex(1);


        dao.addGame("bob", game);

        System.out.println("Done");
    }

}
