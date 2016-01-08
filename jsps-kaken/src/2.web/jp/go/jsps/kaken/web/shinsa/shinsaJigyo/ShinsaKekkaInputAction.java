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

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 審査結果入力画面表示前アクションクラス。
 * 審査結果入力画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInputAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaInputAction extends BaseAction {

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
		
		//------キー情報
		ShinsaKekkaPk pkInfo  = new ShinsaKekkaPk();
		pkInfo.setSystemNo(((ShinsaKekkaForm)form).getSystemNo());		//システム受付番号
		pkInfo.setShinsainNo(container.getUserInfo().getShinsainInfo().getShinsainNo());	//審査員番号
		pkInfo.setJigyoKubun(container.getUserInfo().getShinsainInfo().getJigyoKubun());	//事業区分	
			
		//------キー情報を元に更新データ取得	
		ShinsaKekkaInputInfo selectInfo = 
					getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).select1stShinsaKekka(
								container.getUserInfo(),
								pkInfo);
		
		//------更新対象審査結果情報より、登録フォーム情報の更新
		ShinsaKekkaForm inputForm = new ShinsaKekkaForm();
		
		try {
			PropertyUtils.copyProperties(inputForm, selectInfo);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//------ラジオボタンデータセット
		//2005.10.26 iso まとめて取得するよう変更
		String[] labelNames = {ILabelKubun.KEKKA_ABC, ILabelKubun.KEKKA_TEN, ILabelKubun.KEKKA_TEN_HOGA,ILabelKubun.KENKYUNAIYO,
								ILabelKubun.KENKYUKEIKAKU, ILabelKubun.TEKISETSU_KAIGAI, ILabelKubun.TEKISETSU_KENKYU1,
								ILabelKubun.TEKISETSU, ILabelKubun.DATO, ILabelKubun.SHINSEISHA,
								ILabelKubun.KENKYUBUNTANSHA, ILabelKubun.HITOGENOMU, ILabelKubun.TOKUTEI,
								ILabelKubun.HITOES, ILabelKubun.KUMIKAE, ILabelKubun.CHIRYO,
								ILabelKubun.EKIGAKU,ILabelKubun.RIGAI,ILabelKubun.JUYOSEI,ILabelKubun.DOKUSOSEI,
								ILabelKubun.HAKYUKOKA,ILabelKubun.SUIKONORYOKU,ILabelKubun.JINKEN,
								ILabelKubun.BUNTANKIN};
		HashMap labelMap = (HashMap)LabelValueManager.getLabelMap(labelNames);
		
		//総合評価（ABC）
//		inputForm.setKekkaAbcList(LabelValueManager.getKekkaAbcList());
		inputForm.setKekkaAbcList((List)labelMap.get(ILabelKubun.KEKKA_ABC));
		//総合評価（点数）
//		inputForm.setKekkaTenList(LabelValueManager.getKekkaTenList());
		inputForm.setKekkaTenList((List)labelMap.get(ILabelKubun.KEKKA_TEN));
		
		//総合評価（萌芽）
		inputForm.setKekkaTenHogaList((List)labelMap.get(ILabelKubun.KEKKA_TEN_HOGA));
		
		//研究内容
//		inputForm.setKenkyuNaiyoList(LabelValueManager.getKenkyuNaiyoList());
		inputForm.setKenkyuNaiyoList((List)labelMap.get(ILabelKubun.KENKYUNAIYO));
		//研究計画
//		inputForm.setKenkyuKeikakuList(LabelValueManager.getKenkyuKeikakuList());
		inputForm.setKenkyuKeikakuList((List)labelMap.get(ILabelKubun.KENKYUKEIKAKU));
		//適切性-海外
//		inputForm.setTekisetsuKaigaiList(LabelValueManager.getTekisetsuKaigaiList());
		inputForm.setTekisetsuKaigaiList((List)labelMap.get(ILabelKubun.TEKISETSU_KAIGAI));
		//適切性-研究（1）
//		inputForm.setTekisetsuKenkyu1List(LabelValueManager.getTekisetsuKenkyu1List());
		inputForm.setTekisetsuKenkyu1List((List)labelMap.get(ILabelKubun.TEKISETSU_KENKYU1));
		//適切性
//		inputForm.setTekisetsuList(LabelValueManager.getTekisetsuList());
		inputForm.setTekisetsuList((List)labelMap.get(ILabelKubun.TEKISETSU));
		//妥当性
//		inputForm.setDatoList(LabelValueManager.getDatoList());
		inputForm.setDatoList((List)labelMap.get(ILabelKubun.DATO));
		//研究代表者
//		inputForm.setShinseishaList(LabelValueManager.getShinseishaList());
		inputForm.setShinseishaList((List)labelMap.get(ILabelKubun.SHINSEISHA));
		//研究分担者
//		inputForm.setKenkyuBuntanshaList(LabelValueManager.getKenkyuBuntanshaList());
		inputForm.setKenkyuBuntanshaList((List)labelMap.get(ILabelKubun.KENKYUBUNTANSHA));
		//ヒトゲノム
//		inputForm.setHitogenomuList(LabelValueManager.getHitogenomuList());
		inputForm.setHitogenomuList((List)labelMap.get(ILabelKubun.HITOGENOMU));
		//特定胚
//		inputForm.setTokuteiList(LabelValueManager.getTokuteiList());
		inputForm.setTokuteiList((List)labelMap.get(ILabelKubun.TOKUTEI));
		//ヒトES細胞
//		inputForm.setHitoEsList(LabelValueManager.getHitoEsList());
		inputForm.setHitoEsList((List)labelMap.get(ILabelKubun.HITOES));	
		//遺伝子組換え実験
//		inputForm.setKumikaeList(LabelValueManager.getKumikaeList());
		inputForm.setKumikaeList((List)labelMap.get(ILabelKubun.KUMIKAE));
		//遺伝子治療臨床研究
//		inputForm.setChiryoList(LabelValueManager.getChiryoList());
		inputForm.setChiryoList((List)labelMap.get(ILabelKubun.CHIRYO));
		//疫学研究
//		inputForm.setEkigakuList(LabelValueManager.getEkigakuList());
		inputForm.setEkigakuList((List)labelMap.get(ILabelKubun.EKIGAKU));

		//2005.10.26 kainuma
		//利害関係
		inputForm.setRigaiList((List)labelMap.get(ILabelKubun.RIGAI));
		//学術的重要性・妥当性
		inputForm.setJuyoseiList((List)labelMap.get(ILabelKubun.JUYOSEI));
		//独創性・革新性
		inputForm.setDokusoseiList((List)labelMap.get(ILabelKubun.DOKUSOSEI));	
		//波及効果・普遍性
		inputForm.setHakyukokaList((List)labelMap.get(ILabelKubun.HAKYUKOKA));
		//遂行能力・環境の適切性
		inputForm.setSuikonoryokuList((List)labelMap.get(ILabelKubun.SUIKONORYOKU));
		//人権の保護・法令等の遵守
		inputForm.setJinkenList((List)labelMap.get(ILabelKubun.JINKEN));
		//分担金配分
		inputForm.setBuntankinList((List)labelMap.get(ILabelKubun.BUNTANKIN));
		

		
		//------表示対象情報をセッションに登録。
		container.setShinsaKekkaInputInfo(selectInfo);

		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, inputForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//2005.10.14 確認画面と完了画面を統合
		//トークンをセッションに保存する。
		saveToken(request);
			  
		return forwardSuccess(mapping);
	}

}
