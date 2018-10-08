package com.obfuscation.server;

import org.junit.Test;

import java.util.ArrayList;

import communication.Game;
import communication.Player;

import static org.junit.Assert.*;

/**
 * Created by jalton on 10/8/18.
 */
public class ServerFacadeTest {

    ServerFacade facade = ServerFacade.getInstance();

    @Test
    public void login() throws Exception {
        //Phase 1: test invalid input
        assertEquals(facade.Login(null, null).isSuccess(), false);
        assertEquals(facade.Login(null, "1234").isSuccess(), false);
        assertEquals(facade.Login("1234", null).isSuccess(), false);

        facade.Register("John", "john");

        //Phase 2: test invalid username/password
        assertEquals(facade.Login("John", "notjohn").isSuccess(), false);
        assertEquals(facade.Login("NotJohn", "notjohn").isSuccess(), false);

        //Phase 3: test valid login
        assertEquals(facade.Login("John", "john").isSuccess(), true);
        assertEquals(facade.Login("John", "john").isSuccess(), true);

    }

    @Test
    public void register() throws Exception {
        //Phase 1: test invalid input
        assertEquals(facade.Register(null, "1234").isSuccess(), false);
        assertEquals(facade.Register("1234", null).isSuccess(), false);

        //Phase 2: test registration and re-registration
        assertEquals(facade.Login("James", "james").isSuccess(), false);
        assertEquals(facade.Register("James", "james").isSuccess(), true);
        assertEquals(facade.Login("James", "james").isSuccess(), true);
        assertEquals(facade.Register("James", "james").isSuccess(), false);
    }

    @Test
    public void joinGame() throws Exception {
        //Phase 1: test invalid input

    }

    @Test
    public void leaveGame() throws Exception {
    }

    @Test
    public void createGame() throws Exception {
        //Phase 1: test invalid input
        Game game = null;
        assertEquals(facade.CreateGame(game, "masterKey").isSuccess(), false);

        game = new Game(null, null, null, 0);
        assertEquals(facade.CreateGame(game, "masterKey").isSuccess(), false);
        game.setGameName("Joe's Game");
        assertEquals(facade.CreateGame(game, "masterKey").isSuccess(), false);
        game.setHost("Joe");
        assertEquals(facade.CreateGame(game, "masterKey").isSuccess(), false);
        game.setPlayers(new ArrayList<Player>());
        assertEquals(facade.CreateGame(game, "masterKey").isSuccess(), false);
        game.setMaxPlayers(3);
        assertEquals(facade.CreateGame(game, "notMasterKey").isSuccess(), false);
        assertEquals(facade.CreateGame(game, "masterKey").isSuccess(), true);





    }

    @Test
    public void startGame() throws Exception {
    }

    @Test
    public void getGameList() throws Exception {
    }

    @Test
    public void getGame() throws Exception {
    }

    @Test
    public void checkUpdates() throws Exception {
    }

    @Test
    public void checkGame() throws Exception {
    }

}