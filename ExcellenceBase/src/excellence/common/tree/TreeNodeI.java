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
	 * 连接、跳转
	 */
	public String getAction();

	/*
	 * 域
	 */
	public String getDomain();

	public void setDomain(String string);

	/*
	 * //是否展开
	 */
	public boolean isExpanded();

	public void setExpanded(boolean b);

	/*
	 * 图标
	 */
	public String getIcon();

	public void setIcon(String string);

	/*
	 * id对应的名称
	 */
	public String getLabel();

	public void setLabel(String string);

	/*
	 * id
	 */
	public String getName();

	public void setName(String string);

	/*
	 * 父id
	 */
	public String getParentName();

	public void setParentName(String string);

	/*
	 * 目标框架
	 */
	public String getTarget();

	public void setTarget(String string);
}
