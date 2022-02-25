// --== CS400 File Header Information ==--
// Name: Axel Agelii
// Email: agelii@wisc.edu
// Team: GD Blue
// Role: Frontend Developer
// TA: Surabhi
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Frontend implementation for Plane Mapper Project.
 * @author Axel
 */
public class Frontend {
  // allows input to be captured from the user
  private Scanner scan;

  /**
   * Constructor for Frontend Class, initializes new Scanner object
   */
  public Frontend() {
    scan = new Scanner(System.in);
  }


  /**
   * Introduces the program with instructions, then begins baseMode
   *
   * @param backend instance of Backend that is being run
   */
  public void run(Backend backend) {
    System.out.println("Welcome to Plane Mapper: A program designed to help you navigate through "
        + "a data set of airports!");
    System.out.println("You can press \'i\' to enter insert mode,\'s\' to enter search mode, "
      + "or \'x\' to exit the current mode or exit the program.");
    System.out.println("The program starts in the base mode and displays the all airports and their"
      +"flights in the data set.");
    System.out.println("All key inputs must be followed by the \'enter\' key.");
    System.out.println();
    baseMode(backend);
  }

  /**
   * Takes in file path as argument, then initializes the backend
   *
   * @param args file (csv)
   */
  public static void main(String[] args) {
    Backend backend = null;
    try {
      backend = new Backend(args[0]);
      //backend=new Backend();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    Frontend frontend = new Frontend();
    frontend.run(backend);
  }

  /**
   * The baseMode method begins by displaying all airports as well as their departures with cost
   * and flight duration.
   *
   * @param backend instance of Backend that is being run
   */
  public void baseMode(Backend backend) {
    // display the airports and flights from the database, initially all should display

    List<AirportInterface> airports = backend.getAll(); // gets list of all airports

    for(int i=0; i<airports.size();i++){ // goes thru all airports
      System.out.println(airports.get(i).getName()+", flights leaving: ");
      if(airports.get(i).getFlights().size()==0){ // if no departures, print none
        System.out.println("NO FLIGHTS LEAVING THIS AIRPORT");
      }
      else { // airport has departures, print each on new line with duration and cost
        for (String x : airports.get(i).getFlights()) {
          String[] temp = x.split(",");
          System.out.print(" - Destination: " + temp[0] + "\t\tFlight Duration: " + temp[1] + " hour(s)\t\tCost: $" + temp[2]);
          System.out.println();
        }
      }
      System.out.println();
    }
    System.out.println("Welcome to the base mode.\nIn base mode you can find the shortest path between" +
      "two airports. To begin this, please enter two airports (one at a time) that you would like to" +
      " find the shortest path to.");
    System.out.println("Press 'i' to enter insert mode, 's' to enter search mode " +
        "or 'x' to exit the program");


    String in = ""; // acts as current input

    // takes in input until x is entered
    while (in.compareTo("i") != 0 && in.compareTo("s") != 0 && in.compareTo("x") != 0) {
      try {
        in = scan.nextLine(); // takes input from user
        String a1=in;
        if(a1.equals("x")||a1.equals("i")||a1.equals("s")){ // if a command, break out
          break;
        }
        in=scan.nextLine();
        String a2=in;
        backend.lookup(a1); // checks that airport exists
        backend.lookup(a2); // checks that airport exists
        System.out.println("Shortest path: "+backend.getShortestPath(a1, a2));
        System.out.println();
        System.out.println("Shortest distance: "+backend.getShortestDistance(a1,a2));
      } catch (Exception e) {
        System.out.println("No airport found. Please try again.");
      }
    }
    // enters insert mode
    if (in.compareTo("i") == 0) {
      insertMode(backend);
    }
    // enters search mode
    if (in.compareTo("s") == 0) {
      searchMode(backend);
    }
  }

  /**
   * The insertMode method allows the user to add new airports and paths into the graph by prompting
   * several questions from the user to answer.
   *
   * @param backend instance of Backend that is being run
   */
  public void insertMode(Backend backend) {
    System.out.println("\nInsert Mode");
    System.out.println("Enter 'x' at anytime to exit Insert Mode");

    String in = ""; // acts as current input

    ArrayList<String> insert = new ArrayList<>(); // ArrayList represents properties to add
    for (int i = 0; i < 4; i++) {
      insert.add(null);
    }

    // creates the set of questions for creating airport
    String[] instructions = new String[4];
    instructions[0] = "What is the origin airport's name?";
    instructions[1] = "What is the destination airport's name?";
    instructions[2] = "What is the flight duration? (Enter a whole number of hours)";
    instructions[3] = "What is the cost of the flight? (Enter a whole number of dollars)";

    boolean exit=false;
    // if x is entered, insertMode ends, and goes back to baseMode
    while (in.compareTo("x") != 0 && insert.contains(null)) {
      try {
        for(int i=0; i<4; i++){ // goes thru the set of 4 questions
          exit=false;
          boolean finished= false;
          while(!finished){ // keeps going until the step is complete
            System.out.println(instructions[i]); // display instruction
            in = scan.nextLine(); // takes input
            if(in.compareTo("x")==0){ // exits if x is inputted
              exit=true;
              break;
            }
            if(i==0){ // first question
              if(in.compareTo("x")==0){ // exits if x is inputted
                exit=true;
                break;
              }
              String [] test = in.split("");
              boolean correctFormat = true; // correct format means all strings
              for(String temp : test){
                try{ // tests for correct format
                  Integer.parseInt(temp);
                  correctFormat=false;
                  break;
                }catch(Exception e){
                  // expected
                }
              }
              if(correctFormat){ // no error
                insert.set(0,in); // add input to ArrayList
                finished=true; // finished the step
              }
              else{
                System.out.println("Airport names cannot contain numbers.");
              }
            }
            else if(i==1){ // second question
              if(in.compareTo("x")==0){ // exits if x is inputted
                exit=true;
                break;
              }
              String [] test = in.split("");
              boolean correctFormat = true; // correct format means all strings
              for(String temp : test){
                try{ // tests for correct format
                  Integer.parseInt(temp);
                  correctFormat=false;
                  break;
                }catch(Exception e){
                  //expected
                }
              }
              if(correctFormat){ // no error
                insert.set(1,in); // add input to ArrayList
                finished=true; // finished step
              }
              else{
                System.out.println("Airport names cannot contain numbers.");
              }
            }
            else if(i ==2){ // third question
              try {
                if(in.compareTo("x")==0){ // exits if x is inputted
                  exit=true;
                  break;
                }
                int time = Integer.parseInt(in); // checks format
                insert.set(2,in); // correct format, add input to ArrayList
                finished=true; // finished step
              }catch(Exception e){
                System.out.println("Please enter only whole integer numbers.");
              }
            }
            else{
              try {
                if(in.compareTo("x")==0){ // exits if x is inputted
                  exit=true;
                  break;
                }
                int cost = Integer.parseInt(in); // checks format
                insert.set(3,in); // correct format, add input to ArrayList
                break;
                //finished=true; // finished step

              }catch(Exception e){
                System.out.println("Please enter only whole integer numbers.");
              }
            }
          }
          if(exit){
            System.out.println("\nExiting Insert Mode.\n");
            break;
          }
        }
        break;
      }catch (Exception e){
      }
    }
    if(!exit){
      System.out.println("\nNew airport was added to data set...\n");
      backend.insertAirport(insert.get(0)); // insert new airport
      backend.makeConnection(insert.get(0), insert.get(1), Integer.parseInt(insert.get(2)),
        Integer.parseInt(insert.get(3))); // makes connection
    }
    baseMode(backend);
  }


  /**
   * The searchMode method allows the user to search the name of an airport, then it displays the
   * airport along with its destinations, flight durations, and costs.
   *
   * @param backend
   */
  public void searchMode(Backend backend) {
    System.out.println("\nSearch Mode");
    System.out.println("Enter 'x' at anytime to exit the Search Mode.");
    System.out.println("Search up a name of an airport to look it up in the data set and " +
      "retrieve its properties.");
    String in = ""; // acts as current input
    //in=scan.nextLine(); // flush
    while(in.compareTo("x")!=0){ // keeps going until x is inputted
      try{
        in = scan.nextLine(); // takes input
        try{
          if(in.compareTo("x")==0){ // exit if x is entered
            break;
          }
          Airport temp = backend.lookup(in); // checks if airport exists
          System.out.println(temp.getName()+", flights leaving:");
          for(String x : temp.getFlights()){
            String [] tempArray= x.split(",");
            System.out.print(" - Destination: "+tempArray[0]+"\t\tFlight Duration: "+tempArray[1]+
              " hour(s)\t\tCost: $"+tempArray[2]);
            System.out.println();
          }
        }catch (Exception e){
          System.out.println(in+" is not in the data set! Please try searching again.");
        }
      }catch (Exception e){}
    }
    System.out.println("\nExiting Search Mode.");
    baseMode(backend);
  }
}
