/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : ����
 *    Date        : 2007/6/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.ryoikiKeikaku;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.impl.LabelValueMaintenance;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�v�揑��񌟍��O�A�N�V�����N���X�B
 * �̈�v�揑��񌟍���ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchAction.java,v $"
 * Revision="$Revision: 1.8 $"
 * Date="$Date: 2007/07/25 09:44:59 $"
 */
public class SearchAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//��������������΃N���A����B
		removeFormBean(mapping,request);
		
		//�����������t�H�[�����Z�b�g����B
		RyoikiGaiyoSearchForm searchForm = new RyoikiGaiyoSearchForm();
		
		
		LabelValueMaintenance labelValueMaintenance = new LabelValueMaintenance();
		String jigyoName = labelValueMaintenance.searchJigyoName(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);
		//------�v���_�E���f�[�^�Z�b�g
		searchForm.setJigyoName(jigyoName);

		List listT = new ArrayList();
		listT.add(new LabelValueBean(" ","0"));
		List listTemp = labelValueMaintenance.getLabelList("OUBOJOKYO");
		if(listTemp.size()>0){
			for(int i=0;i<listTemp.size();i++){
				listT.add(listTemp.get(i));
			}
		}
		//		����󋵃��X�g
		searchForm.setJokyoList(listT);
						
		updateFormBean(mapping,request,searchForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
