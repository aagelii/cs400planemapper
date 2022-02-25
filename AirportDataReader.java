// --== CS400 File Header Information ==--
// Name: <Zachary Paronto>
// Email: <paronto@wisc.edu>
// Team: <GD blue>
// Role: <Data Wrangler>
// TA: <Surabhi>
// Lecturer: <Heimerl>
// Notes to Grader: <>
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;


public class AirportDataReader implements AirportDataReaderInterface {

  /**
   * Reads in information from a file and creates and returns a list of AirportInterface object
   * <p>
   *
   * @Override
   * @param inputFileReader the reader object created to match a csv file in the correct format
   * @return List<AirportInterface> a list of airport objects
   * @throws IOException if IO error occurs
   * @throws DataFormatException if the file does not match the correct format
   */
  public List<AirportInterface> readDataSet(Reader inputFileReader)
    throws IOException, DataFormatException {
    try {
      List<AirportInterface> x = new ArrayList<AirportInterface>(); // list to be returned
      BufferedReader csvFile = new BufferedReader(inputFileReader); // reading the input
      String row; // string for each row
      csvFile.readLine(); // flush first
      int counter=0;


      while((row = csvFile.readLine())!=null){ // one line at a time
        List<String> names = new ArrayList<String>();
        List<String> flights = new ArrayList<String>();
        //System.out.println(row); //testing
        counter = 0;
        for(int i=0;i<row.length();i++) { //goes through each character in the line
          //System.out.println(row.substring(i,i+1)); //testing
          if(row.substring(i,i+1).equals("\"")) {//locates the first quote
            for(int j = i+1;j<row.length();j++) {
              //System.out.println(row.substring(j,j+1)); //testing
              if(row.substring(j,j+1).equals("\"")) { //adds what is contained within the two quotes
                //System.out.println("i: "+i+" j: "+j+" "+row.substring(i+1,j)); //testing
                flights.add(row.substring(i+1,j));
                i=j;
                break; //resumes outer loop at index past quote
              }
            }

          }else if(row.substring(i,i+1).equals(",")) {
            counter++;
            names.add(row.substring(0,i));
          }
        }
        if(counter!=1) { //ensures column count is correct
          System.out.println("COUNTER: "+counter);
          throw new DataFormatException("ERROR: Column mismatch");
        }
        //sets values and adds to list
        Airport port = new Airport();
        port.setName(names.get(0));
        //using add method allows for regex expression verification
        for(String f: flights) {
          port.addFlight(f);
        }
        x.add(port);
      }
      csvFile.close(); // close the reader
      return x; // return list
    } catch(IOException f){ // if there is an IO problem, exception will be thrown
      throw new IOException("ERROR: File cannot be opened.");
    }
  }
}