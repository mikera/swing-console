package mikera.gui.demo;

import java.awt.Color;

import mikera.gui.JConsole;
import mikera.gui.Tools;

public class ConsoleApp {

    public static void main(String[] args) {	
    	JConsole jc=new JConsole(80,25);
    	jc.write("Hello World\n");
    	jc.write("Hello World\n",Color.BLACK,Color.MAGENTA);
    	jc.write("Hello World\n",Color.GREEN,Color.BLACK);
    	//System.out.println("Normal output");
    	jc.captureStdOut();
    	jc.fillArea(' ', Color.WHITE, new Color(100,70,30), 20, 20, 3, 3);
    	
    	System.out.println("Hello StdOut");
    	Tools.showFillingComponent(jc);
    	
    }

}
