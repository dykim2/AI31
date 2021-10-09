package baselineCustomClasses;
import java.awt.Image;//for images and such;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
//this class is a base to all playing card games and all methods that throw an exception throw a PlayingCardException
/**
 * A playing card class. This class can be used for any game, any program that needs cards. There are a lot of tools 
 * in the program to assist playing a variety of different games.
 * @author Dong-Yon Kim
 * @since 1.0
 */
public class PlayingCard {//this assumes the running of one game at a time, not two simultaneously (one after the other is fine)
  /**
   * The number of the card.
   */
  private int number;
  /**
   * The face of the card.
   */
  private char face;
  /**
   * The suit of the card.
   */
  private String suit;
  /**
   * The 4 suits.
   */
  public static final String[] SUITS = {"hearts", "diamonds", "spades", "clubs"};
  /**
   * The 4 face card characters.
   */
  public static final char[] FACE_CARDS = {'J','Q','K','A'}; //i need these separate so i 
  // dont rely on the AI31 constants for future games
  /**
   * A specific number used to describe face cards (J, Q, K, or A) for image and other purposes.
   * Aces are 14, kings 13, queens 12, and jacks 11.
   * If the card is not a face card the pip value is the special number.
   */
  private int specialNumber = 0;
  /**
   * The image that belongs with this card.
   */
  private Image pic = null;//time to add the images!
  /**
   * Are aces higher than faces or lower than two's? By default they are low (false).
   */
  private static boolean aceHigh = false;//deafult: ace is 1
  /**
   * How much are aces worth, pip value wise?
   */
  private static int aceValue = 1;//1,11, or 14 or even 15 - depends on the game
  /**
   * The value of aces prior to the latest change.
   */
  private static int previousAceValue = 1;
  /**
   * Do the face card values increment or stay the same? (King > Queen > Jack)
   */
  private static boolean faceCardsIncrement = true;//king larger than queen larger than jack - k 13, q 12, j 11
  /**
   * How much are jacks (or any other face card if faceCardsIncrement is false) valued at?
   */
  private static int jackValue = 11;
  /**
   * The value of jacks prior to the last change.
   */
  private static int previousJackValue = 10;
  /**
   * A constant that signifies the value of aces must be updated.
   */
  public static final int CHANGE_ACE = 1;
  /**
   * A constant that signifies the value of jacks must be updated.
   */
  public static final int CHANGE_JACK = 0;
  /**
   * This is a tester PlayingCard that has no current uses.
   */
  public static final PlayingCard NOT_CARD = new PlayingCard(-6, "Not a card!");
  /*VVVVVVVVVV Constructors VVVVVVVVVV*/
  public PlayingCard(){number = 2; face = ' '; suit = "spades"; specialNumber = 2; setImage();}
  /**
   * Creates a deep copy of the given PlayingCard object.
   * @param pc - the given PlayingCard
   */
  public PlayingCard(PlayingCard pc){
    switch(pc.face) {
      case 'A':
      case 'a':
        number = aceValue;
        break;
      case 'J':
      case 'j':
        number = jackValue;
        break;
      case 'Q':
      case 'q':
        if(faceCardsIncrement) {number = jackValue+1;}
        else {number = jackValue;}
        break;
      case 'K':
      case 'k':
        if(faceCardsIncrement) {number = jackValue+2;}
        else {number = jackValue;}
        break;
      case ' ':
        number = pc.number;
        break;
    }
    face = pc.face;
    suit = new String(pc.suit);
    specialNumber = pc.specialNumber;
    pic = pc.pic;

  }
  /**
   * Creates a PlayingCard with the given number and suit (for non-face cards and aces).
   * @param n - the number on the card
   * @param s - the card's suit (one of the main four)
   */
  public PlayingCard(int n, String s){
    number = n;
    face = ' ';
    suit = new String(s);
    specialNumber = n;
    if(n!=105) {setImage();}
  }
  /**
   * Creates a PlayingCard with the given face (K, Q, J, or A) and suit (for face cards and aces).
   * @param f - the face of the card
   * @param s - the card's suit (one of the main four)
   */
  public PlayingCard(char f, String s){
    try {
      face = f;
      if(f == 'A' || f == 'a'){number = aceValue; specialNumber = 14;}
      else if(f == 'K' || f == 'k'){if(faceCardsIncrement){number = jackValue + 2;} else{number = jackValue;}specialNumber = 13;}
      else if(f == 'Q' || f == 'q'){if(faceCardsIncrement){number = jackValue + 1;} else{number = jackValue;}specialNumber = 12;}
      else if(f == 'J' || f == 'j'){if(faceCardsIncrement){number = jackValue;} else{number = jackValue;}specialNumber = 11;}
      else if(f == ' '){}
      else{throw new PlayingCardException(f+" is not a valid face.", "C4854");}
      suit = new String(s);
      setImage();
    }
    catch(PlayingCardException e) {displayException(e);}
  }
  /*VVVVVVVVVV Getting value functions VVVVVVVVVV*/
  /**
   * Gets the status for aces and jacks.
   * @param choice - 1 for determining whether aces are high (true) or not (false), 
   * 0 for determing whether face cards increment (true) or not (false)
   * @return the selected choice condition - 1: whether aces are high or not, and 0: whether face cards increment or not
   * @throws PlayingCardException - if choice is not 1 or 0 (not asking for face card increment or aces high)
   */
  public static boolean getValueConditions(int choice) throws PlayingCardException{
    if(choice==1){return aceHigh;}
    else if(choice==0){return faceCardsIncrement;}
    else{throw new PlayingCardException(choice+" is not a valid choice.", "V4009");}
  }
  /**
   * Used to get the number of the card.
   * @return the number value of the card
   */
  public int getNumber(){return number;}
  /**
   * Used to get the face of the card.
   * @return the face of the card
   */
  public char getFace(){return face;}
  /**
   * Used to get the suit of the card.
   * @return the suit of the card
   */
  public String getSuit(){return suit;}
  /**
   * Used to get the special number of the card.
   * @return the special number value of the card
   */
  public int getSpecialNumber(){return specialNumber;}
  /**
   * Used to get the image of the card.
   * @return
   */
  public Image getImage() {return pic;}
  /**
   * Used to get the current value of aces.
   * @return how much aces are worth
   */
  public static int getAceValue() {return aceValue;}
  /**
   * Used to get the current value of jacks.
   * @return how much jacks are worth
   */
  public static int getJackValue() {return jackValue;}
  public String toString(){
    if(getSuit().equalsIgnoreCase("not a card!")) {return "not a card!";}
    if(face!=' ') {return face+" of "+suit;}
    else {return number+" of "+suit;}
  }
  /*VVVVVVVVVV Setting value functions VVVVVVVVVV*/
  /**
   * Sets the values for aces and jacks.
   * @param choice - +1 for aces, 0 for face cards
   * @param condition - true for aces: if aces are high or not (true means aces high), 
   * true for faces: whether the face cards increment (11-12-13) or stay the same (10-10-10) (true means faces increment)
   * 
   * @param val - the new value of the card type you chose
   * @throws PlayingCardException - if condition is not 1 or 0 (not a valid option)
   */
  public static void setValueConditions(int choice, boolean condition, int val) throws PlayingCardException{
    if(choice == 1){aceHigh = condition; previousAceValue = aceValue; aceValue = val;}
    else if(choice == 0){faceCardsIncrement = condition; previousJackValue = jackValue; jackValue = val;}
    else{throw new PlayingCardException(choice+ "is not a valid choice.","B1424");}}
  /**
   * Adjusts the number of the card if at least one of the jack or ace value or the face card increment is changed.
   * @throws PlayingCardException if the face of the card is invalid 
   */
  public void updateNumber() throws PlayingCardException {
    if(number == previousAceValue){number = aceValue;}
    else if(number==previousJackValue+2){number=jackValue;}
    else if(number==previousJackValue+1){number=jackValue;}
    else if(number==previousJackValue){
      if(face=='K'||face=='k'){number=jackValue+2;}
      else if(face=='Q'||face=='q'){number=jackValue+1;}
      else if(face=='J'||face=='j'){number=jackValue;}
      else{throw new PlayingCardException(face+" is not a valid face.", "1531");}
    }

  }
  /**
   * Sets the number of the card to the given number.
   * @param n the new number
   */
  public void setNumber(int n){number = n;}
  /**
   * Sets the face of the card to the given face.
   * @param f the new face
   */
  public void setFace(char f){face = f;}
  /**
   * Sets the suit of the card to the given suit.
   * @param s the new suit
   */
  public void setSuit(String s){suit = new String(s);}
  /**
   * Sets the image of the ard to the given image.
   * @param i the new image
   */
  public void setImage(Image i) {pic = i;}
  /**
   * Sets the value of jacks to the given value.
   * @param val the new value of jacks
   */
  public void setJackValue(int val) {previousJackValue = jackValue; jackValue = val;}
  /**
   * Sets the value of aces to the given value.
   * @param val the new value of aces
   */
  public void setAceValue(int val) {previousAceValue = aceValue; aceValue = val;}
  /**
   * Locates and adds the image that corresponds to this card.
   * @param specialNumber the number used to represent the card number/face
   * @param suit the suit of the card
   */
  public void setImage() {
    if(suit.contains("Random") || suit.equalsIgnoreCase("not a card!")) {return;}
    String add = "";
    switch(specialNumber) {
      case 11:
        add = "J";
        break;
      case 12:
        add = "Q";
        break;
      case 13:
        add = "K";
        break;
      case 14:
        add = "A";
        break;
      default:
        add = ""+specialNumber;
        break;
    }
    add+=suit.substring(0, 1).toUpperCase();//creates the string for the image
    try {
      Image image = ImageIO.read(getClass().getResource("/Pictures/"+add+".png"));//reads the image
      Image newImg = image.getScaledInstance(-2, 100, Image.SCALE_SMOOTH);//rescales the image
      setImage(newImg);//sets the image of this card
    }
    catch(IOException e) {displayException(e);}
  }
  /**
   * Returns a copy (not the same reference) of the card image with this card's number, face, and suit.
   * @return a deep copy of the image
   */
  public Image recieveImage() {
    String add = "";
    switch(specialNumber) {
      case 11:
        add = "J";
        break;
      case 12:
        add = "Q";
        break;
      case 13:
        add = "K";
        break;
      case 14:
        add = "A";
        break;
      default:
        add = ""+specialNumber;
        break;
    }
    add+=suit.substring(0, 1).toUpperCase();
    //System.out.println(add);
    try {return ImageIO.read(getClass().getResource("/Pictures/"+add+".png")).getScaledInstance(-2, 100, Image.SCALE_SMOOTH);}
    catch(IOException e) {displayException(e); return null;}
  }
  /*VVVVVVVVVV Comparison related methods VVVVVVVVVV*/
  /**
   * <html>
   * Compares two cards and returns an int that represents the card that has the larger value.
   * @param c - the second PlayingCard you want to compare this PlayingCard against
   * @return -1 when the first card is larger, <br>
   * 1 when the second card is larger, and <br>
   * 0 when the two cards are equal. <br>
   * This only compares on the basis of number.
   * </html>
   */
  public int compareByNumber(PlayingCard c){
    //return -1 when the first card is larger
    //return 0 when they are equal
    //return 1 when the second card is larger (number only)
    if(this == c || this.number == c.number){return 0;}
    else if(this.number > c.number){return -1;}
    else{return 1;}
  }
  /**
   * <html>
   * Compares two cards and returns a boolean checking if the card's faces are the same.s
   * @param c - the second PlayingCard you want to compare this PlayingCard against
   * @return true when the two cards have the same face and <br>
   * false when the two cards have different face. <br>
   * This only checks if the two cards have the same face, nothing else.
   * </html>
   */
  public boolean compareByFace(PlayingCard c){
    //return false when the faces are not the same
    //return true when the faces are the same
    if(this.face == c.face || this == c){return true;}
    else{return false;}
  }
  /**
   * <html>
   * Compares two cards and returns a boolean checking if the card's suits are the same.
   * @param c - the second PlayingCard you want to compare this PlayingCard against
   * @return true when the two cards have the same suit and <br>
   * false when the two cards have different suit. <br>
   * This only checks if the two cards have the same suit, nothing else.
   * </html>
   */
  public boolean compareBySuit(PlayingCard c){
    //return false when the suits are not the same
    //return true when the suits are the same
    if(this.suit.equals(c.suit) || this == c){return true;}
    else{return false;}
  }
  public boolean equals(Object o){
    if(o.getClass().getName().equals("baselineCustomClasses.PlayingCard")){
      if(this == o){return true;}
      PlayingCard c = (PlayingCard)o;
      if((c.getNumber() == this.getNumber() && c.getSuit().equals(this.getSuit())) && c.getFace() == this.getFace()) {return true;}
      else{return false;}
    }
    else{throw new ClassCastException(o.getClass().getName()+" cannot be cast to a PlayingCard object.");}
  }
  /**
   * Checks whether aces or kings hold more value.
   * @return A if aces are worth more than kings, K otherwise
   */
  public static char largestFace(){
    if(!faceCardsIncrement){
      if(aceValue>jackValue){return 'A';}
      else if(aceValue<jackValue){return 'K';}
      else{return 'A';}
    }
    else{
      if(aceValue>jackValue+3){return 'A';}
      else if(aceValue<jackValue+3){return 'K';}
      else{return 'K';}
    }
  }
  /**
   * Creates an array of the standard 52 playing cards.
   * @return
   */
  public static final PlayingCard[] buildAllCards() {
    PlayingCard[] allCards = new PlayingCard[52];
    int index = 0;
    for(int i=2; i<11; i++) {
      for(int j=0; j<4; j++) {
        allCards[index] = new PlayingCard(i, SUITS[j]);
        index++;
      }
    }
    for(int i=0; i<4; i++) {
      for(int j=0; j<4; j++) {
        allCards[index] = new PlayingCard(FACE_CARDS[i], SUITS[j]);
        index++;
      }
    }
    return allCards;
  }
  /**
   * Creates a deck of cards, but starting with the sevens and onwards (excluding 2-6).
   * @return an array of size 32 with all of the standard cards from a pack of 32
   */
  public static final PlayingCard[] buildHalfDeckCards() {
    PlayingCard[] allCards = new PlayingCard[32];
    int index = 0;
    for(int i=7; i<11; i++) {
      for(int j=0; j<4; j++) {
        allCards[index] = new PlayingCard(i, SUITS[j]);
        index++;
      }
    }
    for(int i=0; i<4; i++) {
      for(int j=0; j<4; j++) {
        allCards[index] = new PlayingCard(FACE_CARDS[i], SUITS[j]);
        index++;
      }
    }
    return allCards;
  }
  /**
   * Displays the question in a pop up dialog.
   * @param body - the Object the user want to ask as a JOptionPane
   * @param title - the title of the question
   * @return the result of the question
   */
  protected static String displayQuestion(Object body, String title) {
    String result = JOptionPane.showInputDialog(null, body, title, JOptionPane.QUESTION_MESSAGE);
    try {if(result==null) {throw new NullPointerException("You must enter a value for the result. Error #N8437");}}
    catch(NullPointerException e) {displayException(e);}
    return result;
  }
  /**
   * Displays the question in a pop up dialog and ignores all cases of null instances.
   * @param body - the Object the user want to ask as a JOptionPane
   * @param title - the title of the question
   * @return the result of the question
   */
  protected static String displayQuestionIgnoreNull(Object body, String title) {return JOptionPane.showInputDialog(null, body, title, JOptionPane.QUESTION_MESSAGE);}
  /**
   * Displays the information in a pop up dialog with the title as "Information".
   * @param body - the Object the user wants to display as a JOptionPane
   * @param title - the title of the message
   */
  protected static void displayInfo(Object body) {displayInfo(body, "Information!");}
  /**
   * Displays the information in a pop up dialog. 
   * @param body - the Object the user wants to display as a JOptionPane
   * @param title - the title of the message
   */
  protected static void displayInfo(Object body, String title) {
    JOptionPane.showMessageDialog(null, body, title, JOptionPane.INFORMATION_MESSAGE);}
  /**
   * Displays the warning in a pop up dialog.
   * @param body - the Object the user wants to warn as a JOptionPane
   * @param title - the title of the message
   */
  protected static void displayWarning(Object body, String title) {
    JOptionPane.showMessageDialog(null, body, title, JOptionPane.WARNING_MESSAGE);}
  /**
   * Displays the error in a pop up dialog with the title "ERROR!".
   * @param body - the Object the user wants to display an error for as a JOptionPane
   */
  protected static void displayError(Object body) {JOptionPane.showMessageDialog(null, body, "ERROR!", JOptionPane.ERROR_MESSAGE);}
  /**
   * <html>
   * Displays an exception and what you normally would see in the console as a result of said exception in dialog boxes.
   * @param e - the Exception you want to display
   * @param location - where the Exception occured (which class?) <br>
   * 0: UserInterface<br>
   * 1: UserPanel<br>
   * 2: CustomFrame<br>
   * 3: Player<br>
   * 4: PlayingCard, and<br>
   * 5: Other.
   * </html>
   * 
   */
  protected static void displayException(Exception e) {
    /*
     * This function uses a StringWriter to obtain the information from the Exception's printStackTrace() method
     * and a PrintWriter to ensure compatibility for the method. Then it displays the information in three dialog boxes and prints the actual stack trace..
     */
    try{
      StringWriter s = new StringWriter();
      PrintWriter p = new PrintWriter(s);
      e.printStackTrace(new PrintWriter(s)); 
      displayError(e);
      displayError(s.toString());
      System.out.println(s.toString());
      displayError("Playing Card Error");
      s.close();
      p.close();
    }
    catch(IOException ex){displayException(ex);}
  }
  /**
   * Displays the Object in a pop up dialog and asks for specific information, based on the selection array.
   * @param body - the Object the user wants to display as a JOptionPane
   * @param selection - the option choices for the buttons of the JOptionPane
   * @param title - the title of the message
   * @return an integer representing the chosen option from the array
   */
  protected static int displayOptions(Object body, Object[] selection, String title) {
    return JOptionPane.showOptionDialog(null, body, title, JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE, null, selection, null);}
  /**
   * Displays the Object in a pop up dialog and asks for specific information.
   * @param body - the Object the user wants to display as a JOptionPane
   * @param title - the title of the message
   * @return an integer representing the chosen button (2 for cancel, 0 for yes, 1 for no)
   */
  protected static int displayOptions(Object body, String title) {
    return JOptionPane.showOptionDialog(null, body, title, JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE, null, null, null);}
  /**
   * <html>
   * @see AI31.UserInterface#displayOptions(Object, String)
   * Note that the conditions and return values are exactly the same as: <br>
   * AI31.UserInterface#displayOptions(Object, String).<br>
   * The only difference: This function's buttons are titled YES, NO, and CANCEL, not OK and CANCEL.
   * </html>
   */
  protected static int displayYesNo(Object body, String title) {
    return JOptionPane.showOptionDialog(null, body, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);}
}
