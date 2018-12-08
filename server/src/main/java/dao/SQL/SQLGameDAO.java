package dao.SQL;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import communication.Result;
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
        String suprize = "superize";
        byte[] blob = suprize.getBytes();
        try {
            if(sqlGameDAO.addGame("bro", new SerialBlob(blob))) {
                System.out.println("added");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addGame(String gameID, Blob game) {
        Result result = null;
        String statement = "INSERT INTO games (id, game, cmdlist)" +
                            "VALUES (?, ?, null)";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setString(0,gameID);
            ps.setBlob(1,game);
            result = connection.executeStatement(ps);

        } catch (SQLException e) {
            return false;
        }

        if (result == null) {
            return false;
        }
        return result.isSuccess();
    }

    @Override
    public boolean removeGame(String gameID) {
        Result result = null;
        String statement = "DELETE FROM games WHERE id = ?";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setString(0,gameID);
            result = connection.executeStatement(ps);


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
        Result result = null;
        String statement = "UPDATE games SET game = ? WHERE id = ?";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setBlob(0,game);
            ps.setString(1,gameID);
            result = connection.executeStatement(ps);

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
        Result result = null;
        String statement = "UPDATE games SET cmdlist = ? WHERE id = ?";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setBlob(0,cmdlist);
            ps.setString(1,gameID);
            result = connection.executeStatement(ps);

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
        Result result = null;
        String statement = "SELECT games FROM games;";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        try {
            result = connection.executeStatement(ps);
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
                ArrayList<Blob> gameBlobs = new ArrayList<Blob>();
                while(rs.next()) {
                    gameBlobs.add(rs.getBlob("game"));
                }
                return gameBlobs;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
