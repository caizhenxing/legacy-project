/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 25, 20078:34:26 PM
 * �ļ�����DefaultComInvorkee.java
 * �����ߣ�wuym
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

		visionStatusInfo.setPageType(IVisionInvorkee.EDITPAGE);// �鿴ҳ��༭ҳ
		visionStatusInfo.setVisiableStatus(IVisionInvorkee.VISIBLE);// �Ƿ�ɼ�״̬
		visionStatusInfo.setEditableStatus(IVisionInvorkee.UNEDITABLE);// �Ƿ�༭״̬
		return visionStatusInfo;
	}
}
