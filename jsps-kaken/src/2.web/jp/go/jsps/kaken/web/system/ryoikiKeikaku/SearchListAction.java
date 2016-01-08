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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoSystemInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�v�揑��񌟍��A�N�V�����N���X�B
 * �̈�v�揑���ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.5 $"
 * Date="$Date: 2007/07/25 07:56:05 $"
 */
public class SearchListAction extends BaseAction {

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

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
			
		//���������̎擾
		RyoikiGaiyoSearchForm searchForm = (RyoikiGaiyoSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		RyoikiKeikakushoSystemInfo searchInfo = new RyoikiKeikakushoSystemInfo();
		
		//������ږ�
		if(!searchForm.getJigyoName().equals("")){		
			searchInfo.setJigyoName(searchForm.getJigyoName());
		}
		//���̈�ԍ�
		if(!searchForm.getKariryoikiNo().equals("")){	
			searchInfo.setKariryoikiNo(searchForm.getKariryoikiNo());
		}
		//�̈��\�Ҏ���-��
		if(!searchForm.getNameKanjiSei().equals("")){			
			searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		}
		//�̈��\�Ҏ���-��
		if(!searchForm.getNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		}		
		//���������@�֖��[�ԍ�
		if(!searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
		//���������@�֖��[����
		if(!searchForm.getShozokuName().equals("")){
			searchInfo.setShozokuName(searchForm.getShozokuName());
		}
		//�����
		if(!searchForm.getRyoikiJokyoId().equals("")){
			if (searchForm.getRyoikiJokyoId().length()==1) {
				if (!"0".equals(searchForm.getRyoikiJokyoId())){
					searchInfo.setRyoikiJokyoId("0"+searchForm.getRyoikiJokyoId());
				}
			}else{
				searchInfo.setRyoikiJokyoId(searchForm.getRyoikiJokyoId());
			}
		}		
		ISystemServise service = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
		List result = service.getRyoikiResult(searchInfo);
		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
