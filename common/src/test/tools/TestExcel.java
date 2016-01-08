/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 25, 20077:19:46 PM
 * 文件名：TestExcel.java
 * 制作者：zhaoyf
 * 
 */
package test.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @author zhaoyf
 *
 */
public class TestExcel {

	/**
	 * 功能描述
	 * @param args
	 * Oct 25, 2007 7:19:48 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
		//构建Workbook对象, 只读Workbook对象
			//直接从本地文件创建Workbook
		//从输入流创建Workbook
			File sourcefile=new File("e:\\文档.xls");
		    InputStream is = new FileInputStream(sourcefile);
		    jxl.Workbook rwb = Workbook.getWorkbook(is);
		  //获取第一张Sheet表
		    Sheet rs = rwb.getSheet(0);
		  //获取第一行，第一列的值
		    Cell c00 = rs.getCell(0, 0);
		    String strc00 = c00.getContents();

		    //获取第一行，第二列的值
		    Cell c10 = rs.getCell(1, 0);
		    String strc10 = c10.getContents();

		    //获取第二行，第二列的值
		    Cell c11 = rs.getCell(1, 1);
		    String strc11 = c11.getContents();

		    System.out.println("Cell(0, 0)" + " value : " + strc00 + "; type : " + c00.getType());
		    System.out.println("Cell(1, 0)" + " value : " + strc10 + "; type : " + c10.getType());
		    System.out.println("Cell(1, 1)" + " value : " + strc11 + "; type : " + c11.getType());
		  //操作完成时，关闭对象，释放占用的内存空间
		    rwb.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
