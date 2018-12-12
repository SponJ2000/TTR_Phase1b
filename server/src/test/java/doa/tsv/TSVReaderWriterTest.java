package doa.tsv;
import org.junit.Test;

import java.util.List;

import dao.tsv.TSVReaderWriter;

public class TSVReaderWriterTest {

    @Test
    public void CompareTest() {
        String[] header = new String[1];
        header[0] = "string";
        TSVReaderWriter rw = new TSVReaderWriter(header);
        rw.writeHeader();
        rw.writeLine(header);

        List<String[]> lines = rw.readAll();
        assert(lines.size() == 1);
        String[] tokens = lines.get(0);
        assert(tokens.length == 1);
        assert(tokens[0].equals("string"));
    }

}
