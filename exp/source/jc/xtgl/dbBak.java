/*
 * 创建日期 2004-12-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package jc.xtgl;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class dbBak {
	String bak="exp jc/jc@ORCL_10.5.31.108 owner=jc  buffer=65536 file=";
	String filePath="e:\\db_bak\\";	String fileType=".dmp";
	
		public void exeBak(String filename)
		{
			
			try 
			{
				Process p=Runtime.getRuntime().exec("cmd.exe /c "+this.bak+this.filePath+filename+this.fileType);
			}catch (Exception  e)
			{
				e.printStackTrace();
			} 
		}
		public static void main(String[] args)
		{
			dbBak d=new dbBak();
			d.exeBak("aa");
		}
}
