/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
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
 * �\���ҏ�񌟍��A�N�V�����N���X�B
 * �\���ҏ��ꗗ��ʂ�\������B
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
		
		//�����@�֏��
		UserInfo userInfo = container.getUserInfo();
		ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
		if(shozokuInfo == null){
			throw new ApplicationException(
				"�����@�֏����擾�ł��܂���ł����B",
				new ErrorInfo("errors.application"));
		}
		ShinseiSearchForm searchForm = (ShinseiSearchForm)form;

		//�����������Z�b�g����
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		//�����@�փR�[�h
		searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());
		//���Ɩ�
		if(!searchForm.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(searchForm.getJigyoCd());
		}
		//�N�x
		if(!searchForm.getNendo().equals("")){
			searchInfo.setNendo(searchForm.getNendo());
		}
		//��
		if(!searchForm.getKaisu().equals("")){
			searchInfo.setKaisu(searchForm.getKaisu());
		}
		//�\���Җ��i�������E���j
		if(!searchForm.getNameKanjiSei().equals("")){
			searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		}
		//�\���Җ��i�������E���j
		if(!searchForm.getNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		}
		//�\���Җ��i���[�}���E���j
		if(!searchForm.getNameRoSei().equals("")){
			searchInfo.setNameRoSei(searchForm.getNameRoSei());
		}
		//�\���Җ��i���[�}���E���j
		if(!searchForm.getNameRoMei().equals("")){
			searchInfo.setNameRoMei(searchForm.getNameRoMei());
		}
		//�\���󋵏���
		String jokyo_id = searchForm.getJokyoId();
		if(jokyo_id.equals("0")){
//			//�������I��
//			String[] str_array = new String[]{StatusCode.STATUS_SHINSEISHO_MIKAKUNIN,
//											  StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,
//											  StatusCode.STATUS_GAKUSIN_SHORITYU,
//											  StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,
//											  StatusCode.STATUS_GAKUSIN_JYURI,
//											  StatusCode.STATUS_GAKUSIN_FUJYURI,
//											  StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,
//											  StatusCode.STATUS_WARIFURI_CHECK_KANRYO,
//											  StatusCode.STATUS_1st_SHINSATYU,
//											  StatusCode.STATUS_1st_SHINSA_KANRYO,
//											  StatusCode.STATUS_2nd_SHINSA_KANRYO};
//	
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// �Đ\�����i�\���󋵂Ɋւ�炸�j 
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, null);				//�u�\�������m�F�v:02
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, null);				//�u�����@�֎�t���v:03
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);					//�u�w�U�������v:04
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, null);					//�u�����@�֋p���v:05
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);						//�u�w�U�󗝁v:06
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);						//�u�w�U�s�󗝁v:07
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);			//�u�R��������U�菈����v:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);				//�u����U��`�F�b�N�����v:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);						//�u1���R�����v:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);					//�u1���R�������v:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);					//�u2���R�������v:12
// 2006/6/15 �����@�ǉ��@�u21�A22�A23�A24�v ��������----------------------
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN, null);              //�u�̈��\�Ҋm�F���v:21
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA, null);               //�u�̈��\�ҋp���v:22
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, null);          //�u�̈��\�Ҋm��ς݁v:23
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE, null);              //�u�̈��\�ҏ��������@�֎�t���v:24
// 2006/6/15 �����@�ǉ��@�u21�A22�A23�A24�v�@�����܂�----------------------
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("1")){
			//�\���Җ��m�F
//			String[] str_array = new String[]{StatusCode.STATUS_SHINSEISHO_MIKAKUNIN};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};	//�Đ\���t���O�i�����l�A�Đ\�����j
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, saishinseiArray);		//�u�\�������m�F�v:02
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("2")){
			//�����@�֎�t��
//			String[] str_array = new String[]{StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};	//�Đ\���t���O�i�����l�A�Đ\�����j
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, saishinseiArray);	//�u�����@�֎�t���v:03
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("3")){
			//�w�U������
//			String[] str_array = new String[]{StatusCode.STATUS_GAKUSIN_SHORITYU};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);			//�u�w�U�������v:04
			searchInfo.setStatusSearchInfo(statusInfo);
		} else if (jokyo_id.equals("4")) {
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);			//�u�w�U�󗝁v:06
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, saishinseiArray);//�u�R��������U�菈����v:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, saishinseiArray);	//�u����U��`�F�b�N�����v:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, saishinseiArray);			//�u1���R�����v:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, saishinseiArray);		//�u1���R�������v:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);		//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);		
		}else if(jokyo_id.equals("8")){
			//�s��
//			String[] str_array = new String[]{StatusCode.STATUS_GAKUSIN_FUJYURI};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);			//�u�w�U�s�󗝁v:07
			searchInfo.setStatusSearchInfo(statusInfo);
//		}else if(jokyo_id.equals("4")){
//			//�̑�
////			int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU};
////			searchInfo.setKekka2(int_array);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//
//			 int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU};									//�R�����ʁF�u�̑��v:1
//			 searchInfo.setKekka2(int_array);
//		}else if(jokyo_id.equals("5")){
//			//�s�̑�
////			int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};
////			searchInfo.setKekka2(int_array);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//
//			 int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};									//�R�����ʁF�u�s�̑��v:8
//			 searchInfo.setKekka2(int_array);
//		}else if(jokyo_id.equals("6")){
//			//�̑����
////			int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};
////			searchInfo.setKekka2(int_array);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//
//			 int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};									//�R�����ʁF�u�̗p���v:2
//			 searchInfo.setKekka2(int_array);
//		}else if(jokyo_id.equals("7")){
//			//�⌇
////			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//�u2���R�������v:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);										//�R�����ʁF�u�⌇1�`5�v
		}else if(jokyo_id.equals("9")){
			//�p��
//			String[] str_array = new String[]{StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};	//�Đ\���t���O�i�����l�A�Đ\�����j
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, saishinseiArray);		//�u�����@�֋p���v:05
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("10")){
			//�C���˗�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	//�Đ\�����i�\���󋵂Ɋւ�炸�j 
//			String[] saishinseiArray = new String[]{StatusCode.STATUS_GAKUSIN_SHORITYU};		//�Đ\���t���O�i�Đ\�����j
//			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, saishinseiArray);		//�u�\�������m�F�v:02
//			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, saishinseiArray);	//�u�����@�֎�t���v:03
//			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);			//�u�w�U�������v:04
//			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, saishinseiArray);		//�u�����@�֋p���v:05
//			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);			//�u�w�U�󗝁v:06
//			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);			//�u�w�U�s�󗝁v:07
			searchInfo.setStatusSearchInfo(statusInfo);
//         2006/7/31 zjp�@�ǉ��@�u21�A22�A23�A24�v ��������----------------------
        }else if(jokyo_id.equals("11")){
            //�̈��\�Ҋm�F��
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN, null);         //�u�̈��\�Ҋm�F���v:21
            searchInfo.setStatusSearchInfo(statusInfo);
        }else if(jokyo_id.equals("12")){
            //�̈��\�Ҋm���
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, null);     //�u�̈��\�Ҋm��ρv:23
            searchInfo.setStatusSearchInfo(statusInfo);
        }else if(jokyo_id.equals("13")){
            //�̈��\�ҋp��
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA, null);          //�u�̈��\�ҋp���v:22
            searchInfo.setStatusSearchInfo(statusInfo);
        }else if(jokyo_id.equals("14")){
            //�̈��\�ҏ��������@�֎�t��
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE, null);         //�u�̈��\�ҏ��������@�֎�t���v:24
            searchInfo.setStatusSearchInfo(statusInfo);
        }
//      2006/7/31 zjp�@�ǉ��@�u21�A22�A23�A24�v�@�����܂�----------------------       
        
		//�쐬��(From)
		if(!searchForm.getSakuseiDateFromYear().equals("")){
			searchInfo.setSakuseiDateFrom(searchForm.getSakuseiDateFromYear()
                    + "/" + searchForm.getSakuseiDateFromMonth()
                    + "/" + searchForm.getSakuseiDateFromDay());
		}
		//�쐬��(To)
		if(!searchForm.getSakuseiDateToYear().equals("")){
			searchInfo.setSakuseiDateTo(searchForm.getSakuseiDateToYear()
                    + "/" + searchForm.getSakuseiDateToMonth()
                    + "/" + searchForm.getSakuseiDateToDay());
		}
		//�����@�֏��F��(From)
		if(!searchForm.getShoninDateFromYear().equals("")){
			searchInfo.setShoninDateFrom(searchForm.getShoninDateFromYear()
                    + "/" + searchForm.getShoninDateFromMonth()
                    + "/" + searchForm.getShoninDateFromDay());
		}
		//�����@�֏��F��(To)
		if(!searchForm.getShoninDateToYear().equals("")){
			searchInfo.setShoninDateTo(searchForm.getShoninDateToYear()
                    + "/" + searchForm.getShoninDateToMonth()
                    + "/" + searchForm.getShoninDateToDay());
		}
		//�\������
		if(searchForm.getHyojiHoshiki().equals("2")){
			//�\���Җ��̏ꍇ�͏����@��CD���\����ID���\���ԍ����ŕ\������
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD);
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID);
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);		//2007/3/27�ǉ�
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);
		}else{
			//������ږ��̏ꍇ�͎���ID���\���ԍ����V�X�e����t�ԍ����ŕ\������
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);
//2007/03/28 �����F�@�ǉ��@��������
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);
//2007/03/28 �����F�@�ǉ��@�����܂�
		}
		//�\���Җ��i�t���K�i�E���j
		if(!searchForm.getNameKanaSei().equals("")){
			searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		}
		//�\���Җ��i�t���K�i�E���j
		if(!searchForm.getNameKanaMei().equals("")){
			searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		}
		//�����Ҕԍ�
		if(!searchForm.getKenkyuNo().equals("")){
			searchInfo.setKenkyuNo(searchForm.getKenkyuNo());
		}
		//���ǃR�[�h
		if(!searchForm.getBukyokuCd().equals("")){
			searchInfo.setBukyokuCd(searchForm.getBukyokuCd());
		}
		//2004/12/3update
		//�u��Ռ����v�̐\�����\������Ȃ��悤�Ɉꎞ�Ή�
		//���Ƌ敪�̏ꍇ�̂ݕ\��
		List jigyoKubunList = new ArrayList();
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);	//�u1:�w�p�n��������i���E���j�v
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO);		//�u2:�w�p�n��������i��W���v
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_TOKUSUI);			//�u3:���ʐ��i�����v
		
		// 2005/04/12 �ǉ� ��������-------------------------------------
		//�u��Ռ����v�̏ꍇ���\����\�����邽�ߏ�����ǉ�		
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);				//�u4:��Ռ����v
		//	�ǉ� �����܂�

// 20050606 Start
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);			//�u5:����̈挤���v
// Horikoshi End
//add start dyh 2006/2/20 �����F���Ƌ敪�̒ǉ�
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_WAKATESTART);        //�u6:���X�^�[�g�A�b�v �v
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);        //�u7:���ʌ������i��v
//add end dyh 2006/2/20
		searchInfo.setJigyoKubun(jigyoKubunList);	

		//�y�[�W����
		searchInfo.setStartPosition(searchForm.getStartPosition());
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());

		//�T�[�o�T�[�r�X�̌Ăяo���i�����󋵈ꗗ�y�[�W���擾�j
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		Page page = servise.searchApplication(userInfo, searchInfo);
        
        //ADD START LIUYI 2006/07/03 �d�l�ύX CSV�o�͂̌��������̎擾
        container.setShinseiSearchInfo(searchInfo);
        //ADD END LIUYI 2006/07/03

		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO, page);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}