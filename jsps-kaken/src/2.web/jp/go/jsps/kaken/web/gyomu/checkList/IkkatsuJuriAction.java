/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriAction.java
 *    Description : 一括受理確認アクション。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.gyomu.checkList.CheckListSearchForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理確認アクション
 * 
 * @author masuo_t
 */
public class IkkatsuJuriAction extends BaseAction {

	/** 状況IDが04(学振受付中)のものを表示 */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_SHORITYU		//学振受付中
	};

	public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

		//検索条件の取得
		CheckListInfo checkInfo = container.getCheckListInfo();

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();

		//検索条件の設定
		if(checkInfo.getJigyoCd() != null && !checkInfo.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(checkInfo.getJigyoCd());
		}
		if(checkInfo.getShozokuCd() != null && !checkInfo.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(checkInfo.getShozokuCd());
		}
		//2005.11.18 iso 所属機関名が抜けていたので追加
		if(!StringUtil.isBlank(checkInfo.getShozokuName())){
			searchInfo.setShozokuName(checkInfo.getShozokuName());
		}
		//20060310  add
		CheckListSearchForm checkForm = (CheckListSearchForm) request
			.getSession().getAttribute("checkListSearchForm");
	    String jokyoKubun = checkForm.getJuriJokyo();
		//受理状況が受理済み又は確定解除である場合、受理可能のデータがない為、エラーメッセージを表示
		if(jokyoKubun.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU) ||
			jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			throw new NoDataFoundException("該当データはありません。");
		}
		//20060310  end
	
		//状況IDが04(学振受付中)のものを表示 
		searchInfo.setSearchJokyoId(JIGYO_IDS);
			
		//20060216   dhy  start
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
		//20060216   end

// 20050629 NoDataFoundExceptionをthrowするよう変更
		Page result = null;
		try{
			//検索実行
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
					container.getUserInfo(),
					searchInfo,
					false);
		}catch(NoDataFoundException e){
			throw new NoDataFoundException("該当データはありません。");
		}
// Horikoshi
			
		//検索結果をセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}