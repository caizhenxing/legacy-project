/*
 * Created on 2005/04/15
 * 
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.KenkyushaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究者情報検索アクションクラス。
 * 研究者情報一覧画面を表示する。
 * 
 */
public class SearchListAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//検索条件の取得
		KenkyushaSearchForm searchForm = (KenkyushaSearchForm)form;
		
		//-------▼ VOにデータをセットする。
		KenkyushaSearchInfo searchInfo = new KenkyushaSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//検索実行
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		Page result = service.search(searchInfo);

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		return forwardSuccess(mapping);
	}

}
