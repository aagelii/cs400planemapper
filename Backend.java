// --== CS400 File Header Information ==--
// Name: Viraj Sule
// Email: vsule@wisc.edu
// Team: GD Blue
// Role: Backend Developer
// TA: Surabhi
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.DataFormatException;


/**
 * This class implements the back end function that are written in the FrontendInterface. Loads data
 * from a file and creates a graph of objects.
 *
 * @author viraj
 *
 */
public class Backend implements FrontendInterface {
  private CS400Graph<AirportInterface> graph;
  private List<AirportInterface> airportList;
  private List<String> airportNames;

  /**
   * Constructs a backend object based on an array of String values. Index 0 in that array is
   * assumed to be the file path
   *
   * @param String[] path an array of string values where the first index is a file path
   * @throws FileNotFoundException if the path is invalid or the file is in incorrect format
   */
  public Backend(String[] path) throws FileNotFoundException {
    graph = new CS400Graph<AirportInterface>();

    AirportDataReader aReader = new AirportDataReader();
    FileReader f;
    // attempts to make a FileReader at the provided path
    f = new FileReader(new File(path[0]));
    BufferedReader reader = new BufferedReader(f);

    try {
      airportList = aReader.readDataSet(reader);
      airportNames = new ArrayList<>();
      for (AirportInterface airport : airportList) {
        airportNames.add(airport.getName());
      }
    } catch (Exception e) {
      throw new FileNotFoundException("File not in correct format: " + path[0]);
    }
    populateGraph();
  }

  /**
   * Constructs a backend object based on the input String value
   *
   * @param String path a String value representing a file path
   * @throws FileNotFoundException if the path is invalid or the file is in incorrect format
   */
  public Backend(String path) throws FileNotFoundException {
    graph = new CS400Graph<AirportInterface>();

    AirportDataReader aReader = new AirportDataReader();
    FileReader f;
    // attempts to make a FileReader at the provided path
    f = new FileReader(new File(path));
    BufferedReader reader = new BufferedReader(f);

    try {
      airportList = aReader.readDataSet(reader);
      airportNames = new ArrayList<>();
      for (AirportInterface airport : airportList) {
        airportNames.add(airport.getName());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FileNotFoundException("File not in correct format: " + path);
    }

    populateGraph();
  }

  /**
   * Constructs a backend object based on a Reader object passed in
   *
   * @param Reader inputReader a reader object attached to an appropriate CSV File
   */
  public Backend(Reader inputReader) {
    graph = new CS400Graph<AirportInterface>();

    AirportDataReader aReader = new AirportDataReader();

    try {
      airportList = aReader.readDataSet(inputReader);
      airportNames = new ArrayList<>();
      for (AirportInterface airport : airportList) {
        airportNames.add(airport.getName());
      }

    } catch (FileNotFoundException e) {
      System.out.println("No matching file was found.");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DataFormatException e) {
      System.out.println("File does not follow the correct format.");
      e.printStackTrace();
    }
    populateGraph();
  }

  /**
   * Populates the graph with Airport objects from the CSV file
   */
  private void populateGraph() {
    // adds each airport to the graph as a vertices
    for (AirportInterface airport : airportList) {
      graph.insertVertex(airport);
    }

    // adds the edges between the vertices to the graph if they dont already exist
    for (AirportInterface airport : airportList) {
      List<String> flights = airport.getFlights();
      for (String flight : flights) {
        String[] flightProperties = flight.split(",");
        // if edge doesn't already exist
        if (!graph.containsEdge(airport, graph.vertices.get(airportList.get(airportNames.indexOf(flightProperties[0]))).data)) {
          graph.insertEdge(airport, graph.vertices.get(airportList.get(airportNames.indexOf(flightProperties[0]))).data,
            Integer.parseInt(flightProperties[1]) + Integer.parseInt(flightProperties[2]));
        }
      }
    }
  }

  /**
   * Inserts an airport as a vertice in the graph
   *
   * @param airport name of aiport to be inserted into graph
   */
  @Override
  public void insertAirport(String airport) {
    AirportInterface newAirport = new Airport();
    newAirport.setName(airport);
    if(!airportNames.contains(airport)) {
      graph.insertVertex(newAirport);
      airportList.add(newAirport);
      airportNames.add(newAirport.getName());
    }
  }

  /**
   * Checks if graph contains airport
   *
   * @param airport name to be checked if in graph
   * @return true if airport is found, false otherwise
   */
  @Override
  public boolean contains(String airport) {
    int airportIndex = airportNames.indexOf(airport);
    if (airportIndex == -1) {
      return false;
    }
    return graph.containsVertex(airportList.get(airportIndex));
  }

  /**
   * Makes an flight that goes from one airport to another, or in other words, creates an edge in
   * the graph connecting two vertices
   *
   * @param airport1 airport at one end of the flight
   * @param airport2 airport at other end of the flight
   * @param time     time it takes for flight. Used in computation for shortest path
   * @param cost     cost of flight. Used in computation for shortest path
   */
  @Override
  public void makeConnection(String source, String target, int time, int cost) {
    AirportInterface airport1 =
      graph.vertices.get(airportList.get(airportNames.indexOf(source))).data;
    AirportInterface airport2 =
      graph.vertices.get(airportList.get(airportNames.indexOf(target))).data;
    int distance = time + cost;
    //all flights are 2 ways, so adding flight to both airports
    List<String> flights1 = airport1.getFlights();
    flights1.add(target + "," + time + "," + cost);
    airport1.setFlights(flights1);

    List<String> flights2 = airport2.getFlights();
    flights2.add(source + "," + time + "," + cost);
    airport2.setFlights(flights2);

    graph.insertEdge(airport1, airport2, distance);

  }

  /**
   * Returns the shortest path between two airports
   *
   * @param airport1 airport to use to find shortest path
   * @param airport2 other airport to use to find shortest path
   * @return String displaying shortest path between two airports
   */
  @Override
  public String getShortestPath(String airport1, String airport2) {
    AirportInterface a1 = graph.vertices.get(airportList.get(airportNames.indexOf(airport1))).data;
    AirportInterface a2 = graph.vertices.get(airportList.get(airportNames.indexOf(airport2))).data;
    List<AirportInterface> shortestPath = graph.shortestPath(a1, a2);
    return shortestPath.toString();
  }

  /**
   * Returns the distance of the shortest path between two airports. Distance is calculated by
   * adding up the time and cost of the flights
   *
   * @param airport1 airport to use to find shortest path distance
   * @param airport2 other airport used to find shortest path distance
   * @return String displaying distance/cost of the shortest path between two airports
   */
  @Override
  public String getShortestDistance(String airport1, String airport2) {
    AirportInterface a1 = graph.vertices.get(airportList.get(airportNames.indexOf(airport1))).data;
    AirportInterface a2 = graph.vertices.get(airportList.get(airportNames.indexOf(airport2))).data;
    int shortestDist = graph.getPathCost(a1, a2);
    return shortestDist + "";
  }

  /**
   * Returns an Airport object by searching by name of airport
   *
   * @param airport name of airport to be searched
   * @return Airport object found with same name as parameter
   * @throws NoSuchElementException if airport name not found
   */
  @Override
  public Airport lookup(String airport) throws NoSuchElementException {
    if (contains(airport)) {
      AirportInterface ap = graph.vertices.get(airportList.get(airportNames.indexOf(airport))).data;
      Airport temp = new Airport();
      temp.setName(ap.getName());
      temp.setFlights(ap.getFlights());
      return temp;
    } else {
      throw new NoSuchElementException("This airport was not found!");
    }
  }

  /**
   * Returns a list of all Airports in the graph
   *
   * @return list of all airports in the graph
   */
  public List<AirportInterface> getAll() {
    Set<AirportInterface> airports = graph.vertices.keySet();
    return new ArrayList<>(airports);
  }



}