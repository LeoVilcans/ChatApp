package jtt.vikachaze.util;

import java.awt.Color;

import jtt.vikachaze.dto.Theme;

public class Settings {
	
	public static enum ThemeChoice {
	    CLASSIC,
	    DARK,
	    LIGHT
	}
	
	private static ThemeChoice currentThemeChoice = ThemeChoice.DARK;
	private static Theme currentTheme;
	
	public static void setTheme(ThemeChoice newChoice) {
		currentThemeChoice = newChoice;
	}
	
	public static Theme getTheme() {
		switch(currentThemeChoice) {
			case CLASSIC:
				return new Theme();
			case DARK:
				//backgroundColor, primaryColor, buttonColor, primaryTextColor, secondaryTextColor, imageBorderColor
				return new Theme(new Color(66,66,66), new Color(128,128,128), new Color(128,128,128), new Color(230,230,230), new Color(160,160,160), new Color(200,200,200));
			case LIGHT:
				return new Theme();
			default:
				return new Theme();
		}
	}
}
