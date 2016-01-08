/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�̔�єԍ����X�g���擾����N���X�B
 * �`�F�b�N���X�g�Ǘ��T�[�r�X�Ŕ�єԍ��擾�p�̃f�[�^���擾����B
 * 
 * @author masuo_t
 *
 */
public class TobiListAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start ����������ǉ�
//update start dyh 2006/2/8
//		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
		CheckListForm listForm = (CheckListForm)form;
		checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());//���Ƌ敪
//update end dyh 2006/2/8
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)form;	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//2005/04/13 �ǉ� ��������------------------------------------------------------
		//���R�@��єԍ����X�g��ʂ̃^�C�g�����擾�̂���
		
		//2005/05/19 �ύX ��������------------------------------------------------
		//���R �^�C�g�����̎擾���@�̕ύX�̂���
		//�^�C�g���\�����̎擾
		Page titleResult = 
		//			  getSystemServise(
		//				  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectListData(
		//				  container.getUserInfo(),
		//				  checkInfo);
					  getSystemServise(
						  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTitle(checkInfo);
		//�^�C�g���\�������t�H�[���ɃZ�b�g����
		request.setAttribute(IConstants.TITLE_INFO, titleResult);
		//�ύX �����܂�-----------------------------------------------------------
		
		//�ǉ� �����܂�-----------------------------------------------------------------


		//2005/05/19 �ǉ� ��������--------------------------------------------------
		//���R �f�[�^�����݂��Ȃ��ꍇ��page��EMPTY_PAGE���Z�b�g���邽��try-catch�̒ǉ�
		Page result = null;
		try{	
		//��єԍ��̎擾
			result = getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTobiList(
				container.getUserInfo(),
				checkInfo);	
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
		}
		//�ǉ� �����܂�------------------------------------------------------------	
		
		//���ʂ��t�H�[���ɃZ�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}

}
