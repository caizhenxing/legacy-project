/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 25, 20078:34:26 PM
 * �ļ�����IComInvorkee.java
 * �����ߣ�wuym
 * 
 */
package com.zyf.common.crud.tag;

/**
 * @author wuym
 *
 */
public interface IVisionInvorkee {
	public final static int UNVISIBLE = 1;
	public final static int VISIBLE = 2;
	public final static int UNEDITABLE = 3;
	public final static int EDITABLE = 4;
	public final static int VIEWPAGE = 5;
	public final static int EDITPAGE = 6;
	
	public VisionStatusInfo getVisionStatusInfo(String rwCtrlType,String permissionCode);
}
