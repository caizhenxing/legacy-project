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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * 審査結果入力情報値オブジェクトを更新する。
 * フォーム情報、更新情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInputSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaInputSaveAction extends BaseAction {

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
		//2005.10.14 確認画面と完了画面を統合のため、フォームからデータを取得
		//------セッションより更新情報の取得
//		ShinsaKekkaInputInfo addInfo = container.getShinsaKekkaInputInfo();

		
		//------新規登録フォーム情報の取得
		ShinsaKekkaForm addForm = (ShinsaKekkaForm) form;

		//-------▼ VOにデータをセットする。(基盤等)
		ShinsaKekkaInputInfo addInfo = container.getShinsaKekkaInputInfo();									

		addInfo.setKekkaAbc(addForm.getKekkaAbc());					//総合評価（ABC）
		addInfo.setKekkaTen(addForm.getKekkaTen());					//総合評価（点数）
		addInfo.setComment1(addForm.getComment1());					//コメント1
		addInfo.setComment2(addForm.getComment2());					//コメント2
		addInfo.setComment3(addForm.getComment3());					//コメント3
		addInfo.setComment4(addForm.getComment4());					//コメント4
		addInfo.setComment5(addForm.getComment5());					//コメント5
		addInfo.setComment6(addForm.getComment6());					//コメント6
		addInfo.setKenkyuNaiyo(addForm.getKenkyuNaiyo());			//研究内容
		addInfo.setKenkyuKeikaku(addForm.getKenkyuKeikaku());		//研究計画
		addInfo.setTekisetsuKaigai(addForm.getTekisetsuKaigai());	//適切性-海外
		addInfo.setTekisetsuKenkyu1(addForm.getTekisetsuKenkyu1());	//適切性-研究(1)
		addInfo.setTekisetsu(addForm.getTekisetsu());				//適切性
		addInfo.setDato(addForm.getDato());							//妥当性
		addInfo.setShinseisha(addForm.getShinseisha());				//研究代表者
		addInfo.setKenkyuBuntansha(addForm.getKenkyuBuntansha());	//研究分担者
		addInfo.setHitogenomu(addForm.getHitogenomu());				//ヒトゲノム
		addInfo.setTokutei(addForm.getTokutei());					//特定胚
		addInfo.setHitoEs(addForm.getHitoEs());						//ヒトES細胞
		addInfo.setKumikae(addForm.getKumikae());					//遺伝子組換え実験
		addInfo.setChiryo(addForm.getChiryo());						//遺伝子治療臨床研究
		addInfo.setEkigaku(addForm.getEkigaku());					//疫学研究
		addInfo.setComments(addForm.getComments());					//コメント
		//2005.10.26 kainuma
        //2006.10.26 楊艶飛　仕様変更より、基盤時　削除しました。
		//addInfo.setRigai(addForm.getRigai());						//利害関係
		addInfo.setWakates(addForm.getWakates());					//若手Sの妥当性2007/5/8
		addInfo.setJuyosei(addForm.getJuyosei());					//学術的重要性・妥当性
		addInfo.setDokusosei(addForm.getDokusosei());				//独創性・革新性
		addInfo.setHakyukoka(addForm.getHakyukoka());				//波及効果・普遍性
		addInfo.setSuikonoryoku(addForm.getSuikonoryoku());			//遂行能力・環境の適切性
		addInfo.setJinken(addForm.getJinken());						//人権の保護・法令等の遵守
		addInfo.setBuntankin(addForm.getBuntankin());				//分担金配分
		addInfo.setOtherComment(addForm.getOtherComment());			//その他コメント 
		   
		//------プルダウン・ラジオボタンの表示ラベルをセット
		if(addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
			//事業区分「1」だったら
			//総合評価（ABC）
//			addInfo.setKekkaAbcLabel(LabelValueManager.getKekkaAbcName(addForm.getKekkaAbc()));
			addInfo.setKekkaAbcLabel(LabelValueManager.getlabelName(addForm.getKekkaAbcList(), addForm.getKekkaAbc()));
//		}else if(addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//2006/04/10 更新ここから			
		} else if (addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) ||
				   addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) ||
                   addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)) {	
//苗　更新ここまで			
            //2006.10.26 楊艶飛　仕様変更より、基盤時　修正ここから
			//事業区分「4」と「6」だったら			
			//総合評価（点数）
//			if(!StringUtil.isBlank(addForm.getKekkaTen()) && addForm.getRigai().equals(IShinsaKekkaMaintenance.RIGAI_OFF)){
            if(!StringUtil.isBlank(addForm.getKekkaTen())){
				addInfo.setKekkaTenHogaLabel(LabelValueManager.getKekkaTenHogaName(addForm.getKekkaTen()));
				addInfo.setKekkaTenLabel(LabelValueManager.getKekkaTenName(addForm.getKekkaTen()));
			}
			//研究内容
//			if(addForm.getKenkyuNaiyo() != null && !addForm.getKenkyuNaiyo().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKenkyuNaiyo() != null && !addForm.getKenkyuNaiyo().equals("")){
//				addInfo.setKenkyuNaiyoLabel(LabelValueManager.getKenkyuNaiyoName(addForm.getKenkyuNaiyo()));
				addInfo.setKenkyuNaiyoLabel(LabelValueManager.getlabelName(addForm.getKenkyuNaiyoList(), addForm.getKenkyuNaiyo()));
			}
			//研究計画
//			if(addForm.getKenkyuKeikaku() != null && !addForm.getKenkyuKeikaku().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKenkyuKeikaku() != null && !addForm.getKenkyuKeikaku().equals("")){
//				addInfo.setKenkyuKeikakuLabel(LabelValueManager.getKenkyuKeikakuName(addForm.getKenkyuKeikaku()));
				addInfo.setKenkyuKeikakuLabel(LabelValueManager.getlabelName(addForm.getKenkyuKeikakuList(), addForm.getKenkyuKeikaku()));
			}
			//適切性-海外
//			if(addForm.getTekisetsuKaigai() != null && !addForm.getTekisetsuKaigai().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTekisetsuKaigai() != null && !addForm.getTekisetsuKaigai().equals("")){
//				addInfo.setTekisetsuKaigaiLabel(LabelValueManager.getTekisetsuKaigaiName(addForm.getTekisetsuKaigai()));
				addInfo.setTekisetsuKaigaiLabel(LabelValueManager.getlabelName(addForm.getTekisetsuKaigaiList(), addForm.getTekisetsuKaigai()));
			}
			//適切性-研究(1)
//			if(addForm.getTekisetsuKenkyu1() != null && !addForm.getTekisetsuKenkyu1().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTekisetsuKenkyu1() != null && !addForm.getTekisetsuKenkyu1().equals("")){
//				addInfo.setTekisetsuKenkyu1Label(LabelValueManager.getTekisetsuKenkyu1Name(addForm.getTekisetsuKenkyu1()));
				addInfo.setTekisetsuKenkyu1Label(LabelValueManager.getlabelName(addForm.getTekisetsuKenkyu1List(),addForm.getTekisetsuKenkyu1()));
			}
			//適切性
//			if(addForm.getTekisetsu() != null && !addForm.getTekisetsu().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTekisetsu() != null && !addForm.getTekisetsu().equals("")){
//				addInfo.setTekisetsuLabel(LabelValueManager.getTekisetsuName(addForm.getTekisetsu()));
				addInfo.setTekisetsuLabel(LabelValueManager.getlabelName(addForm.getTekisetsuList(), addForm.getTekisetsu()));
			}
			//妥当性
//			if(addForm.getDato() != null && !addForm.getDato().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getDato() != null && !addForm.getDato().equals("")){
//				addInfo.setDatoLabel(LabelValueManager.getDatoName(addForm.getDato()));
				addInfo.setDatoLabel(LabelValueManager.getlabelName(addForm.getDatoList(), addForm.getDato()));
			}
			//研究代表者
//			if(addForm.getShinseisha() != null && !addForm.getShinseisha().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getShinseisha() != null && !addForm.getShinseisha().equals("")){
//				addInfo.setShinseishaLabel(LabelValueManager.getShinseishaName(addForm.getShinseisha()));
				addInfo.setShinseishaLabel(LabelValueManager.getlabelName(addForm.getShinseishaList(), addForm.getShinseisha()));
			}
			//研究分担者
//			if(addForm.getKenkyuBuntansha() != null && !addForm.getKenkyuBuntansha().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKenkyuBuntansha() != null && !addForm.getKenkyuBuntansha().equals("")){
//				addInfo.setKenkyuBuntanshaLabel(LabelValueManager.getKenkyuBuntanshaName(addForm.getKenkyuBuntansha()));
				addInfo.setKenkyuBuntanshaLabel(LabelValueManager.getlabelName(addForm.getKenkyuBuntanshaList(), addForm.getKenkyuBuntansha()));
			}		
			//ヒトゲノム
//			if(addForm.getHitogenomu() != null && !addForm.getHitogenomu().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getHitogenomu() != null && !addForm.getHitogenomu().equals("")){
//				addInfo.setHitogenomuLabel(LabelValueManager.getHitogenomuName(addForm.getHitogenomu()));
				addInfo.setHitogenomuLabel(LabelValueManager.getlabelName(addForm.getHitogenomuList(), addForm.getHitogenomu()));
			}
			//特定胚
//			if(addForm.getTokutei() != null && !addForm.getTokutei().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getTokutei() != null && !addForm.getTokutei().equals("")){
//				addInfo.setTokuteiLabel(LabelValueManager.getTokuteiName(addForm.getTokutei()));
				addInfo.setTokuteiLabel(LabelValueManager.getlabelName(addForm.getTokuteiList(), addForm.getTokutei()));
			}		
			//ヒトＥＳ細胞
//			if(addForm.getHitoEs() != null && !addForm.getHitoEs().equals("") && addForm.getRigai().equals("0")) {
            if(addForm.getHitoEs() != null && !addForm.getHitoEs().equals("")){
//				addInfo.setHitoEsLabel(LabelValueManager.getHitoEsName(addForm.getHitoEs()));
				addInfo.setHitoEsLabel(LabelValueManager.getlabelName(addForm.getHitoEsList(), addForm.getHitoEs()));
			}
			//遺伝子組換え実験
//			if(addForm.getKumikae() != null && !addForm.getKumikae().equals("") && addForm.getRigai().equals("0")){
            if(addForm.getKumikae() != null && !addForm.getKumikae().equals("")){
//				addInfo.setKumikaeLabel(LabelValueManager.getKumikaeName(addForm.getKumikae()));
				addInfo.setKumikaeLabel(LabelValueManager.getlabelName(addForm.getKumikaeList(), addForm.getKumikae()));
			}
			//遺伝子治療臨床研究
//			if(addForm.getChiryo() != null && !addForm.getChiryo().equals("") && addForm.getRigai().equals("0")){	
            if(addForm.getChiryo() != null && !addForm.getChiryo().equals("")){
//				addInfo.setChiryoLabel(LabelValueManager.getChiryoName(addForm.getChiryo()));	
				addInfo.setChiryoLabel(LabelValueManager.getlabelName(addForm.getChiryoList(), addForm.getChiryo()));
			}
			//疫学研究
//			if(addForm.getEkigaku() != null && !addForm.getEkigaku().equals("") && addForm.getRigai().equals("0")){	
            if(addForm.getEkigaku() != null && !addForm.getEkigaku().equals("")){
//				addInfo.setEkigakuLabel(LabelValueManager.getEkigakuName(addForm.getEkigaku()));
				addInfo.setEkigakuLabel(LabelValueManager.getlabelName(addForm.getEkigakuList(), addForm.getEkigaku()));
			}

//			//2005.10.27 kainuma
//			//利害関係
//		    if(addForm.getRigai() != null && !addForm.getRigai().equals("")){
//			//addInfo.setRigaiLabel(LabelValueManager.getRigaiName(addForm.getRigai()));
//			    addInfo.setRigaiLabel(LabelValueManager.getlabelName(addForm.getRigaiList(), addForm.getRigai()));
//		    }

            //若手Sとしての妥当性ラベルを設定する 2007/5/8
            if (addForm.getWakates() != null && !addForm.getWakates().equals("")) {
                addInfo.setWakatesLabel(LabelValueManager.getlabelName(addForm.getJuyoseiList(), addForm.getWakates()));
            }
            
            //学術的重要性・妥当性 
			//利害関係がある場合は登録しない
//			if(addForm.getJuyosei() != null && !addForm.getJuyosei().equals("") && addForm.getRigai().equals("0")){
// 2006.10.27 楊艶飛 format修正ここから
            if (addForm.getJuyosei() != null
                    && !addForm.getJuyosei().equals("")) {
                // addInfo.setJuyoseiLabel(LabelValueManager.getJuyoseiName(addForm.getJuyosei()));
                addInfo.setJuyoseiLabel(LabelValueManager.getlabelName(addForm
                        .getJuyoseiList(), addForm.getJuyosei()));
            }
            // 独創性・革新性
            // 利害関係がある場合は登録しない
            // if(addForm.getDokusosei() != null &&
            // !addForm.getDokusosei().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getDokusosei() != null
                    && !addForm.getDokusosei().equals("")) {
                // addInfo.setDokusoseiLabel(LabelValueManager.getDokusoseiName(addForm.getDokusosei()));
                addInfo.setDokusoseiLabel(LabelValueManager.getlabelName(
                        addForm.getDokusoseiList(), addForm.getDokusosei()));
            }
            // 波及効果・普遍性
            // 利害関係がある場合は登録しない
            // if(addForm.getHakyukoka() != null &&
            // !addForm.getHakyukoka().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getHakyukoka() != null
                    && !addForm.getHakyukoka().equals("")) {
                // addInfo.setHakyukokaLabel(LabelValueManager.getHakyukokaName(addForm.getHakyukoka()));
                addInfo.setHakyukokaLabel(LabelValueManager.getlabelName(
                        addForm.getHakyukokaList(), addForm.getHakyukoka())); 
            }
            // 遂行能力・環境の適切性
            // 利害関係がある場合は登録しない
            // if(addForm.getSuikonoryoku() != null &&
            // !addForm.getSuikonoryoku().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getSuikonoryoku() != null
                    && !addForm.getSuikonoryoku().equals("")) {
                // addInfo.setSuikonoryokuLabel(LabelValueManager.getSuikonoryokuName(addForm.getSuikonoryoku()));
                addInfo.setSuikonoryokuLabel(LabelValueManager.getlabelName(
                        addForm.getSuikonoryokuList(), addForm
                                .getSuikonoryoku()));
            }
            // 人権の保護・法令等の遵守
            // 利害関係がある場合は登録しない
            // if(addForm.getJinken() != null && !addForm.getJinken().equals("")
            // && addForm.getRigai().equals("0")){
            if (addForm.getJinken() != null && !addForm.getJinken().equals("")) {
                // addInfo.setJinkenLabel(LabelValueManager.getJinkenName(addForm.getJinken()));
                addInfo.setJinkenLabel(LabelValueManager.getlabelName(addForm
                        .getJinkenList(), addForm.getJinken()));
            }
            // 分担金
            // 利害関係がある場合は登録しない
            // if(addForm.getBuntankin() != null &&
            // !addForm.getBuntankin().equals("") &&
            // addForm.getRigai().equals("0")){
            if (addForm.getBuntankin() != null
                    && !addForm.getBuntankin().equals("")) {
                // addInfo.setBuntankinLabel(LabelValueManager.getBuntankinhaibunName(addForm.getBuntankin()));
                addInfo.setBuntankinLabel(LabelValueManager.getlabelName(
                        addForm.getBuntankinList(), addForm.getBuntankin()));
            }
// 2006.10.27 format修正ここまで

// 2006.10.26 楊艶飛　仕様変更より、基盤時　削除ここから
//			//利害関係がある場合、審査結果以外の入力項目はクリアする
//			//if(addForm.getRigai().equals("1"))
//			if("1".equals(addForm.getRigai()))	//2005/11/14
//			{		
//				//学術的重要性・妥当性
//				addInfo.setJuyosei(null);		 
//				addInfo.setJuyoseiLabel(null); 
//				//研究計画
//				addInfo.setKenkyuKeikaku(null);
//				addInfo.setKenkyuKeikakuLabel(null);
//				//独創性・革新性
//				addInfo.setDokusosei(null);
//				addInfo.setDokusoseiLabel(null);
//				//波及効果・普遍性
//				addInfo.setHakyukoka(null);
//				addInfo.setHakyukokaLabel(null);
//				//遂行能力・環境の適切性
//				addInfo.setSuikonoryoku(null);
//				addInfo.setSuikonoryokuLabel(null);
//				//適切性-海外、萌芽
//				addInfo.setTekisetsuKaigai(null);
//				addInfo.setTekisetsuKaigaiLabel(null);
//				//総合評価（点数）
//				//2005/11/15 利害関係あり場合は「-」をセットとする
//				//addInfo.setKekkaTen(null);
//				addInfo.setKekkaTen("-");
//				addInfo.setKekkaTenLabel("-");
//				addInfo.setKekkaTenHogaLabel("-");
//				//妥当性
//				addInfo.setDato(null);
//				addInfo.setDatoLabel(null);
//				//分担金
//				addInfo.setBuntankin(null);
//				addInfo.setBuntankinLabel(null);  
//				//人権
//				addInfo.setJinken(null);
//				addInfo.setJinkenLabel(null);
//				//その他のコメント
//				addInfo.setOtherComment(null);
//			}
// 2006.10.26 楊艶飛　仕様変更より、基盤時　削除ここまで  
        }

		//-------アップロードファイルをセット
		//---添付ファイル
		//利害関係がある場合はファイルをセットしない
//     　2006.10.26 楊艶飛　仕様変更より、基盤時　修正ここから       
		//if(addForm.getRigai().equals("0"))
//	    if("0".equals(addForm.getRigai()) || 
//			addForm.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO))
		//2005/11/16 学創の場合も添付ファイルをセット
//       2006.10.27 format修正ここから       
        if (addForm.getJigyoKubun().equals(
                IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)) {
            FormFile tenpuFile = addForm.getTenpuUploadFile();
            if (tenpuFile != null && tenpuFile.getFileSize() != 0) {
                FileResource tenpuFileRes = createFileResorce(tenpuFile);
                addInfo.setHyokaFileRes(tenpuFileRes);
            } else {
                addInfo.setHyokaFileRes(null);
            }
        }
//      2006.10.27 format修正ここまで           
//     　2006.10.26 楊艶飛　仕様変更より、基盤時　修正ここまで        
		//-------▲

		//DB登録
		ISystemServise service = getSystemServise(
						IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE);
			service.regist1stShinsaKekka(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("審査結果入力情報　登録情報 '"+ addInfo);
		}
		
//		//-----セッションの審査結果入力情報をリセット
//		container.setShinsaKekkaInputInfo(null);
		
//2006/04/10 更新ここから	
		//-----セッションの審査結果入力情報をリセット
		container.setShinsaKekkaInputInfo(addInfo);
//苗　更新ここまで
		
		//-----フォームを削除
		removeFormBean(mapping, request);

//		完了画面に入力情報を表示するためここでセット
		request.setAttribute(IConstants.RESULT_INFO,addInfo);

		//------トークンの削除	
		resetToken(request);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

	/**
     * 添付ファイル生成
	 * @param file アップロードファイル
	 * @return ファイルリソース
	 */
	private FileResource createFileResorce(FormFile file)
            throws ApplicationException {
		FileResource fileRes = null;
		try{	
			if(file != null){
				fileRes = new FileResource();
				fileRes.setPath(file.getFileName());	//ファイル名
				fileRes.setBinary(file.getFileData());	//ファイルサイズ
			}
			return fileRes;
		}catch(IOException e){
			throw new ApplicationException(
				"添付ファイルの取得に失敗しました。",
				new ErrorInfo("errors.7000"),
				e);
		}
	}
}
