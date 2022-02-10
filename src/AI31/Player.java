package AI31;
import java.util.ArrayList;
import java.util.Collections;
import baselineCustomClasses.GameException;
import baselineCustomClasses.PlayingCard;
import baselineCustomClasses.PlayingCardException;
/**
 * The game's players, including the AIs, the humans, and the middle hand. More can come in the future.
 * @author dong-yonkim
 *
 */
public abstract class Player implements AI31Constants {
	/**
	 * The name of the Player.
	 */
	private String name;
	/**
	 * The hand of the Player.
	 */
	private ArrayList<Card31> hand;
	/**
	 * The UserInterface that the Player is playing in. Might make it static
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
	/**
	 * The player that starts the game.
	 */
	private boolean startingPlayer = false;
	/**
	 * Whether this player is alive or not.
	 * 
	 * @deprecated Do not rely on this field.
	 */
	private boolean alive = true;
	// constructors
	/**
	 * Creates a new player in the given user interface.
	 * 
	 * @param u the user interface to place the player in
	 */
	public Player(UserInterface u) {
		hand = new ArrayList<Card31>();
		game = u;// I want the two user interfaces to change together
		lives = lifeCount;
		// one life: once you die, you're out! hardcore mode
	}
	/**
	 * Creates a new Player with the given name located in the user interface.
	 * @param nam the name of the player
	 * @param u the user interface the player belongs to
	 */
	public Player(String nam, UserInterface u) { this(u); name = nam; lives = lifeCount; }
	/**
	 * Creates a new Player with the given name and lives located in the given user interface.
	 * @param nam the name of this player
	 * @param liv the number of lives this player will start with
	 * @param u the user interface the player belongs to
	 */
	public Player(String nam, int liv, UserInterface u) { this(nam, u); lives = liv; }
	/**
	 * An old constructor used to check if players work on their own without the interface.
	 * 
	 * @param nam the name of the player
	 * @deprecated This constructor is for testing purposes only and should not be used.
	 */
	public Player(String nam) { hand = new ArrayList<Card31>(); }// test constructor
	// abstract methods
	/**
	 * This method defines the system of each Player doing their turn.
	 * 
	 * @return an array of size 2 representing what cards are going to be swapped this turn
	 */
	public abstract int[] takeTurn();
	@Override
	public abstract String toString();
	/**
	 * Get's the player's name.
	 * 
	 * @return the name of the player
	 */
	public String getName() { return name; }
	/**
	 * Gets the player's hand.
	 * 
	 * @return the player's hand array list
	 */
	public ArrayList<Card31> getHand() { return hand; }
	/**
	 * Gets the card in a specific slot.
	 * 
	 * @param slot the slot of the card wanted
	 * @return the card object in the given slot
	 */
	public Card31 getCard(int slot) { return hand.get(slot); }
	/**
	 * Requests the middle hand from the game.
	 * 
	 * @return the middle hand object
	 */
	public MiddleHand getMid() { return getUserInterface().getMiddleHand(); }
	/**
	 * Checks how big this player's hand is. This should always be lower than 3.
	 * @return the amount of cards this player has
	 */
	public int handSize() { return hand.size(); }
	/**
	 * Gets the user interface the player is running in.
	 * 
	 * @return the UserInterface this player belongs to
	 */
	protected UserInterface getUserInterface() { return game; }
	/**
	 * Checks for how many lives this Player has.
	 * @return the number of lives the player has
	 */
	public int getLives() { return lives; }
	/**
	 * How many lives the player has as a string.
	 * 
	 * @return a String describing the life count of each player.
	 */
	public String getLifeString() {
		if (lives != 1) {
			return name + ": I have " + lives + " lives.";
		} else {
			return name + ": I have 1 life.";
		}
	}
	/**
	 * Is this player the starting player?
	 * 
	 * @return
	 */
	public boolean isStarting() { return startingPlayer; }
	/**
	 * Is the player alive?
	 * 
	 * @return true of they have more than zero lives, false otherwise
	 */
	public boolean isAlive() { return lives > 0; }
	/**
	 * Adds the given card to the card array.
	 * 
	 * @param card
	 * @throws GameException if an attempt to add a fourth card is made
	 */
	public void addCard(Card31 card) throws GameException {
		if (hand.size() >= 3) { throw new GameException("Do not add a card to a full hand.", "J1103"); }
		hand.add(card);
	}
	/**
	 * Adds the given card in the specific slot, pushing other cards back.
	 * 
	 * @param card the card to add
	 * @param slot the location to add the card (the card will be at this location)
	 */
	public void addCardAtSlot(Card31 card, int slot) { hand.add(slot, card); }
	/**
	 * Replaces the current hand of this Player with the given hand, completely overwriting the reference in the heap.
	 * 
	 * @param ha the hand to replace the current hand with
	 */
	public void addHand(ArrayList<Card31> ha) { hand = null; hand = new ArrayList<Card31>(ha); }
	/**
	 * Removes the given card from the cards array.
	 * 
	 * @param card the card to remove from the cards array
	 */
	public void removeCard(Card31 card) { hand.remove(card); }
	/**
	 * Clears the hand, removing all of the cards in it.
	 */
	public void clearHand() { hand.clear(); }
	/**
	 * Changes the name of this player.
	 * 
	 * @param nam the new name of the player
	 */
	public void setName(String nam) { name = nam; }
	/**
	 * Sets the default amount of lives for every Player.
	 * 
	 * @param newLives the new amount of lives each player has
	 */
	public static void setLifeCounter(int newLives) { lifeCount = newLives; }// only use before setting the game up
	/**
	 * Removes one life from this player.
	 */
	public void removeLife() { lives--; }
	/**
	 * Change whether this is the starting player or not.
	 * 
	 * @param start true if this player is the new starting player, false otherwise
	 * @return true if the player was successfully set as the starting player, false otherwise
	 */
	public boolean setStarting(boolean start) {
		try {
			if (!isAlive()) {
				throw new GameException("Please do not attempt to set the staring status of a dead player.", "Y9546");
			}
			startingPlayer = start;
			return true;
		} catch (GameException e) {
			UserInterface.displayException(e, 3);
			return false;
		}
	}
	/**
	 * @deprecated As of v4.0, this method is no longer in use due to the alive field not being used.
	 */
	public void die() { alive = false; lives = 0; }
	/**
	 * A private subclass that stores the sum of the player's cards.
	 * 
	 * @author dong-yonkim
	 *
	 */
	class CardSum implements Comparable<CardSum> {
		/**
		 * The suit to store the score for.
		 */
		private String suitChoice;
		/**
		 * The sum in the given suit.
		 */
		private int suitSum;
		/**
		 * Creates a new sum of cards for this player.
		 * 
		 * @param n the current sum of the player
		 * @param s the suit of choice to calculate score
		 */
		CardSum(int n, String s) {
			suitChoice = new String(s);
			suitSum = n;
		}
		/**
		 * Gets the sum of the suit.
		 * 
		 * @return the total amount of points the suit has
		 */
		protected int getSuitSum() { return suitSum; }
		/**
		 * Gets the choice of suit.
		 * 
		 * @return the suit chosen to score
		 */
		protected String getSuitChoice() { return suitChoice; }
		/**
		 * Sets the sum of the suit.
		 * 
		 * @param sum the new sum of the given suit
		 */
		protected void setSuitSum(int sum) { suitSum = sum; }
		/**
		 * 
		 * @param choice
		 */
		protected void setSuitChoice(String choice) { suitChoice = new String(choice); }
		/**
		 * 
		 */
		@Override
		public int compareTo(CardSum arg0) { return this.suitSum - arg0.suitSum; }
	}
	/**
	 * The number of cards that match in the most common suit of this player's cards.
	 * 
	 * @return the number of cards in the suit that the player has the most cards in
	 */
	public int matchingSuit() {
		// number of cards in your hand that have the same suit
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getSuit().equalsIgnoreCase(hand.get(i + 1).getSuit())) {
				if (i == 0 && hand.get(1).getSuit().equalsIgnoreCase(hand.get(2).getSuit())) {
					return 3;
				} else {
					return 2;
				}
			}
		}
		if (hand.get(0).getSuit().equalsIgnoreCase(hand.get(2).getSuit())) { return 2; }
		return 0;
	}
	/**
	 * Finds the suit with the highest sum of hand.
	 * @return the suit worth the most sum wise
	 */
	public String highestSuit() {
		int highSum = -50;
		String highSuit = "";
		for (String s : SUITS) {
			int suitSum = 0;
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getSuit().equalsIgnoreCase(s)) { suitSum += hand.get(i).getNumber(); }
			}
			if (suitSum > highSum) {
				highSum = suitSum;
				highSuit = s;
			}
		}
		return highSuit;
	}// finds the suit with the highest point value
	/**
	 * Finds the highest possible score of the hand per suit.
	 * 
	 * @return the highest possible points in one suit
	 */
	public int highestValue() {
		int highSum = -50;
		for (String s : SUITS) {
			int suitSum = 0;
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getSuit().equalsIgnoreCase(s)) { suitSum += hand.get(i).getNumber(); }
			}
			if (suitSum > highSum) { highSum = suitSum; }
		}
		return highSum;
	}
	/**
	 * This method, only used when a player has three of the same number, calculates the sum.
	 * 
	 * @return the score provided by the triple numbers
	 * @throws GameException - if the preconditions aren't met (there must be three of a number and not one of the three of a
	 *             number can be an ace). If even one card's number is an ace, all three will for the first condition (3
	 *             identical numbers) to be met.
	 */
	public double tripleNumber() throws GameException {
		try {
			if (matchingNumber() != 3) {
				throw new GameException("Please do not attempt to use this method without 3 of a number.", "E1206");
			} else if (matchingNumber() == 3 && getCard(1).getFace() == 'A') {
				throw new GameException("Please do not attempt to use this method with 3 aces.", "E1985");
			}
			// will be worth 1 king and 2 queens
			if (!PlayingCard.getValueConditions(0)) {
				return 3 * PlayingCard.getJackValue();
			} else {
				return (PlayingCard.getJackValue() + 1) * 2 + (PlayingCard.getJackValue() + 2);
			}
		} catch (PlayingCardException e) {
			UserInterface.displayException(e, 3);
			return 0;
		}
	}
	/**
	 * Checks how many cards in the player's hand have the same number.
	 * @return the number of cards with the same number value
	 */
	public int matchingNumber() {
		// number of cards in your hand that have the same rank
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).getSpecialNumber() == hand.get(i + 1).getSpecialNumber()) {
				if (i == 0 && hand.get(1).getSpecialNumber() == hand.get(2).getSpecialNumber()) {
					return 3;
				} else {
					return 2;
				}
			}
		}
		if (hand.get(0).getSpecialNumber() == hand.get(2).getSpecialNumber()) { return 2; }
		return 0;
	}
	/**
	 * Checks how many cards in the player's hand have the given suit.
	 * @param s the suit to check
	 * @return the number of cards with the given suit
	 */
	public int cardsPerSuit(String s) {
		int ct = 0;
		for (int i = 0; i < hand.size(); i++) { if (getCard(i).getSuit().equalsIgnoreCase(s)) { ct++; } }
		return ct;
	}
	/**
	 * Determines and returns the sum of the player's hand.
	 * 
	 * @return the sum of the player's hand
	 * @throws GameException
	 * @see {@link AI31.Player#highestValue()}
	 */
	public double sumOfHand() {
		// try {
		if (game != null && game.isCustom()) { return customSumOfHand(); }
		if (matchingSuit() == 3) {
			if (getCard(0).getFace() == 'A' && getCard(1).getFace() == 'K' && getCard(2).getFace() == 'Q') { return 31.5; }
		}
		// hidden gem that you only see by reading the code
		if (matchingNumber() == 3 && getCard(1).getFace() != 'A') {
			return 30.5;
		} else if (matchingNumber() == 3 && getCard(1).getFace() == 'A') {
			return 32;
		} else if (matchingSuit() == 3 && worstCardInSuit(suitRank()[0]).getNumber() == 6
				&& bestCardInSuit(suitRank()[0]).getNumber() == 8) {
			return 21.5;
		}
		return highestValue();
		// } catch(GameErrorException e) {UserInterface.displayException(e, 3); return 0;}

	}
	/**
	 * Calculates the sum of the hand based on the current custom mode rules.
	 * 
	 * @return the
	 */
	public double customSumOfHand() {
		try {
			if (matchingSuit() == 3) {
				if (worstCardInSuit(suitRank()[0]).getNumber() == 6 && bestCardInSuit(suitRank()[0]).getNumber() == 8) {
					return 21.5;
				} else if (getCard(0).getFace() == 'A' && getCard(1).getFace() == 'K' && getCard(2).getFace() == 'Q'
						&& PlayingCard.getValueConditions(1)) {
					if (!PlayingCard.getValueConditions(0)) {
						return PlayingCard.getAceValue() + 2 * PlayingCard.getJackValue() + 0.5;
					} else {
						return PlayingCard.getAceValue() + 2 * PlayingCard.getJackValue() + 3.5;
					}
				} // exactly 0.5 larger than a normal ace-two faces hand, all of the same suit
			}
			if (matchingNumber() == 3) {
				if (getCard(1).getFace() != 'A') {
					return tripleNumber() + 0.5;
				} else {
					// case 1: faceCardsIncrement is off
					if (!PlayingCard.getValueConditions(0)) {
						return PlayingCard.getAceValue() + 2 * PlayingCard.getJackValue() + 1;
					} else {
						return PlayingCard.getAceValue() + 2 * PlayingCard.getJackValue() + 4;
					}

				} //
			}
			// the 21.5 has no significance here due to the wonky ace and jack values you can custom set
			return highestValue();
		} catch (Exception e) {
			UserInterface.displayException(e, 3);
		}
		return 0;
	}
	/**
	 * Checks the cards array for a specific card and returns the index of the card in the array
	 * 
	 * @param c the card to compare with
	 * @return an integer that
	 */
	public int findCard(Card31 c) {
		for (int i = 0; i < hand.size(); i++) { if (getCard(i).equals(c)) { return i; } }
		return -1;
	}
	/**
	 * Finds the best suit sum wise.
	 * 
	 * @return the order of suits, based on their sums
	 */
	public String[] suitRank() {// rank of suits in terms of sums
		int[] sumsOfSuits = {0, 0, 0, 0};
		String[] rank = new String[4];
		for (int f = 0; f < SUITS.length; f++) {
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getSuit().equalsIgnoreCase(SUITS[f])) { sumsOfSuits[f] += hand.get(i).getNumber(); }
			}
		}

		ArrayList<CardSum> cardSums = new ArrayList<CardSum>(4);
		for (int i = 0; i < sumsOfSuits.length; i++) { cardSums.add(new CardSum(sumsOfSuits[i], SUITS[i])); }
		Collections.sort(cardSums);
		Collections.reverse(cardSums);
		for (int i = 0; i < 4; i++) { rank[i] = new String(cardSums.get(i).getSuitChoice()); }
		return rank;
	}
	/**
	 * Finds the best card of the given suit by number.
	 * 
	 * @param s the suit to search for
	 * @return the best card number wise in the given suit
	 */
	public Card31 bestCardInSuit(String s) {
		Card31 highCard = new Card31(2, s);
		boolean check = false;
		for (int i = 0; i < getHand().size(); i++) {
			if (getCard(i).getSuit().equalsIgnoreCase(s) && getCard(i).getNumber() > highCard.getNumber()) {
				check = true;
				highCard = new Card31(getCard(i));
			}
		}
		if (!check)
			return null;
		return highCard;
	}
	/**
	 * Finds the worst card of the given suit, number wise.
	 * 
	 * @param s the suit to check for
	 * @return the worst card number wise in the suit
	 */
	public Card31 worstCardInSuit(String s) {
		Card31 lowCard = new Card31(105, s);
		boolean check = false;
		for (int i = 0; i < getHand().size(); i++) {
			if (getCard(i).getSuit().equalsIgnoreCase(s) && getCard(i).getNumber() < lowCard.getNumber()) {
				check = true;
				lowCard = new Card31(getCard(i));
			}
		}
		if (!check) { return null; }

		return lowCard;
	}
	/**
	 * Checks for the card with the lowest number value, ignoring suits.
	 * 
	 * @return the card that has the lowest number
	 * @throws GameException if there is no lowest card
	 */
	public Card31 smallestCard() throws GameException {
		// by number
		try {
			int low = 100;
			int loc = -1;
			for (int i = 0; i < hand.size(); i++) {
				if (getCard(0).getNumber() < low) { low = getCard(i).getNumber(); loc = i; }
			}
			return getCard(loc);
		} catch (IndexOutOfBoundsException e) { // theoretically this will never run, since the index will never be out of
												// bounds unless something is very wrong
			UserInterface.displayException(e, 3);
			throw new GameException(
					"Something went wrong trying to find the lowest card - seems like there is no lowest card", "Y5340");
		}
	}
	/**
	 * Given two cards in this player's hand have the same suit, but not all three, finds the card that does not have the
	 * same suit.
	 * 
	 * @return the card with a different suit than the others
	 * @throws GameException if the number of cards with the same suit in this hand is not 2
	 */
	public Card31 unidenticalSuitCard() throws GameException {// only to use if matchingSuit() == 2
		if (matchingSuit() == 2) {// otherwise this won't work
			try {
				String high = highestSuit();
				for (int i = 0; i < hand.size(); i++) {
					if (!hand.get(i).getSuit().equalsIgnoreCase(high)) {
						return hand.get(i);
					}
				}
				throw new GameException("A card must be found.", "I0800");
			} catch (GameException e) {
				UserInterface.displayException(e, 3);
				throw new NullPointerException("Something is missing with the unidentical suit card");
			}
		} else {
			throw new GameException(
					"unidenticalSuitCard() only works when the max number of cards in a suit is 2 (out of 3).", "V1567");
		}
	}
	/**
	 * Given two cards in this player's hand have the same number, but not all three, finds the card that does not have the
	 * same number.
	 * 
	 * @return the card with a different number than the others
	 * @throws GameException if the number of cards with the same number in this hand is not 2
	 */
	public Card31 unidenticalNumberCard() throws GameException {
		if (matchingNumber() == 2) {
			if (getCard(0).getSpecialNumber() == getCard(1).getSpecialNumber()) {
				return getCard(2);
			} else if (getCard(0).getSpecialNumber() == getCard(2).getSpecialNumber()) {
				return getCard(1);
			} else {
				return getCard(0);
			}
		} else {
			throw new GameException(
					"unidenticalNumberCard() only works when the max # of common number cards is 2 (out of 3).", "V1893");
		}
	}
	/**
	 * Finds the worst card that does not match the given factors.
	 * 
	 * @param num the number to avoid
	 * @param face the face to avoid
	 * @param suit the suit to avoid
	 * @return the worst card in the hand that is not of suit suit, face face, and number num
	 */
	public Card31 worstCardLimiter(int num, char face, String suit1) {
		if (num == 0 && suit1.equalsIgnoreCase("") && face == ' ') {
			UserInterface.displayError("Why use this function if you're not going to enter values?");
			return null;
		}
		// this finds the worst card (in terms of number) that does not match the suit or number
		int worstCardValue = 100;
		int index = -1;
		if (num == 0 && face == ' ' && !suit1.equalsIgnoreCase("") && matchingSuit() == 3) { return null; }
		for (int i = 0; i < hand.size(); i++) {
			if (num != 10) {
				if (getCard(i).getNumber() != num && !getCard(i).getSuit().equalsIgnoreCase(suit1)) {
					if (getCard(i).getNumber() < worstCardValue) { index = i; worstCardValue = getCard(i).getNumber(); }
				}
			} else {
				if (getCard(i).getFace() != face && !getCard(i).getSuit().equalsIgnoreCase(suit1)) {
					if (getCard(i).getNumber() < worstCardValue) { index = i; worstCardValue = getCard(i).getNumber(); }
				}
			}
		}
		try {
			return getCard(index);
		} catch (IndexOutOfBoundsException e) {
			UserInterface.displayException(e, 3);
			return null;
		}
	}
	/**
	 * Counts the number of cards with the given face.
	 * @param f the face to check
	 * @return the number of cards with the same face as f
	 */
	public int faceCount(char f) {
		int count = 0;
		for (int i = 0; i < hand.size(); i++) { if (getCard(i).getFace() == f) { count++; } }
		return count;
	}
	/*
	 * public int[] bubbleSort(int[] array) { boolean swapped = true; int j = 0; int tmp; while (swapped) { swapped = false;
	 * j++; for (int i = 0; i < array.length - j; i++) { if (array[i] > array[i + 1]) { tmp = array[i]; array[i] = array[i +
	 * 1]; array[i + 1] = tmp; swapped = true; } } } return array; }
	 */
	/**
	 * Finds the sum of the cards in the given suit.
	 * 
	 * @param s
	 * @return
	 */
	public int suitSum(String s) {
		int sum = 0;
		for (int i = 0; i < 3; i++) { if (getCard(i).getSuit().equalsIgnoreCase(s)) { sum += getCard(i).getNumber(); } }
		return sum;
	}
	/**
	 * The "optimal formation" is two of a number, two of a suit.
	 * 
	 * @return true if the "optimal formation" is met, false otherwise
	 */
	public boolean optimalFormation() {// two of a suit, two of a number
		if (matchingNumber() == 2 && matchingSuit() == 2) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks for how many cards have number values of 10. Not used in specific custom rules.
	 * 
	 * @return the number of cards that have their number equal to 10s
	 */
	public int findTens() {// actually this finds the total number of ten-number cards in the hand
		int ct = 0;
		for (int i = 0; i < getHand().size(); i++) { if (getCard(i).getNumber() == 10) { ct++; } }
		return ct;
	}
	/**
	 * Finds the locations of the two cards with number value 10.
	 * @return an array with true elements where the cards are number 10 and false where they are not
	 * @throws GameException if not two cards have number value 10
	 */
	public boolean[] twoTensLocations() throws GameException {
		if (findTens() != 2) {
			throw new GameException("The preconditions of this method (two ten valued cards) were not met.", "L0543");
		} else {
			boolean[] list = new boolean[getHand().size()];
			for (int i = 0; i < list.length; i++) {
				if (getCard(i).getNumber() == 10) { list[i] = true; }
			}
			return list;
		}
	}
	/**
	 * Checks for the theoretical maximum possible sum of the hand with one card from the middle.
	 * 
	 * @param m the middlehand to check cards from
	 * @return the maximum possible sum (3 of a number, suit, etc.)
	 * @throws GameException if exactly two numbers are equal and something goes wrong with calculating the special number
	 *             indexes or locations
	 */
	public double maxPossibleSum(MiddleHand m) throws GameException {
		String[] rank = suitRank();
		ArrayList<String> ranks = new ArrayList<String>();
		for (String r : rank) { ranks.add(r); }
		double maxSum = suitSum(rank[0]);
		double secondMaxSum = suitSum(rank[1]);
		double thirdMaxSum = suitSum(rank[2]);
		ArrayList<Double> oldSums = new ArrayList<Double>();
		oldSums.add(maxSum);
		oldSums.add(secondMaxSum);
		oldSums.add(thirdMaxSum);
		if (matchingNumber() == 3) {
			if (getCard(0).getNumber() == 11) {
				return 32;
			} else {
				return 30.5;
			}
		}
		if (matchingSuit() == 3) {// 3 of a kind
			if (m.bestCardInSuit(rank[0]) != null
					&& m.bestCardInSuit(rank[0]).getNumber() > worstCardInSuit(rank[0]).getNumber()) {
				int high = m.bestCardInSuit(rank[0]).getNumber();
				int low = worstCardInSuit(rank[0]).getNumber();
				maxSum += (high - low);
				return maxSum;
			} else {
				return maxSum;
			}
		}
		if (matchingNumber() == 2) {// 2 of a number, testing if the third of the number exists in mid
			if (m.contains(commonNumber(), commonFace()) >= 0) {
				if (commonNumber() == 11) {
					return 32;
				} else {
					return 30.5;
				}
			}
		}
		if (m.bestCardInSuit(rank[0]) != null) { maxSum += m.bestCardInSuit(rank[0]).getNumber(); }
		if (m.bestCardInSuit(rank[1]) != null) { secondMaxSum += m.bestCardInSuit(rank[1]).getNumber(); }
		if (m.bestCardInSuit(rank[2]) != null) { thirdMaxSum += m.bestCardInSuit(rank[2]).getNumber(); }
		if (maxSum == secondMaxSum && secondMaxSum == thirdMaxSum) { return maxSum; }
		ArrayList<Double> sums = new ArrayList<Double>();
		sums.add(maxSum);
		sums.add(secondMaxSum);
		sums.add(thirdMaxSum);
		Collections.sort(sums);
		Collections.reverse(sums);
		return sums.get(0);
	}
	/**
	 * Checks for the theoretical maximum possible sum of cards with one card from the center, but with custom mode rules.
	 * 
	 * @param m the middle hand to check cards from
	 * @return the max possible sum of cards with the help of any one middle hand card, but with custom mode scoring
	 * @throws GameException if exactly two numbers are equal and something goes wrong with calculating the special number
	 *             indexes or locations
	 */
	public double maxPossibleCustomSum(MiddleHand m) throws GameException {
		String[] rank = suitRank();
		ArrayList<String> ranks = new ArrayList<String>();
		for (String r : rank) { ranks.add(r); }
		double maxSum = suitSum(rank[0]);
		double secondMaxSum = suitSum(rank[1]);
		double thirdMaxSum = suitSum(rank[2]);
		ArrayList<Double> oldSums = new ArrayList<Double>();
		oldSums.add(maxSum);
		oldSums.add(secondMaxSum);
		oldSums.add(thirdMaxSum);
		if (matchingNumber() == 3) {
			if (getCard(0).getNumber() == 11) {
				return PlayingCard.getAceValue() + 2 * PlayingCard.getJackValue() + 3.5;
			} else {
				return 30.5;
			}
		}
		if (matchingSuit() == 3) {// 3 of a kind
			if (m.bestCardInSuit(rank[0]) != null
					&& m.bestCardInSuit(rank[0]).getNumber() > worstCardInSuit(rank[0]).getNumber()) {
				int high = m.bestCardInSuit(rank[0]).getNumber();
				int low = worstCardInSuit(rank[0]).getNumber();
				maxSum += (high - low);
				return maxSum;
			} else {
				return maxSum;
			}
		}
		// fix this up a little
		if (matchingNumber() == 2) {// 2 of a number, testing if the third of the number exists in mid
			if (m.contains(commonNumber(), commonFace()) >= 0) {
				if (commonNumber() == 11) {
					return 32;
				} else {
					return 30.5;
				}
			}
		}
		if (m.bestCardInSuit(rank[0]) != null) { maxSum += m.bestCardInSuit(rank[0]).getNumber(); }
		if (m.bestCardInSuit(rank[1]) != null) { secondMaxSum += m.bestCardInSuit(rank[1]).getNumber(); }
		if (m.bestCardInSuit(rank[2]) != null) { thirdMaxSum += m.bestCardInSuit(rank[2]).getNumber(); }
		if (maxSum == secondMaxSum && secondMaxSum == thirdMaxSum) { return maxSum; }
		ArrayList<Double> sums = new ArrayList<Double>();
		sums.add(maxSum);
		sums.add(secondMaxSum);
		sums.add(thirdMaxSum);
		Collections.sort(sums);
		Collections.reverse(sums);
		return sums.get(0);
	}
	/**
	 * Finds the maximum possible sum with any one card from the middle hand in the given suit.
	 * 
	 * @param suit the suit to calculate the maximum possible sum from
	 * @param m the middle hand to obtain any card to check the sum from
	 * @return the maximum possible sum in the given suit (with help from the middle hand)
	 */
	public double maxPossibleSum(String suit, MiddleHand m) {
		Card31 c = m.bestCardInSuit(suit);
		Card31 h = worstCardInSuit(suit);
		// c is not null
		if (c != null && h != null) {
			int sum = suitSum(suit);
			if (matchingSuit() == 3) {
				int lowInHand = h.getNumber();
				int highInMid = c.getNumber();
				if (lowInHand >= highInMid) {
					return sum;
				} else {
					return sum + (highInMid - lowInHand);
				}
			}
			return sum + c.getNumber();
		} else {
			return suitSum(suit);
		}
	}
	/**
	 * Finds the number of one of two cards with the same special numbers.
	 * 
	 * @return the number of one of the cards with the same special number
	 * @throws GameException if something does not work when calculating the special number indexes
	 */
	public int commonNumber() throws GameException { return getCard(commonSpecialNumberIndex()).getNumber(); }
	/**
	 * Finds the special number of one of two cards with the same special numbers.
	 * 
	 * @return the special number of one of the cards with the same special number
	 * @throws GameException if something does not work when calculating the special number indexes
	 */
	public int commonSpecialNumber() throws GameException {
		return getCard(commonSpecialNumberIndex()).getSpecialNumber();
	}
	// should only be used with 10s or lls, again only works with two of a kinds
	/**
	 * Finds the face of one of two cards with the same special numbers.
	 * 
	 * @return the face of one of the cards with the same special number
	 * @throws GameException if something does not work when calculating the special number indexes
	 */
	public char commonFace() throws GameException { return getCard(commonSpecialNumberIndex()).getFace(); }
	/**
	 * Finds the location of one of the two cards with the same special number. Does not work if no cards have the same
	 * special number or all three have the same special number.
	 * 
	 * @return the location where the cards with the same special number are
	 * @throws GameException if something does not work when calculating the special number indexes
	 */
	public int commonSpecialNumberIndex() throws GameException {
		if (getCard(0).getSpecialNumber() == getCard(1).getSpecialNumber()) {
			return 0;
		} else if (getCard(0).getSpecialNumber() == getCard(2).getSpecialNumber()) {
			return 2;
		} else if (getCard(1).getSpecialNumber() == getCard(2).getSpecialNumber()) {
			return 1;
		} else {
			throw new GameException("Something went wrong finding the common special number index.", "G6666");
		}
	}
	// goal of function: move two cards around
	/**
	 * Moves two cards around by changing their location.
	 * 
	 * @param loc0 the location of the first card
	 * @param loc1 the location of the other card
	 */
	public void moveCardsAround(int loc0, int loc1) {
		ArrayList<Card31> copy = new ArrayList<Card31>(hand);
		hand.clear();
		for (int i = 0; i < copy.size(); i++) {
			if (i == loc0) {
				hand.add(new Card31(copy.get(loc1)));
			} else if (i == loc1) {
				hand.add(new Card31(copy.get(loc0)));
			} else {
				hand.add(copy.get(i));
			}
		}
	}

}