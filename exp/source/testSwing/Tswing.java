/*
 * �������� 2005-1-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testSwing;

/**
 * @author ��һ��
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import javax.swing.*;
import java.awt.*;

public class Tswing extends JFrame{

	public Tswing()
	{
		setTitle("test");
		setSize(12,23);
		GridBagConstraints a=new GridBagConstraints();
		a.weightx=0;
		a.weighty=100;
		a.fill=a.NONE;
		a.anchor=a.EAST;
		a.gridx=0;
		a.gridy=0;
		a.gridheight=1;
		a.gridwidth=1;
		add(new JLabel("aa"),a);
	}
	public static void main(String[] args) {
		JFrame j=new Tswing();
		j.show();
	}
}
