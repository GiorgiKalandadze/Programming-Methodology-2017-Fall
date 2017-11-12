
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import acmx.export.java.util.ArrayList;

public class HangmanLexicon  {
	
	private ArrayList list = new ArrayList();
	private String text = "";
	private BufferedReader r = null;
	
	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return list.size();
	}
	
	/** Returns the word at the specified index. */
	
	
	//This method returns word from the list at the specified index
	public String getWord(int index){
		return (String)list.get(index);
	}
	// This is the HangmanLexicon constructor
	public HangmanLexicon() {
		try {
			r = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			text = r.readLine(); // Read and save in text
			while (text != null) { // While there is still words
				list.add(text); // Add them in ArrayList called "list"
				text = r.readLine();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
