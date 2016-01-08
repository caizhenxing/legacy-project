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
 * 再審査設定アクションクラス。
 * 
 * ID RCSfile="$RCSfile: SaishinsaAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class SaishinsaAction extends BaseAction {
	
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
		shinsaJokyoSearchInfo.setHyojiHoshikiShinsaJokyo(shinsaJokyoForm.getHyojiHoshikiShinsaJokyo());

		//------再審査設定対象情報を取得。
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

		//審査員情報は変更されている可能性を考慮して審査員マスタから取得。
		ShinsainPk shinsainPk = new ShinsainPk();
		shinsainPk.setShinsainNo(shinsaJokyoForm.getShinsainNo());
		shinsainPk.setJigyoKubun(shinsaJokyoForm.getJigyoKubun());

		//------審査員情報を取得。
		try {
			ShinsainInfo shinsainInfo
				= getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(container.getUserInfo(),shinsainPk);
		
			//審査員がマスタに存在した場合、審査員情報を上書き。
			shinsaJokyoInfo.setShinsainNo(shinsainInfo.getShinsainNo());
			shinsaJokyoInfo.setNameKanjiSei(shinsainInfo.getNameKanjiSei());
			shinsaJokyoInfo.setNameKanjiMei(shinsainInfo.getNameKanjiMei());
		} catch(ApplicationException e) {
			//審査員がマスタに存在しない場合、再審査設定確認→再審査設定完了
			//の時にエラーとするので、ここではエラーとしない。
		}
		
		//------再審査設定対象情報をセッションに登録。
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
