/*
 * 创建日期 2004-12-24
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package jc.xtgl;

import java.util.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class TimeTools {

	/**
	 * 
	 */
	public TimeTools() {
		super();
		// TODO 自动生成构造函数存根
	}
	public static boolean between(int begin, int end, int date)
	{
		if(begin<0||end<0||date<0)
		return false;
		if(begin>31||end>31||date>31)
		return false;
		if(end<begin)
		{
			return (date<end);
		}
		return(date>=begin&&date<end);
	}
	public static void main(String[] args) {
		TimeTools tt=new TimeTools();
		System.out.print(tt.between(12,12,12));
	}
}
