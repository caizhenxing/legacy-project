/*
 * 创建日期 2004-12-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

import java.util.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testMemory {

	/**
	 * 
	 */
	public testMemory() {
		super();
		// TODO 自动生成构造函数存根
	}

	public static void main(String[] args) {
		HashMap hm=new HashMap(1000000);
		try
		{
		
		for(long i=0;i<422741;i++)
		hm.put(Long.toString(i),Long.toBinaryString(i));
		}catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			System.out.println(hm.size());
		}
		
	}
}
