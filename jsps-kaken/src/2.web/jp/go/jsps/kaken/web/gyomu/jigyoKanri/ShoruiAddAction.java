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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IJigyoKanriMaintenance;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 書類登録前アクションクラス。
 * フォームに書類登録画面に必要なデータをセットする。
 * 書類登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShoruiAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiAddAction extends BaseAction {

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
			
			//## 更新不可のプロパティ(セッションに保持)　$!userContainer.shoruiKanriInfo.プロパティ名
			//## 登録対象プロパティ 				$!shoruiKanriForm.プロパティ名
			//##

			//-----ActionErrorsの宣言（定型処理）-----
			ActionErrors errors = new ActionErrors();

			//------新規登録フォーム情報の作成
			ShoruiKanriForm addForm = new ShoruiKanriForm();
			
			//------更新モード
			addForm.setAction(BaseForm.ADD_ACTION);

			//------書類登録対象事業管理情報の作成
			ShoruiKanriInfo addInfo = new ShoruiKanriInfo();
			//------キー情報
			String jigyoId = ((ShoruiKanriForm)form).getJigyoId();
			addInfo.setJigyoId(jigyoId);
		
			//------キー情報を元に事業データ取得	
			//DB登録
			ISystemServise servise = getSystemServise(
							IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
			Map map = servise.select(container.getUserInfo(),addInfo);
			
			addInfo = (ShoruiKanriInfo)map.get(IJigyoKanriMaintenance.KEY_JIGYOKANRI_INFO);
			List  result = (List)map.get(IJigyoKanriMaintenance.KEY_SHORUIKANRI_LIST);
						
			//------ラジオボタンデータセット
			//対象
			addForm.setTaishoIdList(LabelValueManager.getTaishoIdList());			
			
			//------登録対象情報をセッションに登録。
			container.setShoruiKanriInfo(addInfo);			

			//------書類管理情報リストをセッションに登録。
			container.setShoruiKanriList(result);
		
			//------新規登録フォームにセットする。
			updateFormBean(mapping, request, addForm);
			
			//トークンをセッションに保存する。
			saveToken(request);

			//-----画面遷移（定型処理）-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
		}
}
