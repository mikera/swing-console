package mikera.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.JComponent;

import mikera.gui.Tools;


/**
 * Class implementing a Swing-based text console
 * 
 * @author Mike Anderson
 *
 */
public class JConsole extends JComponent {
	private static final long serialVersionUID = 3571518591759968333L;
	
	private static final Color DEFAULT_FOREGROUND=Color.LIGHT_GRAY;
	private static final Color DEFAULT_BACKGROUND=Color.BLACK;
	private static final Font DEFAULT_FONT=new Font("Courier New", Font.PLAIN, 20);
	
	private int size;
	private int rows;
	private int columns;
	private Color[] background;
	private Color[] foreground;
	private Font[] font;
	private char[] text;
	private int fontWidth;
	private int fontHeight;
	private int fontYOffset;
	
	private int curPosition=0;
	private Font currentFont=DEFAULT_FONT;
	private Color curForeground=DEFAULT_FOREGROUND;
	private Color curBackground=DEFAULT_BACKGROUND;

	public JConsole(int columns, int rows) {
		init(columns,rows);
	}
	
	public  void setRows(int rows) {
		init(columns,rows);
	}
	
	public void setFont(Font f) {
		currentFont=f;
	}

	public int getRows() {
		return rows;
	}

	public void setColumns(int columns) {
		init(columns,rows);
	}

	public int getColumns() {
		return columns;
	}
	
	public void init(int columns, int rows) {
		size=rows*columns;
		this.rows=rows;
		this.columns=columns;
		
		text=new char[size];
		background=new Color[size];
		foreground=new Color[size];
		font=new Font[size];
		Arrays.fill(background,DEFAULT_BACKGROUND);
		Arrays.fill(foreground,DEFAULT_FOREGROUND);
		Arrays.fill(font,DEFAULT_FONT);
		Arrays.fill(text,' ');
		
		currentFont=DEFAULT_FONT;
		FontRenderContext fontRenderContext=new FontRenderContext(DEFAULT_FONT.getTransform(),false,false);
	    Rectangle2D charBounds = DEFAULT_FONT.getStringBounds("X", fontRenderContext);
	    fontWidth=(int)charBounds.getWidth();
	    fontHeight=(int)charBounds.getHeight();
	    fontYOffset=-(int)charBounds.getMinY();
	    
	    setPreferredSize(new Dimension(columns*fontWidth, rows*fontHeight));
	}
	
	@Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Rectangle r=g.getClipBounds();
        
        //AffineTransform textTransform=new AffineTransform();
        //textTransform.scale(fontWidth, fontHeight);
        //g.setTransform(textTransform);
		
        int x1=(int)(r.getMinX()/fontWidth);
        int x2=(int)(r.getMaxX()/fontWidth)+1;
        int y1=(int)(r.getMinY()/fontWidth);
        int y2=(int)(r.getMaxY()/fontWidth)+1;
        
        for(int j = Math.max(0,y1); j < Math.min(y2,rows); j++) {
    		int offset=j*columns;
    		int start=Math.max(x1, 0);
    		int end=Math.min(x2,columns);
        	while (start<end) {
        		Color nfg=foreground[offset+start];
        		Color nbg=background[offset+start];
        		Font nf=font[offset+start];
        		
        		// detect run
        		int i=start+1;
        		while ((i<end)
        				&&(nfg==foreground[offset+i])
        				&&(nbg==background[offset+i])
        				&&(nf==font[offset+i])) {
        			i++;
        		}

        		// set font
                g.setFont(nf);

     			// draw background
    			g.setBackground(nbg);
    			g.clearRect(fontWidth*start, j*fontHeight, fontWidth*(i-start), fontHeight);
    			
    			// draw chars up to this point
    			g.setColor(nfg);
    			g.drawChars(text,offset+start,i-start,start*fontWidth,j*fontHeight+fontYOffset);
        		
        		start=i;
        	}
        }
    }
	
	public void setCursorPos(int x, int y) {
		if ((x<0)||(x>=columns)) throw new Error("Invalid X cursor position: "+x);
		if ((y<0)||(y>=rows)) throw new Error("Invalid Y cursor position: "+y);
		curPosition=y*columns+x;
	}
	
	public int getCursorX() {
		return curPosition%columns;
	}
	
	public int getCursorY() {
		return curPosition/columns;
	}

	public void setForeground(Color c) {
		curForeground=c;
	}
	
	public void setBackground(Color c) {
		curBackground=c;
	}
	
	public Color getForeground() {
		return curForeground;
	}
	
	public Color getBackground() {
		return curBackground;
	}
	

	
	
	public void captureStdOut() {
		PipedInputStream pipedInput=new PipedInputStream();
		PipedOutputStream pipedOutput;
		try {
			pipedOutput = new PipedOutputStream(pipedInput);
		} catch (IOException e) {
			throw new Error(e);
		}
		PrintStream printStream=new PrintStream(pipedOutput);
		InputStreamReader inputStreamReader=new InputStreamReader(pipedInput);
		
		System.setOut(printStream);
		
		new Thread(new OutputHandler(inputStreamReader)).start();
	}
	
	private class OutputHandler implements Runnable {
		final InputStreamReader reader;
		
		public OutputHandler(InputStreamReader inputStreamReader) {
			reader=inputStreamReader;
		}
		
		public void run() {
			while (true) {
				try {
					char c;
					c = (char) reader.read();
					write(c);
					repaint();
				} catch (Exception e) {
					// return, presumably because program ended.
					return;
				}
			}
		}	
	}

	public void write(char c) {
		int pos=curPosition;
		pos=writeChar(c,pos);
		curPosition=pos;	
	}
	
	/**
	 * Write a single character and update cursor position
	 */
	private int writeChar(char c, int pos) {
		switch (c) {
			case '\n':
				pos=((pos+columns)/columns)*columns;
				break;
			default:
				text[pos]=c;
				foreground[pos]=curForeground;
				background[pos]=curBackground;
				font[pos]=currentFont;
				pos++;
		}
		if (pos>=size) pos=0;		
		return pos;
	}
	
	public void write(String string, Color foreGround, Color backGround) {
		setForeground(foreGround);
		setBackground(backGround);
		write(string);
	}
	
	public void fillArea(char c, Color fg, Color bg, int x, int y, int w, int h) {
		for (int q=Math.max(0,y); q<Math.min(y+h, rows); q++) {
			for (int p=Math.max(0,x); p<Math.min(x+w, columns); p++) {
				int offset=p+q*columns;
				text[offset]=c;
				foreground[offset]=fg;
				background[offset]=bg;
			}
		}
	}

	public void write(String string) {
		int pos=curPosition;
		for (int i=0; i<string.length(); i++) {
			char c=string.charAt(i);
			pos=writeChar(c,pos);
		}
		curPosition=pos;
	}
	
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
