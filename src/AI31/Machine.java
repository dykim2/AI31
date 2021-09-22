package AI31;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import baselineCustomClasses.GameErrorException;
import baselineCustomClasses.PlayingCardException;
public class Machine extends Player {
	//this class defines the AI, giving it a name and such - the below variables have been defined and initialized appropriately
	//private String name;
	//private ArrayList<Card31> hand;
	//private UserInterface game;
	//minimax algorithm
	private int computerNumber = 0;
	/**
	 * Creates a new Machine Object, representing a computer playing the human in a game of 31.
	 * @param n - the computer number (has no other significant purpose)
	 * @param u - the UserInterface the Machine belongs to (to ensure the appropriate middle hand is kept track of)
	 * @param na - the name of this Machine
	 */
	public Machine(int n, UserInterface u, String na){
		super(u);
		setName(na);
		computerNumber = n;
		getUserInterface().getPanel().addText("I am AI #"+computerNumber+" and my name is "+getName()+".\n");

	}
	public Machine(String na) {super(na); setName(na);}
	/**
	 * This constructor should never be used.
	 * 
	 * @param name - the name of this Machine
	 */
	/**
	 * 
	 * @see AI31.Player#takeTurn()
	 * This defines the Machine's turn, choosing the cards it wants.
	 * 
	 * 
	 */
	public int[] takeTurn(){
		//based off of a set of eight conditions, with several sub-conditions
		int[] nums = {0,0};
		try{
			String[] rank = suitRank();
			double high = maxPossibleSum(getMid());
			//system.out.println("WHAT IS HAPPENING");
			if(getUserInterface().getTurnStatus() == -1) {/*0 - praying it works - final turn*/return doFinalTurn();}
			if(matchingNumber()==2 && commonNumber()==11 &&
					getMid().contains(11, 'A')>-1){//1 - complete
				//system.out.println("1");
				nums[0] = findCard(unidenticalNumberCard());
				nums[1] = getMid().contains(11, 'A');
				return increment(nums);
			}
			if(high<middleHandSum()){/*2 - complete*/ /*System.out.println("2");*/ return conditions(-2);}
			//System.out.println("CAUTION");
			if(matchingNumber()==3){//3 - complete
				if(middleHandSum()>=30.5){return conditions(-2);}
				else{return conditions(-1);}
			}
			//System.out.println("...RIP...");
			if(matchingSuit()==3){//4 - complete
				if(maxPossibleSum(getMid()) > sumOfHand()){
					nums[0] = findCard(worstCardInSuit(rank[0]));
					nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));
				}
				else{
					if(getMid().sumOfHand() > sumOfHand()){return conditions(-2);}
					else{return conditions(-1);}
				}
				return increment(nums);
			}
			if(matchingSuit()==2 && matchingNumber()==2){//5 - complete
				nums[0] = findCard(unidenticalSuitCard());
				if(maxPossibleSum(getMid())==31){nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));}//5.0
				else if(maxPossibleSum(getMid())==30.5){//5.1
					nums[0] = findCard(unidenticalNumberCard());
					nums[1] = getMid().contains(commonSpecialNumber());
				}
				else if(getMid().contains(rank[0])>=0){nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));}//5.2
				else if(getMid().contains(rank[1])>=0 && maxPossibleSum(rank[1], getMid())>sumOfHand()){//5.3
					nums[0] = findCard(unidenticalNumberCard());
					nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[1]));
				}
				else{//5.4
					if(getMid().sumOfHand()>sumOfHand()){/*System.out.println("5e1");*/ return conditions(-2);}//5.40
					else{/*System.out.println("5e2"); - only place that needs to get the check of verification*/
						//nums[0] = findCard(determineWorstCard(getMid(), 5, 0));
						if(Math.random()>0.5) {return conditions(-1);}//5.41
						else {nums[1] = getMid().findCard(getMid().bestCardInSuit(getMid().suitRank()[0]));}}//wait more and pass less
				}
				return increment(nums);
			}
			//System.out.println("HELP");
			if(matchingNumber()==2){/*6 - complete*/return doConditionSixTurn();}
			//System.out.println("THERE");
			if(matchingSuit()==2){/*7 - complete*/return doConditionSevenTurn();}
			//System.out.println("HERE");
			if(matchingNumber()==0 && matchingSuit()==0){//8 - complete
				//System.out.println("8");
				if(getMid().contains(rank[0])>=0){
					//System.out.println("8a");
					nums[0] = findCard(bestCardInSuit(rank[2]));
					nums[1] = getMid().findCard
							(getMid().bestCardInSuit(rank[0]));
				}
				else if(getMid().contains(rank[1])>=0){
					nums[0] = findCard(bestCardInSuit(rank[2]));
					nums[1] = getMid().findCard
							(getMid().bestCardInSuit(rank[1]));
				}
				else if(getMid().contains(rank[2])>=0){
					nums[0] = findCard(bestCardInSuit(rank[1]));
					nums[1] = getMid().findCard
							(getMid().bestCardInSuit(rank[2]));
				}
				else{
					try{throw new GameErrorException(
							"This can't run if mid has three of a suit - " +
							"no suit in hand has a match in mid... Error #H8457");}
					catch(GameErrorException e){UserInterface.displayException(e, 5); return conditions(-2);}
				}
				return increment(nums);
			}
			throw new GameErrorException("One condition must run. Error #I9232");
		}
		catch(GameErrorException e){
			UserInterface.displayException(e, 5);
			throw new NullPointerException("Something is missing with the hand");
		}
		catch(PlayingCardException e){
			UserInterface.displayException(e, 5);
			throw new NullPointerException("Something is missing with the hand");
		}
		catch(Exception e) {UserInterface.displayException(e, 5); UserInterface.displayError("Something went wrong while the Ai took its turn"); 
		int[] num1 = {0,0}; return num1;}
	}
	private int[] doFinalTurn() {
		try {
			int[] nums = {0,0};
			double[] maxPossibleSums = new double[7];
			for(int i=0; i<suits.length; i++) {maxPossibleSums[i+2] = maxPossibleSum(suits[i], getUserInterface().getMiddleHand());}
			maxPossibleSums[0] = maxPossibleSum(getUserInterface().getMiddleHand());
			maxPossibleSums[1] = middleHandSum();
			maxPossibleSums[6] = sumOfHand();
			double mps = 0;
			int index = -1;
			ArrayList<Double> printSums = new ArrayList<Double>();
			for(int i=0; i<maxPossibleSums.length; i++) {printSums.add(maxPossibleSums[i]); if(maxPossibleSums[i]>=mps) {mps = maxPossibleSums[i]; index = i;}}
			//found the max possible sum
			if(index>1 && index<6) {
				index-=2;
				Card31 worst = worstCardLimiter(0, ' ', suits[index]);
				//they have three of that suit but it can be improved
				if(worst!=null) {}
				else {worst = worstCardInSuit(suits[index]);}//3 of a suit
				nums[0] = findCard(worst);
				Card31 best = getUserInterface().getMiddleHand().bestCardInSuit(suits[index]);
				nums[1] = getUserInterface().getMiddleHand().findCard(best);
				return increment(nums);
				//find the worst card in the hand
				//then find the best card of index suit
			}
			else if(index==6){return conditions(-1);}
			else if(index==1){return conditions(-2);}
			else if(index==0){//max possible sum largest value
				//either 30.5 or 32 - must mean common number
				nums[0] = findCard(unidenticalNumberCard());
				nums[1] = getUserInterface().getMiddleHand().contains(commonSpecialNumber());
				return increment(nums);
			}
			return nums;
		}
		catch(PlayingCardException e) {UserInterface.displayException(e, 5); return null;}
		catch(GameErrorException e) {UserInterface.displayException(e, 5); return null;}
	}
	private int[] doConditionSixTurn() {
		int[] nums = {0,0};
		try {
			Card31[] matchingCards = {null, null};
			for(int i=0; i<getHand().size(); i++){
				if(getCard(i).getSpecialNumber() == commonSpecialNumber()){
					if(matchingCards[0]==null){matchingCards[0] = new Card31(getCard(i));}
					else if(matchingCards[1]==null){matchingCards[1] = new Card31(getCard(i));}
				}
			}
			if(getMid().contains(commonSpecialNumber())>=0){
				nums[0] = findCard(unidenticalNumberCard());
				nums[1] = getMid().contains(commonSpecialNumber());
			}
			else if(getMid().contains(matchingCards[0].getSuit())>=0 || 
					getMid().contains(matchingCards[1].getSuit())>=0){
				nums[0] = findCard(unidenticalNumberCard()); 
				if(getMid().contains(matchingCards[0].getSuit())>=0 && 
						getMid().contains(matchingCards[1].getSuit())>=0){
					//cards of both suits exist in mid
					int value = getMid().bestCardInSuit(matchingCards[0].getSuit()).
							compareByNumber(getMid().bestCardInSuit(matchingCards[1].getSuit()));
					if(value>=0){
						nums[1] = getMid().findCard(
								getMid().bestCardInSuit(matchingCards[1].getSuit()));
					}
					else{//first card has larger number
						nums[1] = getMid().findCard(
								getMid().bestCardInSuit(matchingCards[0].getSuit()));}
				}
				else if(getMid().contains(matchingCards[0].getSuit())>=0){
					nums[1] = getMid().findCard(
							getMid().bestCardInSuit(matchingCards[0].getSuit()));
				}
				else if(getMid().contains(matchingCards[1].getSuit())>=0){
					nums[1] = getMid().findCard(
							getMid().bestCardInSuit(matchingCards[1].getSuit()));
				}
				else{throw new GameErrorException("Something went wrong finding a suit of a common number card. Error #C9842");}
			}
			else{
				if(getMid().sumOfHand()>sumOfHand()){return conditions(-2);}
				else if(getMid().sumOfHand() == sumOfHand()) {return conditions(-2);}
				else{nums[1] = getMid().findCard(getMid().bestCardInSuit(getMid().suitRank()[0]));}
			}
			return increment(nums);
		}
		catch(Exception e) {UserInterface.displayException(e, 5);}
		return null;
	}
	private int[] doConditionSevenTurn() {
		try {
			int[] nums = {0,0};
			String[] rank = suitRank();
			Card31[] matchingCards = {null, null};
			for(int i=0; i<getHand().size(); i++){
				if(getCard(i).getSuit().equalsIgnoreCase(rank[0])){
					if(matchingCards[0]==null){matchingCards[0] = new Card31(getCard(i));}
					else if(matchingCards[1]==null){matchingCards[1] = new Card31(getCard(i));}
				}
			}
			nums[0] = findCard(unidenticalSuitCard());
			if(getMid().contains(rank[0])>=0){nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[0]));}
			else if(getMid().contains(matchingCards[0].getSpecialNumber())>=0 ||
					getMid().contains(matchingCards[1].getSpecialNumber())>=0){
				//2 of a suit - trying to get 2 of a suit and number
				if(getMid().contains(matchingCards[0].getSpecialNumber())>=0){nums[1] = getMid().contains(matchingCards[0].getSpecialNumber());}
				else if(getMid().contains(matchingCards[1].getSpecialNumber())>=0){nums[1] = getMid().contains(matchingCards[1].getSpecialNumber());}
				else{throw new GameErrorException("Something went wrong here trying to do something I don't know. Error #I1111");}
			}
			else if(getMid().contains(rank[1])>=0 &&
					maxPossibleSum(rank[1], getMid()) > sumOfHand()){
				nums[0] = findCard(worstCardInSuit(rank[0]));
				nums[1] = getMid().findCard(getMid().bestCardInSuit(rank[1]));
			}
			else{
				if(getMid().sumOfHand() > sumOfHand()){return conditions(-2);}
				else{return conditions(-1);}
			}
			return increment(nums);
		}
		catch(PlayingCardException e) {UserInterface.displayException(e, 5);}
		catch(GameErrorException e) {UserInterface.displayException(e, 5);}
		return null;
	}
	public Card31 attemptToFindCard(int num, int i){//knowing the highest suit, it tries to find a card in the middle with the number num	
		try{
			String find = highestSuit();
			int loc;
			if(num==10){loc = getMid().findCard(new Card31(getCard(i).getFace(), find));}
			else{loc = getMid().findCard(new Card31(num, find));}//-<the location

			if(loc!=-1){return new Card31(getCardInMiddle(loc));}
			return null;
		}
		catch(GameErrorException e){UserInterface.displayException(e, 5); return null;}
	}
	public String toString(){
		try{
			String s = getName()+": My cards are: ";
			for(int i=0; i<handSize(); i++){s+=getCard(i)+" ("+(i+1)+"), ";}
			s+="\nSum: "+sumOfHand()+"\n";
			return s;
		}
		catch(GameErrorException e){
			UserInterface.displayException(e, 5);
			throw new NullPointerException("Something went wrong - there is no hand...");
		}

	}
	public int passConditionsMet(MiddleHand m, int turnCounter){
		/*
		try{
			int turnStatus = 0;
			if(!canImproveHand(m)){
				if(m.sumOfHand() >= this.sumOfHand()){
					System.out.println(getName()+": the middle hand is better than my unimprovable hand."); return -2;}
				if(optimalFormation()){System.out.println(getName()+": Optimal formation of unimprovable cards."); return -1;}
				//this works for one reason: if the number of matching suit cards is 3, then the middle must have at least
				//3 of a suit to have a larger sum
				if(matchingSuit() == 3){System.out.println(getName()+": I have 3 of a suit."); return -1;}//three of a suit; pretty obvious why
				if(matchingNumber() == 3){System.out.println(getName()+": I have 3 of a number."); return -1;}
				//the minimum hand sum is 15 for a hand with two suits
				//if the middle has more points but you can't improve your hand

				//nothing matching - might as well swap 3 and pass and hope for the best
				if(turnStatus == -1 &&(m.sumOfHand() > this.sumOfHand())){
					System.out.println(getName()+": final turn, I can't improve my hand and the middle has a higher sum, so I will swap for it."); 
					return -2;}
				if(turnCounter <=3 && m.sumOfHand() > this.sumOfHand()){
					System.out.println(getName()+": it's early in the round, and I've bad cards, so screw it I'll swap out my cards."); return -2;}
				else if(turnCounter <=3 && m.sumOfHand() < this.sumOfHand()){
					System.out.println(getName()+": it's early in the round, and the middle cards are terrible, so I'll pass."); return -1;	}
				if(matchingSuit() == 2 && sumOfHand() >= 19){System.out.println(getName()+": I have a decent, unimprovable sum, so I'll pass.");
				return -1;}
			}

			if((sumOfHand()<15 && matchingNumber()==0) && m.sumOfHand()>16){System.out.println(getName()+": seems like I'll have a better chance with " +
					"the middle cards."); return -2;}
			//30.5... duh, obviously you would pass unless the middle has 31 or 32


			return 0;
		}
		catch(GameErrorException e){System.out.println(e); return 0;}
		 */return 0;}
	public boolean canImproveHand(MiddleHand m){//by suit - if a card of any of the best suits 
		/*
		try{
			if(sumOfHand()==31 || sumOfHand()==30.5){return false;}
			if(matchingNumber()==2/*&& !(m.contains(commonNumber(), commonFace())>0)){
				System.out.println("");
				if(m.contains(commonNumber(), commonFace())>0){return true;}
				if(m.bestCardInSuit(highestSuit())!=null){return true;}
				else{return false;}
			}
			int match = matchingSuit();
			String[] rank = suitRank();

			if(m.bestCardInSuit(rank[0])!=null){//there is a card of the suit in the middle hand
				Card31 best = m.bestCardInSuit(highestSuit());
				if(match==3){
					Card31 worst = worstCardInSuit(getCard(1).getSuit());
					System.out.println("Worst card (in "+getName()+"'s hand): "+worst);
					if(worst.compareTo(best)<0){return true;}
					else{return false;}
					//here if this is the case then you want to swap the two
				}
				else{return true;}
			}
			else{
				if(match==0){
					if(m.bestCardInSuit(rank[1])!=null){return true;}
					else{return false;}
				}
				else{return false;}
			}
			//more concise code
			//if(maxPossibleSum(getMid())>sumOfHand()){return true;}
			//else if(maxPossibleSum(getMid())==sumOfHand()){return false;}
			//else{throw new GameErrorException("Something went wrong trying to check if the hand can be improved.");}

			//for(int i=0; i<handSize(); i++){if(m.contains(getCard(i).getNumber(), getCard(i).getFace())!=-1){return true;}}
			//throw new GameErrorException("Something went wrong trying to check if the hand can be improved.");

		}
		catch(GameErrorException e){System.out.println(e); return false;}
		catch(PlayingCardException e){System.out.println(e); return false;}
		 */
		return false;
	}
	public int doDealersTurn(){//return 1 if the machine wants to swap 3, 2 to keep the dealt cards
		if(matchingSuit()>=2 || matchingNumber()>=2){return 2;}
		else{return 1;}
	}
	private Card31 getCardInMiddle(int index){return new Card31(getMid().getCard(index));}
	public boolean equalTens() throws GameErrorException{
		if(twoTensLocations()!=null){
			boolean[] list = twoTensLocations();
			char[] faces = new char[list.length];
			ArrayList<Integer> indexes = new ArrayList<Integer>(list.length-1);

			for(int i=0; i<list.length; i++){
				if(list[i]!=false){faces[i] = getCard(i).getFace(); indexes.add(new Integer(i));}
				else{faces[i] = 'C';}
			}
			/*
			for(int i=0; i<faces.length; i++){
				System.out.println(faces[i]+" face");
				if(i!=faces.length-1)
					System.out.println(indexes.get(i)+" index");
			}
			 */
			if(faces[indexes.get(0)] == faces[indexes.get(1)]){return true;}
			else{return false;}

		}
		throw new GameErrorException("equalTens() only works with two equal tens, no more and no less. Error #D5645");
	}
	public double middleHandSum() throws GameErrorException{return getMid().sumOfHand();}
	/* Preconditions: num is -1 or -2
	 * Postconditions: returns an int array size 2 with num in both slots
	 */ 
	private int[] conditions(int num) throws GameErrorException{
		int[] nums = new int[2];
		if(num==-1){nums[0] = -1; nums[1] = -1;} else if(num==-2){nums[0] = -2; nums[1] = -2;}
		else{throw new GameErrorException("The precondtions of this function have not been met. Error #H3187");}return nums;}
	private int[] increment(int[] original){for(int i=0; i<original.length; i++){original[i]++;}return original;}

	//make the AIs not want to create a good hand in the mid unless needed
	/**
	 * 
	 * @param m
	 * @param initCondition - from 0 to -9 - the takeTurn condition (three of a suit, three of a number) used to determine which card to remove
	 * @param subCondition
	 * @return the card that is the most optimal to remove
	 */
	public int[] determineWorstCard(MiddleHand m, int initCondition, int subCondition) {return null;}
	public int[] checkMidCards() {return null;}
	public int[] checkHandCards() {return null;}
	//the other function I wanted to create required the index of the card selected and the index of the card wanted, yet my memory is garbage and I cant remember

}
