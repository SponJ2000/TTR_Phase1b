package communication;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by jalton on 10/1/18.
 */

public interface IServer {

    /**
     *
     * @param id        a String containing the username
     * @param password  a String containing the password
     * @return          a Result object containing the authToken
     */
    Result Login(String id, String password);

    /**
     *
     * @param id        a String containing the username
     * @param password  a String containing the password
     * @return          a Result object containing the authToken
     */
    Result Register(String id, String password);

    /**
     *
     * @param id        a String object containing the userName
     * @param gameID    a String object containing the gameID
     * @param authToken a String object with the username's authToken
     * @return          a Result object containing a boolean indicating success
     */
    Result JoinGame(String id, String gameID, String authToken);

    /**
     *
     * @param id
     * @param gameID
     * @param authToken
     * @return
     */
    Result LeaveGame(String id, String gameID, String authToken);

    /**
     *
     * @param game      the game object, containing the game name and max player count
     * @param authToken a String object containing the users authToken
     * @return          a Result object containing a boolean indication success
     */
    Result CreateGame(Game game, String authToken);

    /**
     *
     * @param gameID    a String object containing the gameID of the game to be started
     * @param authToken a String object containing the user's authToken
     * @return          a Result object containing success boolean
     */
    Result StartGame(String gameID, String authToken);

    /**
     *
     * @param authToken a String object containing the authToken of the user
     * @return          a Result object containing the game list
     */
    Result GetGameList(String authToken);

    /**
     *
     * @param gameID    a String object containing the game ID
     * @param authToken a String object containing the user's authToken
     * @return          a Result object containing the Game object
     */
    Result GetGame(String gameID, String authToken);

    /**
     *
     * @param authToken
     * @return          a Result object containing the gameList version number
     */
    Result CheckGameList(String authToken);

    /**
     *
     * @param authToken
     * @param gameID
     * @return          a Result object containing the game version number
     */
    Result CheckGame(String authToken, String gameID);
}
