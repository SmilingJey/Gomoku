package ru.jcod.gomoku.mobile;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class Game extends Canvas {

    public static Game instance;
    public int n;
    public int razm;
    public int otst_left;
    public int otst_top;
    public Pole pole;
    public Xoxo xoxo;
    public int screen_h;
    public int screen_w;
    //x,y
    public int active_p[] = {0, 0};
    public String play1 = "Humen";
    public String play2 = "Computer";
    public byte play1_xo = 1; //1-x
    public byte play2_xo = 2; //2-0
    public int now_hod = 0;
    public boolean comp_dohod = false;

    private void p_game_sceen(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, screen_w, screen_h);
        g.setColor(0);

        for (int i = 0; i <= n; i++) {
            g.drawLine(otst_left, i * razm + otst_top, n * razm + otst_left, i * razm + otst_top);
        }
        for (int i = 0; i <= n; i++) {
            g.drawLine(i * razm + otst_left, otst_top, i * razm + otst_left, n * razm + otst_top);
        }
    }

    private void p_active_p(Graphics g) {
        g.setColor(255, 200, 200);
        g.fillRect(otst_left + razm * active_p[0] + 1, otst_top + razm * active_p[1] + 1, razm - 1, razm - 1);
    }

    private void p_label(Graphics g) {
        g.setColor(0);
        Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        g.setFont(font);
        if (play1_xo == 1) {
            g.drawLine(2, screen_h - 20, 7, screen_h - 15);
            g.drawLine(7, screen_h - 20, 2, screen_h - 15);
            g.drawArc(2, screen_h - 10, 6, 6, 0, 360);
        } else {
            g.drawLine(2, screen_h - 10, 7, screen_h - 5);
            g.drawLine(7, screen_h - 10, 2, screen_h - 5);
            g.drawArc(2, screen_h - 20, 6, 6, 0, 360);
        }
        g.drawString(play1, 15, screen_h - 10, Graphics.LEFT | Graphics.BOTTOM);
        g.drawString(play2, 15, screen_h, Graphics.LEFT | Graphics.BOTTOM);
    }

    private void p_xoxo(Graphics g) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (pole.pole[i][j] == 2) {
                    g.setColor(0, 0, 255);
                    //g.drawArc(0, 0,razm, razm, 0, 360);
                    g.drawArc(otst_left + razm * i + 2, otst_top + razm * j + 2, razm - 4, razm - 4, 0, 360);
                    //g.drawRoundRect(0, 0, 10, 10, razm-4, razm-4);
                    //g.copyArea(0, 0, razm, razm, 50,50, Graphics.TOP | Graphics.LEFT);
                }
                if (pole.pole[i][j] == 1) {
                    g.setColor(255, 0, 0);
                    g.drawLine(otst_left + razm * i + 2, otst_top + razm * j + 2, otst_left + razm * (i + 1) - 2, otst_top + razm * (j + 1) - 2);
                    g.drawLine(otst_left + razm * (i + 1) - 2, otst_top + razm * j + 2, otst_left + razm * i + 2, otst_top + razm * (j + 1) - 2);
                }
            }
        }
    }

    protected void keyPressed(int KeyCode) {
        int action = getNormalCode(KeyCode);
        int key=0;
        String keyname=getKeyName(KeyCode);
        if (keyname.equals("1") || action==-6){
            key=-6;
        }else if (keyname.equals("3") || action==-7){
            key=-7;
        }else if (keyname.equals("2") || action==1){
            key=1;
        }else if (keyname.equals("4") || action==2){
            key=2;
        }else if (keyname.equals("6") || action==5){
            key=5;
        }else if (keyname.equals("5") || action==-8){
            key=-8;
        }else if (keyname.equals("8") || action==6){
            key=6;
        }
        
        if (key == 6) {
            if (active_p[1] == n - 1) {
                active_p[1] = 0;
            } else {
                active_p[1]++;
            }
        }
        else if (key == 1) {
            if (active_p[1] == 0) {
                active_p[1] = n - 1;
            } else {
                active_p[1]--;
            }
        }

        else if (key == 5) {
            if (active_p[0] == n - 1) {
                active_p[0] = 0;
            } else {
                active_p[0]++;
            }
        }

        else if (key == 2) {
            if (active_p[0] == 0) {
                active_p[0] = n - 1;
            } else {
                active_p[0]--;
            }
        }
        else if (key == -8) {
            if (pole.game_end) {
                this.new_game();
            } else {
                humen_hod();
            }
        }

        else if (KeyCode==KEY_POUND){
            Gomoku_mobile.quitApp();
        }
        else if (KeyCode==KEY_STAR){
            this.new_game();
        }
        repaint();
    }

    ;

    public void comp_hod() {
        if (!comp_dohod) {
            if ((play1.equals("Computer") && now_hod == 0) || (play2.equals("Computer") && now_hod == 1)) {
                comp_dohod = true;
                if (now_hod == 0) {
                    xoxo.hod(play1_xo);
                    pole.game_end(play1_xo);
                } else {
                    xoxo.hod(play2_xo);
                    pole.game_end(play2_xo);
                }
                now_hod = (now_hod == 0) ? 1 : 0;
                repaint();
                comp_dohod = false;
            }
        }
    }

    public void humen_hod() {
        if (!pole.game_end && pole.pole[active_p[0]][active_p[1]] == 0) {
            if (now_hod == 0) {
                pole.pole[active_p[0]][active_p[1]] = play1_xo;
                pole.game_end(play1_xo);
            } else {
                pole.pole[active_p[0]][active_p[1]] = play2_xo;
                pole.game_end(play2_xo);
            }

            if (!pole.game_end) {
                now_hod = (now_hod == 0) ? 1 : 0;
                comp_hod();
            }
        }
        repaint();
    }

    public Game() {
        instance = this;
        this.setFullScreenMode(true);
        screen_h = this.getHeight();
        screen_w = this.getWidth();
        n = 15;
        razm = (int) (screen_w - 2) / n;
        otst_left = (int) (screen_w - razm * n) / 2;
        otst_top = 3;
        pole = new Pole(n);
        repaint();
    }

    public void p_end(Graphics g) {
        if (pole.game_end) {
            g.setColor(0);
            Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
            g.setFont(font);
            g.drawString("End game", screen_w - 5, screen_h - 10, Graphics.RIGHT | Graphics.BOTTOM);
            g.drawString("Win:" + pole.win, screen_w - 5, screen_h, Graphics.RIGHT | Graphics.BOTTOM);
            if (!pole.win.equals("no")) {
                g.setColor(0, 255, 0);
                g.drawLine(otst_left + razm * pole.i_end_n + razm / 2, otst_top + razm * pole.j_end_n + razm / 2,
                        otst_left + razm * (pole.i_end_k + 1) - razm / 2, otst_top + razm * (pole.j_end_k + 1) - razm / 2);
            }
        }

    }
    public void p_button(Graphics g){
        if (!pole.game_end) {
            g.setColor(0);
            Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
            g.setFont(font);
            g.drawString("*-new game", screen_w - 5, screen_h - 10, Graphics.RIGHT | Graphics.BOTTOM);
            g.drawString("#-exit", screen_w - 5, screen_h, Graphics.RIGHT | Graphics.BOTTOM);
        }
    }

    protected void paint(Graphics g) {
        p_game_sceen(g);
        p_active_p(g);
        p_xoxo(g);
        p_label(g);
        p_end(g);
        p_button(g);
    }

    public void new_game() {
        pole = new Pole(n);
        xoxo = new Xoxo(pole);

        int prot = Conf.instance.cg1.getSelectedIndex();
        int first = Conf.instance.cg2.getSelectedIndex();
        int vi_igr = Conf.instance.cg3.getSelectedIndex() + 1;

        if (prot == 0) {
            play1 = "Humen";
            play2 = "Humen";
            play1_xo = (byte) vi_igr;
            play2_xo = (byte) ((vi_igr == 1) ? 2 : 1);
        } else if (first == 1) {
            play1 = "Computer";
            play2 = "Humen";
            play2_xo = (byte) vi_igr;
            play1_xo = (byte) ((vi_igr == 1) ? 2 : 1);
        } else if (first == 0) {
            play1 = "Humen";
            play2 = "Computer";
            play1_xo = (byte) vi_igr;
            play2_xo = (byte) ((vi_igr == 1) ? 2 : 1);
        }
        now_hod = 0;
        comp_hod();
    }


    public int getNormalCode(int i) {
        int j = 0;
        String s1="", s2="";
        j = getGameAction(i);
        try {
            s1 = getKeyName(i);
        }catch(IllegalArgumentException _ex) {s1 = null;}
        if(s1 != null){//String s2;
            if((s2 = s1.toLowerCase()).equals("soft1") ||
                s2.equals("Softkey 1") || s2.equals("softkey 1 ") ||
                s2.equals("soft 1") || s2.equals("soft_1") ||
                s2.equals("softkey 1") || s2.startsWith("left soft"))
                return -6;
            if(s2.equals("soft2") || s2.equals("soft 2") ||
                s2.equals("Softkey 4") || s2.equals("softkey 4 ") ||
                s2.equals("soft_2") || s2.equals("softkey 4") ||
                s2.startsWith("right soft"))
                return -7;
            if(j == Canvas.FIRE && i!=Canvas.KEY_NUM5){
                return -8;
            }else if(j == Canvas.LEFT && i!=Canvas.KEY_NUM4){
                return 2;
            }else if(j == Canvas.RIGHT && i!=Canvas.KEY_NUM6){
                return 5;
            }else if(j == Canvas.UP && i!=Canvas.KEY_NUM2){
                return 1;
            }else if(j == Canvas.DOWN && i!=Canvas.KEY_NUM8){
                return 6;
            }
            if(s2.equals("clear") || s2.equals("back") || s2.equals("send"))
                return 7;
            if(s2.equals("select") || s2.equals("ok") || s2.equals("start") ||
                s2.equals("Центральная клавиша") || s2.equals("Клавиша выбора") ||
                s2.equals("клавиша выбора") || s2.equals("Центральная клавиша выбора") ||
                s2.equals("Центральная клавиша выбора ") ||
                s2.equals("центральная клавиша выбора") ||
                s2.equals("центральная клавиша") ||
                s2.equals("центральная клавиша выбора") ||
                s2.equals("fire") || s2.equals("огонь!") || s2.equals("Огонь!") ||
                s2.equals("navi-center"))
                return -8;
            if(s2.equals("up") || s2.equals("navi-up") || s2.equals("up arrow") ||
                s2.equals("вверх") || s2.equals("Вверх") )
                return 1;
            if(s2.equals("down") || s2.equals("navi-down") || s2.equals("down arrow") ||
                s2.equals("вниз") || s2.equals("Вниз") )
                return 6;
            if(s2.equals("left") || s2.equals("navi-left") || s2.equals("влево") ||
                s2.equals("left arrow") || s2.equals("sideup"))
                return 2;
            if(s2.equals("right") || s2.equals("navi-right") || s2.equals("вправо") ||
                s2.equals("right arrow") || s2.equals("sidedown"))
                return 5;
            if(s2.equals("q") || s2.equals("w"))
                return -6;
            if(s2.equals("o") || s2.equals("p"))
                return -7;
            if(s2.equals("escape"))
                return 7;
            }
            if(i == -6 || i == -21 || i == 21 || i == 105 || i == -202 || i == 112 || i == 57345)
                return -6;
            if(i == -7 || i == -22 || i == 22 || i == 106 || i == -203 || i == 113 || i == 57346)
                return -7;
            if(i == -5 || i == -10 || i == -20 || i == 20 || i == 23 || i == -14 ||
                i == -26 || i == -200 || i == 10 || i == 13)
                return 8;
            if(i == -8 || i == -11 || i == -16 || i == -19 || i == -204)
                return -7;
            switch(i){
                case 35: // ‘#’
                case 42: // ‘*’
                case 48: // ‘0?
                case 49: // ‘1?
                case 50: // ‘2?
                case 51: // ‘3?
                case 52: // ‘4?
                case 53: // ‘5?
                case 54: // ‘6?
                case 55: // ‘7?
                case 56: // ‘8?
                case 57: // ‘9?
                return i;
            }
            if(j != 0) return j;
            else return i;
    }
}
