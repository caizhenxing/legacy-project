/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RegistApplicationAction.java
 *    Description : 申請書情報をデータベースに登録する
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/09    V1.0        takano         新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

/**
 * 申請書登録アクションクラス。
 * 申請書情報をデータベースに登録する。
 * 処理が正常に終了した場合、ファイル変換中画面を返す。
 * 
 * ID RCSfile="$RCSfile: RegistApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class RegistApplicationAction extends BaseAction {

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

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
				
		//-----申請書入力フォームの取得
		ShinseiForm shinseiForm = (ShinseiForm)form;
// 2006/02/10　追加　ここから	
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		String jigyoKubun = shinseiForm.getShinseiDataInfo().getKadaiInfo().getJigyoKubun();
		String year = "";
		String month = "";
		String day = "";
		DateUtil dateUtil = new DateUtil();
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
			year = DateUtil.changeWareki4Seireki(shinseiForm.getSaiyoDateYear());
			month = StringUtil.fillLZero(shinseiForm.getSaiyoDateMonth(), 2);
			day = StringUtil.fillLZero(shinseiForm.getSaiyoDateDay(), 2);
			dateUtil.setCal(year, month, day);
			shinseiForm.getShinseiDataInfo().setSaiyoDate(dateUtil.getDateYYYYMMDD());
		} else if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
			if(ouboShikaku.equals("1")||ouboShikaku.equals("2")){
				year = DateUtil.changeWareki4Seireki(shinseiForm.getSikakuDateYear());
				month = StringUtil.fillLZero(shinseiForm.getSikakuDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getSikakuDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setSikakuDate(dateUtil.getDateYYYYMMDD());
			}
			if(ouboShikaku.equals("3")){
				year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuStartDateYear());
				month = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setIkukyuStartDate(dateUtil.getDateYYYYMMDD());
				year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuEndDateYear());
				month = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setIkukyuEndDate(dateUtil.getDateYYYYMMDD());
			}
		}
// 苗　ここまで
//2007/02/08 苗　追加ここから
        //審査希望分野の名を設定
        String shinsaRyoikiName = LabelValueManager.getlabelName(
                                            shinseiForm.getShinsaKiboRyoikiList(), 
                                            shinseiForm.getShinseiDataInfo().getShinsaRyoikiCd());
        shinseiForm.getShinseiDataInfo().setShinsaRyoikiName(shinsaRyoikiName);
//2007/02/08　苗　追加ここまで        
		//-----申請登録メソッドを呼び出す		
		try{
			registApplication(container, shinseiForm);
		}catch(ValidationException e){
			List errorList = e.getErrors();
			for(int i=0; i<errorList.size(); i++){
				ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
				errors.add(errInfo.getProperty(),
						   new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
			}
		}
		

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		//------トークンの削除	
		resetToken(request);
		//------フォーム情報の削除  →ファイル変換アクションで利用するため削除しない。
		//removeFormBean(mapping,request);

		return forwardSuccess(mapping);

	}
	
	
	/**
	 * 申請書を登録する。
	 * @param container ログイン申請者情報
	 * @param form      申請入力フォームデータ
	 * @throws ValidationException  データチェックエラーが発生した場合
	 * @throws ApplicationException 申請登録に失敗した場合
	 */
	private void registApplication(UserContainer container, ShinseiForm form)
		throws ValidationException, ApplicationException
	{
		//添付ファイル
		FileResource annexFileRes = null;
		try{
			FormFile file = form.getUploadFile();
			if(file != null &&
			   file.getFileData() != null && 
			   file.getFileData().length != 0)
			{
				annexFileRes = new FileResource();
				annexFileRes.setPath(file.getFileName());	//ファイル名
				annexFileRes.setBinary(file.getFileData());	//ファイルサイズ
			}
		}catch(IOException e){
			throw new ApplicationException(
				"添付ファイルの取得に失敗しました。",
				new ErrorInfo("errors.7000"),
				e);
		}
		
		//研究経費情報の取得
		ShinseiDataInfo       shinseiDataInfo = form.getShinseiDataInfo();
 
//2007/03/15　苗　追加ここから        
        //年齢が0歳未満または100歳以上の場合はエラー
        //画面にも年齢は表示させない
        if (!StringUtil.isBlank(shinseiDataInfo.getDaihyouInfo().getNenrei())){
            int nenrei = Integer.parseInt(shinseiDataInfo.getDaihyouInfo().getNenrei());
            if(nenrei < 0 || nenrei > 99){     
                List errors = new ArrayList();
                String property = "nenreiInvalid";
                errors.add(new ErrorInfo("errors.9037", null, property));
                String message = "申請書データ形式チェックで検証エラーとなりました。";
                throw new ValidationException(message, errors);
            }
        }
//2007/03/15　苗　追加ここまで
        
		//2005.08.08 iso ファイルログ出力
		form.outputFileInfo();
		
		//-----新規か更新か分岐（受付番号がセットされているかどうか）
		String systemNo = shinseiDataInfo.getSystemNo();
		if(systemNo == null || systemNo.length() == 0){

//			2005/04/13 追加 ここから----------
//			理由:版追加のため

			//-----版を設定する（新規登録）
//2006/06/21 苗　修正ここから            
//			shinseiDataInfo.getKadaiInfo().setEdition(1);
            shinseiDataInfo.getKadaiInfo().setEdition(0);
//2006/06/21　苗　修正ここまで            
			
//			2005/04/13 追加 ここまで----------			
			
			//サーバサービスの呼び出し（新規登録）
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			ShinseiDataInfo newInfo = servise.registApplicationNew(
										container.getUserInfo(),
										shinseiDataInfo,
										annexFileRes);
			//フォームに登録された申請データをセットする
			form.setShinseiDataInfo(newInfo);
			
		}else{
			
//			2005/04/13 追加 ここから----------
//			理由:版追加のため

//2006/06/21 苗　削除ここから            
//			
//            //2005/08/17 申請状況が作成中(01)、及び申請書未確認(02)以外の場合、版をアップする
//			String jokyoId = shinseiDataInfo.getJokyoId();
//			if ( !(jokyoId.equals(StatusCode.STATUS_SAKUSEITHU) 
//				|| jokyoId.equals(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN)) ){
//				//-----版を設定する（更新登録）
//				int edition = shinseiDataInfo.getKadaiInfo().getEdition();
//				edition++;
//				shinseiDataInfo.getKadaiInfo().setEdition(edition);
//			}
//			//2005/8/19 一時保存後、初めて登録の場合、版を設定する
//			else if (shinseiDataInfo.getKadaiInfo().getEdition() == 0){
//				//-----版を設定する（初めて登録）
//
//				shinseiDataInfo.getKadaiInfo().setEdition(1);            
//			}
//2006/06/21　苗　削除ここまで 
            
//			2005/04/13 追加 ここまで----------
			
			//サーバサービスの呼び出し（更新登録）
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			ShinseiDataInfo newInfo = servise.registApplicationUpdate(
					container.getUserInfo(),
					shinseiDataInfo,
					annexFileRes);
			
//			2005/04/21 追加 ここから----------
//			理由:申請書に変更があり新規登録を行った場合、新しい申請データをセットする
			
			//フォームに登録された申請データをセットする
			form.setShinseiDataInfo(newInfo);
		}		
		
//			2005/04/13 追加 ここまで----------
		
	}
	
}