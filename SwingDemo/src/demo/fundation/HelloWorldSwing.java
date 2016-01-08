/**
 * 
 * 项目名称：SwingDemo
 * 制作时间：Oct 11, 20099:53:48 AM
 * 包名：demo.fundation
 * 文件名：HelloWorldSwing.java
 * 制作者：Administrator
 * @version 1.0
 */
package demo.fundation;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Administrator
 * @version 1.0
 */
public class HelloWorldSwing {

	private static void createAndShowGUI()
	{
		String lookAndFeel = null;
		lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("hello world");
		
		
		ButtonActionListener bal = new ButtonActionListener();
		
		JButton button = new JButton("I`m a Swing button!");
		button.setMnemonic('i');
		button.addActionListener(bal);
		
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.add(button);
		panel.add(label);
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * 功能描述
	 * @param args
	 * Oct 11, 2009 9:53:49 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		}
		);
	}

}
