package testIO.intro;
import java.applet.*;
import java.io.*;
import java.awt.*;

public class RedirectApplet extends Applet {

  TextField result = new TextField();

  public void init() {
  
    this.setLayout(new BorderLayout());
    this.add("Center", result);
  
  }

  public void start() {
  
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(out);
    try {
      System.setOut(ps);
      System.out.println("OK");
      result.setText("OK");
    }
    catch (Exception e) {
      result.setText(e.toString());
    }
  
  }


}