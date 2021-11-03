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
 * uniform manner. This parsed code will hopefully be read and executed by an Executer object.
 */
public class Parser {

  //TODO: comment
  //TODO: test syntax parser
  //TODO: check style
  private int startid;
  private String type;

  /**
   * Constructor
   *
   * @param index   start index
   * @param thetype shows if the parser object is the root object at the bottom of the stack
   */
  public Parser(int index, String thetype) {
    startid = index;
    type = thetype;
  }

  /**
   * Creates parser object and runs setup.
   *
   * @param args any arguments provided by the command line
   */
  public static void main(String[] args) {
    Parser theparser = new Parser(0, "root");
    theparser.setup();

  }

  /**
   * This prepares the code for parsing and calls the actual parsing function. It takes the
   * returned, parsed list and passes it to the Executer.
   */
  public void setup() {

    List<String> theTree = new ArrayList<>();
    theTree = readTextFile("sample2.txt");

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
    for (String[] instruction : returned) { //outputs the parsed program
      for (int i = 0; i < instruction.length; i++) {
        System.out.println(instruction[i]);
      }
    }
    Executer codeExecuter = new Executer(returned);
    codeExecuter.interpreter();

  }

  /**
   * This cycles through the instructions and checks the syntax as well as creating pointers for the
   * while loops.
   *
   * @param parsedTree this is the semi parsedtree which hasn't been syntax checked
   * @param position   the index to read
   * @return the syntax-checked, parsed tree
   */
  public List<String[]> run(List<String[]> parsedTree, int position) {

    while (position
        < (parsedTree.size())) { //TODO: what if the program reaches the end of a barebones while without end. maybe have if type not equal to root then error message
      //System.out.println(position+" "+parsedTree.size());
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
            || !currentInstruction[2].equals("not") || !Pattern.matches("[0-9]+",
            currentInstruction[3])) {
          System.out.println("error while");
          System.exit(0);
        }

        Parser newParser = new Parser(position, null);
        position += 1; /*the location of this increment
          is important because the position must be incremented to avoid an infinite loop but start
          id must be the same as the present position.*/
        parsedTree = newParser.run(parsedTree, position);
        String[] positionArray = parsedTree.remove(parsedTree.size() - 1);

        position = Integer.parseInt(positionArray[0]);
        System.out.println(
            "escaped loop"); //maybe add a count for end and while and check they match


      } else if (currentInstruction[0].equals("end") && currentInstruction.length == 1
          && type == null) {
        //update the while jump to and end pointers and give back the new position
        System.out.println("end" + startid);
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
      System.exit(0);
    }

    String[] returnIndex = Integer.toString(position).split(" ");
    parsedTree.add(returnIndex);
    return parsedTree;

  }

  /**
   * This method returns a list of all instructions without leading and trailing whitespace
   *
   * @param filename the name of the file containing the unparsed BareBones code
   * @return the list of instructions
   */
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

        line = line.split("//.")[0]; //removes comments

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
