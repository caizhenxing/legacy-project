/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.ArrayList;
import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �f�[�^�ۊǐ\����񌟍��A�N�V�����N���X�B
 * �\�����ꗗ��ʂ�\������B
 * 
 */
public class SearchListAction extends BaseAction {

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
		DataHokanForm searchForm = (DataHokanForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		
		//���ƃR�[�h
		if(!searchForm.getJigyoCd().equals("")){
//2007/02/25 �c�@�C����������
            if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(searchForm.getJigyoCd())) {
                ArrayList jigyoKubunList = new ArrayList();
                jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
                searchInfo.setJigyoKubun(jigyoKubunList);
            } else {
//2007/02/25�@�c�@�C�������܂�            
    			searchInfo.setJigyoCd(searchForm.getJigyoCd());	//�w�肳��Ă����ꍇ�͓��Y���ƃR�[�h�̂�    
            }
		}else{
			//�w�肳��Ă��Ȃ��ꍇ�́A�i�Ɩ��S���҂Ȃ�΁j�������S�����鎖�ƃR�[�h�̂�
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
			}
		}
		//�N�x
		if(!searchForm.getNendo().equals("")){		
			searchInfo.setNendo(searchForm.getNendo());
		}
		//��
		if(!searchForm.getKaisu().equals("")){	
			searchInfo.setKaisu(searchForm.getKaisu());
		}
		//�\���Җ��i�����j-��
		if(!searchForm.getShinseishaNameKanjiSei().equals("")){			
			searchInfo.setNameKanjiSei(searchForm.getShinseishaNameKanjiSei());
		}
		//�\���Җ��i�����j-��
		if(!searchForm.getShinseishaNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getShinseishaNameKanjiMei());
		}		
		//�\���Ҏ����i�t���K�i-���j
		if(!searchForm.getShinseishaNameKanaSei().equals("")){			
			searchInfo.setNameKanaSei(searchForm.getShinseishaNameKanaSei());
		}
		//�\���Ҏ����i�t���K�i-���j
		if(!searchForm.getShinseishaNameKanaMei().equals("")){
			searchInfo.setNameKanaMei(searchForm.getShinseishaNameKanaMei());
		}
		//2005/04/25 �폜 ���ꂩ��----------------------------------------
		//���R �\���Җ��̃��[�}���������Ȃ����ߍ폜
		/*
		//�\���Җ��i���[�}���j-��
		if(!searchForm.getShinseishaNameRoSei().equals("")){
			searchInfo.setNameRoSei(searchForm.getShinseishaNameRoSei());
		}
		//�\���Җ��i���[�}���j-��
		if(!searchForm.getShinseishaNameRoMei().equals("")){
			searchInfo.setNameRoMei(searchForm.getShinseishaNameRoMei());
		}
		*/
		//�폜 �����܂�---------------------------------------------------
				
		//�����@�փR�[�h
		if(!searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
		//�����Ҕԍ�
		if(!searchForm.getKenkyuNo().equals("")){
			searchInfo.setKenkyuNo(searchForm.getKenkyuNo());
		}
		//�\���ԍ�
		if(!searchForm.getUketukeNo().equals("")){		
			searchInfo.setUketukeNo(searchForm.getUketukeNo());		
		}
		//�זڔԍ�
		if(!searchForm.getBunkaSaimokuCd().equals("")){		
			searchInfo.setBunkasaimokuCd(searchForm.getBunkaSaimokuCd());		
		}
		
//		//�\���󋵏����i�Œ�F�Ɩ��S���҂��Q�Ɖ\�ȃX�e�[�^�X�̂��́j
//		String[] str_array = new String[]{StatusCode.STATUS_GAKUSIN_SHORITYU,			//�\���󋵁F�u�w�U�������v:04
//										  StatusCode.STATUS_GAKUSIN_JYURI,				//�\���󋵁F�u�w�U�󗝁v:06
//										  StatusCode.STATUS_GAKUSIN_FUJYURI,			//�\���󋵁F�u�w�U�s�󗝁v:07
//										  StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,	//�\���󋵁F�u�R��������U�菈����v:08
//										  StatusCode.STATUS_WARIFURI_CHECK_KANRYO,		//�\���󋵁F�u����U��`�F�b�N�����v:09
//										  StatusCode.STATUS_1st_SHINSATYU,				//�\���󋵁F�u1���R�����v:10
//										  StatusCode.STATUS_1st_SHINSA_KANRYO,			//�\���󋵁F�u1���R�������v:11
//										  StatusCode.STATUS_2nd_SHINSA_KANRYO,			//�\���󋵁F�u2���R�������v:12
//										};
//		searchInfo.setJokyoId(str_array);
		
		//�\���󋵏����i�Œ�F�Ɩ��S���҂��Q�Ɖ\�ȃX�e�[�^�X�̂��́j
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// �Đ\�����i�\���󋵂Ɋւ�炸�j	
		//2005/04/27 �ǉ� ��������--------------------------------------------------------
		//���R �s�������̒ǉ�(02:�\�������m�F�A03:�����@�֎�t���A05:�����@�֋p��)
//2006/05/22 �ďC���i�Ɩ��S���҂��Q�Ɖ\�Ȃ��̂����ɖ߂��j
//		statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, null);					//�u�\�������m�F�v:02
//		statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, null);				//�u�����@�֎�t���v:03
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);						//�u�w�U�������v:04
//		statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, null);					//�u�����@�֋p���v:05
		//�ǉ� �����܂�-------------------------------------------------------------------
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);							//�u�w�U�󗝁v:06
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);						//�u�w�U�s�󗝁v:07
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);				//�u�R��������U�菈����v:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);					//�u����U��`�F�b�N�����v:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);							//�u1���R�����v:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);						//�u1���R�������v:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);						//�u2���R�������v:12
		searchInfo.setStatusSearchInfo(statusInfo);
		
		//���я�
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);		//����ID��
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);		//�\���ԍ���
//2007/03/28 �����F�@�ǉ��@��������
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO); 		//�V�X�e����t�ԍ���
//2007/03/28 �����F�@�ǉ��@�����܂�
		
		//�y�[�W����
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//�������s
		Page result = null;
		try{
			result = getSystemServise(
						IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).searchApplication(
						container.getUserInfo(),
						searchInfo);
		}catch(NoDataFoundException e){
			throw e;
		}

		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
	
	
}
