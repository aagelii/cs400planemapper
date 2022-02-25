import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.zip.DataFormatException;

public interface AirportDataReaderInterface {
  public List<AirportInterface> readDataSet(Reader inputFileReader) throws IOException, DataFormatException;
}