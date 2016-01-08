/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchListAction.java
 *    Description : �R���󋵌����A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/27    V1.0        Admin          �V�K�쐬
 *    2006/07/03    V1.1        DIS.dyh        �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R���󋵌����A�N�V�����N���X�B
 * �R���󋵈ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
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
		
		//-----�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//���������̎擾
		ShinsaJokyoSearchForm searchForm = (ShinsaJokyoSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinsaJokyoSearchInfo searchInfo = new ShinsaJokyoSearchInfo();
		
//		searchInfo.setValues(searchForm.getValues());
		searchInfo.setShinsainNo(searchForm.getShinsainNo());           //�R�����ԍ�
		searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());       //�R�������i����-���j
		searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());       //�R�������i����-���j
		searchInfo.setNameKanaSei(searchForm.getNameKanaSei());         //�R�������i�t���K�i-���j
		searchInfo.setNameKanaMei(searchForm.getNameKanaMei());         //�R�������i�t���K�i-���j
// 2006/07/03 dyh add start
        searchInfo.setShozokuName(searchForm.getShozokuName());         //�R�������������@�֖�
// 2006/07/03 dyh add end
		searchInfo.setShozokuCd(searchForm.getShozokuCd());             //����ҏ��������@�֔ԍ�
		searchInfo.setNendo(searchForm.getNendo());                     //�N�x
		searchInfo.setKaisu(searchForm.getKaisu());                     //��
		searchInfo.setKeiName(searchForm.getKeiName());                 //�n���̋敪
		searchInfo.setRigaiJokyo(searchForm.getRigaiJokyo());			//���Q�֌W���͊����󋵁@2007/5/8�ǉ�
		searchInfo.setShinsaJokyo(searchForm.getShinsaJokyo());         //�R����
		searchInfo.setLoginDate(searchForm.getLoginDate());				//�ŏI���O�C�����@�ǉ�2005/10/24
		searchInfo.setRigaiKankeisha(searchForm.getRigaiKankeisha());	//���Q�֌W�Ғǉ�2005/10/25
		searchInfo.setSeiriNo(searchForm.getSeiriNo());					//�����ԍ��i�w�n�p�j��ǉ�	2005/11/2
		//2005.11.03 iso �\��������ǉ�
		searchInfo.setHyojiHoshikiShinsaJokyo(searchForm.getHyojiHoshikiShinsaJokyo());
		//2005.05.18 iso �A�N�Z�X�Ǘ������Ƌ敪������CD�ɕύX
		//�`�F�b�N�{�b�N�X�̎��ƃR�[�h���X�g���Z�b�g
		if(!searchForm.getValues().isEmpty()){
			searchInfo.setValues(searchForm.getValues());	
		}else{
			//����U��̌����ł́A�����ŐR���S���敪���Z�b�g���Ă��邪�A
			//�R���󋵊Ǘ��ł́AShinsaJokyoKakunin�N���X�ōs���Ă���B
			//���삪�Ⴄ�̂Œ��ӁB
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setValues(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}

		//�y�[�W����
		searchInfo.setStartPosition(searchForm.getStartPosition());
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());

		//�������s
		Page result =
			getSystemServise(
				IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE).search(
				container.getUserInfo(),
				searchInfo);

		//2005.11.07 iso �����ꗗ�Ŏg��Ȃ��̂ŁAvm�Ŏ��s
//		List list = result.getList();
//		for(int i = 0; i < result.getSize(); i++) {
//			HashMap hashMap = (HashMap)list.get(i);
//			//��1��͕\�����Ȃ��̂ŋ�ɂ���
//			String kaisu = hashMap.get("KAISU").toString();
//			if(kaisu != null && kaisu.equals("1")) {
//				hashMap.put("KAISU", null);
//			}
//		}

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
