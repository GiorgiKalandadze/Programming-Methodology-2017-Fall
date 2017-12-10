/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import sun.java2d.loops.ProcessPath.DrawHandler;

import java.awt.event.*;
import java.util.*;

import com.sun.xml.internal.bind.v2.runtime.Name;

import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		list = new ArrayList<NameSurferEntry>();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		list.clear();
		update();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		list.add(entry);
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		drawBackground();
		drawGraphic();
	}
	/*	This method draws Background
	 * 	Concretely: Vertical and Horizontal Lines
	 * 	and Years at the bottom of window
	 */
	private void drawBackground(){
		drawVerticalLines();
		drawHorizontalLines();
		drawYears();
	}
	//This method draws Horizontal Lines
	private void drawHorizontalLines(){
		GLine up = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine down = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(up);
		add(down);
	}
	//This method draws Vertical Lines
	private void drawVerticalLines(){
		for(int i = 1; i < NDECADES; i++){
			GLine line = new GLine(getWidth() / NDECADES * i, 0, getWidth() / NDECADES * i, getHeight());
			add(line);
		}
	}
	//This method draws Years at the bottom of window
	private void drawYears(){
		int year = START_DECADE;
		for(int i = 0; i < NDECADES; i++){
			GLabel years = new GLabel(Integer.toString(year));
			years.setLocation(OFF + getWidth() / NDECADES * i, getHeight() - OFF);
			add(years);
			year += DIF;
		}
	}
	//This method calculates place for rank on graphic
	private double calculateCorY(double rank){
		if(rank != MIN_RANK){
			//Y coordinate for all except zero
			return (rank / MAX_RANK) * ((getHeight() - 2 * GRAPH_MARGIN_SIZE)) + GRAPH_MARGIN_SIZE;
		}
		return getHeight() - GRAPH_MARGIN_SIZE; //Y coordinate for zero
	}
	//This method draws all graphics which are in array
	private void drawGraphic(){
		for(int i = 0; i < list.size(); i++){
			drawEach(list.get(i),i);
		}
	}
	//This method draws specific(only one whole) graphic
	//Which consists of NDECADES number of Lines
	private void drawEach(NameSurferEntry entry, int color){
		for(int i = 0; i < NDECADES -1; i++){
			GLine line =  new GLine(getWidth() / NDECADES * i, calculateCorY(entry.getRank(i)),
					getWidth() / NDECADES * (i + 1), calculateCorY(entry.getRank(i+1)));
			Color col = chooseColor(color);
			line.setColor(col);
			add(line);
			
			//Draw name and rank
			GLabel name = drawNames(entry, i);
			name.setColor(col);
			add(name,getWidth() / NDECADES * i + OFF, calculateCorY(entry.getRank(i)));
		}
	}
	/*	This method draws Names and according ranks
	 * 	near the line
	 */
	private GLabel drawNames(NameSurferEntry entry,int decade){
		String name = entry.getName();
		String rank = Integer.toString(entry.getRank(decade));
		GLabel lab;
		if(entry.getRank(decade) != MIN_RANK){
			lab = new GLabel(name + " " + rank);
		} else {
			lab = new GLabel(name + " *"); //Label when rank equals 0
		}
		return lab;
	}
	//This method chooses color according to number of graphic
	private Color chooseColor(int color){
		if(color % 4 == 0){
			return COL_1;
		} else if(color % 4 == 1){ 
			return COL_2;
		} else if(color % 4 == 2){ 
			return COL_3;
		} else if(color % 4 == 3){ 
			return COL_4;
		}
		return COL_1;
	}
	
	
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	
	
	private ArrayList<NameSurferEntry> list;
}
