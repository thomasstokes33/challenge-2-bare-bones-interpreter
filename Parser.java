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
  

  public static void main(String[] args) {
    Parser theparser = new Parser();
    theparser.setup();

  }

  public void setup() {

    List<String> theTree = new ArrayList<>();
    theTree = readTextFile("sample.txt");
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

    run(parsedTree, 0);
  }

  public void run(List<String[]> parsedTree, int position) {
    // TODO: now I need to add in error checking and finally execute + write javadoc

    while (position < (parsedTree.size() - 1)) {
      String[] currentInstruction = parsedTree.get(position);
      if (currentInstruction[0] == "clear") {
        //check if next instruction is a variable
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]", currentInstruction[1])) {
          System.out.println("error");
          System.exit(0);
        }
      } else if (currentInstruction[0] == "incr") {
        //check if next is valid var name
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]", currentInstruction[1])) {
          System.out.println("error");
          System.exit(0);
        }
      } else if (currentInstruction[0] == "decr") {
        //check if next is valid var name. the executer will check if var exits already
        if (!Pattern.matches("[A-Za-z]+[A-Za-z0-9]", currentInstruction[1])) {
          System.out.println("error");
          System.exit(0);
        }


      } else if (currentInstruction[0] == "while") {
        //check if next is not 0 do etc and then initiate object. end handled by this
        WhileObject whileReader = new WhileObject(position);

      } else if (currentInstruction[0] == "end") {

      }
      position += 1;
    }


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
