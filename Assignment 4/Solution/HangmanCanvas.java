
/*
s * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	private static final int OFF = 20; // Offside from top
	private GLabel currentWord = new GLabel("");
	private GLabel currentLetters = new GLabel("");
	private String incorrects = "";
	private int penalty = 0;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();	//Remove All components from canvas
		drawGallow();	//Draw gallow
		resetVariables();	//Reset variables
	}
	//This method resets variables 
	private void resetVariables(){
		currentWord = new GLabel("");
		currentLetters = new GLabel("");
		penalty = 0;
		incorrects = "";
	}
	/*
	 * This method draws Gallow. First three lines: Scaffold, Beam and Rope
	 */
	private void drawGallow(){
		add(new GLine(getWidth() / 2 - BEAM_LENGTH, OFF, getWidth() / 2 - BEAM_LENGTH, OFF + SCAFFOLD_HEIGHT)); // Scaffold
		add(new GLine(getWidth() / 2, OFF, getWidth() / 2 - BEAM_LENGTH, OFF)); // Beam
		add(new GLine(getWidth() / 2, OFF, getWidth() / 2, OFF + ROPE_LENGTH)); // Rope
	}
	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		remove(currentWord);
		currentWord = new GLabel(word);
		currentWord.setLocation(getWidth() / 4 - currentWord.getWidth() / 2, getHeight() - 50);
		currentWord.setFont("Ariel-40");
		add(currentWord);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		penalty++;
		saveIncorrectLetters(letter);
		add(drawMan(penalty));
	}

	/*
	 * This method saves and adds on canvas incorrect letters entered by user
	 * 
	 */
	private void saveIncorrectLetters(char letter) {
		remove(currentLetters); // First of all remove previous label
		incorrects += letter; // Save new incorrect letter
		currentLetters = new GLabel(incorrects);
		currentLetters.setLocation(getWidth() / 4 - currentWord.getWidth() / 2, getHeight() - 20);
		add(currentLetters);
	}

	/*
	 * This method returns ) specific objects according to switch's cases. In
	 * four cases(3,4,5,6) also draws extra lines for "lower arms" and "hips"
	 */
	private GObject drawMan(int index) {
		switch (index) {
		// Head
		case 1:
			GOval oval = new GOval(getWidth() / 2 - HEAD_RADIUS, OFF + ROPE_LENGTH, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
			return oval;
		// Body
		case 2:
			GLine body = new GLine(getWidth() / 2, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS, getWidth() / 2,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
			return body;
		// Left hand
		case 3:
			GLine leftHand = new GLine(getWidth() / 2, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					getWidth() / 2 - UPPER_ARM_LENGTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
			add(new GLine(getWidth() / 2 - UPPER_ARM_LENGTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					getWidth() / 2 - UPPER_ARM_LENGTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH));
			return leftHand;
		// Right hand
		case 4:
			GLine rightHand = new GLine(getWidth() / 2, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					getWidth() / 2 + UPPER_ARM_LENGTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
			add(new GLine(getWidth() / 2 + UPPER_ARM_LENGTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
					getWidth() / 2 + UPPER_ARM_LENGTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH));
			return rightHand;
		// Left Leg
		case 5:
			GLine leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
					getWidth() / 2 - HIP_WIDTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			add(new GLine(getWidth() / 2, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2 - HIP_WIDTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH));
			return leftLeg;
		// Right leg
		case 6:
			GLine rightLeg = new GLine(getWidth() / 2 + HIP_WIDTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
					getWidth() / 2 + HIP_WIDTH, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			add(new GLine(getWidth() / 2, OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2 + HIP_WIDTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH));
			return rightLeg;
		// Left foot
		case 7:
			GLine leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
					getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			return leftFoot;
		// Right foot
		case 8:
			GLine rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
					getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
					OFF + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			return rightFoot;

		default:
			return null;
		}

	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
