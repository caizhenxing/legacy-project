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
package jp.go.jsps.kaken.web.gyomu.kanren;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �֘A����̌����Ҍ����A�N�V�����N���X�B
 * �֘A���쌤���҈ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
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
		KanrenSearchForm searchForm = (KanrenSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		 
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
		  
		  
/*		  
		  //�N�x
		  if(!searchForm.getNendo().equals("")){		
			  searchInfo.setNendo(searchForm.getNendo());
		  }
		  //��
		  if(!searchForm.getKaisu().equals("")){	
			  searchInfo.setKaisu(searchForm.getKaisu());
		  }
		  //�\���Җ��i�����j-��
		  if(!searchForm.getNameKanjiSei().equals("")){			
			  searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		  }
		  //�\���Җ��i�����j-��
		  if(!searchForm.getNameKanjiMei().equals("")){
			  searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		  }	
		  //�\���ҁi�ӂ肪�ȁj�|��
		  if(!searchForm.getNameKanaMei().equals("")){
			  searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		  }	
		  //�\���ҁi�ӂ�Ȃ��j�|��
		  if(!searchForm.getNameKanaSei().equals("")){
			  searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		  }		
		  	
		  //�\���Җ��i���[�}���j-��
		  if(!searchForm.getNameRoSei().equals("")){
			  searchInfo.setNameRoSei(searchForm.getNameRoSei());
		  }
		  //�\���Җ��i���[�}���j-��
		  if(!searchForm.getNameRoMei().equals("")){
			  searchInfo.setNameRoMei(searchForm.getNameRoMei());
		  }
		  //�\���ԍ�
		  if(!searchForm.getUketukeNo().equals("")){		
			  searchInfo.setUketukeNo(searchForm.getUketukeNo());		
		  }
		  //�n���̋敪
		  if(!searchForm.getKeiName().equals("")){
			  searchInfo.setKeiName(searchForm.getKeiName());
		  }	
*/		  

		//�\���󋵏����i�Œ�F�Ɩ��S���҂��Q�Ɖ\�ȃX�e�[�^�X�̂��́j
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU}); // �Đ\�����i�\���󋵂Ɋւ�炸�j 
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);      //�u�w�U�������v:04
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);       //�u�w�U�󗝁v:06
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);      //�u�w�U�s�󗝁v:07
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    //�u�R��������U�菈����v:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);     //�u����U��`�F�b�N�����v:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);       //�u1���R�����v:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);      //�u1���R�������v:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);      //�u2���R�������v:12
		searchInfo.setStatusSearchInfo(statusInfo);
		
		//���я�
		//	searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);		//����ID��
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);		//�\���ԍ���
		
		//�y�[�W����
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//�������s
		Page result =
			getSystemServise(
				IServiceName.KANRENBUNYA_MAINTENANCE_SERVICE).search(
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
