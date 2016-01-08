/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 研究者情報削除前アクションクラス。
 * 削除対象研究者情報を取得。セッションに登録する。 
 * 削除確認画面を表示する。
 */
public class DeleteAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## 削除情報プロパティ(セッションに保持)　$!userContainer.kenkyushaInfo.プロパティ名

		//------修正登録フォーム情報の取得
		KenkyushaForm deleteForm = (KenkyushaForm) form;
		
		//------削除対象研究者情報の取得
		KenkyushaPk pkInfo = new KenkyushaPk();
		//------キー情報
		String kenkyuNo = deleteForm.getKenkyuNo();
		String shozokuCd = deleteForm.getShozokuCd();
		pkInfo.setKenkyuNo(kenkyuNo);
		pkInfo.setShozokuCd(shozokuCd);

		//------キー情報を元に削除データ取得	
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		KenkyushaInfo deleteInfo = service.selectKenkyushaData(container.getUserInfo(),pkInfo, false);
		
		//------削除対象情報をセッションに登録。
		container.setKenkyushaInfo(deleteInfo);

		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
