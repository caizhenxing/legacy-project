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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 証明書発行用CSV出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: ShomeiCsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ShomeiCsvOutAction extends BaseAction {
	
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
			

		ShozokuInfo csvhInfo = container.getShozokuInfo();
		
		//設定ファイルからCSV固定データを取得
		String profileName = ApplicationSettings.getString(ISettingKeys.PROFILE_NAME);			//profile name
		String subjectDn = ApplicationSettings.getString(ISettingKeys.SUBJECT_DN);				//subject DN
		String subjectAltName = ApplicationSettings.getString(ISettingKeys.SUBJECT_ALT_NAME);	//subjectAltName
		String pubkeyAlgo = ApplicationSettings.getString(ISettingKeys.PUBKEY_ALGO);			//pubkey algo
		String keyLength = ApplicationSettings.getString(ISettingKeys.KEY_LENGTH);				//key length
		String p12Flag = ApplicationSettings.getString(ISettingKeys.P12_FLAG);					//p12 flag
				
		//-------CSVデータをリストに格納
		List line = new ArrayList();
		line.add("\"" +profileName + "\"");				//profile name
		//line.add(csvhInfo.getShozokuCd());				//所属機関名（コード）
		line.add(csvhInfo.getShozokuTantoId().substring(0, 7));	////所属機関名（コード）+ 連番
		line.add("\"" + subjectDn + csvhInfo.getShozokuNameEigo() + csvhInfo.getShozokuTantoId().substring(0, 7) + "\"");	//subject DN,所属機関名（英文）
		line.add("\"" + subjectAltName + "\"");			//subjectAltName
		line.add(pubkeyAlgo);							//pubkey algo
		line.add(keyLength);							//key length	
		line.add(p12Flag);								//p12 flag
		line.add("\"" + csvhInfo.getPassword()+ "\"");	//パスワード
		
		//-------CSVデータリストに1行追加
		List csvList = new ArrayList();
		//csvList.add(line);
		
		//2005/05/31　追加　ここから----------------------------
		//理由　CSVデータに部局担当者情報を追加
		ISystemServise servise = getSystemServise(
								IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE);
		csvList = servise.getShomeiCsvData(container.getUserInfo(),csvhInfo, line);
		
		//追加　ここまで----------------------------------------
		
		//-------ファイル名を取得		
		String fileName = 	csvhInfo.getShozokuCd();	//所属機関名（コード）	
		
		//-------ファイルをダウンロード（エンクォートしない）
		DownloadFileUtil.downloadCSV(request, response, csvList, fileName, false, false);	

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
