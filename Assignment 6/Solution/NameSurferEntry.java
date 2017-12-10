/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		StringTokenizer tok = new StringTokenizer(line);
		//We know that name is first token
		name = tok.nextToken();
		//Save ranks in Integer array
		for(int j = 0; tok.hasMoreTokens(); j++){
			decades[j] = Integer.parseInt(tok.nextToken());
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) { 
		return decades[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String text = "";
		text = "\"" + name + " [";
		for(int i = 0; i < NDECADES - 1; i++){
			text += decades[i] + " ";
		}
		//Rank of last decade. (To avoid this symbol " "(space) at the end before "]")
		text += decades[NDECADES - 1] + "]\"";
		return text;
	}
	
	private String name; //Name
	private int[] decades = new int[NDECADES]; //Decades for specific name 
}

