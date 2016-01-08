/**
 * 
 */
package et.test.jxl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author Administrator
 * 
 */
public class TestJXL {

	public static void main(String[] args) {
		try {
			String url = "D:\\gongneng.xls";
			Workbook wb = Workbook.getWorkbook(new File(url));
			ByteArrayOutputStream targetFile = new ByteArrayOutputStream();
			WritableWorkbook wwb = Workbook.createWorkbook(targetFile, wb);
			WritableSheet wws = wwb.getSheet("Sheet1");
			
			Label tmpL = (Label)wws.getWritableCell(0, 0);
			
			Label C1 = new Label(1, 0, "单元格内容");
			
			System.out.println(tmpL.getContents()); 
			tmpL.copyTo(0, 5);
			
			
			wws.addCell(C1);
			wws.addCell(tmpL.copyTo(3, 3));
			wws.addCell(C1.copyTo(2,0));
			wwb.write();
			wwb.close();
			wb.close();
			FileOutputStream fos = new FileOutputStream("D:/test1.xls");
			targetFile.writeTo(fos);
			targetFile.close();
			System.out.println("盒封面.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
