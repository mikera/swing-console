# swing-console

A Swing text console component, useful to provide functionality similar to a terminal in Java.

Provides a simple Java-based API for controlling the console.

swing-console is deigned for situations where you want to emulate the look and feel of a 
traditional console, but want to do so in with a flexible, lightweight GUI component.

Example use cases:

 - Embedding as a visual feature for ASCII graphics in a larger Swing GUI
 - Creating "roguelike" games, e.g. swing-console is used in [Alchemy](https://github.com/mikera/alchemy)

### Key features

 - Background and foreground with arbitrary colours
 - Supports any monospaced font
 - Support for a blinking cursor
 - Can support multiple fonts in the same console
 - Ability to capture System.out


### Basic usage

   	JConsole jc=new JConsole(80,25);
    jc.setCursorVisible(true);
    jc.setCursorBlink(true);
    jc.write("Hello World,\n");
    jc.write("Colourful World\n",Color.BLACK,Color.MAGENTA);
