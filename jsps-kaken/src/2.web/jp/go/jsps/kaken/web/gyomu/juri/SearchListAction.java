/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchListAction.java
 *    Description : �󗝓o�^�Ώې\�����ꗗ�\���A�N�V�����N���X
 *
 *    Author      : 
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0                       �V�K�쐬
 *    2006/06/02    V1.1        DIS.jiangZX    �C���i����������ύX�j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝓o�^�Ώې\�����ꗗ�\���A�N�V�����N���X�B
 * �󗝓o�^�Ώې\�����ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:47 $"
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
        JuriSearchForm searchForm = (JuriSearchForm)form;
        //------�L�����Z����        
        if (isCancelled(request)) {
            searchForm.setStartPosition(0); 
            return forwardCancel(mapping);
        }

		//���������̎擾
// update start dyh 2006/05/30
//		BaseSearchForm searchForm = (BaseSearchForm)form;
        

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        searchInfo.setJigyoCd(searchForm.getJigyoCd());
        searchInfo.setShozokuCd(searchForm.getShozokuCd());
// 2006/06/02 jzx�@add start
        //���������̏��������@�֖�(����)
        searchInfo.setShozokNm(searchForm.getShozokuNm());
// 2006/06/02 jzx�@add end       

		//�w�肳��Ă��Ȃ��ꍇ�́A�i�Ɩ��S���҂Ȃ�΁j�������S�����鎖�ƃR�[�h�̂�
		if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
			GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
			searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
		}

		//�\���󋵂��Z�b�g
		//�\���󋵏����i�Œ�F�Ɩ��S���҂��Q�Ɖ\�ȃX�e�[�^�X�̂��́j
//		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//		statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// �Đ\�����i�\���󋵂Ɋւ�炸�j	
//		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);						//�u�w�U�������v:04
//		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);							//�u�w�U�󗝁v:06
//		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);						//�u�w�U�s�󗝁v:07
//		searchInfo.setStatusSearchInfo(statusInfo);

// 2006/06/02 by jzx�@update start �����F���������ύX
//		//2005/04/20 �ǉ� ��������---------------------------------------------
//		//���R ��Ղ̏ꍇ�͕\�����Ȃ��悤�ɂ��邽��
//        // ���������̎��Ƌ敪
//		ArrayList array = new ArrayList();
//      2006/06/02 by jzx�@update start
//      array.add("1");//�w�p�n���i�����j
//      array.add("2");//�w�p�n���i����j
//      array.add("3");//���ʐ��i�����j
//		searchInfo.setJigyoKubun(array);
        // ���������̒S�����ƃR�[�h��ݒ�
        List array = new ArrayList();
        List jigyoCdNms = searchForm.getJigyoNmList();
        for(int i = 0;i < jigyoCdNms.size(); i++){
            LabelValueBean bean = (LabelValueBean)jigyoCdNms.get(i);
            array.add(bean.getValue());
        }
		searchInfo.setTantoJigyoCd(array);
// 2006/06/02 by jzx�@update end
        //�ǉ� �����܂�--------------------------------------------------------	
        
		//�󗝑O
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
                                                StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);    		//�u�w�U�������v:04
		searchInfo.setStatusSearchInfo(statusInfo);

		//�\�[�g�����Z�b�g
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);	//--����ID��
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);	//--�\���ԍ���

		//�y�[�W����
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());

		//�������s
		Page result = null;
		try{
			result =
				getSystemServise(
					IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchApplication(
					container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
            errors.add("",new ActionError("errors.5002"));
		}

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