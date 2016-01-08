/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-8-18下午03:09:18
 * 文件名：CurdForm.java
 * 制作者：Administrator
 * 
 */
package com.zyf.common.crud.web;

import java.util.List;

import com.zyf.web.BaseActionForm;


/**
 * @author zhaoyifei
 *
 */
public class CurdForm extends BaseActionForm {

	private List all;
	private List select;
	private String pageId;
	private String[] saveList;
	private List his;
	private boolean asc=false;
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	/**
	 * @return the his
	 */
	public List getHis() {
		return his;
	}
	/**
	 * @param his the his to set
	 */
	public void setHis(List his) {
		this.his = his;
	}
	public List getAll() {
		return all;
	}
	public void setAll(List all) {
		this.all = all;
	}
	public List getSelect() {
		return select;
	}
	public void setSelect(List select) {
		this.select = select;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String[] getSaveList() {
		return saveList;
	}
	public void setSaveList(String[] saveList) {
		this.saveList = saveList;
	}
	
}
