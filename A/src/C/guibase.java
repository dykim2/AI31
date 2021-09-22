package C;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.*;


import javax.swing.*;


public class guibase implements ActionListener {
  private JFrame frame = new JFrame("Words");
  private JPanel panel = new JPanel(new GridLayout(3, 1));
  private JButton button = new JButton("Press");
  private JButton exit = new JButton("Exit");
  private JTextArea area = new JTextArea("this is text");
  private JButton saveButton = new JButton("Save to file");
  private String[] words = {"Anand's Big Lips", "Geronimous Gerbil Arma", "*Clack Clack Clack*",
      "No.", "Yes.", "Banana", "Almond", "Kayffle", "Waffles", "they", "NO KUM", "Wufflemond",
      "Mitch", "KUM", "Penguin", "Kaylitch", "Your Mom",
      "Kyle the rigged game developer everyone has a love-hate relationship with", "A Fan",
      "An ancient laptop from 1994 that gets -5 frames per second on shellshock"};
  private String[] words2 = {""};
  private ArrayList<Integer> list = new ArrayList<Integer>();
  private String[] phrases = {"Welcome to NO KUM ZONE. We have ", ",", ", and", ", alongside",
      "We hope you enjoy your stay with", "If you need any help,",
      "is here to throw you under the bus. Have a very", "day."};
  private String[] phrases2 =
      {"It's really warm outside, what a nice day to go to a beach. Once you're there, you see",
          ",", ", and", ", and ", "coming from the water. Turns out, it's a ", ""};
  private BufferedWriter write = null;
  private BufferedReader read = null;

  public static void main(String args[]) {
    new guibase();

  }

  public guibase() {
    try {
      // displayInformation("test","H");
      // System.out.println("test");
      frame.setSize(300, 300);
      frame.getContentPane().add(button); // Adds Button to content pane of frame
      frame.setVisible(true);
      button.addActionListener(this);
      exit.addActionListener(this);
      saveButton.addActionListener(this);
      area.setLineWrap(true);
      panel.add(button);
      panel.add(exit);
      panel.add(saveButton);
      // panel.add(area);
      frame.add(panel);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

      // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } catch (Exception e) {
      System.out.print(e.toString());
    }
    System.out.print("Press enter to exit");
    // new Scanner(System.in).next();
  }
/**
  @Override
  @see newTest.hi
  */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(button)) {
      // System.out.println("hi");
      int count = phrases.length;
      String add = "";
      for (int i = 0; i < count - 1; i++) {
        add += phrases[i] + " " + words[(int) (Math.random() * words.length)] + "";
        if (i > 2) {
          add += "\n";
        }
      }
      add += "day.";
      area.setText(add);
      displayInformation(area.getText(), "Sup");
    } else if (e.getSource().equals(exit)) {
      System.exit(0);
    } else if (e.getSource().equals(saveButton)) {
      System.out.println("debug moment");
      String loc = displayPrompt(
          "What is the path to where you want to save the file? Do not include the file name."
          + "\nExample: C:/Users/yello/Documents (it will automatically be given the name output"
          + " if a file with the same name does not exist)",
          "Choose where to save the file.");
      try {
        File file = new File(loc + "/output.txt");
        write = new BufferedWriter(new FileWriter(file, true));
        write.append("\n" + area.getText());
        write.close();
        System.out.println("Success");
      } catch (FileNotFoundException ex) {
        displayInformation(
            "The system ran into a file system error. Make sure you entered the correct"
            + " intended path, as any typo can cause an error.",
            "Error");
      } catch (IOException ex) {
        ex.printStackTrace();
      }

      String output = "\n";
    }

  }
  /**
   * Multiple descriptive lines of Javadoc text are written here,
   * wrapped manually...
   * <p>
   * I wonder why this works. Is that not a great question?
   * @param p1 is the name of the person that this method investigates,
   *      to determine whether they are worthy of the correct answer 
   * @return the answer to life the universe and everything
   */
  public static void displayInformation(Object info, String title) {
    JOptionPane.showMessageDialog(null, info, title, JOptionPane.INFORMATION_MESSAGE);
  }

  public static String displayPrompt(Object info, String title) {
    return JOptionPane.showInputDialog(null, info, title, JOptionPane.QUESTION_MESSAGE);

  }
}
