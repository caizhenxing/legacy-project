/**
 * 	@(#)TestExcel.java   Nov 23, 2006 1:14:36 PM
 *	 。 
 *	 
 */
package et.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;
import jxl.Workbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author zhang
 * @version Nov 23, 2006
 * @see
 */
public class TestExcel extends TestCase {
	
	public void testCreate() throws IOException, RowsExceededException, WriteException{
		  FileOutputStream os = new FileOutputStream(new File("c:/aa.xls"));
		  jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		  jxl.write.WritableSheet ws = wwb.createSheet("TestSheet1", 0);
		  jxl.write.Label labelC = new jxl.write.Label(0, 0, "我爱中国");
		  ws.addCell(labelC);
		  //jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL,20, WritableFont.BOLD, false,
		  //UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.GREEN);
		  //jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
		 // wcfFC.setBackground(jxl.format.Colour.RED);
		  labelC = new jxl.write.Label(6, 0, "中国爱我");
		  ws.addCell(labelC);
		  wwb.write();
		  wwb.close();
	}

}
