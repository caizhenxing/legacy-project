/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト出力確認アクションクラス。
 * チェックリストの状況IDを判別し、
 * '03'(所属機関受付中)の場合はチェックリスト出力確認画面を表示する。
 * '04'(学振受付中)の場合は、チェックリストアクションクラスをリダイレクトで呼び出す。
 * 
 */
public class CheckListCheckTokuteiAction extends BaseAction {

	private static final String FORWARD_KIKAN = "kikan";
	private static final String FORWARD_GAKUSIN = "gakusin";
	private static final String SYOZOKU_UKETUKETYU = "03";
	private static final String GAKUSIN_UKETUKETYU = "04";

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//宣言
		//ShinseiSearchForm searchForm = new ShinseiSearchForm();

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//検索実行
		String jokyoId = 
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkJokyoId(
				container.getUserInfo(),
				checkInfo);

		//2005/04/12 追加 ここから--------------------------------------------------
		//有効期限チェックの追加
		boolean inPeriod = getSystemServise(
			IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkLimitDate(
			container.getUserInfo(),
			checkInfo);
		//追加 ここまで-------------------------------------------------------------
		
		//状況IDによって遷移先を変更する	  
		if(jokyoId != null && jokyoId.equals(SYOZOKU_UKETUKETYU)){ 	       //所属機関受付中:03	
			//有効期限チェック結果をフォームに格納
			checkForm.setPeriod(inPeriod);
			//検索条件をフォームをセットする。
			request.setAttribute(IConstants.RESULT_INFO, checkForm);
			//チェックリスト出力確認画面へ
			return mapping.findForward(FORWARD_KIKAN);
		
		//2005/04/11 追加 ここから--------------------------------------------------
		//学振受付中以外にも表示するように修正	
		}else if(jokyoId != null && jokyoId.equals(GAKUSIN_UKETUKETYU)     //学振受付中:04
			|| jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA)       //所属機関却下:05
			|| jokyoId.equals(StatusCode.STATUS_GAKUSIN_JYURI)             //学振受理:06
			|| jokyoId.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)           //学振不受理:07
			|| jokyoId.equals(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO) //審査員割り振り処理後:08
			|| jokyoId.equals(StatusCode.STATUS_WARIFURI_CHECK_KANRYO)     //割り振りチェック完了:09
			|| jokyoId.equals(StatusCode.STATUS_1st_SHINSATYU)             //1次審査中:10
			|| jokyoId.equals(StatusCode.STATUS_1st_SHINSA_KANRYO)         //1次審査：判定:11
			|| jokyoId.equals(StatusCode.STATUS_2nd_SHINSA_KANRYO)){       //2次審査完了:12
		
		//追加 ここまで-------------------------------------------------------------	
			
			//チェックリスト出力
			return mapping.findForward(FORWARD_GAKUSIN);					
		}else{
			errors.add("errors.5006", new ActionError("errors.5006", "チェックリスト"));
			saveErrors(request, errors);
			//	エラー処理
			return forwardFailure(mapping);		
		}
	}
}