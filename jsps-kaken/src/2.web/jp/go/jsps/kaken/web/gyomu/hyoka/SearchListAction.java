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
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �]����񌟍��A�N�V�����N���X�B
 * �]�����ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
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

		//���[�U���̎擾
		UserInfo userInfo = container.getUserInfo();

		//���������̎擾
		HyokaSearchForm searchForm = (HyokaSearchForm)form;
		
		//�R����6�l����1���Ɛ�����̂œ��ꐧ�䂪�K�v�B
		//�R�����g�ꗗ�ŁA�\�������E�ő吔�������l(BaseSearchForm�̒l)�̎��́A6�{����B
		BaseSearchForm baseSearchForm = new BaseSearchForm();
		if(searchForm.getJigyoKubun().equals("4") && searchForm.getHyojiHoshikiKiban().equals("2")	//�R�����g���X�g�̎�
				&& searchForm.getPageSize() == baseSearchForm.getPageSize()) {						//�����l�̎�
			searchForm.setPageSize(baseSearchForm.getPageSize() * 6);
			searchForm.setMaxSize(baseSearchForm.getMaxSize() * 6);
		}
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		HyokaSearchInfo searchInfo = new HyokaSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//2005.05.18 iso �A�N�Z�X�Ǘ������Ƌ敪������CD�ɕύX
		//���Ƃ�I�����Ă���ꍇ�́A�I����������CD��
		//�I�����Ă��Ȃ��ꍇ�́A�S������CD���Z�b�g����B
		//�ύX�O�Ɏg���Ă���HyokaSearchInfo��jigyoCd�͖����������B
		//���w�n�͎���CD���Z�b�g���Ȃ��B
		if(searchForm.getJigyoCd() != null && !searchForm.getJigyoCd().equals("")){
			searchInfo.setValues(searchForm.getJigyoCd());	
		}else{
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setValues(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}

		//�������s
		Page result =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchHyokaList(
				container.getUserInfo(),
				searchInfo);

		List list = result.getList();
		for(int i = 0; i < result.getSize(); i++) {
			HashMap hashMap = (HashMap)list.get(i);
			//��1��͕\�����Ȃ��̂ŋ�ɂ���
			String kaisu = hashMap.get("KAISU").toString();
			if(kaisu != null && kaisu.equals("1")) {
				hashMap.put("KAISU", null);
			}
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
