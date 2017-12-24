
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		//To avoid rewrite of messages
		if (text != null) {
			remove(text);
		}
		text = new GLabel(msg);
		text.setLocation(getWidth() / 2 - text.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		text.setFont(MESSAGE_FONT);
		add(text);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		if (profile == null) {
			removeAll();
		} else {
			removeAll();
			//Height difference between friends' names on canvas
			double dif = 0; 
			//Name
			GLabel name = new GLabel(profile.getName());
			addNameCanvas(name);
			//Image
			addImageCanvas(profile,name);
			//Status
			addStatusCanvas(profile,name);
			//Friends
			addFriendsCanvas(profile,dif);
		}
	}

	private void addNameCanvas(GLabel name) {
		name.setColor(Color.BLUE);
		name.setLocation(LEFT_MARGIN, TOP_MARGIN + name.getAscent());
		name.setFont(PROFILE_NAME_FONT);
		add(name);
	}

	private void addStatusCanvas(FacePamphletProfile profile, GLabel name) {
		GLabel status;
		if (profile.getStatus() == null) {
			status = new GLabel("No current status");
		} else {
			status = new GLabel(profile.getName() + " is " + profile.getStatus());
		}
		status.setLocation(LEFT_MARGIN,
				TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
		status.setFont(PROFILE_STATUS_FONT);
		add(status);
	}

	private void addFriendsCanvas(FacePamphletProfile profile, double dif) {
		GLabel title = new GLabel("Friends:");
		title.setLocation(getWidth() / 2 - title.getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN);
		title.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(title);
		Iterator<String> iterator = profile.getFriends();
		while (iterator.hasNext()) {
			GLabel friend = new GLabel(iterator.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			dif += friend.getHeight();
			friend.setLocation(getWidth() / 2 - title.getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN + dif);
			add(friend);
		}
	}

	private void addImageCanvas(FacePamphletProfile profile, GLabel name) {
		GImage img = profile.getImage();
		if (img == null) {
			GRect rect = new GRect(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + name.getAscent(), IMAGE_WIDTH,
					IMAGE_HEIGHT);
			add(rect);
			GLabel noImageText = new GLabel("No Image");
			noImageText.setFont(PROFILE_IMAGE_FONT);
			noImageText.setLocation(LEFT_MARGIN + IMAGE_WIDTH / 2 - noImageText.getWidth() / 2,
					TOP_MARGIN + IMAGE_MARGIN + name.getAscent() + IMAGE_HEIGHT / 2 + noImageText.getAscent() / 2);
			add(noImageText);
		} else {
			img.setBounds(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + name.getAscent(), IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img);
		}
	}

	// Instance variables
	GLabel text;
}
