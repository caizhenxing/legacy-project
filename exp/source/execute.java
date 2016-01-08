/*
 * 创建日期 2004-7-1
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import base.*;
import inheriance.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class execute {
	public static void  printa(parent p)
	{
		System.out.println(p.getI());
		System.out.println(p.getS());
	}
	public static void main(String args[]){
		parent p=new parent();
		childern c=new childern();
		parent cp=new childern();
		cp.addI(15);
		p.setS("bbbb");
		p.addI(10);
		c.setI(5);
		c.setS("aaaa");
		cp=c;		
		System.out.println(p.getI());
		System.out.println(p.getS());
		System.out.println(((parent)c).getI());
		System.out.println(((parent)c).getS());
		System.out.println("cp:"+cp.getS());
		System.out.println(cp.getI());
		printa(p);
		printa(c);
		
	}

}
