import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
public class QuickTest {
    public static void main(String[] args) {
        List<String> nums=Arrays.asList("0", "1","2","3","4","5","6","7","8","9");
        String stringading= "9122hj";
        if (nums.contains(stringading.substring(0,1))) {
            System.out.print("yeah");
            System.out.println(stringading.substring(0,1));

        }
        List<String> things= new ArrayList<>();
        //System.out.println(things.get(0));
        Hashtable<String, Integer> thevars=new Hashtable<>();
        thevars.put("1",1);
        thevars.put("1",2);
        System.out.println(thevars.get("1"));
        System.out.println(thevars.toString());
    }
}
