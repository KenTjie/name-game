import javax.swing.JButton;

/*
 NameGameButton
 Buttons for the NameGameFrame class for the user to click as well as the boxes for the words
 @author Ken Tjie
 @version 1.5 2015-23-09
 */

public class NameGameButton extends JButton {
  
  String word;
  boolean answered;
  
  /*
   NameGameButton
   Instantiates an object of the NameGameButton class; the constructor
   @param String word word that the button keeps as the answer
   @param boolean answered used to keep track if the word has been revealed or not
   @return void
  */
  public NameGameButton (String word, boolean answered) {
    this.word = word;
    this.answered = answered;
  }//end constructor
  
  /*
   getWord
   Returns the string in word
   @return word - the String located in the word variable
  */
  public String getWord () {
    return word;
  }//end getWord
  
  /*
   getStatus
   Returns the boolean in answered
   @return answered - the boolean (true or false) in the answered vairable
  */
  public boolean getStatus () {
    return answered;
  }//end getStatus
  
  /*
   setWord
   Sets the value of word to the string in given in the parameter
   @param String word word to be given to word of this class
   @return void
  */
  public void setWord (String word) {
    this.word = word;
  }//end setWord
  
  /*
   setStatus
   Sets the value of answered to the boolean given in the parameter
   @param boolean answered boolean to be given to answered of this class
   @return void
  */
  public void setStatus (boolean answered) {
    this.answered = answered;
  }//end setStatus 
  
}//end class