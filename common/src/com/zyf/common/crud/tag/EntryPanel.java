/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：common
 * 制作时间：2007-12-26上午11:29:21
 * 包名：com.zyf.common.crud.tag
 * 文件名：EntryPanel.java
 * 制作者：wenjb
 * @version 1.0
 */
package com.zyf.common.crud.tag;

import java.util.List;
/**
 * 
 * @author wenjb
 * @version 1.0
 */
public class EntryPanel {
	
	/**
	 * 描述：此LIST存储选项卡的定义信息.
	 * 属性名：panelList
	 * 属性类型：Map
	 */
	private List panelList ;

	public List getPanelList() {
		return panelList;
	}

	public void setPanelList(List panelList) {
		this.panelList = panelList;
	}

}
