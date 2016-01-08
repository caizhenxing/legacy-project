/*
 * 创建日期 2004-7-1
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package inheriance;
import base.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class childern extends parent{
	 public childern(String s)
	{
		super(s);
	}
	 public childern()
		{
			super();
		}
	public void setI(int i)
	{
		super.i=i;
	}
	public void child()
	{
		System.out.println("dddd");
	}
	public static void main(String args[])
	{
		//parent p=new childern("dd");
		//System.out.print(p.getS());
		new childern();
	}
	private int instanceValue = 20;
    public void test() 
    {
        System.out.println("instance value is: " + instanceValue);
    }

}
