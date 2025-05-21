package jtt.vikachaze.dto;

import java.awt.Color;

public class Theme {
	private Color backgroundColor = new Color(237,237,237);
	private Color primaryColor  = new Color(255,255,255);
	private Color buttonColor = new Color(230,230,255);
	private Color primaryTextColor = new Color(0,0,0);
	private Color secondaryTextColor = new Color(120,120,120);
	private Color imageBorderColor = new Color(120,120,120);
	
	public Theme() {}
	
	public Theme(Color backgroundColor, Color primaryColor, Color buttonColor, Color primaryTextColor, Color secondaryTextColor, Color imageBorderColor) {
		this.backgroundColor = backgroundColor;
		this.primaryColor = primaryColor;
		this.buttonColor = buttonColor;
		this.primaryTextColor = primaryTextColor;
		this.secondaryTextColor = secondaryTextColor;
		this.imageBorderColor = imageBorderColor;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getPrimaryColor() {
		return primaryColor;
	}

	public Color getButtonColor() {
		return buttonColor;
	}

	public Color getPrimaryTextColor() {
		return primaryTextColor;
	}

	public Color getSecondaryTextColor() {
		return secondaryTextColor;
	}

	public Color getImageBorderColor() {
		return imageBorderColor;
	}
	
}
