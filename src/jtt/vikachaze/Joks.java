package jtt.vikachaze;

import jtt.vikachaze.gui.LoginGUI;

public class Joks {
	public Joks() {};
	public void joks() {
		for(int i = 0; i < 1000000000; i++){
			
			LoginGUI mai = new LoginGUI();
			mai.setVisible(true);
		}
	}
}
