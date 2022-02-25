import java.util.NoSuchElementException;

public interface FrontendInterface {
  public void insertAirport(String airport);
  public boolean contains(String airport);
  public void makeConnection(String airport1, String airport2, int time, int cost);
  public String getShortestPath(String airport1, String airport2);
  public String getShortestDistance(String airport1, String airport2);
  public Airport lookup(String airport) throws NoSuchElementException;
}
