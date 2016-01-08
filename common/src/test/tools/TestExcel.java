/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 25, 20077:19:46 PM
 * �ļ�����TestExcel.java
 * �����ߣ�zhaoyf
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
	 * ��������
	 * @param args
	 * Oct 25, 2007 7:19:48 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
		//����Workbook����, ֻ��Workbook����
			//ֱ�Ӵӱ����ļ�����Workbook
		//������������Workbook
			File sourcefile=new File("e:\\�ĵ�.xls");
		    InputStream is = new FileInputStream(sourcefile);
		    jxl.Workbook rwb = Workbook.getWorkbook(is);
		  //��ȡ��һ��Sheet��
		    Sheet rs = rwb.getSheet(0);
		  //��ȡ��һ�У���һ�е�ֵ
		    Cell c00 = rs.getCell(0, 0);
		    String strc00 = c00.getContents();

		    //��ȡ��һ�У��ڶ��е�ֵ
		    Cell c10 = rs.getCell(1, 0);
		    String strc10 = c10.getContents();

		    //��ȡ�ڶ��У��ڶ��е�ֵ
		    Cell c11 = rs.getCell(1, 1);
		    String strc11 = c11.getContents();

		    System.out.println("Cell(0, 0)" + " value : " + strc00 + "; type : " + c00.getType());
		    System.out.println("Cell(1, 0)" + " value : " + strc10 + "; type : " + c10.getType());
		    System.out.println("Cell(1, 1)" + " value : " + strc11 + "; type : " + c11.getType());
		  //�������ʱ���رն����ͷ�ռ�õ��ڴ�ռ�
		    rwb.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
