package mikera.gui.demo;

import java.awt.Color;

import javax.swing.JFrame;

import mikera.gui.JConsole;
import mikera.gui.Tools;

public class ConsoleApp {

    public static void main(String[] args) {	
    	JConsole jc=new JConsole(80,25);
    	jc.setCursorVisible(true);
    	jc.setCursorBlink(true);
    	jc.write("Hello World\n");
    	jc.write("Hello World\n",Color.BLACK,Color.MAGENTA);
    	jc.write("Hello World\n",Color.GREEN,Color.BLACK);
    	//System.out.println("Normal output");
    	jc.captureStdOut();
    	
    	
    	// brown box
    	jc.fillArea(' ', Color.WHITE, new Color(100,70,30), 20, 20, 3, 3);
    	
    	jc.setCursorPos(0, 0);

    	JFrame f=Tools.showFillingComponent(jc);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    }

}
