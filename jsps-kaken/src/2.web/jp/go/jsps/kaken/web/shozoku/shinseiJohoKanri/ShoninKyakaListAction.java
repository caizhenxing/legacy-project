/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShoninKyakaListAction.java
 *    Description : 承認・却下対象応募情報一覧画面を表示する
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *    2006/06/02    V1.1        DIS.liuYi      修正（前画面渡した値は変更するから）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.model.common.IJigyoKubun;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 承認・却下対象応募情報一覧アクションクラス。
 * 承認・却下対象応募情報一覧画面を表示する。
 */
public class ShoninKyakaListAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
        
//　add start ly 2006/06/02
        ShinseiSearchForm searchForm = (ShinseiSearchForm)form;
        
        //------キャンセル処理-----------------
        String forwardStr = (String)request.getParameter("goBack");
        if ("goBack".equals(forwardStr)) {
            searchForm.setStartPosition(0);
            return mapping.findForward(forwardStr);
        }
//　add end ly 2006/06/02

		//所属機関情報
		UserInfo userInfo = container.getUserInfo();
		ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
		if(shozokuInfo == null){
			throw new ApplicationException(
				"所属機関情報を取得できませんでした。",
				new ErrorInfo("errors.application"));
		}
		
		//検索条件をセットする
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());//所属機関コード
		searchInfo.setJokyoId(new String[]{StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU});//申請状況ID
		//TODO 現在保留
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);//システム受付番号の昇順

		//2005/04/19 追加 ここから---------------------------------------------
		//理由 基盤の場合は承認・却下一覧に表示しないようにするため

// update start by ly 2006/06/01
//        ArrayList array = new ArrayList();
//        array.add("1");
//        array.add("2");
//        array.add("3");
//        searchInfo.setJigyoKubun(array);
        
        // 検索条件の事業区分を設定
        if (!StringUtil.isBlank(searchForm.getJigyoKbn())){
            // 学術創成研究費時、事業区分（1,2）を設定
            if (searchForm.getJigyoKbn().equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
                ArrayList array = new ArrayList();
                array.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
                array.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO);
                searchInfo.setJigyoKubun(array);
            }
            // 特別推進研究時、事業区分（3）を設定
            else {
                ArrayList array = new ArrayList();
                array.add(searchForm.getJigyoKbn());
                searchInfo.setJigyoKubun(array);
            }
        }
        // 検索条件の事業コードを設定-->基盤研究（Ｓ）:00031,基盤研究（Ａ）（一般）:00041,
        // 基盤研究（Ａ）（海外学術調査）:00043,基盤研究（Ｂ）（一般）:00051,
        // 基盤研究（Ｂ）（海外学術調査）:00053,特別領域研究（新規領域）-研究計画調書:検討中
        searchInfo.setJigyoCd(searchForm.getJigyoCd());
        
        //ページ制御
        searchInfo.setStartPosition(searchForm.getStartPosition());
        searchInfo.setPageSize(searchForm.getPageSize());
        searchInfo.setMaxSize(searchForm.getMaxSize());
// update end by ly 2006/06/01
		//追加 ここまで--------------------------------------------------------		

		//サーバサービスの呼び出し（処理状況一覧ページ情報取得）
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
//        Page page = servise.searchApplication(userInfo, searchInfo);
		Page page = null;
		try{
			page = servise.searchApplication(userInfo, searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			page = Page.EMPTY_PAGE;
		}

		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO, page);
        
        saveToken(request);
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}