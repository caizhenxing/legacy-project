/*
 * Created on 2005-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ocelot.common.tree;

/**
 * �ڿ��Ʋ������
 */
public interface TreeControlI extends Cloneable{
    public TreeControl getTreeControl();
    public String getKey();
    public Object clone() throws CloneNotSupportedException ;
}
