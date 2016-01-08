/**
 * 	@(#)LoadServiceImpl.java   Feb 8, 2007 3:30:19 PM
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.common.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import et.bo.common.LoadService;

/**
 * @author zhang
 * @version Feb 8, 2007
 * @see
 */
public class LoadServiceImpl implements LoadService {
	private static Log log = LogFactory.getLog(LoadServiceImpl.class);

	private HashMap fileFormat = null;

	// private FtpService ftps=null;
	private int[] Count = new int[4];

	private String url = null;

	private FormFile file = null;

	private List<String> pictureType = new ArrayList<String>();

	private List<String> movieType = new ArrayList<String>();

	private List<String> FlashType = new ArrayList<String>();

	private List<String> otherType = new ArrayList<String>();

	public LoadServiceImpl() {
		pictureType.add("jpg");
		pictureType.add("jpeg");
		pictureType.add("gif");
		pictureType.add("bmp");
		movieType.add("rm");
		movieType.add("rmvb");
		movieType.add("wmv");
		movieType.add("wma");
		movieType.add("mp3");
		movieType.add("asf");
		movieType.add("avi");
		FlashType.add("swf");
		// otherType.add("zip");
		// otherType.add("rar");
		// otherType.add("txt");
		// otherType.add("tar");
		otherType.add("txt");
		otherType.add("doc");
		otherType.add("xls");
		otherType.add("pdf");

	}

	public String saveToFtp(FormFile file, String newName, String ftpUrl,
			String ftpUser, String ftpPW, String path) {
		// TODO Auto-generated method stub
		String type = null;
		String name = file.getFileName();
		int i = name.lastIndexOf(".");
		type = name.substring(i + 1, name.length());
		if (newName == null || newName.equals(""))
			newName = name;
		else {
			if (newName.lastIndexOf(".") == -1)
				newName = newName + "." + type;
		}
		// ftps.setPassWord(ftpPW);
		// ftps.setUrl(ftpUrl);
		// ftps.setUserName(ftpUser);
		// ftps.setPath(path);
		// try {
		// ftps.uploadFile(newName,file.getInputStream());
		// } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		// log.error(this,e);
		// } catch (IOException e) {
		// TODO Auto-generated catch block
		// log.error(this,e);
		// }
		return url + newName;
	}

	public String saveToLocal(FormFile file, String newName, String path) {
		// TODO Auto-generated method stub
		//System.out.println(path);
		int ai = path.lastIndexOf("/");
		if (ai < path.length())
			path = path + "/";
		String type = null;
		String name = file.getFileName();
		int i = name.lastIndexOf(".");
		type = name.substring(i + 1, name.length());
		if (newName == null || newName.equals(""))
			newName = name;
		else {
			if (newName.lastIndexOf(".") == -1)
				newName = newName + "." + type;
		}
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdir();
			}
			f = new File(path + newName);
			if (!f.exists()) {

				f.createNewFile();

			}

			FileOutputStream fos = new FileOutputStream(f);
			fos.write(file.getFileData());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(this, e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(this, e);
		}
		return url + newName;
	}

	public String createNewName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String setFileFormat(HashMap fileFormat) {
		// TODO Auto-generated method stub
		this.fileFormat = fileFormat;
		return "";
	}

	public void setCount(int fileType, int count) {
		// TODO Auto-generated method stub
		Count[fileType] = count;

	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return file.getFileName();
	}

	public boolean isLegality(List uploadList, FormFile file) {
		// TODO Auto-generated method stub
		String type = null;
		String name = file.getFileName();
		int i = name.lastIndexOf(".");
		type = name.substring(i + 1, name.length());
		type = type.toLowerCase();
		if (!fileFormat.containsKey(type))
			return false;
		Long size = (Long) fileFormat.get(type);
		int s = file.getFileSize();
		s = s / 1024;
		if (size.intValue() < s)
			return false;
		int fileType = getType(type);
		int typeCount = getTypeCount(uploadList, fileType);
		if (typeCount >= Count[fileType])
			return false;
		return true;
	}

	private int getType(String type) {

		if (pictureType.contains(type))
			return PICTURE;
		if (movieType.contains(type))
			return MOVIE;
		if (FlashType.contains(type))
			return FLASH;
		if ((otherType.contains(type)))
			return OTHER;
		return -1;
	}

	private int getTypeCount(List uploadList, int type) {
		int count = 0;
		Iterator i = uploadList.iterator();
		while (i.hasNext()) {

			String suffix = null;
			String name = (String) i.next();
			int ii = name.lastIndexOf(".");
			suffix = name.substring(ii + 1, name.length());
			int fileType = getType(suffix);
			if (type == fileType)
				count++;
		}
		return count;
	}

	// public FtpService getFtps() {
	// return ftps;
	// }

	// public void setFtps(FtpService ftps) {
	// this.ftps = ftps;
	// }
	public void setUrl(String url) {
		int i = url.lastIndexOf("/");
		if (i < url.length())
			url = url + "/";
		this.url = url;
	}

	public static void main(String[] arg0) {
		String absPath = LoadServiceImpl.class.getResource("").toString();
		String packagePath = LoadServiceImpl.class.getPackage().getName();
		packagePath = packagePath.replace(".", "/");
		System.out.println(packagePath);
		System.out.println(absPath);
		/*
		 * String newName="aaa"; String name="name.rm"; List l=new ArrayList();
		 * l.add("df.rm"); l.add("dd.rm"); l.add("dd.jpg"); HashMap hm=new
		 * HashMap(); hm.put("rm",new Long(500)); LoadServiceImpl lsi=new
		 * LoadServiceImpl(); lsi.setFileFormat(hm); lsi.setCount(2,2);
		 * System.out.println(lsi.getTypeCount(l,2));
		 */
		/*
		 * String type=""; String path="d:/temp111/fdsaf/fdsfd/"; int
		 * i=name.lastIndexOf("."); type=name.substring(i,name.length()); File
		 * source=new File("d:/aa.zip"); FileInputStream sou; byte[] file=new
		 * byte[1000000]; try { sou = new FileInputStream(source);
		 * sou.read(file); } catch (FileNotFoundException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * if(newName==null||newName.equals("")) newName=name; else
		 * newName=newName+"."+type; try { File f=new File(path);
		 * if(!f.exists()) { f.mkdir(); } f=new File(path+newName);
		 * if(!f.exists()) {
		 * 
		 * f.createNewFile();
		 *  }
		 * 
		 * FileOutputStream fos=new FileOutputStream(f); fos.write(file);
		 * fos.close(); } catch (FileNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public String saveToClasspath(FormFile file, String newName, String path) {
		// TODO Auto-generated method stub

		String webroot = "/WEB-INF/classes/";
		String filePath = "file:/";
		String absPath = LoadServiceImpl.class.getResource("").toString();
		String packagePath = LoadServiceImpl.class.getPackage().getName();
		packagePath = packagePath.replace(".", "/");
		webroot = webroot + packagePath + "/";
		absPath = absPath.replaceFirst(filePath, "");
		absPath = absPath.replaceAll(webroot, path);
		return saveToLocal(file, newName, absPath);
	}

	public List<String> getFlashType() {
		return FlashType;
	}

	public void setFlashType(List<String> flashType) {
		FlashType = flashType;
	}

	public List<String> getMovieType() {
		return movieType;
	}

	public void setMovieType(List<String> movieType) {
		this.movieType = movieType;
	}

	public List<String> getOtherType() {
		return otherType;
	}

	public void setOtherType(List<String> otherType) {
		this.otherType = otherType;
	}

	public List<String> getPictureType() {
		return pictureType;
	}

	public void setPictureType(List<String> pictureType) {
		this.pictureType = pictureType;
	}

}
