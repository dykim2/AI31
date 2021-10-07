package AI31;
import java.util.ArrayList;

import baselineCustomClasses.*;

public class Card31 extends PlayingCard implements Comparable<Card31>{
	private int removedIndex = -1;
	/**
	 * The list of random suits generated.
	 */
	public static final Card31[] RANDOM_SUITS = {new Card31(-1, "Random Spade"), new Card31(-1, "Random Club"), new Card31(-1, "Random Heart"),
			new Card31(-1, "Random Diamond"), new Card31(-1, "Random Spade or Club"), new Card31(-1, "Random Spade or Heart"), new Card31(-1, "Random Spade or Diamond"),
			new Card31(-1, "Random Club or Heart"), new Card31(-1, "Random Club or Diamond"), new Card31(-1, "Random Heart or Diamond"),
			new Card31(-1, "Random not Spade"), new Card31(-1, "Random not Club"), new Card31(-1, "Random not Heart"), 
			new Card31(-1, "Random not Diamond")};
	/**
	 * The list of strings of random suits that are generated.
	 */
	public static final String[] RANDOM_SUIT_STRINGS  = {"Random Spade", "Random Club", "Random Heart", "Random Diamond", "Random Spade or Club",
			"Random Spade or Heart", "Random Spade or Diamond", "Random Club or Heart", "Random Club or Diamond", "Random Heart or Diamond",
			"Random not Spade", "Random not Club", "Random not Heart", "Random not Diamond"};

	/**
	 * Creates a new Card (for the 31 game) with the given number and the hearts suit.
	 * @param num
	 */
	public Card31(int num){super(num, "hearts");}
	/**
	 * 
	 * @param c
	 */
	public Card31(PlayingCard c) {super(c);}
	public Card31(int num, String su){super(num,su);}
	public Card31(char fa, String su){super(fa, su);}
	public String toString(){return super.toString();}
	public boolean equals(Object o){
		if(o.getClass().getName().equals("AI31.Card31")){
			if(this == o){return true;}
			Card31 c = (Card31)o;
			if((c.getNumber() == this.getNumber() && c.getSuit().equals(this.getSuit())) && c.getFace() == this.getFace()) {return true;}
			return false;
		}
		else{throw new ClassCastException(o.getClass().getName()+" cannot be cast to a Card31 object.");}
	}
	public int compareTo(Card31 arg0){//only sorts by number - this object larger return positive, this object smaller returns negative
		if(this.getSpecialNumber() != arg0.getSpecialNumber()){return this.getSpecialNumber()-arg0.getSpecialNumber();}
		//tens
		else{return this.getSuit().compareTo(arg0.getSuit());}

	}
	public void setRemovedIndex(int ind) {removedIndex = ind;}
	public int getRemovedIndex() {return removedIndex;}
	public boolean isRandomCard() {
		if(getSuit().contains("Random ")) {return true;}
		return false;
	}
	/**
	 * <html>
	 * DO NOT USE if the card does not have a suit that has the word "random" in it.<br>
	 * @see AI31.UserInterface#generateSuitCard(int)
	 * @return a number in correspondence with the guidelines set by the method generateSuitCard(int)
	 */
	public int randomCardNum() {
		try {
			if(getSuit().contains("Random")) {
				if(getSuit().contains("not")) {
					if(getSuit().contains("Spade")) {return 10;}
					else if(getSuit().contains("Club")) {return 11;}
					else if(getSuit().contains("Heart")) {return 12;}
					else if(getSuit().contains("Diamond")) {return 13;}
					else {throw new GameErrorException("Please specify a valid suit to not include.", "A9981");}
				}
				else if(getSuit().contains("Spade")) {
					if(getSuit().contains("Club")) {
						if(getSuit().contains("Heart")) {return 13;}
						else if(getSuit().contains("Diamond")) {return 12;}
						else {return 4;}
					}
					else if(getSuit().contains("Heart")) {
						if(getSuit().contains("Diamond")) {return 11;}
						else{return 5;}
					}
					else if(getSuit().contains("Diamond")) {return 6;}
					else {return 0;}
				}
				else if(getSuit().contains("Club")) {
					if(getSuit().contains("Heart")) {
						if(getSuit().contains("Diamond")) {return 10;}
						else {return 7;}
					}
					else if(getSuit().contains("Diamond")) {return 8;}
					else {return 1;}
				}
				else if(getSuit().contains("Heart")) {
					if(getSuit().contains("Diamond")) {return 9;}
					else {return 2;}
				}
				else if(getSuit().contains("Diamond")) {return 3;}
				else {throw new GameErrorException("Please do not specify an invalid suit.", "A3173");}
			}
			throw new GameErrorException("Please choose a valid card.", "A2274");
		}
		catch(GameErrorException e) {UserInterface.displayException(e, 5); System.exit(0); return -1;}
	}
	public static ArrayList<Card31> generateCards(String[] suitChoice, int[] numChoice, char[] faceChoice, Card31[] removedCards){
		int faceLoc = 0;
		ArrayList<Card31> cards = new ArrayList<Card31>();
		for(int i=0; i<numChoice.length; i++) {for(int j=0; j<suitChoice.length; j++) {if(numChoice[i]>10) {break;}cards.add(new Card31(numChoice[i], suitChoice[j]));}}
		//above is only for numbers
		//only for faces
		for(int i=0; i<faceChoice.length; i++) {for(String s : suitChoice) {cards.add(new Card31(faceChoice[i], s));}}
		if(removedCards!=null) {for(Card31 c : removedCards) {cards.remove(c);}}
		return cards;
	}
	/**
	 * PLEASE ENTER ALL suits like a name - UPPERCASE first letter, lowercase other letters
	 * @param suitsToPlayWith - the suits you want to add to generate the random card placeholders
	 * @return the ArrayList of random card placeholders
	 * @throws PlayingCardException
	 */
	public static ArrayList<String> generateRandomStrings(ArrayList<String> suitsToPlayWith){
		try {
			if(suitsToPlayWith.size() >= 5) {throw new PlayingCardException("suitsToPlayWith.size() > 4", "R4491");}
			else if(suitsToPlayWith.size() == 4) {return CustomModeFrame.toStringArrayList(RANDOM_SUIT_STRINGS);}
			else if(suitsToPlayWith.size() == 3) {
				ArrayList<String> possibleCardStrings = new ArrayList<String>();
				for(int i=0; i<suitsToPlayWith.size(); i++) {
					if(suitsToPlayWith.get(i).charAt(suitsToPlayWith.get(i).length()-1) == 's')
					{suitsToPlayWith.set(i, suitsToPlayWith.get(i).substring(0, suitsToPlayWith.get(i).length()-1));}
					String str = suitsToPlayWith.get(i);
					if(str.charAt(str.length()-1) == 's') {str = str.substring(0, str.length()-1);}
					possibleCardStrings.add("Random "+str);
				}
				possibleCardStrings.add("Random "+suitsToPlayWith.get(0)+" or "+suitsToPlayWith.get(1));
				possibleCardStrings.add("Random "+suitsToPlayWith.get(0)+" or "+suitsToPlayWith.get(2));
				possibleCardStrings.add("Random "+suitsToPlayWith.get(1)+" or "+suitsToPlayWith.get(2));
				for(String str : suitsToPlayWith) {
					possibleCardStrings.add("Random not "+str);
				}

				return possibleCardStrings;
			}
			else if(suitsToPlayWith.size() == 2) {
				for(int i=0; i<suitsToPlayWith.size(); i++) {
					if(suitsToPlayWith.get(i).charAt(suitsToPlayWith.get(i).length()-1) == 's')
					{suitsToPlayWith.set(i, suitsToPlayWith.get(i).substring(0, suitsToPlayWith.get(i).length()-1));}
				}
				ArrayList<String> possibleCardStrings = new ArrayList<String>();
				for(String str : suitsToPlayWith) {possibleCardStrings.add("Random "+str);}
				possibleCardStrings.add(2, "Random "+suitsToPlayWith.get(0)+" or "+suitsToPlayWith.get(1));
				for(String str : suitsToPlayWith) {possibleCardStrings.add("Random not "+str);}
				return possibleCardStrings;
			}
			else if(suitsToPlayWith.size() == 1) {throw new PlayingCardException("suitsToPlayWith.size() == 1", "R4082");}
			else {throw new PlayingCardException("suitsToPlayWith.size() == 0", "R4765");}
		}
		catch(PlayingCardException e) {UserInterface.displayException(e, 5); return new ArrayList<String>();}
	}
}


