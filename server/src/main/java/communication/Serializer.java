package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;

import communication.ICommand;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class Serializer {

    private Gson gson;

    public Serializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Result.class, new IResultAdapter());
        builder.registerTypeAdapter(ICommand.class, new ICommandAdapter());
        gson = builder.create();

    }

    public String serializeResult(Result result){
        String json = gson.toJson(result);
        return json;
    }

    public Result deserializeResult(String json) {
        Result result = gson.fromJson(json, Result.class);
        return result;
    }

    public String serializeCommand(ICommand command){
        String json = gson.toJson(command);
        return json;
    }

    public ICommand deserializeCommand(String json){
        GenericCommand command = gson.fromJson(json, GenericCommand.class);
        return command;
    }

    public Ticket deserializeTicket(String json){
        Ticket ticket = gson.fromJson(json, Ticket.class);
        return ticket;
    }

    public Game deserializeGame(String json) {
        Game game = gson.fromJson(json, Game.class);
        return game;
    }

    public GameLobby deserializeGameLobby(String json) {
        GameLobby gameLobby = gson.fromJson(json, GameLobby.class);
        return gameLobby;
    }

    public Card deserializeCard(String json) {
        Card c = gson.fromJson(json, Card.class);
        return c;
    }

}
