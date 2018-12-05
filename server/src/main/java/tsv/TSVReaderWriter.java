package tsv;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSVReaderWriter {

    private static final String FILENAME = "tsv_database.tsv";
    private static final String DELIMITER = "\t";
    private static final String NEW_LINE = "\n";

    private String header;

    public TSVReaderWriter(String header) {
        this.header = header;
    }

    public List<String[]> readAll() {

        List<String[]> objects = new ArrayList<>();
        String line = null;
        String[] tokens = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILENAME));

//          skipping header
            reader.readLine();

//          read all lines in the file
            while((line = reader.readLine()) != null) {
                tokens = line.split(DELIMITER);
                if(tokens.length > 0) {
                    objects.add(tokens);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return objects;
    }

    public void write(List<String[]> toWrite) {

        FileWriter writer = null;

        try {
            writer = new FileWriter(FILENAME);
            writer.append(header);
            writer.append(NEW_LINE);

            for(String[] row : toWrite) {
                for(String s : row) {
                    writer.append(s);
                    writer.append(DELIMITER);
                }
                writer.append(NEW_LINE);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
