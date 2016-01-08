/**
 * 
 * ����ʱ�䣺2007-8-20����09:31:04
 * �ļ�����ViewBean.java
 * �����ߣ�zhaoyifei
 * 
 */
package base.zyf.web.crud.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import base.zyf.web.crud.db.CommonTableView;
import base.zyf.web.crud.db.CommonTableViewSet;

/**
 * @author zhaoyifei
 *
 */
public class ViewBean {

	private List viewRow;
	private String idName="id";
	private List viewList;
	public class ViewAssi{
		String row;
		String rowName;
		String link;
		String style;
		public String getStyle() {
			return style;
		}
		public void setStyle(String style) {
			this.style = style;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		/**
		 * @return the row
		 */
		public String getRow() {
			return row;
		}
		/**
		 * @param row the row to set
		 */
		public void setRow(String row) {
			this.row = row;
		}
		/**
		 * @return the rowName
		 */
		public String getRowName() {
			return rowName;
		}
		/**
		 * @param rowName the rowName to set
		 */
		public void setRowName(String rowName) {
			this.rowName = rowName;
		}
	}
	
	/**
	 * @return the viewList
	 */
	public List getViewList() {
		return viewList;
	}
	/**
	 * @param viewList the viewList to set
	 */
	public void setViewList(List viewList) {
		this.viewList = viewList;
	}
	/**
	 * @return the viewRow
	 */
	public List getViewRow() {
		return viewRow;
	}
	/**
	 * @param viewRow the viewRow to set
	 */
	public void setViewRow(List viewRow) {
		Iterator i=viewRow.iterator();
		this.viewRow=new ArrayList();
		while(i.hasNext())
		{
			Object o=i.next();
			ViewAssi va=new ViewAssi();
			if(o instanceof CommonTableViewSet)
			{
			CommonTableViewSet ctvs=(CommonTableViewSet)o;
			
			va.row=ctvs.getCommonTableView().getRowName();
			va.rowName=ctvs.getCommonTableView().getRowDisplayname();
			va.link=ctvs.getCommonTableView().getPopLink();
			va.style=ctvs.getCommonTableView().getStyle();
			}
			else
			{
				CommonTableView ctv=(CommonTableView)o;
				va.row=ctv.getRowName();
				va.rowName=ctv.getRowDisplayname();
				va.link=ctv.getPopLink();
			}
			this.viewRow.add(va);
		}
		
	}
	/**
	 * @return the idName
	 */
	public String getIdName() {
		return idName;
	}
	/**
	 * @param idName the idName to set
	 */
	public void setIdName(String idName) {
		this.idName = idName;
	}
}