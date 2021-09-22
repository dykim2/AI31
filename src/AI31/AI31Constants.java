package AI31;
import java.awt.*;
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
	 * 
	 */
	public static final int LIVES_NOT_ELIMINATIONS = 1;
	/**
	 * 
	 */
	public static final int NO_LIVES_AND_ELIMINATIONS = 0;
	/**
	 * 
	 */
	public static final int NO_LIVES_NOR_ELIMINATIONS = -1;
	/**
	 * 
	 */
	public static final String[] TIMER_OPTIONS = {"Choose the speed of the game:", "0 Seconds", "0.01 Seconds", "1 Second", 
			"2 Seconds", "3 Seconds", "4 Seconds", "5 Seconds", "Choose your own timer..."};
	/**
	 * 
	 */
	public static final String[] DISPLAY_OPTIONS = {"Choose which dialog boxes you want:", "No lives nor eliminations",
			"Eliminations but no lives", "Lives but no eliminations", "Lives and eliminations"};
	/**
	 * 
	 */
	public static final String[] MODE_OPTIONS = {"Choose the game mode:", "1: Competition (facing AIs)", "2: Friendly (facing local players)",
			"3: Combination (facing both local and AI players)", "4: Custom (choose from a variety of different setttings)"};
	/**
	 * 
	 */
	public static final String[] CARD_BACK_OPTIONS = {"Choose the color of the back of the cards: ", "Blue", "Yellow", "Red", "Green",
			"Gray", "Purple", "Orange", "Brown", "Aqua", "Black"};
	/**
	 * 
	 */
	public static final PlayingCard[] ALL_CARDS = PlayingCard.buildAllCards();
}
