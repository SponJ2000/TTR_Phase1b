package communication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by jalton on 10/24/18.
 */

public class Message {
    private String playerID;
    private String text;

    public Message(String playerID) {
        this.playerID = playerID;
        text = "";
    }

    public Message(String playerID, String text) {
        this.playerID = playerID;
        try{
            this.text = URLEncoder.encode(text,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.text = text;
        }

    }

    public String getPlayerID() {
        return playerID;
    }

    public String getText() {
        URLDecoder decoder = new URLDecoder();
        try {
            return decoder.decode(this.text, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this.text;
    }

    public void setText(String text) {
        try{
            this.text = URLEncoder.encode(text,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.text = text;
        }
    }

}
