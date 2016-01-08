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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.impl.ShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.vo.SearchInfo;

import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �R���S�����\�����ꗗ�\���O�A�N�V�����N���X�B
 * �R���S�����\���ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: TantoShinseiListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class TantoShinseiListAction extends BaseAction {

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

		//------�L�[���
		String jigyoId = ((ShinsaKekkaSearchForm)form).getJigyoId();        //����ID
		String kekkaTen = ((ShinsaKekkaSearchForm)form).getKekkaTen();      //�����]���i�_���j

//		//2006.06.08 iso �R���S�����ƈꗗ�ł̎��Ɩ��\�������C��
//		String jigyoName = ((ShinsaKekkaSearchForm) form).getJigyoName();   //���Ɩ�
		
//2006/04/18 �ǉ���������		
		String jigyoKubun = ((ShinsaKekkaSearchForm) form).getJigyoKubun();   //���Ƌ敪
		container.getUserInfo().getShinsainInfo().setJigyoKubun(jigyoKubun);
//�ǉ������܂Ł@�c
        
//2006/05/12 �ǉ���������
        String shinsaJokyo = ((ShinsaKekkaSearchForm)form).getShinsaJokyo();            //�R���� 
        
        if(kekkaTen.equals("")){
            if(shinsaJokyo.equals("0")){
                kekkaTen = ShinsaKekkaMaintenance.SHINSAKEKKA_KEKKA_TEN_MI;
                ((ShinsaKekkaSearchForm)form).setKekkaTen(kekkaTen);
            } else if (shinsaJokyo.equals("1")){
                kekkaTen = ShinsaKekkaMaintenance.SHINSAKEKKA_KEKKA_TEN_KANRYOU;
                ((ShinsaKekkaSearchForm)form).setKekkaTen(kekkaTen);
            }
        }
//�c�@�ǉ������܂�
 
		//��՗p�Ή��B�����]���i�_���j���u0�F���ׂāv�̏ꍇ�́ANULL���Z�b�g�i���������O�Ƃ��邽�߁j
		if(kekkaTen.equals("0")){
			kekkaTen = null;
		}
		
		SearchInfo searchInfo = new SearchInfo();
		
		//�y�[�W����
		searchInfo.setPageSize(0);
		searchInfo.setMaxSize(0);
		searchInfo.setStartPosition(0);
				
		//------�L�[�������ɍX�V�f�[�^�擾	
		Map result = 
				getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).
						selectShinsaKekkaTantoList(
										container.getUserInfo(),
										jigyoId,
										kekkaTen,
                                        ShinsaKekkaMaintenance.SINNSA_FLAG,//2006/10/27 �c�@�ǉ�
                                        null,//2006/10/27 �c�@�ǉ�
										searchInfo);

		//2006.06.08 iso �R���S�����ƈꗗ�ł̎��Ɩ��\�������C��
		//���Ə��́Aresult���擾����悤�ύX
//        //���ƃI�u�W�F�N�g�Ɏ��Ə����Z�b�g
//		JigyoKanriInfo jigyoInfo = new JigyoKanriInfo();
//		String kaisu = jigyoId.substring(7, 8);
//		String jigyoCd = jigyoId.substring(2, 7);
//		DateUtil dateUtil = new DateUtil();
//		dateUtil.setCal(Integer.parseInt(jigyoId.substring(0, 2)) + 2000, 4, 1);
//		jigyoInfo.setJigyoId(jigyoId);
//		jigyoInfo.setNendo(dateUtil.getNendo());
//		jigyoInfo.setKaisu(kaisu);
//		jigyoInfo.setJigyoCd(jigyoCd);
//		jigyoInfo.setJigyoName(jigyoName);
//
//		// -----�Z�b�V�����Ɏ��Ə���o�^����B
//		container.setJigyoKanriInfo(jigyoInfo);

		// �������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
