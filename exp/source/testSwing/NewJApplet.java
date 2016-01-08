package testSwing;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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
public class NewJApplet extends javax.swing.JApplet {

	/**
	* Auto-generated main method to display this 
	* JApplet inside a new JFrame.
	*/
	JButton b1=new JButton("submit");
	private JFormattedTextField jFormattedTextField1;
	private JCheckBox jCheckBox1;
	private JTextPane jTextPane2;
	private JTextPane jTextPane1;
	private JFormattedTextField jFormattedTextField2;
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		NewJApplet inst = new NewJApplet();
		frame.getContentPane().add(inst);
		((JComponent)frame.getContentPane()).setPreferredSize(inst.getSize());
		frame.pack();
		frame.setVisible(true);
	}
	
	public NewJApplet() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			this.setSize(448, 322);
			{
				jCheckBox1 = new JCheckBox();
				getContentPane().add(
					jCheckBox1,
					new AnchorConstraint(
						740,
						282,
						827,
						141,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL));
				jCheckBox1.setText("\u7528\u6237");
				jCheckBox1.setPreferredSize(new java.awt.Dimension(63, 28));
			}
			{
				jTextPane2 = new JTextPane();
				getContentPane().add(jTextPane2, new AnchorConstraint(110, 1001, 718, 813, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jTextPane2.setText("jTextPane2");
				jTextPane2.setPreferredSize(new java.awt.Dimension(84, 196));
			}
			{
				jTextPane1 = new JTextPane();
				getContentPane().add(jTextPane1, new AnchorConstraint(110, 172, 718, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jTextPane1.setText("jTextPane1");
				jTextPane1.setPreferredSize(new java.awt.Dimension(77, 196));
			}
			{
				jFormattedTextField2 = new JFormattedTextField();
				getContentPane().add(jFormattedTextField2, new AnchorConstraint(610, 813, 718, 172, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jFormattedTextField2.setText("jFormattedTextField2");
				jFormattedTextField2.setPreferredSize(new java.awt.Dimension(287, 35));
			}
			{
				jFormattedTextField1 = new JFormattedTextField();
				getContentPane().add(jFormattedTextField1, new AnchorConstraint(110, 813, 610, 172, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				jFormattedTextField1.setText("jFormattedTextField1");
				jFormattedTextField1.setPreferredSize(new java.awt.Dimension(287, 161));
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
