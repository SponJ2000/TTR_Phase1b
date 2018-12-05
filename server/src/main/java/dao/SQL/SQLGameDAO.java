package dao.SQL;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by haoyucn on 12/5/18.
 */

public class SQLGameDAO {
    SQLDBConnection connection;

    public SQLGameDAO() {
        this.connection = new SQLDBConnection();
    }

    public void addGame(String gameID, Blob game) {
        String statement = "INSERT INTO games (id, game, cmdlist)" +
                            "VALUES (" + gameID + ", " + game + ", null)";
        ResultSet resultSet = connection.executeCommand(statement);
        if (resultSet == null){
            //TODO: do something to throw error
        }
        //result is not null
        //TODO: do something to parse the result to a game object
    }

}
