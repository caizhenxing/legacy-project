package et.common.tableExl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ImgExcel {
	public void createExcel(String basePath,String imgName, OutputStream out)
	{
		String imgPath = basePath+"/temp/"+imgName;
		if(imgPath!=null&&!"".equals(imgPath))
		{
			WritableWorkbook writeWorkbook;
			try {
				writeWorkbook = Workbook.createWorkbook(out);
				WritableSheet sheetWrite=writeWorkbook.createSheet("Í³¼ÆÍ¼",0);
				File imgFile = new File(imgPath);
				 
				
				WritableImage img = new WritableImage(0, 0, 11, 25, imgFile);   
	
				sheetWrite.addImage(img); 
				writeWorkbook.write();
				try {
					writeWorkbook.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
}
