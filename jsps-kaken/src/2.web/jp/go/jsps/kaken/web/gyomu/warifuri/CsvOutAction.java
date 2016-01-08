/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * CSV�o�̓A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: CsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class CsvOutAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		 throws ApplicationException
		{
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//���������̎擾
		WarifuriSearchForm searchForm = (WarifuriSearchForm)form;
			
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		WarifuriSearchInfo searchInfo = new WarifuriSearchInfo();
			
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
//		//�`�F�b�N�{�b�N�X�̎��ƃR�[�h���X�g���Z�b�g
//		List jigyoCdList = searchForm.getValueList();
//		if(jigyoCdList == null || jigyoCdList.size() == 0){
//			//���Ƃ��I������Ă��Ȃ��ꍇ�͒S�����Ƃ��Z�b�g
//			GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo();
//			searchInfo.setJigyoCdValueList(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
//		}else{
//			searchInfo.setJigyoCdValueList(searchForm.getValueList());
//		}
		//2005.05.17 iso �A�N�Z�X�Ǘ������Ƌ敪������CD�ɂ�������̂őΉ�
		//�ꗗ�����ƌ��������ݒ���@�𓝈�
		//�`�F�b�N�{�b�N�X�̎��ƃR�[�h���X�g���Z�b�g
		if(!searchForm.getValueList().isEmpty()){
			searchInfo.setJigyoCdValueList(searchForm.getValueList());	
		}else{
			//�w�肳��Ă��Ȃ��ꍇ�́A�i�Ɩ��S���҂Ȃ�΁j�������S�����鎖�Ƌ敪����R���Ώە��̎��Ƌ敪�̂ݎ擾	
			Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(container.getUserInfo());
			//�R���Ώە��̎��Ƌ敪�����������ɃZ�b�g
			searchInfo.setTantoJigyoKubun(shinsaTaishoSet);
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setJigyoCdValueList(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}
		
		ISystemServise servise = getSystemServise(IServiceName.SHINSAIN_WARIFURI_SERVICE);
		
		String jigyoKubun = servise.selectJigyoKubun(container.getUserInfo(),
													(String)searchForm.getValues(0));
		searchInfo.setJigyoKubun(jigyoKubun);
	
		//�������s
		FileResource fileRes = null;
		try {
			fileRes = 
				getSystemServise(
					IServiceName.SHINSAIN_WARIFURI_SERVICE).createIraisho(
					container.getUserInfo(),
					searchInfo);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
		}
				
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//-----�t�@�C���̃_�E�����[�h
		DownloadFileUtil.downloadFile(response, fileRes);	

		return forwardSuccess(mapping);
	}

}
