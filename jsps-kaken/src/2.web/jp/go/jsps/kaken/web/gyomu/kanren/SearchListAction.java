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
package jp.go.jsps.kaken.web.gyomu.kanren;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 関連分野の研究者検索アクションクラス。
 * 関連分野研究者一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class SearchListAction extends BaseAction {

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

		//検索条件の取得
		KanrenSearchForm searchForm = (KanrenSearchForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		 
		//事業コード
		if(!searchForm.getJigyoCd().equals("")){	
			searchInfo.setJigyoCd(searchForm.getJigyoCd());	//指定されていた場合は当該事業コードのみ
		}else{
			//指定されていない場合は、（業務担当者ならば）自分が担当する事業コードのみ
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
			}
		}
		  
		  
/*		  
		  //年度
		  if(!searchForm.getNendo().equals("")){		
			  searchInfo.setNendo(searchForm.getNendo());
		  }
		  //回数
		  if(!searchForm.getKaisu().equals("")){	
			  searchInfo.setKaisu(searchForm.getKaisu());
		  }
		  //申請者名（漢字）-姓
		  if(!searchForm.getNameKanjiSei().equals("")){			
			  searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		  }
		  //申請者名（漢字）-名
		  if(!searchForm.getNameKanjiMei().equals("")){
			  searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		  }	
		  //申請者（ふりがな）－姓
		  if(!searchForm.getNameKanaMei().equals("")){
			  searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		  }	
		  //申請者（ふりなが）－名
		  if(!searchForm.getNameKanaSei().equals("")){
			  searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		  }		
		  	
		  //申請者名（ローマ字）-姓
		  if(!searchForm.getNameRoSei().equals("")){
			  searchInfo.setNameRoSei(searchForm.getNameRoSei());
		  }
		  //申請者名（ローマ字）-名
		  if(!searchForm.getNameRoMei().equals("")){
			  searchInfo.setNameRoMei(searchForm.getNameRoMei());
		  }
		  //申請番号
		  if(!searchForm.getUketukeNo().equals("")){		
			  searchInfo.setUketukeNo(searchForm.getUketukeNo());		
		  }
		  //系統の区分
		  if(!searchForm.getKeiName().equals("")){
			  searchInfo.setKeiName(searchForm.getKeiName());
		  }	
*/		  

		//申請状況条件（固定：業務担当者が参照可能なステータスのもの）
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU}); // 再申請中（申請状況に関わらず） 
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);      //「学振処理中」:04
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);       //「学振受理」:06
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);      //「学振不受理」:07
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    //「審査員割り振り処理後」:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);     //「割り振りチェック完了」:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);       //「1次審査中」:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);      //「1次審査完了」:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);      //「2次審査完了」:12
		searchInfo.setStatusSearchInfo(statusInfo);
		
		//並び順
		//	searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);		//事業ID毎
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);		//申請番号順
		
		//ページ制御
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//検索実行
		Page result =
			getSystemServise(
				IServiceName.KANRENBUNYA_MAINTENANCE_SERVICE).search(
				container.getUserInfo(),
				searchInfo);

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
