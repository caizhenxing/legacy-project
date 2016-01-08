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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.impl.ShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.vo.SearchInfo;

import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 審査担当分申請情報一覧表示前アクションクラス。
 * 審査担当分申請一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: TantoShinseiListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class TantoShinseiListAction extends BaseAction {

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

		//------キー情報
		String jigyoId = ((ShinsaKekkaSearchForm)form).getJigyoId();        //事業ID
		String kekkaTen = ((ShinsaKekkaSearchForm)form).getKekkaTen();      //総合評価（点数）

//		//2006.06.08 iso 審査担当事業一覧での事業名表示方式修正
//		String jigyoName = ((ShinsaKekkaSearchForm) form).getJigyoName();   //事業名
		
//2006/04/18 追加ここから		
		String jigyoKubun = ((ShinsaKekkaSearchForm) form).getJigyoKubun();   //事業区分
		container.getUserInfo().getShinsainInfo().setJigyoKubun(jigyoKubun);
//追加ここまで　苗
        
//2006/05/12 追加ここから
        String shinsaJokyo = ((ShinsaKekkaSearchForm)form).getShinsaJokyo();            //審査状況 
        
        if(kekkaTen.equals("")){
            if(shinsaJokyo.equals("0")){
                kekkaTen = ShinsaKekkaMaintenance.SHINSAKEKKA_KEKKA_TEN_MI;
                ((ShinsaKekkaSearchForm)form).setKekkaTen(kekkaTen);
            } else if (shinsaJokyo.equals("1")){
                kekkaTen = ShinsaKekkaMaintenance.SHINSAKEKKA_KEKKA_TEN_KANRYOU;
                ((ShinsaKekkaSearchForm)form).setKekkaTen(kekkaTen);
            }
        }
//苗　追加ここまで
 
		//基盤用対応。総合評価（点数）が「0：すべて」の場合は、NULLをセット（検索条件外とするため）
		if(kekkaTen.equals("0")){
			kekkaTen = null;
		}
		
		SearchInfo searchInfo = new SearchInfo();
		
		//ページ制御
		searchInfo.setPageSize(0);
		searchInfo.setMaxSize(0);
		searchInfo.setStartPosition(0);
				
		//------キー情報を元に更新データ取得	
		Map result = 
				getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).
						selectShinsaKekkaTantoList(
										container.getUserInfo(),
										jigyoId,
										kekkaTen,
                                        ShinsaKekkaMaintenance.SINNSA_FLAG,//2006/10/27 苗　追加
                                        null,//2006/10/27 苗　追加
										searchInfo);

		//2006.06.08 iso 審査担当事業一覧での事業名表示方式修正
		//事業情報は、resultより取得するよう変更
//        //事業オブジェクトに事業情報をセット
//		JigyoKanriInfo jigyoInfo = new JigyoKanriInfo();
//		String kaisu = jigyoId.substring(7, 8);
//		String jigyoCd = jigyoId.substring(2, 7);
//		DateUtil dateUtil = new DateUtil();
//		dateUtil.setCal(Integer.parseInt(jigyoId.substring(0, 2)) + 2000, 4, 1);
//		jigyoInfo.setJigyoId(jigyoId);
//		jigyoInfo.setNendo(dateUtil.getNendo());
//		jigyoInfo.setKaisu(kaisu);
//		jigyoInfo.setJigyoCd(jigyoCd);
//		jigyoInfo.setJigyoName(jigyoName);
//
//		// -----セッションに事業情報を登録する。
//		container.setJigyoKanriInfo(jigyoInfo);

		// 検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
