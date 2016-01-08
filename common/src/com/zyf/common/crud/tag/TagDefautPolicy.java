package com.zyf.common.crud.tag;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zyf.context.BusinessContext;
import com.zyf.context.OperType;
import com.zyf.security.SecurityContextInfo;
import com.zyf.security.model.CurrentUser;
import com.zyf.security.model.RWCtrlType;

public class TagDefautPolicy extends TagSecurityDefautPolicy {


	
	public void securityParameterPopulate(String rwCtrlType,String permissionCode){

	}
	
	
	//����ҳ��״̬:OperType.ADD����ҳ/OperType.EDIT�༭ҳ/OperType.VIEW�鿴ҳ
	public int pageType(){
		if (pageContext.getAttribute("oid") == null || pageContext.getAttribute("oid").toString().length()==0) {
			return OperType.ADD;
		}
		else {
			return OperType.EDIT;
		}
	}
	

	public VisionStatusInfo compomentPermission(String rwCtrlType,
			String permissionCode, String wfPermissionCode,
			PageContext pageContext) {
		this.pageContext = pageContext;
		securityParameterPopulate(rwCtrlType, permissionCode, wfPermissionCode);
		int operType = pageType();
		// ����ҳ
		if (operType == OperType.ADD) {
			visionStatusInfo = addPagePermission();
		}// �鿴ҳ
		else if (operType == OperType.VIEW) {
			visionStatusInfo = inviewPagePermission(hibernateTemplate);
		}// �༭ҳ
		else if (operType == OperType.EDIT) {
			visionStatusInfo = inEditPagePermission(hibernateTemplate);
		}

		return visionStatusInfo;
	}
	
	
	//�鿴ҳ����
	public VisionStatusInfo inviewPagePermission(HibernateTemplate hibernateTemplate){
		visionStatusInfo.setPageType(ITagSecurityPolicy.VIEWPAGE);
		visionStatusInfo.setVisiableStatus(ITagSecurityPolicy.VISIBLE);
		visionStatusInfo.setEditableStatus(ITagSecurityPolicy.EDITABLE);
		return visionStatusInfo;
	}
	//�༭ҳ����
	public VisionStatusInfo inEditPagePermission(HibernateTemplate hibernateTemplate){
		visionStatusInfo.setPageType(ITagSecurityPolicy.EDITPAGE);
		visionStatusInfo.setVisiableStatus(ITagSecurityPolicy.VISIBLE);
		visionStatusInfo.setEditableStatus(ITagSecurityPolicy.EDITABLE);
		return visionStatusInfo;
	}
}
