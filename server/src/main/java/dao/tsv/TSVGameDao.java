package dao.tsv;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
import java.util.List;

import communication.GameServer;
import communication.Serializer;
import dao.IGameDao;

import static dao.tsv.DATA_TYPES.GAME;


public class TSVGameDao implements IGameDao {

    private TSVReaderWriter rw;

    private static int ARRAY_SIZE = 4;

    private static int i_TYPE = 0;
    private static int i_ID = 1;
    private static int i_GAME = 2;
    private static int i_CMDL = 3;

    TSVGameDao() {
        String[] header = new String[ARRAY_SIZE];
        header[i_TYPE] = "type";
        header[i_ID] = "id";
        header[i_GAME] = "game";
        header[i_CMDL] = "cmdlist";
        rw = new TSVReaderWriter(header);
        rw.writeHeader();
    }

    @Override
    public boolean addGame(String gameID, GameServer game) {
        String[] row = new String[ARRAY_SIZE];
        row[i_TYPE] = GAME;
        row[i_ID] = gameID;
        row[i_GAME] = game.toString();
        row[i_CMDL] = "";
        rw.writeLine(row);

        return true;
    }

    @Override
    public boolean removeGame(String gameID) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[i_TYPE].equals(GAME)) {
                String[] row = rows.get(i);
                if(row[i_ID].equals(gameID)) {
                    rows.remove(i);
                    break;
                }
            }

        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public boolean updateGame(String gameID, GameServer game) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[i_TYPE].equals(GAME)) {
                String[] row = rows.get(i);
                if (row[i_ID].equals(gameID)) {
                    row[i_GAME] = new Serializer().serializeGameServer(game);
                    break;
                }
            }
        }
        rw.writeLines(rows);

        return true;
    }

    @Override
    public boolean updateCmdList(String gameID, ArrayList<GenericCommand> cmdlist) {
        List<String[]> rows = rw.readAll();
        for(int i = 0; i < rows.size(); i++) {
            if(rows.get(i)[i_TYPE].equals(GAME)) {
                String[] row = rows.get(i);
                if (row.equals(gameID)) {
                    String[] r = new String[ARRAY_SIZE];
                    r[i_TYPE] = GAME;
                    r[i_ID] = row[i_ID];
                    r[i_GAME] = row[i_GAME];
                    r[i_CMDL] = serializeCmdList(cmdlist);
                    rows.set(i, r);
                    break;
                }
            }
        }
        rw.writeLines(rows);

        return true;
    }

    private String serializeCmdList(ArrayList<GenericCommand> cmdlist) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Serializer serializer = new Serializer();
        for (GenericCommand c : cmdlist) {
            sb.append(serializer.serializeCommand(c));
        }
        if(sb.charAt(sb.length() -1) == ',') {
            sb.setCharAt(sb.charAt(sb.length() - 1),']');
        }
        return sb.toString();
    }

    @Override
    public List<GameServer> getGames() {
        return null;
//        List<Blob> games = new ArrayList<>();
//        List<String[]> rows = rw.readAll();
//        for(String[] row : rows) {
//            games.add(row);
//        }
//        return games;
    }

}
