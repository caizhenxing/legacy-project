/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : IkenSaveAction.java
 *    Description : 意見情報退避するアクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * @author user1
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class IkenSaveAction extends BaseAction {

	/* (Javadoc なし)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response,
				UserContainer container)
			throws ApplicationException {
		
		//フォーム設定
		IkenForm frm = (IkenForm)form;
		
		//ご意見ご要望情報を取得する。
		String strIken = frm.getIkenInfo();
		if (log.isDebugEnabled()){
			log.debug("対象者ID：" + frm.getTaishoID());
			//log.debug("ご意見：" + strIken.length() + ":" + strIken);
		}

		//意見情報クラス生成
		IkenInfo ikenInfo = new IkenInfo();
		
		//対象者ＩＤ設定
		ikenInfo.setTaisho_id( Integer.parseInt( frm.getTaishoID() ));
		
		//意見内容設定
		ikenInfo.setIken(strIken);
		
		//DBへ書き込む
		//サーバサービスの呼び出し（新規登録）
		ISystemServise servise = getSystemServise(IServiceName.IKEN_MAINTENANCE_SERVICE);
		servise.insert(	ikenInfo );
		
		// 次の画面へ遷移
		return forwardSuccess(mapping);
	}

}
