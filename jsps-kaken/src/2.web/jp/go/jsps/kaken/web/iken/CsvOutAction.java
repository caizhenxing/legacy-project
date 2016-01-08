/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : CsvOutAction.java
 *    Description : 意見情報CSV出力アクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/25    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
//import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: CsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class CsvOutAction extends BaseAction {
	
	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String filename = "IKENINFO";

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
		
		if (log.isDebugEnabled()){
			log.debug("意見情報CSV出力！！！");
		}
		
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//検索条件の取得
		IkenSearchForm searchForm = (IkenSearchForm)form;
			
		//-------▼ VOにデータをセットする。
		IkenSearchInfo searchInfo = new IkenSearchInfo();
			
		//申請者フラグ
		searchInfo.setShinseisya( searchForm.getShinseisya() );
		
		//所属機関担当者フラグ
		searchInfo.setSyozoku( searchForm.getSyozoku() );

		//部局担当者フラグ
		searchInfo.setBukyoku( searchForm.getBukyoku() );
		
		//審査員フラグ
		searchInfo.setShinsyain( searchForm.getShinsyain() );
		
		//作成日（開始日）
		if(!searchForm.getSakuseiDateFromYear().equals("")){	
			searchInfo.setSakuseiDateFrom(								
							searchForm.getSakuseiDateFromYear() + "/" +
							searchForm.getSakuseiDateFromMonth() + "/" +
							searchForm.getSakuseiDateFromDay()
							);
		}
		//作成日（終了日）
		if(!searchForm.getSakuseiDateToYear().equals("")){
			searchInfo.setSakuseiDateTo(									
							searchForm.getSakuseiDateToYear() + "/" +
							searchForm.getSakuseiDateToMonth()+ "/" +
							searchForm.getSakuseiDateToDay()
							);
		}
		
		//表示方式
		searchInfo.setDispmode( searchForm.getDispmode() );
	
		//検索実行
		List result =
			getSystemServise(
				IServiceName.IKEN_MAINTENANCE_SERVICE).searchCsvData(searchInfo);
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
