/*
 * 创建日期 2004-12-23
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Arrays;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testArray {

	/**
	 * 
	 */
	public testArray() {
		super();
		// TODO 自动生成构造函数存根
	}
	
	public static void main(String[] args) {
		Vector v=new Vector();
		for(int i=0;i<100;i++)
		v.add(new String(Integer.toString(i)));
		Arrays.sort(v.toArray());
		Iterator i=v.iterator();
		while(i.hasNext())
		System.out.println(i.next().toString());
	}
}
