import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Interpreter2 {

  public List<String> basictypes = Arrays.asList("clear", "incr", "decr");
  public List<String> nums = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

  public static void main(String[] args) {
    Interpreter2 program = new Interpreter2();
    program.run();
  }

  public void run() {

    try {
      String type;
      String removedItem;
      BufferedReader obj = new BufferedReader(new FileReader("sample.txt"));
      String line;
      Hashtable<String, Integer> variables = new Hashtable<String, Integer>();
      List<String> stack = new ArrayList<>();
      List<Integer> whilestack = new ArrayList<>();
      boolean readOn = true;
      //stack.add("pear");
      //System.out.println(stack.get(0));
      String theProgramString = "";
      while ((line = obj.readLine()) != null) {
        System.out.println(line);
        theProgramString += line;
      }
      String[] minilist = theProgramString.split("[\n\t\r]*;[\n\t\r]*");
      int count = 0;
      String item;
      while (count <= (minilist.length - 1)) {
        System.out.println("");
        System.out.println("*****************");
        System.out.println(minilist[count]);
        System.out.println(stack.toString());
        System.out.println(variables.toString());
        System.out.println(readOn);

        System.out.println("");

        count += 1;


      }
      System.out.println(variables.toString());
      obj.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("file not found");
    } catch (IOException ioe) {
      System.out.println("ioe");
    }


  }

  public void updateVariables() {
    ;
  }
}