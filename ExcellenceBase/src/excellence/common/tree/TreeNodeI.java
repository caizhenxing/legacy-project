/*
 * Created on 2004-7-8
 */
//package com.cw.common;
package excellence.common.tree;

/**
 * @author guxf
 * 
 */
public interface TreeNodeI {

	public Object getExtender();

	/*
	 * ���ӡ���ת
	 */
	public String getAction();

	/*
	 * ��
	 */
	public String getDomain();

	public void setDomain(String string);

	/*
	 * //�Ƿ�չ��
	 */
	public boolean isExpanded();

	public void setExpanded(boolean b);

	/*
	 * ͼ��
	 */
	public String getIcon();

	public void setIcon(String string);

	/*
	 * id��Ӧ������
	 */
	public String getLabel();

	public void setLabel(String string);

	/*
	 * id
	 */
	public String getName();

	public void setName(String string);

	/*
	 * ��id
	 */
	public String getParentName();

	public void setParentName(String string);

	/*
	 * Ŀ����
	 */
	public String getTarget();

	public void setTarget(String string);
}
