import java.util.*;
public class testNew {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter the phrase: ");
    String input = in.next();
    System.out.println(input);
    //if(input[0] == 'x') {System.out.println("1");}
    if(input.charAt(0) == 'x') {System.out.println("2");}
    //if(input.length == 3) {System.out.println("3");}
    if(input.length() == 3) {System.out.println("4");}
    boolean checkDigit = (Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(2)));
    if(checkDigit == true) {System.out.println("5");}
    int lastDigit = Integer.parseInt(input.substring(1));
    if(lastDigit >= 10 && lastDigit <= 99) {System.out.println("6");}
    in.close();
    System.out.println("After 2: "+input.substring(2));
  }
}
