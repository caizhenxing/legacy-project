/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：ExcellenceBase
 * 制作时间：2009-3-26下午08:52:33
 * 包名：excellence.common.mail
 * 文件名：MailAttachment.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package excellence.common.mail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class MailAttachment {

	private String path;
	private String name;
	private int size;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public static List<MailAttachment> getMailAttachs(String paths)
	{
		List<MailAttachment> mas = new ArrayList<MailAttachment>();
		String[] ps = paths.split(";");
		if(StringUtils.isEmpty(paths))
		{
			return mas;
		}
		for(int i = 0; i<ps.length; i++)
		{
			MailAttachment ma = new MailAttachment();
			ma.path = ps[i];
			
			ma.name = ma.path.substring(ma.path.lastIndexOf("/")+1);
			mas.add(ma);
		}
		return mas;
	}
}
