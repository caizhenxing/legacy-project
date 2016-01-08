/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShoninKyakaListAction.java
 *    Description : ���F�E�p���Ώۉ�����ꗗ��ʂ�\������
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *    2006/06/02    V1.1        DIS.liuYi      �C���i�O��ʓn�����l�͕ύX���邩��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.model.common.IJigyoKubun;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���F�E�p���Ώۉ�����ꗗ�A�N�V�����N���X�B
 * ���F�E�p���Ώۉ�����ꗗ��ʂ�\������B
 */
public class ShoninKyakaListAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
        
//�@add start ly 2006/06/02
        ShinseiSearchForm searchForm = (ShinseiSearchForm)form;
        
        //------�L�����Z������-----------------
        String forwardStr = (String)request.getParameter("goBack");
        if ("goBack".equals(forwardStr)) {
            searchForm.setStartPosition(0);
            return mapping.findForward(forwardStr);
        }
//�@add end ly 2006/06/02

		//�����@�֏��
		UserInfo userInfo = container.getUserInfo();
		ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
		if(shozokuInfo == null){
			throw new ApplicationException(
				"�����@�֏����擾�ł��܂���ł����B",
				new ErrorInfo("errors.application"));
		}
		
		//�����������Z�b�g����
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());//�����@�փR�[�h
		searchInfo.setJokyoId(new String[]{StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU});//�\����ID
		//TODO ���ݕۗ�
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);//�V�X�e����t�ԍ��̏���

		//2005/04/19 �ǉ� ��������---------------------------------------------
		//���R ��Ղ̏ꍇ�͏��F�E�p���ꗗ�ɕ\�����Ȃ��悤�ɂ��邽��

// update start by ly 2006/06/01
//        ArrayList array = new ArrayList();
//        array.add("1");
//        array.add("2");
//        array.add("3");
//        searchInfo.setJigyoKubun(array);
        
        // ���������̎��Ƌ敪��ݒ�
        if (!StringUtil.isBlank(searchForm.getJigyoKbn())){
            // �w�p�n��������A���Ƌ敪�i1,2�j��ݒ�
            if (searchForm.getJigyoKbn().equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
                ArrayList array = new ArrayList();
                array.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
                array.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO);
                searchInfo.setJigyoKubun(array);
            }
            // ���ʐ��i�������A���Ƌ敪�i3�j��ݒ�
            else {
                ArrayList array = new ArrayList();
                array.add(searchForm.getJigyoKbn());
                searchInfo.setJigyoKubun(array);
            }
        }
        // ���������̎��ƃR�[�h��ݒ�-->��Ռ����i�r�j:00031,��Ռ����i�`�j�i��ʁj:00041,
        // ��Ռ����i�`�j�i�C�O�w�p�����j:00043,��Ռ����i�a�j�i��ʁj:00051,
        // ��Ռ����i�a�j�i�C�O�w�p�����j:00053,���ʗ̈挤���i�V�K�̈�j-�����v�撲��:������
        searchInfo.setJigyoCd(searchForm.getJigyoCd());
        
        //�y�[�W����
        searchInfo.setStartPosition(searchForm.getStartPosition());
        searchInfo.setPageSize(searchForm.getPageSize());
        searchInfo.setMaxSize(searchForm.getMaxSize());
// update end by ly 2006/06/01
		//�ǉ� �����܂�--------------------------------------------------------		

		//�T�[�o�T�[�r�X�̌Ăяo���i�����󋵈ꗗ�y�[�W���擾�j
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
//        Page page = servise.searchApplication(userInfo, searchInfo);
		Page page = null;
		try{
			page = servise.searchApplication(userInfo, searchInfo);
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			page = Page.EMPTY_PAGE;
		}

		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO, page);
        
        saveToken(request);
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}