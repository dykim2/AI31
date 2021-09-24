package AI31;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import AI31.CustomModeFrame.CustomModeInfo;
import AI31.CustomModeFrame.SpecificCardsInfo;
import baselineCustomClasses.GUISetUpException;
import baselineCustomClasses.GameErrorException;
import baselineCustomClasses.PlayingCard;
import baselineCustomClasses.PlayingCardException;
/**
 * <html>
 * This class is the class that runs all of the game information between players and components. <br>
 * Made by Dong-Yon Kim. Please do NOT claim or publish this without my written permission. <br>
 * Do you want written permission? Email me: dongyonkim5@gmail.com. <br>
 * Enjoy and use this for personal reasons as much as you want! Just don't publish it please.
 * Any questions? Email them to me and I'll take a look. Make sure to have fun...
 * <br> <br>
 * NOTE: All Javadoc information is given for global variables and methods. No Javadoc information for local variables.
 * @author dykim
 * @since 1.1
 * @version 10.1 // I just want it to work!
 * </html>
 */
public class UserInterface extends JFrame implements AI31Constants{
	/**
	 * The serial version UID. I don't know what it's for, but please do not attempt to use outdated versions.
	 */
	private static final long serialVersionUID = 23987645246L;
	/**
	 * The game cards.
	 */
	private ArrayList<Card31> cards;//the cards in the deck
	/**
	 * The game players.
	 */
	private ArrayList<Player> players;
	/**
	 * All the game's players.
	 */
	private ArrayList<Player> allPlayers;
	/**
	 * All player names.
	 */
	private ArrayList<String> names;
	/**
	 * The number of players (can include the middle hand)
	 */
	private int playerCount;//starts at 1 
	/**
	 * The number of humans
	 */
	private int humanCount;
	/**
	 * The list of possible AI names. Want me to add yours? Ask me.
	 */
	private ArrayList<String> playerNames = new ArrayList<String>();//the names of the AIs
	/**
	 * Who's turn it is. If you are playing a default game, 0 is the first computer, 1 is the second, and so on.
	 * The middle hand does not do a turn - it needs a hand.
	 */
	private int playerTurn;//0 is the first computer, 1 is the second, and so on (starts at 0)
	//After every computer, you are the next player, and then the middle hand does nothing.
	/**
	 * Is it the dealer's turn (1), the normal turn (0), or the final turn (-1)?
	 */
	private int firstTurn = 1;//first turn means a swap - 1 means initial swap or keep, 
	//0 means normal, -1 means final round
	/**
	 * Which players are currently alive for this round? A "dead" player has taken their final turn for the round.
	 */
	private ArrayList<Integer> alivePlayers;
	/**
	 * Number of played rounds.
	 */
	private int roundCount = 1;
	/**
	 * How many players are not dead (how many players have at least one life)? Used to determine standing (1st, 3rd, 7th, etc. place).
	 */
	private int alivePlayerCount;
	/**
	 * The minimum number that a card can be. For example, 7-ace: 7 is the minimum card number.
	 */
	private static int minimumCardNumber = 7;
	/**
	 * The panel of components: whatever you see on the screen is from the UserPanel.
	 */
	private UserPanel panel;
	/**
	 * The game mode - competition, friendly, or combination
	 */
	private String gameMode = "competition";
	/**
	 * Is the game a custom game?
	 */
	private boolean isCustom = false;
	/**
	 * Is the game a multiplayer game?
	 */
	private boolean multiPlayer = false;
	/**
	 * If applicable, the custom frame
	 */
	private CustomModeFrame customFrame;
	/**
	 * The custom information (the game information) for a custom game
	 */
	private CustomModeInfo customInfo;
	/**
	 * The specific cards information (what cards are available and such)
	 */
	private SpecificCardsInfo specificInfo;
	/**
	 * The starting player index
	 */
	private int startingPlayer;
	/*"friendly" means any number of people can play at the same computer with a set number of AI's
	(0 for friendly, a specifed number for combination
	- just make sure they don't look at each other's hand - the AIs will play first, then the players will play at the end - 
	the last player will start the game
	the GameErrorException class is the error that is thrown when something is not right
	 */
	/**
	 * The game timer - deals with repeating actions. This timer keeps running for multiple times when needed.
	 */
	private Timer timer;
	/**
	 * A specific timer that is used for the end of the game processes and such. This timer is stopped after running once.
	 */
	private Timer nonRepeatTimer;
	/*VVVVVVVVVV Special custom settings VVVVVVVVVV*/
	/**
	 * The names of the humans. 
	 */
	private ArrayList<String> humanPlayerNames;
	/**
	 * I don't quite remember what this was (AKA GARBAGE PROGRAMMING)
	 */
	private static boolean askedInfo = false;
	/**
	 * For singleplayer, which player location is the HUMAN PLAYER?
	 */
	private int humanPlayer;
	/**
	 * Is the starting player set or not? If not, one custom game mode will default it.
	 */
	protected boolean startingPlayerSet = false;
	/**
	 * Whether this game is a default game or not. Basically, a default game takes you straight in - randomizes AI name and player name
	 * What determines a default game? When the number of times buttons have been pressed is exactly ONE
	 */
	protected boolean defaultGame = false;
	/**
	 * How many non-timer actions?
	 */
	protected int actionCount = 0;
	/*^^^^^^^^^^ Special custom settings ^^^^^^^^^^*/
	/*VVVVVVV Constructor and construction methods VVVVVVV*/
	/**
	 * Creates the game. It's that simple.
	 * @throws PlayingCardException if something goes wrong setting up the playing cards
	 * @throws GameErrorException if something goes wrong while playing the game
	 */
	public UserInterface() throws PlayingCardException, GameErrorException{
		//setting up the ArrayLists
		super("31 v10.1");
		setUpGUI();
	}
	/** An old constructor for UserInterface, primarily for testing purposes. DO NOT USE.
	 * @deprecated
	 * @param info - this boolean means absolutely nothing at all
	 */
	public UserInterface(boolean info) {
		setUpInformation();
		customFrame = new CustomModeFrame(true);
		customInfo = customFrame.new CustomModeInfo();
		specificInfo = customFrame.new SpecificCardsInfo();
	}
	/** DO NOT USE. This method has been replaced by the sliders at the top of the screen.
	 * @deprecated As of v.4.0, this method is no longer being used.
	 * @return the number of players that you want to play with
	 */
	protected int askPlayerCount() {
		if(!askedInfo) {
			int num = 0;
			while(num <2 || num>13){//between three and eight must play
				String message = "How many players do you want to play, including yourself?" +
						"\nMinimun player count is 2 and maximum is 11 (otherwise there won't be enough cards).\n"
						+ "Don't try this with anything above nine players the first time you run this!\n"
						+ "Unless you want a ton of errors or bad plays, DON'T play with 11 players!";
				String input = displayQuestion(message, "Player Count Dialog");
				try {num = Integer.parseInt(input);}
				catch(NumberFormatException n) {displayException(n, 0);}
				if(num>9 && num<14) {minimumCardNumber-=(num-9);}//10 players - 6 and above, 12 - 4 and above, 13 - 3 and above (no more)
				if(num<=4) {minimumCardNumber = 8;}
			}
			return num;
		}
		return playerCount;
	}
	/*^^^^^^^^^^ Constructor and construction methods ^^^^^^^^^^^*/
	/*VVVVVVVVVV Using JOptionPanes VVVVVVVVVV*/
	/**
	 * Displays the question in a pop up dialog.
	 * @param body - the Object the user want to ask as a JOptionPane
	 * @param title - the title of the question
	 * @return the result of the question
	 */
	public static String displayQuestion(Object body, String title) {
		String result = JOptionPane.showInputDialog(null, body, title, JOptionPane.QUESTION_MESSAGE);
		try {if(result==null) {throw new NullPointerException("You must enter a value for the result. Error #N8437");}}
		catch(NullPointerException e) {displayException(e, 0);}
		return result;
	}
	/**
	 * Displays the question in a pop up dialog and ignores all cases of null instances.
	 * @param body - the Object the user want to ask as a JOptionPane
	 * @param title - the title of the question
	 * @return the result of the question
	 */
	public static String displayQuestionIgnoreNull(Object body, String title) {return JOptionPane.showInputDialog(null, body, title, JOptionPane.QUESTION_MESSAGE);}
	/**
	 * Displays the information in a pop up dialog with the title as "Information".
	 * @param body - the Object the user wants to display as a JOptionPane
	 * @param title - the title of the message
	 */
	public static void displayInfo(Object body) {displayInfo(body, "Information!");}
	/**
	 * Displays the information in a pop up dialog. 
	 * @param body - the Object the user wants to display as a JOptionPane
	 * @param title - the title of the message
	 */
	public static void displayInfo(Object body, String title) {
		JOptionPane.showMessageDialog(null, body, title, JOptionPane.INFORMATION_MESSAGE);}
	/**
	 * Displays the warning in a pop up dialog.
	 * @param body - the Object the user wants to warn as a JOptionPane
	 * @param title - the title of the message
	 */
	public static void displayWarning(Object body, String title) {
		JOptionPane.showMessageDialog(null, body, title, JOptionPane.WARNING_MESSAGE);}
	/**
	 * Displays the error in a pop up dialog with the title "ERROR!".
	 * @param body - the Object the user wants to display an error for as a JOptionPane
	 */
	public static void displayError(Object body) {JOptionPane.showMessageDialog(null, body, "ERROR!", JOptionPane.ERROR_MESSAGE);}
	/**
	 * <html>
	 * Displays an exception and what you normally would see in the console as a result of said exception in dialog boxes.
	 * @param e - the Exception you want to display
	 * @param location - where the Exception occured (which class?) <br>
	 * 0: UserInterface<br>
	 * 1: UserPanel<br>
	 * 2: CustomFrame<br>
	 * 3: Player<br>
	 * 4: PlayingCard, and<br>
	 * 5: Other.
	 * </html>
	 * 
	 */
	public static void displayException(Exception e, int location) {
		/*
		 * This function uses a StringWriter to obtain the information from the Exception's printStackTrace() method
		 * and a PrintWriter to ensure compatibility for the method. Then it displays the information in three dialog boxes and prints the actual stack trace..
		 */
		try{
			StringWriter s = new StringWriter();
			PrintWriter p = new PrintWriter(s);
			e.printStackTrace(new PrintWriter(s)); 
			UserInterface.displayError(e);
			UserInterface.displayError(s.toString());
			System.out.println(s.toString());
			switch(location){
			case 0:
				displayError("User Interface Error");
				break;
			case 1:
				displayError("User Panel Error");
				break;
			case 2:
				displayError("Custom Frame Error");
				break;
			case 3:
				displayError("Player Error");
				break;
			case 4:
				displayError("Playing Card Error");
				break;
			case 5:
				displayError("Miscellaneous Error");
				break;
			}
			s.close();
			p.close();
		}
		catch(IOException ex){displayException(ex, 0);}
	}
	/**
	 * Displays the Object in a pop up dialog and asks for specific information, based on the selection array.
	 * @param body - the Object the user wants to display as a JOptionPane
	 * @param selection - the option choices for the buttons of the JOptionPane
	 * @param title - the title of the message
	 * @return an integer representing the chosen option from the array
	 */
	public static int displayOptions(Object body, Object[] selection, String title) {
		return JOptionPane.showOptionDialog(null, body, title, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, selection, null);}
	/**
	 * Displays the Object in a pop up dialog and asks for specific information.
	 * @param body - the Object the user wants to display as a JOptionPane
	 * @param title - the title of the message
	 * @return an integer representing the chosen button (2 for cancel, 0 for yes, 1 for no)
	 */
	public static int displayOptions(Object body, String title) {
		return JOptionPane.showOptionDialog(null, body, title, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,	null, null);}
	/**
	 * <html>
	 * @see AI31.UserInterface#displayOptions(Object, String)
	 * Note that the conditions and return values are exactly the same as: <br>
	 * AI31.UserInterface#displayOptions(Object, String).<br>
	 * The only difference: This function's buttons are titled YES, NO, and CANCEL, not OK and CANCEL.
	 */
	public static int displayYesNo(Object body, String title) {
		return JOptionPane.showOptionDialog(null, body, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);}
	/*^^^^^^^^^^ Using JOptionPanes ^^^^^^^^^^*/
	/*VVVVVVV Creating and setting up the game VVVVVVV*/
	/**
	 * Sets up the visible UI components to use for playing the game.
	 * @throws GUISetUpException - if something goes wrong while setting up the game
	 * 
	 */
	protected void setUpGUI() {
		setUpInformation();
		panel = new UserPanel(this);
		add(panel);
		timer = new Timer(UserPanel.getSpeed(), panel);
		timer.setInitialDelay(UserPanel.getSpeed());
		nonRepeatTimer = new Timer(UserPanel.getSpeed(), panel);
		nonRepeatTimer.setInitialDelay(UserPanel.getSpeed());
		nonRepeatTimer.setRepeats(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}
	/**
	 * Sets up all necessary arrays and information for the game.
	 */
	protected void setUpInformation() {
		try {
			PlayingCard.setValueConditions(1, true, 11);
			PlayingCard.setValueConditions(0, false, 10);
			cards = new ArrayList<Card31>();
			players = new ArrayList<Player>();
			allPlayers = new ArrayList<Player>();
			alivePlayers = new ArrayList<Integer>();
			playerCount = 4;
			humanPlayerNames = new ArrayList<String>();
			names = new ArrayList<String>();
			playerTurn = playerCount-1;//you are the first player - playerCount does not include the middle hand
			//that is unlike players.size()
			if(!askedInfo) {gameMode = "Competition";}
			askedInfo = true;
			alivePlayerCount = playerCount;

		}
		catch(PlayingCardException e) {displayException(e, 0);}
	}
	/**
	 * This function adds the names of the AIs for the game to reference. The player can decide what the names will be, or can let the game decide.
	 * @param: None
	 * @author Dong-Yon
	 * 
	 */
	protected void startGame(){
		boolean errorFound = false;
		try {
			addCards();
			addAINames();
			playerTurn = playerCount-1;
			alivePlayerCount = playerCount;
			if(isCustom()) {alivePlayerCount--;}
			if(multiPlayer){startLocalMultiPlayer();}
			else {setUpSinglePlayer();}
		}
		catch(IllegalArgumentException e) {displayException(e, 0);}
		catch(Exception e) {displayException(e, 0);}
		finally{if(errorFound){displayError("What is this error?");}}

	}
	/**
	 * Sets up the game for one human player.
	 */
	protected void setUpSinglePlayer() {
		try {
			multiPlayer = false;
			humanCount = 1;
			if(names.size()==0) {
				int decision = 1;
				//decision = JOptionPane.showOptionDialog(null, "Would you like to set the AI names?", "Custom AI names!",
				//JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);
				if(decision==0) {
					while(names.size()<playerCount-1) {
						String name;
						do {
							name = displayQuestionIgnoreNull("Enter the name of an AI or"
									+ " enter -1 to quit adding AI names.", "Choose an AI name!");
							if(name == null || name.equals("") || name.equals("-1")) {break;}
							else if(checkAI(name)) {names.add(name);}
							else {displayError("Please do not enter a duplicate AI name.");}
						}
						while(!checkAI(name));
						if(name == null || name.equals("") || name.equals("-1")) {break;}
						if(names.get(names.size()-1).equals("-1")) {names.remove(names.size()-1); break;}
						else {players.add(new Machine(names.size()-1, this, names.get(names.size()-1)));}
					}
					for(int i=names.size(); i<playerCount-humanCount; i++) {/*player count minus one is the human, names.size() is the last name not added*/
						players.add(new Machine(i, this, generateName()));}
				}
				else {for(int i=0; i<playerCount-1; i++){players.add(new Machine(i, this, generateName()));}}
			}
			else {for(int i=0; i<playerCount-1; i++){players.add(new Machine(i, this, names.get(i)));}}

			displayInfo("31 is a game of swapping cards to gain the highest value in cards." +
					"\nYou will be facing "+(playerCount-1)+" highly skilled AI(s)!"
					+ "\nSee the rules on the left for how to play.", "Welcome to 31!");
			int d = displayYesNo("Click 'Yes' to make your player an AI and 'No' to play as a human\n"
					+ "(good for those players new to the game or those who like spectator sports).",
					"Do you want to be represented by one of my AIs?");
			String humanName;
			boolean checked = true;
			do {
				humanName = displayQuestion("May we request your name?", "Enter your name:");
				if(!checkAI(humanName)) {displayError("Please do not enter a used AI name for the human.");}
				if(humanName.equals("")) {checked = false;}
				else {checked = true;}
			}
			while(!checkAI(humanName) || !checked);
			if(d==JOptionPane.NO_OPTION) {players.add(new Human(humanName, this));}
			else {players.add(new Machine(players.size(), this, humanName));}
			players.get(players.size()-1).setStarting(true);
			startingPlayer = players.size()-1;
			players.add(new MiddleHand(this));
			allPlayers.addAll(players);
			dealCards();
			displayWarning("The game might run slow, so be patient!", "Patience is key!");
			panel.addPlayers();
			deal();
			humanPlayer = players.size()-2;
		}
		catch(GameErrorException e) {displayException(e, 0);}
		catch(Exception e) {displayException(e, 0);}
	}
	/** Sets up the game for multiple human players.
	 *	
	 */
	protected void startLocalMultiPlayer(){
		try {
			multiPlayer = true;
			if(gameMode.equalsIgnoreCase("friendly")) {
				humanCount = playerCount;
				for(int i=0; i<playerCount; i++){
					String name;
					do {
						name = displayQuestionIgnoreNull("Please enter the name of a human player or -1 to randomize this human name\n" +
								"Note that leaving the field blank is equivalent to entering -1.", "Human names needed!");
						if(name!=null && !name.equals("") && !name.equals("-1")) {
							if(checkName(name)) {humanPlayerNames.add(name); break;}
							else {displayError("Please do not enter a duplicate human name.");}
						}
						else {humanPlayerNames.add(generateName()); panel.addText("Human Player #"+i+": "+humanPlayerNames.get(humanPlayerNames.size()-1)+"\n");}
					}
					while(!checkName(name));
				}
				for(int i=0; i<playerCount; i++) {players.add(new Human(humanPlayerNames.get(i), this));}
			}
			else if(gameMode.equalsIgnoreCase("combination")){
				for(int i=0; i<humanCount; i++){
					String name;
					do {
						name = displayQuestionIgnoreNull("Please enter the name of a human player or -1 to randomize this human name\n" +
								"Note that leaving the field blank is equivalent to entering -1.", "Human names needed!");
						if(name!=null && !name.equals("") && !name.equals("-1")) {
							if(checkName(name)) {humanPlayerNames.add(name); break;}
							else {displayError("Please do not enter a duplicate human name.");}
						}
						else {humanPlayerNames.add(generateName()); panel.addText("Human Player #"+i+": "+humanPlayerNames.get(humanPlayerNames.size()-1)+"\n");}
					}
					while(!checkName(name));
				}
				int decision = JOptionPane.showOptionDialog(null, "Would you like to set the AI names?", "Custom AI names!",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);
				if(decision==0) {
					while(names.size()<playerCount-humanCount) {
						String name;
						do {
							name = displayQuestionIgnoreNull("Enter the name of an AI or"
									+ " enter -1 to quit adding AI names.", "Choose an AI name!");
							if(name == null || name.equals("") || name.equals("-1")) {break;}
							else if(checkAI(name)) {names.add(name);}
							else {displayError("Please do not enter a duplicate AI name.");}
						}
						while(!checkAI(name));
						if(name == null || name.equals("") || name.equals("-1")) {break;}
						if(names.get(names.size()-1).equals("-1")) {names.remove(names.size()-1); break;}
						else {players.add(new Machine(names.size()-1, this, names.get(names.size()-1)));}
					}
					/*for(int i=0; i<names.size(); i++) {players.add(new Machine(i, this, names.get(i)));}*/
					for(int i=names.size(); i<playerCount-humanCount; i++) {/*player count minus one is the human, names.size() is the last name not added*/
						players.add(new Machine(i, this, generateName()));}
					for(int i=0; i<humanCount; i++) {players.add(new Human(humanPlayerNames.get(i), this));}
				}
				else {for(int i=0; i<playerCount-humanCount; i++){players.add(new Machine(i, this, generateName()));} 
				for(int i=0; i<humanCount; i++) {players.add(new Human(humanPlayerNames.get(i), this));}}
				displayInfo("31 is a game of swapping cards to gain the highest value in cards." +
						"\nYou will be facing "+(playerCount-humanCount)+" highly skilled AI(s) and "+(humanCount-1)+ " human player(s)!"
						+ "\nSee the rules on the left for how to play.", "Welcome to 31!");
			}

			players.get(players.size()-humanCount).setStarting(true);
			startingPlayer = players.size()-humanCount;
			playerTurn = players.size()-humanCount;
			players.add(new MiddleHand(this));
			panel.addText("31 is a game of swapping cards to gain the highest value in cards.\n");
			if(gameMode.equalsIgnoreCase("combination")){panel.addText("You will be facing "+(playerCount-humanCount)+" highly skilled AI(s) "
					+ "and "+(humanCount-1)+" human player(s)!\n");}
			else {panel.addText("You will be facing "+(humanCount-1)+" human player(s)!\n"); displayInfo("31 is a game of swapping cards to gain the highest value in cards. "
					+ "\nYou will be facing "+(humanCount-1)+" human player(s)!\nSee the rules on the left for how to play.", "Welcome to 31!");}
			dealCards();
			allPlayers.addAll(players);
			displayWarning("The game might run slow, so be patient!", "Patience is key!");
			panel.playerInfo.setText(players.get(playerTurn).getName()+"'s cards:");
			panel.updateSpecificPlayerHand();
			deal();
		}
		catch(Exception e) {displayException(e, 0);}
	}
	/**
	 * Creates the CustomModeFrame to obtain information on how to set the game up (special settings?).
	 */
	protected void startCustom() {customFrame = new CustomModeFrame(this);}
	/**
	 * Sets up custom mode, where you can change almost anything but the size of each player's hand.
	 */
	protected void setUpCustom(){
		boolean errorFound = false;
		try {
			panel.changeStatus(-5, false);
			isCustom = true;
			customInfo = customFrame.getCustomInfo();
			panel.addCustomText(customInfo.toString()+"\n\n");
			panel.firstCustomSelected = false;
			specificInfo = customFrame.getSpecificCardInfo();
			panel.addCustomText(specificInfo.toString()+"\n\n");
			playerCount = customInfo.playerCt;//one extra player for the mid
			humanCount = customInfo.humanCt;
			if(playerCount-1==humanCount) {gameMode = "friendly"; multiPlayer = true;}
			else if(humanCount==1) {gameMode = "competition"; humanPlayer = customInfo.singlePlayerLoc; multiPlayer = false;}
			else {gameMode = "combination"; multiPlayer = true;}
			PlayingCard.setValueConditions(PlayingCard.CHANGE_ACE, true, customInfo.aceVal);	
			Player.setLifeCounter(customInfo.lifeCt);
			addAINames();
			alivePlayerCount = playerCount;
			minimumCardNumber = customInfo.lowNum;
			PlayingCard.setValueConditions(PlayingCard.CHANGE_JACK, !customInfo.equalFaceCards, customInfo.jackVal);
			if(!customInfo.useTextArea) {panel.changeStatus(-8, false);}
			//human player count must equal the specific player locations.
			addCustomPlayers();
			startingPlayer = customInfo.startingPlayerLoc;
			playerTurn = customInfo.startingPlayerLoc;
			System.out.println(customInfo.startingPlayerLoc+": Set Up Custom starting player");
			players.add(new MiddleHand(this));
			allPlayers.addAll(players);
			addCards();
			dealCards();
			displayWarning("The game might run slow, so be patient!", "Patience is key!");
			panel.addPlayers();
			deal();
		}
		catch(GameErrorException e){displayException(e, 0);}
		catch(PlayingCardException e){displayException(e, 0);}
		catch(IllegalArgumentException e) {displayException(e, 0);}
		finally{if(errorFound){displayError("What is this error?");}}

	}
	/**
	 * Sets up the default game. You (the player) have the name Player and the Ai names are random.
	 */
	protected void setUpDefaultGame() {
		try {
			addCards();
			addAINames();
			playerCount = 4;
			playerTurn = 3;
			Player.setLifeCounter(5);
			alivePlayerCount = 4;
			for(int i=0; i<3; i++) {players.add(new Machine(i, this, generateName()));}
			players.add(new Human("Player", this));
			players.add(new MiddleHand(this));
			players.get(3).setStarting(true);
			humanPlayer = 3;
			startingPlayer = 3;
			dealCards();
			allPlayers.addAll(players);
			panel.updatePlayerHand();
			panel.addPlayers();
			deal();
		}
		catch(GameErrorException e) {displayException(e, 0);}
		catch(Exception e) {displayException(e, 0);}
	}
	/**
	 * Essentially sets up the players in a custom settings game.
	 */
	protected void addCustomPlayers() {
		if(multiPlayer){
			if(gameMode.equalsIgnoreCase("Friendly")) {
				for(int i=0; i<playerCount-1; i++){
					String name;
					do {
						name = displayQuestionIgnoreNull("Enter the name of a human player or -1 to randomize the name.", "Custom human name choice!");
						if(name!=null && !name.equals("") && !name.equals("-1")) {
							if(checkName(name)) {humanPlayerNames.add(name); break;}
							else {displayError("Please do not enter a duplicate human name.");}
						}
						else {name = generateName(); humanPlayerNames.add(name); break;}
					}
					while(!checkName(name));
				}
				for(int i=0; i<playerCount-1; i++) {players.add(new Human(humanPlayerNames.get(i), this));}
				displayInfo("31 is a game of swapping cards to gain the highest value in cards." +
						"\nYou will be facing "+(humanCount-1)+" human player(s)!"
						+ "\nSee the rules on the left for how to play.", "Welcome to 31!");
				if(!startingPlayerSet) {startingPlayer = playerCount-2;}
			}
			else if(gameMode.equalsIgnoreCase("Combination")) {
				if(customInfo.multiPlayerLoc.size() < customInfo.humanCt) {
					for(int i=customInfo.humanCt-1; i>-1; i--) {
						if(!customFrame.find(customInfo.multiPlayerLoc, new Integer(i))){customInfo.multiPlayerLoc.add(i);}//not found - box not selected
						if(customInfo.multiPlayerLoc.size() == customInfo.humanCt) {break;}
					}
				}
				int decision = JOptionPane.showOptionDialog(null, "Would you like to set the AI names?", "Custom AI names!",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);
				for(int i=0; i<playerCount-1; i++){//dont account for the mid hand
					String name;
					if(customFrame.find(customInfo.multiPlayerLoc, i)) {
						do {
							name = displayQuestionIgnoreNull("Enter the name of a human player or -1 to randomize the name.", "Custom human name choice!");
							if(name!=null &&!name.equals("") && !name.equals("-1")) {
								if(checkName(name)) {humanPlayerNames.add(name); break;}
								else {displayError("Please do not enter a duplicate human name.");}
							}
							else {name = generateName(); break;}
						}
						while(!checkName(name));
						players.add(new Human(name, this));
					}
					else {
						if(decision==0) {
							do {
								name = displayQuestionIgnoreNull("Enter the name of an AI or"
										+ " -1 to randomize the name.", "Choose an AI name!");
								if(name == null || name.equals("") || name.equals("-1")) {break;}
								else if(checkAI(name)) {names.add(name);}
								else {displayError("Please do not enter a duplicate AI name.");}
							}
							while(!checkAll(name));
							if(name == null || name.equals("") || name.equals("-1")) {names.add(generateName());}
							players.add(new Machine(names.size()-1, this, names.get(names.size()-1)));

							/*for(int i=0; i<names.size(); i++) {players.add(new Machine(i, this, names.get(i)));}*/

						}
						else {players.add(new Machine(i, this, generateName()));}
					}
				}
				displayInfo("31 is a game of swapping cards to gain the highest value in cards." +
						"\nYou will be facing "+(playerCount-humanCount-1)+" highly skilled AI(s) and "+(humanCount-1)+ " human player(s)!"
						+ "\nSee the rules on the left for how to play.", "Welcome to 31!");
			}
		}
		else {
			String name = "";
			int decision = JOptionPane.showOptionDialog(null, "Would you like to set the AI names?", "Custom AI names!",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);
			for(int i=0; i<playerCount-1; i++) {
				if(i==customInfo.singlePlayerLoc) {
					int decision1 = JOptionPane.showOptionDialog(null, "Would you like to set your human name?", "Custom human name choice!",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);;
							if(decision1==JOptionPane.YES_OPTION) {
								do {
									name = displayQuestion("Enter the name of the human player or -1 to randomize the name.", "Selecting your name.");
									if(name.equals("") || name.equals("-1")) {break;}
									else if(!checkAll(name)) {displayError("Please do not enter a duplicate human name.");}
								}
								while(!checkAll(name));
								if(name.equals("") || name.equals("-1")) {name = generateName(); panel.addText("Welcome to 31! Your name is: "+name+".\n");}
							}
							else {name = generateName(); panel.addText("Welcome to 31! Your name is: "+name+".\n");}
							players.add(new Human(name, this));
				}
				else {
					if(decision==JOptionPane.YES_OPTION) {
						do {
							name = displayQuestion("Enter the name of an AI player or -1 to randomize the name.", "Selecting AI names.");
							if(name.equals("") || name.equals("-1")) {break;}
							else if(!checkAll(name)) {displayError("Please do not enter a duplicate AI name.");}
						}
						while(!checkAll(name));
						if(name.equals("") || name.equals("-1")) {name = generateName();}
					}
					else {name = generateName();}
					players.add(new Machine(i, this, name));
				}
			}
			displayInfo("31 is a game of swapping cards to gain the highest value in cards." +
					"\nYou will be facing "+(playerCount-2)+" highly skilled AI(s)!"
					+ "\nSee the rules on the left for how to play.", "Welcome to 31!");
			int d = displayYesNo("Click 'Yes' to make your player an AI and 'No' to play as a human\n"
					+ "(good for those players new to the game or those who like spectator sports).",
					"Do you want to be represented by one of my AIs?");
			if(d==JOptionPane.YES_OPTION) {
				for(int i=0; i<players.size(); i++) {
					if(players.get(i)!=null && players.get(i).getClass().getName().equals("AI31.Human")) {
						Player p = players.remove(i); 
						players.add(i, new Machine(i, this, p.getName()));
					}
				}
			}
		}
		playerTurn = startingPlayer;
	}
	/**
	 * Ensures no duplicate human names exist. Enter a name for this to check and it will return whether the name is used or not (through human names).
	 * @param name - the name you want to check
	 * @return true if the name is not used, false if it is used
	 */
	protected boolean checkName(String name) {
		for(String n : humanPlayerNames) {if(name.equals(n)) {return false;}}
		return true;
	}
	/**
	 * Ensures no duplicate human names exist. Enter a name for this to check and it will return whether the name is used or not (through AI names).
	 * @param name - the name you want to check
	 * @return true if the name is not used, false if it is used
	 */
	protected boolean checkAI(String name) {
		for(Player p : players) {if(p!=null && name.equals(p.getName())) {return false;}}
		return true;
	}
	/**
	 * Essentially a combination of the methods checkName(String) and checkAI(name). Ensures no duplicate names exist. 
	 * Enter a name for this to check and it will return whether the name is used or not.
	 * @param name - the name you want to check
	 * @return true if the name is not used, false if it is used
	 */
	protected boolean checkAll(String name){
		if(!checkAI(name)) {return false;}
		if(!checkName(name)) {return false;}
		return true;
	}
	/**
	 * Generates a random name from the AI names and checks to ensure no duplicate names.
	 * @return a selected name from the ArrayList of player names
	 */
	protected String generateName(){//no longer generates duplicate names
		boolean found;
		while(true) {
			found = true;
			int loc = (int)((playerNames.size()*Math.random()));
			String name = playerNames.remove(loc);
			for(String playerName : humanPlayerNames) {if(name.equals(playerName)) {found = false;}}
			if(!checkAll(name)) {found = false;}
			if(found) {return name;}
			else {playerNames.add(loc, name);}
		}
	}
	/**
	 * The preset possible names of the AIs. Did I miss one? Do you want one added? Email me. (If you know my number, you can text)
	 */
	protected void addAINames(){//gives the AIs generic names (or some to my friends),
		//so that the game has some people to reference. Why not?
		playerNames.add("George"); playerNames.add("Brian"); playerNames.add("Adam"); playerNames.add("John");
		playerNames.add("Daniel"); playerNames.add("Jessica"); playerNames.add("Katie"); playerNames.add("Mark");
		playerNames.add("Ryan"); playerNames.add("Colin"); playerNames.add("Kelly"); playerNames.add("Christopher");
		playerNames.add("Ashley"); playerNames.add("Rachel"); playerNames.add("Elizabeth"); playerNames.add("Emily");
		playerNames.add("Abigail"); playerNames.add("Sara"); playerNames.add("David"); playerNames.add("Joshua");
		playerNames.add("Samantha"); playerNames.add("Hannah"); playerNames.add("Lauren"); playerNames.add("James");
		playerNames.add("Taylor"); playerNames.add("Benjamin"); playerNames.add("Jackson"); playerNames.add("Michael");
		playerNames.add("Stephanie"); playerNames.add("Emma"); playerNames.add("Gavin"); playerNames.add("Nathan");
		playerNames.add("Madison"); playerNames.add("Liam"); playerNames.add("Robert"); playerNames.add("Sophia");
		playerNames.add("Olivia"); playerNames.add("Isabella"); playerNames.add("Isabel"); playerNames.add("Evan");
		playerNames.add("Alex"); playerNames.add("Kayla"); playerNames.add("Audrey"); playerNames.add("Frank");
		//below are my friends names, why not? 
		playerNames.add("Hayk"); playerNames.add("Elise"); playerNames.add("Conall"); playerNames.add("Logan");
		playerNames.add("Marina"); playerNames.add("Fortune David"); playerNames.add("Grayson");
		playerNames.add("Dong-Yon"); //playerNames.add("Kaiya"); playerNames.add("Moiz");
		playerNames.add("Mika"); playerNames.add("Clay");
		//- will double check tomorrow
		//that's 40 possible names for the AIs - 20 girls, 20 guys I believe
		//an additional 5 guys and 5 girls for my friends, whether now or former
		//I need more names to avoid consistent repetition - goal is 50 total, including like ten slots for friends
	}
	/**
	 * Adds the life status to the UI and calls nonRepeatTimer to run once 
	 * (after the game speed amount of time has passed, the round will begin).
	 * 
	 * @throws GameErrorException
	 */
	protected void deal() throws GameErrorException{
		panel.changeMidSum(-1);
		panel.addText("\nRound #"+roundCount+":\n\n");
		panel.addText("Lives count: \n");
		System.out.println("Starting player: "+startingPlayer);
		if(gameMode.equalsIgnoreCase("competition")) {System.out.println("Human player: "+humanPlayer);}
		for(int i=0; i<players.size()-1; i++){if(players.get(i)!=null) {panel.addText(players.get(i).getLifeString()+"\n");}} 
		panel.updateTimerCounter(0);
		nonRepeatTimer.restart();
	}
	/**
	 * Continues the deal process and prepares the alive players for the round.
	 */
	protected void dealAgain() {
		try {
			if(!multiPlayer) {for(Player p : players) {if(p!=null && p.getClass().getName().equals("AI31.Human")) {panel.addText(p+"\n");}}}
			for(int i=0; i<players.size()-1; i++){if(players.get(i)!=null) {alivePlayers.add(new Integer(i));}}
			if(gameMode.equalsIgnoreCase("competition")){panel.updatePlayerHand();}
			else{panel.updateSpecificPlayerHand();}
			//for(int i=0; i<players.size(); i++) {displayInfo(i+": ("+players.get(i).getName()+"): "+players.get(i).getHand()+" Sum: "+players.get(i).sumOfHand());}
			if(firstTurn==1) {doFirstTurn();}
			nextPlayer();
		}
		catch(Exception e) {displayException(e, 0);}
	}
	/**
	 * Quite frankly calls for each player's turns and detects when the final player of the round has taken their turn, hence the name "doAITurns".
	 */
	protected void doAITurns() {
		if(alivePlayers.size()==0) {timer.stop();}
		else {
			timer.restart();
			panel.updateTimerCounter(-1);
		}
	}
	/**
	 * Does the first turn, the dealer's turn. For more information...
	 * @see AI31.Machine#doDealersTurn()
	 * @see #doFirstHumanTurn()
	 */
	protected void doFirstTurn(){
		try{
			displayInfo(players.get(playerTurn).getName()+" ("+playerTurn+") is the starting player for round #"+roundCount+".", "Who starts round #"+roundCount+"?");
			if(players.get(playerTurn).getClass().getName().equals("AI31.Human")) {doFirstHumanTurn();}
			else{

				if(((Machine) players.get(playerTurn)).doDealersTurn() == 1){
					swapWithMiddle();
					panel.addText(players.get(playerTurn).getName()+": swap 3.\n");
					displayInfo(players.get(playerTurn).getName()+": swap 3.\n");
				}
				else {panel.addText(players.get(playerTurn).getName()+": I want to keep the cards I was dealt.\n");
				displayInfo(players.get(playerTurn).getName()+": I want to keep the cards I was dealt.\n");}
				if(gameMode.equalsIgnoreCase("competition")){panel.updatePlayerHand();}
			}

			panel.addText(players.get(players.size()-1)+"\n\n");
			panel.changeStatus(0, true);
			panel.changeStatus(-1, false);
			panel.changeStatus(-2, false);
			panel.changeStatus(-3, true);
			panel.changeStatus(-4, true);
			moveTurnCounter();
			panel.updateMiddleHand();
			doAITurns();
		}
		catch(ClassCastException e) {displayException(e, 0);}
		catch(Exception e) {displayException(e, 0);}
	}
	/**
	 * Does the dealer's turn for a human: Do you want to keep your cards or swap them with the middle three?
	 * This does NOT trigger final turn.
	 */
	protected void doFirstHumanTurn() {
		//playerTurn == players.size()-2
		try {
			while(true){
				int n = JOptionPane.showOptionDialog(null, players.get(playerTurn).getName()+": Look at your cards in the top right of the screen.\n"+
						"Would you like to swap your hand for the middle hand (yes or no)?", "Would you like to swap your hand for the middle hand (yes or no)?", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 1);

				if(n==JOptionPane.YES_OPTION){//yes
					String spd = "You decided to swap your dealt hand with the middle hand.\n";
					String mpd = players.get(playerTurn).getName()+" has decided to swap their dealt hand with the middle hand.\n";
					if(!multiPlayer) {panel.addText(spd); displayInfo(spd);}
					else {panel.addText(mpd); displayInfo(mpd);}
					swapWithMiddle();
					if(isMultiPlayer()) {panel.updateSpecificPlayerHand();}
					else {panel.updatePlayerHand();}
					break;
				}
				else{//no
					String spd = "You have decided to keep the cards you were dealt.\n";
					String mpd = players.get(playerTurn).getName()+" has decided to keep the cards they were dealt.\n";
					if(!multiPlayer) {panel.addText(spd); displayInfo(spd);}
					else {panel.addText(mpd); displayInfo(mpd);}
					break;
				}
			}
		}
		catch(Exception e) {displayException(e, 0);}
	}
	/*^^^^^^^^^^ Setting up the game ^^^^^^^^^^*/
	/*VVVVVVVVVV Processing the player's turns VVVVVVVVVV*/
	/**
	 * Each round of gameplay is essentially... one player's turn. Except exceptions are caught.
	 * @throws GameErrorException - if a PlayingCardException is thrown
	 */
	protected void gameplay() throws GameErrorException {//each run of gameplay is a turn of a player but with caught exceptions
		if(!gameMode.equalsIgnoreCase("competition")) {panel.clearPlayerHand();}
		try{if(players.get(playerTurn)!=null){processTurns();}}
		catch(PlayingCardException e){
			displayException(e, 0);
			try {
				throw new GameErrorException("Something went wrong swapping cards.","P8732");
			}
			catch(GameErrorException ex) {displayException(ex, 0);}
		}
		catch(GameErrorException e) {displayException(e, 0);}
		catch(Exception e) {displayException(e, 0);}
	}
	/**
	 * Essentially processes the turns of each player (takes care of and organizes the results of each player's turn)
	 * @throws GameErrorException - if something =goes wrong passing the player's turn or if the player does not make a decision
	 * @throws PlayingCardException
	 */
	protected void processTurns() throws GameErrorException, PlayingCardException{
		//this function does the regular turns
		while(players.get(playerTurn) == null) {System.out.println("Null player");nextPlayer();}
		int[] decisionMatrix = players.get(playerTurn).takeTurn();//1, 2, or 3
		int decision = decisionMatrix[0];//1, 2, or 3
		int decisiontwo = decisionMatrix[1];//1, 2, or 3
		/*
		 * NOTE: 
		 */
		panel.addText(decision+", "+decisiontwo+"\n");
		if(decision>0){
			/* swap */
			/* get the player's specified card, 1, 2, or 3*/
			Card31 obtain = new Card31(players.get(playerTurn).getCard(decision-1));
			players.get(playerTurn).removeCard(obtain);
			Card31 next = new Card31(players.get(players.size()-1).getCard(decisiontwo-1));
			/*get the middle hand's specified card, 0, 1, or 2*/
			players.get(players.size()-1).removeCard(next);
			players.get(playerTurn).addCardAtSlot(next, decision-1);
			players.get(players.size()-1).addCardAtSlot(obtain, decisiontwo-1);
			panel.addText(players.get(playerTurn).getName()+": SWAP "+next+" and "+obtain+"\n");
		}
		else{
			if(decision == -2){
				/*swap 3 and pass*/
				swapWithMiddle();
				panel.addText(players.get(playerTurn).getName()+": SWAP 3 and PASS\n");}
			else if(decision == -1){
				/* pass */
				panel.addText(players.get(playerTurn).getName()+": PASS\n");}
			else if(decision==0){throw new GameErrorException("A decision must be made. Please try again.", "O5328");}
			else{/* This should not run, but if it does, I will throw an exception */
				throw new GameErrorException("An error occured trying to pass.", "O7742");}
			moveTurnCounter();
		}
		panel.addText(players.get(players.size()-1)+"\n");
		if(!multiPlayer){panel.updatePlayerHand();}
		else if(players.get(playerTurn).getClass().getName().equals("AI31.Human")) {panel.updateSpecificPlayerHand();}
		//update the player hand at the end
		panel.updateMiddleHand();
		nextPlayer();
	}
	/**
	 * Prints each player's hand and prepares for the end of the round.
	 */
	protected void endOfRound() {
		panel.addText("\nFINAL HANDS:\n\n");
		timer.restart();
		panel.updateTimerCounter(3);
	}
	/**
	 * Removes the lives from the players with the lowest hands, removes the dead players,
	 * and organizes the system for the next starting player.
	 * It also determines whether the game is over or not.
	 * It is the hardest method to code, the most buggy, and the most essential. How wonderful.
	 */
	protected void finishEndOfRound() {
		try {
			ArrayList<Player> newCopy = new ArrayList<Player>(players);
			for(Iterator<Player> playerIt = newCopy.iterator(); playerIt.hasNext();){if(playerIt.next() == null){playerIt.remove();}}
			alivePlayerCount = newCopy.size()-1;
			boolean gameOver = false;
			double[] handSums = new double[players.size()-1];
			for(int i=0; i<players.size()-1; i++){if(players.get(i)!=null) {handSums[i] = players.get(i).sumOfHand();}else{handSums[i] = 100;}}
			double minSum = 100.0;
			for(int i=0; i<players.size()-1; i++){if(handSums[i] < minSum){minSum = handSums[i];}}
			ArrayList<Player> livesLostPlayers = new ArrayList<Player>();
			for(int i=0; i<players.size()-1; i++){if(minSum == handSums[i]){livesLostPlayers.add(players.get(i));}}
			for(int i=0; i<livesLostPlayers.size(); i++){
				livesLostPlayers.get(i).removeLife();
				if(panel.getEliminationStatus() == UserPanel.LIVES_AND_ELIMINATIONS || panel.getEliminationStatus() == UserPanel.LIVES_NOT_ELIMINATIONS) {
					if(livesLostPlayers.get(i).getClass().getName().equals("AI31.Human") && !multiPlayer) {
						if(players.get(findPlayer(livesLostPlayers.get(i))).getLives()!=1) {displayInfo("You currently have "+players.get(findPlayer(livesLostPlayers.get(i))).getLives()+" lives remaining.",
								"You have lost a life this round.");}
						else {displayInfo("You currently have 1 life remaining.",
								"You have lost a life this round.");}
					}
					else{
						if(livesLostPlayers.get(i).getLives()!=1) {displayInfo(livesLostPlayers.get(i).getName()+": I have "+livesLostPlayers.get(i).getLives()
								+ " lives remaining.", livesLostPlayers.get(i).getName()+" has lost a life this round.");}
						else {displayInfo(livesLostPlayers.get(i).getName()+": I have 1 life remaining.",
								livesLostPlayers.get(i).getName()+" has lost a life this round.");}
					}
				}
				panel.addText(livesLostPlayers.get(i).getName()+" has lost a life this round.\n");
				if(livesLostPlayers.get(i).getLives() == 0){
					panel.addText(livesLostPlayers.get(i).getName()+": I am eliminated.\n");
					alivePlayerCount--;
					if(panel.getEliminationStatus() == UserPanel.LIVES_AND_ELIMINATIONS || panel.getEliminationStatus() == UserPanel.NO_LIVES_AND_ELIMINATIONS) {
						if(alivePlayerCount!=1) {
							displayInfo("There are "+alivePlayerCount+" alive players remaining.", livesLostPlayers.get(i).getName()+" has been eliminated.");}
						else{displayInfo("There is 1 alive player remaining.", livesLostPlayers.get(i).getName()+" has been eliminated.");}
					}
					int dead = findPlayer(livesLostPlayers.get(i));
					players.get(dead).die();
				}
				boolean humansAlive = false;
				for(int k=0; k<players.size(); k++) {
					if(players.get(k)!=null) {
						if(!players.get(k).isAlive() && multiPlayer && players.get(k).getClass().getName().equals("AI31.Human")) {
							int ct = alivePlayerCount+1;
							//one subtracted when eliminated, add one to represent placement
							String print = players.get(k).getName()+" has won ";
							if(ct==2 || ct==1){print+="2nd place.";}
							else if(ct==3){print+="3rd place.";}
							else{print+=ct+"th place.";}
							displayInfo(print, players.get(k).getName()+" has been eliminated.");
						}
					}
				}
				if(!multiPlayer && players.get(humanPlayer).getLives()==0 && players.size()>1){
					int ct = alivePlayerCount+1;
					String print = "You have won ";
					if(ct==2 || ct==1){print+="2nd place.";}
					else if(ct==3){print+="3rd place.";}
					else{print+=ct+"th place.";}
					print+=" Want to play again?";
					int s = displayYesNo(print, "You have been eliminated.");
					if(s==0){gameOver = true; playerNames.clear(); new UserInterface();}else{System.exit(0);} break;}
				else if(!multiPlayer && players.get(humanPlayer).getLives() == 0 && players.size() == 1){}
				if(players.isEmpty()){displayInfo("You have won tied 1st place. All players have been eliminated.");}
				if(multiPlayer) {
					for(int k=0; k<players.size()-1; k++) {if(players.get(k)!=null) {
						if(players.get(k).getClass().getName().equals("AI31.Human") && players.get(k).getLives()!=0) {humansAlive = true; break;}}}
				}
				if(!humansAlive && multiPlayer) {
					int n = displayYesNo("Would you like to play another game?", "All human players have been eliminated.");
					if(n==JOptionPane.YES_OPTION) {gameOver = true; playerNames.clear(); new UserInterface();}
					else {System.exit(0);}
				}
			}

			/*
			 * Finds the current starting player and checks if the next player is alive. If not, the next alive player will be the new starting player.
			 * Starting player pseudocode:
			 * if a player is starting
			 * 	set them to no longer start
			 * 	if the next player is alive
			 * 		set the next player to be starting
			 * 	else if someone is alive
			 * 		set the next alive player to be starting
			 * 	else
			 * 		throw an exception stating that no players are alive
			 *  
			 */
			/*
			for(int i=0; i<players.size(); i++) {
				if(players.get(players.size()-1).isStarting()) {
					if(players.get(0).isAlive()) {players.get(0).setStarting(true);}
					else {for(int k=0; k<players.size(); k++) {if(players.get(k).getLives()>0) {if(players.get(k).setStarting(true)) {break;}}}}
				}
				else if(players.get(i).isStarting()) {
					if(players.get(i+1).isAlive()) {players.get(i+1).setStarting(true);}
					else {
						for(int k=i+1; k<players.size(); k++) {
							if(players.get(k).isAlive()) {players.get(k).setStarting(true); break;}
							if(k==players.size()-1) {k = -1;}
							else if(k==i) {throw new GameErrorException("No players are alive.");}
						}
					}
					players.get(i).setStarting(false);
				}
			}
			 */
			/*
			ArrayList<Player> playerOrder = new ArrayList<Player>(players);
			int startPlayer = 0;
			for(int i=0; i<players.size(); i++) {if(players.get(i).getStarting()) 
			{startPlayer = i; players.get(i).setStarting(false); break;}}
			for(int i=0; i<startPlayer; i++) {playerOrder.add(playerOrder.remove(0));}
			//shifts the elements to the back in the original order
			for(int i=0; i<playerOrder.size(); i++) {System.out.println(i+": "+playerOrder.get(i).getName());}
			for(int i=1; i<playerOrder.size(); i++) {if(playerOrder.get(i).isAlive()) {playerOrder.get(i).setStarting(true); break;}
			if(i==playerOrder.size()-1) {throw new GameErrorException("No players are alive, seemingly. Error #X4444");}}
			 */
			/*
			for(int i=0; i<players.size()-1; i++) {
				if(players.get(i).getStarting()) {
					players.get(i).setStarting(false);
					int k = i+1;
					if(k==players.size()-1) {k=0;}
					boolean found = false;
					for(int j=k; j<players.size()-1; j++) {if(allPlayers.get(j).isAlive()){allPlayers.get(j).setStarting(true); found = true; break;}
					if(j==allPlayers.size()-2) {j=-1;} if(j==i) {throw new GameErrorException("No players are alive, seemingly. Error #X4444");}}
					if(found) {break;}
				}
			}
			 */
			boolean found = false;
			ArrayList<Player> pCopy = new ArrayList<Player>(players);
			for(int i=pCopy.size()-1; i>-1; i--) {
				if(pCopy.get(i)!=null && pCopy.get(i).getLives() == 0) {
					int pla = findPlayer(pCopy.get(i));
					players.remove(pla); 
					players.add(pla, null);
				}
			}
			boolean startAlive = true;
			if(players.get(startingPlayer) == null) {startAlive = false;}
			for(int i=startingPlayer+1; i<players.size()-1; i++) {if(players.get(i)!=null) {found = true; startingPlayer = i; break;}}
			if(!found) {for(int i=0; i<startingPlayer; i++) {if(players.get(i)!=null) {found = true; startingPlayer = i; break;}}}
			if(!found) {gameOver = gameOver();}
			if(!gameOver) {
				livesLostPlayers.clear();
				System.out.println(players.get(startingPlayer).getName()+" is the new starting player.");
				System.out.println(players.size()+": Players size");
				if(!gameOver()) {
					panel.addText("\nPress the 'Start Next Round' button to begin the next round.\n");
					panel.changeStatus(-2, true);
					panel.changeStatus(-1, false);
					roundCount++;
					if(!isCustom) {
						if(players.size()<=8) {minimumCardNumber = 7;}
						if(players.size()<=5) {minimumCardNumber = 8;}
					}
				}
			}
			if(!gameOver) {
				playerTurn = startingPlayer;
				System.out.println(players.get(startingPlayer).getName()+" is still the new starting player.");
				if(gameMode.equalsIgnoreCase("competition")){for(int i=0; i<players.size()-1; i++) {
					if(players.get(i)!=null && players.get(i).getClass().getName().equals("AI31.Human")) {humanPlayer = i;}}}
			}
			panel.changeStatus(-7, true);
		}
		catch(GameErrorException e) {
			//no one is alive
			panel.gameOver = true;
			int result = displayYesNo("Want to play again?", "The game has ended in a draw. Not a single player is alive.");
			try {
				if(result==0){
					playerNames.clear();
					new UserInterface();
					/*
					panel.changeStatus(-5, true);
					panel.changeStatus(-6, true);
					displayInfo("Use the top panel of components to start a new game!", "Ready for the next game!");
					panel.gameOver = true;
					 */
				}
				else{System.exit(0);}}
			catch(Exception e1) {displayException(e1, 0);}
		}
		catch(Exception e) {displayException(e, 0);}
	}
	/**
	 * <html>
	 * Determines whether the game is over.
	 * @return true if the game is over (all human players or your AI are eliminated), <br>
	 * false if the game is stll running
	 * </html>
	 */
	protected boolean gameOver() {
		try {
			ArrayList<Player> truePlayers = new ArrayList<Player>(players);
			for(Iterator<Player> iter = truePlayers.iterator(); iter.hasNext();) {if(iter.next() == null) {iter.remove();}}
			if(truePlayers.size()==2 && !multiPlayer) {
				int result = displayYesNo("You win 1st place!\nWant to play again?", "Congratulations!");
				if(result==0){playerNames.clear(); new UserInterface(); return true;}
				else{System.exit(0);}
			}
			else if(truePlayers.size()==2 && multiPlayer){
				int c = displayYesNo(players.get(0).getName()+" has won 1st place!", "Want to play again?");
				if(c==1) {System.exit(0);} else{playerNames.clear(); new UserInterface(); return true;}}
		}
		catch(Exception e) {displayException(e, 0); return false;}
		return false;
	}
	/**
	 * Swaps the cards of the middle and the cards of the current player.
	 */
	protected void swapWithMiddle(){
		ArrayList<Card31> old = new ArrayList<Card31>();
		for(int i=0; i<players.get(players.size()-1).getHand().size(); i++){
			//takes from the middle hand
			Card31 move = new Card31(players.get(players.size()-1).getHand().get(i));
			old.add(move);
			//the above gets the middle hand (the last player) and removes the card from their hand 
		}
		players.get(players.size()-1).clearHand();
		for(int i=0; i<3; i++){/*what this does: takes the card from the player and adds it to the middle*/
			Card31 c = players.get(playerTurn).getCard(i);
			players.get(players.size()-1).addCard(c);
			c = null;
		}
		players.get(playerTurn).clearHand();
		for(int i=0; i<3; i++){players.get(playerTurn).addCard(old.get(i));}
		old.clear();
		if(players.get(playerTurn).getClass().getName().equals("AI31.Human") && !multiPlayer) 
		{panel.addText(players.get(playerTurn).getName()+": "+players.get(playerTurn)); panel.updatePlayerHand();}
		else{panel.updateSpecificPlayerHand();}
		return;
	}
	/**
	 * Resets the player hands.
	 * @throws PlayingCardException
	 */
	protected void resetHands(){
		for(Player p : players){if(p!=null) {p.clearHand();}}//clear all the hands
		cards.clear();//clear the cards
		addCards();//re-add the cards
		dealCards();//deal the cards
		firstTurn = 1;
		panel.updateCardLabels(0, 0);
		System.out.println(players+": Players ");
		if(gameMode.equalsIgnoreCase("competition")){panel.updatePlayerHand();}
	}
	/**
	 * Adds the cards to be dealt for the game. If players have specific cards for a set amount of rounds, this will give them the cards.
	 * Note that the players must be added BEFORE using this method, otherwise an IndexOutOfBounds exception will run.
	 */
	protected void addCards(){
		try {
			if(customInfo!=null && customInfo.specificCards) {//customInfo implies customFrame is non-null
				//specific cards enabled - cards are set
				cards.addAll(specificInfo.allAvailableCards);
				if(specificInfo.specificCardsEnabled && specificInfo.specificRoundCt!=0 && specificInfo.cardsAddedToPlayers!=0) {
					for(int i=0; i<customInfo.playerCt; i++) {
						if(!(specificInfo.playerCards.get(i) == null || specificInfo.playerCards.get(i).cards[0] == null)) {
							ArrayList<Card31> playerCard = new ArrayList<Card31>(Arrays.asList(specificInfo.playerCards.get(i).cards));
							for(int i1=0; i1<playerCard.size(); i1++) {
								if(playerCard.get(i1)==null) {playerCard.remove(i1);}
								else if(playerCard.get(i1).isRandomCard()) {
									Card31 removed = playerCard.remove(i1);
									playerCard.add(i1, generateSuitCard(removed.randomCardNum()));
								}
							}
							if(players.get(i)!=null) {players.get(i).addHand(playerCard);}
							if(players.get(i).getClass().getName().equals("AI31.MiddleHand")) {System.out.println("Added Middle Hand");}
						}
					}
				}
				Collections.shuffle(cards);

				return;
			}
			for(int i=minimumCardNumber; i<11; i++){//adds min card number-10 of each suit
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
			Collections.shuffle(cards);
			Collections.shuffle(cards);
		}
		catch(GameErrorException e) {displayException(e, 0);}
	}
	/**
	 * <html>
	 * Deals the cards out to the players. <br>
	 * If a player has a preset hand from custom mode, it gives the player the hand and randomizes the remaining cards. <br>
	 * If not, it gives the player three pseudorandomly selected cards. <br>
	 * If the "Set Random Suit Quantities" option is selected, random cards are generated per suit quantity, in suit order.
	 * </html>
	 */
	protected void dealCards(){
		boolean suitQuantitiesInfo = false;
		if(specificInfo!=null) {
			if(specificInfo.suitQuantities!=null) {
				if(isCustom) {
					for(int i=0; i<4; i++) {
						if(specificInfo.suitQuantities[i] != null && specificInfo.suitQuantities[i] != customFrame.antiQuantity) {
							suitQuantitiesInfo = true;
							break;
						}
					}
				}
			}
		}
		//eliminate nulls
		boolean done = false;
		System.out.println(players.size()+" Players size");
		if(specificInfo!=null && specificInfo.specificCardsEnabled && specificInfo.specificRoundCt!=0) {
			//remove nulls here
			for(Player p : players) {
				if(p!=null) {
					while(p.getHand().remove(null)) {}//removes all null instances no matter what from each player hand
					if(!suitQuantitiesInfo) {for(int i = p.getHand().size(); i<3; i++) {p.addCard(generateCard());}}
					//only generate cards if all of the suit quantities are anti quantities
				}
			}
			if(suitQuantitiesInfo) {
				/*
				 for(int i=0; i<4; i++) {//4 suits
					 specificInfo.suitQuantities[i].randomize();
					 System.out.println(specificInfo.suitQuantities[i].toString()+"\nAAA");
					 //System.out.println("HI");																																												
					 if(specificInfo.suitQuantities[i] != customFrame.antiQuantity) {//not equal to the deafult, the antiQuantity - this takes care of non-generated values
						 for(int k=0; k<specificInfo.suitQuantities[i].randomVal; k++) {//this number of cards
							 //System.out.println("HELLO");
							 //add this set number of cards to players
							 for(Player p : players) {//three cards per player
								 if(p!=null) {
									 //let us retry compatibility
									 //System.out.println("MAYBE HI");
									 for(int l=p.getHand().size(); l<3; l++) {
										 if(p!=null) {
											 Card31 c = null;
											 while(c==null) {
												 //if the suit is not the selected one
												 //System.out.println("MASTER RACE");
												 //the suits 

												 try {c = generateSuitCard(AI31Constants.ORDERED_SUITS[i]);}
												 catch(GameErrorException e) {displayException(e, 0); c = null;}
												 if(cardCount(AI31Constants.ORDERED_SUITS[i]) == 0) {break;}
											 }
											 cards.remove(c);
											 p.addCard(c);
											 c = null;
										 }
									 }
								 }
							 }
							 boolean done1 = true;
							 for(Player p : players) {if(p.getHand().size() != 3) {done1 = false;}}
							 if(done1) {done = true;}
							 if(done) {break;}
						 }
						 if(done) {break;}
					 }
				 }
				 */
				int totalCards = 0;//total number of cards that have been added
				//int chosenSuitNum = 0;
				//int[] cardsPerSuit = {0,0,0,0};
				for(int i=0; i<4; i++) {if(specificInfo.suitQuantities[i]!=customFrame.antiQuantity) {
					specificInfo.suitQuantities[i].randomize(); System.out.println(specificInfo.suitQuantities[i]+"\n");}}
				for(int i=0; i<players.size(); i++) {System.out.println(players.get(i).getHand().size()+": "+i); totalCards += players.get(i).getHand().size();}
				//totalCards is originally the number of cards that exist in the player hands - subtract the total number of cards from this to get the new value
				totalCards = (3*customInfo.playerCt) - totalCards;
				System.out.println("Total cards: "+totalCards);
				ArrayList<Card31> addCards = new ArrayList<Card31>();
				for(int i=0; i<4; i++) {
					//cardsPerSuit: [a,b,c,0] if spades is antiquantity
					if(addCards.size() < totalCards) {//more cards can be added
						if(specificInfo.suitQuantities[i] != customFrame.antiQuantity) {//not an antiquantity
							for(int k=0; k<specificInfo.suitQuantities[i].randomVal; k++) {//each extra card to add
								try {addCards.add(generateSuitCard(AI31Constants.ORDERED_SUITS[i]));}//generate a suit card
								catch (GameErrorException e) {displayException(e,0);}
							}
						}
					}
					else {break;}
				}
				for(Player p : players) {if(p.getHand().size() < 3) {for(int i=p.getHand().size(); i<3; i++) {
					p.addCard(addCards.remove(new Random().nextInt(addCards.size())));}}}
				//generate the cards prior to this
				//instead, I will pre-generate enough cards to 
				/*for(int i=0; i<totalCards; i++) {//maybe there isn't enough time...
					 if(cardsPerSuit[chosenSuitNum] < specificInfo.suitQuantities[chosenSuitNum].randomVal) {
						 boolean allDone = true;
						 int random = (int)(Math.random() * players.size());//random number from 0 to player size
						 Collections.shuffle(specificInfo.availableSuits);
						 chosenSuitNum = (int)(Math.random() * specificInfo.availableSuits.size());
						 System.out.println("Chosen suit num #"+i+": "+chosenSuitNum);
						 String chosenSuit = specificInfo.availableSuits.get(chosenSuitNum);
						 cardsPerSuit[chosenSuitNum]++;
						 //counts irrelevant of a cap on a per-suit basis
						 try {if(players.get(random).getHand().size() < 3) {players.get(random).addCard(generateSuitCard(chosenSuit));}else{i--;}}
						 catch(GameErrorException e) {displayException(e, 0);}
						 for(Player p : players) {System.out.println(p.getName()+", "+p.getHand().size()); if(p.getHand().size() < 3) {allDone = false; break;}}
						 if(allDone) {System.out.println("DONE"); break;}//enough cards, I guess, or really? Idk
					 }
				 }
				 for(Player p : players) {if(p.getHand().size() < 3) {for(int i=p.getHand().size(); i<3; i++) {p.addCard(generateCard());}}}
				 */
			}
			specificInfo.specificRoundCt--;
		}
		else {
			Random r = new Random();
			int card = 0;
			Card31 c = null;
			for(int i=1; i<4; i++){//3 cards
				for(Player p: players){
					if(p!=null) {
						card = r.nextInt(cards.size());//0 to size-1	
						c = cards.remove(card);
						p.addCard(c);//adds the card c and removes the card from the arraylist, making sured there are no shallow copies
						c = null;//to ensure that c is reset
					}
				}
			}
			System.out.println("Cards size: "+cards.size()); 
		}
	}
	/**
	 * Generates a card from the cards arraylist based on all four suits.
	 * @return a randomly selected card from the cards ArrayList
	 */
	protected Card31 generateCard() {
		Collections.shuffle(cards);
		return cards.remove(new Random().nextInt(cards.size()));
	}
	/**
	 * <html>
	 * Generates a random card based on a suit or set of suits, specified by the condition variable. <br>
	 * 0: Spades <br>
	 * 1: Clubs <br>
	 * 2: Hearts <br>
	 * 3: Diamonds <br>
	 * 4: Spades or Clubs<br>
	 * 5: Spades or Hearts<br>
	 * 6: Spades or Diamonds<br>
	 * 7: Clubs or Hearts<br>
	 * 8: Clubs or Diamonds<br>
	 * 9: Hearts or Diamonds<br>
	 * 10: Clubs, Hearts, or Diamonds<br>
	 * 11: Spades, Hearts, or Diamonds<br>
	 * 12: Spades, Clubs, or Diamonds, or<br>
	 * 13: Spades, Clubs, or Hearts.<br>
	 * @param condition - the number of the choice of suits (which number represents the suits you want?)
	 * <b> Let me know if I missed any! </b>
	 * @throws GameErrorException if the given condition is invalid or there are no available cards for the given conditions
	 */
	protected Card31 generateSuitCard(int condition) throws GameErrorException{
		ArrayList<String> chosenSuits = new ArrayList<String>(3);
		switch(condition) {
		case 0:
			chosenSuits.add("spades");
			break;
		case 1:
			chosenSuits.add("clubs");
			break;
		case 2:
			chosenSuits.add("hearts");
			break;
		case 3:
			chosenSuits.add("diamonds");
			break;
		case 4:
			chosenSuits.add("spades");
			chosenSuits.add("clubs");
			break;
		case 5:
			chosenSuits.add("spades");
			chosenSuits.add("hearts");
			break;
		case 6:
			chosenSuits.add("spades");
			chosenSuits.add("diamonds");
			break;
		case 7:
			chosenSuits.add("clubs");
			chosenSuits.add("hearts");
			break;
		case 8:
			chosenSuits.add("clubs");
			chosenSuits.add("diamonds");
			break;
		case 9:
			chosenSuits.add("hearts");
			chosenSuits.add("diamonds");
			break;
		case 10:
			chosenSuits.add("clubs");
			chosenSuits.add("hearts");
			chosenSuits.add("diamonds");
			break;
		case 11:
			chosenSuits.add("spades");
			chosenSuits.add("hearts");
			chosenSuits.add("diamonds");
			break;
		case 12:
			chosenSuits.add("spades");
			chosenSuits.add("clubs");
			chosenSuits.add("diamonds");
			break;
		case 13:
			chosenSuits.add("spades");
			chosenSuits.add("clubs");
			chosenSuits.add("hearts");
			break;
		default:
			throw new GameErrorException("Please enter a valid index for a suit or combination of suits (read the javadoc).", "E3295");
		}
		Collections.shuffle(cards);
		Collections.shuffle(chosenSuits);
		while(true) {
			int rand = new Random().nextInt(cards.size());
			for(String s : chosenSuits) {if(s.equalsIgnoreCase(cards.get(rand).getSuit())) {return cards.remove(rand);} Collections.shuffle(chosenSuits);}
			for(int i=0; i<chosenSuits.size(); i++) {if(suitAvailable(chosenSuits.get(i))) {break;}
			if(i==chosenSuits.size()-1) {throw new GameErrorException("No cards that match the given suit parameters are currently available.", "G1985");}}

		}
		//throw new GameErrorException("No cards that match the given suit parameters are currently available.");
	}
	/**
	 * Generates a random card based on a suit or set of suits, specified by the suit and suits varaiables. 
	 * @param suit - the first suit you want to add
	 * @param suits - all the remaining suits you want to add
	 * @return a card that matches the specified suit
	 * @throws GameErrorException - if there are no available cards given the selected suit
	 */
	protected Card31 generateSuitCard(String suit, String...suits) throws GameErrorException{
		if(suits == null || suits.length > 2) {return null;}
		ArrayList<String> chosenSuits = new ArrayList<String>();
		chosenSuits.add(suit);
		for(String possibleSuit : suits) {chosenSuits.add(possibleSuit);};
		Collections.shuffle(cards);
		Collections.shuffle(chosenSuits);
		while(true) {
			int rand = new Random().nextInt(cards.size());
			for(String s : chosenSuits) {if(s.equalsIgnoreCase(cards.get(rand).getSuit())) {return cards.remove(rand);} Collections.shuffle(chosenSuits);}
			for(int i=0; i<chosenSuits.size(); i++) {if(suitAvailable(chosenSuits.get(i))) {break;}
			if(i==chosenSuits.size()-1) {throw new GameErrorException("No cards that match the given suit parameters are currently available.", "B663");}}

		}
	}
	/**
	 * How many available cards per suit are there?
	 * @param suit - what suit do you want to check?
	 * @return the number of cards in that suit
	 */
	protected int cardCount(String suit) {
		int counter = 0;
		for(Card31 c : cards) {if(c.getSuit().equalsIgnoreCase(suit)) {counter++;}}
		return counter;
	}
	/**
	 * Ends the current player's turn and beings the next player's turn. If this is the final turn,
	 * it removes the player from the list of alive players for the round.
	 */
	protected void nextPlayer(){
		if(firstTurn == -1){alivePlayers.remove(new Integer(playerTurn));}
		if(playerTurn==players.size()-2){playerTurn = 0;}
		else{playerTurn++;}
	}
	/**
	 * Changes the turn counter (dealer's turn (1) to normal turn (0) to final turn (-1)).
	 */
	protected void moveTurnCounter(){
		if(firstTurn == 0){firstTurn = -1; panel.addText("\nFINAL TURN!\n\n");}
		else if(firstTurn == 1){firstTurn = 0;}
	}
	/**
	 * Shuffles the given array.
	 * @param <T> - the reference object of the array 
	 * @param info 0- the array that is intended to be shuffled
	 * @return a shuffled copy of the array
	 */
	protected <T> T[] shuffle(T[] info) {
		ArrayList<T> aList = (ArrayList<T>) Arrays.asList(info);
		Collections.shuffle(aList);
		return aList.toArray(info);
	}
	/*^^^^^^^^^^ Processing the player's turns ^^^^^^^^^^*/
	/*VVVVVVVVVV Obtaining elements VVVVVVVVVV*/
	/**
	 * Gets a copy of the middle hand's contents
	 * @return a deep copy of the Middle hand (not the same object)
	 */
	protected MiddleHand getMiddleHand(){
		MiddleHand copy = new MiddleHand(this);
		copy.addHand(players.get(players.size()-1).getHand());
		return copy;
	}
	/**
	 * @see #moveTurnCounter()
	 * @return the turn counter value
	 */
	protected int getTurnStatus(){return firstTurn;}
	/**
	 * Finds the given Player and returns the location of the Player.
	 * @param p - the Player you want to find
	 * @return the index location of the Player
	 */
	protected int findPlayer(Player p) {//where will I use this?
		for(int i=0; i<players.size()-1; i++) {if(players.get(i)!=null && players.get(i)==p) {
			System.out.println("\nFound Player "+p.getName()+" at index "+i+"\n");return i;}}
		return -1;
	}
	/**
	 * @return whether the game is custom or not
	 */
	protected boolean isCustom(){return isCustom;}
	/**
	 * @return the list of all players
	 */
	protected ArrayList<Player> getPlayers(){return players;}
	/**
	 * @return who's turn it currently is
	 */
	protected int getPlayerTurn() {return playerTurn;}
	/**
	 * @return whether the game is multiplayer or not
	 */
	protected boolean isMultiPlayer() {return multiPlayer;}
	/**
	 * 
	 * @return the game mode this game is playing
	 */
	protected String getMode() {return gameMode;}
	/**
	 * 
	 * @return whether players are alive or not
	 */
	protected boolean playersAlive() {return !alivePlayers.isEmpty();}
	/**
	 * 
	 * @return the repeating Timer object
	 */
	protected Timer getRepeatingTimer() {return timer;}
	/**
	 * 
	 * @return the non-repeating Timer object
	 */
	protected Timer getNonRepeatingTimer() {return nonRepeatTimer;}
	/**
	 * 
	 * @return the component panel
	 */
	protected UserPanel getPanel() {return panel;}
	/**
	 * 
	 * @return the minimum number of cards
	 */
	protected static int getMinCardNum(){return minimumCardNumber;}
	/**
	 * 
	 * @return the custom frame
	 */
	protected CustomModeFrame getCustomFrame() {return customFrame;}
	/**
	 * 
	 * @return the custom mode information
	 */
	protected CustomModeInfo getCustomInfo() {return customInfo;}
	/**
	 * 
	 * @return the specific cards information
	 */
	protected SpecificCardsInfo getSpecificInfo() {return specificInfo;}
	/**
	 * 
	 * @return the location of the human player if the mode is competition (or custom competition), 0 if the mode is not competition
	 */
	protected int getHumanPlayer() {if(gameMode.equalsIgnoreCase("competition")) {return humanPlayer;}else{return 0;}}
	/*^^^^^^^^^^ Obtaining elements ^^^^^^^^^^*/
	/*VVVVVVVVVVVVV Setting Elements VVVVVVVVVVVVV*/
	/**
	 * Updates the speed of the repeating timer (how fast between calls?)
	 * @param ms - how long it takes between method pings (the shorter the speed the shorter the delay)
	 */
	protected void updateTimer(int ms) {
		timer.setDelay(ms);
		timer.setInitialDelay(ms);
	}
	/**
	 * Starts or ends the timer based on the parameter.
	 * @param sOE - true if you want to restart the timer, false to end the timer
	 */
	protected void startOrEndTimer(boolean sOE) {
		if(sOE) {timer.restart();}
		else {timer.stop();}
	}
	/**
	 * Starts or ends the non repeating timer.
	 * The only difference: this will affect the non-repeating timer, not the repeating timer
	 * @param sOe - true if the game is starting and you want to restart the timer, false if you want to end the game.
	 * @see #startOrEndTimer(boolean)
	 * 
	 */
	protected void startOrEndNonRepeatTimer(boolean sOE) {
		if(sOE) {nonRepeatTimer.restart();}
		else {nonRepeatTimer.stop();}
	}
	/**
	 * Updates the speed of the non repeating timer.
	 * Note that this applies for the non-repeating timer, not the repeating timer.
	 * @param ms - the amount (in milliseconds) the timer will run for
	 * @see #updateTimer(int)
	 * 
	 */
	protected void updateNonRepeatTimer(int ms) {
		nonRepeatTimer.setDelay(ms);
		nonRepeatTimer.setInitialDelay(ms);
	}
	/**
	 * <html>
	 * Sets the game mode and checks for custom mode.
	 * @param mode - the gamemode you want the game to be in
	 * <br>
	 * <br>
	 * DO NOT use this method without knowing the human counts - how many humans? 1? Everyone? Between 1 and everyone?
	 * <br>
	 * Just a reminder:
	 * <br>
	 * "competition" means single player
	 * <br>
	 * "friendly" means every non-mid Player is a human
	 * <br>
	 * "combination" means some AIs, some humans
	 * </html>
	 */
	protected void setMode(String mode) {
		boolean okMode = false;
		if(mode.equalsIgnoreCase("custom")) {isCustom = true; okMode = true;}
		if(mode.equalsIgnoreCase("competition") || mode.equalsIgnoreCase("friendly")) {okMode = true;}
		if(mode.equalsIgnoreCase("combination")) {okMode = true;}
		if(mode.equals("") || mode == null || !okMode){try {throw new GameErrorException("Please do not attempt to use a nonexistent game mode.", "I4204");}
		catch(GameErrorException e) {displayException(e, 0); displayError("Please choose a different mode next time."); System.exit(0);};}
		else{gameMode = mode;}
	}
	/**
	 * Sets the number of players. Currently unsure of whether is considers the mid or not.
	 * @param ct - the number of players
	 */
	protected void setPlayerCount(int ct) {
		playerCount = ct;
		if(ct>9 && ct<14) {minimumCardNumber-=(ct-9);}//10 players - 6 and above, 12 - 4 and above, 13 - 3 and above (no more)
		if(ct<=4) {minimumCardNumber = 8;}
	}
	/**
	 * Sets the number of humans.
	 * @param ct - the number of human players
	 */
	protected void setHumanCount(int ct){
		if(ct<0 || ct> playerCount){displayError("Please do not attempt to add an invalid number of players.");}
		else{humanCount = ct;}
	}
	/**
	 * Sets the life count for each player.
	 * @param ct - the number of lives each player starts with
	 */
	protected void setLifeCount(int ct) {Player.setLifeCounter(ct);}
	/**
	 * Sets whether the game is multiplayer or singleplayer.
	 * @param setting - whether multiplayer is enabled or disabled (true for enabled, false for disabled)
	 */
	protected void setMultiPlayer(boolean setting) {multiPlayer = setting;}
	/**
	 * Sets the custom mode information.
	 * @param info - the custom mode information object
	 */
	protected void setCustomInfo(CustomModeInfo info) {
		//new CustomModeInfo(info);
		customInfo = info;
	}
	/**
	 * Sets the specific cards information.
	 * @param sci - the specific cards info object
	 */
	protected void setSpecificCardsInfo(SpecificCardsInfo sci) {specificInfo = sci;}
	/**
	 * Finds whether the specified suit exists in the cards arraylist.
	 * @param suit - the suit you are searching for
	 * @return true if a card with the specified suit exists, false if no cards with the specified suit exist
	 */
	protected boolean suitAvailable(String suit) {
		for(Card31 c : cards) {if(c.getSuit().equalsIgnoreCase(suit)) {return true;}}
		return false;
	}
	/*^^^^^^^^^^^^^ Setting Elements ^^^^^^^^^^^^^*/
	/*VVVVVVVVVV MAIN method and playing the userInterface VVVVVVVVVV*/
	/**
	 * The good old main method. You don't need to know what happens here, except that this makes Swing thread safe (?)
	 * @param args - the given arguments by the system
	 */
	public static void main(String[] args) {
		askedInfo = false;
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				try {new UserInterface();}
				catch(PlayingCardException e) {displayException(e, 0);}
				catch(GameErrorException e) {displayException(e, 0);}
			}
		});
	}
	/*^^^^^^^^^^ MAIN method and playing the userInterface ^^^^^^^^^^*/
}