package dao.SQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import communication.Result;

/**
 * Created by haoyucn on 12/5/18.
 */

public class SQLDBConnection {


    String url;
    Connection conn;

    public SQLDBConnection() {
        String fileDirectory = System.getProperty("user.dir") + "/server/src/main/java/dao/SQL/";
        String fileName="server.db";
        this.url = "jdbc:sqlite:"+ fileDirectory + fileName;
    }

    public Boolean createDBFile() {
        File file = new File(url);
        try {
            if (file.createNewFile()) {
                System.out.println("file has been created");
                return true;
            }
            else {
                System.out.println("failed to create a file");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    private Boolean openConnection() {
        try {
            this.conn = DriverManager.getConnection(url);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet executeCommand (String stmtString) {
        if (openConnection()) {
            try {
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                if (stmt.execute(stmtString)) {
                    conn.commit();
                    closeConnection();
                    return stmt.getResultSet();
                }
                else {
                    conn.rollback();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            closeConnection();
        }
        return null;
    }

    // return null for false result
    public PreparedStatement getPreparedStatment(String sql){
        PreparedStatement preparedStatement = null;
        if (openConnection()) {
            try {
                preparedStatement = conn.prepareStatement(sql);
            }
            catch(SQLException e) {
                e.printStackTrace();
                closeConnection();
            }
        }
        return preparedStatement;
    }

    public Result executeStatement(PreparedStatement p) {
        try{
            boolean isSuccess = p.execute();
            if (!isSuccess) {
                closeConnection();
                return new Result(isSuccess, p.getWarnings(), "SQL Failure");
            }
            conn.commit();
            closeConnection();
            return new Result(true, p.getResultSet(), null);

        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
            return new Result(false, e,e.getMessage());
        }
    }

    private boolean closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                return true;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
