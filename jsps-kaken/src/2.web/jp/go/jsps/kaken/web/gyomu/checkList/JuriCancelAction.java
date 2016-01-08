/*
 * Created on 2005/04/14
 *
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理解除確認を行う。
 * 
 * @author masuo_t
 */
public class JuriCancelAction extends BaseAction {

	/** 状況IDが06(学振受理)のものを表示 */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_JYURI	//学振受理
	};

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
			//検索条件の取得
			CheckListForm searchForm = (CheckListForm) form;

			//-------▼ VOにデータをセットする。
			CheckListSearchInfo searchInfo = new CheckListSearchInfo();

			//検索条件の設定
			if (searchForm.getJigyoId() != null
				&& !searchForm.getJigyoId().equals("")) {
				searchInfo.setJigyoCd(searchForm.getJigyoId().substring(2, 7));
// 20050829 年度を条件に追加するため
				searchInfo.setJigyoId(searchForm.getJigyoId());
			}
			if (searchForm.getShozokuCd() != null
				&& !searchForm.getShozokuCd().equals("")) {
				searchInfo.setShozokuCd(searchForm.getShozokuCd());
			}
			if(searchForm.getKaisu() != null
				&& !searchForm.getKaisu().equals("")){
				searchInfo.setKaisu(searchForm.getKaisu());
			}
			
			//状況IDが06(学振受理)のものを表示 
			searchInfo.setSearchJokyoId(JIGYO_IDS);

// 20050613 Start
			//20060215  dhy  update
			//searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
			CheckListSearchForm checkForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
			searchInfo.setJigyoKubun(checkForm.getJigyoKbn());
			//20060215  end
// Horikoshi End

			//検索実行
			Page result =getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE)
					.selectCheckList(container.getUserInfo(),searchInfo);

			//検索結果をセットする。
			request.setAttribute(IConstants.RESULT_INFO, result);

			//トークンをセッションに保存する。
			saveToken(request);
			return forwardSuccess(mapping);
	}

}
