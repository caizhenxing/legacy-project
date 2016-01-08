/*
 * 作成日: 2005/04/24
 *
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 登録済みファイル一時削除アクションクラス。
 * 
 * 2005/04/26 ファイルを毎回アップロードするため、使用しない。
 * 
 * @author yoshikawa_h
 *
 */
public class DeleteFileAction extends BaseAction {

	/** ダウンロードファイルフラグ。添付ファイル（Win） */
	public static String FILE_FLG_TENPU_WIN = "0";
	
	/** ダウンロードファイルフラグ。添付ファイル（Mac） */
	public static String FILE_FLG_TENPU_MAC = "1";
	
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

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.jigyoKanriInfo.プロパティ名
		//## 更新対象プロパティ 				$!jigyoKanriForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
		
		//------修正登録フォーム情報の取得
		JigyoKanriForm editForm = (JigyoKanriForm) form;
		String downloadFileFlg = editForm.getDownloadFileFlg();
		
		JigyoKanriInfo editInfo = container.getJigyoKanriInfo();
		editForm.setAction(BaseForm.EDIT_ACTION);
		
		try {
			PropertyUtils.copyProperties(editInfo, editForm);
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		if(downloadFileFlg.equals(FILE_FLG_TENPU_WIN)){
			editInfo.setDelWinFileFlg(true);
			editInfo.setTenpuWin(null);
			editForm.setTenpuWin(null);
		}else if(downloadFileFlg.equals(FILE_FLG_TENPU_MAC)){
			editInfo.setDelMacFileFlg(true);
			editInfo.setTenpuMac(null);
			editForm.setTenpuMac(null);
		}
		
		//------修正登録フォームにセットする。
		updateFormBean(mapping,request,editForm);
		
		//------更新対象情報をセッションに登録。
		container.setJigyoKanriInfo(editInfo);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}