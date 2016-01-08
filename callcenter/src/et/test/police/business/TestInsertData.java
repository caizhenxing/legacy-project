/**
 * 	@(#)TestInsertData.java   Oct 20, 2006 11:17:47 AM
 *	 。 
 *	 
 */
package et.test.police.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @author zhang
 * @version Oct 20, 2006
 * @see
 */
public class TestInsertData {
	
	public static void main(String[] args) throws Exception {
		TestInsertData.writeExcel();
	}

	
	public static void writeExcel() throws Exception {
		File tempFile=new File("C:/jiyuan.XLS");
//		创建只读的Excel工作薄的对象
	    Workbook wb = Workbook.getWorkbook(tempFile); 
//		创建可写入的Excel工作薄对象
	    InputStream is = new FileInputStream(tempFile);
	    jxl.Workbook rwb = Workbook.getWorkbook(is);

	    Sheet rs = rwb.getSheet(0);
	    for (int i = 0; i < 16328; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println("j "+j+" i "+i);
			}
		}
//	    Cell c00 = rs.getCell(0, 0);
//	    String strc00 = c00.getContents();
//	    //获取第一行，第二列的值
//	    Cell c10 = rs.getCell(1, 0);
//	    String strc10 = c10.getContents();
//	    //获取第二行，第二列的值
//	    Cell c11 = rs.getCell(1, 1);
//	    String strc11 = c11.getContents();

	    //System.out.println("Cell(0, 0)" + " value : " + strc00 + "; type : " + c00.getType());
	    //System.out.println("Cell(1, 0)" + " value : " + strc10 + "; type : " + c10.getType());
	    //System.out.println("Cell(1, 1)" + " value : " + strc11 + "; type : " + c11.getType());
	}
}
