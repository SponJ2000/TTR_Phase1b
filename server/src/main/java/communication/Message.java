package communication;

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
        this.text = text;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
