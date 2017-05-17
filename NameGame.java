import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/*
 NameGame
 @purpose asks for the player's name and creates the first instance of the NameGameFrame class
 @author Ken Tjie
 @version 1.5 2015-23-09
*/

public class NameGame{ 
  public static void main (String[] args) throws FileNotFoundException {
    
    String userName = JOptionPane.showInputDialog ("Please enter your name");
    
    while (userName.length() <= 0 || userName == null)
      userName = JOptionPane.showInputDialog ("Please enter your name");
    
    NameGameFrame gameFrame = new NameGameFrame (1, userName);
    gameFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    
  }//end main
}//end class