/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 25, 20078:34:26 PM
 * 文件名：DefaultComInvorkee.java
 * 制作者：wuym
 * 
 */
package com.zyf.common.crud.tag;

/**
 * @author wuym
 * 
 */
public class VisionUneditableInvorkee implements IVisionInvorkee {
	public VisionStatusInfo getVisionStatusInfo(String rwCtrlType,
			String permissionCode) {
		VisionStatusInfo visionStatusInfo = new VisionStatusInfo();

		visionStatusInfo.setPageType(IVisionInvorkee.EDITPAGE);// 查看页或编辑页
		visionStatusInfo.setVisiableStatus(IVisionInvorkee.VISIBLE);// 是否可见状态
		visionStatusInfo.setEditableStatus(IVisionInvorkee.UNEDITABLE);// 是否编辑状态
		return visionStatusInfo;
	}
}
