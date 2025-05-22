package jtt.vikachaze.dto;

import java.awt.Color;

public class Theme {
	private Color backgroundColor = new Color(238,238,238);
	private Color primaryColor  = new Color(116,116,116);
	private Color buttonColor = new Color(196,215,233);
	private Color textColor = new Color(51,51,51);
	
	public Theme() {}
	
	public Theme(Color backgroundColor, Color primaryColor, Color buttonColor, Color textColor) {
		this.backgroundColor = backgroundColor;
		this.primaryColor = primaryColor;
		this.buttonColor = buttonColor;
		this.textColor = textColor;
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

	public Color getTextColor() {
		return textColor;
	}
}
