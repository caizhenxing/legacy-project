/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : IkenJyokenAction.java
 *    Description : �ӌ���񌟍��������̓A�N�V�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     �V�K
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
//import jp.go.jsps.kaken.model.vo.UserInfo;
//import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ӌ���񌟍��O�A�N�V�����N���X�B
 * Bean�����������āA�ӌ���񌟍���ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: IkenJyokenAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenJyokenAction extends BaseAction {

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

		//if ( !((IkenSearchForm)form).getAction().equals("edit") ){
			//��������������΃N���A����B(Bean���N���A����)
			removeFormBean(mapping,request);
		//}
		
		//�����������t�H�[�����Z�b�g����B
		IkenSearchForm searchForm = new IkenSearchForm();

		/*
		//���̏������Z�b�g
		IkenSearchForm oldForm = (IkenSearchForm)form;
		searchForm.setShinseisya(oldForm.getShinseisya());	//�\����
		searchForm.setSyozoku(oldForm.getSyozoku());		//�����@�֒S����
		searchForm.setBukyoku(oldForm.getBukyoku());		//���ǒS����
		searchForm.setShinsyain(oldForm.getShinsyain());	//�R����
		searchForm.setSakuseiDateFromYear(oldForm.getSakuseiDateFromYear());
		searchForm.setSakuseiDateFromMonth(oldForm.getSakuseiDateFromMonth());
		searchForm.setSakuseiDateFromDay(oldForm.getSakuseiDateFromDay());
		searchForm.setSakuseiDateToYear(oldForm.getSakuseiDateToYear());
		searchForm.setSakuseiDateToMonth(oldForm.getSakuseiDateToMonth());
		searchForm.setSakuseiDateToDay(oldForm.getSakuseiDateToDay());
		searchForm.setDispmode(oldForm.getDispmode());
		searchForm.setAction("");

		if (log.isDebugEnabled()){
			log.debug("setShinseisya:" + searchForm.getShinseisya());
			log.debug("setSakuseiDateFromYear:" + searchForm.getSakuseiDateFromYear());
		}
		*/
		
		updateFormBean(mapping,request,searchForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
