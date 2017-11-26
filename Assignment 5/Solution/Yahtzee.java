
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.HashMap;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	//Some "private static final" variables are added by me and are stored in YahtzeeConstants
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		nPlayers = enterNumberOfPlayers(); // Number of players
		// Map for player's name.
		playerNames = enterPlayerNames(); // Names of players
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		/*
		 * 2D Array for scores. In this array we save scores according to
		 * categories.
		 */
		playGame();
	}

	/*
	 * This method asks user to enter number of players Number of players
	 * (Input) must be from 1 to MAX_PLAYERS
	 */
	private int enterNumberOfPlayers() {
		int numOfPlayers;
		while (true) {
			numOfPlayers = dialog.readInt("Enter number of players");
			if (numOfPlayers <= MAX_PLAYERS && numOfPlayers > 0) {
				break;
			} else
				dialog.println("Please enter number of players from 1 to " + MAX_PLAYERS);
		}
		return numOfPlayers;
	}

	/*
	 * This method asks user to enter player names, and also puts them in map
	 * Key is number of player(first,second,third,etc). // Values is name of
	 * player
	 */
	private String[] enterPlayerNames() {
		String[] arr = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			arr[i - 1] = dialog.readLine("Enter name for player " + i);
			map.put(i, arr[i - 1]);
		}
		return arr;
	}

	/*
	 * Game Process Game is held for N_SCORING_CATEGORIES times. In our case
	 * there are 13 hands. In each hand each player has his own turn
	 */
	private void playGame() {
		// Array to save used categories;
		// nPlayers + 1 because of simpleness
		// In array count starts from zero
		// I want to save used category exactly in player's index graph
		int[][] used = new int[nPlayers + 1][N_CATEGORIES];
		for (int hand = 0; hand < N_SCORING_CATEGORIES; hand++) {
			// Loop to find which user's turn it is
			for (int player = 1; player <= nPlayers; player++) { //
				dicePart(player, map.get(player)); // Provides dice roll
				// Checks and saves category chosen by user
				int category = categoryPart(player, used);
				scorePart(category, player); // Provides score updates
			}
		}
		updateFinalScores();
		checkWinner();
	}

	/*
	 * Dice part. This method asks user to roll the dice. After first and second
	 * rolls asks to choose which dices he wants to re-roll
	 */
	private void dicePart(int player, String name) {
		display.printMessage(name + "'s turn! Click \"Roll Dice\" button to roll the dice.");
		display.waitForPlayerToClickRoll(player);
		display.displayDice(firstRoll());
		for (int i = 0; i < NROLL_IN_TURN - 1; i++) {
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\"");
			display.waitForPlayerToSelectDice();
			display.displayDice(secondAndThirdRolls());
		}
	}

	/*
	 * First Roll Make array with length N_DICE (number of dice) and give each
	 * element random value from 1-6
	 */
	private int[] firstRoll() {
		for (int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		return dice;
	}

	/*
	 * Second and Third Roll /*At second and third roll we need to roll only
	 * selected dices!
	 */
	private int[] secondAndThirdRolls() {
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) { // If dice selected than scroll it
											// (give it a new value from 1 - 6)
				dice[i] = rgen.nextInt(1, 6);
			}
		}
		return dice;
	}

	/*
	 * In this method user chooses category. We check whether this category is
	 * already used or not. If it is already used, we ask user to choose another
	 * category. Finally we return category chosen by user which is not used
	 * yet.
	 */
	private int categoryPart(int player, int[][] used) {
		display.printMessage("Select a category for this roll");
		int category;
		while (true) {
			category = display.waitForPlayerToSelectCategory();
			if (!categoryIsUsed(category, player, used)) {
				break;
			}
			display.printMessage("You have already chosen this category. Please choose different category");
		}
		return category;
	}

	/*
	 * Score Part This method takes value for category, player and array;
	 * According to "calculateScore" method calculates score; Adds this score in
	 * array on according position And updates Score-card; Include TOTAL graph
	 */
	private void scorePart(int category, int player) {
		int score = 0;
		/*
		 * If user's chosen category corresponds dices' combination, than
		 * calculate score according to category
		 */
		if (checkCategory(dice, category, player)) {
			score = calculateScore(category);
		}
		// If not, score should be zero in this category
		scoreWindow[category][player] = score;
		// This loop increases TOTAL on each turn (player's turn)
		for (int i = 1; i < TOTAL; i++) {
			 // See all frames and increase total
										// according to them
			scoreWindow[TOTAL][player] += scoreWindow[i][player];
		}
		// Update specific category
		display.updateScorecard(category, player, score);
		// Update TOTAL graph
		display.updateScorecard(TOTAL, player, scoreWindow[TOTAL][player]); // TOTAL graph

	}

	/*
	 * This method checks whether chosen category is already chosen (already
	 * used)
	 */
	private boolean categoryIsUsed(int category, int player, int[][] used) {
		if (used[player][category] == category) {
			return true;
		}
		// If category hasn't been used yet, so it is its' first time and save
		// it.
		used[player][category] = category;
		return false;
	}

	// This method checks whether dices' combination satisfies chosen category
	private boolean checkCategory(int[] dice, int category, int player) {
		switch (category) {
		case ONES:
			return true;
		case TWOS:
			return true;
		case THREES:
			return true;
		case FOURS:
			return true;
		case FIVES:
			return true;
		case SIXES:
			return true;
		case THREE_OF_A_KIND:
			return isOfAKined(3);
		case FOUR_OF_A_KIND:
			return isOfAKined(4);
		case FULL_HOUSE:
			return isFullHouse();
		case SMALL_STRAIGHT:
			return isSmallStraight();
		case LARGE_STRAIGHT:
			return isLargeStraight();
		case YAHTZEE:
			return isYahtzee();
		case CHANCE:
			return true;
		}
		return false;
	}

	// Calculate user's score according to category chosen by them
	private int calculateScore(int index) {
		switch (index) {
		case ONES: // ONES
			return calculateOneDigits(ONES);
		case TWOS: // TWOS
			return calculateOneDigits(TWOS);
		case THREES: // THREES
			return calculateOneDigits(THREES);
		case FOURS: // FOURS
			return calculateOneDigits(FOURS);
		case FIVES: // FIVES
			return calculateOneDigits(FIVES);
		case SIXES: // SIXES
			return calculateOneDigits(SIXES);
		case THREE_OF_A_KIND: // THREE_OF_A_KIND
			return calculateSumOfDice();
		case FOUR_OF_A_KIND: // FOUR_OF_A_KIND
			return calculateSumOfDice();
		case FULL_HOUSE: // FULL_HOUSE
			return FULL_HOUSE_SCORE;
		case SMALL_STRAIGHT: // SMALL_STRAIGHT
			return SMALL_STRAIGHT_SCORE;
		case LARGE_STRAIGHT: // LARGE_STRAIGHT
			return LARGE_STRAIGHT_SCORE;
		case YAHTZEE: // YAHTZEE
			return YAHTZEE_SCORE;
		case CHANCE: // CHANCE
			return calculateSumOfDice();
		default:
			return 0;
		}
	}

	/*
	 * This method calculates score for oneDigit Categories. Concretely those
	 * categories are: ONES, TWOS, THREES, FOURS, FIVES, SIXES
	 */
	private int calculateOneDigits(int index) {
		int score = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == index) {
				score += index;
			}
		}
		return score;
	}

	/*
	 * This method calculates score for categories in witch score equals sum of
	 * all dice values. Concretely those categories are: THREE_OF_A_KIND,
	 * FOUR_OF_A_KIND, CHANCE
	 */
	private int calculateSumOfDice() {
		int score = 0;
		for (int i = 0; i < dice.length; i++) {
			score += dice[i];
		}
		return score;
	}

	// This method alagebs zrdadobit masivs
	private int[] increasingOrder(int[] dice) {
		for (int j = 0; j < dice.length; j++) {
			for (int k = 0; k < dice.length; k++) {
				// We can't (mustn't) check last element cause dice[last + 1]
				// doesn't exist
				if (k == dice.length - 1) { //
					break;
				} else if (dice[k] > dice[k + 1]) {
					/*
					 * If element is more than next element Then exchange their
					 * positions in array
					 */
					int x = dice[k]; // Save current element
					dice[k] = dice[k + 1]; // Change current with next
					dice[k + 1] = x; // Change next with current
				}
			}
		}
		return dice;
	}

	/*
	 * Update UPPER_SCORE, UPPER_BONUS, LOWER_SCORE and TOTAL categories at the
	 * end of the game
	 */
	private void updateFinalScores() {
		for (int player = 1; player <= nPlayers; player++) {
			for (int cat = ONES; cat < TOTAL; cat++) {
				if (cat < UPPER_SCORE) { // Calculate Upper score
					scoreWindow[UPPER_SCORE][player] += scoreWindow[cat][player];
				} else if (cat > UPPER_BONUS && cat < LOWER_SCORE) { // Calculate
																		// Lower
																		// Score
					scoreWindow[LOWER_SCORE][player] += scoreWindow[cat][player];
				}
			}
			if (scoreWindow[UPPER_SCORE][player] > BONUS) { // Check Bonus
				scoreWindow[UPPER_BONUS][player] = BONUS_POINT;
			} else {
				scoreWindow[UPPER_BONUS][player] = 0;
			}
			display.updateScorecard(UPPER_SCORE, player, scoreWindow[UPPER_SCORE][player]);
			display.updateScorecard(LOWER_SCORE, player, scoreWindow[LOWER_SCORE][player]);
			display.updateScorecard(UPPER_BONUS, player, scoreWindow[UPPER_BONUS][player]);
			display.updateScorecard(TOTAL, player,
					scoreWindow[UPPER_SCORE][player] + scoreWindow[LOWER_SCORE][player] + scoreWindow[UPPER_BONUS][player]);
		}
	}
	// This method checks dice combination for Three of a kind and Four of a kind
	private boolean isOfAKined(int index) {
		int count = 0;
		for (int i = 0; i < dice.length; i++) {
			for (int j = 0; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
					count++;
					if (count == index) {
						return true;
					}
				}
			}
			count = 0; // To start count from zero for new index
						// (value)(dice)
		}
		return false;
	}

	/*
	 * In full house there should be three same and tow other same Let's make new
	 * array and count how many times each one meets us in array. Sum should be
	 * 3 + 3 + 3 + 2 + 2 = 13
	 */
	private boolean isFullHouse() {
		int[] res = new int[dice.length];
		int sum = 0;
		for (int i = 0; i < dice.length; i++) {
			for (int j = 0; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
					res[i] += 1;
				}
			}
		}
		for (int k = 0; k < res.length; k++) {
			sum += res[k];
		}
		if (sum == 13) {
			return true;
		}
		return false;
	}
	// This method checks dice combination for small straight
	private boolean isSmallStraight() {
		int[] small = increasingOrder(dice);
		/*
		 * From 5 dice we need 4 to be different, so in SMALL_STRAIGHT category
		 * there can be only two same dice not more
		 */
		int countEqual = 0;
		int countDif = 0;
		for (int i = 0; i < small.length; i++) {
			if (i == small.length - 1) {
				break;
			} else if (small[i] == small[i + 1]) {
				countEqual++;
				// From 5 dice we need 4 to be different, so in
				// SMALL_STRAIGHT category there can be only two same
				// dice not more
				if (countEqual > 2) {
					return false;
				}
			} else if (small[i] + 1 != small[i + 1]) {
				countDif++;
				if (countDif > 1) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * In LARGE-STRAIGHT category each value of dice should be different and
	 * more with one from each other For example: 1 2 3 4 5 or 2 3 4 5 6
	 */
	private boolean isLargeStraight() {
		int[] large = increasingOrder(dice);
		for (int i = 0; i < large.length; i++) {
			if (i == large.length - 1) { // Last element can't be compared
											// with [last + 1]. Avoid bug
				break;
			} else if (large[i] + 1 != large[i + 1]) {
				return false;
			}
		}
		return true;
	}
	// This method checks dice combination for Yahtzee
	private boolean isYahtzee() {
		for (int i = 0; i < dice.length; i++) {
			if (i == dice.length - 1) { // Last element can't be compared
										// with [last + 1]. Avoid bug
				break;
			} else if (dice[i] != dice[i + 1]) {
				return false;
			}
		}
		return true;
	}
	//This method checks who is winner
	private void checkWinner(){
		//Max score
		int maxScore = -1;
		String winners = "";
		//With this loop we find maximum Score
		for(int i = 1; i <= nPlayers; i++){
			if(scoreWindow[TOTAL][i] >= maxScore){
				maxScore = scoreWindow[TOTAL][i];
			}
		}
		//With this loop we find players who's score equals maxScore
		for(int i = 1; i <= nPlayers; i++){
			if(scoreWindow[TOTAL][i] == maxScore){
				winners += map.get(i) + ",";
			}
		}
		display.printMessage("Congratulations, " + winners + " you are the winner with a total score of " + maxScore + "!");
	}
	

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private IODialog dialog = getDialog();
	// In this array we save dice values after roll
	private int[] dice = new int[N_DICE];
	//In this array we save scores for each category and player
	private int[][] scoreWindow = new int[N_CATEGORIES + 1][MAX_PLAYERS + 1];
	//In this HashMap we save players' indexes(key) and names(value)
	private HashMap<Integer, String> map = new HashMap<>(); //
}
