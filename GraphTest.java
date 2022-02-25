// --== CS400 File Header Information ==--
// Name: Axel Agelii
// Email: agelii@wisc.edu
// Team: GD
// TA: Surabhi
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {

  private CS400Graph<String> graph;

  /**
   * Instantiate graph from last week's shortest path activity.
   */
  @BeforeEach
  public void createGraph() {
    graph = new CS400Graph<>();
    // insert vertices A-E
    graph.insertVertex("A");
    graph.insertVertex("B");
    graph.insertVertex("C");
    graph.insertVertex("D");
    graph.insertVertex("E");
    // insert edges from Week 09. Dijkstra's Activity
    graph.insertEdge("A","B",2);
    graph.insertEdge("A","D",4);
    graph.insertEdge("A","E",1);
    graph.insertEdge("B","C",5);
    graph.insertEdge("C","A",3);
    graph.insertEdge("D","B",3);
    graph.insertEdge("D","C",7);
    graph.insertEdge("D","E",1);
    graph.insertEdge("E","C",8);
  }

  /**
   * Checks the distance/total weight cost from the vertex labelled C to E
   * (should be 4), and from the vertex labelled A to C (should be 7).
   */
  @Test
  public void providedTestToCheckPathCosts() {
    assertTrue(graph.getPathCost("C", "E") == 4);
    assertTrue(graph.getPathCost("A", "C") == 7);
  }

  /**
   * Checks the ordered sequence of data within vertices from the vertex
   * labelled C to E, and from the vertex labelled A to C.
   */
  @Test
  public void providedTestToCheckPathContents() {
    assertTrue(graph.shortestPath("C", "E").toString().equals(
      "[C, A, E]"
    ));
    assertTrue(graph.shortestPath("A", "C").toString().equals(
      "[A, B, C]"
    ));
  }

  /**
   * Checks and confirms that the distance that I reported as #3 from last week's activity is
   * correct.
   */
  @Test
  public void distanceFromActivityCorrectness(){
    assertTrue(graph.getPathCost("E", "B") == 13);
    assertTrue(graph.getPathCost("E", "D") == 15);
  }

  /**
   * Checks and confirms the sequence of values along the path from your source node to this same
   * end node (the end node that is furthest from your source node).
   */
  @Test
  public void pathFromSourceActivityCorrectness(){
    assertTrue(graph.shortestPath("E", "B").toString().equals("[E, C, A, B]"));
    assertTrue(graph.shortestPath("E", "D").toString().equals("[E, C, A, D]"));
  }

  /**
   * This test checks and confirms that if given the same starting and end node, the shortest path
   * will be just the node itself.
   */
  @Test
  public void sameStartingNode(){
    assertTrue(graph.shortestPath("A", "A").toString().equals("[A]"));
    assertTrue(graph.shortestPath("B", "B").toString().equals("[B]"));
    assertTrue(graph.shortestPath("C", "C").toString().equals("[C]"));
    assertTrue(graph.shortestPath("D", "D").toString().equals("[D]"));
    assertTrue(graph.shortestPath("E", "E").toString().equals("[E]"));
    assertTrue(graph.getPathCost("A","A")==0);
  }

  /**
   * Checks and confirms that the distance from node "A" to node "C" from last week's activity
   * is correct.
   */
  @Test
  public void pathCostFromAtoC(){
    assertTrue(graph.getPathCost("A","C")==7);
  }

  /**
   * Checks and confirms that the path from node "A" to node "C" from last week's activity
   * is correct.
   */
  @Test
  public void pathFromAtoC(){
    assertTrue(graph.shortestPath("A","C").toString().equals("[A, B, C]"));
  }
}