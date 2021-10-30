import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.*;
import java.util.Arrays;

public class WhileObject {

  private int startAddress;

  public WhileObject(int startAddress) {
    this.startAddress = startAddress;
  }

  public List<String[]> parse(List<String[]> thelist, int index) {
    int startIndex = index;
    while (thelist.get(index)[0] != "end" || thelist.get(index)[0] == "while") {
      index += 1;
    }
    //edit thelist to include a return address for end and while or call new while object
    return thelist; //it will return a small list detailing end index of super list
  }
}