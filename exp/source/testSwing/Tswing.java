/*
 * 创建日期 2005-1-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testSwing;

/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
