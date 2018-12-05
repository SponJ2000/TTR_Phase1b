package dao;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import tsv.TSVReaderWriter;

public class TSVGameDao implements IGameDao {

    private TSVReaderWriter rw;

    TSVGameDao() {
        String[] header = new String[3];
        header[0] = "id";
        header[1] = "game";
        header[2] = "cmdlist";
        rw = new TSVReaderWriter(header);
        rw.writeHeader();
    }

    @Override
    public boolean addGame(String gameID, Blob game) {
        String[] row = new String[3];

        row[0] = gameID;
        row[1] = game.toString();
        row[2] = "";
        rw.writeLine(row);

        return true;
    }

    @Override
    public boolean removeGame(String gameID) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[0].equals(gameID)) {
                rows.remove(i);
                break;
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public boolean updateGame(String gameID, Blob game) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[0].equals(gameID)) {
                rows.get(i)[1] = game.toString();
                break;
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public boolean updateCmdList(String gameID, Blob cmdlist) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[0].equals(gameID)) {
                String[] r = new String[3];
                r[0] = rows.get(i)[0];
                r[1] = rows.get(i)[1];
                r[2] = cmdlist.toString();
                rows.set(i, r);
                break;
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public List<Blob> getGames() {
        return null;
//        List<Blob> games = new ArrayList<>();
//        List<String[]> rows = rw.readAll();
//        for(String[] row : rows) {
//            games.add(row);
//        }
//        return games;
    }

}
