/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 評価情報検索前アクションクラス。
 * 評価情報検索画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
 */
public class SearchAction extends BaseAction {

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

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//ユーザ情報の取得
        UserInfo userInfo = container.getUserInfo();
        
		//宣言
		HyokaSearchForm searchForm = new HyokaSearchForm();

		//-------▼ VOにデータをセットする。
//		SearchInfo searchInfo = new SearchInfo();

		//事業名
//update start ly 2006/04/11
//		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(), ((HyokaSearchForm)form).getJigyoKubun()));
		String jigyoKbn = ((HyokaSearchForm)form).getJigyoKubun();
		
		if (IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKbn)) {
			//学術創成（非公募）の場合
			searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(), jigyoKbn));
		}
		else if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKbn)){
			//基盤研究、若手研究スタートアップの場合
			List jigyoList = new ArrayList();
			List jigyoList2 = new ArrayList();
            List jigyoList3 = new ArrayList();
//2006/05/19 追加ここから
//			//基盤の事業名
//			jigyoList = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_KIBAN);
//			//若手スタートの事業名
//			jigyoList2 = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
//            //特別研究促進費の事業名
//            jigyoList3 = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
//			//若手スタートの事業名を追加
//			jigyoList.addAll(jigyoList2);
//            //特別研究促進費の事業名を追加
//            jigyoList.addAll(jigyoList3);
            
            if (userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
                Set iset = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
                // 基盤の事業名
                if (iset.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)) {
                    jigyoList = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_KIBAN);
                }
                if (iset.contains(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)) {
                    jigyoList2 = LabelValueManager.getJigyoNameList(container.getUserInfo(),IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
                }
                if (iset.contains(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)) {
                    jigyoList3 = LabelValueManager.getJigyoNameList(container.getUserInfo(),IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
                }
            }
            jigyoList.addAll(jigyoList2);
            jigyoList.addAll(jigyoList3);
			searchForm.setJigyoList(jigyoList);
//苗　追加ここまで            
		}
//update end ly 2006/04/11
		//表示方式学創用(点数順 or Aの多い順)
		searchForm.setHyojiHoshikiList(LabelValueManager.getHyokaHyojiList());
		//表示方式基盤用(評価順一覧 or コメント一覧)
		searchForm.setHyojiHoshikiListKiban(LabelValueManager.getHyokaHyojiListKiban());

		//検索条件をフォームをセットする。
		updateFormBean(mapping,request,searchForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
