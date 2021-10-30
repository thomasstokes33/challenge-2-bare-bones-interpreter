import java.util.Hashtable;
import java.util.List;

public class Executer {

  private List<String[]> runList;

  public Executer(List<String[]> aList) {
    runList = aList;
  }

  public void interpreter() {
    int position = 0;
    Hashtable<String, Integer> variables = new Hashtable<String, Integer>();
    String currentKey;
    while (position < runList.size() - 1) {
      currentKey = "";
      if (runList.get(position)[0] == "clear") {
        variables.put(runList.get(position)[1], 0);
      } else if (runList.get(position)[0] == "incr") {
        currentKey = runList.get(position)[1];
        variables.put(currentKey, variables.get(currentKey) + 1);
      } else if (runList.get(position)[0] == "decr") {
        currentKey = runList.get(position)[1];
        variables.put(currentKey, variables.get(currentKey) - 1);
      } else if (runList.get(position)[0] == "while") {
        ;
      }
    }
  }
}
