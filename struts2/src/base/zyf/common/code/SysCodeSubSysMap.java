/**
 * 
 * 制作时间：Oct 27, 20074:34:22 PM
 * 文件名：SysCodeSubSysMap.java
 * 制作者：zhaoyf
 * 
 */
package base.zyf.common.code;

import java.util.HashMap;
import java.util.Map;

import base.zyf.common.tree.classtree.ClassTreeService;
import base.zyf.spring.SpringRunningContainer;
import base.zyf.web.view.ComboSupportList;

/**
 * @author zhaoyf
 *
 */
public class SysCodeSubSysMap extends HashMap implements Map {

	public Object get(Object key) {
		// TODO Auto-generated method stub
		ComboSupportList value=(ComboSupportList)super.get(key);
		if(value==null)
		{
			ComboSupportList csl = getClassTreeService().getLabelVaList(
					key.toString());
			if(csl == null)
			{
				csl = getSysCodeService().getlist(key.toString());
			}
			value=csl;
			this.put(key, value);
		}
		 return value;
	}
	private ClassTreeService getClassTreeService() {
		return (ClassTreeService) SpringRunningContainer
				.getService(ClassTreeService.SERVICE_NAME);
	}
	private SysCodeService getSysCodeService() {
		return (SysCodeService) SpringRunningContainer
				.getService(SysCodeService.SERVICE_NAME);
	}
}
