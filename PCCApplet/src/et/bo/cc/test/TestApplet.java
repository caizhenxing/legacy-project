/*
 * TestApplet.java
 *
 * Created on 2006年10月6日, 下午2:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package et.bo.cc.test;

import javax.swing.JApplet;
import javax.swing.JFrame;

import et.bo.cc.applet.AppJApplet;

/**
 *
 * @author ddddd
 */
public class TestApplet {
    
    /** Creates a new instance of TestApplet */
    public TestApplet() {
    }
    public static void main(String[] arg0)
    {
        JApplet ajp=new AppJApplet();
        JFrame frame=new JFrame("AppJApplet");
        frame.getContentPane().add(ajp);
        frame.setSize(800,180);
        ajp.init();
        ajp.start();
        frame.setVisible(true);
        
    }
}
