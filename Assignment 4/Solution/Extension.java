
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

/* Extension things.
 * 1 - Sound effects and voice guide
 * 2 - You can play as many hands as you wish
 * 3 - There is thing like a menu. So you can choose category, in other words, 
 *     which kind of words you want to guess. For instance: countries,companies,etc.
 * 3 - If you enter correct letter twice or more programs tells you that you have already entered this letter
 * 4 - If you enter incorrect letter twice or more, it won't be drawn twice or more on GCanvas.
 * 5 - At the end of the hand in the question "Do you want to play more" if you enter "NO" window closes automatically
 * 
 */
import java.applet.AudioClip;
import java.awt.Dimension;
import acm.program.*;
import acm.util.*;
import acm.io.IOConsole;

public class Extension extends ConsoleProgram{
	//Audio files
	private AudioClip rope = MediaTools.loadAudioClip("audiorope.au");
	private AudioClip fail = MediaTools.loadAudioClip("audiofail.au");
	private AudioClip applause = MediaTools.loadAudioClip("audioapplause.au");
	private AudioClip bye = MediaTools.loadAudioClip("audiobye.au");
	//Random Generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanCanvasExtension canvas;
	//Number of guesses(Attempt/Live)
	private int guess = 8;
	//In future chosen word
	private String word = ""; 
	//User's wish to play a hand
	private boolean wish = true;
	//User's choice of type
	private int choice = 0;
	//Incorrect letters
	IOConsole x = this.getConsole();
	
	//Add right side. Canvas
	public void init() { 
		canvas = new HangmanCanvasExtension();
		add(canvas);	
		setSize(new Dimension(1200,650));	//Set size for canvas
	}
	
	public void run() {
		while(wish){  //While user wants to play game.
			
			canvas.reset();	//Clear canvas	
			askForType();	//Ask user to choose category
			chooseWord();	//Chose word from list
			String result = setUp();	//Set Up
			game(result);	//Game process
			wish = checkUserWish(result); //Check user's wish whether he wants to play or not
			x.clear();
		}
	}
	//This method asks user to choose category/game mode,in other words, which kind of words user wants to guess
	private void askForType(){
		println("Welcome to hangman!");
		println("Choose game mode: ");
		println("1 - Default");
		println("2 - Countries");
		println("3 - Football Clubs");
		println("4 - Freeuni's Lectures");
		println("5 - Companies");
		boolean x = true;
		while(x){	//while user enters correct number of type
			choice = readInt("Enter number of type: ");
			for(int i = 1; i <= 5; i++){
				if(choice == i){
					x = false;	
					break;
				}
			}
			if(x){	//If user has entered incorrect number
				println("Wrong input");
				println("Please enter correct number");
				}
		}
	}
	// This method chooses word from list
	private void chooseWord() {
		ExtensionHangmanLexicon lex = new ExtensionHangmanLexicon(choice);
		int numberOfWords = lex.getWordCount(); // Find out how many words are
												// in the list
		int x = rgen.nextInt(0, numberOfWords - 1); // Choose random number(int)
													// from 0 to (numberOfWords
													// - 1);
		word = lex.getWord(x);
	}

	// This method draws first few things
	private String setUp() {
		String s1 = "The word now looks like this: ";
		String result = "";
		// This loop draws symbol "-" according to word's length
		for (int i = 0; i < word.length(); i++) {
			result += "-";
		}
		println(s1 + result);
		println("You have " + guess + " guess left");
		
		return result;
	}

	// This method provides game proccess
	private void game(String result) {
		boolean x = false; // I need boolean x to find out whether user has
							// guessed the letter from the word or not
		while (gameIsntOver(result)) {
			print("Your guess: "); // Print it because user enters character by
									// method readChar();
			char ch = readChar(); // Ask user for enter character
			for (int j = 0; j < word.length(); j++) { // Check if word contains
														// entered symbol
				if (ch == word.charAt(j)) {
					
					checkGuessedLetter(ch,result); //Check whether user's guess is already in letter or not
					result = result.substring(0, j) + ch + result.substring(j + 1);
					x = true; // If word contains letter(entered by user) than
								// make x true;
				}
			}
			// If user has guessed the letter
			if (x == true) {
				userGuessed(result);
			} else { // If user hasn't guessed the letter
				userDidntGuess(ch, result);
			}
			x = false; // Make x false to check another letter entered by user
		}
	}
	//This method checks whether user's guess is already in letter or not
	private void checkGuessedLetter(char ch, String result){
		if(result.indexOf(ch) != - 1){
			println("You have already guessed this letter");
			
		} else println("That guess is correct");
	}
	/*
	 * If user guessed the letter, this method calls "displayWord" method from
	 * "HangmanCanvas" class to present guessed letter on canvas. Also this
	 * method checks whether this guessed letter was last or not(So, whether
	 * user has guessed whole world yet or not)
	 */
	private void userGuessed(String result) {
		canvas.displayWord(result);
		if (result.contains("-") == true) { // Check If user has guessed
											// whole word yet or not with this entered
											// letter. We need to check it
											// because if he did than at the end
											// we
											// need another text(like "You
											// guessed the word")
			println("The word now looks like this: " + result);
			println("You have " + guess + " guess left");
			
		}
	}

	/*
	 * If user didn't guess the word. this method calls "notIncorrectGuess"
	 * method from HangmanCanvas class to present incorrect letter on canvas.
	 * Also this method decreases number of "guess" (Attempt/Live) by one
	 */
	private void userDidntGuess(char ch, String result) {
		canvas.noteIncorrectGuess(ch);
		guess--;
		if (guess != 0) {
			println("There are no " + ch + " 's in the word");
			println("The word now looks like this: " + result);
			if (guess == 1) { // When only one guess is left
				println("You have only one guess left");
			} else
				println("You have " + guess + " guess left");
		}
	}

	// This method asks user to enter character.
	private char readChar() {
		while (true) {
			String s = readLine();
			if (s.length() == 1) { // If length of the string is one, it means
									// it is char
				s = s.toUpperCase();
				if(Character.isLetter(s.charAt(0))){
					return s.charAt(0);
				}
			}
			println("Wrong input");
			println("Please enter letter");
			print("Your guess: ");
		}
	}

	/*
	 * There are two ways of ending game. 1. If user has guessed the
	 * word.(Guessed all the letters) 2. User has no more lives ("guess")
	 */
	private boolean gameIsntOver(String result) {
		if (guess == 0) {
			fail.play();
			rope.play();
			println("You're completely hung");
			println("The word was: " + word);
			println("You lose");
			return false;
		}
		if (result.contains("-") == false) {
			applause.play();
			println("You guessed the word: " + word);
			println("You win");
			return false;
		}
		return true;
	}
	//This method returns user's wish (weather h wants to play or not)
	private boolean checkUserWish(String result){
		println("Do you want to play more? ");
		println("If you do type YES");	//Ask user if he wants to play more
		String w = readLine();	//Read user's answer
		w = w.toUpperCase();	//Make entered answer in Uppercases
		//Stop sounds
		fail.stop();
		applause.stop();
		rope.stop();
		if(w.equals("YES")){
			result = "";
			guess = 8; 
			word = "";
			return true;
		} else {
			bye.play();
			pause(1300);
			System.exit(0);
			return false;
		}
	}
}
