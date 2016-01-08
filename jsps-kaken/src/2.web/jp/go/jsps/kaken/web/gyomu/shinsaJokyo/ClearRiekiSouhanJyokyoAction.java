/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2006/10/25
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

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * ���v�����ē��͊m�F�B
 * 
 * ID RCSfile="$RCSfile: ClearRiekiSouhanJyokyoAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class ClearRiekiSouhanJyokyoAction extends BaseAction {
	
    /* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, UserContainer container)
	throws ApplicationException {
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//���������̎擾
		ShinsaJokyoForm shinsaJokyoForm = (ShinsaJokyoForm)form;
			
		//-------�� VO�ɂ�����B
		ShinsaJokyoSearchInfo shinsaJokyoSearchInfo = new ShinsaJokyoSearchInfo();
		shinsaJokyoSearchInfo.setShinsainNo(shinsaJokyoForm.getShinsainNo());
		shinsaJokyoSearchInfo.setJigyoId(shinsaJokyoForm.getJigyoId());
		shinsaJokyoSearchInfo.setJigyoKubun(shinsaJokyoForm.getJigyoKubun());

		//------���v�����ē��̓N���A�Ώۏ����擾�B
		Page result = getSystemServise(IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE)
						.search(container.getUserInfo(), shinsaJokyoSearchInfo);

		ShinsaJokyoInfo shinsaJokyoInfo = new ShinsaJokyoInfo();
		HashMap shinsaJokyoMap = (HashMap)result.getList().get(0);
		shinsaJokyoInfo.setShinsainNo((String)shinsaJokyoMap.get("SHINSAIN_NO"));
		shinsaJokyoInfo.setJigyoId((String)shinsaJokyoMap.get("JIGYO_ID"));
		shinsaJokyoInfo.setJigyoKubun(shinsaJokyoMap.get("JIGYO_KUBUN").toString());
		shinsaJokyoInfo.setNameKanjiSei((String)shinsaJokyoMap.get("SHINSAIN_NAME_KANJI_SEI"));
		shinsaJokyoInfo.setNameKanjiMei((String)shinsaJokyoMap.get("SHINSAIN_NAME_KANJI_MEI"));
		shinsaJokyoInfo.setNendo((String)shinsaJokyoMap.get("NENDO"));
		shinsaJokyoInfo.setKaisu(shinsaJokyoMap.get("KAISU").toString());
		shinsaJokyoInfo.setJigyoName((String)shinsaJokyoMap.get("JIGYO_NAME"));
		//save information to request object
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