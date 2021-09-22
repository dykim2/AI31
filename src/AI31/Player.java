package AI31;
import java.util.ArrayList;
import java.util.Collections;
import baselineCustomClasses.GameErrorException;
import baselineCustomClasses.PlayingCard;
import baselineCustomClasses.PlayingCardException;
public abstract class Player {
	/**
	 * The name of the Player.
	 */
	private String name;
	/**
	 * 	The hand of the Player. 
	 */
	private ArrayList<Card31> hand;
	/**
	 * The UserInterface that the Player is playing in.
	 */
	private UserInterface game;
	/**
	 * The number of lives this Player has.
	 */
	private int lives;
	/**
	 * The default number of lives for Players.
	 */
	private static int lifeCount = 1;
	public static final String[] suits = {"hearts", "diamonds", "spades", "clubs"};
	private boolean startingPlayer = false;
	private boolean alive = true;
	//constructors
	public Player(UserInterface u){
		hand = new ArrayList<Card31>();
		game = u;//I want the two user interfaces to change together
		lives = lifeCount;
		//one life: once you die, you're out! hardcore mode
	}
	public Player(String nam, UserInterface u){this(u); name = nam; lives = lifeCount;}
	public Player(String nam, int liv, UserInterface u){this(u); name = nam; lives = liv;}
	public Player(String nam) {hand = new ArrayList<Card31>();}//test constructor
	//abstract methods
	/**
	 * This method defines the system of each Player doing their turn. 
	 * @return an array of size 2 representing what cards are going to be swapped this turn
	 */
	public abstract int[] takeTurn();
	public abstract String toString();
	public String getName(){return name;}
	public ArrayList<Card31> getHand(){return hand;}
	public Card31 getCard(int slot){return hand.get(slot);}
	public MiddleHand getMid(){return getUserInterface().getMiddleHand();}
	public int handSize(){return hand.size();}
	protected UserInterface getUserInterface(){return game;}	
	public int getLives(){return lives;}
	public String getLifeString(){if(lives!=1){return name+": I have "+lives+" lives.";}else{return name+": I have 1 life.";}}
	public boolean isStarting(){return startingPlayer;}
	public boolean isAlive() {return lives>0;}
	public void addCard(Card31 card){if(hand.size()>=3) {return;}hand.add(card);}
	public void addCardAtSlot(Card31 card, int slot){hand.add(slot, card);}
	public void addHand(ArrayList<Card31> ha){hand = null; hand = new ArrayList<Card31>(ha);}
	public void removeCard(Card31 card){hand.remove(card);}
	public void clearHand(){hand.clear();}
	public void setName(String nam){name = nam;}
	public static void setLifeCounter(int newLives) {lifeCount = newLives;}//only use before setting the game up
	public void removeLife(){lives--;}
	public boolean setStarting(boolean start) {
		try {
			if(!isAlive()) {throw new GameErrorException("Please do not attempt to set the staring status of a dead player.");}
			startingPlayer = start;
			return true;
		}
		catch(GameErrorException e) {UserInterface.displayException(e, 3); return false;}
	}
	public void die() {alive = false; lives = 0;}
	private void updateScore(){}//updates the score - a meme function, as it is not used
	class CardSum implements Comparable<CardSum>{
		private String suitChoice;
		private int suitSum;
		CardSum(int n, String s){
			suitChoice = new String(s);
			suitSum = n;
		}
		int getSuitSum(){return suitSum;}
		String getSuitChoice(){return suitChoice;}
		void setSuitSum(int sum){suitSum = sum;}
		void setSuitChoice(String choice){suitChoice = new String(choice);}
		@Override
		public int compareTo(CardSum arg0){return this.suitSum-arg0.suitSum;}
	}
	public int matchingSuit(){
		//number of cards in your hand that have the same suit
		for(int i=0; i<hand.size()-1; i++){
			if(hand.get(i).getSuit().equalsIgnoreCase(hand.get(i+1).getSuit())){
				if(i == 0 && hand.get(1).getSuit().equalsIgnoreCase(hand.get(2).getSuit())){return 3;}
				else{return 2;}
			}
		}
		if(hand.get(0).getSuit().equalsIgnoreCase(hand.get(2).getSuit())){return 2;}
		return 0;
	}
	public String highestSuit() throws GameErrorException{
		int highSum = -50;
		String highSuit = "";
		for(String s : suits){
			int suitSum = 0;
			for(int i=0; i<hand.size(); i++){if(hand.get(i).getSuit().equalsIgnoreCase(s)) {suitSum+=hand.get(i).getNumber();}}
			if(suitSum>highSum){
				highSum = suitSum;
				highSuit = s;
			}
		}
		return highSuit;
	}//finds the suit with the highest point value
	public int highestValue() throws GameErrorException{
		int highSum = -50;
		for(String s : suits){
			int suitSum = 0;
			for(int i=0; i<hand.size(); i++){if(hand.get(i).getSuit().equalsIgnoreCase(s)){suitSum+=hand.get(i).getNumber();}}
			if(suitSum>highSum){highSum = suitSum;}
		}
		return highSum;
	}
	/**
	 * This method, only used when a player
	 * @return
	 * @throws GameErrorException - if the preconditions aren't met (there must be three of a number and not one of the three of a number can be an ace).
	 * If even one card's number is an ace, all three will for the first condition (3 identical numbers) to be met.
	 */
	public double tripleNumber() throws GameErrorException {
		try {
			if(matchingNumber()!=3) {throw new GameErrorException("Please do not attempt to use this method without 3 of a number.");} 
			else if(matchingNumber() == 3 && getCard(1).getFace() == 'A') {throw new GameErrorException("Please do not attempt to use this method with 3 aces.");}
			//will be worth 1 king and 2 queens
			if(!PlayingCard.getValueConditions(0)) {return 3*PlayingCard.getJackValue();}
			else {return (PlayingCard.getJackValue()+1)*2+(PlayingCard.getJackValue()+2);}
		}
		catch(PlayingCardException e) {UserInterface.displayException(e, 3); return 0;}
	}
	public int matchingNumber(){
		//number of cards in your hand that have the same rank
		for(int i=0; i<hand.size()-1; i++){
			if(hand.get(i).getSpecialNumber() == hand.get(i+1).getSpecialNumber()){
				if(i == 0 && hand.get(1).getSpecialNumber() == hand.get(2).getSpecialNumber()){return 3;}
				else{return 2;}
			}
		}
		if(hand.get(0).getSpecialNumber() == hand.get(2).getSpecialNumber()){return 2;}
		return 0;
	}
	public int cardsPerSuit(String s){
		int ct = 0;
		for(int i=0; i<hand.size(); i++){if(getCard(i).getSuit().equalsIgnoreCase(s)){ct++;}}
		return ct;
	}
	/**
	 * Determines and returns the sum of the player's hand.
	 * @return the sum of the player's hand
	 * @throws GameErrorException @see {@link AI31.Player#highestValue()}
	 */
	public double sumOfHand() throws GameErrorException{
		try {
			if(game!=null && game.isCustom()){return customSumOfHand();}
			if(matchingSuit()==3){if(getCard(0).getFace() == 'A' && getCard(1).getFace() == 'K' && getCard(2).getFace() == 'Q'){return 31.5;}}//hidden gem that you only scather by reading the code
			if(matchingNumber() == 3 && getCard(1).getFace() != 'A'){return 30.5;}
			else if(matchingNumber() ==3 && getCard(1).getFace() == 'A'){return 32;}
			else if(matchingSuit()==3 && worstCardInSuit(suitRank()[0]).getNumber() == 6 && bestCardInSuit(suitRank()[0]).getNumber()==8) {return 21.5;}
			return highestValue();
		} catch(PlayingCardException e) {UserInterface.displayException(e, 3); return 0;}

	}
	public double customSumOfHand() throws GameErrorException{
		try {
			if(matchingSuit()==3) {
				if(worstCardInSuit(suitRank()[0]).getNumber() == 6 && bestCardInSuit(suitRank()[0]).getNumber()==8) {return 21.5;}
				else if (getCard(0).getFace() == 'A' && getCard(1).getFace() == 'K' && getCard(2).getFace() == 'Q' && PlayingCard.getValueConditions(1)){
					if(!PlayingCard.getValueConditions(0)) {return PlayingCard.getAceValue()+2*PlayingCard.getJackValue()+0.5;}
					else {return PlayingCard.getAceValue()+2*PlayingCard.getJackValue()+3.5;}
				}//exactly 0.5 larger than a normal ace-two faces hand, all of the same suit
			}
			if(matchingNumber()==3) {
				if(getCard(1).getFace()!= 'A') {return tripleNumber()+0.5;}
				else {
					//case 1: faceCardsIncrement is off
					if(!PlayingCard.getValueConditions(0)) {return PlayingCard.getAceValue()+2*PlayingCard.getJackValue()+1;}
					else {return PlayingCard.getAceValue()+2*PlayingCard.getJackValue()+4;}

				}//
			}
			//the 21.5 has no significance here due to the wonky ace and jack values you can custom set
			return highestValue();
		}
		catch(Exception e) {UserInterface.displayException(e, 3);}
		return 0;
	}
	public int findCard(Card31 c){
		for(int i=0; i<hand.size(); i++){if(getCard(i).equals(c)){return i;}}
		return -1;
	}
	public String[] suitRank(){//rank of suits in terms of sums
		int[] sumsOfSuits = {0,0,0,0};
		String[] rank = new String[4];
		for(int f=0; f<suits.length; f++){
			for(int i=0; i<hand.size(); i++){if(hand.get(i).getSuit().equalsIgnoreCase(suits[f])){sumsOfSuits[f] += hand.get(i).getNumber();}}}

		ArrayList<CardSum> cardSums = new ArrayList<CardSum>(4);
		for(int i=0; i<sumsOfSuits.length; i++){cardSums.add(new CardSum(sumsOfSuits[i],suits[i]));}
		Collections.sort(cardSums);
		Collections.reverse(cardSums);
		for(int i=0; i<4; i++){rank[i] = new String(cardSums.get(i).getSuitChoice());}
		return rank;
	}
	public Card31 bestCardInSuit(String s) throws PlayingCardException{
		Card31 highCard = new Card31(2, s);
		boolean check = false;
		for(int i=0; i<getHand().size(); i++){
			if(getCard(i).getSuit().equalsIgnoreCase(s) && getCard(i).getNumber() > highCard.getNumber()){check = true; 
			highCard = new Card31(getCard(i));}
		}
		if(!check)
			return null;
		return highCard;
	}
	public Card31 worstCardInSuit(String s) throws PlayingCardException{
		Card31 lowCard = new Card31(105, s);
		boolean check = false;
		for(int i=0; i<getHand().size(); i++){
			if(getCard(i).getSuit().equalsIgnoreCase(s) && getCard(i).getNumber() < lowCard.getNumber()){check = true; 
			lowCard = new Card31(getCard(i));}
		}
		if(!check){return null;}

		return lowCard;
	}
	public Card31 smallestCard() throws GameErrorException{
		//by number
		try{
			int low = 100;
			int loc = -1;
			for(int i=0; i<hand.size(); i++){if(getCard(0).getNumber() < low){low = getCard(i).getNumber(); loc = i;}}
			return getCard(loc);
		}
		catch(IndexOutOfBoundsException e){
			UserInterface.displayException(e, 3);
			throw new GameErrorException("Something went wrong trying to find the lowest card - seems like there is no lowest card");
		}
	}
	public Card31 unidenticalSuitCard() throws GameErrorException{//only to use if matchingSuit() == 2
		if(matchingSuit() == 2){//otherwise this won't work
			try{
				String high = highestSuit();
				for(int i=0; i<hand.size(); i++){
					if(!hand.get(i).getSuit().equalsIgnoreCase(high)){
						return hand.get(i);
					}
				}
				throw new GameErrorException("A card must be found.");
			}
			catch(GameErrorException e){
				UserInterface.displayException(e, 3);
				throw new NullPointerException("Something is missing with the unidentical suit card");
			}
		}
		else{
			throw new GameErrorException("unidenticalSuitCard() only works when the max number of cards in a suit is 2 (out of 3).");
		}
	}
	public Card31 unidenticalNumberCard() throws GameErrorException{
		if(matchingNumber()==2){
			if(getCard(0).getSpecialNumber() == getCard(1).getSpecialNumber()){return getCard(2);}
			else if(getCard(0).getSpecialNumber() == getCard(2).getSpecialNumber()){return getCard(1);}
			else{return getCard(0);}
		}
		else{throw new GameErrorException("unidenticalNumberCard() only works when the max # of common number cards is 2 (out of 3).");}
	}
	public Card31 worstCardLimiter(int num, char f, String suit1){
		if(num==0 && suit1.equalsIgnoreCase("") && f==' '){UserInterface.displayError("Why use this function if you're not going to enter values?");return null;}
		//this finds the worst card (in terms of number) that does not match the suit or number
		int worstCardValue = 100;
		int index = -1;
		if(num==0 && f==' ' && !suit1.equalsIgnoreCase("") && matchingSuit()==3) {return null;}
		for(int i=0; i<hand.size(); i++){
			if(num!=10){
				if(getCard(i).getNumber()!=num && !getCard(i).getSuit().equalsIgnoreCase(suit1)){if(getCard(i).getNumber() < worstCardValue)
				{index = i; worstCardValue = getCard(i).getNumber();}}
			}
			else{
				if(getCard(i).getFace()!=f && !getCard(i).getSuit().equalsIgnoreCase(suit1)){if(getCard(i).getNumber() < worstCardValue)
				{index = i; worstCardValue = getCard(i).getNumber();}}
			}
		}
		try{return getCard(index);}
		catch(IndexOutOfBoundsException e){UserInterface.displayException(e, 3); return null;}
	}
	public int faceCount(char f){
		int count = 0;
		for(int i=0; i<hand.size(); i++){if(getCard(i).getFace()==f){count++;}}
		return count;
	}
	/*
	public int[] bubbleSort(int[] array) {
		boolean swapped = true;
		int j = 0;
		int tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.length - j; i++) {
				if (array[i] > array[i + 1]) {
					tmp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = tmp;
					swapped = true;
				}
			}
		}
		return array;
	}
	 */
	public int suitSum(String s){
		int sum=0;
		for(int i=0; i<3; i++){if(getCard(i).getSuit().equalsIgnoreCase(s)){sum+=getCard(i).getNumber();}}
		return sum;
	}
	public boolean optimalFormation(){//two of a suit, two of a number
		if(matchingNumber()==2 && matchingSuit()==2){return true;}
		else{return false;}
	}
	public int findTens(){//actually this finds the total number of ten-number cards in the hand
		int ct=0;
		for(int i=0; i<getHand().size(); i++){if(getCard(i).getNumber() == 10){ct++;}}
		return ct;
	}
	public boolean[] twoTensLocations(){
		if(findTens()!=2){return null;}
		else{
			boolean[] list = new boolean[getHand().size()];
			for(int i=0; i<list.length; i++){
				if(getCard(i).getNumber() == 10){list[i] = true;}
			}
			return list;
		}
	}
	public double maxPossibleSum(MiddleHand m) throws GameErrorException, PlayingCardException{
		String[] rank = suitRank();
		ArrayList<String> ranks = new ArrayList<String>();
		for(String r : rank){ranks.add(r);}
		double maxSum = suitSum(rank[0]);
		double secondMaxSum = suitSum(rank[1]);
		double thirdMaxSum = suitSum(rank[2]);
		ArrayList<Double> oldSums = new ArrayList<Double>();
		oldSums.add(maxSum);
		oldSums.add(secondMaxSum);
		oldSums.add(thirdMaxSum);
		if(matchingNumber()==3){
			if(getCard(0).getNumber()==11){return 32;}
			else{return 30.5;}
		}
		if(matchingSuit()==3){//3 of a kind
			if(m.bestCardInSuit(rank[0])!=null && m.bestCardInSuit(rank[0]).getNumber()>worstCardInSuit(rank[0]).getNumber()){
				int high = m.bestCardInSuit(rank[0]).getNumber();
				int low = worstCardInSuit(rank[0]).getNumber();
				maxSum+=(high-low);
				return maxSum;
			}
			else{return maxSum;}
		}
		if(matchingNumber()==2){//2 of a number, testing if the third of the number exists in mid
			if(m.contains(commonNumber(), commonFace())>=0){
				if(commonNumber()==11){return 32;}
				else{return 30.5;}
			}
		}
		if(m.bestCardInSuit(rank[0])!=null){maxSum+=m.bestCardInSuit(rank[0]).getNumber();}
		if(m.bestCardInSuit(rank[1])!=null){secondMaxSum+=m.bestCardInSuit(rank[1]).getNumber();}
		if(m.bestCardInSuit(rank[2])!=null){thirdMaxSum+=m.bestCardInSuit(rank[2]).getNumber();}
		if(maxSum == secondMaxSum && secondMaxSum == thirdMaxSum){return maxSum;}
		ArrayList<Double> sums = new ArrayList<Double>();
		sums.add(new Double(maxSum));
		sums.add(new Double(secondMaxSum));
		sums.add(new Double(thirdMaxSum));
		Collections.sort(sums);
		Collections.reverse(sums);
		return sums.get(0);
	}
	public double maxPossibleCustomSum(MiddleHand m) throws GameErrorException, PlayingCardException{
		String[] rank = suitRank();
		ArrayList<String> ranks = new ArrayList<String>();
		for(String r : rank){ranks.add(r);}
		double maxSum = suitSum(rank[0]);
		double secondMaxSum = suitSum(rank[1]);
		double thirdMaxSum = suitSum(rank[2]);
		ArrayList<Double> oldSums = new ArrayList<Double>();
		oldSums.add(maxSum);
		oldSums.add(secondMaxSum);
		oldSums.add(thirdMaxSum);
		if(matchingNumber()==3){
			if(getCard(0).getNumber()==11){return PlayingCard.getAceValue()+2*PlayingCard.getJackValue()+3.5;}
			else{return 30.5;}
		}
		if(matchingSuit()==3){//3 of a kind
			if(m.bestCardInSuit(rank[0])!=null && m.bestCardInSuit(rank[0]).getNumber()>worstCardInSuit(rank[0]).getNumber()){
				int high = m.bestCardInSuit(rank[0]).getNumber();
				int low = worstCardInSuit(rank[0]).getNumber();
				maxSum+=(high-low);
				return maxSum;
			}
			else{return maxSum;}
		}
		if(matchingNumber()==2){//2 of a number, testing if the third of the number exists in mid
			if(m.contains(commonNumber(), commonFace())>=0){
				if(commonNumber()==11){return 32;}
				else{return 30.5;}
			}
		}
		if(m.bestCardInSuit(rank[0])!=null){maxSum+=m.bestCardInSuit(rank[0]).getNumber();}
		if(m.bestCardInSuit(rank[1])!=null){secondMaxSum+=m.bestCardInSuit(rank[1]).getNumber();}
		if(m.bestCardInSuit(rank[2])!=null){thirdMaxSum+=m.bestCardInSuit(rank[2]).getNumber();}
		if(maxSum == secondMaxSum && secondMaxSum == thirdMaxSum){return maxSum;}
		ArrayList<Double> sums = new ArrayList<Double>();
		sums.add(new Double(maxSum));
		sums.add(new Double(secondMaxSum));
		sums.add(new Double(thirdMaxSum));
		Collections.sort(sums);
		Collections.reverse(sums);
		return sums.get(0);
	}
	public double maxPossibleSum(String suit, MiddleHand m) throws GameErrorException, PlayingCardException{
		Card31 c = m.bestCardInSuit(suit);
		Card31 h = worstCardInSuit(suit);
		//c is not null
		if(c!=null && h!=null){
			int sum = suitSum(suit);
			if(matchingSuit()==3){
				int lowInHand = h.getNumber();
				int highInMid = c.getNumber();
				//System.out.println("(low, high): ("+lowInHand+", "+highInMid+")");
				if(lowInHand>=highInMid){return sum;}
				else{return sum+(highInMid-lowInHand);}
			}
			return sum+c.getNumber();
		}
		else{return suitSum(suit);}
	}
	public int commonNumber() throws GameErrorException{return getCard(commonSpecialNumberIndex()).getNumber();}
	public int commonSpecialNumber() throws GameErrorException{
		return getCard(commonSpecialNumberIndex()).getSpecialNumber();}
	//should only be used with 10s or lls, again only works with two of a kinds
	public char commonFace() throws GameErrorException{return getCard(commonSpecialNumberIndex()).getFace();}
	public int commonSpecialNumberIndex() throws GameErrorException{
		if(getCard(0).getSpecialNumber() == getCard(1).getSpecialNumber()){return 0;}
		else if(getCard(0).getSpecialNumber() == getCard(2).getSpecialNumber()){return 2;}
		else if(getCard(1).getSpecialNumber() == getCard(2).getSpecialNumber()){return 1;}
		else{throw new GameErrorException("Something went wrong finding the common special number index.");}
	}
	//goal of function: move two cards around
	public void moveCardsAround(int loc0, int loc1) {
		ArrayList<Card31> copy = new ArrayList<Card31>(hand);
		hand.clear();
		for(int i=0; i<copy.size(); i++) {
			if(i==loc0) {hand.add(new Card31(copy.get(loc1)));}
			else if(i==loc1) {hand.add(new Card31(copy.get(loc0)));}
			else {hand.add(copy.get(i));}
		}
	}

}