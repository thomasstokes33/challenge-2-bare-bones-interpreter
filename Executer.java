import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;
import java.lang.NumberFormatException;
import java.lang.NullPointerException;

/**
 * This class is used to execute the parsed list.
 */
public class Executer {

  private List<String[]> runList;

  /**
   * Constructor
   *
   * @param aList the parsed list
   */
  public Executer(List<String[]> aList) {
    runList = aList;
  }

  /**
   * This executes the code in the private attribute runList.
   */
  public void interpreter() {
    int position = 0;
    Hashtable<String, Integer> variables = new Hashtable<String, Integer>();
    String currentKey;
    System.out.println();
    while (position < runList.size()) {
      System.out.println(variables.toString() + "" + position);
      System.out.println("now executing" + runList.get(position)[0]);
      currentKey = "";
//      try {
//        Thread.sleep(0);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
      if (runList.get(position)[0].equals("clear")) {
        variables.put(runList.get(position)[1], 0);
        position += 1;
      } else if (runList.get(position)[0].equals("incr")) {
        currentKey = runList.get(position)[1];
        try {
          variables.put(currentKey, variables.get(currentKey) + 1);
          position += 1;
        } catch (NullPointerException npe) {
          System.out.println("dictionary error");
        }
      } else if (runList.get(position)[0].equals("decr")) {
        currentKey = runList.get(position)[1];

        try {
          variables.put(currentKey, variables.get(currentKey) - 1);
          position += 1;
        } catch (NullPointerException npe) {
          System.out.println("dictionary error");
        }

      } else if (runList.get(position)[0].equals("while")) { //assumes not keyword
        currentKey = runList.get(position)[1];

        if (variables.get(currentKey) == Integer.parseInt(runList.get(position)[3])) {
          position = Integer.parseInt(runList.get(position)[4]);

        }
        position += 1;
        //check condition and jump or continue
      } else if (Pattern.matches("[0-9]+", runList.get(position)[0])) {
        try {
          position = Integer.parseInt(runList.get(position)[0]);
        } catch (NumberFormatException nfe) {
          System.out.println("bigtime error");
        }
        //jumps back

      } else {
        System.out.println("problem");
      }


    }
    System.out.println("variables" + variables.toString());
  }
}
