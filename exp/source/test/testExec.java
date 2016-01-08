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
public class testExec {
	//String bak="exp jc/jc@ORCL_10.5.31.108 owner=jc  buffer=65536 file=";
	//String name="e:/bak.dmp";
	public static void main(String[] args) {
		testExec t=new testExec();
		try {
			
				  Process process = Runtime.getRuntime().exec("cmd.exe start  http://www.yahoo.com");  //登录网站
				  //Process process1 = Runtime.getRuntime().exec("cmd.exe  /c  start  ping 10.144.98.100");  //调用Ping命令
				  //Process p=Runtime.getRuntime().exec("cmd.exe /c "+t.bak+t.name);
				}catch (Exception  e)
				{
					e.printStackTrace();
					} 
	}
}
