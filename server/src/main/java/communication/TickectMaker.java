package communication;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hao on 10/27/18.
 */

public class TickectMaker {
    public ArrayList<Ticket> MakeCards(){
        ArrayList<Ticket> result = new ArrayList<Ticket>();
        try {
            Scanner sc = new Scanner(new File("DestinationTicketCard.txt"));
            while (sc.hasNextLine()) {
                String beginningCity = sc.nextLine();
                String endingCity = sc.nextLine();
                int value = Integer.parseInt(sc.nextLine());
                Ticket d = new Ticket(new City(beginningCity), new City(endingCity),value);
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
