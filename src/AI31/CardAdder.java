package AI31;
import java.util.*;
import baselineCustomClasses.*;
public class CardAdder {
	public static ArrayList<Card31> init(int low) throws PlayingCardException{//low 
		ArrayList<Card31> cards = new ArrayList<Card31>();
		for(int i=UserInterface.getMinCardNum(); i<11; i++){//adds 7-10 of each suit
			cards.add(new Card31(i, "clubs"));
			cards.add(new Card31(i, "hearts"));
			cards.add(new Card31(i, "spades"));
			cards.add(new Card31(i, "diamonds"));
		}
		for(int j=0; j<UserInterface.faceCards.length; j++){
			char c = UserInterface.faceCards[j];
			cards.add(new Card31(c, "clubs"));
			cards.add(new Card31(c, "hearts"));
			cards.add(new Card31(c, "spades"));
			cards.add(new Card31(c, "diamonds"));
		}
		return cards;
	}


}
