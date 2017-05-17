//import all required components
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.net.URL;
import java.net.MalformedURLException;
import java.applet.AudioClip;

/*
 NameGameButton
 Class to run the program and keep track of the buttons clicked by the user, as well as the words typed
 @author Ken Tjie
 @version 1.5 2015-23-09
*/

public class NameGameFrame extends JFrame implements ActionListener {//give the class a JFrame and ActionListener
  
  //required vairbales
  private AudioClip[] sounds;
  private NameGameButton[] listButtons;
  private NameGameFrame game;
  private Container gameFrame;
  private GridLayout gameSetup;
  private JLabel time;
  private Timer timer;
  private Scanner input;
  private int level, showTime, counter = 0, numRevealed = 0, numTries = 0;
  private final int NUM_WORDS;
  private static final int DELAY_MSECS = 1000;
  private String userName, userAnswer, theme;
  private JButton startButton = new JButton("Start"), nextLevel = new JButton("Next Level") ;

  /*
   NameGameFrame
   To instantiate an object of the NameGame Frame class; the constructor
   @param int level level of the game to be displayed
   @param String userName name of the user who is playing
   @throws FileNotFoundException exception for if the required file is not found
   @return void
  */
  public NameGameFrame (int level, String userName) throws FileNotFoundException {
    
    super ("Can You Name Them All?");//name of the frames
    
    this.level = level;
    this.userName = userName;
    
    //display all of the instructions
    if (level == 1) {
      JOptionPane.showMessageDialog(null, "Welcome to the game: Can You Name Them All?!");
      JOptionPane.showMessageDialog(null, "Your objective is to complete the list according to the category given.");
      JOptionPane.showMessageDialog(null, "Type your answers in the text box that pops up.");
      JOptionPane.showMessageDialog(null, "DO NOT put spaces before or after the word or it will be considered wrong! Spelling counts. However, the answers are not case sensitive!");
      JOptionPane.showMessageDialog(null, "There are a total of 5 levels, complete a list to advance.");
      JOptionPane.showMessageDialog(null, "Press the Start button to start the level.\nThe Next Level button will not do anything unless all the words are revealed.");
      JOptionPane.showMessageDialog(null, "Type in 'exit' to exit the game at any time.");
      JOptionPane.showMessageDialog(null, "Good luck!");
    }//end if
    
    //play sounds "ORASContest.wav"
    sounds = new AudioClip[1];
    try {
      URL fileLocation = new URL ("file:ORASContest.wav");
      sounds[0] = JApplet.newAudioClip (fileLocation);
    } 
    catch (MalformedURLException e) {}//end try catch
    sounds[0].loop();
    
    UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));//set colour of the disabled buttons to black
    
    gameFrame = getContentPane();
    
    //set the size, theme and time according to the level
    switch (level) {
      case 1:
        gameSetup = new GridLayout (3, 4, 3, 3);
        theme = "Planets";
        showTime = 60;
        break;
        
      case 2:
        gameSetup = new GridLayout (3, 6, 3, 3);
        theme = "Continents and Oceans";
        showTime = 180;
        break;
        
      case 3:
        gameSetup = new GridLayout (3, 5, 3, 3);
        theme = "Gonzaga Houses";
        showTime = 120;
        break;
        
      case 4:
        gameSetup = new GridLayout (5, 3, 3, 3);
        theme = "Greek Zodiac";
        showTime = 180;
        break;
        
      case 5:
        gameSetup = new GridLayout (5, 3, 3, 3);
        theme = "Chinese Zodiac";
        showTime = 180;
        break;
        
      case 6:
        gameSetup = new GridLayout (3, 7, 3, 3);
        theme = "Canada's Provinces and Territories";
        showTime = 150;
        break;
        
      case 7:
        gameSetup = new GridLayout (4, 7, 3, 3);
        theme = "Canada's Capitals";
        showTime = 150;
        break;
        
      case 8:
        gameSetup = new GridLayout (3, 7, 3, 3);
        theme = "The Seven Heavenly Virtues and The Seven Deadly Sins";
        showTime = 300;
        break;
        
      case 9: 
        gameSetup = new GridLayout (5, 3, 3, 3);
        theme = "Birthstones";
        showTime = 210;
        break;
        
      case 10:
        gameSetup = new GridLayout (15, 10, 3, 3);
        theme = "The Original 151 Pokemon";
        showTime = 721;
        break;
        
    }//end switch
    
    gameFrame.setLayout(gameSetup);
    
    //count the number of lines according to the level
    input = new Scanner (new FileReader (level + ".txt"));
    
    while (input.hasNextLine()) {
      counter++;
      input.nextLine();
    }
    
    input.close();
    
    listButtons = new NameGameButton[counter];//set the length of listButttons to counter
    NUM_WORDS = counter;//set NUM_WORDS to counter
    
    //fill the array listButtons with buttons according to each line in the text file, disable it and add it to the frame
    input = new Scanner (new FileReader(level + ".txt"));
    
    for (int i = 0; i < listButtons.length; i++) {
      listButtons[i] = new NameGameButton(input.nextLine(), false);
      listButtons[i].setText(i + 1 + ".");
      listButtons[i].setEnabled(false);
      gameFrame.add(listButtons[i]);
    }
 
    input.close();
    
    //add the Start button to the frame
    startButton.addActionListener (this);
    gameFrame.add (startButton);
    
    //add the timer to the frame
    time = new JLabel ("Time left: " + showTime + "s");
    gameFrame.add (time);
    
    //add the Next Level button to the frame
    nextLevel.addActionListener (this);
    gameFrame.add (nextLevel);
    
    setSize (1000, 700);
    setVisible (true);
  }//end constructor
   
  /*
   countDown
   Decreases the time on the screen by one second and updates the text of the time JLabel
   @return void
  */
  public void countDown () {
    showTime --;//decrease showTime by 1
    time.setText("Time left: " + showTime + "s");//update the text on the time label
  }//end countDown
  
  /*
   actionPerformed
   Allows the player to start the game. A question box pops up to allow users to enter their answers, if the start button is clicked.
   If the next level button is clicked, the existing window will close and open a new widow with the given parameters, 
     only if the user has revealed all the answers of the current level.
   @param ActionEvent buttonClicked - the detector that a button was clicked
   @return void
  */
  public void actionPerformed(ActionEvent buttonClicked) {
    
    //check if the button clicked was the Start button
    if (buttonClicked.getSource() == startButton) {
      
      startButton.setEnabled(false);//disable the start buttton
      
      timer = new Timer (DELAY_MSECS, new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          countDown();
        }
      });
      
      timer.start();//start the timer
      
      //while the number of answers revealed is less than NUM_WORDS
      while (numRevealed < NUM_WORDS) {
      
        userAnswer = JOptionPane.showInputDialog(userName + ", please enter a word. The theme is: " + theme);//ask the user for an input
        
        //check if the user closes the window or presses cancel
        while (userAnswer == null) 
          userAnswer = JOptionPane.showInputDialog(userName + ", please enter a word. The theme is: " + theme);
        
        numTries ++;//add 1 to numTries
        
        //check the words of the buttons using a for loop, set the colour off disabled buttons to red and change the text to its word if theres a match
        for (int i = 0; i < listButtons.length; i++)
          if (userAnswer.equalsIgnoreCase(listButtons[i].getWord()) && listButtons[i].getStatus() == false) {
          listButtons[i].setText(listButtons[i].getWord());
          UIManager.put("Button.disabledText", new ColorUIResource(Color.RED));
          listButtons[i].setStatus(true);//set the button's status to true
          numRevealed ++;//add 1 to numRevealed
        }//end for
        
        //if user says "exit", close the program
        if (userAnswer.equalsIgnoreCase("exit"))
          System.exit(0);
        
        else if (userAnswer.equalsIgnoreCase("Pokemon")) {
          sounds[0].stop();
          dispose();
          try {
            game = new NameGameFrame (10, userName);
          }
          catch (FileNotFoundException e) {}//end try catch
        }//end else if
        
        //if showTme is less than 0, than say they lose and close the program
        else if (showTime <= 0) {
          timer.stop();
          JOptionPane.showMessageDialog(null, "Time's Up! You win? Nope, you didn't! Game over! Bye Bye!");
          System.exit(0);
        }//end else if
      
      }//end while
      
      //if showTme is less than 0, than say they lose and close the program
      if (showTime <= 0) {
        timer.stop();
        JOptionPane.showMessageDialog(null, "Time's Up! You win? Nope, you didn't! Game over! Bye Bye!");
        System.exit(0);
      }//end if
      
      sounds[0].stop();
      timer.stop();
      JOptionPane.showMessageDialog (null, "Level finsihed! " + userName + ", you got " + listButtons.length + " answers in " + numTries + " tries with " + showTime + "s left to spare.");
    }//end if
    
    //if the Next Level button was clicked and the number of revealed answers is equal to NUM_WORDS...
    if (buttonClicked.getSource() == nextLevel && numRevealed == NUM_WORDS) {
      
      if (level == 10) {
        JOptionPane.showMessageDialog (null, "Congratz, " + userName + "! You finished the game!");
        System.exit(0);
      }//end if
      
      dispose();
      try {
        game = new NameGameFrame (level + 1, userName);
      } 
      catch (FileNotFoundException e) {}//end try catch
      
    }//end if
    
  }//end actionPerformed
  
}//end class