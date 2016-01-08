package testSwing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicArrowButton;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Applet1b extends JApplet {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	JButton b1=new JButton("submit"),
			b2=new JButton("clear");
	BasicArrowButton up=new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton down=new BasicArrowButton(BasicArrowButton.SOUTH);
	BasicArrowButton left=new BasicArrowButton(BasicArrowButton.WEST);
	BasicArrowButton right=new BasicArrowButton(BasicArrowButton.EAST);
	
	JTextField txt =new JTextField(10);
	private JScrollPane jScrollPane1;
	JTextArea ta =new JTextArea(20,40);
	JTextPane tp=new JTextPane();
	ActionListener al=new ActionListener()
	{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name=txt.getText();
			ta.append(name);
			txt.setText("");
		}
	};
	ActionListener al1=new ActionListener()
	{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			ta.setText("");
		}
	};
	public void init()
	{
		b1.addActionListener(al);
		b2.addActionListener(al1);
		
		Box jpv=Box.createVerticalBox();
		//jpv.setLayout(new BoxLayout(jpv,BoxLayout.Y_AXIS));
		jpv.add(txt);
		jpv.add(b1);
		jpv.add(Box.createVerticalStrut(10));
		jpv.add(b2);
		
		Box jph=Box.createHorizontalBox();
		//jph.setLayout(new BoxLayout(jph,BoxLayout.Y_AXIS));
		
		jph.add(new JScrollPane(ta));
		Container cp=getContentPane();
		cp.add(BorderLayout.NORTH,jph);
		cp.add(BorderLayout.SOUTH,jpv);
		cp.add(new JScrollPane(tp));
		cp.add(up);
		cp.add(down);
		cp.add(left);
		cp.add(right);
	}
	
	private void initGUI() {
		try {
			{
				BoxLayout thisLayout = new BoxLayout(this.getContentPane(), javax.swing.BoxLayout.X_AXIS);
				this.getContentPane().setLayout(thisLayout);
				this.setSize(435, 135);
				{
					jScrollPane1 = new JScrollPane();
					getContentPane().add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(545, 135));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
