/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * 形式チェッククラス（特別研究促進費（基盤研究(B)相当））
 * ID RCSfile="$RCSfile: ShinseiValidatorShokushinhiKibanB.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/13 07:18:05 $"
 */
public class ShinseiValidatorShokushinhiKibanB extends DefaultShinseiValidator {

	/** 研究費を何年分入力するか */
	private int NENSU = 4;

	/** 研究経費システムMAX値 */
	private int MAX_KENKYUKEIHI = 9999999;

	/** 研究経費合計最低金額(円) */
	private int MIN_KENKYUKEIHI_GOKEI = 5000;

	/** 研究経費合計最高金額(円) */
	private int MAX_KENKYUKEIHI_GOKEI = 20000;

	/** 各年度の研究経費(万円) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * コンストラクタ
	 * 
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorShokushinhiKibanB(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	/**
	 * 形式チェック（特別研究促進費（基盤研究(B)相当））
	 * 
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, int page, ActionErrors errors) {

		// ActionErrors errors = new ActionErrors();

		// 研究経費の合計を算出しつつ、最大値チェックを行う
		validateAndSetKeihiTotal(errors, page);

		// 若手は研究組織票が無いためチェックを行わない
		// 研究組織表の形式チェックを行う。
		validateKenkyuSoshiki(errors, page);

		countKenkyushaNinzu(errors, page);

		return errors;
	}

	/**
	 * 研究組織表より、全研究者数、他機関の研究者数を算出する。 また、研究経費の合計が申請データの研究経費（1年目）と同じ値になるかをチェックする。
	 * 違っていた場合は ValidationException をスローする。
	 * 
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page ページ番号（＝検証レベル）
	 */
	private void countKenkyushaNinzu(ActionErrors errors, int page) {
		int keihiTotal = 0; // 研究組織表の研究経費合計
		List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList(); // 研究組織表の代表者年齢を申請データにもセットする

		// 研究代表者の所属機関コードを取得する
		DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
		String daihyouKikanCd = daihyouInfo.getShozokuCd();

		// 研究代表者の年齢、他機関の研究者数を取得する
		int kenkyuNinzu = kenkyuSoshikiList.size();
		int takikanNinzu = 0;
		int kyoryokushaNinzu = 0;

		for (int i = 0; i < kenkyuNinzu; i++) {
			KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo) kenkyuSoshikiList
					.get(i);

			if (i == 0) { // 代表者（インデックス値が「0」のもの）
				daihyouInfo.setNenrei(kenkyushainfo.getNenrei()); // 代表者の年齢を申請データ側にセットする
			} else if ("2".equals(kenkyushainfo.getBuntanFlag())) { // 分担者
				if (daihyouKikanCd != null && // 代表者の所属機関コードと違う場合はカウントプラス１
						!daihyouKikanCd.equals(kenkyushainfo.getShozokuCd())) {
					takikanNinzu++;
				}
			} else { // 協力者
				kyoryokushaNinzu++;
			}
			keihiTotal = keihiTotal
					+ StringUtil.parseInt((String) kenkyushainfo.getKeihi());
		}
		shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu
				- kyoryokushaNinzu)); // 研究者数
		shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu)); // 他機関の研究者数
		shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu)); // 研究協力者数

		// 本登録
		if (page >= 2) {

			// 20050809 分担金の有無と研究人数を比較 ※有の場合には研究人数が2以上
			// ★研究分担者が存在しない場合にチェックをすり抜けていた
			if (Integer.parseInt(shinseiDataInfo.getKenkyuNinzu()) == 1
					&& "1".equals(shinseiDataInfo.getBuntankinFlg())) {
				String property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
				ActionError error = new ActionError("errors.5055");
				errors.add(property, error);
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				shinseiDataInfo.getErrorsMap().put(property,"分担金の有無:「有」→「研究組織表」の「分担者」の記入があり、かつ、「研究経費」に入力があること（無ければエラ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			}
			// Horikoshi
			// 研究経費の合計が申請データの1年目研究経費と同じになっているかチェックする
			if (keihiTotal != StringUtil.parseInt(shinseiDataInfo
					.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].getKeihi())) {
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.kenkyuKeihi";
				ActionError error = new ActionError("errors.5017");
				errors.add(property, error);
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				shinseiDataInfo.getErrorsMap().put(property,"研究経費(1年目)が研究組織表の研究経費合計と違います。 ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
			}
		}
	}

	/**
	 * 研究経費の総計を算出して申請情報にセットする。
	 * @param errors 検証エラーがあった場合は、errorsに追加していく。
	 * @param page   ページ番号（＝検証レベル）
	 */
	private void validateAndSetKeihiTotal(ActionErrors errors, int page)
	{
		
		//エラーメッセージ
		String name         = null;												//項目名
		String value        = String.valueOf(MAX_KENKYUKEIHI);						//値
		String errKey       = "errors.maxValue";									//キー文字列
		String property     = "shinseidataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";	//プロパティ名

		
		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo.getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[]     keihiInfo  = soukeiInfo.getKenkyuKeihi();
		
		//研究経費の総計を算出する
		int intKeihiTotal          = 0;
		int intBihinhiTotal        = 0;
		int intShomohinhiTotal     = 0;
		int intKokunairyohiTotal   = 0;
		int intGaikokuryohiTotal   = 0;
		int intRyohiTotal          = 0;
		int intShakinTotal         = 0;
		int intSonotaTotal         = 0;
		
		//年度ごとに繰り返す
		for(int i=0; i<NENSU; i++){
			//単年度
			int intBihinhi        = StringUtil.parseInt(keihiInfo[i].getBihinhi());
			int intShomohinhi     = StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			int intKokunairyohi   = StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			int intGaikokuryohi   = StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			int intRyohi          = StringUtil.parseInt(keihiInfo[i].getRyohi());
			int intShakin         = StringUtil.parseInt(keihiInfo[i].getShakin());
			int intSonota         = StringUtil.parseInt(keihiInfo[i].getSonota());
			//単年度合計
			int intKeihi          = intBihinhi
								   + intShomohinhi
								   + intKokunairyohi
								   + intGaikokuryohi
								   + intRyohi
								   + intShakin
								   + intSonota
								   ;
			//単年度合計をオブジェクトにセットする
			keihiInfo[i].setKeihi(String.valueOf(intKeihi)); 

			int rowIndex = i+1;
			name = "研究経費 " + rowIndex + "行目";
			//単年度合計の形式チェック
			if(MAX_KENKYUKEIHI < intKeihi){
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
			}
			
			//チェックは一時保存時には行わない
			if(page >= 2){
				//本登録でのみチェックする
				if(page >= 2){
					//研究経費は10万円以上でなければならない(3年目と4年目をのぞく)
					if(i==2 || i==3){
						//新規・継続区分が新規の場合のみに条件を追加
//						if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0){	
						if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0
//2006/08/22　苗　削除ここから                                
//								&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22　苗　削除ここまで                                
								){
							ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
							errors.add(property, error);
						}
					//1年目は新規と同じルール
					}else if(i == 0){
						if(MIN_KENKYUKEIHI > intKeihi){	
							ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
							errors.add(property, error);
						}

					}else{
						if(MIN_KENKYUKEIHI > intKeihi
//2006/08/22　苗　削除ここから                                  
//								&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22　苗　削除ここまで                                 
								){	
							ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
							errors.add(property, error);
						}
					}

//					//一年目以降で前年度の値が「0」でないことをチェック
//					if(i>0
/*					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())	*/
//						){
//						int lastKeihi = StringUtil.parseInt(keihiInfo[i-1].getKeihi());
//						if(		lastKeihi == 0		//前年度の研究経費が0
//							&&	intKeihi != 0		//研究経費が0以外
//							){
//							ActionError error = new ActionError(
//									"errors.5032",
//									"研究経費 " + String.valueOf(i) + "行目",
//									"0円",
//									"研究経費 " + String.valueOf(i+1) + "行目"
//									);
//							errors.add(property, error);
//						}
//					}
				}
			}

			//総計
			intKeihiTotal          = intKeihiTotal        + intKeihi;
			intBihinhiTotal        = intBihinhiTotal      + StringUtil.parseInt(keihiInfo[i].getBihinhi());
			intShomohinhiTotal     = intShomohinhiTotal   + StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			intKokunairyohiTotal   = intKokunairyohiTotal + StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			intGaikokuryohiTotal   = intGaikokuryohiTotal + StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			intRyohiTotal          = intRyohiTotal        + StringUtil.parseInt(keihiInfo[i].getRyohi());
			intShakinTotal         = intShakinTotal       + StringUtil.parseInt(keihiInfo[i].getShakin());
			intSonotaTotal         = intSonotaTotal       + StringUtil.parseInt(keihiInfo[i].getSonota());
		}
		
		//チェックは一時保存時には行わない
		if(page >= 2){
			//本登録でのみチェックする
			if(page >= 2){
				//-----研究経費の組み合わせチェック-----
				//3年目の研究経費が０円の場合、4年目も０円でなければならない
				if("0".equals(keihiInfo[2].getKeihi()) && !("0".equals(keihiInfo[3].getKeihi()))){
					ActionError error = new ActionError("errors.5032","研究経費 3行目","0円","研究経費 4行目");
					errors.add(property, error);
				}
//2006/08/22　苗　削除ここから                
				//申請区分が継続の場合は、4年目は０円でなければならない
//				if("2".equals(shinseiDataInfo.getShinseiKubun()) && !("0".equals(keihiInfo[3].getKeihi()))){
//					ActionError error = new ActionError("errors.5032","新規・継続区分","継続","研究経費 4行目");
//					errors.add(property, error);
//				}
//2006/08/22　苗　削除ここまで                
			}
		}
		
		//総計をセットする
		soukeiInfo.setKeihiTotal(String.valueOf(intKeihiTotal));
		soukeiInfo.setBihinhiTotal(String.valueOf(intBihinhiTotal));
		soukeiInfo.setShomohinhiTotal(String.valueOf(intShomohinhiTotal));
		soukeiInfo.setKokunairyohiTotal(String.valueOf(intKokunairyohiTotal));
		soukeiInfo.setGaikokuryohiTotal(String.valueOf(intGaikokuryohiTotal));
		soukeiInfo.setRyohiTotal(String.valueOf(intRyohiTotal));
		soukeiInfo.setShakinTotal(String.valueOf(intShakinTotal));
		soukeiInfo.setSonotaTotal(String.valueOf(intSonotaTotal));
		
		
		//-----総計の形式チェック-----
		
		//範囲のチェックは一時保存時には行わない
		if(page >= 2){
			if(MIN_KENKYUKEIHI_GOKEI > intKeihiTotal
//2006/08/22　苗　削除ここから                      
					//新規の場合を追加
//					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22　苗　削除ここまで                    
					){
				name = "研究経費 総計";
				ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI_GOKEI));
				errors.add(property, error);
			}
			if(MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
				name = "研究経費 総計";
				ActionError error = new ActionError("errors.5033",name,String.valueOf(MAX_KENKYUKEIHI_GOKEI));
				errors.add(property, error);
			}
		}
		
		//システム上限のチェック
		if(MAX_KENKYUKEIHI < intKeihiTotal){
			name = "研究経費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intBihinhiTotal){
			name = "設備備品費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intShomohinhiTotal){
			name = "消耗品費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intKokunairyohiTotal){
			name = "国内旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intGaikokuryohiTotal){
			name = "外国旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intRyohiTotal){
			name = "旅費 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intShakinTotal){
			name = "謝金等 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}		
		if(MAX_KENKYUKEIHI < intSonotaTotal){
			name = "その他 総計";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}		
	}
}