package AI31;
import java.util.*;

import baselineCustomClasses.GameErrorException;
import baselineCustomClasses.PlayingCardException;
public class MiddleHand extends Player {
	public MiddleHand(UserInterface u){super(u); setName("THE MIDDLE HAND");}
	public MiddleHand(MiddleHand m, UserInterface u){this(u); addCard(m.getCard(0)); addCard(m.getCard(1)); addCard(m.getCard(2));}
	public MiddleHand(ArrayList<Card31> a, UserInterface u){this(u); addCard(a.get(0)); addCard(a.get(1)); addCard(a.get(2));}
	public int[] takeTurn(){
		try{throw new GameErrorException("The middle should not be able to take a turn.");}
		catch(GameErrorException e){UserInterface.displayException(e, 5); return null;}
	}
	public String toString(){
		try{return "The cards in the middle are: "+getCard(0)+" (1), "+getCard(1)+" (2), and "+getCard(2)+" (3),\nSum: "+sumOfHand()+"\n";}
		catch(GameErrorException e){
			UserInterface.displayException(e, 5);
			throw new NullPointerException("Something went wrong - there is no middle hand...");
		}
	}
	//the hand that is face up; the contents are at all times visible to all players
	//everyone can see its contents
	public int contains(int num, char f){
		for(int i=0; i<getHand().size(); i++){if(getCard(i).getFace() == f && getCard(i).getNumber() == num){return i;}}return -1;}
	public int contains(String s){for(int i=0; i<handSize(); i++){if(getCard(i).getSuit().equalsIgnoreCase(s)){return i;}}return -1;}
	public int contains(int specialNum){
		for(int i=0; i<handSize(); i++){if(getCard(i).getSpecialNumber()==specialNum){return i;}}return -1;}
	/**
	 * 
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
