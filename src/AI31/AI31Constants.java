package AI31;
import java.awt.Dimension;
import java.awt.Insets;
import baselineCustomClasses.PlayingCard;
/**
 * <html>
 * An interface that holds many useful constants for AI31 classes. <br>
 * All constants are static and final, meaning they should be referenced by the class name and can never be changed while running.
 * 
 * </html>
 */
public interface AI31Constants {
	/**
	 * The size of a small label. Not used often.
	 */
	public static final Dimension SMALL_LABEL_SIZE = new Dimension(300,25);
	/**
	 * The size of a minimum label. Primarily used size.
	 */
	public static final Dimension MIN_LABEL_SIZE = new Dimension(100,25);
	/**
	 * Basic, default Insets used in GridBagConstraints.
	 */
	public static final Insets LABEL_INSETS = new Insets(0,0,0,0);
	/**
	 * The game suits. Not in order. 
	 */
	public static final String[] SUITS = {"hearts", "diamonds", "spades", "clubs"};
	/**
	 * The game suits, in order. 
	 */
	public static final String[] ORDERED_SUITS = {"clubs", "diamonds", "hearts", "spades"};
	/**
	 * The face cards, in order.
	 */
	public static final char[] FACE_CARDS = {'J','Q','K','A'};
	/**
	 * Signifies that the game will be displaying life loss and elimination dialog boxes.
	 */
	public static final int LIVES_AND_ELIMINATIONS = 2;
	/**
	 * Signifies that the game will be displaying life loss, but not elimination dialog boxes.
	 */
	public static final int LIVES_NOT_ELIMINATIONS = 1;
	/**
	 * Signifies that the game will not be displaying life loss dialog boxes, but instead displaying elimination ones only.
	 */
	public static final int NO_LIVES_AND_ELIMINATIONS = 0;
	/**
	 * Signifies that the game will not display either life loss or elimination dialog boxes.
	 */
	public static final int NO_LIVES_NOR_ELIMINATIONS = -1;
	/**
	 * The options for how fast the game runs.
	 */
	public static final String[] TIMER_OPTIONS = {"Choose the speed of the game:", "0 Seconds", "0.01 Seconds", "1 Second", 
			"2 Seconds", "3 Seconds", "4 Seconds", "5 Seconds", "Choose your own timer..."};
	/**
	 * The options for what display option you want.
	 */
	public static final String[] DISPLAY_OPTIONS = {"Choose which dialog boxes you want:", "No lives nor eliminations",
			"Eliminations but no lives", "Lives but no eliminations", "Lives and eliminations"};
	/**
	 * The game mode options.
	 */
	public static final String[] MODE_OPTIONS = {"Choose the game mode:", "1: Competition (facing AIs)", "2: Friendly (facing local players)",
			"3: Combination (facing both local and AI players)", "4: Custom (choose from a variety of different setttings)"};
	/**
	 * The options for the back of the deck colors.
	 */
	public static final String[] CARD_BACK_OPTIONS = {"Choose the color of the back of the cards: ", "Blue", "Yellow", "Red", "Green",
			"Gray", "Purple", "Orange", "Brown", "Aqua", "Black"};
	/**
	 * All cards - from 2 of hearts to ace of clubs
	 */
	public static final PlayingCard[] ALL_CARDS = PlayingCard.buildAllCards();
}
