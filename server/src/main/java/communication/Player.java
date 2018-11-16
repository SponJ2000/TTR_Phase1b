package communication;

import java.util.ArrayList;

/**
 * Created by jalton on 10/1/18.
 */

public class Player implements IPlayer{
    private String playerName;
    private Integer point = Integer.valueOf(0);
    private ArrayList<String> claimedRoutesID = new ArrayList<>();
    private GameColor playerColor;
    private Integer trainNum = Integer.valueOf(0);



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
        trainNum = 40;
    }

    public int getTrainNum(){
        return trainNum;
    }

    public void setTrainNum(int newNum){
        trainNum = newNum;
    }

    public GameColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(GameColor playerColor) {
        this.playerColor = playerColor;
    }

    public Integer getCardNum() {
        return 0;
    }

    public Integer getTicketNum() {
        return 0;
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

    @Override
    public PlayerIdentity getIdentity() {
        return PlayerIdentity.PLAYER;
    }

}
