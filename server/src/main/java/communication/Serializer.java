package communication;

import com.google.gson.Gson;

import communication.ICommand;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class Serializer {

    Gson gson;

    public Serializer() {
        gson = new Gson();
    }

    public String serialize(Result result){
        return null;
    }
    public ICommand deserialize(String json){
        

        return null;
    }
}
