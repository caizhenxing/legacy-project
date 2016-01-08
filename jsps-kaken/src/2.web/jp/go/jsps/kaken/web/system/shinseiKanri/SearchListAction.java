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
package jp.go.jsps.kaken.web.system.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\����񌟍��A�N�V�����N���X�B
 * �\�����ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
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
		ShinseiSearchForm searchForm = (ShinseiSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		
		//���ƃR�[�h
		if(!searchForm.getJigyoCd().equals("")){	
			searchInfo.setJigyoCd(searchForm.getJigyoCd());	//�w�肳��Ă����ꍇ�͓��Y���ƃR�[�h�̂�
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
		//�\����ID
		if(!searchForm.getShinseishaId().equals("")){			
			searchInfo.setShinseishaId(searchForm.getShinseishaId());
		}
		//�\���Ҏ����i������-���j
		if(!searchForm.getNameKanjiSei().equals("")){			
			searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		}
		//�\���Ҏ����i������-���j
		if(!searchForm.getNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		}		
		//�\���Ҏ����i�t���K�i-���j
		if(!searchForm.getNameKanaSei().equals("")){			
			searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		}
		//�\���Ҏ����i�t���K�i-���j
		if(!searchForm.getNameKanaMei().equals("")){
			searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		}
		//�\���Ҏ����i���[�}��-���j
		if(!searchForm.getNameRoSei().equals("")){
			searchInfo.setNameRoSei(searchForm.getNameRoSei());
		}
		//�\���Ҏ����i���[�}��-���j
		if(!searchForm.getNameRoMei().equals("")){
			searchInfo.setNameRoMei(searchForm.getNameRoMei());
		}
		//�����@�փR�[�h
		if(!searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
		//�����Ҕԍ�
		if(!searchForm.getKenkyuNo().equals("")){
			searchInfo.setKenkyuNo(searchForm.getKenkyuNo());
		}
		//�n���̋敪�ԍ�
//		if(!searchForm.getKeiName().equals("")){
//			searchInfo.setKeiName(searchForm.getKeiName());
//		}
		//���E�̊ϓ_�ԍ�
//		if(!searchForm.getKantenNo().equals("")){
//			searchInfo.setKantenNo(searchForm.getKantenNo());
//		}
		//�\���󋵏���
		String jokyo_id = searchForm.getJokyoId();
		if("0".equals(jokyo_id)){
			//�������I��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// �Đ\�����i�\���󋵂Ɋւ�炸�j 
			statusInfo.addOrQuery(StatusCode.STATUS_SAKUSEITHU, null);     				 		//�u�쐬���v:01
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, null);     				//�u�\���Җ��m�F�v:02
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, null);     			//�u�����@�֎�t���v:03
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);     				 	//�u�w�U�������v:04
			//2005.09.21 iso �V�X�e���Ǘ��җp�Ɍ����������C��(�Ɩ��S���҂Ƃ̓X�e�[�^�X���Ⴄ�̂ł��̂܂܎g���Ȃ�)
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, null);     				//�u�����@�֋p���v:05
			
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);       					//�u�w�U�󗝁v:06
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);      					//�u�w�U�s�󗝁v:07
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    			//�u�R��������U�菈����v:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);     			//�u����U��`�F�b�N�����v:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);       					//�u1���R�����v:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);      				//�u1���R�������v:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);      				//�u2���R�������v:12
//           2006/7/31 zjp�@�ǉ��@�u21�A22�A23�A24�v ��������----------------------
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN, null);                 //�u�̈��\�Ҋm�F���v:21
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA, null);                  //�u�̈��\�ҋp���v:22
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, null);             //�u�̈��\�Ҋm��ς݁v:23
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE, null);                 //�u�̈��\�ҏ��������@�֎�t���v:24
//          2006/7/31 zjp�@�ǉ��@�u21�A22�A23�A24�v �����܂�----------------------
			searchInfo.setStatusSearchInfo(statusInfo);	
		}else if("1".equals(jokyo_id)){
			//�쐬��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT};    				 //�Đ\���t���O�i�����l�j
			statusInfo.addOrQuery(StatusCode.STATUS_SAKUSEITHU, saishinseiArray);    						 //�u�쐬���v:01
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("2".equals(jokyo_id)){
			//�\���Җ��m�F
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			//2005.09.21 iso �V�X�e���Ǘ��҂͍Đ\�������\������
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT};				     //�Đ\���t���O�i�����l�j
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};		//�Đ\���t���O�i�����l�A�Đ\�����j
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, saishinseiArray);				//�u�\���Җ��m�F�v:02
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("3".equals(jokyo_id)){
			//�����@�֎�t��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			//2005.09.21 iso �V�X�e���Ǘ��҂͍Đ\�������\������
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT};     				//�Đ\���t���O�i�����l�j
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};     	//�Đ\���t���O�i�����l�A�Đ\�����j
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, saishinseiArray);				//�u�����@�֎�t���v:03
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("4".equals(jokyo_id)){
			//�󗝑O
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);					//�u�w�U�������v:04
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("5".equals(jokyo_id)){
			//�����@�֋p��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			//2005.09.21 iso �V�X�e���Ǘ��҂͍Đ\�������\������
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT};   				  //�Đ\���t���O�i�����l�j
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};		//�Đ\���t���O�i�����l�j
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, saishinseiArray);					//�u�����@�֋p���v:05
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("6".equals(jokyo_id)){
			//�󗝍ς�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);      					//�u�w�U�󗝁v:06
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("7".equals(jokyo_id)){
			//�s��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};    //�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);      				//�u�w�U�s�󗝁v:07
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("8".equals(jokyo_id)){
			//�C���˗�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU}); 			// �Đ\�����i�\���󋵂Ɋւ�炸�j
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("9".equals(jokyo_id)){
			//�P���R����
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, saishinseiArray);						//�u1���R�����v:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, saishinseiArray);					//�u1���R�������v:11
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("10".equals(jokyo_id)){
			//�Q���R������
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);    				//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);
//			int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU,		//�R�����ʁF�u�̑��v:1
//											StatusCode.KEKKA2_FUSAITAKU,	//�R�����ʁF�u�̑��v:1
//											
//										};    									
//			searchInfo.setKekka2(int_array);
//		}else if("7".equals(jokyo_id)){
//			//�s�̑�
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//			int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};    								//�R�����ʁF�u�s�̑��v:8
//			searchInfo.setKekka2(int_array);
//		}else if("8".equals(jokyo_id)){
//			//�̗p���
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//			int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};									//�R�����ʁF�u�̗p���v:2
//			searchInfo.setKekka2(int_array);
//		}else if("9".equals(jokyo_id)){
//			//�⌇
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);											//�R�����ʁF�u�⌇1�`5�v
//           2006/7/31 zjp�@�ǉ��@�u21�A22�A23�A24�v ��������----------------------
            }else if(jokyo_id.equals("11")){
                //�̈��\�Ҋm�F��
                CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
                statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN, null);         //�u�̈��\�Ҋm�F���v:21
                searchInfo.setStatusSearchInfo(statusInfo);
            }else if(jokyo_id.equals("12")){
                //�̈��\�Ҋm���
                CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
                statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, null);         //�u�̈��\�Ҋm��ρv:23
                searchInfo.setStatusSearchInfo(statusInfo);
            }else if(jokyo_id.equals("13")){
                //�̈��\�ҋp��
                CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
                statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA, null);         //�u�̈��\�ҋp���v:22
                searchInfo.setStatusSearchInfo(statusInfo);
            }else if(jokyo_id.equals("14")){
                //�̈��\�ҏ��������@�֎�t��
                CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
                statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE, null);         //�u�̈��\�ҏ��������@�֎�t���v:24
                searchInfo.setStatusSearchInfo(statusInfo);
            }
//        2006/7/31 zjp�@�ǉ��@�u21�A22�A23�A24�v�@�����܂�----------------------     

		//�쐬���i�J�n���j
		if(!searchForm.getSakuseiDateFromYear().equals("")){	
			searchInfo.setSakuseiDateFrom(								
									searchForm.getSakuseiDateFromYear() + "/" +
									searchForm.getSakuseiDateFromMonth() + "/" +
									searchForm.getSakuseiDateFromDay()
									);
		}
		//�쐬���i�I�����j
		if(!searchForm.getSakuseiDateToYear().equals("")){
			searchInfo.setSakuseiDateTo(									
									searchForm.getSakuseiDateToYear() + "/" +
									searchForm.getSakuseiDateToMonth()+ "/" +
									searchForm.getSakuseiDateToDay()
									);
		}
		//�����@�֏��F���i�J�n���j
		if(!searchForm.getShoninDateFromYear().equals("")){
			searchInfo.setShoninDateFrom(									
									searchForm.getShoninDateFromYear() + "/" +
									searchForm.getShoninDateFromMonth() + "/" +
									searchForm.getShoninDateFromDay()
									);
		}
		//�����@�֏��F���i�I�����j
		if(!searchForm.getShoninDateToYear().equals("")){
			searchInfo.setShoninDateTo(										
									searchForm.getShoninDateToYear() + "/" +
									searchForm.getShoninDateToMonth()+ "/" +
									searchForm.getShoninDateToDay()
									);
		}
		//�\���ԍ�
		if(!searchForm.getUketukeNo().equals("")){		
			searchInfo.setUketukeNo(searchForm.getUketukeNo());		
		}
		//�זڔԍ�
		if(!searchForm.getBunkaSaimokuCd().equals("")){		
			searchInfo.setBunkasaimokuCd(searchForm.getBunkaSaimokuCd());		
		}
		//�폜�t���O
		if(!searchForm.getDelFlg().equals("")){		
			if(searchForm.getDelFlg().equals("1")){
				//�폜�t���O�u1:�폜�f�[�^�������v�̏ꍇ�A�폜�t���O�u0�v���Z�b�g
				searchInfo.setDelFlg(new String[]{ShinseiSearchInfo.NOT_DELETE_FLG});
			}else if(searchForm.getDelFlg().equals("2")){
				//�폜�t���O�u2:�폜�f�[�^���܂ށv�̏ꍇ�A�폜�t���O�u0�v�u1�v���Z�b�g
				searchInfo.setDelFlg(new String[]{ShinseiSearchInfo.NOT_DELETE_FLG, ShinseiSearchInfo.DELETE_FLG});			
			}
		}
		
		if("1".equals(searchForm.getHyojiSentaku())){									//�\���`���i������ږ��ɕ\���j
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
//2007/03/28 �����F�@�ǉ��@��������
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  //--�V�X�e����t�ԍ���
//2007/03/28 �����F�@�ǉ��@�����܂�
		}else if("2".equals(searchForm.getHyojiSentaku())){								//�\���`���i�\���Җ��ɕ\���j
//2007/03/12 �����F�@�X�V�@��������
            searchInfo.setTablePrefix("A");
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_NAME_KANA_SEI);       //--�\���Җ��i�J�i�|���j��             
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_NAME_KANA_MEI);       //--�\���Җ��i�J�i�|���j��    
//2007/03/28 �����F�@�ǉ��@��������
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID);                  //--�\����ID��
//2007/03/28 �����F�@�ǉ��@�����܂�
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
            searchInfo.setTablePrefix("");
//2007/03/12 �����F�@�X�V�@�����܂�    
		}
		
		//�y�[�W����
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//�������s
		Page result =
				getSystemServise(
					IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchApplication(
					container.getUserInfo(),
					searchInfo);

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
