package ru.jcod.gomoku.mobile;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;


public class Conf extends Form implements CommandListener {
	static Conf instance;
	String s1[]={"Человек","Компьютер"};
	ChoiceGroup cg1=new ChoiceGroup("Противник:",ChoiceGroup.EXCLUSIVE,s1,null);
	String s2[]={"X","O"};
	ChoiceGroup cg2=new ChoiceGroup("Первый ход:",ChoiceGroup.EXCLUSIVE,s1,null);
	ChoiceGroup cg3=new ChoiceGroup("Вы играете:",ChoiceGroup.EXCLUSIVE,s2,null);

    public Conf(){
		super("Настройки");
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        instance = this;
	}
    private void jbInit() throws Exception {
        setCommandListener(this);

        addCommand(new Command("ОТМЕНА", Command.BACK, 1));
        addCommand(new Command("СОХРАНИТЬ", Command.OK, 1));
        this.append(cg1);
        this.append(cg2);
        this.append(cg3);

        try {
            RecordStore recordstore = RecordStore.openRecordStore("gomokuconfig", true);
            if (recordstore.getNumRecords()>=1){
            	String protivnik=new String(recordstore.getRecord(1));
            	if (!protivnik.equals("Человек")) cg1.setSelectedIndex(1, true);
            	else cg1.setSelectedIndex(0, true);

            	String kem_game=new String(recordstore.getRecord(2));
            	if (!kem_game.equals("Человек")) cg2.setSelectedIndex(1, true);
            	else cg2.setSelectedIndex(0, true);

            	String first=new String(recordstore.getRecord(2));
            	if (!first.equals("X")) cg3.setSelectedIndex(0, true);
            	else cg3.setSelectedIndex(1, true);

            } else{
            	cg1.setSelectedIndex(1, true);
            	cg2.setSelectedIndex(0, true);
            	cg3.setSelectedIndex(0, true);
            }
            recordstore.closeRecordStore();
        } catch (RecordStoreException rse) {
            System.out.println(rse.getMessage());
        };
    }
    public void commandAction(Command command, Displayable displayable) {
        if (command.getCommandType() == Command.BACK) {
            Display.getDisplay(Gomoku_mobile.instance).setCurrent(Mainmenu.
                    instance);
        }
        if (command.getCommandType() == Command.OK) {
            try {
            	String protivnik = cg1.getString(cg1.getSelectedIndex());
            	String kem_game = cg2.getString(cg2.getSelectedIndex());
            	String first =cg3.getString(cg3.getSelectedIndex());
                RecordStore.deleteRecordStore("gomokuconfig");
                RecordStore recordstore = RecordStore.openRecordStore("gomokuconfig", true);
                recordstore.addRecord(protivnik.getBytes(), 0, protivnik.length());
                recordstore.addRecord(kem_game.getBytes(), 0, kem_game.length());
                recordstore.addRecord(first.getBytes(), 0, first.length());
                recordstore.closeRecordStore();
            } catch (RecordStoreException rse) {
                System.out.println(rse.getMessage());
            }
            Display.getDisplay(Gomoku_mobile.instance).setCurrent(Mainmenu.instance);
        }
    }
}
