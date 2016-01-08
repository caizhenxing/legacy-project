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

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KadaiInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書入力前アクションクラス。
 * フォームに申請書入力録画面に必要なデータをセットする。
 * 申請書入力画面を表示する。
 * 
 * ID RCSfile="$RCSfile: UpdateApplicationAction.java,v $"
 * Revision="$Revision: 1.8 $"
 * Date="$Date: 2007/07/26 08:18:43 $"
 */
public class UpdateApplicationAction extends BaseAction {

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

		//-----申請書入力フォームの取得
		ShinseiForm shinseiForm = getUpdateForm(container, (ShinseiForm)form);
		
//2006/02/08 Start 年度リストを追加する
		//-----画面の年度リストを設定する
		String nendo = shinseiForm.getShinseiDataInfo().getNendo();
		String pre1Nendo = Integer.toString(Integer.parseInt(nendo) - 1);
		String pre2Nendo = Integer.toString(Integer.parseInt(nendo) - 2);
		String pre3Nendo = Integer.toString(Integer.parseInt(nendo) - 3);
		
		//資格取得年度リストを設定する
		List sikakuDateList = new ArrayList();
		sikakuDateList.add(new LabelValueBean(pre1Nendo, pre1Nendo));
		sikakuDateList.add(new LabelValueBean(nendo, nendo));
		shinseiForm.setSikakuDateList(sikakuDateList);
		
        //育休等開始年度リストを設定する
		List ikukyuStartDateList = new ArrayList();
		ikukyuStartDateList.add(new LabelValueBean(pre3Nendo, pre3Nendo));
		ikukyuStartDateList.add(new LabelValueBean(pre2Nendo, pre2Nendo));
		ikukyuStartDateList.add(new LabelValueBean(pre1Nendo, pre1Nendo));
		shinseiForm.setIkukyuStartDateList(ikukyuStartDateList);
		
		DateUtil dateUtil = new DateUtil();
		String jigyoKubun = shinseiForm.getShinseiDataInfo().getKadaiInfo().getJigyoKubun();
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
			if ( ouboShikaku == null ||!("1".equals(ouboShikaku) || "2".equals(ouboShikaku) || "3"
					.equals(ouboShikaku))){
				ActionError error = new ActionError("errors.9021");
				errors.add(null,error);
			}else{
				if(ouboShikaku.equals("1")||ouboShikaku.equals("2")){
					if(shinseiForm.getShinseiDataInfo().getSikakuDate() != null){
						String sikakuDate = shinseiForm.getShinseiDataInfo().getSikakuDate().toString();
						dateUtil.setCal(sikakuDate.substring(0,4),"4","1");
						shinseiForm.setSikakuDateYear(dateUtil.getNendo());
						shinseiForm.setSikakuDateMonth(Integer.toString(Integer.parseInt(sikakuDate.substring(5,7))));
						shinseiForm.setSikakuDateDay(Integer.toString(Integer.parseInt(sikakuDate.substring(8,10))));
					}
				}else if(ouboShikaku.equals("3")){
					if(shinseiForm.getShinseiDataInfo().getIkukyuStartDate() != null){
						String ikukyuStartDate = shinseiForm.getShinseiDataInfo().getIkukyuStartDate().toString();
						dateUtil.setCal(ikukyuStartDate.substring(0,4),"4","1");
						shinseiForm.setIkukyuStartDateYear(dateUtil.getNendo());
						shinseiForm.setIkukyuStartDateMonth(Integer.toString(Integer.parseInt(ikukyuStartDate.substring(5,7))));
						shinseiForm.setIkukyuStartDateDay(Integer.toString(Integer.parseInt(ikukyuStartDate.substring(8,10))));
					}
					if(shinseiForm.getShinseiDataInfo().getIkukyuEndDate() != null){
						String ikukyuEndDate = shinseiForm.getShinseiDataInfo().getIkukyuEndDate().toString();
						dateUtil.setCal(ikukyuEndDate.substring(0,4),"4","1");
						shinseiForm.setIkukyuEndDateYear(dateUtil.getNendo());
						shinseiForm.setIkukyuEndDateMonth(Integer.toString(Integer.parseInt(ikukyuEndDate.substring(5,7))));
						shinseiForm.setIkukyuEndDateDay(Integer.toString(Integer.parseInt(ikukyuEndDate.substring(8,10))));
					}
				}
			}
		}
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
			if(shinseiForm.getShinseiDataInfo().getSaiyoDate() != null){
				String saiyoDate = shinseiForm.getShinseiDataInfo().getSaiyoDate().toString();
				dateUtil.setCal(saiyoDate.substring(0,4),"4","1");
				shinseiForm.setSaiyoDateYear(dateUtil.getNendo());
				shinseiForm.setSaiyoDateMonth(Integer.toString(Integer.parseInt(saiyoDate.substring(5,7))));
				shinseiForm.setSaiyoDateDay(Integer.toString(Integer.parseInt(saiyoDate.substring(8,10))));
			}
		}
// Nae End
		
		//-----申請書入力フォーム固定のプルダウンリストをセット
		//新規・継続リスト
		shinseiForm.setShinkiKeibetuList(LabelValueManager.getSinkiKeizokuFlgList());
		//事業CDを取得
		String jigyoCd = shinseiForm.getShinseiDataInfo().getJigyoCd();
		//研究計画最終年度前年度の応募リスト
		if (jigyoCd.equals(IJigyoCd.JIGYO_CD_TOKUSUI)) {
			//特別推進事業
			shinseiForm.setZennendoList(LabelValueManager.getZennendoOboListTokusui());
		}else{
			//特別推進事業以外
			shinseiForm.setZennendoList(LabelValueManager.getZennendoOboList(false));
		}
		//分担金の有無リスト
		shinseiForm.setBuntankinList(LabelValueManager.getBuntankinList());

		//2005/03/31 追加 -----------------------------------------------ここから
		//理由 開示希望の有無追加のため
		shinseiForm.setKaijiKiboList(LabelValueManager.getKaijiKiboList());
		//2005/03/31 追加 -----------------------------------------------ここまで
				
		//2005/04/08 追加 ここから----------------------------
		//理由：研究対象の類型追加のため
		shinseiForm.setKenkyuTaishoList(LabelValueManager.getKenkyuTaishoList());
		//追加　ここまで--------------------------------------
		
		//2005/04/18 追加 ここから----------------------------
		//理由：審査希望分野追加のため
		shinseiForm.setShinsaKiboBunyaList(LabelValueManager.getKaigaiBunyaList());
		//追加　ここまで--------------------------------------
		
//		2005/04/13 追加 ここから----------
//		理由:分割番号に"-"が表示されてしまうため
		//分割番号が入力されなかった場合は、"-"がDBに登録されているので空白に戻す
		KadaiInfo kadaiInfo = shinseiForm.getShinseiDataInfo().getKadaiInfo();
		if("-".equals(kadaiInfo.getBunkatsuNo())){
			kadaiInfo.setBunkatsuNo("");
		}
//		2005/04/13 追加 ここまで----------

// 20050530 Start 特定領域(研究区分)の追加
		shinseiForm.setKenkyuKubunList(LabelValueManager.getKenkyuKubunList());
// Horikoshi End

// 2007/02/08　苗　削除ここから    審査希望分野はラベルマスタに取得を変更する         
////		2006/02/15
//        //審査分野名
//		shinseiForm.setShinsaKiboRyoikiList(LabelValueManager.getKiboRyoikiList());
//		//syuu End
// 2007/02/08 苗　削除ここまで
        
//2007/02/08 苗　追加ここから
        //審査希望分野の名を設定
        String shinsaRyoikiName = LabelValueManager.getlabelName(
                                            shinseiForm.getShinsaKiboRyoikiList(), 
                                            shinseiForm.getShinseiDataInfo().getShinsaRyoikiCd());
        shinseiForm.getShinseiDataInfo().setShinsaRyoikiName(shinsaRyoikiName);
//2007/02/08　苗　追加ここまで      

		//-----申請書入力フォームにセットする。
		updateFormBean(mapping, request, shinseiForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
				
		return forwardSuccess(mapping);
		
	}
	
	

	/**
	 * 申請書修正入力用フォーム取得メソッド。
	 * @param container 申請者情報
	 * @param form　申請書入力フォーム
	 * @return 修正用申請書入力フォーム
	 * @throws ApplicationException
	 */
	private ShinseiForm getUpdateForm(UserContainer container, ShinseiForm form)
		throws ApplicationException
	{
		//システム受付Noを取得する
		String systemNo = form.getShinseiDataInfo().getSystemNo();
		
		//事業管理主キーオブジェクトの生成
		ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
		
		//DBよりレコードを取得
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			Map resultMap = servise.selectShinseiDataForInput(container.getUserInfo(),shinseiDataPk);
		
		//申請情報、各プルダウンメニューリスト取得
		ShinseiDataInfo shinseiDataInfo = (ShinseiDataInfo)resultMap.get(ISystemServise.KEY_SHINSEIDATA_INFO);
		List            keiKubunList    = (List)resultMap.get(ISystemServise.KEY_KEI_KUBUN_LIST);
		List            suisenList      = (List)resultMap.get(ISystemServise.KEY_SUISEN_LIST);
		List            shokushuList    = (List)resultMap.get(ISystemServise.KEY_SHOKUSHU_LIST);
// 20050527 Start 特定領域用のプルダウンメニューリストを追加
		List            ryouikiList		= (List)resultMap.get(ISystemServise.KEY_RYOUIKI_LIST);
// Horikoshi End
// 2007/02/08 苗　追加ここから
        List            kiboubumonList  = (List)resultMap.get(ISystemServise.KEY_KIBOUBUMON_WAKA_LIST);
//　2007/02/08　苗　追加ここまで
		
// 2006/02/08 Start 申請資格フラグを追加
		shinseiDataInfo.setOuboShikaku(container.getUserInfo().getShinseishaInfo().getOuboshikaku());
// Nae End

		//申請入力フォームの生成
		ShinseiForm inputForm = new ShinseiForm();
		inputForm.setShinseiDataInfo(shinseiDataInfo);
		inputForm.setKeitouList(keiKubunList);
		inputForm.setSuisenList(suisenList);
		inputForm.setShokushuList(shokushuList);
// 20050527 Start
		inputForm.setKenkyuKubunList(ryouikiList);
// Horikoshi End
		inputForm.setYoshikiShubetu(shinseiDataInfo.getJigyoCd().substring(2,4));
//2007/02/08 苗　追加ここから
        inputForm.setShinsaKiboRyoikiList(kiboubumonList);
//2007/02/08　苗　追加ここまで

		return inputForm;
		
	}
}
