package baselineCustomClasses;
import java.awt.*;//for images and such
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import AI31.AI31Constants;
import AI31.UserInterface;
//this class is a base to all playing card games and all methods that throw an exception throw a PlayingCardException
public class PlayingCard {//this assumes the running of one game at a time, not two simultaneously (one after the other is fine)
	/**
	 * 
	 */
	private int number;
	/**
	 * 
	 */
	private char face;
	/**
	 * 
	 */
	private String suit;
	/**
	 * A specific number used to make sure the images work as intended. It is also used to describe the card in a numerical way.
	 */
	private int specialNumber = 0;
	/**
	 * The face card characters.
	 */
	public static final char[] faceCards = {'J','Q','K','A'};
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
		catch(PlayingCardException e) {UserInterface.displayException(e, 4);}
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
	public void setNumber(int n){number = n;}
	public void setFace(char f){face = f;}
	public void setSuit(String s){suit = new String(s);}
	public void setImage(Image i) {pic = i;}
	public void setJackValue(int val) {previousJackValue = jackValue; jackValue = val;}
	public void setAceValue(int val) {previousAceValue = aceValue; aceValue = val;}
	/**
	 * Locates and adds the image that corresponds to this card.
	 * @param specialNumber - the number used to represent the card number/face
	 * @param suit - the suit of the card
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
		add+=suit.substring(0, 1).toUpperCase();
		try {
			Image image = ImageIO.read(getClass().getResource("/Pictures/"+add+".png"));
			Image newImg = image.getScaledInstance(-2, 100, Image.SCALE_SMOOTH);
			setImage(newImg);
		}
		catch(IOException e) {UserInterface.displayException(e, 4);}
	}
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
		catch(IOException e) {UserInterface.displayException(e, 4); return null;}
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
	public boolean compareByFace(PlayingCard c){
		//return false when the faces are not the same
		//return true when the faces are the same
		if(this.face == c.face || this == c){return true;}
		else{return false;}
	}
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
	public static final PlayingCard[] buildAllCards() {
		PlayingCard[] allCards = new PlayingCard[52];
		int index = 0;
		for(int i=2; i<11; i++) {
			for(int j=0; j<4; j++) {
				allCards[index] = new PlayingCard(i, AI31Constants.SUITS[j]);
				index++;
			}
		}
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				allCards[index] = new PlayingCard(AI31Constants.FACE_CARDS[i], AI31Constants.SUITS[j]);
				index++;
			}
		}
		return allCards;
	}
}
