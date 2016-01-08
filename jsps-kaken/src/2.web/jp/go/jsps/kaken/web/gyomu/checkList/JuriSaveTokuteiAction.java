/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriSaveTokuteiAction.java
 *    Description : �󗝓o�^(����)���s���B
 *
 *    Author      : Admin
 *    Date        : 2005/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/06/14    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import jp.go.jsps.kaken.model.common.IJigyoKubun;

import java.util.List;
import java.util.ArrayList;

/**
 * �󗝓o�^(����)���s���B
 */
public class JuriSaveTokuteiAction extends BaseAction {

// 2006/07/21 dyh delete start ���R�F�g�p���Ȃ�
//	/** ��ID��04(�w�U��t��)�̂��̂�\�� */
//	private static String[] JIGYO_IDS = new String[]{
//		StatusCode.STATUS_GAKUSIN_SHORITYU		//�w�U��t��
//	};
// 2006/07/21 dyh delete end

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		String chJuriKekka = "";								//�ύX��̏�ID���擾����
		String chJuriBiko = "";									//���l
		ActionErrors errors = new ActionErrors();				//-----ActionErrors�̐錾�i��^�����j-----
		JuriCheckListForm juriForm = (JuriCheckListForm)form;	// �t�H�[���擾

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		//------�g�[�N���̍폜
		resetToken(request);

		//���������̐ݒ�
		if(juriForm.getJigyoID() != null && !juriForm.getJigyoID().equals("")){			//����ID
			searchInfo.setJigyoId(juriForm.getJigyoID());
		}
		if(juriForm.getShozokuCD() != null && !juriForm.getShozokuCD().equals("")){		//����CD
			searchInfo.setShozokuCd(juriForm.getShozokuCD());
		}
		searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);						//���Ƌ敪
		searchInfo.setJokyoId(juriForm.getJokyoID());									//�ύX�O�̏�ID
		chJuriKekka = request.getParameter("juriCheckListForm.juriKekka");				//�ύX��̏�ID
		if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			juriForm.setJuriKekka(StatusCode.STATUS_GAKUSIN_JYURI);
			searchInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
		}
		else if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			juriForm.setJuriKekka(StatusCode.STATUS_GAKUSIN_FUJYURI);
			searchInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_FUJYURI);
		}
		chJuriBiko = request.getParameter("juriCheckListForm.juriBiko");				//�󗝔��l
		if(chJuriBiko != null && chJuriBiko.length() > 0){
			juriForm.setJuriBiko(chJuriBiko);
			searchInfo.setJuriComment(chJuriBiko);
		}

		//���������ƂȂ��Ԃ̃Z�b�g�@���ύX��̏�Ԃ��m��(06)�̏ꍇ�͎󗝉����ɂčs�����ߑΏۊO
		if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			//�ύX��̏�Ԃ���(06)�̏ꍇ�A�Ώۂ͏�ID��(04)�܂���(07)�̂���
			String[] JokyoStr = new String[]{
					StatusCode.STATUS_GAKUSIN_SHORITYU,
					StatusCode.STATUS_GAKUSIN_FUJYURI
					};
			searchInfo.setSearchJokyoId(JokyoStr);				//��ID�����������ɃZ�b�g
		}
		else if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			//�ύX��̏�Ԃ��s��(07)�̏ꍇ�A�Ώۂ͏�ID��(04)�̂���
			String[] JokyoStr = new String[]{
					StatusCode.STATUS_GAKUSIN_SHORITYU
					};
			searchInfo.setSearchJokyoId(JokyoStr);				//��ID�����������ɃZ�b�g
		}

		//�ύX�O�ƕύX��̏󋵂�����̏ꍇ
		if(chJuriKekka.equals(searchInfo.getJokyoId())){
			ActionError error = new ActionError("errors.5051", "����̏󋵂ɍX�V�͂ł��܂���B");
			errors.add("�󗝓o�^�ŃG���[���������܂����B", error);

			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		List lstErrors = new ArrayList();
		try{
			ISystemServise servise = getSystemServise(IServiceName.CHECKLIST_MAINTENANCE_SERVICE);	//CheckListMaintenance�̃T�[�r�X���擾
			lstErrors = servise.CheckListAcceptUnacceptable(container.getUserInfo(), searchInfo);			//�󗝁A�s�󗝂̎��s
			for(int n=0; n<lstErrors.size(); n++){
				ActionError error = new ActionError("errors.5051",lstErrors.get(n));
				errors.add("kenkyuExists", error);
			}
//		}catch(NoDataFoundException ex){
//			ActionError error = new ActionError("errors.5051",ex.getMessage());
//			errors.add("���݂��Ȃ������ҏ�񂪓o�^����Ă��܂��B", error);
		}catch(ApplicationException ex){
			ActionError error = new ActionError("errors.4002");
			errors.add("�󗝓o�^�ŃG���[���������܂����B", error);
		}
		finally{
		}

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		return forwardSuccess(mapping);
	}
}