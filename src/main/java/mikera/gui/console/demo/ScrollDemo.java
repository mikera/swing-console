package mikera.gui.console.demo;

import java.awt.Color;

import javax.swing.JFrame;

import mikera.gui.Frames;
import mikera.gui.console.AnsiColour;
import mikera.gui.console.JConsole;

public class ScrollDemo {

    public static void main(String[] args) throws InterruptedException {	
    	final int w=80;
    	final int h=24;
    	JConsole jc=new JConsole(w,h);
    	jc.setCursorVisible(false);
    	jc.setCursorBlink(false);
    	
    	jc.setCursorPos(0, 0);

    	JFrame f=Frames.display(jc,"Scrolling JConsole");

    	long i=0;
    	while (f.isVisible()) {
    		Thread.sleep(10);
    		jc.scroll(1, 0); // scroll to the right
    		
    		for (int y=0; y<h; y++) {
    			if (((i>>y)&1)!=0) {
    				jc.setChar(' ', Color.BLACK, AnsiColour.get(i%256), w-1, y);
    			}
    		}
    		i++;
    	}
    }

}
