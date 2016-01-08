/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import javax.servlet.http.HttpServletRequest;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * 形式チェック（若手研究(スタートアップ)）
 * ID RCSfile="$RCSfile: ShinseiValidatorWakatestart.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/11 06:51:41 $"
 */
public class ShinseiValidatorWakatestart extends DefaultShinseiValidator {

	/** 研究費を何年分入力するか */
	private int NENSU = 2;

	/** 研究経費システムMAX値 */
	private int MAX_KENKYUKEIHI = 1500;

	/** 研究経費合計最高金額(円) */
	private int MAX_KENKYUKEIHI_GOKEI = 9999999;

	/** 各年度の研究経費(万円) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * コンストラクタ
	 * 
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorWakatestart(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	/**
	 *形式チェック（若手研究(スタートアップ)）
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, int page, ActionErrors errors) {

		// 定型処理-----
		// ActionErrors errors = new ActionErrors();

		// 研究経費の合計を算出しつつ、最大値チェックを行う
		validateAndSetKeihiTotal(errors, page);

		//特別研究員奨励費内約額
		validateAndSetNaiyakugaku(errors, page);
		
		validateAndKinmuHour(errors,page);

//2007/03/20 劉長宇　修正　ここから
        if (page >= 2) {
            //2007/02/25 劉長宇　追加　ここから  
            validateAndShoreihiNoNendo(errors, page);
            //2007/02/25 劉長宇　追加　ここまで
        }
//2007/03/20 劉長宇　修正　ここまで
        return errors;
	}
	
	/**
	 * 勤務時間数して申請情報にセットする。
	 * 
	 * @param errors
	 *            検証エラーがあった場合は、errorsに追加していく。
	 * @param page
	 *            ページ番号（＝検証レベル）
	 */
	private void validateAndKinmuHour(ActionErrors errors, int page) {
		String name = "勤務時間数";// 項目名
		String property = "shinseiDataInfo.kinmuHour"; // プロパティ名
		int valueAnd = StringUtil.parseInt(shinseiDataInfo.getKinmuHour());
		int MINKINMUHOUR = 31;
		int MAXKINMUHOUR = 99;
		if(page >= 2){
			if (shinseiDataInfo.getKinmuHour().length() != 0
					&& shinseiDataInfo.getKinmuHour() != null) {
				//2006/4/11 勤務時間が100以上を入力された場合、桁数のチェックがあるので
				//重複エラーを表示しないように修正した。
				//if (valueAnd > MAXKINMUHOUR || valueAnd < MINKINMUHOUR) {
				if (valueAnd < MINKINMUHOUR) {
					ActionError error = new ActionError("errors.9024", name, String
							.valueOf(MINKINMUHOUR), String.valueOf(MAXKINMUHOUR));
					errors.add(property, error);

				}
			}
		}	
	}

	/**
	 * 特別研究員奨励費内約額して申請情報にセットする。
	 * 
	 * @param errors
	 *            検証エラーがあった場合は、errorsに追加していく。
	 * @param page
	 *            ページ番号（＝検証レベル）
	 */
	private void validateAndSetNaiyakugaku(ActionErrors errors, int page) {
		String name = "特別研究員奨励費内約額";// 項目名
		String property = "shinseiDataInfo.naiyakugaku"; // プロパティ名
		int valueAnd = StringUtil.parseInt(shinseiDataInfo.getNaiyakugaku());
		int MINNAIYAKUGAKU = 100;
		int MAXNAIYAKUGAKU = 3000;
		if(page >= 2){
			if (valueAnd != 0 ) {
				if (valueAnd > MAXNAIYAKUGAKU || valueAnd < MINNAIYAKUGAKU) {
					ActionError error = new ActionError("errors.9023", name, String
							.valueOf(MINNAIYAKUGAKU), String.valueOf(MAXNAIYAKUGAKU));
					errors.add(property, error);

				}
			}
		}
	}
	/**
	 * 研究経費の総計を算出して申請情報にセットする。
	 * 
	 * @param errors
	 *            検証エラーがあった場合は、errorsに追加していく。
	 * @param page
	 *            ページ番号（＝検証レベル）
	 */
	private void validateAndSetKeihiTotal(ActionErrors errors, int page) {

		//エラーメッセージ
		String name = null; //項目名
		String value = String.valueOf(MAX_KENKYUKEIHI); //値
		String value1 = String.valueOf(MAX_KENKYUKEIHI_GOKEI);//値
		String errKey = "errors.maxValue"; //キー文字列
		String property = "shinseidataInfo.kenkyuKeihiSoukeiInfo.keihiTotal"; //プロパティ名
//ADD　START 2007-07-11 BIS 劉多良
		//行目プロパティ
		String propertyRow = null;
//ADD　END 2007-07-11 BIS 劉多良

		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo
				.getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();

		//研究経費の総計を算出する
		int intKeihiTotal = 0;
		int intBihinhiTotal = 0;
		int intShomohinhiTotal = 0;
		int intKokunairyohiTotal = 0;
		int intGaikokuryohiTotal = 0;
		int intRyohiTotal = 0;
		int intShakinTotal = 0;
		int intSonotaTotal = 0;

		//年度ごとに繰り返す
		for (int i = 0; i < NENSU; i++) {
			//単年度
			int intBihinhi = StringUtil.parseInt(keihiInfo[i].getBihinhi());
			int intShomohinhi = StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			int intKokunairyohi = StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			int intGaikokuryohi = StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			int intRyohi = StringUtil.parseInt(keihiInfo[i].getRyohi());
			int intShakin = StringUtil.parseInt(keihiInfo[i].getShakin());
			int intSonota = StringUtil.parseInt(keihiInfo[i].getSonota());
			//単年度合計
			int intKeihi = intBihinhi + intShomohinhi + intKokunairyohi
					+ intGaikokuryohi + intRyohi + intShakin + intSonota;
			//単年度合計をオブジェクトにセットする
			keihiInfo[i].setKeihi(String.valueOf(intKeihi));

			int rowIndex = i + 1;
//ADD　START 2007-07-11 BIS 劉多良
			//行目を設定する
			propertyRow = property + (rowIndex - 1);
//ADD　END 2007-07-11 BIS 劉多良
			name = "研究経費 " + rowIndex + "行目";
//2007/03/26 劉長宇　更新　ここから
			//単年度合計の形式チェック
            if (MAX_KENKYUKEIHI_GOKEI < intKeihi) {
                ActionError error = new ActionError(errKey, name, value1);
                errors.add(property, error);
//ADD　START 2007-07-11 BIS 劉多良
				errors.add(propertyRow, error);
//ADD　END 2007-07-11 BIS 劉多良
            }
			
			//チェックは一時保存時には行わない
			if (page >= 2) {
				//単年度合計の最大値チェック(150万円以下)
				if (MAX_KENKYUKEIHI  <  intKeihi) {					
					ActionError error = new ActionError("errors.5033", name, value);
					errors.add(property, error);
//ADD　START 2007-07-11 BIS 劉多良
					errors.add(propertyRow, error);
//ADD　END 2007-07-11 BIS 劉多良
				}
//2007/03/26 劉長宇　更新　ここまで

				//研究経費は10万円以上でなければならない(1,2年目をのぞく)
				if (i == 0 || i == 1) {
					if (MIN_KENKYUKEIHI > intKeihi
							//新規の場合を条件に追加
							&& IShinseiMaintenance.SHINSEI_NEW
									.equals(shinseiDataInfo.getShinseiKubun())) {
						ActionError error = new ActionError("errors.5031",
								name, String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
//ADD　START 2007-07-11 BIS 劉多良
						errors.add(propertyRow, error);
//ADD　END 2007-07-11 BIS 劉多良
					}

				} 
			}

			//総計
			intKeihiTotal = intKeihiTotal + intKeihi;
			intBihinhiTotal = intBihinhiTotal
					+ StringUtil.parseInt(keihiInfo[i].getBihinhi());
			intShomohinhiTotal = intShomohinhiTotal
					+ StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			intKokunairyohiTotal = intKokunairyohiTotal
					+ StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			intGaikokuryohiTotal = intGaikokuryohiTotal
					+ StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			intRyohiTotal = intRyohiTotal
					+ StringUtil.parseInt(keihiInfo[i].getRyohi());
			intShakinTotal = intShakinTotal
					+ StringUtil.parseInt(keihiInfo[i].getShakin());
			intSonotaTotal = intSonotaTotal
					+ StringUtil.parseInt(keihiInfo[i].getSonota());
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
//		if (page == 2) {
//			if (MAX_KENKYUKEIHI_GOKEI < intKeihiTotal) {
//				name = "研究経費 総計";
//				ActionError error = new ActionError("errors.5033", name, String
//						.valueOf(MAX_KENKYUKEIHI_GOKEI));
//				errors.add(property, error);
//			}
//		}
		
		if (MAX_KENKYUKEIHI_GOKEI < intKeihiTotal) {
			name = "研究経費 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intBihinhiTotal) {
			name = "設備備品費 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intShomohinhiTotal) {
			name = "消耗品費 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intKokunairyohiTotal) {
			name = "国内旅費 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intGaikokuryohiTotal) {
			name = "外国旅費 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intRyohiTotal) {
			name = "旅費 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intShakinTotal) {
			name = "謝金等 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intSonotaTotal) {
			name = "その他 総計";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}

	}
    
//2007/02/25 劉長宇　追加　ここから  
    /**
     * 特別研究員奨励費課題番号-年度と特別研究員奨励費課題番号－整理番号をチェック。
     * 
     * @param errors
     *            検証エラーがあった場合は、errorsに追加していく。
     * @param page
     *            ページ番号（＝検証レベル）
     */
    private void validateAndShoreihiNoNendo(ActionErrors errors, int page) {

    	//特別研究員奨励費内約額に金額が入力された（0でない）場合、必須.
    	if (!StringUtil.isBlank(shinseiDataInfo.getNaiyakugaku())
                && StringUtil.parseInt(shinseiDataInfo.getNaiyakugaku()) != 0) {
//2007/03/13 劉長宇　修正　ここから        
            if (StringUtil.isBlank(shinseiDataInfo.getShoreihiNoNendo())) {
                ActionError error = new ActionError("errors.9033");
                errors.add("shinseiDataInfo.shoreihiNoNendo", error);
            }
            if (StringUtil.isBlank(shinseiDataInfo.getShoreihiNoSeiri())) {
                ActionError error = new ActionError("errors.9034");
                errors.add("shinseiDataInfo.shoreihiNoSeiri", error);
            }
        }
        //特別研究員奨励費内約額が0の場合、入力できません.
        if (!StringUtil.isBlank(shinseiDataInfo.getNaiyakugaku())
                && StringUtil.parseInt(shinseiDataInfo.getNaiyakugaku()) == 0) {
            if (!StringUtil.isBlank(shinseiDataInfo.getShoreihiNoNendo())) {
                ActionError error = new ActionError("errors.9035");
                errors.add("shinseiDataInfo.shoreihiNoNendo", error);
            }
            if (!StringUtil.isBlank(shinseiDataInfo.getShoreihiNoSeiri())) {
                ActionError error = new ActionError("errors.9036");
                errors.add("shinseiDataInfo.shoreihiNoSeiri", error);
            }

        }
//2007/03/13 劉長宇　修正　ここまで 
    }
//  2007/02/25 劉長宇　追加　ここまで

}
