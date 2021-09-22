package AI31;
import baselineCustomClasses.GameErrorException;
public class Human extends Player {
	public Human(String name, UserInterface u){
		super(u);
		setName(name);
	}
	public int[] takeTurn() {
		getUserInterface().getPanel().updateSpecificPlayerHand();
		final Object[] options = {"SWAP", "SWAP 3 AND PASS", "PASS"};
		final Object[] cardChoices = {"Card #1", "Card #2", "Card #3"};
		String instructions = "Would you like to \nSWAP a card in your hand with a card in the middle, " +
				"\nSWAP all THREE of your cards for the middle hand/cards AND PASS, " +
				"\nor would you like to PASS?\n";
		if(getUserInterface().getTurnStatus() == -1){instructions+="FINAL TURN! Even if you don't pass, this is your last turn for the round.";}
		else{instructions+="Once you pass, the final turn starts and you no longer are able to change your hand.";}
		int[] value = new int[2];
		int decision = UserInterface.displayOptions(instructions, options, getName()+": Choose what you would like to do with your cards.");
		if(decision == 0){
			try{
				boolean met = false;
				value[0] = UserInterface.displayOptions("Which card would you like to swap out of your hand (1, 2, or 3)?", cardChoices,
						"Choose a card to get rid of...");
				value[1] = UserInterface.displayOptions("Which card from the middle do you want (1, 2, or 3)?", cardChoices,
						"Choose a card you want from the middle...");
				if(value[0]<0){throw new GameErrorException(value[0]+" is an invalid card index to remove from your hand.");}
				else if(value[1]<0){throw new GameErrorException(value[1]+" is an invalid card index to request from the mid.");}
				else{met=true; value[1]++; value[0]++;}
				if(met){return value;}
			}
			catch(GameErrorException e){UserInterface.displayException(e, 5); UserInterface.displayError("Please enter an appropiate card index."); return takeTurn();}
			catch(NumberFormatException e){UserInterface.displayException(e, 5); return takeTurn();}
			return value;
			//the card number you want to return
		}
		else if(decision == 2){
			value[0] = -1;
			value[1] = -1;
			return value;
			//final turn
		}
		else if(decision == 1){
			value[0] = -2;
			value[1] = -2;
			return value;
			//again, final turn
		}
		else if(decision == -1) {
			try{throw new GameErrorException("Please do not close out the window... Error #B5545");}
			catch(GameErrorException e) {UserInterface.displayException(e, 5);}
		}
		else {
			try{throw new GameErrorException("This should not be a choice. Error #K0645");}
			catch(GameErrorException e) {UserInterface.displayException(e, 5);}
		}
		return null;
	}
	public String toString(){
		if(!getUserInterface().isMultiPlayer()) {try{return "My cards are: "+getHand().get(0)+" (1), "
				+ ""+getHand().get(1)+" (2), and "+getHand().get(2)+" (3),\nSum: "+sumOfHand()+"\n";}
		catch(GameErrorException e){
			UserInterface.displayException(e, 5);
			throw new NullPointerException("Something went wrong - there is no hand...");
		}}
		else {
			if(getName().charAt(getName().length()-1) != 'S' || getName().charAt(getName().length()-1) != 's') {
				try{return "My ("+getName()+")'s cards are: "+getHand().get(0)+" (1), "
						+ ""+getHand().get(1)+" (2), and "+getHand().get(2)+" (3),\nSum: "+sumOfHand()+"\n";}
				catch(GameErrorException e){
					UserInterface.displayException(e, 5);
					throw new NullPointerException("Something went wrong - there is no hand...");
				}
			}
			else {
				try{return "My ("+getName()+")' cards are: "+getHand().get(0)+" (1), "
						+ ""+getHand().get(1)+" (2), and "+getHand().get(2)+" (3),\nSum: "+sumOfHand()+"\n";}
				catch(GameErrorException e){
					UserInterface.displayException(e, 5);
					throw new NullPointerException("Something went wrong - there is no hand...");
				}
			}
		}
	}

}
