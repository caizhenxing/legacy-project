package base.zyf.common.ftp;

import java.io.InputStream;
import java.io.OutputStream;



 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface FtpService {

	public void setUserName(String name);
	public void setPassWord(String pw);
	public void setUrl(String url);
	public void setPath(String path);
	public void uploadFile(String name,InputStream fis);
	public void removeFile(String namePath);
	public void download(String namePath,OutputStream os);
	public String changePath(String path);
}
