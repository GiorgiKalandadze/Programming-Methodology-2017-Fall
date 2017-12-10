
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		drawInteractors(); // Draw Interactors
		addActionListeners();
		textField.addActionListener(this);
		graph = new NameSurferGraph();
		add(graph);
	}

	// This method draws interactors
	private void drawInteractors() {
		textField = new JTextField(SIZE);
		add(new JLabel("Name"), SOUTH);
		add(textField, SOUTH);
		add(new JButton("Graph"), SOUTH);
		add(new JButton("Clear"), SOUTH);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Graph") || e.getSource().equals(textField)) {
			entry = data.findEntry(textField.getText());
			if (entry != null) {
				graph.addEntry(entry); //Save new entry
				graph.update();			//Update graphic
				textField.setText(""); // Make textField clear
			} else {
				textField.setText("");
			}
		} else if (cmd.equals("Clear")) { // Clear Button
			graph.clear();
		}
	}

	// Instance Variables
	private JTextField textField;
	private NameSurferEntry entry;
	private NameSurferDataBase data = new NameSurferDataBase("names-data.txt");
	private NameSurferGraph graph;
}
