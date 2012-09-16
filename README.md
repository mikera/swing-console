# swing-console


A Swing text console component, useful to provide functionality similar to a terminal in Java.

Provides a simple Java-based API for controlling the console.

### Key features

 - Support for blinking cursor
 - Background and foreground with arbitrary colours
 - Supports any monospaced font
 - Can support multiple fonts in the same console
 - Ability to capture System.out


### Basic usage

   	JConsole jc=new JConsole(80,25);
    jc.setCursorVisible(true);
    jc.setCursorBlink(true);
    jc.write("Hello World,\n");
    jc.write("Colourful World\n",Color.BLACK,Color.MAGENTA);
