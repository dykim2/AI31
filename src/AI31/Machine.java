package AI31;
import java.util.ArrayList;
import baselineCustomClasses.GameException;
import baselineCustomClasses.PlayingCard;
/**
 * This class defines the artificial intelligence playing the game of 31. 
 * @author Dong-Yon Kim
 */
public class Machine extends Player {
	// this class defines the AI, giving it a name and such - the below variables have been defined and initialized
	// appropriately
	// private String name;
	// private ArrayList<Card31> hand;
	// private UserInterface game;
	// minimax algorithm
	private int computerNumber = 0;
	/**
	 * Creates a new Machine Object, representing a computer playing the human in a game of 31.
	 * 
	 * @param n the computer number (has no other significant purpose)
	 * @param u the UserInterface the Machine belongs to (to ensure the appropriate middle hand is kept track of)
	 * @param na the name of this Machine
	 */
	public Machine(int n, UserInterface u, String na) {
		super(u);
		setName(na);
		computerNumber = n;
		getUserInterface().getPanel().addText("I am AI #" + computerNumber + " and my name is " + getName() + ".\n");

	}
	/**
	 * Do not use this constructor.
	 * 
	 * @param na the name of the machine
	 * @deprecated
	 */
	public Machine(String na) { super(na); setName(na); }
	@Override
	/**
	 * <html> The 9 conditions are as follows: 0: Final turn (SPECIAL)<br>
	 * 1: Two aces, third in the middle (swap for the third ace)<br>
	 * 2: The middle hand's sum is larger than the highest possible sum with a card from the middle (swap 3 and pass)<br>
	 * 3: 3 of a matching number<br>
	 * 4: 3 of the same suit<br>
	 * 5: the "optimal condition" hand (2 of a number, 2 of a suit)<br>
	 * 6: 2 of a matching number<br>
	 * 7: 2 of a matching suit<br>
	 * 8: no matches<br>
	 * Some have sub conditions, but those do not matter here.
	 */
	public int[] takeTurn() {
		// based off of a set of eight conditions, with several sub-conditions
		try {
			double high = maxPossibleSum(getMid());
			if (getUserInterface().getTurnStatus() == -1) { /* 0 - praying it works - final turn */ return doFinalTurn(); }
			if (matchingNumber() == 2 && commonNumber() == 11 && getMid().contains(11, 'A') > -1) {
				/* 1 - complete */ return doConditionOneTurn(); }
			// for the above, what is aces are 12?
			if (high < middleHandSum()) { /* 2 - complete */ return doConditionTwoTurn(); }
			// System.out.println("CAUTION");
			if (matchingNumber() == 3) { /* 3 - complete */ return doConditionThreeTurn(); }
			// System.out.println("...RIP...");
			if (matchingSuit() == 3) { /* 4 - complete */ return doConditionFourTurn(); }
			if (matchingSuit() == 2 && matchingNumber() == 2) { /* 5 - complete */ return doConditionFiveTurn(); }
			// System.out.println("HELP");
			if (matchingNumber() == 2) { /* 6 - complete */ return doConditionSixTurn(); }
			// System.out.println("THERE");
			if (matchingSuit() == 2) { /* 7 - complete */ return doConditionSevenTurn(); }
			// System.out.println("HERE");
			if (matchingNumber() == 0 && matchingSuit() == 0) { /* 8 - complete */ return doConditionEightTurn(); }
			throw new GameException("One condition must run.", "I9232");
		} catch (GameException e) {
			UserInterface.displayException(e, 5);
			throw new NullPointerException("Something is missing with the hand");
		} catch (Exception e) {
			UserInterface.displayException(e, 5);
			UserInterface.displayError("Something went wrong while the AI took its turn");
			int[] num1 = {0, 0};
			return num1;
		}
	}
	/**
	 * Runs the final turn scenarios for the machine. This is also the 0th condition function, where there is a final turn.
	 * 
	 * @return the array of size 2 containing the choice made by this machine
	 */
	private int[] doFinalTurn() {
		try {
			int[] nums = {0, 0};
			double[] maxPossibleSums = new double[7];
			for (int i = 0; i < SUITS.length; i++) {
				maxPossibleSums[i + 2] = maxPossibleSum(SUITS[i], getUserInterface().getMiddleHand());
			}
			// check all the maximum possible sums
			maxPossibleSums[0] = maxPossibleSum(getUserInterface().getMiddleHand());
			maxPossibleSums[1] = middleHandSum();
			maxPossibleSums[6] = sumOfHand();
			double mps = 0;
			int index = -1;
			ArrayList<Double> printSums = new ArrayList<Double>();
			for (int i = 0; i < maxPossibleSums.length; i++) {
				printSums.add(maxPossibleSums[i]);
				if (maxPossibleSums[i] >= mps) { mps = maxPossibleSums[i]; index = i; }
			}
			// found the max possible sum
			if (index > 1 && index < 6) {
				index -= 2;
				Card31 worst = worstCardLimiter(0, ' ', SUITS[index]);
				// they have three of that suit but it can be improved
				if (worst != null) {
				} else {
					worst = worstCardInSuit(SUITS[index]);
				} // 3 of a suit
				nums[0] = findCard(worst);
				Card31 best = getUserInterface().getMiddleHand().bestCardInSuit(SUITS[index]);
				nums[1] = getUserInterface().getMiddleHand().findCard(best);
				increment(nums);
				return nums;
				// find the worst card in the hand
				// then find the best card of index suit
			} else if (index == 6) {
				return conditions(-1);
			} else if (index == 1) {
				return conditions(-2);
			} else if (index == 0) {// max possible sum largest value
				// either 30.5 or 32 - must mean common number
				nums[0] = findCard(unidenticalNumberCard());
				nums[1] = getUserInterface().getMiddleHand().contains(commonSpecialNumber());
				increment(nums);
				return nums;
			}
			return nums;
		} catch (GameException e) {
			UserInterface.displayException(e, 5);
			return null;
		}
	}
	/**
	 * Runs the play through for condition 1, where there is an ace in the middle and two in the hand.
	 * 
	 * @return an array that selects the player's card to remove and the middle card to get
	 * @throws GameException
	 */
	private int[] doConditionOneTurn() throws GameException {
		// system.out.println("1");
		int[] nums = new int[2];
		nums[0] = findCard(unidenticalNumberCard());
		nums[1] = getMid().contains(11, 'A');
		increment(nums);
		return nums;
	}
	/**
	 * Runs the play through for condition 2, where the middle hand's sum is larger than the maximum possible sum of hand.
	 * 
	 * @return an array of length 2 that signifies a swap 3 and pass
	 * @throws GameException if something goes wrong creating the array
	 */
	private int[] doConditionTwoTurn() throws GameException { return conditions(-2); }
	/**
	 * Runs the play through for condition 3, where the AI has 3 of the same number.
	 * 
	 * @return an array of length 2 that signifies either a pass or a swap 3 and pass, depending on the sum of the middle
	 *         hand
	 * @throws GameException if something goes wrong creating the array
	 */
	private int[] doConditionThreeTurn() throws GameException {
		if (middleHandSum() >= 30.5) {return conditions(-2);} 
		else {return conditions(-1);}
	}
	/**
	 * Runs the play through for condition 4, with 3 of a matching suit.
	 * 
	 * @return the choice of cards the AI makes represented as an array of length 2
	 * @throws GameException if something goes wrong while taking the turn
	 */
	private int[] doConditionFourTurn() throws GameException {
		int[] nums = new int[2];
		String[] rank = suitRank();
		if (maxPossibleSum(getMid()) > sumOfHand()) {
			nums[0] = findCard(worstCardInSuit(rank[0]));
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));
		} else {
			if (getMid().sumOfHand() > sumOfHand()) {
				return conditions(-2);// swap 3 and pass
			} else {
				return conditions(-1);// pass
			}
		}
		increment(nums);
		return nums;
	}
	/**
	 * Runs the play through for condition 5, with 2 of a number and 2 of a suit.
	 * 
	 * @return the choice of cards the AI makes represented as an array of length 2
	 * @throws GameException if something goes wrong while taking the turn
	 */
	private int[] doConditionFiveTurn() throws GameException {
		int[] nums = new int[2];
		String[] rank = suitRank();
		nums[0] = findCard(unidenticalSuitCard());
		if (maxPossibleSum(getMid()) == 31) {
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));
		} // 5.0
		else if (maxPossibleSum(getMid()) == 30.5) {// 5.1
			nums[0] = findCard(unidenticalNumberCard());
			nums[1] = getMid().contains(commonSpecialNumber());
		} else if (getMid().contains(rank[0]) >= 0) {
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));
		} // 5.2
		else if (getMid().contains(rank[1]) >= 0 && maxPossibleSum(rank[1], getMid()) > sumOfHand()) {// 5.3
			nums[0] = findCard(unidenticalNumberCard());
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[1]));
		} else {// 5.4
			if (getMid().sumOfHand() > sumOfHand()) {
				/* System.out.println("5e1"); */ return conditions(-2);
			} // 5.40
			else {/* System.out.println("5e2"); - only place that needs to get the check of verification */
				// nums[0] = findCard(determineWorstCard(getMid(), 5, 0));
				if (Math.random() > 0.5) {
					return conditions(-1);
				} // 5.41
				else {
					nums[1] = getMid().findCard(getMid().bestCardInSuit(getMid().suitRank()[0]));
				}
			} // wait more and pass less
		}
		increment(nums);
		return nums;
	}
	/**
	 * Runs the play through for condition 6, with 2 of a number.
	 * @return the choices made by this AI in the form of a 1x2 array
	 */
	private int[] doConditionSixTurn() {
		int[] nums = {0, 0};
		try {
			Card31[] matchingCards = {null, null};
			for (int i = 0; i < getHand().size(); i++) {
				if (getCard(i).getSpecialNumber() == commonSpecialNumber()) {
					if (matchingCards[0] == null) {
						matchingCards[0] = new Card31(getCard(i));
					} else if (matchingCards[1] == null) { matchingCards[1] = new Card31(getCard(i)); }
				}
			}
			if (getMid().contains(commonSpecialNumber()) >= 0) {
				nums[0] = findCard(unidenticalNumberCard());
				nums[1] = getMid().contains(commonSpecialNumber());
			} else if (getMid().contains(matchingCards[0].getSuit()) >= 0
					|| getMid().contains(matchingCards[1].getSuit()) >= 0) {
				nums[0] = findCard(unidenticalNumberCard());
				if (getMid().contains(matchingCards[0].getSuit()) >= 0
						&& getMid().contains(matchingCards[1].getSuit()) >= 0) {
					// cards of both SUITS exist in mid
					int value = getMid().bestCardInSuit(matchingCards[0].getSuit())
							.compareByNumber(getMid().bestCardInSuit(matchingCards[1].getSuit()));
					if (value >= 0) {
						nums[1] = getMid().findCard(getMid().bestCardInSuit(matchingCards[1].getSuit()));
					} else {// first card has larger number
						nums[1] = getMid().findCard(getMid().bestCardInSuit(matchingCards[0].getSuit()));
					}
				} else if (getMid().contains(matchingCards[0].getSuit()) >= 0) {
					nums[1] = getMid().findCard(getMid().bestCardInSuit(matchingCards[0].getSuit()));
				} else if (getMid().contains(matchingCards[1].getSuit()) >= 0) {
					nums[1] = getMid().findCard(getMid().bestCardInSuit(matchingCards[1].getSuit()));
				} else {
					throw new GameException("Something went wrong finding a suit of a common number card.", "C9842");
				}
			} else {
				if (getMid().sumOfHand() > sumOfHand()) {
					return conditions(-2);
				} else if (getMid().sumOfHand() == sumOfHand()) {
					return conditions(-2);
				} else {
					nums[1] = getMid().findCard(getMid().bestCardInSuit(getMid().suitRank()[0]));
				}
			}
			increment(nums);
			return nums;
		} catch (Exception e) {
			UserInterface.displayException(e, 5);
		}
		return null;
	}
	/**
	 * Consolidates the seventh condition into its own set of code. The seventh condition is when the best the AI has is two
	 * of a matching suit.
	 * 
	 * @return
	 */
	private int[] doConditionSevenTurn() {
		try {
			int[] nums = {0, 0};
			String[] rank = suitRank();
			Card31[] matchingCards = {null, null};
			for (int i = 0; i < getHand().size(); i++) {
				if (getCard(i).getSuit().equalsIgnoreCase(rank[0])) {
					if (matchingCards[0] == null) {
						matchingCards[0] = new Card31(getCard(i));
					} else if (matchingCards[1] == null) { matchingCards[1] = new Card31(getCard(i)); }
				}
			}
			nums[0] = findCard(unidenticalSuitCard());
			if (getMid().contains(rank[0]) >= 0) {
				nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));
			} else if (getMid().contains(matchingCards[0].getSpecialNumber()) >= 0
					|| getMid().contains(matchingCards[1].getSpecialNumber()) >= 0) {
				// 2 of a suit - trying to get 2 of a suit and number
				if (getMid().contains(matchingCards[0].getSpecialNumber()) >= 0) {
					nums[1] = getMid().contains(matchingCards[0].getSpecialNumber());
				} else if (getMid().contains(matchingCards[1].getSpecialNumber()) >= 0) {
					nums[1] = getMid().contains(matchingCards[1].getSpecialNumber());
				} else {
					throw new GameException("Something went wrong here trying to do something I don't know.", "I1111");
				}
			} else if (getMid().contains(rank[1]) >= 0 && maxPossibleSum(rank[1], getMid()) > sumOfHand()) {
				nums[0] = findCard(worstCardInSuit(rank[0]));
				nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[1]));
			} else {
				if (getMid().sumOfHand() > sumOfHand()) {
					return conditions(-2);
				} else {
					return conditions(-1);
				}
			}
			increment(nums);
			return nums;
		} catch (GameException e) {
			UserInterface.displayException(e, 5);
		}
		return null;
	}
	/**
	 * Runs the play through for condition 8, with 0 of a suit and 0 of a number.
	 * @return the choice of cards made by this AI in a length 2 array
	 */
	private int[] doConditionEightTurn() {
		int[] nums = new int[2];
		String[] rank = suitRank();
		if (getMid().contains(rank[0]) >= 0) {
			// System.out.println("8a");
			nums[0] = findCard(bestCardInSuit(rank[2]));
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));
		} else if (getMid().contains(rank[1]) >= 0) {
			nums[0] = findCard(bestCardInSuit(rank[2]));
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[1]));
		} else if (getMid().contains(rank[2]) >= 0) {
			nums[0] = findCard(bestCardInSuit(rank[1]));
			nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[2]));
		} else {
			try {
				throw new GameException("This can't run if mid has three of a suit - no suit in hand has a match in mid.",
						"H8457");
			} catch (GameException e) {
				UserInterface.displayException(e, 5);
				nums[0] = -2;
				nums[1] = -2;
				return nums;
			}
		}
		increment(nums);
		return nums;
	}
	/**
	 * Tries to find a card with the
	 * 
	 * @param num
	 * @param i
	 * @return a card
	 */
	public Card31 attemptToFindCard(int num, int i) {// knowing the highest suit, it tries to find a card in the middle with
														// the number num
		String find = highestSuit();
		int loc;
		// custom mode? I'm slightly concerned
		if ((num == 10 && !getUserInterface().isCustom())
				|| (num == PlayingCard.getJackValue() && getUserInterface().isCustom())) {
			loc = getMid().findCard(new Card31(getCard(i).getFace(), find));
		} else {
			loc = getMid().findCard(new Card31(num, find));
		} // -<the location

		if (loc != -1) { return new Card31(getCardInMiddle(loc)); }
		return null;

	}
	@Override
	public String toString() {
		String s = getName() + ": My cards are: ";
		for (int i = 0; i < handSize(); i++) { s += getCard(i) + " (" + (i + 1) + "), "; }
		s += "\nSum: " + sumOfHand() + "\n";
		return s;
	}
	/**
	 * Formerly used method to check if the pass conditions were met.
	 * 
	 * @param m the middle hand of the game
	 * @param turnCounter the turn count of the game -
	 * @return
	 * @deprecated
	 */
	private int passConditionsMet(MiddleHand m, int turnCounter) {
		/*
		 * try{ int turnStatus = 0; if(!canImproveHand(m)){ if(m.sumOfHand() >= this.sumOfHand()){
		 * System.out.println(getName()+": the middle hand is better than my unimprovable hand."); return -2;}
		 * if(optimalFormation()){System.out.println(getName()+": Optimal formation of unimprovable cards."); return -1;}
		 * //this works for one reason: if the number of matching suit cards is 3, then the middle must have at least //3 of
		 * a suit to have a larger sum if(matchingSuit() == 3){System.out.println(getName()+": I have 3 of a suit."); return
		 * -1;}//three of a suit; pretty obvious why if(matchingNumber() ==
		 * 3){System.out.println(getName()+": I have 3 of a number."); return -1;} //the minimum hand sum is 15 for a hand
		 * with two suits //if the middle has more points but you can't improve your hand
		 * 
		 * //nothing matching - might as well swap 3 and pass and hope for the best if(turnStatus == -1 &&(m.sumOfHand() >
		 * this.sumOfHand())){ System.out.println(getName()
		 * +": final turn, I can't improve my hand and the middle has a higher sum, so I will swap for it."); return -2;}
		 * if(turnCounter <=3 && m.sumOfHand() > this.sumOfHand()){
		 * System.out.println(getName()+": it's early in the round, and I've bad cards, so screw it I'll swap out my cards."
		 * ); return -2;} else if(turnCounter <=3 && m.sumOfHand() < this.sumOfHand()){
		 * System.out.println(getName()+": it's early in the round, and the middle cards are terrible, so I'll pass.");
		 * return -1; } if(matchingSuit() == 2 && sumOfHand() >=
		 * 19){System.out.println(getName()+": I have a decent, unimprovable sum, so I'll pass."); return -1;} }
		 * 
		 * if((sumOfHand()<15 && matchingNumber()==0) &&
		 * m.sumOfHand()>16){System.out.println(getName()+": seems like I'll have a better chance with " +
		 * "the middle cards."); return -2;} //30.5... duh, obviously you would pass unless the middle has 31 or 32
		 * 
		 * 
		 * return 0; } catch(GameErrorException e){System.out.println(e); return 0;}
		 */return 0;
	}
	/**
	 * Checks to see if the hand can be improved.
	 * 
	 * @param m the middle hand to check with
	 * @return true if the hand can be improved, false if not
	 * @deprecated No longer used and has no code. Do not use this method.
	 */
	private boolean canImproveHand(MiddleHand m) {// by suit - if a card of any of the best suits
		/*
		 * try{ if(sumOfHand()==31 || sumOfHand()==30.5){return false;} if(matchingNumber()==2/*&&
		 * !(m.contains(commonNumber(), commonFace())>0)){ System.out.println(""); if(m.contains(commonNumber(),
		 * commonFace())>0){return true;} if(m.bestCardInSuit(highestSuit())!=null){return true;} else{return false;} } int
		 * match = matchingSuit(); String[] rank = suitRank();
		 * 
		 * if(m.bestCardInSuit(rank[0])!=null){//there is a card of the suit in the middle hand Card31 best =
		 * m.bestCardInSuit(highestSuit()); if(match==3){ Card31 worst = worstCardInSuit(getCard(1).getSuit());
		 * System.out.println("Worst card (in "+getName()+"'s hand): "+worst); if(worst.compareTo(best)<0){return true;}
		 * else{return false;} //here if this is the case then you want to swap the two } else{return true;} } else{
		 * if(match==0){ if(m.bestCardInSuit(rank[1])!=null){return true;} else{return false;} } else{return false;} } //more
		 * concise code //if(maxPossibleSum(getMid())>sumOfHand()){return true;} //else
		 * if(maxPossibleSum(getMid())==sumOfHand()){return false;} //else{throw new
		 * GameErrorException("Something went wrong trying to check if the hand can be improved.");}
		 * 
		 * //for(int i=0; i<handSize(); i++){if(m.contains(getCard(i).getNumber(), getCard(i).getFace())!=-1){return true;}}
		 * //throw new GameErrorException("Something went wrong trying to check if the hand can be improved.");
		 * 
		 * } catch(GameErrorException e){System.out.println(e); return false;} catch(PlayingCardException
		 * e){System.out.println(e); return false;}
		 */
		return false;
	}
	/**
	 * If the machine is the dealer, they must decide to keep or swap their cards. This function does the math.
	 * 
	 * @return 2 (keep) with at least 2 of the same suit or number, 1 (swap) otherwise
	 */
	public int doDealersTurn() {// return 1 if the machine wants to swap 3, 2 to keep the dealt cards
		if (matchingSuit() >= 2 || matchingNumber() >= 2) {
			return 2;
		} else {
			return 1;
		}
	}
	/**
	 * Obtains a card from the middle hand at the given index.
	 * 
	 * @param index the location of the card in the mid hand
	 * @return the card at the index
	 */
	private Card31 getCardInMiddle(int index) { return new Card31(getMid().getCard(index)); }
	/**
	 * Checks if two cards with number value 10 are the same. Requires there to be only two cards with numerical value 10.
	 * 
	 * @return true if the cards are the same,
	 * @throws GameException
	 */
	public boolean equalTens() throws GameException {
		if (twoTensLocations() != null) {
			boolean[] list = twoTensLocations();
			char[] faces = new char[list.length];
			ArrayList<Integer> indexes = new ArrayList<Integer>(list.length - 1);

			for (int i = 0; i < list.length; i++) {
				if (list[i] != false) {
					faces[i] = getCard(i).getFace();
					indexes.add(i);
				} else {
					faces[i] = 'C';
				}
			}
			/*
			 * for(int i=0; i<faces.length; i++){ System.out.println(faces[i]+" face"); if(i!=faces.length-1)
			 * System.out.println(indexes.get(i)+" index"); }
			 */
			if (faces[indexes.get(0)] == faces[indexes.get(1)]) {
				return true;
			} else {
				return false;
			}

		}
		throw new GameException("equalTens() only works with two equal tens, no more and no less.", "D5645");
	}
	/**
	 * Calculates the sum of the middle hand.
	 * 
	 * @return a double representing the sum of the best suit in the middle hand
	 */
	public double middleHandSum() { return getMid().sumOfHand(); }
	/**
	 * Generates an array according to the given conditions.
	 * 
	 * @param num the number of the condition (must be -1 or -2 or preconditions are not met)
	 * @return an integer array of length 2 containing these conditions
	 * @throws GameException if the value of num is not -1 or -2
	 */
	private int[] conditions(int num) throws GameException {
		int[] nums = new int[2];
		if (num == -1) {
			nums[0] = -1;
			nums[1] = -1;
		} else if (num == -2) {
			nums[0] = -2;
			nums[1] = -2;
		} else {
			throw new GameException("The precondtions of this function have not been met.", "H3187");
		}
		return nums;
	}
	/**
	 * Increments every item in the given array by 1.
	 */
	private void increment(int[] original) { for (int i = 0; i < original.length; i++) { original[i]++; } }

	// make the AIs not want to create a good hand in the mid unless needed
	/**
	 * An unused method that checks for the worst card in the middle hand.
	 * 
	 * @param m the middle hand to check with
	 * @param initCondition from 0 to -9 - the takeTurn condition (three of a suit, three of a number) used to determine
	 *            which card to remove
	 * @param subCondition the other unknown condition
	 * @return the card that is the most optimal to remove
	 * @deprecated Currently not in a state of working.
	 */
	public int[] determineWorstCard(MiddleHand m, int initCondition, int subCondition) { return null; }
	/**
	 * This method does not do anything and should not be used. It is a placeholder.
	 * 
	 * @return null
	 * @deprecated
	 */

	private int[] checkMidCards() { return null; }
	/**
	 * This method does not do anything and should not be used. It is a placeholder as well.
	 * 
	 * @return null
	 * @deprecated
	 */
	private int[] checkHandCards() { return null; }
	// the other function I wanted to create required the index of the card selected and the index of the card wanted, yet my
	// memory is garbage and I cant remember

}
