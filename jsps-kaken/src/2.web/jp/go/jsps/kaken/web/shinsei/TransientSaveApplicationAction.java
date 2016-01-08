/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
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
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.*;

/**
 * 申請書一時保存アクションクラス。
 * 申請書情報をデータベースに登録する。
 * 処理が正常に終了した場合、一時保存完了画面を返す。
 * 
 * ID RCSfile="$RCSfile: TransientSaveApplicationAction.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/20 08:00:35 $"
 */
public class TransientSaveApplicationAction extends BaseAction {


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

//2006/02/15　追加　ここから	
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		String jigyoKubun = shinseiForm.getShinseiDataInfo().getKadaiInfo().getJigyoKubun();
		String year = "";
		String month = "";
		String day = "";
		DateUtil dateUtil = new DateUtil();
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
			if(!StringUtil.isBlank(shinseiForm.getSaiyoDateYear()) && 
					!StringUtil.isBlank(shinseiForm.getSaiyoDateMonth()) && 
					!StringUtil.isBlank(shinseiForm.getSaiyoDateDay())){
				year = DateUtil.changeWareki4Seireki(shinseiForm.getSaiyoDateYear());
				month = StringUtil.fillLZero(shinseiForm.getSaiyoDateMonth(), 2);
				day = StringUtil.fillLZero(shinseiForm.getSaiyoDateDay(), 2);
				dateUtil.setCal(year, month, day);
				shinseiForm.getShinseiDataInfo().setSaiyoDate(dateUtil.getDateYYYYMMDD());
			}
		} else if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
			if(ouboShikaku.equals("1")||ouboShikaku.equals("2")){
				if(!StringUtil.isBlank(shinseiForm.getSikakuDateYear()) && 
						!StringUtil.isBlank(shinseiForm.getSikakuDateMonth()) && 
						!StringUtil.isBlank(shinseiForm.getSikakuDateDay())){
					year = DateUtil.changeWareki4Seireki(shinseiForm.getSikakuDateYear());
					month = StringUtil.fillLZero(shinseiForm.getSikakuDateMonth(), 2);
					day = StringUtil.fillLZero(shinseiForm.getSikakuDateDay(), 2);
					dateUtil.setCal(year, month, day);
					shinseiForm.getShinseiDataInfo().setSikakuDate(dateUtil.getDateYYYYMMDD());
				}
			}
			if(ouboShikaku.equals("3")){
				if(!StringUtil.isBlank(shinseiForm.getIkukyuStartDateYear()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuStartDateMonth()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuStartDateDay())){
					year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuStartDateYear());
					month = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateMonth(), 2);
					day = StringUtil.fillLZero(shinseiForm.getIkukyuStartDateDay(), 2);
					dateUtil.setCal(year, month, day);
					shinseiForm.getShinseiDataInfo().setIkukyuStartDate(dateUtil.getDateYYYYMMDD());
				}
				if(!StringUtil.isBlank(shinseiForm.getIkukyuEndDateYear()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuEndDateMonth()) && 
						!StringUtil.isBlank(shinseiForm.getIkukyuEndDateDay())){
					year = DateUtil.changeWareki4Seireki(shinseiForm.getIkukyuEndDateYear());
					month = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateMonth(), 2);
					day = StringUtil.fillLZero(shinseiForm.getIkukyuEndDateDay(), 2);
					dateUtil.setCal(year, month, day);
					shinseiForm.getShinseiDataInfo().setIkukyuEndDate(dateUtil.getDateYYYYMMDD());
				}
			}
		}
// 苗　ここまで
		
		//-----仮保存メソッドを呼び出す
		try{
			transientSave(container, shinseiForm);
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

// 20050706
		//-----一時保存完了画面にて、SYSTEMNOが必要なためリクエストにセット
		ShinseiDataPk shinseiDataPk = new ShinseiDataPk();
		shinseiDataPk.setSystemNo(shinseiForm.getShinseiDataInfo().getSystemNo());
		//-----SYSTEMNOをリクエスト属性にセット
		request.setAttribute("shinseiDataPk", shinseiDataPk);
// Horikoshi

		//------トークンの削除	
		resetToken(request);
		//------フォーム情報の削除
		removeFormBean(mapping,request);		

		return forwardSuccess(mapping);
	}
	
	
	
	/**
	 * 申請書を仮保存する。
	 * @param container ログイン申請者情報
	 * @param form      申請入力フォームデータ
	 * @throws ValidationException  データチェックエラーが発生した場合
	 * @throws ApplicationException 仮保存に失敗した場合
	 */
	private void transientSave(UserContainer container, ShinseiForm form)
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
				new ErrorInfo("errors.7001"),
				e);
		}
		
		//研究経費情報の取得
		ShinseiDataInfo       shinseiDataInfo = form.getShinseiDataInfo();
 
//2007/03/15 苗　追加ここから        
        //年齢が0歳未満または100歳以上の場合はnullをセット
        //画面にも年齢は表示させない
        if (!StringUtil.isBlank(shinseiDataInfo.getDaihyouInfo().getNenrei())){
            int nenrei = Integer.parseInt(shinseiDataInfo.getDaihyouInfo().getNenrei());
            if(nenrei < 0 || nenrei > 99){
                //年齢にnullをセットし、画面に表示させない
                shinseiDataInfo.getDaihyouInfo().setNenrei(null);
                //研究組織情報に、年齢にnullをセットし、画面に表示させない
                if (shinseiDataInfo.getKenkyuSoshikiInfoList().size() >= 1) {
                    KenkyuSoshikiKenkyushaInfo kenkyushaInfo = 
                        (KenkyuSoshikiKenkyushaInfo) shinseiDataInfo.getKenkyuSoshikiInfoList().get(0);
                    kenkyushaInfo.setNenrei(null);
                }
            }
        }
//2007/03/15　苗　追加ここまで
        
		//2005.08.08 iso ファイルログ出力
		form.outputFileInfo();
		
		//-----新規か更新か分岐（受付番号がセットされているかどうか）
		String systemNo = shinseiDataInfo.getSystemNo();
		if(systemNo == null || systemNo.length() == 0){
			//サーバサービスの呼び出し（新規一時保存）
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			ShinseiDataInfo newInfo = servise.transientSaveNew(
										container.getUserInfo(),
										shinseiDataInfo,
										annexFileRes);
			//フォームに登録された申請データをセットする
			form.setShinseiDataInfo(newInfo);
			
		}else{
			//サーバサービスの呼び出し（更新一時保存）
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			servise.transientSaveUpdate(container.getUserInfo(),shinseiDataInfo, annexFileRes);
		}		
	}
	
}
