/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CheckListForm.java
 *    Description : �󗝓o�^�Ώۉ�����ꗗ�p�t�H�[��
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    V1.0                       �V�K�쐬
 *    2006/06/06    V1.1        DIS.GongXB     �C���i����CD��ǉ��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

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
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�i��Ձj�ꗗ�A�N�V�����N���X�B
 * �`�F�b�N���X�g�ꗗ��ʂ�\������B
 */
public class SearchCheckListAction extends BaseAction {

	/** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//�����@�֎�t��
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//�w�U��t��
//			StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,			//�����@�֋p��
			StatusCode.STATUS_GAKUSIN_JYURI,			//�w�U��
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//�w�U�s��
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//�R��������U�菈����
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,			//����U��`�F�b�N����
			StatusCode.STATUS_1st_SHINSATYU,			//�ꎟ�R����
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//�ꎟ�R���F����
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//�񎟐R������
	};
	
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
		
		//��ID�������@�֎�t���ȍ~�̂��̂ɂ��ĕ\������
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		
// 20050606 Horikoshi Start
//update start liuyi 2006/2/16
		//checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
	    CheckListForm listForm = (CheckListForm)form;
//2006/06/02 �{�G�j�@�ǉ���������
        //�`�F�b�N���X�g�m�F(��Ռ����iC�j�E�G�茤���E��茤��)�̎��A
        //���������̎��ƃR�[�h���i00061�A00062�A00111�A00121�A00131�j�ɐݒ�
        if ((! StringUtil.isBlank(listForm.getJigyoCd()))
                &&(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(listForm.getJigyoCd()))){
            ArrayList array = new ArrayList();
            array.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
            array.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
            
//DEL�@START 2007/06/28 BIS �����_            
            //array.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
//DEL�@END�@ 2007/06/28 BIS �����_            
            
            array.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
            array.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
            checkInfo.setTantoJigyoCd(array);
        }
        
//      2006/06/08 �ǉ���������        
        listForm.setJigyoCd("");
        updateFormBean(mapping,request,listForm);
//�c�@�ǉ������܂�
        
        //���������̎��Ƌ敪��ݒ�
        if(! StringUtil.isBlank(listForm.getJigyoKbn())){
            checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());
        }        
		//checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());
//2006/06/02 �{�G�j�@�ǉ������܂�
//update end liuyi 2006/2/16
// 20050606 Horikoshi End

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
		//2005/05/19 �ǉ� �����܂�--------------------------------------------------		

		//�������ʂ��t�H�[���ɃZ�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);

		return forwardSuccess(mapping);
	}
}