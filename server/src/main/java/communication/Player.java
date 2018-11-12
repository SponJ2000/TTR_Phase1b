package communication;

import java.util.ArrayList;

/**
 * Created by jalton on 10/1/18.
 */

public class Player {
    private String playerName;
    private Integer point;
    private ArrayList<String> claimedRoutesID = new ArrayList<>();
    private GameColor playerColor;
    private Integer carNum;



    public ArrayList<String> getClaimedRoutes() {
        return claimedRoutesID;
    }

    public void setClaimedRoutes(ArrayList<String> claimedRoutesID) {
        this.claimedRoutesID = claimedRoutesID;
    }

    public boolean addRouteAsClaimed(String r) {
        if (claimedRoutesID.contains(r)) {
            return false;
        }
        claimedRoutesID.add(r);
        return true;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player() {}

    public Player(String playerName) {
        this.playerName = playerName;
        carNum = 40;
    }

    public int getCarNum(){
        return carNum;
    }

    public void setCarNume(int newNum){
        carNum = newNum;
    }

    public GameColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(GameColor playerColor) {
        this.playerColor = playerColor;
    }

//    @Override
//    public String toString() {
//        return "Player{" +
//                "playerName='" + playerName + '\'' +
//                ", point=" + point +
//                ", tickets=" + tickets +
//                ", cards=" + cards +
//                ", claimedRoutesID=" + claimedRoutesID +
//                ", ticketToChoose=" + ticketToChoose +
//                ", cardToChoose=" + cardToChoose +
//                ", ticketNum=" + ticketNum +
//                ", trainCarNum=" + trainCarNum +
//                ", cardNum=" + cardNum +
//                ", playerColor=" + playerColor +
//                '}';
//    }


    public Player(String playerName, Integer point, GameColor playerColor) {
        this.playerName = playerName;
        this.point = point;
        this.playerColor = playerColor;
    }


}
