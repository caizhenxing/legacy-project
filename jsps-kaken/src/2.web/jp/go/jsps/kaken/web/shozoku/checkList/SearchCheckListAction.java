/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchCheckListAction.java
 *    Description : �`�F�b�N���X�g�ꗗ�A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�ꗗ�A�N�V�����N���X�B
 * �`�F�b�N���X�g�ꗗ��ʂ�\������B
 */
public class SearchCheckListAction extends BaseAction {

	//2005/04/11 �ǉ� ��������--------------------------------------------------
	//��ID�������@�֎�t���ȍ~�̂��̂ɂ��ĕ\������悤�C��

	/** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,  //�����@�֎�t��
			StatusCode.STATUS_GAKUSIN_SHORITYU,         //�w�U��t��
//			StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,        //�����@�֋p��
			StatusCode.STATUS_GAKUSIN_JYURI,            //�w�U��
			StatusCode.STATUS_GAKUSIN_FUJYURI,          //�w�U�s��
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,//�R��������U�菈����
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,    //����U��`�F�b�N����
			StatusCode.STATUS_1st_SHINSATYU,            //�ꎟ�R����
			StatusCode.STATUS_1st_SHINSA_KANRYO,        //�ꎟ�R���F����
			StatusCode.STATUS_2nd_SHINSA_KANRYO         //�񎟐R������
	};
	//�ǉ� �����܂�-------------------------------------------------------------

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

// 20050601 Start ���������Ɏ��Ƌ敪��ǉ����������֐��ɕύX
//update start ly 2006/6/2
		CheckListForm listForm = (CheckListForm) form;
        if (listForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
            ArrayList jigyoCds = new ArrayList();
            jigyoCds.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
            jigyoCds.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
            
//DEL�@START 2007/06/28 BIS �����_
            /*
             jigyoCds.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
             */
//DEL�@END�@ 2007/06/28 BIS �����_�@
            
            jigyoCds.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
            jigyoCds.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
            checkInfo.setTantoJigyoCd(jigyoCds);
        }
        checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());
//		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
//update end ly 2006/6/2
// Horikoshi End
        
//2006/06/08 �c �ǉ���������        
        listForm.setJigyoCd("");
        updateFormBean(mapping,request,listForm);
//2006/06/08 �c �ǉ������܂�
        
		//2005/04/11 �ǉ� ��������--------------------------------------------------
		//��ID�������@�֎�t���ȍ~�̂��̂ɂ��ĕ\������悤�C��
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		//�ǉ� �����܂�-------------------------------------------------------------


		//2005/05/19 �ǉ� ��������--------------------------------------------------
		//���R �f�[�^�����݂��Ȃ��ꍇ��page��EMPTY_PAGE���Z�b�g���邽��try-catch�̒ǉ�
		Page result = null;
		try{
		//�������s

		result = 
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
				container.getUserInfo(),
				checkInfo);

		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
		}
		//�ǉ� �����܂�------------------------------------------------------------
		
		//�����������t�H�[�����Z�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}
}