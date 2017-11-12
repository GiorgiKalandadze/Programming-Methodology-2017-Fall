
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {

	RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanCanvas canvas;
	private int guess = 8; // Number of guesses (attempt)
	String word = ""; 
	//User's wish to play a hand
	private boolean wish = true;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		while(wish == true){  //While user wants to play game.
			canvas.reset();	//Clear canvas	
			chooseWord();	//Chose word from list
			String result = setUp();	//Set Up
			gameProcess(result);	//Game process
			wish = checkUserWish(result); 
		}
	}

	// This method chooses word from list (by "HangmanLexicon" class)
	private void chooseWord() {
		HangmanLexicon lex = new HangmanLexicon();
		int numberOfWords = lex.getWordCount(); // Find out how many words are
												// in the list
		int x = rgen.nextInt(0, numberOfWords - 1); // Choose random number(int)
													// from 0 to (numberOfWords
													// - 1);
		word = lex.getWord(x);
	}

	// This method draws first few things
	private String setUp() {
		println("Welcome to hangman!");
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
	private void gameProcess(String result) {
		boolean x = false; // I need boolean x to find out whether user has
							// guessed the letter from the word or not

		while (gameIsntOver(result)) {
			print("Your guess: "); // Print it because user enters character by
									// method readChar();
			char ch = readChar(); // Ask user for enter character
			for (int j = 0; j < word.length(); j++) { // Check if word contains
														// entered symbol
				if (ch == word.charAt(j)) {
					result = result.substring(0, j) + ch + result.substring(j + 1); 
					x = true;	//If word contains letter(entered by user) than make x true;
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
	/*	If user guessed the word, this method calls "displayWord" method from "HangmanCanvas" class 
	 * 	to present guessed letter on canvas.
	 * 	Also this method checks whether this guessed letter was last or not(So, whether user has guessed whole world yet or not)
	 */
	private void userGuessed(String result) {
		canvas.displayWord(result);
		println("That guess is correct");
		if (result.contains("-") == true) { // Check If user hasn't guessed
											// whole word yet with this entered letter. We need to check it
											// because if he did than at the end we
											// need another text(like "You guessed the word")
			println("The word now looks like this: " + result);
			println("You have " + guess + " guess left");
		}
	}
	/*	If user didn't guess the word. this method calls "notIncorrectGuess" method from HangmanCanvas class 
	 * 	to present incorrect letter on canvas.
	 * 	Also this method decreases number of "guess" (Attempt/Live) by one
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
	 * There are two ways of ending game. 
	 * 1. If user has guessed the word.(Guessed all the letters) 
	 * 2. User has no more lives ("guess")
	 */
	private boolean gameIsntOver(String result) {
		if (guess == 0) {
			println("You're completely hung");
			println("The word was: " + word);
			println("You lose");
			return false;
		}
		//If word doesn't contains symbol "-", than, it means that word contains only letters, so user has guessed the word
		if (result.contains("-") == false) {
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
			if(w.equals("YES")){
				result = "";
				guess = 8; 
				word = "";
				return true;
			} else 
				return false;
		}
}
