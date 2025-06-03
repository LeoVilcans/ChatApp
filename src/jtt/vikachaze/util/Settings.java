package jtt.vikachaze.util;

import java.awt.Color;

import jtt.vikachaze.dto.Theme;

public class Settings {
	
	public static enum ThemeChoice {
	    CLASSIC,
	    DARK,
	    LIGHT
	}
	
	private static ThemeChoice currentThemeChoice = ThemeChoice.CLASSIC;
	private static Theme currentTheme;
	
	public static void setTheme(ThemeChoice newChoice) {
		currentThemeChoice = newChoice;
	}
	
	public static Theme getTheme() {
		switch(currentThemeChoice) {
			case CLASSIC:
				return new Theme();
			case DARK:
				//backgroundColor, primaryColor, buttonColor, textColor
				return new Theme(new Color(66,66,66), new Color(128,128,128), new Color(128,128,128), new Color(230,230,230));
			case LIGHT:
				return new Theme(new Color(255, 255, 255), new Color(0, 0, 0), new Color(235,235,235), new Color(60,60,60));
			default:
				return new Theme();
		}
	}
}
