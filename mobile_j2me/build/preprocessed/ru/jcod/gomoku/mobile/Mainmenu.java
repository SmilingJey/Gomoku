package ru.jcod.gomoku.mobile;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Mainmenu extends Canvas{
	static Mainmenu instance;
	public int screen_h;
	public int screen_w;
	public int active_b=0;

    Image img;
	private static String main_button[]={"New Game","About","Exit"};
	private static String main_action[]={"Game","About","Exit"};
	private static int sdvig[]={43,43,20};

	public Mainmenu(){
		super();
		instance=this;
		this.setFullScreenMode(true);
		screen_h=this.getHeight();
		screen_w=this.getWidth();
        try{
            img=Image.createImage("/ru/jcod/gomoku/mobile/res/back1.png");
        } catch(java.io.IOException e){System.out.println("error load images "+ e.getMessage());}
	}

    protected void keyReleased(int KeyCode){
        int action=getGameAction(KeyCode);
        if (action==DOWN) if (active_b==sdvig.length-1) active_b=0; else active_b++;
        if (action==UP) if (active_b==0) active_b=2; else active_b--;
        if (action==RIGHT) if (active_b==sdvig.length-1) active_b=0; else active_b++;
        if (action==LEFT)if (active_b==0) active_b=2; else active_b--;
        if (action==8) doaction();
        repaint();
        };

    public void doaction(){
    	if (main_action[active_b].equals("Game")){
    		Game.instance.new_game();
    		Display.getDisplay(Gomoku_mobile.instance).setCurrent(Gomoku_mobile.instance.game);
    	}
    	if (main_action[active_b].equals("Conf")){
    		Display.getDisplay(Gomoku_mobile.instance).setCurrent(Gomoku_mobile.instance.conf);
    	}
    	if (main_action[active_b].equals("About")){
    		Display.getDisplay(Gomoku_mobile.instance).setCurrent(Gomoku_mobile.instance.about);
    	}
    	if (main_action[active_b].equals("Exit")){
    		Gomoku_mobile.quitApp();
    	}
    }
    private void pclear(Graphics g){
        g.setColor(0xffffff);
        g.fillRect(0, 0, screen_w, screen_h);
        g.drawImage(img, screen_w/2, screen_h/2,Graphics.HCENTER | Graphics.VCENTER);
    };
    private void paint_mb(Graphics g){
    	Font font;
    	font=Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_PLAIN,Font.SIZE_LARGE);
    	g.setFont(font);
        int hh=font.getHeight();
        for (int i=0;i<3;i++){
        	if (i!=active_b){
                g.setColor(255,255,255);
                g.fillRect(screen_w/2-60, screen_h/2+i*(hh+4)-hh, 120, hh+4);
                g.setColor(255,0,0);
                g.drawRect(screen_w/2-60,screen_h/2+i*(hh+4)-hh, 120, hh+4);
                g.setColor(0,0,0);
        		g.drawString(main_button[i],(int)screen_w/2,screen_h/2+i*(hh+4)+2, Graphics.HCENTER | Graphics.BOTTOM);
        	} else{
                g.setColor(0x999999);
                g.fillRect(screen_w/2-60,screen_h/2+i*(hh+4)-hh,120, hh+4);
                g.setColor(255,0,0);
                g.drawRect(screen_w/2-60,screen_h/2+i*(hh+4)-hh, 120, hh+4);
                g.setColor(0,0,0);
        		g.drawString(main_button[i],(int)screen_w/2,screen_h/2+i*(hh+4)+2, Graphics.HCENTER| Graphics.BOTTOM);
        	}
        }
    };

    protected void paint(Graphics g) {
    	pclear(g);
    	paint_mb(g);

    }
}
