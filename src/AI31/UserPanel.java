/**
 * The package of AI31 classes.
 */
package AI31;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.imageio.ImageIO;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import baselineCustomClasses.GUISetUpException;
import baselineCustomClasses.GameException;
/**
 * @author dykim This panel is the base of the components. It takes care of updating each component with the numbers
 *         necessary for the component.
 * @version 10.4
 */
public class UserPanel extends JPanel implements ActionListener, AI31Constants, MouseListener, MouseMotionListener {
	/**
	 * @see AI31.UserInterface#serialVersionUID
	 */
	private static final long serialVersionUID = 2L;
	// this will be the new framework for the GUI
	/**
	 * Some miscellaneous number that is used to display player information.
	 */
	private int count;
	/* VVVVVVVVVV Components for the game GUI VVVVVVVVVV */
	/**
	 * The Image of the back of the cards
	 */
	private Image backImage;
	/**
	 * The UserInterface to which this game belongs to
	 */
	private UserInterface userInterface;
	/**
	 * Since the timer actions come here (to the panel), what action should be taken? The timer counter determines the action
	 * to take.
	 */
	private int timerCounter = -1;
	/**
	 * The size of all JTextFields in the first page. This is so that when I add them, they don't auto shrink to a tiny size.
	 */
	protected static final Dimension LABEL_SIZE = new Dimension(600, 25);
	/**
	 * The size of some of the smaller components.
	 */
	protected static final Dimension SMALL_LABEL_SIZE = new Dimension(300, 25);
	/**
	 * Some random Insets dimension. It doesn't do anything at all - its there for the sake of being there.
	 */
	protected static final Insets LABEL_INSETS = new Insets(0, 0, 0, 0);
	/**
	 * <html> The nonexistent button used for starting the turns after the dealer's turn. Removed becuase it was unneeded.
	 * 
	 * @deprecated Do not use this button. <br>
	 * 
	 */
	private JButton nextTurnButton;
	/**
	 * The pause button. It should pause the program for 3 seconds.
	 */
	private JButton pauseButton;
	/**
	 * The "Finish Round" button. Used to display the scores of each player.
	 */
	private JButton finishRoundButton;
	/**
	 * The quit button. Literally does as its name says: quits the game.
	 */
	private JButton quitButton;
	/**
	 * The "Start Next Round" button. Used to, as the name says, start the next round.
	 */
	private JButton startRoundButton;
	/**
	 * The "Clear Text Area" button, used to clear the text area.
	 */
	private JButton clearButton;
	/**
	 * The "Start Game" button used to start the game, right under the game mode combo box.
	 */
	private JButton startGameButton;
	/**
	 * The "Show another player's cards" button that only works with
	 */
	private JButton showPreviousButton;
	/**
	 * The combo box that displays options for display settings
	 */
	private JComboBox displayLivesLostBox;
	/**
	 * The primary text area - all text comes from here
	 */
	private JTextArea playArea;
	/**
	 * The text on the left - the rulebook, explaining how to play the game
	 */
	private JTextArea rulesArea;
	/**
	 * Some random useless text area. Yay...
	 */
	private JTextArea infoArea;
	/**
	 * The text field that says "Player cards: "
	 */
	protected JTextField playerInfo;
	/**
	 * The text field that shows the sum of the player's hand
	 */
	private JTextField playerSum;
	/**
	 * The text field that says "Mid cards: "
	 */
	private JTextField midInfo;
	/**
	 * The text field that says "Mid sum". It shows the sum of the mid player's hand.
	 */
	private JTextField midSum;
	/**
	 * The text field that says "Speed Label". It shows the speed of the game.
	 */
	private JLabel speedLabel;
	/**
	 * The display label that says "Display settings". It shows the display settings available.
	 */
	private JLabel otherDisplayLabel;
	/**
	 * The display label that says "Player cards:"
	 */
	private JLabel playerLabel;
	/**
	 * The first card image of the human player.
	 */
	private JLabel playerImage1;
	/**
	 * The second card image of the human player.
	 */
	private JLabel playerImage2;
	/**
	 * The third card image of the human player.
	 */
	private JLabel playerImage3;
	/**
	 * The first card image of the CPU player. Only visible at the end of a round.
	 */
	private JLabel cpuImage1;
	/**
	 * The second card image of the CPU player.
	 */
	private JLabel cpuImage2;
	/**
	 * The third card image of the CPU player.
	 */
	private JLabel cpuImage3;
	/**
	 * The sum of the CPU cards.
	 */
	private JLabel sumOfCPU;
	/**
	 * The first card image of the mid player.
	 */
	private JLabel midImage1;
	/**
	 * The second card image of the mid player.
	 */
	private JLabel midImage2;
	/**
	 * The third card image of the mid player.
	 */
	private JLabel midImage3;
	/**
	 * The CPU cards label.
	 */
	private JLabel cpuCards = new JLabel("CPU Cards (round end):");
	/**
	 * The panel that hosts the components at the right.
	 */
	private JPanel rightPanel;
	/**
	 * The panel that hosts the components at the bottom.
	 */
	private JPanel bottomPanel;
	/**
	 * The panel that hosts the components at the top.
	 */
	private JPanel topPanel;
	/**
	 * The panel that hosts the components at the left.
	 */
	private JPanel leftPanel;
	/**
	 * The panel that hosts the components at the center.
	 */
	private JPanel centerPanel;
	/**
	 * The panel that hosts the multiplayer radio buttons.
	 */
	private JPanel radioButtonPanel;
	/**
	 * The panel that hosts the three player card images.
	 */
	private JPanel playerImagePanel;
	/**
	 * The panel that hosts the three cpu card images.
	 */
	private JPanel cpuImagePanel;
	/**
	 * The panel that hosts the three mid card images.
	 */
	private JPanel midImagePanel;
	/**
	 * The play area scroll pane.
	 */
	private JScrollPane scrollPane;
	/**
	 * The rules scroll pane.
	 */
	private JScrollPane rulesScrollPane;
	/**
	 * The "type anything here" scroll pane.
	 */
	private JScrollPane infoScrollPane;
	/**
	 * The speed box at the bottom that is used to set the time.
	 */
	private JComboBox speedBox;// length of the delay
	/**
	 * The slider at the top used to set the number of lives per player.
	 */
	private JSlider lifeCountSlider;
	/**
	 * The slider at the top used to set the number of players.
	 */
	private JSlider playerCountSlider;
	/**
	 * The slider at the top used to set the number of human players.
	 */
	private JSlider humanCountSlider;
	/**
	 * The radio button that says "On". Used to identify a multiplayer game.
	 */
	private JRadioButton multiPlayerOn;
	/**
	 * The radio button that says "Off". Used to identify a singleplayer game.
	 */
	private JRadioButton multiPlayerOff;
	/**
	 * The arraylist of options for game modes.
	 */
	private JComboBox<String> gameModeOptions;
	/**
	 * The arraylist of options for the color backs.
	 */
	private JComboBox<String> cardBackOptionsBox;
	/**
	 * The future menu bar, to simplify the bottom buttons.
	 */
	private JMenuBar menuBar;
	/**
	 * The first menu, containing information components.
	 */
	private JMenu infoMenu;
	/**
	 * The help menu item.
	 */
	private JMenuItem helpItem;
	/**
	 * The pause menu item.
	 */
	private JMenuItem pauseItem;
	/**
	 * The quit menu item. Replaces the quit buttn, which I will soon deprecate. Once it is deprecated I will remove it.
	 */
	private JMenuItem quitItem;
	/**
	 * Just some random counter.
	 */
	private int counter;
	/**
	 * The previous index (used for the game mode box - determines what mode choice you make)
	 */
	private int priorIndex = -1;
	/**
	 * Is the game over?
	 */
	protected boolean gameOver = false;
	/**
	 * Timer options - how fast do you want the game to go? Can be customized to your pleasure.
	 */
	private static final String[] TIMER_OPTIONS = {"Choose the speed of the game:", "0 Seconds", "0.01 Seconds", "1 Second",
			"2 Seconds", "3 Seconds", "4 Seconds", "5 Seconds", "Choose your own timer..."};
	/**
	 * The display options - do you want there to be pop-ups for lives lost, eliminations, both, or neither? It will remain
	 * in the chat no matter what way you choose.
	 */
	private static final String[] DISPLAY_OPTIONS = {"Choose which dialog boxes you want:", "No lives nor eliminations",
			"Eliminations but no lives", "Lives but no eliminations", "Lives and eliminations"};
	/**
	 * Gamemode options. There are four main (not including default mode) options, which include the following: 1:
	 * Competition (you vs some number of AI's) 2: Friendly (you vs other people on the same machine - no AI players) 3:
	 * Combination (you and other local players vs AI's) 4: Custom - you can change most of the bonus settings here!
	 */
	private static final String[] MODE_OPTIONS = {"Choose the game mode:", "1: Competition (facing AIs)",
			"2: Friendly (facing local players)", "3: Combination (facing both local and AI players)",
			"4: Custom (choose from a range of different settings)"};
	/**
	 * The options for the colors of the back of the cards. Currently, there are ten options, all shown in the bottom combo
	 * box.
	 */
	private static final String[] CARD_BACK_OPTIONS = {"Choose the card back color: ", "Blue", "Yellow", "Red", "Green",
			"Gray", "Purple", "Orange", "Brown", "Aqua", "Black"};
	/**
	 * The speed of the actions - how much time is there between each player's actions?
	 */
	private static int speed = 2500;
	/**
	 * The color of the card's backs.
	 */
	private String selectedBack = "blue";
	/**
	 * A constant equal to 2 that denotes that the current display settings (showing both life loss and eliminations)
	 */
	public static final int LIVES_AND_ELIMINATIONS = 2;
	/**
	 * A constant equal to 1 that denotes that the current display settings (showing only life loss, no eliminations)
	 */
	public static final int LIVES_NOT_ELIMINATIONS = 1;
	/**
	 * A constant equal to 0 that denotes that the current display settings (showing only eliminations, no life loss)
	 */
	public static final int NO_LIVES_AND_ELIMINATIONS = 0;
	/**
	 * A constant equal to -1 that denotes that the current display settings (showing neither life loss nor eliminations)
	 */
	public static final int NO_LIVES_NOR_ELIMINATIONS = -1;
	/**
	 * Stores the display settings for lives and eliminations.
	 */
	private static int showEliminations = LIVES_AND_ELIMINATIONS;
	/**
	 * Whether the game is paused or running.
	 */
	private boolean paused = false;
	/* ^^^^^^^^^^ Components for the game GUI ^^^^^^^^^^ */
	/**
	 * The list of players for the game.
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	/**
	 * A copy of the players array, needed for moving Player objects around.
	 */
	private ArrayList<Player> playerCopy = new ArrayList<Player>();
	/**
	 * Is the first custon mode selected?
	 */
	protected boolean firstCustomSelected = true;
	/**
	 * Sets up the UserPanel (most of the components of the UserInterface GUI)
	 * 
	 * @param u - the UserInterface this panel belongs with
	 */
	public UserPanel(UserInterface u) {
		super();
		userInterface = u;
		try {
			setUp();
		} catch (GUISetUpException e) {
			UserInterface.displayException(e, 1);
		}
	}
	/**
	 * Runs the set up of the game.
	 * 
	 * @throws GUISetUpException
	 */
	public void setUp() throws GUISetUpException {// this will run the exact same set up as the main function
		try {
			try {
				backImage = ImageIO.read(getClass().getResource("/Pictures/" + selectedBack + "_back.png"))
						.getScaledInstance(-2, 100, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				UserInterface.displayException(e, 1);
			}
			setLayout(new BorderLayout());
			playArea = new JTextArea(
					"Welcome to 31!\nIf you want a simple game, press the 'Start Default Game' button and nothing else.\n",
					50, 50);
			playArea.setEditable(false);
			rulesArea = new JTextArea("31 Game Rules:\n");
			rulesArea.setEditable(false);
			rulesArea.setPreferredSize(new Dimension(300, 600));
			rulesArea.append("31 is a game of swapping cards to\ntry and gain the highest possible hand.\n\n"
					+ "Your goal is to try and gain the highest\npossible sum in your hand.\n"
					+ "The sum of your hand is equal to the highest\nsum of cards of one suit in your hand.\n"
					+ "3 of a number is worth 30.5 points, 3 aces\nare worth 32 points, and 6-7-8 of a suit\n"
					+ "in 10+ player games is worth 21.5 points.\n\n"// why 21.5? worse than 6-7-9 (22) but better than
																		// face-A-another suit (21)
					+ "Tens and face cards are worth 10 and aces 11,\nrespectively. Other cards carry their pip value.\n\n"
					+ "Each turn, you can either swap a card with\nanother card, swap all three cards from the middle\n"
					+ "with all three of your cards and pass, or pass.\nPassing makes this your last turn for the round.\n"
					+ "Everyone else gets one last turn to improve\ntheir hand once someone passes.\n"
					+ "Good luck and have fun! Trust me, it will\ntake a while to beat my AIs unless you get lucky...\n\n"
					+ "Use the 'Next Turn' button to let the AIs\ndo their turn, use the 'Next Round' button\n"
					+ "when the round is over to finish the round\nand use the 'Start Round' button to start\n"
					+ "the next round..\nLook above for your cards and\nbelow for the middle cards. Enjoy!\n\n"
					+ "Made by Dong-Yon Kim. I do not claim to own\nthe card game this is based off of. However,\n"
					+ "the GUI and strategy/mechanics were built\nby me. I hope you enjoy this game I made.");
			scrollPane = new JScrollPane(playArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			rulesScrollPane = new JScrollPane(rulesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			scrollPane.setMinimumSize(new Dimension(600, 450));
			ArrayList<JTextField> components = new ArrayList<JTextField>();
			playerInfo = new JTextField("Your cards: ");
			playerSum = new JTextField("Player sum");
			components.add(playerInfo);
			components.add(playerSum);
			midInfo = new JTextField("Mid cards: ");;
			midSum = new JTextField("Mid sum");
			components.add(midInfo);
			components.add(midSum);
			speedLabel = new JLabel("Speed", SwingConstants.CENTER);
			updateSpeed();
			speedLabel.setMaximumSize(SMALL_LABEL_SIZE);
			speedLabel.setPreferredSize(SMALL_LABEL_SIZE);

			// new GridBagConstraints( 0, 0, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
			// 0, 0, 0, 0 ), 0, 0 );

			infoArea = new JTextArea("Type anything here:");
			infoScrollPane = new JScrollPane(infoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			otherDisplayLabel = new JLabel("None", SwingConstants.CENTER);
			otherDisplayLabel.setMaximumSize(SMALL_LABEL_SIZE);
			otherDisplayLabel.setPreferredSize(SMALL_LABEL_SIZE);

			for (JTextField comp : components) { comp.setMaximumSize(LABEL_SIZE); comp.setEditable(false); }
			addCardComponents();
			add(centerPanel, BorderLayout.CENTER);
			speedBox = new JComboBox(TIMER_OPTIONS);
			speedBox.setSelectedIndex(0);
			speedBox.addActionListener(this);
			speedBox.setMaximumSize(new Dimension(250, 25));
			speedBox.setEditable(false);
			speedBox.setEnabled(true);
			displayLivesLostBox = new JComboBox(DISPLAY_OPTIONS);
			displayLivesLostBox.setSelectedIndex(0);
			displayLivesLostBox.addActionListener(this);
			displayLivesLostBox.setEditable(false);
			displayLivesLostBox.setEnabled(true);
			displayLivesLostBox.setMaximumSize(new Dimension(325, 25));
			cardBackOptionsBox = new JComboBox<String>(CARD_BACK_OPTIONS);
			cardBackOptionsBox.setSelectedIndex(0);
			cardBackOptionsBox.setEditable(false);
			cardBackOptionsBox.setEnabled(true);
			cardBackOptionsBox.addActionListener(this);
			cardBackOptionsBox.setMaximumSize(SMALL_LABEL_SIZE);
			updateDisplaySetting();
			nextTurnButton = new JButton("Next AI Turns");
			nextTurnButton.setEnabled(false);
			nextTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			finishRoundButton = new JButton("Finish Round");
			finishRoundButton.setEnabled(false);
			finishRoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			quitButton = new JButton("Quit");
			pauseButton = new JButton("Pause");
			startRoundButton = new JButton("Start Next Round");
			startRoundButton.setEnabled(false);
			startRoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			clearButton = new JButton("Clear the Play Area");
			nextTurnButton.addActionListener(this);
			finishRoundButton.addActionListener(this);
			quitButton.addActionListener(this);
			pauseButton.addActionListener(this);
			startRoundButton.addActionListener(this);
			clearButton.addActionListener(this);
			leftPanel = new JPanel(new GridBagLayout());
			GridBagConstraints cons = new GridBagConstraints();
			cons.gridx = 0;
			cons.gridy = 0;
			cons.gridheight = 14;
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.insets = LABEL_INSETS;
			cons.weightx = 0.5;
			cons.weighty = 1.0;
			// cons.ipady = 450;
			cons.fill = GridBagConstraints.BOTH;
			addSetUpComponents();
			rightPanel = new JPanel();
			rightPanel.add(finishRoundButton);
			rightPanel.add(startRoundButton);
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
			rightPanel.add(playerInfo);
			rightPanel.add(playerImagePanel);
			rightPanel.add(playerSum);
			rightPanel.add(infoScrollPane);
			rightPanel.add(midInfo);
			rightPanel.add(midImagePanel);
			rightPanel.add(midSum);
			add(rightPanel, BorderLayout.EAST);
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
			bottomPanel.add(quitButton);
			bottomPanel.add(pauseButton);
			bottomPanel.add(clearButton);
			bottomPanel.add(speedBox);
			bottomPanel.add(displayLivesLostBox);
			bottomPanel.add(cardBackOptionsBox);
			leftPanel.add(rulesScrollPane, cons);
			cons.ipady = 0;
			cons.gridy = 14;
			cons.gridheight = 1;
			cons.weighty = 0.05;
			cons.fill = GridBagConstraints.HORIZONTAL;
			leftPanel.add(speedLabel, cons);
			cons.gridy++;
			leftPanel.add(otherDisplayLabel, cons);
			add(leftPanel, BorderLayout.WEST);
			leftPanel.setMaximumSize(leftPanel.getPreferredSize());
			add(bottomPanel, BorderLayout.SOUTH);
		} catch (Exception e) {
			UserInterface.displayException(e, 1);
			throw new GUISetUpException("Something went wrong creating the components of the GUI", "B1953");
		}

	}
	/**
	 * Creates a menu bar for the user interface, allowing for separate display of instructions and more.
	 */
	public void createMenuBar() throws GUISetUpException {
		menuBar = new JMenuBar();
		infoMenu = new JMenu("Help");
		menuBar.add(infoMenu);
		helpItem = new JMenuItem("Rules");
		helpItem.addActionListener(this);
		pauseItem = new JMenuItem("Pause");
		pauseItem.addActionListener(this);

		// one to two extra menus and
		//pull whats from the bottom of the screen

		// the menus I want to create are going to be the ones in the bottom panel, eventually I will be able to drag and drop
		// at least I hope to be able to drag 'n' drop soon
	}
	/**
	 * Creates the future paneling that will be used to drag and drop cards, moving the text version of the game away into a
	 * more colorful, unique one. Steps: 1. Overhaul the current UI - try to maybe build a square, with players around the
	 * edges of where the text box is now 2. Implement animations - the cards move to the player (I could do this by getting
	 * the coordinates and moving them, or can I? I don't know since they are bound to panels, but I can give it my best shot
	 */
	public void createLayeredPaneling() {

	}
	/**
	 * Adds the card labels to the game.
	 */
	private void addCardComponents() {
		// adds the cards you see
		playerImage1 = new JLabel(new ImageIcon(backImage));
		playerImage2 = new JLabel(new ImageIcon(backImage));
		playerImage3 = new JLabel(new ImageIcon(backImage));
		cpuImage1 = new JLabel(new ImageIcon(backImage));
		cpuImage2 = new JLabel(new ImageIcon(backImage));
		cpuImage3 = new JLabel(new ImageIcon(backImage));
		midImage1 = new JLabel(new ImageIcon(backImage));
		midImage2 = new JLabel(new ImageIcon(backImage));
		midImage3 = new JLabel(new ImageIcon(backImage));
		playerImagePanel = new JPanel();
		cpuImagePanel = new JPanel();
		midImagePanel = new JPanel();
		playerImagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		cpuImagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		midImagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		playerImagePanel.add(playerImage1);
		playerImagePanel.add(playerImage2);
		playerImagePanel.add(playerImage3);
		cpuCards.setAlignmentX(CENTER_ALIGNMENT);
		cpuImagePanel.add(cpuCards);
		cpuImagePanel.add(cpuImage1);
		cpuImagePanel.add(cpuImage2);
		cpuImagePanel.add(cpuImage3);
		sumOfCPU = new JLabel("Sum: ");
		cpuImagePanel.add(sumOfCPU);
		showPreviousButton = new JButton("Show another player's cards");
		showPreviousButton.setEnabled(false);
		showPreviousButton.setAlignmentX(CENTER_ALIGNMENT);
		showPreviousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Player> newCopy = new ArrayList<Player>(players);
					for (Iterator<Player> playerIt = newCopy.iterator(); playerIt.hasNext();) {
						if (playerIt.next() == null) { playerIt.remove(); }
					}
					String[] names = new String[newCopy.size()];// add one later, for now remove the middle
					names[0] = "Choose a player name: ";
					for (int i = 0; i < newCopy.size() - 1; i++) {
						if (newCopy.get(i) != null) { names[i + 1] = newCopy.get(i).getName(); }
					}
					JComboBox choiceBox = new JComboBox(names);
					choiceBox.setSelectedIndex(0);
					choiceBox.setEditable(false);
					choiceBox.setMaximumSize(SMALL_LABEL_SIZE);
					choiceBox.setEnabled(true);
					UserInterface.displayInfo(choiceBox, "Choose the name of the player you want to see cards from:");
					ArrayList<JLabel> list = new ArrayList<JLabel>();
					list.add(cpuImage1);
					list.add(cpuImage2);
					list.add(cpuImage3);
					for (int i = 0; i < newCopy.size(); i++) {
						if (((String) choiceBox.getSelectedItem()).equals(newCopy.get(i).getName())) {
							addText("You are currently seeing " + newCopy.get(i).getName() + "'s cards.\n");
							cpuCards.setText(newCopy.get(i).getName() + "'s cards:");
							sumOfCPU.setText("Sum: " + newCopy.get(i).sumOfHand());
							for (int k = 0; k < 3; k++) {
								list.get(k).setIcon(new ImageIcon(newCopy.get(i).getCard(k).getImage()));
							}
							// if(newCopy.get(i).getName().equals("THE MIDDLE HAND")) {cpuImagePanel.remove(choiceBox);
							// cpuImagePanel.add(choiceBox);
							// cpuImagePanel.revalidate(); cpuImagePanel.repaint();};
							break;
						}
					}
					choiceBox.setSelectedIndex(0);

				} catch (Exception ex) {
					UserInterface.displayException(ex, 1);
				}
			}
		});
		cpuImagePanel.add(showPreviousButton);
		midImagePanel.add(midImage1);
		midImagePanel.add(midImage2);
		midImagePanel.add(midImage3);
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridy = 0;
		centerPanel.add(scrollPane, c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.gridy = 1;
		centerPanel.add(cpuImagePanel, c);
	}
	/**
	 * Creates the set up components.
	 */
	@SuppressWarnings("rawtypes")
	private void addSetUpComponents() {
		// adds the top of the screen: game mode and such
		gameModeOptions = new JComboBox<String>(MODE_OPTIONS);
		gameModeOptions.setSelectedIndex(0);
		gameModeOptions.setEditable(false);
		gameModeOptions.addActionListener(this);
		Hashtable playerCountTable = new Hashtable();
		Hashtable lifeCountTable = new Hashtable();
		for (int i = 2; i < 12; i++) { playerCountTable.put(new Integer(i), new JLabel("" + i)); }
		for (int i = 1; i < 16; i++) { lifeCountTable.put(new Integer(i), new JLabel("" + i)); }
		playerCountSlider = new JSlider(JSlider.HORIZONTAL, 2, 11, 4);
		playerCountSlider.setMajorTickSpacing(1);
		playerCountSlider.setPaintTicks(true);
		playerCountSlider.setSnapToTicks(true);
		playerCountSlider.setLabelTable(playerCountTable);
		playerCountSlider.setPaintLabels(true);
		lifeCountSlider = new JSlider(JSlider.HORIZONTAL, 1, 15, 5);
		lifeCountSlider.setMajorTickSpacing(1);
		lifeCountSlider.setPaintTicks(true);
		lifeCountSlider.setSnapToTicks(true);
		lifeCountSlider.setLabelTable(lifeCountTable);
		lifeCountSlider.setPaintLabels(true);

		JLabel lifeLabel = new JLabel("Choose the number of lives each player has:", SwingConstants.CENTER);
		startGameButton = new JButton("Start Default Game");
		startGameButton.addActionListener(this);

		topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		playerLabel = new JLabel("Choose the number of players:", SwingConstants.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		addComponent(playerLabel, c, topPanel);
		c.gridx = 1;
		// c.gridwidth = 2;
		addComponent(playerCountSlider, c, topPanel);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		addComponent(lifeLabel, c, topPanel);
		c.gridx = 1;
		// c.gridwidth = 2;
		addComponent(lifeCountSlider, c, topPanel);
		JPanel sparePanel = new JPanel(new GridLayout(0, 1));
		c.gridx = 2;
		c.gridy = 1;
		sparePanel.add(gameModeOptions);
		sparePanel.add(startGameButton);
		addComponent(sparePanel, c, topPanel);
		c.gridwidth = 1;
		addRadioButtons();
		c.gridx = 2;
		add(topPanel, BorderLayout.NORTH);
		// playerLabel.setMaximumSize(playerLabel.getPreferredSize());
		// startGameButton.setMaximumSize(startGameButton.getPreferredSize());
		// lifeLabel.setMaximumSize(lifeLabel.getPreferredSize());
	}
	private void addComponent(JComponent comp, GridBagConstraints c, JPanel p) {
		if (c != null) {
			p.add(comp, c);
		} else {
			p.add(comp);
		}
	}
	protected void addRadioButtons() {
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 2;
		multiPlayerOn = new JRadioButton("Enabled");
		multiPlayerOff = new JRadioButton("Disabled");
		multiPlayerOff.addActionListener(this);
		multiPlayerOff.setEnabled(false);
		multiPlayerOn.addActionListener(this);
		multiPlayerOff.setSelected(true);
		multiPlayerOn.setEnabled(false);
		ButtonGroup group = new ButtonGroup();
		group.add(multiPlayerOff);
		group.add(multiPlayerOn);
		radioButtonPanel = new JPanel(new GridLayout(1, 0));
		JLabel multi = new JLabel("Multiplayer:");
		multi.setEnabled(false);
		radioButtonPanel.add(multi);
		radioButtonPanel.add(multiPlayerOff);
		radioButtonPanel.add(multiPlayerOn);
		topPanel.add(radioButtonPanel);
	}
	/**
	 * Adds the players to the game.
	 */
	protected void addPlayers() {
		ArrayList<Player> playerCopy1 = userInterface.getPlayers();
		players.clear();
		players.addAll(playerCopy1);
	}
	/**
	 * Replaces the sliders that show the number of AI enemies with sliders that ask for the number of human players.
	 */
	protected void swapSlidersOut() {
		changeStatus(-6, false);
		playerLabel.setText("Choose the number of human players:");
		UserInterface.displayInfo("Choose the number of human players using the above (replaced) slider!",
				"Human player count needed!");
		Hashtable humanPlayerTable = new Hashtable();
		for (int i = 1; i <= playerCountSlider.getValue(); i++) { humanPlayerTable.put(new Integer(i), new JLabel("" + i)); }
		topPanel.remove(playerCountSlider);
		humanCountSlider = new JSlider(JSlider.HORIZONTAL, 1, playerCountSlider.getValue(), 2);
		humanCountSlider.setLabelTable(humanPlayerTable);
		humanCountSlider.setSnapToTicks(true);
		humanCountSlider.setMajorTickSpacing(1);
		humanCountSlider.setPaintTicks(true);
		humanCountSlider.setPaintLabels(true);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		addComponent(humanCountSlider, c, topPanel);
		topPanel.remove(radioButtonPanel);
		c.gridx = 2;
		c.fill = GridBagConstraints.NONE;
		addComponent(radioButtonPanel, c, topPanel);
		topPanel.revalidate();
		topPanel.repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// switch statement or just if conditions?
		// if conditions seem to be better
		// the following five conditions allow for using the buttons on the bottom without affecting the game in progress
		if (e.getSource() == clearButton) { userInterface.actionCount--; }
		if (e.getSource() == speedBox) { userInterface.actionCount--; }
		if (e.getSource() == cardBackOptionsBox) { userInterface.actionCount--; }
		if (e.getSource() == pauseButton) { userInterface.actionCount--; }
		if (e.getSource() == displayLivesLostBox) { userInterface.actionCount--; }

		userInterface.actionCount++;
		if (userInterface.actionCount == 1) {
			startGameButton.setText("Start Game");
			topPanel.revalidate();
			topPanel.repaint();
		}
		try {
			if (e.getSource() == startGameButton) {
				startGameAction();
			} else if (e.getSource() == nextTurnButton) {
				userInterface.doAITurns();
			} else if (e.getSource() == pauseButton) {
				paused = !paused;
				pauseAction();
			} else if (e.getSource() == finishRoundButton) {
				finishRound();
			} else if (e.getSource() == startRoundButton) {
				startRoundAction();
			} else if (e.getSource() == userInterface.getRepeatingTimer()) {
				repeatingTimerAction();
			} else if (e.getSource() == userInterface.getNonRepeatingTimer()) {
				nonRepeatingTimerAction();
			} else if (e.getSource() == quitButton) {
				System.exit(0);
			} else if (e.getSource() == pauseButton) {
				System.out.println("pause?");
				if (pauseButton.getText().equals("Pause")) {
					pauseButton.setText("Resume");
					try {
						Thread.sleep(3000);// wait 3 second before continuing
					} catch (InterruptedException exc) {
						UserInterface.displayError("Program interrupted.");
					}
				} else {
					pauseButton.setText("Pause");
				}
			} else if (e.getSource() == clearButton) {
				playArea.setText("");
			} else if (e.getSource() == speedBox) {
				speedBoxAction();
			} else if (e.getSource() == displayLivesLostBox) {
				livesLostBoxAction();
			} else if (e.getSource() == cardBackOptionsBox) {
				cardBackBoxAction();
			} else if (e.getSource() == gameModeOptions) { updateGameMode(); }
		} catch (Exception ex) {
			UserInterface.displayException(ex, 1);
		}

	}
	protected void startGameAction() {
		if (userInterface.actionCount == 1) {
			userInterface.defaultGame = true;
			userInterface.setUpDefaultGame();
			return;
		} // only set up changes
		else {
			userInterface.defaultGame = false;
		}
		changeStatus(-6, false);
		if (counter == 0) {
			if (priorIndex != 4) {
				addText("You have decided to play with " + playerCountSlider.getValue() + " players.\n");
				userInterface.setPlayerCount(playerCountSlider.getValue());
				userInterface.setLifeCount(lifeCountSlider.getValue());
				lifeCountSlider.setEnabled(false);
			}
			if (priorIndex == 4) {
				playerCountSlider.setEnabled(false);
				lifeCountSlider.setEnabled(false);
				changeStatus(-6, false);
				UserInterface.displayInfo(
						"You can change a lot of different things in custom mode!\n"
								+ "Note that the player and human counts can be changed to go beyond 11 players!\n"
								+ "Note that you must press Enter for your selection to be saved.",
						"Custom mode has been selected!");
				userInterface.setMode("custom");
				userInterface.startCustom();
			} else if (priorIndex == 3) {
				userInterface.setMultiPlayer(true);
				startGameButton.setText("Confirm");
				counter++;
				swapSlidersOut();
			} else if (priorIndex == 2) {
				userInterface.setMultiPlayer(true);
				changeStatus(-5, false);
				userInterface.startGame();
			} else {
				playerCountSlider.setEnabled(false);
				userInterface.setMultiPlayer(false);
				userInterface.setHumanCount(playerCountSlider.getValue());
				changeStatus(-5, false);
				userInterface.startGame();
			}
		} else {
			addText("You have decided to play with " + humanCountSlider.getValue() + " human players.\n");
			userInterface.setHumanCount(humanCountSlider.getValue());
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			topPanel.remove(humanCountSlider);
			addComponent(playerCountSlider, c, topPanel);
			playerLabel.setText("Choose the number of players:");
			playerCountSlider.setEnabled(false);
			lifeCountSlider.setEnabled(false);
			humanCountSlider.setEnabled(false);
			topPanel.remove(radioButtonPanel);
			c.gridx = 2;
			c.fill = GridBagConstraints.NONE;
			addComponent(radioButtonPanel, c, topPanel);
			changeStatus(-5, false);
			changeStatus(-6, false);
			startGameButton.setText("Start Game");
			counter = 0;
			topPanel.revalidate();
			topPanel.repaint();
			userInterface.startGame();

		}
	}
	/**
	 * Resets the hand's information regarding computer and player sums.
	 */
	protected void startRoundAction() {
		try {
			sumOfCPU.setText("Sum: ");
			cpuCards.setText("CPU Cards (round end):");
			userInterface.resetHands();
			changeStatus(-7, false);
			midInfo.setText("Mid cards: ");;
			updateCardLabels(0, -5);
			cpuImage1.setIcon(new ImageIcon(backImage));
			cpuImage2.setIcon(new ImageIcon(backImage));
			cpuImage3.setIcon(new ImageIcon(backImage));
			changeStatus(-1, false);
			changeStatus(-2, false);
			userInterface.deal();
		} catch (GameException e1) {
			UserInterface.displayException(e1, 1);
		} catch (Exception e1) {
			UserInterface.displayException(e1, 1);
		}
	}
	protected void repeatingTimerAction() {
		if (timerCounter == -1) {
			while (players.get(userInterface.getPlayerTurn()) == null) { userInterface.nextPlayer(); }
			// create a warning so that the player in turn can do their turn without showing the next player their cards
			if (players.get(userInterface.getPlayerTurn()) != null
					&& (players.get(userInterface.getPlayerTurn()).getClass().getName().equals("AI31.Human")
							|| userInterface.getPlayerTurn() == 0)
					&& userInterface.isMultiPlayer()) {
				clearPlayerHand();
			}
			if (players.get(userInterface.getPlayerTurn()) != null
					&& players.get(userInterface.getPlayerTurn()).getClass().getName().equals("AI31.Human")
					&& userInterface.isMultiPlayer()) {
				if (userInterface.playersAlive()) {
					UserInterface.displayWarning(
							"It is currently " + players.get(userInterface.getPlayerTurn()).getName() + " " + "("
									+ userInterface.getPlayerTurn() + ")'s turn.",
							"The player about to take their turn is a human.");
					updateSpecificPlayerHand();
				}
			}
			if (userInterface.playersAlive()) {
				try {
					userInterface.gameplay();
				} catch (GameException e1) {
					UserInterface.displayException(e1, 1);
				}
			} else {
				addText("\nAll player turns for the round are complete.\nPress the 'Finish Round' button to finish the round.\n\n");
				changeStatus(-1, true);
				changeStatus(-2, false);
				changeStatus(-3, true);
				changeStatus(-4, true);
				userInterface.startOrEndTimer(false);
				if (multiPlayerOn.isSelected()) { clearPlayerHand(); }
				addPlayers();
				playerCopy.clear();
				playerCopy.addAll(players);
			}
		}
		if (timerCounter == 3) {
			updateCardLabels(1, count);
			if (players.get(count) != null) { addText(players.get(count) + "\n"); }
			count++;
			if (count == players.size() - 1) {
				userInterface.startOrEndTimer(false);
				userInterface.finishEndOfRound();
				count = 0;
			}
		}

	}
	/**
	 * <html> The action the nonRepeatingTimer takes. <br>
	 * If timerCounter = 0:
	 * 
	 * @see {@link AI31.UserInterface#dealAgain()} <br>
	 *      If timerCounter = 2:
	 * @see {@link AI31.UserInterface#doAITurns()} </html>
	 */
	protected void nonRepeatingTimerAction() {
		if (timerCounter == 0) {
			userInterface.dealAgain();
		} else if (timerCounter == 2) {
			// first turn is done - do turns for the AIs
			userInterface.doAITurns();
		}

	}
	/**
	 * The result of clicking the box that says "Choose the speed of the game". Speed can be a decimal. It does not have to
	 * be a whole number.
	 */
	protected void speedBoxAction() {
		String selection = (String) speedBox.getSelectedItem();
		if (selection.equals(TIMER_OPTIONS[0])) {
		} else if (selection.equals(TIMER_OPTIONS[2])) {
			speed = 10;
		} else if (selection.equals(TIMER_OPTIONS[1])) {
			speed = 0;
		} else if (selection.equals(TIMER_OPTIONS[TIMER_OPTIONS.length - 1])) {
			while (true) {
				try {
					double quantity = Double.parseDouble(UserInterface.displayQuestion(
							"Enter the delay, in seconds, between turns you want " + "(it can be a decimal).",
							"How fast would you like the game to go?"));
					speed = (int) (quantity * 1000);
				} catch (NumberFormatException ex) {
					UserInterface.displayException(ex, 1);
				} catch (NullPointerException ex) {
					UserInterface.displayException(ex, 1);
				}
				break;
			}
		} else {
			for (int i = 3; i < TIMER_OPTIONS.length; i++) {
				if (selection.equals(TIMER_OPTIONS[i])) { speed = (i - 2) * 1000; }
			}
		}
		userInterface.updateTimer(speed);
		updateSpeed();
		userInterface.updateNonRepeatTimer(speed);
		speedBox.setSelectedItem(TIMER_OPTIONS[0]);

	}
	protected void livesLostBoxAction() {
		String selected = (String) displayLivesLostBox.getSelectedItem();
		if (selected.equals(DISPLAY_OPTIONS[1])) {
			showEliminations = NO_LIVES_NOR_ELIMINATIONS;
		} else if (selected.equals(DISPLAY_OPTIONS[2])) {
			showEliminations = NO_LIVES_AND_ELIMINATIONS;
		} else if (selected.equals(DISPLAY_OPTIONS[3])) {
			showEliminations = LIVES_NOT_ELIMINATIONS;
		} else if (selected.equals(DISPLAY_OPTIONS[4])) {
			showEliminations = LIVES_AND_ELIMINATIONS;
		} else {
		}
		displayLivesLostBox.setSelectedIndex(0);
		updateDisplaySetting();
	}
	protected void cardBackBoxAction() {
		if (cardBackOptionsBox.getSelectedIndex() != 0) {
			selectedBack = (cardBackOptionsBox.getSelectedItem() + "").toLowerCase();
			addText("You have decided to change the color of the card backs to " + selectedBack
					+ ".\nThis will be applied starting the next round.\n");
		}
		try {
			backImage = ImageIO.read(getClass().getResource("/Pictures/" + selectedBack + "_back.png")).getScaledInstance(-2,
					100, Image.SCALE_SMOOTH);
			cardBackOptionsBox.setSelectedIndex(0);
		} catch (IOException e) {
			UserInterface.displayException(e, 1);
		}
	}
	protected void pauseAction() {
		return;
		/*
		 * try { if(paused) {pauseButton.setText("Pause"); this.notify();} else {pauseButton.setText("Resume"); this.wait();}
		 * } catch(InterruptedException e) {}
		 */
	}
	protected void finishRound() {
		if (!userInterface.playersAlive()) {
			finishRoundButton.setEnabled(false);
			try {
				userInterface.endOfRound();
			} catch (Exception x) {
				UserInterface.displayException(x, 1);
			}
		}
	}
	protected void updatePlayerHand() {// the single player version
		addPlayers();
		playerInfo.setText("Your cards:");
		playerSum.setText("Sum: " + players.get(userInterface.getHumanPlayer()).sumOfHand());
		updateCardLabels(2, -6);
	}
	protected void updateSpecificPlayerHand() {
		addPlayers();
		if (!players.get(userInterface.getPlayerTurn()).getClass().getName().equals("AI31.Human")) { return; }
		playerInfo.setText(players.get(userInterface.getPlayerTurn()).getName() + "'s cards:");
		playerSum.setText("Sum: " + players.get(userInterface.getPlayerTurn()).sumOfHand());
		updateCardLabels(2, 1);
	}
	protected void clearPlayerHand() {
		playerInfo.setText("Player cards: ");
		playerSum.setText("Player sum");
		updateCardLabels(2, 0);
	}
	protected void updateMiddleHand() {
		addPlayers();
		midSum.setText("Sum: " + players.get(players.size() - 1).sumOfHand());
		updateCardLabels(0, -8);
	}
	protected void updateSpeed() {
		if (speed == 0) {
			UserInterface.displayWarning(
					"Be warned that the normal timer may not work. It still workes as\nintended otherwise. Let me know if the normal timer actualy does not work so I can fix it.",
					"Things get crazy when a speed of 0 seconds is enabled!");
		}
		speedLabel.setText("Current speed: " + (speed / 1000.0) + " second(s)");
	}
	protected void updateDisplaySetting() {
		otherDisplayLabel.setText("Display setting: \n" + DISPLAY_OPTIONS[showEliminations + 2] + ": " + (showEliminations));
	}
	protected void updateTimerCounter(int counter) { timerCounter = counter; }
	protected void addText(String text) {
		if (userInterface.getMode().equalsIgnoreCase("custom")) {
			if (firstCustomSelected || !userInterface.getCustomInfo().useTextArea) { return; }
		}
		playArea.append(text);
	}
	protected void addCustomText(Object text) { playArea.append(text.toString()); }
	/**
	 * <html> Used to set the enabled/disabled settings of varying important components.
	 * 
	 * @param object - the number of the object you want to change enable/disable settings for Object numbers: <br>
	 *            0 for nextTurnButton, <br>
	 *            -1 for finishRoundButton, <br>
	 *            -2 for startRoundButton, <br>
	 *            -3 for speedBox, <br>
	 *            -4 for displayLivesLostBox, <br>
	 *            -5 for startGameButton, <br>
	 *            -6 for gameModeOptions, <br>
	 *            -7 for showPreviousButton, and <br>
	 *            -8 for playArea
	 * @param status - whether you want the specified object enabled (true) or disabled (false) </html>
	 */
	protected void changeStatus(int object, boolean status) {
		/*
		 * Object numbers: 0 for nextTurnButton -1 for finishRoundButton -2 for startRoundButton -3 for speedBox -4 for
		 * displayLivesLostBox -5 for startGameButton -6 for gameModeOptions -7 for showPreviousButton -8 for playArea
		 */
		switch (object) {
			case 0 :
				nextTurnButton.setEnabled(status);
				break;
			case -1 :
				finishRoundButton.setEnabled(status);
				break;
			case -2 :
				startRoundButton.setEnabled(status);
				break;
			case -3 :
				speedBox.setEnabled(status);
				break;
			case -4 :
				displayLivesLostBox.setEnabled(status);
				break;
			case -5 :
				startGameButton.setEnabled(status);
				break;
			case -6 :
				gameModeOptions.setEnabled(status);
				break;
			case -7 :
				showPreviousButton.setEnabled(status);
				break;
			case -8 :
				playArea.setEnabled(status);
				break;
		}
	}
	protected void changeMidSum(int newSum) throws GameException {
		if (newSum == -1) {
			midSum.setText("Mid sum");
		} else {
			addPlayers();
			midSum.setText("Sum: " + players.get(players.size() - 1).sumOfHand());
		}
	}
	protected int getEliminationStatus() { return showEliminations; }
	protected static int getSpeed() { return speed; }
	protected void updateEliminationStatus(int status) { showEliminations = status; }
	protected void updateGameMode() {
		int ind = 0;
		if (gameModeOptions.getSelectedItem().equals(MODE_OPTIONS[1])) {
			userInterface.setMode("Competition");
			updateMultiButtons(false);
			ind = 1;
		} else if (gameModeOptions.getSelectedItem().equals(MODE_OPTIONS[2])) {
			userInterface.setMode("Friendly");
			updateMultiButtons(true);
			ind = 2;
		} else if (gameModeOptions.getSelectedItem().equals(MODE_OPTIONS[3])) {
			userInterface.setMode("Combination");
			updateMultiButtons(true);
			ind = 3;
		} else if (gameModeOptions.getSelectedItem().equals(MODE_OPTIONS[4])) {
			userInterface.setMode("Custom");
			ind = 4;
		} else {
			return;
		}
		if (ind != 0 && ind != 4) {
			addText("You have chosen the game mode " + MODE_OPTIONS[ind] + "\n");
		} else if (ind == 4) {
			addCustomText("You have chosen the game mode " + MODE_OPTIONS[4] + "\n"
					+ "Note that none of the above settings, except for the game mode, will save.\n");
		}
		if (priorIndex == 0) {
			priorIndex = 1;
		} else {
			priorIndex = gameModeOptions.getSelectedIndex();
		}
		gameModeOptions.setSelectedIndex(0);
	}
	protected void updateMultiButtons(boolean status) {
		if (status) {
			multiPlayerOn.setSelected(true);
			multiPlayerOff.setSelected(false);
		} else {
			multiPlayerOn.setSelected(false);
			multiPlayerOff.setSelected(true);
		}
	}
	protected void updateCardLabels(int type, int subType) {
		addPlayers();
		ArrayList<JLabel> list = new ArrayList<JLabel>();
		list.add(midImage1);
		list.add(midImage2);
		list.add(midImage3);
		list.add(cpuImage1);
		list.add(cpuImage2);
		list.add(cpuImage3);
		list.add(playerImage1);
		list.add(playerImage2);
		list.add(playerImage3);
		switch (type) {
			case 0 :// mid
				if (userInterface.getTurnStatus() != 1) {
					for (int i = 0; i < 3; i++) {
						list.get(i).setIcon(new ImageIcon(players.get(players.size() - 1).getCard(i).getImage()));
					}
					break;
				} else {
					for (int i = 0; i < 3; i++) { list.get(i).setIcon(new ImageIcon(backImage)); }
					break;
				}
			case 1 :// cpu
				if (!players.get(userInterface.getPlayerTurn()).getClass().getName().equals("AI31.Machine")) { return; }
				if (userInterface.getTurnStatus() == -1 && !userInterface.playersAlive()) {
					for (int i = 3; i < 6; i++) {
						if (players.get(subType) != null) {
							list.get(i).setIcon(new ImageIcon(players.get(subType).getCard(i - 3).getImage()));
						}
					}
					if (players.get(subType) != null) { sumOfCPU.setText("Sum: " + players.get(subType).sumOfHand()); }
					break;
				} else {
					for (int i = 3; i < 6; i++) { list.get(i).setIcon(new ImageIcon(backImage)); }
					break;
				}
			case 2 :// player
				if (!userInterface.isMultiPlayer()) {
					for (int i = 6; i < 9; i++) {
						list.get(i).setIcon(
								new ImageIcon(players.get(userInterface.getHumanPlayer()).getCard(i - 6).getImage()));
					}
					break;
				} else {
					if (subType == 0) {
						for (int i = 6; i < 9; i++) { list.get(i).setIcon(new ImageIcon(backImage)); }
						break;
					} // cards disabled/hidden
					else if (subType == 1) {
						if (players.get(userInterface.getPlayerTurn()) != null) {
							for (int i = 6; i < 9; i++) {
								list.get(i).setIcon(
										new ImageIcon(players.get(userInterface.getPlayerTurn()).getCard(i - 6).getImage()));
							}
							break;
						}
					} // cards enabled/visible
					playerSum.setText("Sum: " + players.get(userInterface.getPlayerTurn()).sumOfHand());
				}
				break;
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
