/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriSaveAction.java
 *    Description : �ꊇ�󗝏������s���A�N�V�����B
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ꊇ�󗝏������s���A�N�V����
 * 
 * @author masuo_t
 */
public class IkkatsuJuriSaveAction extends BaseAction {

	/** ��ID��04(�w�U��t��)�̂��̂�\�� */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_SHORITYU		//�w�U��t��
	};
		
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//2005/04/20 �폜 ��������-------------------------------------
		//���R ��������ʕ\���p�Ƀg�[�N���̃`�F�b�N��IkkatsuJuriCheck�ōs���悤�ɕύX��������
		//-----ActionErrors�̐錾�i��^�����j-----
		//ActionErrors errors = new ActionErrors();

		//-----�擾�����g�[�N���������ł���Ƃ�
		//if (!isTokenValid(request)) {
		//	errors.add(
		//		ActionErrors.GLOBAL_ERROR,
		//		new ActionError("error.transaction.token"));
		//	saveErrors(request, errors);
		//	return forwardTokenError(mapping);
		//}
		
		//------�g�[�N���̍폜	
		//resetToken(request);
		//�폜 �����܂�-------------------------------------------------------------------

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//���������̎擾
		CheckListInfo checkInfo = container.getCheckListInfo();
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
	
		//���������̐ݒ�
		if(checkInfo.getJigyoCd() != null && !checkInfo.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(checkInfo.getJigyoCd());
		}
		if(checkInfo.getShozokuCd() != null && !checkInfo.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(checkInfo.getShozokuCd());
		}
		//2005.11.18 iso �����@�֖��������Ă����̂Œǉ�
		if(!StringUtil.isBlank(checkInfo.getShozokuName())){
			searchInfo.setShozokuName(checkInfo.getShozokuName());
		}
	
		//��ID��04(�w�U��t��)�̂��̂�\�� 
		searchInfo.setSearchJokyoId(JIGYO_IDS);
		//20060216  dhy start
		CheckListSearchForm checkForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(checkForm.getJigyoKbn());
		//20060216   end

		//�������s
		Page result = 
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectListData(
				container.getUserInfo(),
				searchInfo);
		if (result == null) {
			return forwardFailure(mapping);
		}
		
		//2005.05.22 iso �ꊇ�󗝂̃p�t�H�[�}���X���P
//		//------����CD�̔z��
//		ArrayList shozokuCdArray = new ArrayList();
//		//------����ID�̔z��
//		ArrayList jigyoIdArray = new ArrayList();
//		//------SYSTEM_NO�̔z��
//		ArrayList systemNoArray = new ArrayList();

		//------����CD�̔z��
		ArrayList shozokuCdArray = new ArrayList(result.getSize());
		//------����ID�̔z��
		ArrayList jigyoIdArray = new ArrayList(result.getSize());
		//------SYSTEM_NO�̔z��
		ArrayList systemNoArray = new ArrayList(result.getSize());
		//���x�������g�D�\�`�F�b�N���J��Ԃ��o�O���C��
		//����CD+����ID����ӃL�[�Ƃ��Ċi�[����Set
		Set beforeKeySet = new HashSet(result.getSize());
		//�g�D�\�`�F�b�N�p�̏���CD
		ArrayList checkShozokuCdArray = new ArrayList(result.getSize());
		//�g�D�\�`�F�b�N�p�̎���ID
		ArrayList checkJigyoIdArray = new ArrayList(result.getSize());
		
		
		//------�\����ID���ږ��FSHINSEISHA_ID
		String shozokuCd = ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD;
		String jigyoId = ShinseiSearchInfo.ORDER_BY_JIGYO_ID;
		String systemNo = ShinseiSearchInfo.ORDER_BY_SYSTEM_NO;

		//------�\���҂̐����J��Ԃ�
		for (int i = 0; i < result.getSize(); i++) {

			//------�e�\���҂̏����擾
			HashMap juriDataMap = (HashMap) result.getList().get(i);

			Object shozokuData = juriDataMap.get(shozokuCd);
			Object jigyoData = juriDataMap.get(jigyoId);
			Object systemData = juriDataMap.get(systemNo);
			//------�\����ID�Ƀf�[�^������ꍇ�͔z��Ɋi�[	
			if (shozokuData != null && !shozokuData.equals("")
				&& jigyoData != null && !jigyoData.equals("")
				&& systemData!= null && !systemData.equals("")) {
					shozokuCdArray.add(shozokuData);
					jigyoIdArray.add(jigyoData);
					systemNoArray.add(systemData);

					//2005.05.22 iso �ꊇ�󗝂̃p�t�H�[�}���X���P
					if(!beforeKeySet.contains(shozokuData.toString() + jigyoData.toString())) {
						//�ߋ��ɂȂ�����CD+����ID�Ȃ�A�`�F�b�N�p�ϐ��Ɋi�[
						checkShozokuCdArray.add(shozokuData);
						checkJigyoIdArray.add(jigyoData);
						//����̏���CD+����ID�L�[����ӃL�[�Ƃ��ēo�^
						beforeKeySet.add(shozokuData.toString() + jigyoData.toString());
					}
			}
		}
		ISystemServise servise =
			getSystemServise(IServiceName.CHECKLIST_MAINTENANCE_SERVICE);

// 20050823 �ꊇ�󗝎��Ɍ����҃}�X�^�̃`�F�b�N�����s����
		List lstErrors = new ArrayList();
		CheckListSearchInfo kenkyuCheck = new CheckListSearchInfo();
		kenkyuCheck.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
		//20060216    dhy  update
		//kenkyuCheck.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		kenkyuCheck.setJigyoKubun(searchForm.getJigyoKbn());
		//20060216   end
		try{
			lstErrors = servise.IkkatuKenkyushaExist(
					container.getUserInfo(),
					kenkyuCheck,
					//2005.05.22 iso �ꊇ�󗝂̃p�t�H�[�}���X���P
//					shozokuCdArray,
//					jigyoIdArray
					checkShozokuCdArray,
					checkJigyoIdArray
					);
			for(int n=0; n<lstErrors.size(); n++){
				ActionError error = new ActionError("errors.5051",lstErrors.get(n));
				errors.add("kenkyuExists", error);
			}
		}catch(ApplicationException ex){
			ActionError error = new ActionError("errors.4002");
			errors.add("�󗝓o�^�ŃG���[���������܂����B", error);
		}finally{
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
		}

// 20050721
//		servise.juriAll(container.getUserInfo(), shozokuCdArray, jigyoIdArray, systemNoArray);
		servise.juriAll(container.getUserInfo(), shozokuCdArray, jigyoIdArray, systemNoArray, null);
// Horikoshi

		return forwardSuccess(mapping);
	}
}