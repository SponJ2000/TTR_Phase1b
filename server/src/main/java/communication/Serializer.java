package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        ICommand command = gson.fromJson(json, ICommand.class);
        return command;
    }
}
