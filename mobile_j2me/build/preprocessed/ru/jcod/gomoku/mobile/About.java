package ru.jcod.gomoku.mobile;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;


public class About extends Canvas {

    public About(){
        setFullScreenMode(true);
    }

    private void pclear(Graphics g){
        g.setColor(255,255,255);
        g.fillRect(0, 0, Mainmenu.instance.screen_w, Mainmenu.instance.screen_h);
        g.setColor(0);
        //g.drawRect(0, 0, 132, 176);
    };
    private void pabout(Graphics g){
        g.setColor(0);
        Font font=Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_SMALL);
        g.setFont(font);
        g.drawString("     Gomoku", 5, 20, Graphics.LEFT | Graphics.BOTTOM);
        g.drawString("  Крестики - нолики", 5,40, Graphics.LEFT | Graphics.BOTTOM);
        font=Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_SMALL);
        g.setFont(font);
        g.drawString("Develop by Jedy", 5,60, Graphics.LEFT | Graphics.BOTTOM);
        g.drawString("Copyright(c)Jedy 2008.", 5,80, Graphics.LEFT | Graphics.BOTTOM);
        g.drawString("evsyakov@gmail.com", 5,100, Graphics.LEFT | Graphics.BOTTOM);
        g.drawString("Press any key to return", 5,140, Graphics.LEFT | Graphics.BOTTOM);
    };

    protected void keyReleased(int KeyCode){
    	Display.getDisplay(Gomoku_mobile.instance).setCurrent(Gomoku_mobile.instance.mainmenu);
    }

	protected void paint(Graphics g) {
		pclear(g);
		pabout(g);
	}

}
