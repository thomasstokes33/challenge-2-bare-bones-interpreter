import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.*;

public class BareBones {

  public List<String> basictypes = Arrays.asList("clear", "incr", "decr");
  public List<String> nums = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

  public static void main(String[] args) {
    BareBones program = new BareBones();
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
      String[] minilist = theProgramString.split(" ");
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

        if (stack.size() == 0 || "while".contains(stack.get(stack.size() - 1))) {
          System.out.println("check for new char");
          switch (minilist[count]) {
            case "incr":
              stack.add("incr");
              break;
            case "decr":
              stack.add("decr");
              break;
            case "clear":
              stack.add("clear");
              break;
            case "while":
              // WhileObject while1 = new WhileObject(variables);
              // variables = while1.getvars();
              whilestack.add(count);
              stack.add("while");

              break;
            default:
              System.out.println("error1");

          }

        } else if (stack.get(stack.size() - 1) == "while") {
          //check condition
          if (Pattern.matches("[A-Za-z]+ not [0-9]+", minilist[count])) {
            stack.add("var");
            stack.add("do");
            Pattern rgx = Pattern.compile("([A-Za-z]+) not ([0-9]+)");
            Matcher results = rgx.matcher(minilist[count]);
            results.find();
            if (variables.containsKey(results.group(1))
                && variables.get(results.group(1)) != Integer.parseInt(results.group(2))) {
              readOn = true;
            } else {
              readOn = false;
            }


          } else {
            System.out.println("error");
          }


        } else if (stack.get(stack.size() - 1) == "do") { /*  IIIIIIIIIIIIIIIIIIIIII */

          String[] substrings = minilist[count].split(";");
          if (Pattern.matches("do;?", minilist[count])) {
            if (minilist[count] == "do") {
              stack.add(";");
            } else if (minilist[count] == "do;") {
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                stack.remove(stack.size() - 1);
              }
            } else if (substrings.length > 1) {
              if (basictypes.contains(substrings[1])) {
                while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                  stack.remove(stack.size() - 1);
                }
                stack.add(substrings[1]);

              }
            }
          } else {
            System.out.println("error");
          }

        } else if (basictypes.contains(stack.get(stack.size() - 1))) {
          //check for var
          //  Pattern rgx=Pattern.compile("\\d+;?");
          //  Matcher results=rgx.matcher(minilist[count]);

          //  results.find();
          //  results.group(0);
          if (nums.contains((minilist[count]).substring(0, 1)) == false && Pattern.matches(
              "[A-Za-z]+(;|end|end;)*(incr|clear|decr|while)?", minilist[count])) {
            //add semicolon or var
            stack.add("var");
            type = stack.get(stack.size() - 2);
            String[] partsOfSubstring = minilist[count].split(";");
            String varname = partsOfSubstring[0];
            //System.out.println("varname equals "+ varname+type.compareTo("incr")+(type=="incr"));

            if (variables.containsKey(varname) == true) {
              //System.out.println("contains key" +(type.contains("incr"))+("incr".contains(type)+"b"));
              if (type.contains("incr")) {
                System.out.println("incrementing");
                int valueTemp = variables.get(varname);
                if (readOn == true) {

                  variables.put(varname, valueTemp += 1);
                }
              } else if (type.contains("decr")) {
                System.out.println("decrementing");
                int valueTemp = variables.get(varname);
                if (readOn == true) {
                  variables.put(varname, valueTemp -= 1);
                }
              } else {
                System.out.println("?");
              }
            }

            if (type == "clear") {
              variables.put(varname, 0);

            }

            if (partsOfSubstring.length > 1) {
              System.out.println("here");
              if (partsOfSubstring[partsOfSubstring.length - 1] == "end;") {

              }
              if (basictypes.contains(partsOfSubstring[1]) || "while".contains(
                  partsOfSubstring[1])) {
                while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                  stack.remove(stack.size() - 1);
                }
                stack.add(partsOfSubstring[1]);
              }
            } else if (Pattern.matches("[A-Za-z];", minilist[count]) == true) {
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                stack.remove(stack.size() - 1);
              }
            } else if (Pattern.matches("[A-Za-z]", minilist[count]) == true) {
              stack.add(";");
            }

          } else {
            System.out.println(
                "error vars" + minilist[count] + (Pattern.matches("[A-Za-z]+;?", minilist[count])));
          }

        } else if (Pattern.matches("end;?[incr|decr|clear|while]?", minilist[count])) {
          System.out.println("last command end");
          String[] partsOfSubstring = minilist[count].split(";");

          // } else if ( ((item = (stack.get(stack.size()-1))) == "var")) {
          //     System.out.println("last command" + item );
          //     stack.add(";"); //and more
          if (partsOfSubstring.length > 1) {
            if (basictypes.contains(partsOfSubstring[1]) || partsOfSubstring[1] == "while") {
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                removedItem = stack.remove(stack.size() - 1);
                if (removedItem == "end" && readOn == true) {
                  count = whilestack.remove(whilestack.size() - 1);
                  stack.remove(stack.size() - 1);
                }
              }
              if (readOn == false) {
                whilestack.add(count);
                stack.add("while");
              }
            }
          } else if (Pattern.matches("[A-Za-z];", minilist[count]) == true) {
            while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
              removedItem = stack.remove(stack.size() - 1);
              if (removedItem == "end" && readOn == true) {
                count = whilestack.remove(whilestack.size() - 1);
                stack.remove(stack.size() - 1);
              }
            }
          } else if (Pattern.matches("[A-Za-z]", minilist[count]) == true) {
            stack.add("end");
            stack.add(";");
          }


        } else if ((item = (stack.get(stack.size() - 1))) == ";") {
          System.out.println("last command" + item);

          switch (minilist[count]) {
            case ";":
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                removedItem = stack.remove(stack.size() - 1);
                if (removedItem == "end" && readOn == true) {
                  count = whilestack.remove(whilestack.size() - 1);
                  stack.remove(stack.size() - 1);
                }

              }

              break;
            case ";clear":
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                removedItem = stack.remove(stack.size() - 1);
                if (removedItem == "end" && readOn == true) {
                  count = whilestack.remove(whilestack.size() - 1);
                  stack.remove(stack.size() - 1);
                }

              }
              if (readOn == false) {
                stack.add("clear");
              }

              break;
            case ";incr":
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                removedItem = stack.remove(stack.size() - 1);
                if (removedItem == "end" && readOn == true) {
                  count = whilestack.remove(whilestack.size() - 1);
                  stack.remove(stack.size() - 1);
                }

              }
              if (readOn == false) {
                stack.add("incr");
              }

              break;
            case ";decr":
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                removedItem = stack.remove(stack.size() - 1);
                if (removedItem == "end" && readOn == true) {
                  count = whilestack.remove(whilestack.size() - 1);
                  stack.remove(stack.size() - 1);
                }


              }
              if (readOn == false) {
                stack.add("decr");
              }

              break;
            case ";while":
              stack.add("var");
              stack.add("do");
              Pattern rgx = Pattern.compile("([A-Za-z]+) not ([0-9]+)");
              Matcher results = rgx.matcher(minilist[count]);
              results.find();
              if (variables.containsKey(results.group(1))
                  && variables.get(results.group(1)) != Integer.parseInt(results.group(2))) {
                readOn = true;
              } else {
                readOn = false;
              }
              break;
            case ";end":
              while (stack.size() > 0 && (stack.get(stack.size() - 1) != "while")) {
                removedItem = stack.remove(stack.size() - 1);
                if (removedItem == "end" && readOn == true) {
                  count = whilestack.remove(whilestack.size() - 1);
                  stack.remove(stack.size() - 1);
                }
              }
              stack.add("end");
              stack.add(";");
              // if (readOn==false) {
              //     stack.add("end");
              //     stack.add(";");
              // }
              break;
          }

          //pop and stuff
        }
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