/**
 * 	@(#)LoadService.java   Feb 8, 2007 3:29:50 PM
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.common;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.upload.FormFile;

/**
 * @author zhang
 * @version Feb 8, 2007
 * @see
 */
public interface LoadService {

	public static int PICTURE=1;
	public static int MOVIE=2;
	public static int FLASH=3;
	public static int OTHER=0;

	public String saveToFtp(FormFile file,
							String newName,
							String ftpUrl,
							String ftpUser,
							String ftpPW,
							String path);
	public String saveToLocal(FormFile file,String newName,String path);
	
	
	
	public String getFileName();
	
	public String setFileFormat(HashMap fileFormat);
	
	public void setCount(int fileType, int count);
	
	public boolean isLegality(List uploadList,FormFile file);
	
	public String createNewName();
	
	public void setUrl(String url);
	

	
	public String saveToClasspath(FormFile file,String newName,String path);
}
