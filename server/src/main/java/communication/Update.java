package communication;

import java.util.List;

public class Update {

    boolean update;
    List<String> gameList;

    public Update(boolean update, List<String> gameList) {
        this.update = update;
        this.gameList = gameList;
    }

    public boolean isUpdate() {
        return update;
    }

    public List<String> getGameList() {
        return gameList;
    }
}
