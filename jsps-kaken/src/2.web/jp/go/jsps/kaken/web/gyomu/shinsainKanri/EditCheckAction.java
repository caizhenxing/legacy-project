/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 更新された審査員情報の入力チェックを行う。
 * 審査員登録情報値オブジェクトを作成する。
 * 修正確認画面を表示する。 
 * 
 * ID RCSfile="$RCSfile: EditCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class EditCheckAction extends BaseAction {

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

		//------修正登録フォーム情報の取得
		ShinsainForm editForm = (ShinsainForm) form;
		
		//------セッションより更新対象情報の取得
		ShinsainInfo editInfo = container.getShinsainInfo();

		//VOにデータをセットする。
		editInfo.setShozokuCd(editForm.getShozokuCd());
		editInfo.setShozokuName(editForm.getShozokuName());
//		editInfo.setBukyokuCd(editForm.getBukyokuCd());
		editInfo.setBukyokuName(editForm.getBukyokuName());
//		editInfo.setShokushuCd(editForm.getShokushuCd());
		editInfo.setShokushuName(editForm.getShokushuName());
//		editInfo.setKeiCd(editForm.getKeiCd());
//		editInfo.setLevelA1(editForm.getLevelA1());
//		editInfo.setLevelB11(editForm.getLevelB11());
//		editInfo.setLevelB12(editForm.getLevelB12());
//		editInfo.setLevelB13(editForm.getLevelB13());
//		editInfo.setLevelB21(editForm.getLevelB21());
//		editInfo.setLevelB22(editForm.getLevelB22());
//		editInfo.setLevelB23(editForm.getLevelB23());
//		editInfo.setShinsaKahi(editForm.getShinsaKahi());
		editInfo.setSofuZip(editForm.getSofuZip());
		editInfo.setSofuZipaddress(editForm.getSofuZipaddress());
		editInfo.setSofuZipemail(editForm.getSofuZipemail());
//		editInfo.setJitakuTel(editForm.getJitakuTel());
		editInfo.setShozokuTel(editForm.getShozokuTel());
//		editInfo.setSinkiKeizokuFlg(editForm.getSinkiKeizokuFlg());
//		//選択された新規・継続
//		if(editForm.getSinkiKeizokuFlg() != null) {
//			editInfo.setSinkiKeizokuHyoji(LabelValueManager.getSinkiKeizokuFlgList(editForm.getSinkiKeizokuFlg()));
//		} else{
//			editInfo.setSinkiKeizokuHyoji(editForm.getSinkiKeizokuHyoji());
//		}
//		editInfo.setShakin(editForm.getShakin());
//		//選択された謝金名
//		if(editForm.getShakin() != null) {
//			editInfo.setShakinHyoji(LabelValueManager.getShakinList(editForm.getShakin()));
//		} else{
//			editInfo.setShakinHyoji(editForm.getShakinHyoji());
//		}
//		editInfo.setKey1(editForm.getKey1());
//		editInfo.setKey2(editForm.getKey2());
//		editInfo.setKey3(editForm.getKey3());
//		editInfo.setKey4(editForm.getKey4());
//		editInfo.setKey5(editForm.getKey5());
//		editInfo.setKey6(editForm.getKey6());
//		editInfo.setKey7(editForm.getKey7());
		editInfo.setUrl(editForm.getUrl());
		editInfo.setBiko(editForm.getBiko());
		
		editInfo.setNameKanjiSei(editForm.getNameKanjiSei());
		editInfo.setNameKanjiMei(editForm.getNameKanjiMei());
		editInfo.setNameKanaSei(editForm.getNameKanaSei());
		editInfo.setNameKanaMei(editForm.getNameKanaMei());
		editInfo.setShozokuFax(editForm.getShozokuFax());
		editInfo.setJigyoKubun(editForm.getJigyoKubun());
		editInfo.setSenmon(editForm.getSenmon());

//		2006/10/24 易旭 追加ここから
        if("1".equals(request.getParameter("downloadFlag"))){
            editInfo.setDownloadFlag("1");
        } else {
            editInfo.setDownloadFlag("0");
        }
//		2006/10/24 易旭 追加ここまで 
		
		//データ更新日付の設定
		DateUtil dateUtil = new DateUtil();
		editInfo.setKoshinDate(dateUtil.getCal().getTime());
		
//		//委嘱開始日（String→Date)
//		DateUtil dateUtil = new DateUtil();
//		String kizokuStartYear = editForm.getKizokuStartYear();
//		if(kizokuStartYear.equals("")){
//			//委嘱開始日・年が未入力の場合はnullをセットする
//			editInfo.setKizokuStart(null);
//		}else{
//			dateUtil.setCal(kizokuStartYear, editForm.getKizokuStartMonth(), editForm.getKizokuStartDay());
//			editInfo.setKizokuStart(dateUtil.getCal().getTime());
//		}
//		//委嘱終了日（String→Date)
//		dateUtil = new DateUtil();
//		String kizokuEndYear = editForm.getKizokuEndYear();
//		if(kizokuEndYear.equals("")){
//			//委嘱終了日・年が未入力の場合はnullをセットする
//			editInfo.setKizokuEnd(null);
//		}else{
//			dateUtil.setCal(kizokuEndYear, editForm.getKizokuEndMonth(), editForm.getKizokuEndDay());
//			editInfo.setKizokuEnd(dateUtil.getCal().getTime());
//		}
		//有効期限（String→Date)
		dateUtil = new DateUtil();
		dateUtil.setCal(editForm.getYukoDateYear(),editForm.getYukoDateMonth(),editForm.getYukoDateDay());
		editInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------▲
		
		try {
			//サーバ入力チェック
			editInfo =
				getSystemServise(
					IServiceName.SHINSAIN_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					editInfo,
					IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}
		
		//-----セッションに審査員情報を登録する。
		container.setShinsainInfo(editInfo);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
