/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
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
 * 
 */
public class SearchCheckListAction extends BaseAction {

	/** �u���ׂāF0�v�F03(�����@�֎�t��), 04(�w�U��t��),06(�w�U��), 08�`12�̂��̂�\�� */
	private static String[] JOKYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//�����@�֎�t��	
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//�w�U��t��
			StatusCode.STATUS_GAKUSIN_JYURI,			//�w�U��
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//�R��������U�菈����
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO, 		//�R��������U��`�F�b�N����
			StatusCode.STATUS_1st_SHINSATYU,			//�ꎟ�R����
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//�ꎟ�R������
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//�񎟐R������
// 20050721 �w�U�s�󗝂�ǉ�
			,
			StatusCode.STATUS_GAKUSIN_FUJYURI			//�w�U�s��
// Horikoshi
	};
	
	//2005/04/21 �ǉ� ��������--------------------------------------------
	//���R ���������Ɏ󗝏󋵂�ǉ��������߁A�e�󋵂�ǉ�
	
	/** �u�m������F03�v�F��ID��03(�����@�֎�t��)�̂��̂�\�� */
	private static String[] JOKYO_ID_SYOZOKU = new String[] {
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU			//�����@�֎�t��
	};

	/** �u�󗝑O�F04�v�F��ID��04(�w�U��t���F�󗝑O)�̂��̂�\�� */
	private static String[] JOKYO_ID_JYURIMAE = new String[]{
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//�w�U��t��
	};

	/** �u�󗝍ς݁F06�A08�ȏ�v�F��ID��06(�w�U��),08�`12�̂��̂�\�� */
	private static String[] JOKYO_ID_JYURIZUMI = new String[]{
			StatusCode.STATUS_GAKUSIN_JYURI,			//�w�U��
//2006/05/22 �ǉ���������
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,      //�R��������U�菈���� 
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,         //����U��`�F�b�N����
            StatusCode.STATUS_1st_SHINSATYU,            //�ꎟ�R����
            StatusCode.STATUS_1st_SHINSA_KANRYO,           //�ꎟ�R��:����
            StatusCode.STATUS_2nd_SHINSA_KANRYO,             //�񎟐R������
//�c�@�ǉ������܂�            
	};

// 20050721 �w�U�s��
    /** �u�s�� �F07�v�F��ID��07(�w�U�s��)�̂��̂�\�� */
	private static String[] JOKYO_ID_FUJYURI = new String[]{
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//�w�U�s��
	};
// Horikoshi

	/** ��������:�S�� */
	private static final String CHECK_ALL = "0";
	
	/** ��������:�m����� */
	private static final String CHECK_SHOZOKU = "1";
	
//	�ǉ� �����܂�-------------------------------------------------------
	
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
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		//���������ێ��p
		CheckListInfo checkInfo = new CheckListInfo();
	
		//�t�H�[�����擾
		CheckListSearchForm searchForm = (CheckListSearchForm)form;

// 2006/02/13 update		
		//���Ƌ敪
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
// 2006/02/13 dhy  end		
		
		//���������̐ݒ�
		if(searchForm.getJigyoCd() != null && !searchForm.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(searchForm.getJigyoCd());
			checkInfo.setJigyoCd(searchForm.getJigyoCd());
		}
		if(searchForm.getShozokuCd() != null && !searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
			checkInfo.setShozokuCd(searchForm.getShozokuCd());
		}
	
		//2005/04/21 �ǉ� ��������---------------------------------------------
		//���R ���������̒ǉ�
		if(searchForm.getShozokuName() != null && !searchForm.getShozokuName().equals("")){
			searchInfo.setShozokuName(searchForm.getShozokuName());
			checkInfo.setShozokuName(searchForm.getShozokuName());
				
		}
		String jokyoKubun = searchForm.getJuriJokyo();
        //�f�t�H���g��I�����鎞�A�S�ĂɂƂ���
		if(jokyoKubun == null || jokyoKubun.equals("") ||jokyoKubun.equals("0")){
		
			//��ID��04(�w�U��t��),06(�w�U��), 08�`12�̂��̂�\��
			searchInfo.setSearchJokyoId(JOKYO_IDS);
			searchInfo.setCancellationFlag(CHECK_ALL);
		}
        //�u�m������F03�v��I�����鎞
		else if(jokyoKubun.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
			//��ID��03(�����@�֎�t��)�ŁA�����t���O��������̂�\��
			searchInfo.setSearchJokyoId(JOKYO_ID_SYOZOKU);
			searchInfo.setCancellationFlag(CHECK_SHOZOKU);
		}
        //�u�󗝑O�F04�v��I�����鎞
		else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_SHORITYU)){
			//��ID��04(�w�U��t��)�̂��̂�\��
			searchInfo.setSearchJokyoId(JOKYO_ID_JYURIMAE);
		}
        //�u�󗝍ς݁F06�A08�ȏ�v��I�����鎞
		else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			//��ID��06(�w�U��),08�`12�̂��̂�\��
			searchInfo.setSearchJokyoId(JOKYO_ID_JYURIZUMI);
		}
// 20050721
        //�u�s�� �F07�v��I�����鎞
		else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			//��ID��07(�w�U�s��)�̂��̂�\��
			searchInfo.setSearchJokyoId(JOKYO_ID_FUJYURI);
		}
// Horikoshi
        //��L�ȊO��I�����鎞
		else{
			//��ID��04(�w�U��t��),06(�w�U��), 08�`12�̂��̂�\�� 
			searchInfo.setSearchJokyoId(JOKYO_IDS);
		}

		//�ǉ� �����܂�-----------------------------------------------------------
		
		//�y�[�W����
		searchInfo.setStartPosition(searchForm.getStartPosition());
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());

// 20050629 NoDataFoundException��throw����悤�ύX
		Page result = null;
		try{
			//�������s
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
					container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			throw new NoDataFoundException("�Y���f�[�^�͂���܂���B");
		}
// Horikoshi

		//�����������R���e�i�Ɋi�[		
		container.setCheckListInfo(checkInfo);
		container.setCheckListSearchInfo(searchInfo);

		//�������ʂ��Z�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}
}