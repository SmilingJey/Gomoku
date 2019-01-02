package ru.jcod.gomoku.mobile;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class Gomoku_mobile extends MIDlet {

	static Gomoku_mobile instance;
	Mainmenu mainmenu=new Mainmenu();
	About about=new About();
	Conf conf=new Conf();
	Game game=new Game();

	public Gomoku_mobile() {
		instance = this;
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        mainmenu=null;
        about=null;
        conf=null;
        game=null;
	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(mainmenu);
	}
    public static void quitApp() {
        //instance.destroyApp(true);
        instance.notifyDestroyed();
        instance = null;
    }

}