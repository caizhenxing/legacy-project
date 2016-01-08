/*
 * 创建日期 2004-12-13
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testString1 {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		
		for(long i=0;i<10000000;i++)
		{
			
//			StringBuffer s=new StringBuffer("aaaaa");
//			s.append("aa");
//			s.append("bb");
//			s.append("aa");
//			s.append("aa");
			String s=new String("aaaaa");
			//String s="aaaaa";
			s+="aa";
			s+="bb";
			s+="aa";
			s+="aa";
		}
		System.out.println(System.currentTimeMillis()-start);
	}
}
