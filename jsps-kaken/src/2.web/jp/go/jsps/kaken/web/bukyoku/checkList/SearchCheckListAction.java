/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckListForm.java
 *    Description : 受理登録対象応募情報一覧用フォーム
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    V1.0                       新規作成
 *    2006/06/06    V1.1        DIS.GongXB     修正（事業CDを追加）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト（基盤）一覧アクションクラス。
 * チェックリスト一覧画面を表示する。
 */
public class SearchCheckListAction extends BaseAction {

	/** 所属機関受付中(状況ID:03)以降の状況ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//所属機関受付中
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//学振受付中
//			StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,			//所属機関却下
			StatusCode.STATUS_GAKUSIN_JYURI,			//学振受理
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//学振不受理
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//審査員割り振り処理後
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,			//割り振りチェック完了
			StatusCode.STATUS_1st_SHINSATYU,			//一次審査中
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//一次審査：判定
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//二次審査完了
	};
	
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
		
		//状況IDが所属機関受付中以降のものについて表示する
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		
// 20050606 Horikoshi Start
//update start liuyi 2006/2/16
		//checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
	    CheckListForm listForm = (CheckListForm)form;
//2006/06/02 宮秀彬　追加ここから
        //チェックリスト確認(基盤研究（C）・萌芽研究・若手研究)の時、
        //検索条件の事業コードを（00061、00062、00111、00121、00131）に設定
        if ((! StringUtil.isBlank(listForm.getJigyoCd()))
                &&(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(listForm.getJigyoCd()))){
            ArrayList array = new ArrayList();
            array.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
            array.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
            
//DEL　START 2007/06/28 BIS 金京浩            
            //array.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
//DEL　END　 2007/06/28 BIS 金京浩            
            
            array.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
            array.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
            checkInfo.setTantoJigyoCd(array);
        }
        
//      2006/06/08 追加ここから        
        listForm.setJigyoCd("");
        updateFormBean(mapping,request,listForm);
//苗　追加ここまで
        
        //検索条件の事業区分を設定
        if(! StringUtil.isBlank(listForm.getJigyoKbn())){
            checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());
        }        
		//checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());
//2006/06/02 宮秀彬　追加ここまで
//update end liuyi 2006/2/16
// 20050606 Horikoshi End

		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		Page result = null;
		try{		
			//検索実行
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
					container.getUserInfo(),
					checkInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//2005/05/19 追加 ここまで--------------------------------------------------		

		//検索結果をフォームにセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);

		return forwardSuccess(mapping);
	}
}