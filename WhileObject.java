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
    public List<String> basictypes=Arrays.asList("clear", "incr","decr");
    public List<String> nums=Arrays.asList("0", "1","2","3","4","5","6","7","8","9");
    private Hashtable<String, Integer> thevars;
    public WhileObject(Hashtable<String, Integer> vars) {
        thevars = vars;
    }
    public Hashtable<String, Integer> getvars() {
        return thevars;
    }

    public void loop () {
        try{
            BufferedReader  obj= new BufferedReader(new FileReader("sample.txt"));
            String line;
            Hashtable<String, Integer> variables = new Hashtable<String, Integer>();
            List<String> stack= new ArrayList<>();
            //stack.add("pear");
            //System.out.println(stack.get(0));
            String parsed= new String();
            while (( line=obj.readLine()) != null) {
                System.out.println(line);
                String[] minilist = line.split(" ");
                int count=0;
                String item;
                while (count <= (minilist.length-1)) {
                    System.out.println(minilist[count]);
                    if (stack.size() == 0  ) {
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
                                
                                WhileObject while1 = new WhileObject(variables);
                                variables = while1.getvars();
                                break;
                            default:
                                System.out.println("error1");
                        
                        }

                    } else if (stack.get(stack.size() - 1) == "while") {
                        ;
                    
                    } else if (basictypes.contains(stack.get(stack.size() - 1)) ) {
                         //check for var
                        //  Pattern rgx=Pattern.compile("\\d+;?");
                        //  Matcher results=rgx.matcher(minilist[count]);
                         
                        //  results.find();
                        //  results.group(0);
                        if (nums.contains((minilist[count]).substring(0,1))==false && Pattern.matches("[A-Za-z]+;?", minilist[count])) {
                            //add semicolon or var
                            stack.add("var");
                            String type=stack.get(stack.size() - 2);
                            String[] partsOfSubstring = minilist[count].split(";");
                            String varname = partsOfSubstring[0];
                            System.out.println("varname equals "+ varname);
                            
                            if (variables.containsKey(varname)==true){
                                System.out.println("contains key");
                                if (type=="incr") {
                                    int valueTemp= variables.get(varname);
                                    variables.put(varname,valueTemp+=1);
                                } else if (type =="decr") {
                                    int valueTemp= variables.get(varname);
                                    variables.put(varname,valueTemp-=1);
                                }
                            }
                            if (type =="clear") {
                                variables.put(varname, 0);

                            }
                            if (Pattern.matches("[A-Za-z];?", minilist[count])==true) {
                                while (stack.size()>0 && (stack.get(stack.size()-1)!="while") ){
                                    stack.remove(stack.size()-1);
                                }   

                            } else if (partsOfSubstring.length>1){
                                if (basictypes.contains(partsOfSubstring[1])) {
                                    stack.add(partsOfSubstring[1]);
                                }
                            }else {
                                stack.add(";");
                            }

                        }  else {
                            System.out.println("error");
                        }
                    
                    } else if ( ((item = (stack.get(stack.size()-1))) == "end")) {
                        System.out.println("last command" + item );
                        stack.add("end");
                        stack.add(";");                        
                    // } else if ( ((item = (stack.get(stack.size()-1))) == "var")) {
                    //     System.out.println("last command" + item );
                    //     stack.add(";"); //and more
                    } else if  ((item = (stack.get(stack.size()-1))) == ";") {
                        System.out.println("last command" + item );
                        switch (minilist[count]) {
                            case ";":
                                while (stack.size()>0 && (stack.get(stack.size()-1)!="while") ){
                                    stack.remove(stack.size()-1);
                                }                                
                                break;
                            case ";clear":
                                while (stack.size()>0 && (stack.get(stack.size()-1)!="while") ){
                                    stack.remove(stack.size()-1);
                                }  
                                stack.add("clear");
                                break;
                            case ";incr":
                                while (stack.size()>0 && (stack.get(stack.size()-1)!="while") ){
                                    stack.remove(stack.size()-1);
                                }  
                                stack.add("incr");
                                break;
                            case ";decr":
                                while (stack.size()>0 && (stack.get(stack.size()-1)!="while") ){
                                    stack.remove(stack.size()-1);
                                }  
                                stack.add("decr");
                                break;
                        }
                        
                        //pop and stuff
                    }
                    count+=1;
                }
            }
            System.out.println(variables.toString());
            obj.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("file not found");
        } catch (IOException ioe) {
            System.out.println("ioe"); 
        }
    }
}