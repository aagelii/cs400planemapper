// --== CS400 File Header Information ==--
// Name: <Zachary Paronto>
// Email: <paronto@wisc.edu>
// Team: <GD blue>
// Role: <Data Wrangler>
// TA: <Surabhi>
// Lecturer: <Heimerl>
// Notes to Grader: <>
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Airport implements AirportInterface {
  private String name;
  private List<String> flights;

  /**
   * Default constructor
   * @Override
   */
  public Airport() {
    name="";
    flights = new ArrayList<String>();

  }


  /**
   * Gets the object's name
   * <p>
   *
   * @Override
   * @return String the name of the airport
   */
  public String getName() {

    return name;
  }


  /**
   * Gets the object's outgoing flights
   * <p>
   *
   * @Override
   * @return List<String> the list of flights for this airport
   */
  public List<String> getFlights() {

    return flights;
  }

  /**
   * Sets the object's name
   * <p>
   *
   * @Override
   * @param name the new name of the object
   */
  public void setName(String name) {

    this.name = name;
  }


  /**
   * Sets the object's flights
   * <p>
   *
   * @Override
   * @param list the new list of flights to be updated to
   */
  public void setFlights(List<String> list) {

    flights = list;
  }

  /**
   * Uses the string.compareTo method to compare the two object's name fields
   * <p>
   *
   * @Override
   * @param otherAirplane another object that extends the AirportInterface
   * @return String the name of the airport
   */
  public int compareTo(AirportInterface otherAirplane) {

    return this.name.compareTo(otherAirplane.getName());
  }

  /**
   * Returns a string representation of the string object
   * <p>
   *
   * @Override
   * @return String the string representation of the object;
   */
  public String toString() {
    Iterator<String> it = flights.iterator();
    String _string = "";

    //separates flights by one tab index
    while (it.hasNext()) {
      _string += it.next() + "  ";
    }

    return "Name: " + name + " Flights: " + _string;

  }

  /**
   * Attempts to add a flight to the list of flights based on a string
   * that matches ".*,[0-9]*,[0-9]*"
   * <p>
   *
   * @param string the string to be added to the list
   * @return boolean true if string was added to list, false otherwise
   */
  public boolean addFlight(String string) {
    if (string == null)
      return false;
    //creates the regex pattern
    Pattern pattern = Pattern.compile(".*,[0-9]*,[0-9]*");
    //compares string to regex format
    Matcher matcher = pattern.matcher(string);

    if (matcher.matches()) {
      flights.add(string);
      return true;
    }
    return false;
  }


}