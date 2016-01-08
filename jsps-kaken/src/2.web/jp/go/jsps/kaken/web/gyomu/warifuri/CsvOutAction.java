/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * CSV出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: CsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class CsvOutAction extends BaseAction {

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		 throws ApplicationException
		{
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//検索条件の取得
		WarifuriSearchForm searchForm = (WarifuriSearchForm)form;
			
		//-------▼ VOにデータをセットする。
		WarifuriSearchInfo searchInfo = new WarifuriSearchInfo();
			
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
//		//チェックボックスの事業コードリストをセット
//		List jigyoCdList = searchForm.getValueList();
//		if(jigyoCdList == null || jigyoCdList.size() == 0){
//			//事業が選択されていない場合は担当事業をセット
//			GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo();
//			searchInfo.setJigyoCdValueList(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
//		}else{
//			searchInfo.setJigyoCdValueList(searchForm.getValueList());
//		}
		//2005.05.17 iso アクセス管理が事業区分→事業CDにかわったので対応
		//一覧検索と検索条件設定方法を統一
		//チェックボックスの事業コードリストをセット
		if(!searchForm.getValueList().isEmpty()){
			searchInfo.setJigyoCdValueList(searchForm.getValueList());	
		}else{
			//指定されていない場合は、（業務担当者ならば）自分が担当する事業区分から審査対象分の事業区分のみ取得	
			Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(container.getUserInfo());
			//審査対象分の事業区分を検索条件にセット
			searchInfo.setTantoJigyoKubun(shinsaTaishoSet);
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setJigyoCdValueList(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}
		
		ISystemServise servise = getSystemServise(IServiceName.SHINSAIN_WARIFURI_SERVICE);
		
		String jigyoKubun = servise.selectJigyoKubun(container.getUserInfo(),
													(String)searchForm.getValues(0));
		searchInfo.setJigyoKubun(jigyoKubun);
	
		//検索実行
		FileResource fileRes = null;
		try {
			fileRes = 
				getSystemServise(
					IServiceName.SHINSAIN_WARIFURI_SERVICE).createIraisho(
					container.getUserInfo(),
					searchInfo);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
		}
				
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//-----ファイルのダウンロード
		DownloadFileUtil.downloadFile(response, fileRes);	

		return forwardSuccess(mapping);
	}

}
