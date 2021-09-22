package baselineCustomClasses;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Game extends JFrame implements ActionListener{//scoreboard and such
	private JButton helloButton;
	private JButton goodbyeButton;
	private JTextField field;
	private JTextArea textArea;
	public Game() {
		initUI();
	}
	public void initUI() {
		setTitle("Game");
		setSize(300, 300);
		textArea = new JTextArea("This is a text area.\nHello there.", 10, 10);
		helloButton = new JButton("Hello");
		goodbyeButton = new JButton("Goodbye");
		goodbyeButton.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		//JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
		JOptionPane.showMessageDialog(null, "Welcome to the game!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
		String input = JOptionPane.showInputDialog(null, "What is 1+1?", "question", JOptionPane.QUESTION_MESSAGE);
		try{int result = Integer.parseInt(input);}
		catch(NumberFormatException e) {System.out.println(e);}
		JOptionPane.showMessageDialog(null, "Your answer:\n"+input, "Answer", JOptionPane.WARNING_MESSAGE);
		panel.add(helloButton, BorderLayout.NORTH);
		panel.add(goodbyeButton, BorderLayout.SOUTH);
		panel.add(textArea, BorderLayout.CENTER);
		add(panel);
		setResizable(true); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) throws GameErrorException, PlayingCardException, InterruptedException{
			Game ex = new Game();
			ex.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == helloButton) {
			
		}
		if(e.getSource() == goodbyeButton) {
			System.exit(0);
		}
	}
}
