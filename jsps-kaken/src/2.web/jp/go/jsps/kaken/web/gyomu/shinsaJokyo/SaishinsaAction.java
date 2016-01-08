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
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ĐR���ݒ�A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: SaishinsaAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class SaishinsaAction extends BaseAction {
	
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

		//���������̎擾
		ShinsaJokyoForm shinsaJokyoForm = (ShinsaJokyoForm)form;
			
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinsaJokyoSearchInfo shinsaJokyoSearchInfo = new ShinsaJokyoSearchInfo();
		shinsaJokyoSearchInfo.setShinsainNo(shinsaJokyoForm.getShinsainNo());
		shinsaJokyoSearchInfo.setJigyoId(shinsaJokyoForm.getJigyoId());
		shinsaJokyoSearchInfo.setJigyoKubun(shinsaJokyoForm.getJigyoKubun());
		shinsaJokyoSearchInfo.setHyojiHoshikiShinsaJokyo(shinsaJokyoForm.getHyojiHoshikiShinsaJokyo());

		//------�ĐR���ݒ�Ώۏ����擾�B
		Page result = getSystemServise(IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE)
						.search(container.getUserInfo(), shinsaJokyoSearchInfo);

		ShinsaJokyoInfo shinsaJokyoInfo = new ShinsaJokyoInfo();
		HashMap shinsaJokyoMap = (HashMap)result.getList().get(0);
		shinsaJokyoInfo.setShinsainNo((String)shinsaJokyoMap.get("SHINSAIN_NO"));
		shinsaJokyoInfo.setJigyoId((String)shinsaJokyoMap.get("JIGYO_ID"));
		shinsaJokyoInfo.setJigyoKubun(shinsaJokyoMap.get("JIGYO_KUBUN").toString());
		shinsaJokyoInfo.setNameKanjiSei((String)shinsaJokyoMap.get("SHINSAIN_NAME_KANJI_SEI"));
		shinsaJokyoInfo.setNameKanjiMei((String)shinsaJokyoMap.get("SHINSAIN_NAME_KANJI_MEI"));
		shinsaJokyoInfo.setNendo((String)shinsaJokyoMap.get("NENDO").toString());
		shinsaJokyoInfo.setKaisu(shinsaJokyoMap.get("KAISU").toString());
		shinsaJokyoInfo.setJigyoName((String)shinsaJokyoMap.get("JIGYO_NAME"));

		//�R�������͕ύX����Ă���\�����l�����ĐR�����}�X�^����擾�B
		ShinsainPk shinsainPk = new ShinsainPk();
		shinsainPk.setShinsainNo(shinsaJokyoForm.getShinsainNo());
		shinsainPk.setJigyoKubun(shinsaJokyoForm.getJigyoKubun());

		//------�R���������擾�B
		try {
			ShinsainInfo shinsainInfo
				= getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(container.getUserInfo(),shinsainPk);
		
			//�R�������}�X�^�ɑ��݂����ꍇ�A�R���������㏑���B
			shinsaJokyoInfo.setShinsainNo(shinsainInfo.getShinsainNo());
			shinsaJokyoInfo.setNameKanjiSei(shinsainInfo.getNameKanjiSei());
			shinsaJokyoInfo.setNameKanjiMei(shinsainInfo.getNameKanjiMei());
		} catch(ApplicationException e) {
			//�R�������}�X�^�ɑ��݂��Ȃ��ꍇ�A�ĐR���ݒ�m�F���ĐR���ݒ芮��
			//�̎��ɃG���[�Ƃ���̂ŁA�����ł̓G���[�Ƃ��Ȃ��B
		}
		
		//------�ĐR���ݒ�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinsaJokyoInfo(shinsaJokyoInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
