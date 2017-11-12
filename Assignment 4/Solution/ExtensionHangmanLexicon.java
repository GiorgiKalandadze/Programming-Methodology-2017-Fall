
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

public class ExtensionHangmanLexicon  {
	private BufferedReader r = null;
	private ArrayList list = new ArrayList();
	private String text = "";

	
	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return list.size();
	}
	
	/** Returns the word at the specified index. */
	
	public String getWord(int index){
		return (String)list.get(index);
	}
	// This is the HangmanLexicon constructor
	public ExtensionHangmanLexicon(int choice) {
		try {
			 	if(choice == 1){
			 		r = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			 	} else if(choice == 2){ 
			 		r = new BufferedReader(new FileReader("textCountries.txt"));
			 	} else if(choice == 3){
			 		r = new BufferedReader(new FileReader("textfootball.txt"));
			 	} else if(choice == 4){
			 		r = new BufferedReader(new FileReader("textFreeuni's Lectures.txt"));
			 	} else if(choice == 5){
				 r = new BufferedReader(new FileReader("textcompanies.txt"));
			 	}
		
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
