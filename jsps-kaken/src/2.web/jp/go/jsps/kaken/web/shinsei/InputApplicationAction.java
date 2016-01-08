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
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;
import jp.go.jsps.kaken.web.common.LabelValueManager;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書入力前アクションクラス。
 * フォームに申請書入力録画面に必要なデータをセットする。
 * 申請書入力画面を表示する。
 * 
 * ID RCSfile="$RCSfile: InputApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class InputApplicationAction extends BaseAction {

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
		ShinseiForm shinseiForm = getInputNewForm(container, (ShinseiForm)form);
		
// 2006/02/08 Start 年度リストを追加する
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
		//計画研究・公募研究・終了研究領域区分リスト
		shinseiForm.setKenkyuKubunList(LabelValueManager.getKenkyuKubunList());

// 2006/02/15	追加 -----------------------------------------------ここから	
		shinseiForm.getShinseiDataInfo().setNaiyakugaku("0");
// syuu         追加 -----------------------------------------------ここまで
		//2005/03/28 追加 -----------------------------------------------ここから
		//理由 開示希望の有無追加のため
		shinseiForm.setKaijiKiboList(LabelValueManager.getKaijiKiboList());
		//2005/03/28 追加 -----------------------------------------------ここまで

		//2005/04/08 追加 ここから----------------------------
		//理由：研究対象の類型追加のため
		shinseiForm.setKenkyuTaishoList(LabelValueManager.getKenkyuTaishoList());
		//追加　ここまで--------------------------------------

		//2005/04/18 追加 ここから----------------------------       
		//理由：審査希望分野追加のため
		shinseiForm.setShinsaKiboBunyaList(LabelValueManager.getKaigaiBunyaList());
		//追加　ここまで--------------------------------------
//2007/02/08　苗　削除ここから    審査希望分野はラベルマスタに取得を変更する        
//		//2006/02/15
//        //審査分野名
//		shinseiForm.setShinsaKiboRyoikiList(LabelValueManager.getKiboRyoikiList());
//		//syuu End
//2007/02/08　苗　削除ここまで        
		//2005/04/18 追加 ここから----------------------------
		//理由：費用の0埋め追加
		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiForm.getShinseiDataInfo().getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[] keihiList = soukeiInfo.getKenkyuKeihi();
//2006/06/14 追加ここから        
//		for(int nensu=0;nensu < 5;nensu++){
        for(int nensu=0;nensu < IShinseiMaintenance.NENSU;nensu++){    
//苗　追加ここまで            
			keihiList[nensu].setKeihi("0");
			keihiList[nensu].setBihinhi("0");
			keihiList[nensu].setShomohinhi("0");
			keihiList[nensu].setKokunairyohi("0");
			keihiList[nensu].setGaikokuryohi("0");
			keihiList[nensu].setRyohi("0");
			keihiList[nensu].setShakin("0");
			keihiList[nensu].setSonota("0");
		}
//2006/07/03 苗　追加ここから
        KenkyuKeihiInfo[] keihiList6 = soukeiInfo.getKenkyuKeihi6();
        for(int nensu=0;nensu < IShinseiMaintenance.NENSU_TOKUTEI_SINNKI;nensu++){               
            keihiList6[nensu].setKeihi("0");
            keihiList6[nensu].setBihinhi("0");
            keihiList6[nensu].setShomohinhi("0");
            keihiList6[nensu].setKokunairyohi("0");
            keihiList6[nensu].setGaikokuryohi("0");
            keihiList6[nensu].setRyohi("0");
            keihiList6[nensu].setShakin("0");
            keihiList6[nensu].setSonota("0");
        }
//2006/07/03　苗　追加ここまで        
		soukeiInfo.setKeihiTotal("0");
		soukeiInfo.setBihinhiTotal("0");
		soukeiInfo.setShomohinhiTotal("0");
		soukeiInfo.setKokunairyohiTotal("0");
		soukeiInfo.setGaikokuryohiTotal("0");
		soukeiInfo.setRyohiTotal("0");
		soukeiInfo.setShakinTotal("0");
		soukeiInfo.setSonotaTotal("0");
// 20050803 項目追加
		soukeiInfo.setMeetingCost("0");		//会議費
		soukeiInfo.setPrintingCost("0");	//印刷費
// Horikoshi
		//追加　ここまで--------------------------------------
		
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
	 * 申請書新規入力用フォーム取得メソッド。
	 * @param container 申請者情報
	 * @param form 申請書入力フォーム
	 * @return 新規用申請書入力フォーム
	 * @throws ApplicationException
	 */
	private ShinseiForm getInputNewForm(UserContainer container, ShinseiForm form)
		throws ApplicationException
	{
		//事業IDを取得する
		String jigyoId = form.getShinseiDataInfo().getJigyoId();
		
		//事業管理主キーオブジェクトの生成
		JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(jigyoId);
		
		//DBよりレコードを取得
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			Map resultMap = servise.selectShinseiDataForInput(container.getUserInfo(),jigyoKanriPk);
		
		//申請情報、各プルダウンメニューリスト取得
		ShinseiDataInfo shinseiDataInfo = (ShinseiDataInfo)resultMap.get(ISystemServise.KEY_SHINSEIDATA_INFO);
		List            keiKubunList    = (List)resultMap.get(ISystemServise.KEY_KEI_KUBUN_LIST);
		List            suisenList      = (List)resultMap.get(ISystemServise.KEY_SUISEN_LIST);
		List            shokushuList    = (List)resultMap.get(ISystemServise.KEY_SHOKUSHU_LIST);

// 20050526 Start 計画研究・公募研究・終了研究領域区分
		List			ryouikiList		= (List)resultMap.get(ISystemServise.KEY_RYOUIKI_LIST);
// Horikoshi End
		
// 2007/02/08 苗　追加ここから
        List            kiboubumonList  = (List)resultMap.get(ISystemServise.KEY_KIBOUBUMON_WAKA_LIST);
//　2007/02/08　苗　追加ここまで        
// 2006/02/08 Start 申請資格フラグを追加
		shinseiDataInfo.setOuboShikaku(container.getUserInfo().getShinseishaInfo().getOuboshikaku());
// Nae End
		
//		申請入力フォームの生成
		ShinseiForm inputForm = new ShinseiForm();
		inputForm.setShinseiDataInfo(shinseiDataInfo);
		inputForm.setKeitouList(keiKubunList);
		inputForm.setSuisenList(suisenList);
		inputForm.setShokushuList(shokushuList);
        
//2007/02/08 苗　追加ここから
        inputForm.setShinsaKiboRyoikiList(kiboubumonList);
//2007/02/08　苗　追加ここまで        

// 20050527 Start
		inputForm.setKenkyuKubunList(ryouikiList);
// Horikoshi End

		inputForm.setYoshikiShubetu(shinseiDataInfo.getJigyoCd().substring(2,4));

		return inputForm;
		
	}
}
