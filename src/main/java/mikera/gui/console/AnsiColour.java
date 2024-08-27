package mikera.gui.console;

import java.awt.Color;

public class AnsiColour {

	private static final Color[] cols=new Color[256];
	
	public static Color get(long value) {
		if ((value<0)||(value>=256)) throw new IllegalArgumentException("Ansi colour out of range 0-255");
		int i=(int)value;
		
		Color c=cols[i];
		if (c!=null) return c;
		
		if (i<8) {
			// low intensity primary colours
			int r=(i&1)==0?0:128;
			int g=(i&2)==0?0:128;
			int b=(i&4)==0?0:128;
			c=new Color(r,g,b);
		} else if (i<16) {
			// high intensity colours
			if (i==8) {
				c= Color.DARK_GRAY; // high intensity black, I guess?
			} else {
				int r=(i&1)==0?0:255;
				int g=(i&2)==0?0:255;
				int b=(i&4)==0?0:255;
				c=new Color(r,g,b);
			}
		} else if (i<(16+216)){
			// 6*6*6 colour cube
			int z=i-16;
			int r=51*(z/36);
			int g=51*((z%36)/6);
			int b=51*(z%6);
			c=new Color(r,g,b);
		} else {
			int z = (i - 232) * 10 + 8;
			c=new Color(z,z,z);
		}
			
		cols[i]=c;
		return c;
	}
}
