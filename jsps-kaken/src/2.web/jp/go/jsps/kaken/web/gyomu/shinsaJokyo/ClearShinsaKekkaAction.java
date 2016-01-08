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

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 審査結果クリア確認アクションクラス。
 * 
 * ID RCSfile="$RCSfile: ClearShinsaKekkaAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class ClearShinsaKekkaAction extends BaseAction {
	
	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//検索条件の取得
		ShinsaJokyoForm shinsaJokyoForm = (ShinsaJokyoForm)form;
			
		//-------▼ VOにデータをセットする。
		ShinsaJokyoSearchInfo shinsaJokyoSearchInfo = new ShinsaJokyoSearchInfo();
		shinsaJokyoSearchInfo.setShinsainNo(shinsaJokyoForm.getShinsainNo());
		shinsaJokyoSearchInfo.setJigyoId(shinsaJokyoForm.getJigyoId());
		shinsaJokyoSearchInfo.setJigyoKubun(shinsaJokyoForm.getJigyoKubun());

		//------審査結果クリア対象情報を取得。
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

		//------審査結果クリア対象情報をセッションに登録。
		container.setShinsaJokyoInfo(shinsaJokyoInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
