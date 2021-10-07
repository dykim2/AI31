package AI31;

import java.util.ArrayList;
import baselineCustomClasses.GameErrorException;
public class MiddleHand extends Player {
  /**
   * Creates a new Middle Hand in the given user interface. 
   * The user interface will have name "The middle hand".
   * @param u the user interface this middle hand is part of
   */
  public MiddleHand(UserInterface u){super(u); setName("THE MIDDLE HAND");}
  /**
   * Creates a new Middle Hand in the given user interface with the cards from the given middle hand.
   * @param m the middle hand to get cards from
   * @param u the user interface this middle hand is part of
   */
  public MiddleHand(MiddleHand m, UserInterface u){this(m.getHand(), u);}
  /**
   * Creates a new Middle Hand with the given user interface and set of cards.
   * @param a the array list of cards to add
   * @param u the user interface this middle hand is part of
   */
  public MiddleHand(ArrayList<Card31> a, UserInterface u){this(u); addCard(a.get(0)); addCard(a.get(1)); addCard(a.get(2));}
  @Override
  public int[] takeTurn(){
    try{throw new GameErrorException("The middle should not be able to take a turn.", "Z0134");}
    catch(GameErrorException e){UserInterface.displayException(e, 5); return null;}
  }
  @Override
  public String toString(){
    return "The cards in the middle are: "+getCard(0)+" (1), "+getCard(1)+" (2), and "+getCard(2)+" (3),\nSum: "+sumOfHand()+"\n";
  }
  //the hand that is face up; the contents are at all times visible to all players
  //everyone can see its contents
  /**
   * Finds a card with the given face and number.
   * @param num the number of the card to find
   * @param f the face of the card to find
   * @return the first index at which a card with the given number and face exists
   */
  public int contains(int num, char f){
    for(int i=0; i<getHand().size(); i++){if(getCard(i).getFace() == f && getCard(i).getNumber() == num){return i;}}return -1;}
  /**
   * Finds a card with the given suit.
   * @param s the suit of the card to find
   * @return the first index at which a card with the given suit exists
   */
  public int contains(String s){for(int i=0; i<handSize(); i++){if(getCard(i).getSuit().equalsIgnoreCase(s)){return i;}}return -1;}
  /**
   * Finds a card with the given special number.
   * @param specialNum the special number of the card to find
   * @return the first index at which a card with the given special number exists
   */
  public int contains(int specialNum){
    for(int i=0; i<handSize(); i++){if(getCard(i).getSpecialNumber()==specialNum){return i;}}return -1;}
  /**
   * Replaces the card with the given index in the middle hand with the card provided.
   * @param out - the index of the card you want to remove
   * @param in - the card you want to add in
   * @return the new sum of mid after changing these cards
   */
  public MiddleHand editHand(int out, Card31 in) {
    ArrayList<Card31> copyHand = new ArrayList<Card31>(getHand());
    copyHand.remove(out);
    copyHand.add(out, in);
    MiddleHand newMid = new MiddleHand(copyHand, getUserInterface());
    return newMid;
  }

}
