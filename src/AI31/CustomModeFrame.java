package AI31;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import baselineCustomClasses.GameException;
import baselineCustomClasses.PlayingCard;
import baselineCustomClasses.PlayingCardException;
/**
 * <html>
 * There are many different things you can customize in the game, which include the following 
 * (other settings can be changed in the UserInterface): <br>
 * Lowest card number (between 2 and 8 or even 2 and 9) <br>
 * Card values (special value for ace that is higher than the high card number value), <br>
 * Specifically available cards (do you want to restrict cards for the game to a
 * specific set and give them each a preset hand for a set number of rounds?) <br>
 * Players (up to 12),<br>
 * Human players (up to 12 - if equal to or larger than the number of players not 
 * including the middle hand, it will make the game mode Friendly), <br>
 * Lives (at least one per player, no upper limit - go crazy!), <br>
 * Starting position (for combination and competition - will be decided by number of human players), <br>
 * (for combination) where each human player is located, <br>
 * and whether to use the text area in the middle.
 * @author Dong-Yon
 * </html>
 */
@SuppressWarnings("serial")
/**
 * This class contains the setup for the custom game mode of the AI31 game.
 * @author dong-yonkim
 *
 */
public class CustomModeFrame extends JFrame implements ActionListener, AI31Constants, ItemListener, WindowListener{
	/**
	 * The panel that hosts all of the components.
	 */
	private JPanel primaryPanel = new JPanel(new GridBagLayout());
	/**
	 * A field that is used to input the lowest card number.
	 */
	private JTextField lowCardNum;
	/**
	 * A label used to describe the above text field.
	 */
	private JLabel lowCardLab;
	/**
	 * 
	 */
	private JTextField aceValue;
	/**
	 * 
	 */
	private JLabel aceLab;
	/**
	 * 
	 */
	private JTextField jackValue;
	/**
	 * 
	 */
	private JLabel jackLab;
	private JTextField playerCount;
	private JLabel playerLab;
	private JTextField humanCount;
	private JLabel humanLab;
	private JRadioButton specificCardsOn;
	private JRadioButton specificCardsOff;
	private JTextField lifeCount;
	private JLabel lifeLab;
	private JTextField startingPosition;
	private JLabel startingPosLab;
	private JTextField startingIndex;
	private JLabel startingIndexLab;
	private JButton end;
	//for human player locations use 12 JCheckBoxes - they will be placed in order
	private JCheckBox[] checkBoxes;
	private ArrayList<JComponent> specificCardsFeatures;
	private ArrayList<JComponent> generalComponents;
	private ArrayList<Card31> availableCards;
	private ArrayList<Card31> availableCardsCopy;
	private ArrayList<String> removedCardsToString = new ArrayList<String>();
	private JRadioButton textAreaOn;
	private JRadioButton textAreaOff;
	private JRadioButton equalFacesOn;
	private JRadioButton equalFacesOff;
	private JLabel humanLocsInfo;
	private JLabel humanInfo;
	private JLabel startPos;
	private JLabel aVal;
	private JLabel lowInfo;
	private JLabel startIndexLab;
	private CustomModeInfo info;
	private SpecificCardsInfo sci;
	private JButton okButton;
	private int checkedBoxesCount;
	private UserInterface uI;
	private int oldSinglePlayerLoc;
	private int randomCount = -2;
	private int randomCount2 = -1;
	private static final int ALL_ROUNDS = -2;		
	protected final SuitRandom antiQuantity = new SuitRandom(-15353, -15353);//this should but can't be static, regardless of where I place it
	/**
	 * A subclass of CustomModeFrame that holds the information provided by the frame.
	 * @author dong-yonkim
	 *
	 */
	class CustomModeInfo{
		protected int lowNum;
		protected int aceVal;
		protected int jackVal;
		protected int humanCt;
		protected int playerCt;
		protected int lifeCt;
		protected boolean specificCards;
		protected boolean useTextArea;
		protected boolean equalFaceCards;
		protected int singlePlayerLoc;
		protected ArrayList<Integer> multiPlayerLoc;
		protected int startingPlayerLoc;
		/**
		 * Creates a new set of custom info with default values for all.
		 */
		public CustomModeInfo() {
			lowNum = 7;
			aceVal = 11;
			jackVal = 10;
			humanCt = -1;
			playerCt = 4;
			lifeCt = 5;
			specificCards = false;
			useTextArea = true;
			equalFaceCards = true;
			singlePlayerLoc = -1;
			multiPlayerLoc = new ArrayList<Integer>();
			startingPlayerLoc = 0;
		}
		/**
		 * Creates a new set of custom mode info with the given parameters.
		 * @param ln - the lowest number in the deck of cards (say the deck was from 7 to A, then the lowest number is 7)
		 * @param av - how much aces are worth
		 * @param jv - how much jacks are worth
		 * @param hc - how many human players there are
		 * @param pc - how many total players there are
		 * @param lc - how many lives per person exist
		 * @param sc - do players start with specific cards or not?
		 * @param uta - will the main text area be used?
		 * @param efc - are the face card values all equal to each other?
		 * @param spl - the location of the human player in regards to the players arraylist (competition custom only)
		 * @param mpl - the locations of human players in regards to the players arraylist (combination custom only)
		 * @param startLoc - the location of the starting player in the players array
		 */
		public CustomModeInfo(int ln, int av, int jv, int hc, int pc, int lc, boolean sc, boolean uta, boolean efc, int spl, ArrayList<Integer> mpl, int startLoc) {
			lowNum = ln;
			aceVal = av;
			jackVal = jv;
			humanCt = hc;
			playerCt = pc;
			lifeCt = lc;
			specificCards = sc;
			useTextArea = uta;
			equalFaceCards = efc;
			singlePlayerLoc = spl;
			multiPlayerLoc = new ArrayList<Integer>();
			startingPlayerLoc = startLoc;
		}
		/**
		 * Creates a new set of custom mode info by copying the information from another CustomModeInfo.
		 * @param in - the CustomModeInfo object you want to copy information from
		 */
		public CustomModeInfo(CustomModeInfo in) {
			this.lowNum = in.lowNum;
			this.aceVal = in.aceVal;
			this.jackVal = in.jackVal;
			this.humanCt = in.humanCt;
			this.playerCt = in.playerCt;
			this.lifeCt = in.lifeCt;
			this.specificCards = in.specificCards;
			this.useTextArea = in.useTextArea;
			this.equalFaceCards = in.equalFaceCards;
			this.singlePlayerLoc = in.singlePlayerLoc;
			this.multiPlayerLoc = in.multiPlayerLoc;
			this.startingPlayerLoc = in.startingPlayerLoc;
		}
		@Override
		public String toString() {
			String info = "\n";
			info+="Lowest Card Number: "+lowNum;
			info+="\nAce Value: "+aceVal;
			info+="\nJack Value: "+jackVal;
			info+="\nHuman Count: "+humanCt;
			info+="\nPlayer Count: "+playerCt;
			info+="\nLife Count: "+lifeCt;
			info+="\nSpecific Cards enabled: "+specificCards;
			info+="\nText Area enabled: "+useTextArea;
			info+="\nEqual Face Cards enabled: "+equalFaceCards;
			info+="\nSingle Player location: "+singlePlayerLoc;
			info+="\nMulti Player locations: "+multiPlayerLoc;
			info+="\nStarting Player location: "+startingPlayerLoc;
			return info;
		}
	}
	/**
	 * A bit of bonus player information for the frame: what cards and what index each player have/is
	 * @author dong-yon kim
	 *
	 */
	class PlayerCard{
		protected Card31[] cards;
		protected int index;
		private PlayerCard() {
			index = -1;
			cards = new Card31[3];
		}
		private PlayerCard(int index) {
			this();
			this.index = index;
		}
		@Override
		public String toString(){
			String info = "";
			info+="\nIndex: "+index;
			for(int i=0; i<3; i++){if(cards[i]!=null) {info+="\nCard #"+i+": "+cards[i].toString();}}
			return info;
		}
		@Override
		public boolean equals(Object arg0) {
			PlayerCard ha = (PlayerCard)arg0;
			for(Card31 c : cards) {for(Card31 d : ha.cards) {if(c==null || d==null || !c.equals(d)) {return false;}}}
			if(index!=ha.index) {return false;}
			return true;
		}
	}
	class SpecificCardsInfo{
		protected ArrayList<String> availableSuits;
		protected ArrayList<Integer> availableNums;
		protected ArrayList<Character> availableFaces;
		protected boolean specificCardsEnabled;
		protected ArrayList<PlayerCard> playerCards;
		protected int specificRoundCt;
		protected ArrayList<Card31> allAvailableCards;
		protected SuitRandom[] suitQuantities;
		protected int cardsAddedToPlayers;
		public SpecificCardsInfo() {
			availableSuits = new ArrayList<String>();
			availableNums = new ArrayList<Integer>();
			availableFaces = new ArrayList<Character>();
			specificCardsEnabled = false;
			//specificCardsEnabled is for each player having their own specific pre-dealt cards
			playerCards = new ArrayList<PlayerCard>(14);
			for(int i=0; i<14; i++) {playerCards.add(new PlayerCard(i));}//0 to 12 for players, 13 for mid
			specificRoundCt = 0;
			allAvailableCards = new ArrayList<Card31>();
			suitQuantities = new SuitRandom[4];
			for(int i=0; i<suitQuantities.length; i++) {suitQuantities[i] = antiQuantity;}
			cardsAddedToPlayers = 0;
			//index 0 is clubs, 1 diamonds, 2 hearts, 3 spades
		}
		@Override
		public String toString(){
			String info = "";
			info+="\nAvaiable suits: "+availableSuits.toString();
			info+="\nAvailable numbers: "+availableNums.toString();
			info+="\nAvailable faces: "+availableFaces.toString();
			info+="\nSpecific cards enabled: "+specificCardsEnabled;
			if(playerCards!=null) {info+="\nPlayer cards: "+playerCards.toString();}
			info+="\nSpecific cards round count: "+specificRoundCt;
			info+="\n"+Arrays.toString(suitQuantities);
			info+="\nCards added to players: "+cardsAddedToPlayers;
			return info;
		}
	}
	class SuitRandom{
		protected int bottom;
		protected int top;
		protected int randomVal;
		public SuitRandom() {
			bottom = 0;
			top = 1;
			randomVal = new Random().nextInt(2);
		}
		public SuitRandom(int bottom, int top) {
			this.bottom = bottom;
			this.top = top;
		}
		public int randomize() {
			randomVal = new Random().nextInt(top+1-bottom) + bottom;
			System.out.println(randomVal+": RANDOM VALUE (is it zero?)");
			return randomVal;
		}
		public String toString() {
			String info = "Bottom: "+bottom;
			info+="\nTop: "+top;
			info+="\nGenerated Random Value: "+randomVal;
			return info;
		}
	}
	/**
	 * @deprecated As of v.7.0, replaced with CustomModeFrame(UserInterface). Note that this constructor does nothing at all.
	 */
	private CustomModeFrame() {
		super("31 Custom Settings (more are on the UserInterface)");
		info = new CustomModeInfo();
		sci = new SpecificCardsInfo();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUpPrimaryFrame();
	}
	/**
	 * This is a test constructor that does literally and absolutely nothing at all. 
	 * @param b - a boolean (that does nothing)
	 * @deprecated Do not use this constructor. Instead, use the constructor 
	 * CustomModeFrame(UserInterface) as the two need to cross-reference each other.
	 */
	public CustomModeFrame(boolean b) {}
	/**
	 * Sets up the CustomModeFrame to display the available settings that can be changed in the game.
	 * @param u - the UserInterface you want the data to be sent to
	 */
	public CustomModeFrame(UserInterface u) {
		super("31 Custom Settings (more are on the UserInterface)");
		uI = u;
		info = new CustomModeInfo();
		sci = new SpecificCardsInfo();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		super.addWindowListener(this);
		setUpPrimaryFrame();
	}
	@Override
	/**
	 * Records all of the RadioButton, ComboBox, and Button actions and runs the appropriate method to correspond with the pressed button.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == lowCardNum) {lowCards();}
			if(e.getSource() == aceValue){aceValue();}
			if(e.getSource() == jackValue) {jackValue();}
			if(e.getSource() == playerCount){playerCount();}
			if(e.getSource() == humanCount) {humanCount();}
			if(e.getSource() == textAreaOff) {info.useTextArea = false;}
			if(e.getSource() == textAreaOn) {info.useTextArea = true;}
			if(e.getSource() == specificCardsOn) {specificCardsOn();}
			if(e.getSource() == specificCardsOff) {specificCardsOff();}
			if(e.getSource() == equalFacesOff) {equalFaces(false);}
			if(e.getSource() == equalFacesOn) {equalFaces(true);}
			if(e.getSource() == lifeCount) {lifeCount();}
			if(e.getSource() == startingPosition){startingPosition();}
			if(e.getSource() == startingIndex) {startingIndex();}
			if(e.getSource() == okButton) {okButton();}
			if(e.getSource() == specificCardsFeatures.get(22)) {confirmTop();}
			if(e.getSource() == specificCardsFeatures.get(24)) {removeACard();}//choose a card to remove
			if(e.getSource() == specificCardsFeatures.get(25)) {checkCards();}
			//((JComboBox)specificCardsFeatures.get(28)).getSelectedIndex();
			if(e.getSource() == specificCardsFeatures.get(26)) {confirmMid();}//confirm mid
			if(e.getSource() == specificCardsFeatures.get(28)) {pIndexBox();}//combo box
			if(e.getSource() == specificCardsFeatures.get(29)) {addCardButton(((JComboBox)specificCardsFeatures.get(28)).getSelectedIndex());}//buttons
			if(e.getSource() == specificCardsFeatures.get(30)) {removeCardButton(((JComboBox)specificCardsFeatures.get(28)).getSelectedIndex());}//buttons
			if(e.getSource() == specificCardsFeatures.get(32)) {numRounds(1);}
			if(e.getSource() == specificCardsFeatures.get(33)) {numRounds(-1);}
			if(e.getSource() == specificCardsFeatures.get(34)) {numRounds();}
			if(e.getSource() == specificCardsFeatures.get(35)) {howManyCards();}
			if(e.getSource() == specificCardsFeatures.get(36)) {seeHowManyCards();}
			if(e.getSource() == specificCardsFeatures.get(38)) {confirmBottom();}//confirm bottom
			if(e.getSource() == end) {this.dispose();}
		}
		catch(NumberFormatException ex) {UserInterface.displayError("Please do not enter characters that do not belong.");}
		catch(GameException ex) {UserInterface.displayException(ex, 2); System.exit(0);}
		catch(Exception ex) {UserInterface.displayException(ex, 2);}
	}
	/**
	 * Executed when the OK button is pressed on the custom mode frame.
	 */
	private void okButton() {
		if(info.multiPlayerLoc!=null) {info.multiPlayerLoc.clear();}
		else {info.multiPlayerLoc = new ArrayList<Integer>();}
		if(info.humanCt == -1) {info.humanCt = 1;}
		if(info.humanCt == 1) {info.multiPlayerLoc.clear(); info.multiPlayerLoc.add(-1);}
		else{for(int i=0; i<info.playerCt; i++) {if(checkBoxes[i].isSelected()){info.multiPlayerLoc.add(new Integer(i));}}}
		//say low card number is 7 - index 5 or 9 - index 7
		for(JComponent comp : generalComponents){comp.setEnabled(false);}
		if((16-info.playerCt)<info.lowNum || info.lowNum>9) {
			lowCardNum.setText(""+(16-info.playerCt));
			lowCards();
		}
		if(info.startingPlayerLoc == -1) {startingIndex.setText(""+(info.playerCt-1)); startingIndex();}
		checkedBoxesCount = 0;
		for(int i=0; i<checkBoxes.length; i++) {if(checkBoxes[i].isSelected()) {checkedBoxesCount++;}}
		if(info.specificCards) {
			for(int i=0; i<23; i++) {specificCardsFeatures.get(i).setEnabled(true);}
			for(int i=6; i<4+info.lowNum; i++) {specificCardsFeatures.get(i).setEnabled(false);}
		}
		else {
			sci.allAvailableCards = null;
			uI.setUpCustom();
			dispose();
			setVisible(false);
			return;
		}
		if(info.singlePlayerLoc == -1 && info.humanCt == 1) {info.singlePlayerLoc = 0;}
		//UserInterface method to start the game
	}
	/**
	 * Disables specific cards in this custom mode frame.
	 */
	private void specificCardsOff() {info.specificCards = false;}
	/**
	 * Enables specific cards in this custom mode frame.
	 */
	private void specificCardsOn() {info.specificCards = true;}
	/**
	 * Confirms the top button options. 
	 */
	private void confirmTop() {
		try {
			try {
				PlayingCard.setValueConditions(PlayingCard.CHANGE_JACK, !info.equalFaceCards, info.jackVal);
				PlayingCard.setValueConditions(PlayingCard.CHANGE_ACE, true, info.aceVal);
			}
			catch(PlayingCardException e) {UserInterface.displayException(e, 2);}
			availableCards = new ArrayList<Card31>();
			availableCardsCopy = new ArrayList<Card31>();
			availableCards.clear();
			availableCardsCopy.clear();
			sci.availableNums.clear();
			sci.availableSuits.clear();
			sci.availableFaces.clear();
			if(((JRadioButton)specificCardsFeatures.get(20)).isSelected()) {sci.specificCardsEnabled = false;}
			else {sci.specificCardsEnabled = true;}
			if(((JCheckBox)specificCardsFeatures.get(1)).isSelected()) {sci.availableSuits.add("Clubs");}
			if(((JCheckBox)specificCardsFeatures.get(2)).isSelected()) {sci.availableSuits.add("Diamonds");}
			if(((JCheckBox)specificCardsFeatures.get(3)).isSelected()) {sci.availableSuits.add("Hearts");}
			if(((JCheckBox)specificCardsFeatures.get(4)).isSelected()) {sci.availableSuits.add("Spades");}
			for(int i=6; i<15; i++) {if(((JCheckBox)specificCardsFeatures.get(i)).isSelected()) {sci.availableNums.add(i-4);}}
			/*
			if(((JCheckBox)specificCardsFeatures.get(6)).isSelected()) {sci.availableNums.add(2);}
			if(((JCheckBox)specificCardsFeatures.get(7)).isSelected()) {sci.availableNums.add(3);}
			if(((JCheckBox)specificCardsFeatures.get(8)).isSelected()) {sci.availableNums.add(4);}
			if(((JCheckBox)specificCardsFeatures.get(9)).isSelected()) {sci.availableNums.add(5);}
			if(((JCheckBox)specificCardsFeatures.get(10)).isSelected()) {sci.availableNums.add(6);}
			if(((JCheckBox)specificCardsFeatures.get(11)).isSelected()) {sci.availableNums.add(7);}
			if(((JCheckBox)specificCardsFeatures.get(12)).isSelected()) {sci.availableNums.add(8);}
			if(((JCheckBox)specificCardsFeatures.get(13)).isSelected()) {sci.availableNums.add(9);}
			if(((JCheckBox)specificCardsFeatures.get(14)).isSelected()) {sci.availableNums.add(10);}
			*/
			for(int i=15; i<19; i++) {if(((JCheckBox)specificCardsFeatures.get(i)).isSelected()) 
			{sci.availableNums.add(i-4); sci.availableFaces.add(FACE_CARDS[i-15]);}}
			/*
			if(((JCheckBox)specificCardsFeatures.get(15)).isSelected()) {sci.availableNums.add(11); sci.availableFaces.add('J');}
			if(((JCheckBox)specificCardsFeatures.get(16)).isSelected()) {sci.availableNums.add(12); sci.availableFaces.add('Q');}
			if(((JCheckBox)specificCardsFeatures.get(17)).isSelected()) {sci.availableNums.add(13); sci.availableFaces.add('K');}
			if(((JCheckBox)specificCardsFeatures.get(18)).isSelected()) {sci.availableNums.add(14); sci.availableFaces.add('A');}
			*/
			if(((JRadioButton)specificCardsFeatures.get(20)).isSelected()) {sci.specificCardsEnabled = true;}//22 is the button	
			else {sci.specificCardsEnabled = false; sci.playerCards = null;}
			for(int i=0; i<sci.availableNums.size(); i++) {
				for(int j=0; j<sci.availableSuits.size(); j++) {
					switch(sci.availableNums.get(i)) {
					case 11:
						availableCards.add(new Card31('J', sci.availableSuits.get(j)));
						break;
					case 12:
						availableCards.add(new Card31('Q', sci.availableSuits.get(j)));
						break;
					case 13:
						availableCards.add(new Card31('K', sci.availableSuits.get(j)));
						break;
					case 14:
						availableCards.add(new Card31('A', sci.availableSuits.get(j)));
						break;
					default:
						availableCards.add(new Card31(sci.availableNums.get(i), sci.availableSuits.get(j)));
						break;
					}
				}
			}
			if(availableCards.size()<(info.playerCt*3)){//enough cards for a set number of players and a middle hand
				//lacking enough cards to be successful
				int extraNeededCards = (info.playerCt*3)-availableCards.size();
				UserInterface.displayError("Please change your card settings to allow for at least "+extraNeededCards+" extra cards to be added.");
				return;
			}
			//if(!sci.specificCardsEnabled) {sci.allAvailableCards = availableCards; uI.setUpCustom(); return;}
			for(int i=0; i<23; i++) {specificCardsFeatures.get(i).setEnabled(false);}

			for(int i=23; i<27; i++) {specificCardsFeatures.get(i).setEnabled(true);}//hidden JLabel
			String[] pIndexes = new String[info.playerCt];
			for(int i=0; i<info.playerCt-1; i++) {pIndexes[i] = "Player Index #"+i;}
			pIndexes[info.playerCt-1] = "The Middle Hand";
			DefaultComboBoxModel d = new DefaultComboBoxModel(pIndexes);
			((JComboBox)specificCardsFeatures.get(28)).setModel(d);
			ArrayList<String> avCards = toStringArrayList(toStringArray(availableCards));
			avCards.add(0, "Choose a card to remove:");
			DefaultComboBoxModel mod = new DefaultComboBoxModel(avCards.toArray(new String[3]));
			((JComboBox)specificCardsFeatures.get(24)).setModel(mod);
			availableCardsCopy.addAll(availableCards);
		}
		catch(Exception e) {UserInterface.displayException(e, 2);}
	}
	private void confirmMid() {
		String[] allCards = toStringArray(availableCards);
		for(String s : removedCardsToString) {for(int i=0; i<allCards.length; i++) {if(s.equals(allCards[i])) {availableCards.remove(i);}}}
		for(int i=23; i<27; i++) {specificCardsFeatures.get(i).setEnabled(false);}
		boolean e = ((JRadioButton)specificCardsFeatures.get(21)).isSelected();//the No Button
		if(e){
			sci.allAvailableCards = availableCards;
			uI.setUpCustom();
			dispose();
			setVisible(false);
			return;
		}
		else{for(int i=27; i<specificCardsFeatures.size(); i++) {specificCardsFeatures.get(i).setEnabled(true);}}
		totalCardCount();
	}
	private void confirmBottom() {
		for(int i=27; i<39; i++) {specificCardsFeatures.get(i).setEnabled(false);}
		JRadioButton one = (JRadioButton)(specificCardsFeatures.get(32));
		JRadioButton two = (JRadioButton)(specificCardsFeatures.get(33));
		if(one.isSelected()) {sci.specificRoundCt = 1;}
		else if(two.isSelected()) {sci.specificRoundCt = CustomModeFrame.ALL_ROUNDS;}
		for(PlayerCard pc : sci.playerCards) {for(Card31 c : pc.cards) {if(c!=null && c.getSuit().equalsIgnoreCase("not a card!")) {c = null;}}}
		System.out.println(sci.toString());
		sci.allAvailableCards = availableCards;
		uI.setUpCustom();
		dispose();
		setVisible(false);
	}
	private void equalFaces(boolean cond) {info.equalFaceCards = cond;}
	private void humanCount() {
		if(humanCount.getText().length()==0) {
			UserInterface.displayError("Please enter a value for the number of human players. Decimals are truncated.");
			return;
		}
		int hc = (int)Double.parseDouble(humanCount.getText());
		if(hc<1) {hc = 1; UserInterface.displayError("Please do not enter a number less than 1 for the human count."); humanCount.setText("1");}
		if(hc>=info.playerCt) {hc = info.playerCt-1; UserInterface.displayError("Please do not enter a number larger than "
				+ "or equal to the player count for the human count.");	humanCount.setText(""+hc);}
		info.humanCt = hc;
		if(hc==1) {
			startPos.setEnabled(true);
			startingPosition.setEnabled(true); 
			startingPosLab.setEnabled(true);
			humanLab.setText("Value: 1");
			UserInterface.displayInfo("You have selected the game mode 1: Competition.", "Selected Mode"); 
			humanLocsInfo.setEnabled(false);
			info.multiPlayerLoc.clear();
			info.multiPlayerLoc.add(-1);
			info.singlePlayerLoc = oldSinglePlayerLoc;
			for(int i=0; i<info.playerCt-1; i++) {checkBoxes[i].setEnabled(false); checkBoxes[i].setSelected(false);}
			return;
		}
		else if(hc>=info.playerCt-1) {
			humanLab.setText("Value: "+(info.playerCt-1));
			UserInterface.displayInfo("You have selected the game mode 2: Friendly.", "Selected Mode");
			humanLocsInfo.setEnabled(false);
			for(int i=0; i<info.playerCt-1; i++) {checkBoxes[i].setSelected(true); checkBoxes[i].setEnabled(false);}
			info.singlePlayerLoc = -1;
			if(info.multiPlayerLoc.size()==1) {if(info.multiPlayerLoc.get(0) == -1) {info.multiPlayerLoc.clear();}}
			for(JCheckBox box : checkBoxes) {box.setEnabled(false);}
			return;
		}
		else {UserInterface.displayInfo("You have selected the game mode 3: Combination.", "Selected Mode"); for(JCheckBox box : checkBoxes) {box.setSelected(false);}}
		humanLab.setText("Value: "+hc);
		humanLocsInfo.setEnabled(true);
		for(int i=0; i<info.playerCt-1; i++) {checkBoxes[i].setEnabled(true);}
		for(int i=info.playerCt-1; i<checkBoxes.length; i++) {checkBoxes[i].setEnabled(false); checkBoxes[i].setSelected(false);}
		startingPosLab.setEnabled(false);
		startPos.setEnabled(false);
		startingPosition.setEnabled(false);
		info.singlePlayerLoc = -1;
		if(info.multiPlayerLoc.size()==1) {if(info.multiPlayerLoc.get(0) == -1) {info.multiPlayerLoc.clear();}}
	}
	private void playerCount() {
		if(playerCount.getText().length()!=0){
			int pc = (int)Double.parseDouble(playerCount.getText());
			if(pc<3){pc = 3; UserInterface.displayInfo("The player count will be truncated to 3 players.");}
			else if(pc>13){pc = 13; UserInterface.displayInfo("The player count will be truncated to 13 players.");}
			info.playerCt = pc;
			humanInfo.setEnabled(true);
			humanCount.setEnabled(true);
			humanLab.setEnabled(true);
			lowInfo.setEnabled(true);
			lowCardNum.setEnabled(true);
			lowCardLab.setEnabled(true);
			startingIndex.setEnabled(true);
			startIndexLab.setEnabled(true);
			startingIndexLab.setEnabled(true);
			playerLab.setText("Value: "+pc);
			boolean needToRedo = false;
			if(info.humanCt>1) {humanCount.setText(""+info.humanCt); humanCount(); needToRedo = true;}
			else if(info.playerCt == info.humanCt) {humanCount();}
			else if(info.playerCt<info.humanCt) {humanCount.setText(""+info.playerCt); humanCount(); needToRedo = true;}
			if(needToRedo) {}
		}
		else{UserInterface.displayError("Please enter a value for the number of players. Decimals are truncated.");}
	}
	private void lowCards() {
		if(lowCardNum.getText().length() == 0) {
			UserInterface.displayError("Please enter a value for the lowest card number. ");
			return;
		}
		UserInterface.displayInfo("Note that the minimum card number, if larger than 16-# of players,\n"
				+ "will not be accepted at all. Note that the minimum card number is 9, unless playing with 8\n"
				+ "or more players. If so, the minimum card number is 16-(player count). Thank you.\n"
				+ "Note that the default minimum card number is 16 minus the number of players.\n"
				+ "Also note that these consider the middle hand in the player count.");
		int lowNum = 0;
		try{lowNum = (int)Double.parseDouble(lowCardNum.getText());}
		catch(NumberFormatException e1) {UserInterface.displayException(e1, 2); lowCardNum.setText(""); return;}
		if(lowNum>(16-info.playerCt)) {UserInterface.displayError("Please enter a valid smaller number (16-player count or smaller) for the minimum card number."); lowCardNum.setText(""); return;}
		else if(lowNum<2) {UserInterface.displayError("Please do not enter a value smaller than 2 for the low card number."); lowCardNum.setText(""); return;}
		else if(lowNum>9) {UserInterface.displayError("Please do not enter a value larger than 9 for the low card number."); lowCardNum.setText(""); return;}
		info.lowNum = lowNum;
		lowCardLab.setText("Value: "+lowNum);
	}
	private void aceValue(){
		if(aceValue.getText().length() == 0) {
			UserInterface.displayError("Please enter a value for the value of each ace. Decimals are truncated.");
			return;
		}
		int val = (int)Double.parseDouble(aceValue.getText());
		if(val<9) {UserInterface.displayError("Please do not enter a value smaller than 9 for the value of each ace."); aceValue.setText(""); return;}
		if(val>15) {UserInterface.displayError("Please do not enter a value larger than 15 for the value of each ace."); aceValue.setText(""); return;}
		aceLab.setText("Value: "+val);
		info.aceVal = val;
	}
	private void jackValue() {
		if(jackValue.getText().length() == 0) {
			UserInterface.displayError("Please enter a value for the value of each jack. Decimals are truncated.");
			return;
		}
		int val = (int)Double.parseDouble(jackValue.getText());
		if(val<9) {UserInterface.displayError("Please do not enter a value smaller than 9 for the value of each jack."); jackValue.setText(""); return;}
		if(val>14) {UserInterface.displayError("Please do not enter a value larger than 14 for the value of each jack."); jackValue.setText(""); return;}
		jackLab.setText("Value: "+val);
		info.jackVal = val;
	}
	private void lifeCount() {
		if(lifeCount.getText().length() == 0) {
			UserInterface.displayError("Please enter a value for the number of lives each player has. Decimals are truncated.");
			return;
		}
		int life = (int)Double.parseDouble(lifeCount.getText());
		if(life<1) {UserInterface.displayError("Please do not enter a value smaller than 1 for the number of lives each player has."); lifeCount.setText(""); return;}
		lifeLab.setText("Value: "+life);
		info.lifeCt = life;
	}
	private void startingPosition() throws GameException{
		if(startingPosition.getText().length() == 0) {
			UserInterface.displayError("Please enter a starting position value. Decimals are truncated.");
			return;
		}
		if(info.humanCt!=1) {throw new GameException("No starting position should be avaiable for multiplayer.", "Z6146");}
		int pos = (int)Double.parseDouble(startingPosition.getText());
		if(pos<0) {UserInterface.displayError("Please do not enter an index smaller than 0 for your starting position."); startingPosition.setText(""); return;}
		if(pos>=info.playerCt-1) {UserInterface.displayError("Please do not enter an index larger than or equal to the number "
				+ "of players -1 (mid is a player) for your starting position."); startingPosition.setText(""); return;}
		startingPosLab.setText("Value: "+pos);
		info.singlePlayerLoc = pos;
		oldSinglePlayerLoc = pos;
	}
	private void startingIndex() {
		if(startingIndex.getText().length() == 0) {
			UserInterface.displayError("Please enter a value for the first starting player's index (who starts?). Decimals are truncated.");
			return;
		}
		int val = (int)Double.parseDouble(startingIndex.getText());
		if(val<0) {UserInterface.displayError("Please do not enter a value smaller than 0 for the first starting player's index."); startingIndex.setText(""); return;}
		if(val>=info.playerCt-1) {UserInterface.displayError("Please do not enter a value larger than or equal to the number of players "
				+ "(excluding the middle hand) for the first starting player's index."); startingIndex.setText(""); return;}
		startingIndexLab.setText("Value: "+val);
		info.startingPlayerLoc = val;
		if(uI!=null) {uI.startingPlayerSet = true;}
	}
	/**
	 * Gives a placeholder card to a player. 
	 * @param index - which player is getting the cards?
	 */
	private void addCardButton(int index){
		JPanel p = createPanel();
		p.add(new JLabel("Choose a card to add for player index #"+index+":"));
		ArrayList<String> strings = Card31.generateRandomStrings(sci.availableSuits);
		String[] cards = new String[availableCards.size()+strings.size()];
		for(int i=0; i<availableCards.size(); i++) {cards[i] = availableCards.get(i).toString();}
		for(int i=availableCards.size(); i<cards.length; i++) {cards[i] = strings.get(i-availableCards.size());}
		//remove all the cards with the removed suit
		JComboBox b = createComboBox(cards);
		b.setSelectedIndex(0);
		b.setEnabled(true);
		p.add(b);
		int val = UserInterface.displayOptions(p, "Select a card to add:");
		if(val != JOptionPane.OK_OPTION){return;}
		String select = (String)(b.getSelectedItem());
		Card31 remove = new Card31(105, "Spades");
		for(int i=0; i<availableCards.size(); i++) {
			if(availableCards.get(i).toString().equals(select)) {
				if(sci.playerCards.get(index).cards[0]==null || sci.playerCards.get(index)
						.cards[0].getSuit().equalsIgnoreCase("not a card!")) {sci.playerCards.get(index).cards[0] = availableCards.get(i);}
				else if(sci.playerCards.get(index).cards[1]==null || sci.playerCards.get(index)
						.cards[1].getSuit().equalsIgnoreCase("not a card!")) {sci.playerCards.get(index).cards[1] = availableCards.get(i);}
				else if(sci.playerCards.get(index).cards[2]==null || sci.playerCards.get(index)
						.cards[2].getSuit().equalsIgnoreCase("not a card!")) {sci.playerCards.get(index).cards[2] = availableCards.get(i);}
				else {UserInterface.displayError("Please do not attempt to add more than 3 cards for a player."); return;}
				remove = availableCards.get(i);
				break;
			}

		}
		for(int i=0; i<Card31.RANDOM_SUIT_STRINGS.length; i++) {
			if(Card31.RANDOM_SUIT_STRINGS[i].equals(select)) {
				if(sci.playerCards.get(index).cards[0]==null || sci.playerCards.get(index)
						.cards[0].getSuit().equalsIgnoreCase("not a card!")) {sci.playerCards.get(index).cards[0] = new Card31(randomCount, Card31.RANDOM_SUIT_STRINGS[i]);
						randomCount--;}
				else if(sci.playerCards.get(index).cards[1]==null || sci.playerCards.get(index)
						.cards[1].getSuit().equalsIgnoreCase("not a card!")) {sci.playerCards.get(index).cards[1] = new Card31(randomCount, Card31.RANDOM_SUIT_STRINGS[i]);
						randomCount--;}
				else if(sci.playerCards.get(index).cards[2]==null || sci.playerCards.get(index)
						.cards[2].getSuit().equalsIgnoreCase("not a card!")) {sci.playerCards.get(index).cards[2] = new Card31(randomCount, Card31.RANDOM_SUIT_STRINGS[i]);
						randomCount--;}
				else {UserInterface.displayError("Please do not attempt to add more than 3 cards for a player."); return;}
			}
		}
		if(!remove.equals(new Card31(105, "spades"))) {availableCards.remove(remove); totalCardCount();}
		sci.cardsAddedToPlayers++;
	}
	/**
	 * Removes the selected placeholder card from a player.
	 * @param index - which player is getting the cards?
	 */
	private void removeCardButton(int index) {
		JPanel p = createPanel();
		p.add(new JLabel("Choose a card to remove for player index #"+index+":"));
		String[] cards = toStringArrayRemoveCard(sci.playerCards.get(index).cards);
		JComboBox b = createComboBox(cards);
		for(int i=0; i<b.getItemCount(); i++) {if(((String)b.getItemAt(i)) == null) {b.removeItemAt(i);}}
		b.setEnabled(true);
		p.add(b);
		int val = UserInterface.displayOptions(p, "Select a card to remove:");
		if(val != JOptionPane.OK_OPTION){return;}
		if(b.getItemAt(b.getSelectedIndex()) == null || ((String)(b.getItemAt(b.getSelectedIndex()))).substring(0,1).equals("")) {return;}
		if(((String) b.getItemAt(b.getSelectedIndex())).equalsIgnoreCase("not a card!")){return;}
		int ind = b.getSelectedIndex();
		/* Temporarily commenting it out to find a more efficient solution
		if(!sci.playerCards.get(index).cards[ind].isRandomCard()) {//if it is not a random card

			int[] realNums = AI31Functions.convertInts(sci.availableNums);
			String[] realSuits = sci.availableSuits.toArray(new String[sci.availableSuits.size()]);
			ArrayList<Card31> removedCards = new ArrayList<Card31>();
			for(PlayerCard c : sci.playerCards) {if(c!=null) {for(Card31 card : c.cards) {
				if(card!=null && !card.getSuit().equalsIgnoreCase("not a card")) {removedCards.add(card);}}}}
			removedCards.remove(sci.playerCards.get(index).cards[ind]);
			availableCards.clear();
			char[] possibleFaces = new char[sci.availableFaces.size()];
			for(int i=0; i<sci.availableFaces.size(); i++) {possibleFaces[i] = sci.availableFaces.get(i);}
			availableCards.addAll(Card31.generateCards(realSuits, realNums, possibleFaces, removedCards.toArray(new Card31[4])));
			totalCardCount();
		}
		 */
		//instead: like the old approach, but instead I will find the cards location based on nearby cards
		//availableCards.add(sci.playerCards.get(index).cards[ind].getRemovedIndex(), sci.playerCards.get(index).cards[ind]);
		//(old approach above)
		//suit order: clubs, diamonds, hearts, spades
		//new approach: use compareTo to sort
		if(sci.playerCards.get(index).cards[ind].isRandomCard()) {sci.playerCards.get(index).cards[ind] = new Card31(randomCount2, "Not a card!"); return;}
		Card31 add = sci.playerCards.get(index).cards[ind];
		availableCards.add(add);
		//sort by number and suit after adding the card back in
		Collections.sort(availableCards);
		randomCount2--;
		sci.playerCards.get(index).cards[ind] = new Card31(randomCount2, "Not a card!");
		sci.cardsAddedToPlayers--;
	}
	private void pIndexBox() {
		((JButton)specificCardsFeatures.get(29)).setText("Add a Card (Player #"+((JComboBox)specificCardsFeatures.get(28)).getSelectedIndex()+")");
		((JButton)specificCardsFeatures.get(30)).setText("Remove a Card (Player #"+((JComboBox)specificCardsFeatures.get(28)).getSelectedIndex()+")");
		((JButton)specificCardsFeatures.get(29)).revalidate();
		((JButton)specificCardsFeatures.get(29)).repaint();
		((JButton)specificCardsFeatures.get(30)).revalidate();
		((JButton)specificCardsFeatures.get(30)).repaint();
	}
	private void removeACard() {
		JComboBox box = (JComboBox)(specificCardsFeatures.get(24));
		if(box.getSelectedIndex()==0) {return;}
		int counter = 0;
		for(String s : removedCardsToString) {if(s.contains(((String)(box.getSelectedItem())).substring(6))) {counter++;}}
		try {if(counter == sci.availableNums.size()-1) {throw new GameException("Plesae do not attempt to remove the last card of a suit.", "F0319");}}
		catch(GameException e) {UserInterface.displayException(e, 2);}
		if(!find(removedCardsToString, (String)(box.getSelectedItem()))){removedCardsToString.add((String)(box.getSelectedItem()));
		UserInterface.displayInfo("You have removed the card "+((String)(box.getSelectedItem()))+" from the list of available cards.", "Card removal successful.");}
		else {removedCardsToString.remove((String)(box.getSelectedItem())); UserInterface.displayInfo("You have added back the card "+((String)(box.getSelectedItem()))+
				" to the list of available cards.", "Card re-addition successful.");}
		box.setSelectedIndex(0);
		//what to do in the case of 0 cards remaining?
		
		
	}
	private void checkCards() {
		String info = "Here are all removed cards: \n";
		for(String s : removedCardsToString) {info+=s+"\n";}
		UserInterface.displayInfo(info, "The list of additional removed cards.");
	}
	private void howManyCards() {
		JPanel p = createPanel();
		JLabel description = new JLabel("Which suit and how many cards of that suit do you want to add?");
		JComboBox suitBox = createComboBox(sci.availableSuits.toArray(new String[sci.availableSuits.size()]));
		suitBox.setEnabled(true);
		JComboBox numBoxOne = createComboBox(this.intArrayListSizeCap(sci.availableNums).toArray(new Integer[sci.availableNums.size()]));
		numBoxOne.setEnabled(true);
		JComboBox numBoxTwo = createComboBox(this.intArrayListSizeCap(sci.availableNums).toArray(new Integer[sci.availableNums.size()]));
		numBoxTwo.setEnabled(true);
		p.add(description);
		p.add(suitBox);
		p.add(new JLabel(", "));
		p.add(numBoxOne);
		p.add(new JLabel(" to "));
		p.add(numBoxTwo);
		p.add(new JLabel("cards"));
		boolean conditions = false;
		String[] labels = {"OK", "Reset Selected Suit", "Cancel"};
		while(!conditions) {
			int status = UserInterface.displayOptions(p, labels, "How many cards per suit?");
			if(status == JOptionPane.CANCEL_OPTION || status == JOptionPane.CLOSED_OPTION) {return;}
			if(status == JOptionPane.NO_OPTION) {
				for(int i=0; i<4; i++) {if(AI31Constants.ORDERED_SUITS[i].equalsIgnoreCase((String)(suitBox.getSelectedItem()))) {sci.suitQuantities[i] = antiQuantity;}}
				//reset the quantity
				return;
			}
			//availableNums size is the 
			if((Integer)(numBoxTwo.getSelectedItem()) >= (Integer)(numBoxOne.getSelectedItem())) {conditions = true;}
			else {UserInterface.displayError("Please enter a larger max (second term) than min (first term) for the selected suit.");}
		}
		String selectSuit = ((String)suitBox.getSelectedItem()).toLowerCase();
		if(selectSuit.contains("club")) {sci.suitQuantities[0] = new SuitRandom((Integer)numBoxOne.getSelectedItem(), (Integer)numBoxTwo.getSelectedItem());}
		else if(selectSuit.contains("diamond")) {sci.suitQuantities[1] = new SuitRandom((Integer)numBoxOne.getSelectedItem(), (Integer)numBoxTwo.getSelectedItem());}
		else if(selectSuit.contains("heart")) {sci.suitQuantities[2] = new SuitRandom((Integer)numBoxOne.getSelectedItem(), (Integer)numBoxTwo.getSelectedItem());}
		else if(selectSuit.contains("spade")) {sci.suitQuantities[3] = new SuitRandom((Integer)numBoxOne.getSelectedItem(), (Integer)numBoxTwo.getSelectedItem());}
		//System.out.println(Arrays.toString(sci.suitQuantities));
	}
	private void seeHowManyCards() {
		String info = "";
		String[] suitCopy = {"Clubs", "Diamonds", "Hearts", "Spades"};
		for(int i=0; i<4; i++) {info+="Suit: "+suitCopy[i]+"\n\n"+sci.suitQuantities[i]+"\n\n";}
		UserInterface.displayInfo(info, "Random Card Quantities Information");
	}
	private void totalCardCount() {
		((JLabel)specificCardsFeatures.get(39)).setText("Total number of available cards: "+availableCards.size()); 
		specificCardsFeatures.get(39).revalidate();
		specificCardsFeatures.get(39).repaint();
	}
	private void numRounds() {
		sci.specificRoundCt = Integer.parseInt(UserInterface.displayQuestionIgnoreNull("Entering a negative number will result in an infinite number of rounds.", "How many rounds?"));
		if(sci.specificRoundCt<0) {((JLabel)specificCardsFeatures.get(37)).setText("Number of selected rounds: All");}
		else {((JLabel)specificCardsFeatures.get(37)).setText("Number of selected rounds: "+sci.specificRoundCt);}
	}
	private void numRounds(int ct) {
		sci.specificRoundCt = ct;
		if(sci.specificRoundCt<0) {((JLabel)specificCardsFeatures.get(37)).setText("Number of selected rounds: All");}
		else {((JLabel)specificCardsFeatures.get(37)).setText("Number of selected rounds: "+sci.specificRoundCt);}
	}
	private void setUpPrimaryFrame() {
		GridBagConstraints c = new GridBagConstraints();
		//4 columns, any number of rows
		c.gridy = 0;
		c.gridx = 0;
		c.gridheight = 1;
		UserInterface.displayInfo("Press ENTER to register values for each text field setting.\nNote that you must enter a number of players "
				+ "and a number of humans to decide starting positions.", "How to set up Custom Mode");
		c.gridwidth = 2;
		primaryPanel.add(new JLabel("General settings:"), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.NONE;
		lowInfo = new JLabel("Choose the lowest card number (2-9):");
		lowInfo.setToolTipText("Cards from this number through ace (aces are high) will be used in this game."); 
		lowInfo.setEnabled(false);
		primaryPanel.add(lowInfo, c);
		lowCardNum = new JTextField();
		lowCardNum.setEnabled(false);
		lowCardNum.addActionListener(this);
		lowCardNum.setMinimumSize(MIN_LABEL_SIZE);
		lowCardNum.setPreferredSize(MIN_LABEL_SIZE);
		lowCardNum.setMaximumSize(MIN_LABEL_SIZE);
		c.gridx = 1;
		primaryPanel.add(lowCardNum, c);
		c.gridx = 2;
		lowCardLab = new JLabel("Value: ");
		lowCardLab.setEnabled(false);
		lowCardLab.setMinimumSize(MIN_LABEL_SIZE);
		lowCardLab.setMaximumSize(MIN_LABEL_SIZE);
		lowCardLab.setPreferredSize(MIN_LABEL_SIZE);
		primaryPanel.add(lowCardLab, c);
		aceValue = new JTextField();
		c.gridx = 0;
		c.gridy = 2;
		aceValue.addActionListener(this);
		aceValue.setMinimumSize(MIN_LABEL_SIZE);
		aceValue.setPreferredSize(MIN_LABEL_SIZE);
		aceValue.setMaximumSize(MIN_LABEL_SIZE);
		aVal = new JLabel("Choose the value of aces (9-15):");
		primaryPanel.add(aVal, c);
		c.gridx = 1;
		primaryPanel.add(aceValue, c);
		c.gridx = 2;
		aceLab = new JLabel("Value: ");
		aceLab.setMinimumSize(MIN_LABEL_SIZE);
		aceLab.setMaximumSize(MIN_LABEL_SIZE);
		aceLab.setPreferredSize(MIN_LABEL_SIZE);
		primaryPanel.add(aceLab, c);
		c.gridx = 0;
		c.gridy = 4;
		JLabel pInfo = new JLabel("Choose the player count (3-13):");
		pInfo.setToolTipText("The player count includes the middle hand. Note that changing the player count will deselect all human locations for multiplayer.");
		primaryPanel.add(pInfo, c);
		c.gridx = 1;
		playerCount = new JTextField();
		playerCount.addActionListener(this);
		playerCount.setMinimumSize(MIN_LABEL_SIZE);
		playerCount.setPreferredSize(MIN_LABEL_SIZE);
		playerCount.setMaximumSize(MIN_LABEL_SIZE);
		primaryPanel.add(playerCount, c);
		c.gridx = 2;
		playerLab = new JLabel("Value: ");
		playerLab.setMinimumSize(MIN_LABEL_SIZE);
		playerLab.setMaximumSize(MIN_LABEL_SIZE);
		playerLab.setPreferredSize(MIN_LABEL_SIZE);
		primaryPanel.add(playerLab, c);
		c.gridx = 0;
		c.gridy = 3;
		jackValue = new JTextField();
		jackValue.addActionListener(this);
		jackValue.setMaximumSize(MIN_LABEL_SIZE);
		jackValue.setMinimumSize(MIN_LABEL_SIZE);
		jackValue.setPreferredSize(MIN_LABEL_SIZE);
		JLabel jackInfo = new JLabel("Choose the value of jacks (9-14):");
		jackInfo.setToolTipText("Don't try to set the jack value larger than the ace value unless you want a crazy game!");
		primaryPanel.add(jackInfo, c);
		c.gridx = 1;
		primaryPanel.add(jackValue, c);
		jackLab = new JLabel("Value: ");
		jackLab.setMaximumSize(MIN_LABEL_SIZE);
		jackLab.setMinimumSize(MIN_LABEL_SIZE);
		jackLab.setPreferredSize(MIN_LABEL_SIZE);
		c.gridx = 2;
		primaryPanel.add(jackLab, c);
		humanCount = new JTextField();
		humanCount.setEnabled(false);
		humanCount.setMinimumSize(MIN_LABEL_SIZE);
		humanCount.setPreferredSize(MIN_LABEL_SIZE);
		humanCount.setMaximumSize(MIN_LABEL_SIZE);
		humanCount.addActionListener(this);
		c.gridx = 0;
		c.gridy = 5;
		humanInfo = new JLabel("Choose the number of humans:");
		humanInfo.setEnabled(false);
		primaryPanel.add(humanInfo, c);
		c.gridx = 1;
		primaryPanel.add(humanCount, c);
		c.gridx = 2;
		humanLab = new JLabel("Value: ");
		humanLab.setEnabled(false);
		humanLab.setMinimumSize(MIN_LABEL_SIZE);
		humanLab.setMaximumSize(MIN_LABEL_SIZE);
		humanLab.setPreferredSize(MIN_LABEL_SIZE);
		primaryPanel.add(humanLab, c);
		c.gridy = 6;
		c.gridx = 0;
		JLabel spInfo = new JLabel("Choose whether to use specific cards:");
		spInfo.setToolTipText("Would you like to remove specific cards that may exist (roll your mouse over the low card number label)?");
		primaryPanel.add(spInfo, c);
		c.gridx = 1;
		ButtonGroup group = new ButtonGroup();
		specificCardsOn = new JRadioButton("On");
		specificCardsOn.addActionListener(this);
		group.add(specificCardsOn);
		specificCardsOff = new JRadioButton("Off");
		group.add(specificCardsOff);
		specificCardsOff.addActionListener(this);
		specificCardsOff.setSelected(true);
		JPanel specificCards = new JPanel();
		specificCards.setLayout(new FlowLayout());
		specificCards.add(specificCardsOn);
		specificCards.add(specificCardsOff);
		primaryPanel.add(specificCards, c);
		c.gridy = 7;
		c.gridx = 0;
		primaryPanel.add(new JLabel("Choose the number of lives each player has:"), c);
		lifeCount = new JTextField();
		lifeCount.addActionListener(this);
		lifeCount.setMinimumSize(MIN_LABEL_SIZE);
		lifeCount.setPreferredSize(MIN_LABEL_SIZE);
		lifeCount.setMaximumSize(MIN_LABEL_SIZE);
		c.gridx = 1;
		primaryPanel.add(lifeCount, c);
		c.gridx = 2;
		lifeLab = new JLabel("Value: ");
		lifeLab.setMinimumSize(MIN_LABEL_SIZE);
		lifeLab.setMaximumSize(MIN_LABEL_SIZE);
		lifeLab.setPreferredSize(MIN_LABEL_SIZE);
		primaryPanel.add(lifeLab, c);
		c.gridx = 0;
		c.gridy = 8;
		startingPosition = new JTextField();
		startingPosition.addActionListener(this);
		startingPosition.setEnabled(false);
		startingPosition.setMaximumSize(MIN_LABEL_SIZE);
		startingPosition.setMinimumSize(MIN_LABEL_SIZE);
		startingPosition.setPreferredSize(MIN_LABEL_SIZE);
		startPos = new JLabel("Choose the place you start at (0 to playerCt-1):");
		startPos.setToolTipText("Would you like to start in front or behind a specific player? If so, what number index do you want to be placed in?");
		startPos.setEnabled(false);
		primaryPanel.add(startPos, c);
		c.gridx = 1;
		primaryPanel.add(startingPosition, c);
		c.gridx = 2;
		startingPosLab = new JLabel("Value: ");
		startingPosLab.setMinimumSize(MIN_LABEL_SIZE);
		startingPosLab.setMaximumSize(MIN_LABEL_SIZE);
		startingPosLab.setPreferredSize(MIN_LABEL_SIZE);
		startingPosLab.setEnabled(false);
		primaryPanel.add(startingPosLab, c);
		c.gridx = 0;
		c.gridy = 9;
		ButtonGroup textAreaStatus = new ButtonGroup();
		textAreaOn = new JRadioButton("On");
		textAreaOn.setSelected(true);
		textAreaOn.addActionListener(this);
		textAreaOff = new JRadioButton("Off");
		textAreaOff.addActionListener(this);
		JPanel p = new JPanel(new FlowLayout());
		p.add(textAreaOn);
		p.add(textAreaOff);
		textAreaStatus.add(textAreaOn);
		textAreaStatus.add(textAreaOff);
		primaryPanel.add(new JLabel("Choose whether to enable the text area:"), c);
		c.gridx = 1;
		primaryPanel.add(p, c);
		c.gridx = 0;
		c.gridy = 10;
		checkBoxes = new JCheckBox[12];
		for(int i=0; i<12; i++){
			checkBoxes[i] = new JCheckBox(""+i);
			checkBoxes[i].addItemListener(this);
			checkBoxes[i].setEnabled(false);
		}
		JPanel checkBoxPan0 = new JPanel(new FlowLayout());
		JPanel checkBoxPan1 = new JPanel(new FlowLayout());
		JPanel checkBoxPan2 = new JPanel(new FlowLayout());
		for(int i=0; i<4; i++){checkBoxPan0.add(checkBoxes[i]);}
		for(int i=4; i<9; i++){checkBoxPan1.add(checkBoxes[i]);}
		for(int i=9; i<12; i++){checkBoxPan2.add(checkBoxes[i]);}
		humanLocsInfo = new JLabel("Choose where the human players start:");
		humanLocsInfo.setToolTipText("Where (in terms of indexes) do you want the humans to be located (in front of or behind a specific player index)?"); 
		humanLocsInfo.setEnabled(false);
		primaryPanel.add(humanLocsInfo, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		primaryPanel.add(checkBoxPan0, c);
		c.gridy = 11;
		c.gridx = 0;
		primaryPanel.add(checkBoxPan1, c);
		c.gridx = 1;
		primaryPanel.add(checkBoxPan2, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 12;
		c.gridx = 0;
		JLabel eqFace = new JLabel("Choose whether to enable or disable equal faces:");
		eqFace.setToolTipText("<html>Equal faces mean that jack-queen-king values are equal.<br>Not equal faces means that jack-queen-king values increment.<html>");
		primaryPanel.add(eqFace, c);
		c.gridx = 1;
		JPanel pa = new JPanel();
		ButtonGroup equalFaceStatus = new ButtonGroup();
		equalFacesOn = new JRadioButton("Enabled");
		equalFacesOn.addActionListener(this);
		equalFacesOff = new JRadioButton("Disabled");
		equalFacesOff.addActionListener(this);
		equalFacesOn.setSelected(true);
		pa.add(equalFacesOn);
		pa.add(equalFacesOff);
		equalFaceStatus.add(equalFacesOn);
		equalFaceStatus.add(equalFacesOff);
		primaryPanel.add(pa, c);
		c.gridx = 0;
		c.gridy = 13;
		startingIndex = new JTextField();
		startingIndex.setMinimumSize(MIN_LABEL_SIZE);
		startingIndex.setMaximumSize(MIN_LABEL_SIZE);
		startingIndex.setPreferredSize(MIN_LABEL_SIZE);
		startingIndex.setEnabled(false);
		startingIndex.addActionListener(this);
		startIndexLab = new JLabel("Choose who (player index) starts the game:");
		startIndexLab.setToolTipText("Who is the first player to do the dealer's turn (for the first round) (from 0 to playerCount-1)?");
		startIndexLab.setEnabled(false);
		primaryPanel.add(startIndexLab, c);
		c.gridx = 1;
		primaryPanel.add(startingIndex, c);
		c.gridx = 2;
		startingIndexLab = new JLabel("Value: ");
		startingIndexLab.setEnabled(false);
		startingIndexLab.setMinimumSize(MIN_LABEL_SIZE);
		startingIndexLab.setMaximumSize(MIN_LABEL_SIZE);
		startingIndexLab.setPreferredSize(MIN_LABEL_SIZE);
		primaryPanel.add(startingIndexLab, c);
		okButton = new JButton("Create Game");
		okButton.addActionListener(this);
		okButton.setAlignmentX(CENTER_ALIGNMENT);
		c.gridx = 2;
		c.gridy = 14;
		primaryPanel.add(okButton, c);
		generalComponents = new ArrayList<JComponent>();
		for(Component comp : primaryPanel.getComponents()){
			if(comp instanceof JPanel){for(Component com : ((JPanel)comp).getComponents()){generalComponents.add((JComponent)com);}}
			else{generalComponents.add((JComponent)comp);}
		}
		setUpSecondaryFrame(c);
	}
	private void setUpSecondaryFrame(GridBagConstraints c) {
		//what should be created? Let us say that we want the following:
		//Total number of available cards (noneditable)
		c.gridx = 3;
		c.gridwidth = 2;
		c.gridy = 0;
		primaryPanel.add(new JLabel("Custom settings for specific cards:"), c);
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 1;
		JLabel suitLabel = createLabel("Choose the suits available:");
		JCheckBox clubs = createCheckBox("Clubs");
		JCheckBox diamonds = createCheckBox("Diamonds");
		JCheckBox hearts = createCheckBox("Hearts");
		JCheckBox spades = createCheckBox("Spades");
		JPanel suitPanel = createPanel();
		specificCardsFeatures = new ArrayList<JComponent>();
		specificCardsFeatures.add(clubs);
		specificCardsFeatures.add(diamonds);
		specificCardsFeatures.add(hearts);
		specificCardsFeatures.add(spades);
		for(JComponent comp : specificCardsFeatures) {suitPanel.add(comp);}
		specificCardsFeatures.add(0, suitLabel);
		primaryPanel.add(suitLabel, c);
		c.gridx = 4;
		primaryPanel.add(suitPanel, c);
		c.gridy = 2;
		c.gridx = 3;
		JLabel numberLabel = createLabel("Choose the numbers/faces available:");
		numberLabel.setToolTipText("What numbers/faces are available to play with? Note that placing your mouse over a check "
				+ "box here selects or de-selects the box if it is enabled based on its original state (was it checked before? If so, it will be unchecked)");
		specificCardsFeatures.add(numberLabel);
		JPanel numPanel1 = createPanel();
		JPanel numPanel2 = createPanel();
		JPanel numPanel3 = createPanel();
		for(int i=2; i<11; i++) {
			JCheckBox num = createCheckBox(""+i);
			specificCardsFeatures.add(num);
			if(i>6) {numPanel2.add(num);}
			else {numPanel1.add(num);}
		}
		for(int i=0; i<4; i++) {
			JCheckBox f = createCheckBox(""+FACE_CARDS[i]);
			specificCardsFeatures.add(f);
			numPanel3.add(f);
		}
		primaryPanel.add(numberLabel, c);
		c.gridx = 4;
		primaryPanel.add(numPanel1, c);
		c.gridy = 3;
		c.gridx = 3;
		primaryPanel.add(numPanel2, c);
		c.gridx = 4;
		primaryPanel.add(numPanel3, c);
		JLabel specificCards = createLabel("Choose whether to set default cards for players:");
		JRadioButton yesB = createRadioButton("Yes");
		JRadioButton noB = createRadioButton("No");
		noB.setSelected(true);
		JRadioButton[] buttonArray = {yesB, noB};
		ButtonGroup g = createButtonGroup(buttonArray);
		JPanel specificCardP = createPanel();
		specificCardP.add(yesB);
		specificCardsFeatures.add(specificCards);
		specificCardsFeatures.add(yesB);
		specificCardsFeatures.add(noB);
		specificCardP.add(noB);
		c.gridx = 3;
		c.gridy = 4;
		primaryPanel.add(specificCards, c);
		c.gridx = 4;
		primaryPanel.add(specificCardP, c);
		c.gridx = 3;
		c.gridwidth = 2;
		JButton confirm = createButton("Confirm Top Section");
		specificCardsFeatures.add(confirm);
		c.gridy = 5;
		primaryPanel.add(confirm, c);
		c.gridy  = 6;
		c.gridwidth = 1;
		c.gridx = 3;
		String[] availableCardArray = {"Choose a card to remove:"};
		JLabel infoLab = createLabel("Choose cards to play with:");
		JComboBox<String> cardBox = createComboBox(availableCardArray);
		cardBox.setToolTipText("<html>Choose cards you don't want to play with. Each time you remove a card (by clicking on it), it will be saved<br>and visible "
				+ "when you click the 'Check Cards' button."
				+"\nRemoving a card twice re-adds it back in and so on.</html>");
		specificCardsFeatures.add(infoLab);
		specificCardsFeatures.add(cardBox);
		JButton checkCardBtn = createButton("Check Cards");
		checkCardBtn.setToolTipText("Click me to see all available cards that have been removed.");
		JButton confirmMid = createButton("Confirm Middle Section");
		specificCardsFeatures.add(checkCardBtn);
		specificCardsFeatures.add(confirmMid);
		JPanel btnPanel = createPanel();
		btnPanel.add(checkCardBtn);
		btnPanel.add(confirmMid);
		JPanel boxPanel = createPanel();
		boxPanel.add(infoLab);
		boxPanel.add(cardBox);
		primaryPanel.add(cardBox, c);
		c.gridx = 4;
		primaryPanel.add(btnPanel, c);
		//String[] pIndex = new String[info.playerCt];
		String[] pIndex = new String[info.playerCt+1];
		pIndex[pIndex.length-1] = "THE MIDDLE HAND";
		for(int i=0; i<info.playerCt; i++) {pIndex[i] = "Player Index #"+i;}
		JComboBox pIndexBox = createComboBox(pIndex);
		//use setModel on it after casting to JComboBox from the mega array
		//create a defaultComboBoxModel using a new array given the new real number of players
		c.gridx = 3;
		c.gridy = 7;
		JLabel pIndexLab = createLabel("Choose the player index for cards:");
		specificCardsFeatures.add(pIndexLab);
		specificCardsFeatures.add(pIndexBox);
		primaryPanel.add(pIndexLab, c);
		c.gridx = 4;
		primaryPanel.add(pIndexBox, c);
		JButton addCardButton = createButton("Add a Card (Player #0)");
		addCardButton.setToolTipText("Adds a card to the specified player's hand. Maximum 3 cards. The middle hand index will be one larger than the "
				+ "largest numerical index.");
		specificCardsFeatures.add(addCardButton);
		JButton removeCardButton = createButton("Remove a Card (Player #0)");
		removeCardButton.setToolTipText("Removes a card from the specified player's hand. Minimum 0 cards (fewer than 3 means randomized cards)."
				+ " The middle hand index will be one larger than the largest numerical index.");
		specificCardsFeatures.add(removeCardButton);
		c.gridy = 8;
		c.gridx = 3;
		primaryPanel.add(addCardButton, c);
		c.gridx = 4;
		primaryPanel.add(removeCardButton, c);
		JLabel roundCt = createLabel("# of rounds the cards will remain this way?");
		JRadioButton rD1 = createRadioButton("One");
		JRadioButton rInf = createRadioButton("All");
		JRadioButton rSpec = createRadioButton("Specific Number");
		specificCardsFeatures.add(roundCt);
		specificCardsFeatures.add(rD1);
		specificCardsFeatures.add(rInf);
		specificCardsFeatures.add(rSpec);
		JRadioButton[] buttons = {rD1, rInf, rSpec};
		ButtonGroup bg = createButtonGroup(buttons);
		JPanel roundPan = createPanel();
		for(JRadioButton bu : buttons) {roundPan.add(bu);}
		c.gridy = 9;
		c.gridx = 3;
		primaryPanel.add(roundCt, c);
		c.gridx = 4;
		primaryPanel.add(roundPan, c);
		c.gridx = 3;
		c.gridy = 10;
		c.gridwidth = 1;
		JButton cardQuantities = createButton("Set Random Card Quantities");
		cardQuantities.setToolTipText("<html>How many cards do you want per suit? Do you want an even distribution or not?<br>"
				+ "You can set a range of how many cards per suit or just a single flat number.<br>"
				+ "If you add too few cards, the rest are random; If you add too many cards...<br>"
				+ "then the first requests will take priority.");
		specificCardsFeatures.add(cardQuantities);
		primaryPanel.add(cardQuantities, c);
		c.gridx = 4;
		c.gridwidth = 1;
		JButton seeQuantities = createButton("See Selected Quantities");
		seeQuantities.setToolTipText("See what random card quantities have been selected. This will not inform you of the random value. ");
		primaryPanel.add(seeQuantities, c);
		specificCardsFeatures.add(seeQuantities);
		c.gridx = 3;
		c.gridwidth = 2;
		c.gridy = 11;
		JLabel numRoundVal = createLabel("Number of selected rounds: 0");
		specificCardsFeatures.add(numRoundVal);
		primaryPanel.add(numRoundVal, c);
		c.gridy = 12;
		JButton finalConfirm = createButton("Confirm Bottom Section");
		specificCardsFeatures.add(finalConfirm);
		primaryPanel.add(finalConfirm, c);
		JLabel totalCardCount = createLabel("Total number of available cards: 0");
		specificCardsFeatures.add(totalCardCount);
		c.gridwidth = 2;
		c.gridx = 3;
		c.gridy = 13;
		primaryPanel.add(totalCardCount, c);
		c.gridx = 4;
		c.gridy = 14;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		end = new JButton("Close");
		end.setAlignmentX(RIGHT_ALIGNMENT);
		end.addActionListener(this);
		end.setEnabled(true);
		primaryPanel.add(end, c);
		add(primaryPanel);
		for(int i=0; i<specificCardsFeatures.size(); i++) {
			System.out.println(i+": "+specificCardsFeatures.get(i).getClass().getName());
			if(specificCardsFeatures.get(i) instanceof AbstractButton) {System.out.println(((AbstractButton)specificCardsFeatures.get(i)).getText());}
		}
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private JButton createButton(String info) {
		JButton b = new JButton(info);
		b.setEnabled(false);
		b.addActionListener(this);
		b.setAlignmentX(CENTER_ALIGNMENT);
		return b;
	}
	private JLabel createLabel(String info) {
		JLabel l = new JLabel(info);
		l.setEnabled(false);
		return l;
	}
	private JCheckBox createCheckBox(String info) {
		final JCheckBox box = new JCheckBox(info);
		box.setEnabled(false);
		box.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {if(box.isEnabled()) {box.setSelected(!box.isSelected());}}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		return box;
	}
	private JRadioButton createRadioButton(String info) {
		JRadioButton button = new JRadioButton(info);
		button.addActionListener(this);
		button.setEnabled(false);
		return button;
	}
	private ButtonGroup createButtonGroup(JRadioButton[] buttons) {
		ButtonGroup group = new ButtonGroup();
		for(JRadioButton b : buttons) {group.add(b);}
		return group;
	}
	private JPanel createPanel() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		return p;
	}
	private <T> JComboBox<T> createComboBox(T[] info) {
		JComboBox<T> box = new JComboBox<T>(info);
		box.setEnabled(false);
		box.addActionListener(this);
		box.setEditable(false);
		return box;
	}
	public static void main(String[] args) {SwingUtilities.invokeLater(new Runnable() {public void run() {new CustomModeFrame();}});}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(info.playerCt == info.humanCt || info.humanCt == 1) {return;}
		if(!(e.getSource() instanceof JCheckBox)) {return;}
		checkedBoxesCount = 0;
		for(int i=0; i<checkBoxes.length; i++) {if(checkBoxes[i].isSelected()) {checkedBoxesCount++;}}
		if(checkedBoxesCount>=info.humanCt) {disableCheckBoxes();}
		else {enableCheckBoxes();}
	}
	private void disableCheckBoxes(){for(int i=0; i<info.playerCt-1; i++){if(!checkBoxes[i].isSelected()){checkBoxes[i].setEnabled(false);}}}
	private void enableCheckBoxes() {for(int i=0; i<info.playerCt-1; i++) {checkBoxes[i].setEnabled(true);}}
	public CustomModeInfo getCustomInfo() {return info;}
	public SpecificCardsInfo getSpecificCardInfo() {return sci;}
	public static String[] toStringArray(ArrayList al) {
		String[] info = new String[al.size()];
		for(int i=0; i<al.size(); i++) {info[i] = al.get(i).toString();}
		return info;
	}
	public static String[] toStringArray(Object[] ar) {
		String[] info = new String[ar.length];
		for(int i=0; i<ar.length; i++) {if(ar[i]!=null) {info[i] = ar[i].toString();}}
		return info;
	}
	public static String[] toStringArrayRemoveCard(Object[] ar) {
		String[] info = new String[ar.length];
		for(int i=0; i<ar.length; i++) {if(ar[i]!=null) {info[i] = ar[i].toString();}else{info[i] = "not a card!";}}
		return info;
	}
	public static ArrayList toStringArrayList(Object[] ar){
		ArrayList<String> info = new ArrayList<String>();
		for(int i=0; i<ar.length; i++) {info.add(ar[i].toString());}
		return info;
	}
	public static int[] toIntArray(ArrayList<Integer> numArrayList) {
		int[] numArray = new int[numArrayList.size()];
		for(int i=0; i<numArrayList.size(); i++) {numArray[i] = numArrayList.get(i);}
		return numArray;
	}
	public static int[] intArraySizeCap(ArrayList<Integer> possibleNums) {
		int[] nums = new int[possibleNums.size()];
		for(int i=0; i<possibleNums.size(); i++) {nums[i] = i;}
		return nums;
	}
	public static ArrayList<Integer> intArrayListSizeCap(ArrayList<Integer> possibleNums) {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for(int i=0; i<possibleNums.size()+1; i++) {nums.add(i);}
		return nums;
	}
	/**
	 * Attempts to find an object and returns true if successful.
	 * @param stuff - the array you want to check
	 * @param find - the object you want to find
	 * @return true if the object is found, false if the object is not found
	 */
	public boolean find(Object[] stuff, Object find) {
		for(Object s : stuff) {if(s.equals(find)) {return true;}}
		return false;
	}
	/**
	 * Attempts to find an object and returns true if successful.
	 * @param stuff - the ArrayList you want to check
	 * @param find - the object you want to find
	 * @return true if the object is found, false if the object is not found
	 */
	public boolean find(ArrayList stuff, Object find) {
		for(Object s : stuff) {if(s.equals(find)) {return true;}}
		return false;
	}
	@Override
	public void windowOpened(WindowEvent e) {

	}
	@Override
	public void windowClosing(WindowEvent e) {UserInterface.displayInfo("Press the Close Button to close the frame.");}
	@Override
	public void windowClosed(WindowEvent e) {

	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
