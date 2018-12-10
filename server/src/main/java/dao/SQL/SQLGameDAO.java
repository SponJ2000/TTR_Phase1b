package dao.SQL;

import com.google.gson.Gson;
import com.obfuscation.server.GenericCommand;
import com.obfuscation.server.Server;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import communication.Game;
import communication.GameServer;
import communication.Result;
import communication.Serializer;
import dao.IGameDao;


/**
 * Created by haoyucn on 12/5/18.
 */

public class SQLGameDAO implements IGameDao{
    SQLDBConnection connection;

    public SQLGameDAO() {
        this.connection = new SQLDBConnection();
    }

//    public void addGame(String gameID, Blob game) {
//
//    }

    public static void main(String argv[]) {
        SQLGameDAO sqlGameDAO = new SQLGameDAO();
        try {
            GameServer gameServer = new GameServer();

            sqlGameDAO.getGames(null);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addGame(String gameID, Blob game) {
        return false;
    }

//    tested
    public boolean addGame(String gameID, GameServer game) {
        Result result = null;
        String statement = "INSERT INTO games (id, game, cmdlist) " +
                            "VALUES (?, ?, \"\")";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setString(1,gameID);
            Serializer serializer = new Serializer();
            String gameString = serializer.serializeGameServer(game);
            ps.setString(2, gameString);
            result = connection.executeUpdateStatement(ps);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (result == null) {
            return false;
        }
        return result.isSuccess();
    }

//    tested
    @Override
    public boolean removeGame(String gameID) {
        Result result = null;
        String statement = "DELETE FROM games WHERE id = ?";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setString(1,gameID);
            result = connection.executeUpdateStatement(ps);


        } catch (SQLException e) {
            return false;
        }

        if (result == null) {
            return false;
        }
        return result.isSuccess();
    }


    @Override
    public boolean updateGame(String gameID, Blob game) {
        return false;
    }

    //    tested
    public boolean updateGame(String gameID, GameServer game) {
        Result result = null;
        String statement = "UPDATE games SET game = ? WHERE id = ?";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            String gameString = new Serializer().serializeGameServer(game);
            ps.setString(1,gameString);
            ps.setString(2,gameID);
            result = connection.executeUpdateStatement(ps);

        } catch (SQLException e) {
            return false;
        }

        if (result == null) {
            return false;
        }
        return result.isSuccess();
    }

    @Override
    public boolean updateCmdList(String gameID, Blob cmdlist) {
        return false;
    }

    // tested
    public boolean updateCmdList(String gameID, ArrayList<GenericCommand> cmdlist) {
        Result result = null;
        String statement = "UPDATE games SET cmdlist = ? WHERE id = ?";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            Serializer serializer = new Serializer();
            for (GenericCommand c : cmdlist) {
                sb.append(serializer.serializeCommand(c));
            }
            if(sb.charAt(sb.length() -1) == ',') {
                sb.setCharAt(sb.charAt(sb.length() - 1),']');
            }
            ps.setString(1,sb.toString());
            ps.setString(2,gameID);
            result = connection.executeUpdateStatement(ps);

        } catch (SQLException e) {
            return false;
        }

        if (result == null) {
            return false;
        }
        return result.isSuccess();
    }


    @Override
    public List<Blob> getGames() {
        return null;
    }

    public List<GameServer> getGames(Game g) {
        ArrayList<GameServer> games = new ArrayList<>();
        Result result = null;
        String statement = "SELECT * FROM games;";
//        PreparedStatement ps = connection.getPreparedStatment(statement);
        Statement ps = connection.getStatement();
        try {
//            result = connection.executeQueryStatement(ps);
            result = new Result(true, ps.executeQuery(statement),null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (result == null) {
            return null;
        }
        else if (result.isSuccess()){
            ResultSet rs = (ResultSet) result.getData();
            try {
                ArrayList<String> gameJsons = new ArrayList<String>();
                System.out.println(rs.getFetchSize());
                while(rs.next()) {
                    Serializer serializer = new Serializer();
                    games.add(serializer.deserializeGameServer(rs.getString("game")));
//                    String gameJson = new String(b.getBytes(1,(int) b.length()));
//                    gameJsons.add(gameJson);
//                    System.out.println(gameJson);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private String readBlob(){

        return null;
    }
}
