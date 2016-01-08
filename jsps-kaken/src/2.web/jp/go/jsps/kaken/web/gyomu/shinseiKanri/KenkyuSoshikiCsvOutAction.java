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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����g�DCSV�o�̓A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KenkyuSoshikiCsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class KenkyuSoshikiCsvOutAction extends BaseAction {
	
	/**
	 * CSV�t�@�C�����̐ړ����B
	 */
	public static final String filename = "SOSHIKIHYO_";

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
		if(!searchForm.getKeiName().equals("")){
			searchInfo.setKeiName(searchForm.getKeiName());
		}
		//���E�̊ϓ_�ԍ�
		if(!searchForm.getKantenNo().equals("")){
			searchInfo.setKantenNo(searchForm.getKantenNo());
		}
		//�\���󋵏���
		String jokyo_id = searchForm.getJokyoId();
		if("0".equals(jokyo_id)){
			//�������I��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// �Đ\�����i�\���󋵂Ɋւ�炸�j 
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);     				 	//�u�w�U�������v:04
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);       					//�u�w�U�󗝁v:06
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);      					//�u�w�U�s�󗝁v:07
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    			//�u�R��������U�菈����v:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);     			//�u����U��`�F�b�N�����v:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);       					//�u1���R�����v:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);      				//�u1���R�������v:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);      				//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);	
		}else if("1".equals(jokyo_id)){
			//�󗝑O
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);    				 //�u�w�U�������v:04
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("2".equals(jokyo_id)){
			//�󗝍ς�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);      					//�u�w�U�󗝁v:06
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("3".equals(jokyo_id)){
			//�s��
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};    //�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);      				//�u�w�U�s�󗝁v:07
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("4".equals(jokyo_id)){
			//�C���˗�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU}); 			// �Đ\�����i�\���󋵂Ɋւ�炸�j
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("5".equals(jokyo_id)){
			//�R����
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, saishinseiArray);						//�u1���R�����v:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, saishinseiArray);					//�u1���R�������v:11
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("6".equals(jokyo_id)){
			//�̑�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);    				//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);
			int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU};    									//�R�����ʁF�u�̑��v:1
			searchInfo.setKekka2(int_array);
		}else if("7".equals(jokyo_id)){
			//�s�̑�
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);
			int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};    								//�R�����ʁF�u�s�̑��v:8
			searchInfo.setKekka2(int_array);
		}else if("8".equals(jokyo_id)){
			//�̗p���
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);
			int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};									//�R�����ʁF�u�̗p���v:2
			searchInfo.setKekka2(int_array);
		}else if("9".equals(jokyo_id)){
			//�⌇
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//�Đ\���t���O�i�����l�A�Đ\���ς݁j
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//�u2���R�������v:12
			searchInfo.setStatusSearchInfo(statusInfo);
			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);											//�R�����ʁF�u�⌇1�`5�v
		}

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
		
		//�����g�D�\�Ɛ\���f�[�^�e�[�u���ŃJ�����������Ԃ邽�߁A�����I�Ƀe�[�u���ړ������Z�b�g����
		searchInfo.setTablePrefix("A");
		
		if("0".equals(searchForm.getHyojiSentaku())){								//�\���`���i������ږ��ɕ\���j
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--�V�X�e����t�ԍ���
		}else if("1".equals(searchForm.getHyojiSentaku())){							//�\���`���i�\���Җ��ɕ\���j
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_NAME_KANA_SEI);		//--�\���Җ��i�J�i�|���j��				
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_NAME_KANA_MEI);		//--�\���Җ��i�J�i�|���j��
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID);                  //--�\����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
		}else if("2".equals(searchForm.getHyojiSentaku())){							//�\���`���i�@�֖��ɕ\���j
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD);						//--�����@�֏�			
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--�V�X�e����t�ԍ���
		}else if("3".equals(searchForm.getHyojiSentaku())){							//�\���`���i�n���̋敪���ɕ\���j
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_KEI_NAME_NO);					//--�n���̋敪�ԍ���
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--�V�X�e����t�ԍ���
		}else if("4".equals(searchForm.getHyojiSentaku())){							//�\���`���i���E�̊ϓ_�ԍ����ɕ\���j
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_KANTEN_NO);						//--���E�̊ϓ_�ԍ���				
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--����ID��
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--�\���ԍ���
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--�V�X�e����t�ԍ���
		}
		//�����g�D�\�̃V�[�P���X����ǉ��@2007/3/29
		searchInfo.setTablePrefix("B");
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SEQ_NO);                  		//--�V�[�P���X����
		//-------��

		//�������s
		List result =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchKenkyuSoshikiCsvData(
				container.getUserInfo(),
				searchInfo);
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
