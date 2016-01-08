package ocelot.common.load;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.jspsmart.upload.File;



 /**
 * @author ’‘“ª∑«
 * @version 2007-1-15
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
