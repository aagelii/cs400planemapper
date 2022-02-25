// --== CS400 File Header Information ==--
// Name: <Zachary Paronto>
// Email: <paronto@wisc.edu>
// Team: <GD blue>
// Role: <Data Wrangler>
// TA: <Surabhi>
// Lecturer: <Heimerl>
// Notes to Grader: <>
import java.util.List;
public interface AirportInterface extends Comparable<AirportInterface>{
  public String getName();
  public List<String> getFlights();
  public void setName(String name);
  public void setFlights(List<String> list);
  int compareTo(AirportInterface otherAirplane);
}