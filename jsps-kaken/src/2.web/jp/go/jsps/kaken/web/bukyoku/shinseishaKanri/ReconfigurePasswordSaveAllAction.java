/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/4/8
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �p�X���[�h�Đݒ���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�p�X���[�h�Đݒ�����N���A����B
 * 
 */
public class ReconfigurePasswordSaveAllAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//2005/04/20 �폜 ��������-------------------------------------
		//���R ��������ʕ\���p�Ƀg�[�N���̃`�F�b�N��ReconfigurePasswordCheck�ōs���悤�ɕύX��������
		//-----ActionErrors�̐錾�i��^�����j-----
		//ActionErrors errors = new ActionErrors();

		//-----�擾�����g�[�N���������ł���Ƃ�
		//if (!isTokenValid(request)) {
		//	errors.add(ActionErrors.GLOBAL_ERROR,
		//			   new ActionError("error.transaction.token"));
		//	saveErrors(request, errors);
		//	return forwardTokenError(mapping);
		//}
		//�폜 �����܂�------------------------------------------------
			
		//------�\���ҏ��ێ��N���X
		ShinseishaInfo reconfigurePasswordInfo = new ShinseishaInfo();

		//------�p�X���[�h�Ĕ��s�p
		ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
		ShinseishaSearchForm searchForm = (ShinseishaSearchForm)request.getSession().getAttribute("shinseishaSearchForm");

		//searchForm����searchInfo�ɃR�s�[
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
	
		//�y�[�W�T�C�Y��0��ݒ肷�邱�ƂőS�Ẵy�[�W�������悤�ɂ���
		//�p�X���[�h�ꊇ�Đݒ�Ńy�[�W�\�����݂̂���������ꍇ�͂��̏������R�����g�A�E�g����
		searchInfo.setPageSize(0);		
	
		//�\���ҏ��ꗗ�擾
		//SearchListAction.java�̏����Ɠ��l�̏����Ő\���ҏ��̈ꗗ���擾���Ă���A
		//��L�\�[�X���C������ꍇ�A���l�ɏC������K�v������
		searchInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
		Page resultTnto = null;
		try {
			resultTnto =
				getSystemServise(
					IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).search(
					container.getUserInfo(),
					searchInfo);
		} catch(ApplicationException e) {
			//------�Y���S���҂Ȃ����ʏ킠�肦�Ȃ��̂ŋ�\��
		}
		
		if(resultTnto == null){
			return forwardFailure(mapping);
		}
		//------�\����ID�̔z��
		ArrayList data = new ArrayList();

		//2005/04/29 �ǉ� ----------------------------------------------��������
		//���R �o�^����������No��UserContainer�֒��ڕێ�����悤�ɏC����������
		ArrayList kenkyuNoList = new ArrayList();
		//2005/04/29 �ǉ� ----------------------------------------------�����܂�
		
		
		//------�\����ID���ږ��FSHINSEISHA_ID
		String shinseishaId = ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID;
		
		//------�\���҂̐����J��Ԃ�
		for(int i = 0; i < resultTnto.getSize(); i++){
			
			//------�e�\���҂̏����擾
			HashMap shinseishaDataMap = (HashMap)resultTnto.getList().get(i);
				
			//------�\����ID�Ƀf�[�^������ꍇ�͔z��Ɋi�[	
			if(shinseishaDataMap.get(shinseishaId) != null && !shinseishaDataMap.get(shinseishaId).equals("")){
				 data.add(shinseishaDataMap.get(shinseishaId));

				 //2005/04/29 �ǉ� ----------------------------------------------��������
				 //���R �o�^����������No��UserContainer�֒��ڕێ�����悤�ɏC����������
				 kenkyuNoList.add(shinseishaDataMap.get(ShinseiSearchInfo.ORDER_BY_KENKYU_NO));
				 //2005/04/29 �ǉ� ----------------------------------------------�����܂�
			}
		}
		ISystemServise servise = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE);

		//------DB�o�^ �p�X���[�h�Ĕ��s����
		servise.reconfigurePasswordAll(container.getUserInfo(),reconfigurePasswordInfo, data);
		
		// �ύX�@�����܂�
		
		
		//2005/04/29 �ǉ� ----------------------------------------------��������
		//���R �o�^����������No��UserContainer�֒��ڕێ�����悤�ɏC��
		container.setKenkyuNo((String[])kenkyuNoList.toArray(new String[kenkyuNoList.size()]));
		//2005/04/29 �ǉ� ----------------------------------------------�����܂�

		
		
		if(log.isDebugEnabled()){
			log.debug("�\���ҏ��@�p�X���[�h�Đݒ��� '"+ request);
		}

		//------�g�[�N���̍폜	
		resetToken(request);
		//------�Z�b�V�������V�K�o�^���̍폜
		container.setShinseishaInfo(null);
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}

}
