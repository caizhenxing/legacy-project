/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShinseiValidatorTokuteiSinki.java
 *    Description : 応募情報入力(特定領域研究(新規領域))のチェック
 *
 *    Author      : 苗苗
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    v1.0        苗苗                        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

/**
 * 応募情報入力(特定領域研究(新規領域))のチェック
 */
public class ShinseiValidatorTokuteiSinki extends DefaultShinseiValidator {

    /**
     * コンストラクタ
     * @param shinseiDataInfo
     * @param page
     */
    public ShinseiValidatorTokuteiSinki(ShinseiDataInfo shinseiDataInfo) {
        super(shinseiDataInfo);
    }


    /**
     * 形式チェック（特定領域用）
     * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(
            ActionMapping mapping,
            HttpServletRequest request,
            int page,
            ActionErrors errors) {

        validateAndSetKeihiTotal(errors, request, page);    //研究経費の合計を算出しつつ、最大値チェックを行う
        validateKenkyuSoshiki(errors, page);                //研究組織表の形式チェックを行う
        countKenkyushaNinzu(errors, page);                  //研究者
        validateKomokuNo(errors);                           //研究項目番号の正規表現チェック

        return errors;
    }

    /**
     * 研究組織表より、全研究者数、他機関の研究者数を算出する。
     * また、研究経費の合計が申請データの研究経費（1年目）と同じ値になるかをチェックする。
     * 違っていた場合は ValidationException をスローする。
     * @param errors 検証エラーがあった場合は、errorsに追加していく。
     * @param page   ページ番号（＝検証レベル）
     */
    private void countKenkyushaNinzu(ActionErrors errors, int page) {
        int keihiTotal = 0;                                                         //研究組織表の研究経費合計
        List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList();        //研究組織表の代表者年齢を申請データにもセットする

        //研究代表者の所属機関コードを取得する
        DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
        String daihyouKikanCd = daihyouInfo.getShozokuCd();

        //研究代表者の年齢、他機関の研究者数を取得する
        int kenkyuNinzu  = kenkyuSoshikiList.size();
        int takikanNinzu = 0;
        int kyoryokushaNinzu = 0;

        for(int i=0; i<kenkyuNinzu; i++){
            KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo)kenkyuSoshikiList.get(i);

            if(i==0){                                                       //代表者（インデックス値が「0」のもの）
                daihyouInfo.setNenrei(kenkyushainfo.getNenrei());           //代表者の年齢を申請データ側にセットする
            }else if("2".equals(kenkyushainfo.getBuntanFlag())){            //分担者
                if( daihyouKikanCd != null &&                               //代表者の所属機関コードと違う場合はカウントプラス１
                   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
                {
                    takikanNinzu++;
                }
            } else {                                                        //協力者
                kyoryokushaNinzu++;
            }
            keihiTotal = keihiTotal + StringUtil.parseInt((String)kenkyushainfo.getKeihi());
        }
        shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu-kyoryokushaNinzu));   //研究者数
        shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu));                  //他機関の研究者数
        shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu));          //研究協力者数
        
        //本登録
        if(page >= 2){
            // 分担金の有無:「有」→「研究組織表」の「分担者」の記入があり、かつ、「研究経費」に入力があること（無ければエラー）。
            if(Integer.parseInt(shinseiDataInfo.getKenkyuNinzu()) == 1 &&
                "1".equals(shinseiDataInfo.getBuntankinFlg())){
                String property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
                ActionError error = new ActionError("errors.5055");
                errors.add(property, error);
//				<!-- ADD　START 2007/07/09 BIS 張楠 -->	
				shinseiDataInfo.getErrorsMap().put(property,"分担金の有無:「有」→「研究組織表」の「分担者」の記入があり、かつ、「研究経費」に入力があること（無ければエラ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD　END　 2007/07/09 BIS 張楠 -->	
            }
            //研究経費の合計が申請データの1年目研究経費と同じになっているかチェックする
            if(keihiTotal != 
               StringUtil.parseInt(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi6()[0].getKeihi())) {
                String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.kenkyuKeihi6";
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
    private void validateAndSetKeihiTotal(
            ActionErrors errors,
            HttpServletRequest request,
            int page){
        
        // 調整班がオン時
        if(IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.chouseiFlg"))) {
            shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_ON);//オン
        } else {
            shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);//オフ
        }             

        //エラーメッセージ
        String name         = null;                                                 //項目名
        String value        = String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI);  //値
        String errKey       = "errors.maxValue";                                    //キー文字列
        String property     = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";   //プロパティ名
//ADD　START 2007-07-13 BIS 劉多良
		//行目プロパティ
		String propertyRow = null;
//ADD　END 2007-07-13 BIS 劉多良

        KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo.getKenkyuKeihiSoukeiInfo();
        KenkyuKeihiInfo[]     keihiInfo  = soukeiInfo.getKenkyuKeihi6();

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
        for(int i=0; i<IShinseiMaintenance.NENSU_TOKUTEI_SINNKI; i++){
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
//ADD　START 2007-07-13 BIS 劉多良
			//行目を設定する
			propertyRow = property + (rowIndex - 1);
//ADD　END 2007-07-13 BIS 劉多良
            name = "研究経費 " + rowIndex + "行目";
            //単年度合計の形式チェック
            if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihi){
                ActionError error = new ActionError(errKey,name,value);
                errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
				errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
            }

            //チェックは一時保存時には行わない
            if(page >= 2){
                switch(i){
                    // １年目
                    case(0):
                        //(0または10万円以上)以外時
                        if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        break;
                    //２年目
                    case(1):
                        //(0または10万円以上)以外時
                        if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        break;
                    //３年目
                    case(2):
                        //前年度が0円の場合には0円
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " 前年度",
                                    "0円",
                                    ""
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        //(0または10万円以上)以外時
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        break;
                    //４年目
                    case(3):
                        //前年度が0円の場合には0円
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " 前年度",
                                    "0円",
                                    ""
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        //(0または10万円以上)以外時
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        break;
                    //５年目
                    case(4):
                        //前年度が0円の場合には0円
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " 前年度",
                                    "0円",
                                    ""
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        //(0または10万円以上)以外時
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        break;
                    //６年目
                    case(5):
                        //前年度が0円の場合には0円
                        if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5032",
                                    name + " 前年度",
                                    "0円",
                                    ""
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        //(0または10万円以上)以外時
                        else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
                            ActionError error = new ActionError(
                                    "errors.5031",
                                    name,
                                    String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
                                    );
                            errors.add(property, error);
//ADD　START 2007-07-13 BIS 劉多良
    						errors.add(propertyRow, error);
//ADD　END 2007-07-13 BIS 劉多良
                        }
                        break;   
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
        if(page == 2){
            if(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
                name = "研究経費 総計";
                ActionError error = new ActionError(
                        "errors.5033",
                        name,
                        String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI)
                        );
                errors.add(property, error);
            }
        }

        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihiTotal){
            name = "研究経費 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intBihinhiTotal){
            name = "設備備品費 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShomohinhiTotal){
            name = "消耗品費 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKokunairyohiTotal){
            name = "国内旅費 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intGaikokuryohiTotal){
            name = "外国旅費 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intRyohiTotal){
            name = "旅費 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShakinTotal){
            name = "謝金等 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
        if(IShinseiMaintenance.MAX_KENKYUKEIHI < intSonotaTotal){
            name = "その他 総計";
            ActionError error = new ActionError(errKey,name,value);
            errors.add(property, error);
        }
    }

    /**
     * 研究項目番号の正規表現チェック
     * 
     * @param errors 検証エラーがあった場合は、errorsに追加していく。
     */
    private void validateKomokuNo(ActionErrors errors){
        
        //研究項目番号
        String komokuNo = shinseiDataInfo.getRyouikiKoumokuNo();
        
        //３桁文字のチェック
        if(komokuNo != null && komokuNo.length() == 3){
            char komokuNoLetter = komokuNo.charAt(0);
            String komokuNoDigit = komokuNo.substring(1);

            //アルファベット１文字（大文字）でなければならない
            if (!StringUtil.isCapitalLetter(komokuNoLetter)) {
                ActionError error = new ActionError("errors.2001", "研究項目番号");
                errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                return;
            }

            //１桁目が「Ｘ」また「Ｙ」の場合，２～３桁目は，００のみ
            if (komokuNoLetter == 'X' || komokuNoLetter == 'Y') {
                if (!"00".equals(komokuNoDigit)) {
                    ActionError error = new ActionError("errors.2001", "研究項目番号");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }

            //１桁目がそれ以外のアルファベットの場合，２～３桁目は，０１～９９のみ    
            } else {
                if (!StringUtil.isDigit(komokuNoDigit)
                        || StringUtil.parseInt(komokuNoDigit) < 1
                        || StringUtil.parseInt(komokuNoDigit) > 99) {
                    ActionError error = new ActionError("errors.2001", "研究項目番号");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }
            }
        }
    }
}