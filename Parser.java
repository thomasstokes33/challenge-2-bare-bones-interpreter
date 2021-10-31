import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * The Parser class aims to read through the code and convert it to something that can be read in a
 * uniform manner, which will be read and executed in a different manner by this class. The parser
 * will check Syntax
 */

public class Parser {
  //TODO: update javadoc and comment
  //TODO: test syntax parser
  //TODO: write executer
  //TODO: check style
  private int startid;
  private String type;

  public Parser(int index, String thetype) {
    startid = index;
    type = thetype;
  }

  public static void main(String[] args) {
    Parser theparser = new Parser(0, "root");
    theparser.setup();

  }

  public void setup() {

    List<String> theTree = new ArrayList<>();
    theTree = readTextFile("sample2.txt");
//    theTree.add("clear x");
//    theTree.add("incr x");
//    theTree.add("incr x");
//    theTree.add("incr x");

    List<String> whilestack = new ArrayList<>();
    List<String[]> parsedTree = new ArrayList<>();
    for (String item : theTree) {
      System.out.println(item);
      String[] tempVar = item.split("[\n\r\t ]+");
      parsedTree.add(tempVar);


    }

    List<String[]> returned = run(parsedTree, startid);
    String[] positionArray = returned.remove(returned.size() - 1);
    int lastIndex = Integer.parseInt(positionArray[0]);
    System.out.println(returned.toString());
    for (String[] instruction : returned) {
      for (int i = 0; i<instruction.length; i++) {
        System.out.println(instruction[i]);
      }
    }
  }

  public List<String[]> run(List<String[]> parsedTree, int position) {


    while (position < (parsedTree.size() )) { //TODO: what if the program reaches the end of a barebones while without end. maybe have if type not equal to root then error message
      System.out.println(position+" "+parsedTree.size());
      String[] currentInstruction = parsedTree.get(position);
      if (currentInstruction[0].equals("clear") && currentInstruction.length == 2) {
        //check if next instruction is a variable
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]*", currentInstruction[1])) {
          System.out.println("error clear" + currentInstruction[1]);
          System.exit(0);
        }
      } else if (currentInstruction[0].equals("incr") && currentInstruction.length == 2) {
        //check if next is valid var name
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]*", currentInstruction[1])) {
          System.out.println("error incr");
          System.exit(0);
        }
      } else if (currentInstruction[0].equals("decr") && currentInstruction.length == 2) {
        //check if next is valid var name. the executer will check if var exits already
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]*", currentInstruction[1])) {
          System.out.println("error decr");
          System.exit(0);
        }


      } else if (currentInstruction[0].equals("while") && currentInstruction.length == 5) {
        //check if next is not 0 do etc and then initiate object. end handled by this
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]*", currentInstruction[1])
            || currentInstruction[2] != "not" || !Pattern.matches("[0-9]+",
            currentInstruction[3])) {

          Parser newParser = new Parser(position, null);
          position+=1; /*the location of this increment
          is important because the position must be incremented to avoid an infinite loop but start
          id must be the same as the present position.*/
          parsedTree = newParser.run(parsedTree, position);
          String[] positionArray = parsedTree.remove(parsedTree.size() - 1);

          position = Integer.parseInt(positionArray[0]);
          System.out.println("escaped loop"); //maybe add a count for end and while and check they match

        }

      } else if (currentInstruction[0].equals("end") && currentInstruction.length == 1 && type == null) {
        //update the while jump to and end pointers and give back the new position
        System.out.println("end"+startid);
        parsedTree.get(startid)[4] = Integer.toString(position);
        parsedTree.get(position)[0] = Integer.toString(startid);
        String[] returnIndex = Integer.toString(position).split(" ");
        parsedTree.add(returnIndex);
        return parsedTree;

      } else {
        System.out.println("stack error or expression not in vocabulary");
        System.out.println(currentInstruction[0]);
        System.exit(0);
      }
      position += 1;
    }
    if (type == null) {
      System.out.println("while finished without end");
      System.out.println(position);
    }
    String[] returnIndex = Integer.toString(position).split(" ");
    parsedTree.add(returnIndex);
    return parsedTree;


  }

  public List<String> readTextFile(String filename) {
    List<String> returnList = new ArrayList<>();
    try {
      String type;
      String removedItem;
      BufferedReader obj = new BufferedReader(new FileReader(filename));
      String line;
      Hashtable<String, Integer> variables = new Hashtable<String, Integer>();
      List<String> stack = new ArrayList<>();

      boolean readOn = true;
      String theProgramString = "";
      while ((line = obj.readLine()) != null) {
        //System.out.println(line);
        theProgramString += line;
      }
      String[] minilist = theProgramString.split("[ \n\t\r]*;[ \n\t\r]*");
      returnList = Arrays.stream(minilist).toList();
      obj.close();
      return returnList;


    } catch (FileNotFoundException fnfe) {
      System.out.println("file not found");
      System.exit(0);
    } catch (IOException ioe) {
      System.out.println("ioe");
      System.exit(0);
    }
    return returnList;
  }


}
