/*
 * @(#)CommonExcel.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.common.tableExl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <p>将table里的内容转成Excel导出</p>
 * 
 * @version 2008-07-08
 * @author wangwenquan
 */
public class CommonExcel {
	//解table生的字
	private String tblCells;
	private int cellNum = 0;
	/**
	 * tblCells的第子就title集合
	 * @param tblCells
	 * @return List<String>
	 */
	private List<String> getTitleList(String tblCells)
	{
		List<String> titleList = new ArrayList<String>();
		if(tblCells!=null&&!"".equals(tblCells.trim()))
		{
			int index = tblCells.indexOf("|");
			if(index>0)
			{
				tblCells = tblCells.substring(0,index);
			}
			String[] titles = tblCells.split("@");
			for(int i=0; i<titles.length; i++)
			{
				titleList.add(titles[i]);
			}
			cellNum = titles.length;
		}
		return titleList;
	}
	/**
	 * tblCells的第子就title集合
	 * @param tblCells
	 * @return
	 */
	private List<List> getContentList(String tblCells)
	{
		List<List> contentList = new ArrayList<List>();
		if(tblCells!=null&&!"".equals(tblCells.trim()))
		{
			int firstIndex = tblCells.indexOf("|");
			if(firstIndex!=-1&&firstIndex!=(tblCells.length()-1))
			{
				tblCells = tblCells.substring(firstIndex+1);
				//System.out.println(tblCells);
				String[] rows = tblCells.split("\\|");
				for(int i=0; i<rows.length; i++)
				{
					String row = rows[i];
					//System.out.println(rows[i]+"rows is :"+row);
					String[] cells = row.split("@");
					List<String> colList = new ArrayList<String>();
					for(int j=0; j<cells.length; j++)
					{
						colList.add(cells[j]);
					}
					int difference = cellNum - cells.length;
					if(difference>0)
					{
						for(int j=0; j<difference; j++)
						{
							colList.add("");
						}
					}
					contentList.add(colList);
				}
			}
		}
		return contentList;
	}
	
	public void createExcel(OutputStream fileOut, String sheetName,String tblCells) throws IOException
	{
		List<String> titleList = this.getTitleList(tblCells); 
		List<List> contentList = this.getContentList(tblCells);
		HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
		try {
				HSSFSheet sheet = wb.createSheet();
				wb.setSheetName(0, sheetName,HSSFWorkbook.ENCODING_UTF_16);
				HSSFRow row0 = sheet.createRow(0);//建立新行 ENCODING_COMPRESSED_UNICODE
				//System.out.println(titleList.size()+"tSize");
				if(titleList.size()>0)
				{
					for(int i=0; i<titleList.size(); i++)
					{
						HSSFCell cell00 = row0.createCell((short)i);//建立新cell
						cell00.setCellType(HSSFCell.CELL_TYPE_STRING );
						cell00.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell00.setCellValue(titleList.get(i));
					}
				}
				
				int rowcount = 1;        
				for(int i=0; i<contentList.size(); i++)
				{
					List<String> cols = contentList.get(i);
					for(int j=0; j<cols.size(); j++)
					{
						HSSFRow row = sheet.createRow(rowcount);//建立新行
						HSSFCell celli0 = row.createCell((short)j);//建立新cell
						//HSSFRichTextString hssfs=new HSSFRichTextString("aa"j);
						celli0.setCellType(HSSFCell.CELL_TYPE_STRING );
						celli0.setEncoding(HSSFCell.ENCODING_UTF_16);
						celli0.setCellValue(cols.get(j));
					}
					rowcount++;
					
					
					
				}
		}
		catch(Exception e)
		{
			//System.out.println(new java.util.Date()+"在CreateEmpExcel里执行查询出现了非SQL异常 "+e.getMessage());
			e.printStackTrace();
		}
		wb.write(fileOut);
		fileOut.close();
		//System.out.println("fileOut is close!");
	}
	public void createExcel(OutputStream fileOut, String sheetName,List<List<String>> cells) throws IOException
	{
		HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
		try {
				HSSFSheet sheet = wb.createSheet();
				wb.setSheetName(0, sheetName,HSSFWorkbook.ENCODING_UTF_16);
				HSSFRow row0 = sheet.createRow(0);//建立新行 ENCODING_COMPRESSED_UNICODE
				//System.out.println(titleList.size()+"tSize");
				List<String> titles = cells.get(0);
				if(cells.size()>0)
				{
					for(int i=0; i<titles.size(); i++)
					{
						HSSFCell cell00 = row0.createCell((short)i);//建立新cell
						cell00.setCellType(HSSFCell.CELL_TYPE_STRING );
						cell00.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell00.setCellValue(titles.get(i));
					}
				}
				
				int rowcount = 1;        
				for(int i=1; i<cells.size(); i++)
				{
					List<String> curCol = cells.get(i);
					for(int j=0; j<curCol.size(); j++)
					{
						HSSFRow row = sheet.createRow(rowcount);//建立新行
						HSSFCell celli0 = row.createCell((short)j);//建立新cell
						//HSSFRichTextString hssfs=new HSSFRichTextString("aa"j);
						celli0.setCellType(HSSFCell.CELL_TYPE_STRING );
						celli0.setEncoding(HSSFCell.ENCODING_UTF_16);
						celli0.setCellValue(curCol.get(j));
					}
					rowcount++;
					
					
					
				}
		}
		catch(Exception e)
		{
			//System.out.println(new java.util.Date()+"在CreateEmpExcel里执行查询出现了非SQL异常 "+e.getMessage());
			e.printStackTrace();
		}
		wb.write(fileOut);
		fileOut.close();
		//System.out.println("fileOut is close!");
	}
}
