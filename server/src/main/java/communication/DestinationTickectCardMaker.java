package communication;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hao on 10/27/18.
 */

public class DestinationTickectCardMaker {
    public ArrayList<DestinationTicketCard> MakeCards(){
        ArrayList<DestinationTicketCard> result = new ArrayList<DestinationTicketCard>();
        try {
            Scanner sc = new Scanner(new File("DestinationTicketCard.txt"));
            while (sc.hasNextLine()) {
                String beginningCity = sc.nextLine();
                String endingCity = sc.nextLine();
                int value = Integer.parseInt(sc.nextLine());
                DestinationTicketCard d = new DestinationTicketCard(beginningCity,endingCity,value);
                result.add(d);
                sc.nextLine();
            }
        }
        catch ( Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
