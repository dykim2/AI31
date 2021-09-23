package AI31;
import java.util.*;
import baselineCustomClasses.*;
public class CardAdder implements AI31Constants {
	/**
	 * Initializes a desk of cards, with specific limits.
	 * @return an array full of cards
	 * @throws PlayingCardException if something goes wrong setting up the playing cards
	 */
	public static ArrayList<Card31> init() throws PlayingCardException{
		ArrayList<Card31> cards = new ArrayList<Card31>();
		for(int i=UserInterface.getMinCardNum(); i<11; i++){//adds 7-10 of each suit
			cards.add(new Card31(i, "clubs"));
			cards.add(new Card31(i, "hearts"));
			cards.add(new Card31(i, "spades"));
			cards.add(new Card31(i, "diamonds"));
		}
		for(int j=0; j<FACE_CARDS.length; j++){
			char c = FACE_CARDS[j];
			cards.add(new Card31(c, "clubs"));
			cards.add(new Card31(c, "hearts"));
			cards.add(new Card31(c, "spades"));
			cards.add(new Card31(c, "diamonds"));
		}
		return cards;
	}


}
