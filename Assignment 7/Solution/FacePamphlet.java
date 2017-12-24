
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management .
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program // SOS Console To Program
		implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		drawInteractors();
		addActionListeners();
		changeStatus.addActionListener(this);
		changePicture.addActionListener(this);
		addFriend.addActionListener(this);
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	private void drawInteractors() {
		westInteractors();
		northInteractors();
	}

	private void westInteractors() {
		add(changeStatus, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(changePicture, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(addFriend, WEST);
		add(new JButton("Add Friend"), WEST);
	}

	private void northInteractors() {
		add(new JLabel("Name"), NORTH);
		add(name, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) {
			addProfile();
		} else if (cmd.equals("Delete")) {
			deleteProfile();
		} else if (cmd.equals("Lookup")) {
			lookUpProfile();
		} else if (cmd.equals("Change Status") || e.getSource().equals(changeStatus)) {
			changeStatus();
		} else if (cmd.equals("Change Picture") || e.getSource().equals(changePicture)) {
			changePicture();
		} else if (cmd.equals("Add Friend") || e.getSource().equals(addFriend)) {
			addFriendProfile();
		}
	}

	/*
	 * This method adds new profile in base and updates canvas according to it
	 */
	private void addProfile() {

		if (name.getText().length() == 0) {
			canvas.showMessage("Name graph is empty");
		}
		// If base doesn't contains entered profile create new one
		else if (!data.containsProfile(name.getText())) {
			// Create new profile
			currentProfile = new FacePamphletProfile(name.getText());
			// Add the profile in base
			data.addProfile(currentProfile);
			// Update canvas
			canvas.displayProfile(currentProfile);
			// Update message
			canvas.showMessage("New profile created");
			// Clear "name" JTextfield
			name.setText("");
		} else {
			// If profile is already in base make currentProfile this profile
			currentProfile = data.getProfile(name.getText());
			// Update canvas
			canvas.displayProfile(currentProfile);
			// Update message
			canvas.showMessage("A profile with the name " + currentProfile.getName() + " already exists");
			// Clear "name" JTextfield
			name.setText("");
		}
	}

	private void deleteProfile() {
		if (name.getText().length() == 0) {
			canvas.showMessage("Name graph is empty");
		} else {
			currentProfile = data.getProfile(name.getText());
			// If profile exists
			if (currentProfile != null) {
				// Delete profile from base
				data.deleteProfile(currentProfile.getName());
				// Update canvas
				canvas.displayProfile(null);
				// Update message
				canvas.showMessage("Profile of " + currentProfile.getName() + " deleted");
				// Clear "name" JTextfield
				name.setText("");
				// After delete of profile make currentProfile null
				currentProfile = null;
			} else {
				// Update canvas
				canvas.displayProfile(null);
				// Update message
				canvas.showMessage("A profile with the name " + name.getText() + " doesn't exists");
				// Clear "name" JTextfield
				name.setText("");
			}
		}
	}

	private void lookUpProfile() {
		if (name.getText().length() == 0) {
			canvas.showMessage("Name graph is empty");
		} else if (data.containsProfile(name.getText())) {
			currentProfile = data.getProfile(name.getText());
			// Update canvas
			canvas.displayProfile(currentProfile);
			// Update message
			canvas.showMessage("Displaying " + currentProfile.getName());
			// Clear "name" JTextfield
			name.setText("");
		} else {
			// Update canvas
			canvas.displayProfile(null);
			// Update message
			canvas.showMessage("Profile with the name " + name.getText() + " doesn't exists");
			// Make currentProfile null
			currentProfile = null;
			// Clear "name" JTextfield
			name.setText("");
		}
	}

	private void changeStatus() {
		if (currentProfile != null) {
			// Renew status
			currentProfile.setStatus(changeStatus.getText());
			// Update canvas
			canvas.displayProfile(currentProfile);
			// Update message
			canvas.showMessage("Status updated to " + changeStatus.getText());
			// Clear "changeStatus" JTextfield
			changeStatus.setText(""); // Clear "changeStatus" JTextfield
		} else {
			// Clear canvas
			canvas.displayProfile(null);
			// Update message
			canvas.showMessage("Please select a profile to change status");
			// Clear "changeStatus" JTextfield
			changeStatus.setText("");
		}
	}

	private void changePicture() {
		if (currentProfile != null) {
			// Check whether "changePicture" JTextfield is empty or not
			// If is empty do nothing
			if (!changePicture.getText().isEmpty()) {
				GImage image = null;
				try {
					image = new GImage(changePicture.getText());
				} catch (ErrorException ex) {
					// Update message
					canvas.showMessage("Unable to open image file: " + changePicture.getText());
				}
				if (image != null) {
					// Renew image
					currentProfile.setImage(image);
					// Update canvas
					canvas.displayProfile(currentProfile);
					// Update message
					canvas.showMessage("Picture updated");
				} else {
					// Update message
					canvas.showMessage("Picture doesn't exists");

				}
				// Clear "changePicture" JTextfield
				changePicture.setText("");
			}
		} else {
			// Clear canvas
			canvas.displayProfile(null);
			// Update message
			canvas.showMessage("Please select a profile to change picture");
			// Clear "changePicture" JTextfield
			changePicture.setText("");
		}

	}

	private void addFriendProfile() {
		if (currentProfile != null) {
			if (addFriend.getText().equals(currentProfile.getName())) {
				canvas.showMessage("Can't add yourself as a friend");
			} else {
				if (data.containsProfile(addFriend.getText())) {
					boolean isntInList = currentProfile.addFriend(addFriend.getText());
					// If a new profile is already currentProfile's friend
					if (!isntInList) {
						// Update message
						canvas.showMessage(
								currentProfile.getName() + " already has " + addFriend.getText() + " as a friend");
					} else {
						FacePamphletProfile newFriendProfile = data.getProfile(addFriend.getText());
						// Add currentProfile as a new profile's friend
						newFriendProfile.addFriend(currentProfile.getName());
						// Update canvas
						canvas.displayProfile(currentProfile);
						// Update message
						canvas.showMessage(addFriend.getText() + " added as a friend");
					}
				} else {
					// Update message
					canvas.showMessage(addFriend.getText() + " doesn't exists");
				}
				// Clear "addFriend" JTextfield
				addFriend.setText("");
			}
		} else {
			// Update message
			canvas.showMessage("Please select a profile to add friend");
			// Clear "addFriend" JTextfield
			addFriend.setText("");
		}

	}

	// Instance Variables
	private JTextField changeStatus = new JTextField(TEXT_FIELD_SIZE);
	private JTextField changePicture = new JTextField(TEXT_FIELD_SIZE);
	private JTextField addFriend = new JTextField(TEXT_FIELD_SIZE);
	private JTextField name = new JTextField(TEXT_FIELD_SIZE);
	private FacePamphletDatabase data = new FacePamphletDatabase();
	private FacePamphletProfile currentProfile;
	private FacePamphletCanvas canvas;
}
