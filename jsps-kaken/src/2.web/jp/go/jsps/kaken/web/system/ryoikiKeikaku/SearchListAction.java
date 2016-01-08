/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 張楠
 *    Date        : 2007/6/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.ryoikiKeikaku;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoSystemInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 領域計画書情報検索アクションクラス。
 * 領域計画書情報一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.5 $"
 * Date="$Date: 2007/07/25 07:56:05 $"
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
		RyoikiGaiyoSearchForm searchForm = (RyoikiGaiyoSearchForm)form;
		
		//-------▼ VOにデータをセットする。
		RyoikiKeikakushoSystemInfo searchInfo = new RyoikiKeikakushoSystemInfo();
		
		//研究種目名
		if(!searchForm.getJigyoName().equals("")){		
			searchInfo.setJigyoName(searchForm.getJigyoName());
		}
		//仮領域番号
		if(!searchForm.getKariryoikiNo().equals("")){	
			searchInfo.setKariryoikiNo(searchForm.getKariryoikiNo());
		}
		//領域代表者氏名-姓
		if(!searchForm.getNameKanjiSei().equals("")){			
			searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		}
		//領域代表者氏名-名
		if(!searchForm.getNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		}		
		//所属研究機関名ー番号
		if(!searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
		//所属研究機関名ー名称
		if(!searchForm.getShozokuName().equals("")){
			searchInfo.setShozokuName(searchForm.getShozokuName());
		}
		//応募状況
		if(!searchForm.getRyoikiJokyoId().equals("")){
			if (searchForm.getRyoikiJokyoId().length()==1) {
				if (!"0".equals(searchForm.getRyoikiJokyoId())){
					searchInfo.setRyoikiJokyoId("0"+searchForm.getRyoikiJokyoId());
				}
			}else{
				searchInfo.setRyoikiJokyoId(searchForm.getRyoikiJokyoId());
			}
		}		
		ISystemServise service = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
		List result = service.getRyoikiResult(searchInfo);
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
