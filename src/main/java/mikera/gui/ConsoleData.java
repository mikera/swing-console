package mikera.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;

/**
 * Class used for storing console data
 * 
 * @author Mike
 */
public final class ConsoleData {
	public int size;
	public int rows;
	public int columns;
	public Color[] background;
	public Color[] foreground;
	public Font[] font;
	public char[] text;

	ConsoleData() {
	}

	void init(int columns, int rows) {
		size=rows*columns;
		this.rows=rows;
		this.columns=columns;
		
		text=new char[size];
		background=new Color[size];
		foreground=new Color[size];
		font=new Font[size];
	}
	
	/**
	 * Sets a single chatacter position
	 */
	public void setDataAt(int column, int row, char c, Color fg, Color bg, Font f) {
		int pos=column+row*columns;
		text[pos]=c;
		foreground[pos]=fg;
		background[pos]=bg;
		font[pos]=f;
	}
	
	public char getCharAt(int column, int row) {
		int offset=column+row*columns;
		return text[offset];
	}
	
	public Color getForegroundAt(int column, int row) {
		int offset=column+row*columns;
		return foreground[offset];
	}
	
	public Color getBackgroundAt(int column, int row) {
		int offset=column+row*columns;
		return background[offset];
	}
	
	public Font getFontAt(int column, int row) {
		int offset=column+row*columns;
		return font[offset];
	}

	
	public void fillArea(char c, Color fg, Color bg, Font f, int column, int row, int width, int height) {
		for (int q=Math.max(0,row); q<Math.min(row+height, rows); q++) {
			for (int p=Math.max(0,column); p<Math.min(column+width, columns); p++) {
				int offset=p+q*columns;
				text[offset]=c;
				foreground[offset]=fg;
				background[offset]=bg;
				font[offset]=f;
			}
		}
	}
}