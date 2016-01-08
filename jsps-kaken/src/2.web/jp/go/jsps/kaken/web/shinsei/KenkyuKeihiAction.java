/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KenkyuKeihiAction.java
 *    Description : 研究組織経費確認画面アクション
 *
 *    Author      : DIS.liujia
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    V1.0        DIS.liujia    新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究組織経費確認画面アクション
 * ID RCSfile="$RCSfile: KenkyuKeihiAction.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/25 06:58:54 $"
 */
public class KenkyuKeihiAction extends BaseAction {

    /**
     * 該当申請データ管理テーブルのうち、JOKYO_ID=[21,23]の取得
     */
    private static String[] JOKYO_ID = new String[]{
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN,     // 申請状況：「領域代表者確認中」
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, // 申請状況：「領域代表者確定済み」
    };

    /**
     * Actionクラスの主要な機能を実装する。 戻り値として、次の遷移先をActionForward型で返する。
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
        throws ApplicationException {

        // -----ActionErrorsの宣言（定型処理）-----
        ActionErrors errors = new ActionErrors();

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        KenkyuSoshikiKenkyushaInfo kenkyusosikikeihiInfo = new KenkyuSoshikiKenkyushaInfo();
        kenkyusosikikeihiInfo.setJokyoId(JOKYO_ID);
        
        // 前画面で仮領域番号
        kenkyusosikikeihiInfo.setKariryoikiNo(ryoikiGaiyoForm
                .getRyoikikeikakushoInfo().getKariryoikiNo());

        // 研究項目年目小計情報
        List result = null;
        try {
            result = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .searchKenkyuKeihi(container.getUserInfo(),
                            kenkyusosikikeihiInfo);
        } catch (NoDataFoundException e) {
            errors.add("研究組織経費", new ActionError("errors.5002"));
        } catch (ApplicationException de) {
            errors.add("データ検索中にDBエラーが発生しました。", new ActionError("errors.4000"));
        }

        ryoikiGaiyoForm.setKeihi(result);

        //-----計画研究の研究項目うち、小計の部分情報-------
        List subtotalKeikaku = new ArrayList();

        // 研究組織経費の研究項目年目小計値
        for (int num = 1; num <= IShinseiMaintenance.NENSU_TOKUTEI_SINNKI; num++) {
            Integer keihi = new Integer(getKeikakuSubtotalInfo(result, "KEIHI" + num));
            subtotalKeikaku.add(keihi);
        }
        subtotalKeikaku.add(IShinseiMaintenance.NENSU_TOKUTEI_SINNKI,
                       new Integer(getKeikakuSubtotalInfo(result, "KEIHI_TOTAL")));
        ryoikiGaiyoForm.setKeihiTotal(subtotalKeikaku);

        // 公募研究の年目値
        List kenkyuSyokei = new ArrayList();
        RyoikiKeikakushoInfo rInfo = ryoikiGaiyoForm.getRyoikikeikakushoInfo();

// 2006/08/25 dyh update start 原因：仕様変更
//        int kenkyuSyo1 = 0;
//        if (StringUtil.isBlank(rInfo.getKenkyuSyokei1())) {
//            kenkyuSyo1 = getKouboSubtotalInfo(result, "KENKYU_SYOKEI_1");
//            rInfo.setKenkyuSyokei1(new Integer(kenkyuSyo1).toString());
//            kenkyuSyokei.add(0, new Integer(kenkyuSyo1));
//        } else {
//            kenkyuSyo1 = Integer.parseInt(rInfo.getKenkyuSyokei1());
//            kenkyuSyokei.add(0, Integer.valueOf(rInfo.getKenkyuSyokei1()));
//        }
//        if (StringUtil.isBlank(rInfo.getKenkyuUtiwake1())) {
//            String kenkyuUtiwake1 = getKouboUtiwakeInfo(result, "KENKYU_UTIWAKE_1");
//            rInfo.setKenkyuUtiwake1(kenkyuUtiwake1);
//        }

        //1年目
        kenkyuSyokei.add(0, new Integer(0));
// 2006/08/25 dyh update end

        //2年目
        int kenkyuSyo2 = 0;
        if (StringUtil.isBlank(rInfo.getKenkyuSyokei2())) {
            kenkyuSyo2 = getKouboSubtotalInfo(result, "KENKYU_SYOKEI_2");
            rInfo.setKenkyuSyokei2(new Integer(kenkyuSyo2).toString());
            kenkyuSyokei.add(1, new Integer(kenkyuSyo2));
        } else {
//        	<!-- UPDATE　START 2007/07/25 BIS 張楠 -->
// 			数字変換のチック
//        	<!-- 古いコード -->
            //kenkyuSyo2 = Integer.parseInt(rInfo.getKenkyuSyokei2());
            //kenkyuSyokei.add(1, Integer.valueOf(rInfo.getKenkyuSyokei2()));
        	if(StringUtil.isDigit(rInfo.getKenkyuSyokei2())){
        		if(rInfo.getKenkyuSyokei2().length()<10){
                    kenkyuSyo2 = Integer.parseInt(rInfo.getKenkyuSyokei2());
                    kenkyuSyokei.add(1, Integer.valueOf(rInfo.getKenkyuSyokei2()));
        		}else{
        			kenkyuSyokei.add(1, new Integer(0));
        		}
        	}else{
        		kenkyuSyokei.add(1, new Integer(0));
        	}
//        	<!-- UPDATE　END　 2007/07/25 BIS 張楠 -->
        }
        if (StringUtil.isBlank(rInfo.getKenkyuUtiwake2())) {
            String kenkyuUtiwake2 = getKouboUtiwakeInfo(result,
                    "KENKYU_UTIWAKE_2");
            rInfo.setKenkyuUtiwake2(kenkyuUtiwake2);
        }

        //3年目
        int kenkyuSyo3 = 0;
        if (StringUtil.isBlank(rInfo.getKenkyuSyokei3())) {
            kenkyuSyo3 = getKouboSubtotalInfo(result, "KENKYU_SYOKEI_3");
            rInfo.setKenkyuSyokei3(new Integer(kenkyuSyo3).toString());
            kenkyuSyokei.add(2, new Integer(kenkyuSyo3));
        } else {
//        	<!-- UPDATE　START 2007/07/25 BIS 張楠 -->
// 			数字変換のチック
//        	<!-- 古いコード -->
            //kenkyuSyo3 = Integer.parseInt(rInfo.getKenkyuSyokei3());
            //kenkyuSyokei.add(2, Integer.valueOf(rInfo.getKenkyuSyokei3()));
        	if(StringUtil.isDigit(rInfo.getKenkyuSyokei3())){
        		if(rInfo.getKenkyuSyokei3().length()<10){
                    kenkyuSyo3 = Integer.parseInt(rInfo.getKenkyuSyokei3());
                    kenkyuSyokei.add(2, Integer.valueOf(rInfo.getKenkyuSyokei3()));
        		}else{
        			kenkyuSyokei.add(2, new Integer(0));
        		}
        	}else{
        		kenkyuSyokei.add(2, new Integer(0));
        	}
//        	<!-- UPDATE　END　 2007/07/25 BIS 張楠 -->
        }
        if (StringUtil.isBlank(rInfo.getKenkyuUtiwake3())) {
            String kenkyuUtiwake3 = getKouboUtiwakeInfo(result, "KENKYU_UTIWAKE_3");
            rInfo.setKenkyuUtiwake3(kenkyuUtiwake3);
        }

        //4年目
        int kenkyuSyo4 = 0;
        if (StringUtil.isBlank(rInfo.getKenkyuSyokei4())) {
            kenkyuSyo4 = getKouboSubtotalInfo(result, "KENKYU_SYOKEI_4");
            rInfo.setKenkyuSyokei4(new Integer(kenkyuSyo4).toString());
            kenkyuSyokei.add(3, new Integer(kenkyuSyo4));
        } else {
//        	<!-- UPDATE　START 2007/07/25 BIS 張楠 -->
// 			数字変換のチック
//        	<!-- 古いコード -->
            //kenkyuSyo4 = Integer.parseInt(rInfo.getKenkyuSyokei4());
            //kenkyuSyokei.add(3, Integer.valueOf(rInfo.getKenkyuSyokei4()));
        	if(StringUtil.isDigit(rInfo.getKenkyuSyokei4())){
        		if(rInfo.getKenkyuSyokei4().length()<10){
                    kenkyuSyo4 = Integer.parseInt(rInfo.getKenkyuSyokei4());
                    kenkyuSyokei.add(3, Integer.valueOf(rInfo.getKenkyuSyokei4()));
        		}else{
        			kenkyuSyokei.add(3, new Integer(0));
        		}
        	}else{
        		kenkyuSyokei.add(3, new Integer(0));
        	}
//        	<!-- UPDATE　END　 2007/07/25 BIS 張楠 -->
        }
        if (StringUtil.isBlank(rInfo.getKenkyuUtiwake4())) {
            String kenkyuUtiwake4 = getKouboUtiwakeInfo(result, "KENKYU_UTIWAKE_4");
            rInfo.setKenkyuUtiwake4(kenkyuUtiwake4);
        }

        //5年目
        int kenkyuSyo5 = 0;
        if (StringUtil.isBlank(rInfo.getKenkyuSyokei5())) {
            kenkyuSyo5 = getKouboSubtotalInfo(result, "KENKYU_SYOKEI_5");
            rInfo.setKenkyuSyokei5(new Integer(kenkyuSyo5).toString());
            kenkyuSyokei.add(4, new Integer(kenkyuSyo5));
        } else {
//        	<!-- UPDATE　START 2007/07/25 BIS 張楠 -->
// 			数字変換のチック
//        	<!-- 古いコード -->
            //kenkyuSyo5 = Integer.parseInt(rInfo.getKenkyuSyokei5());
            //kenkyuSyokei.add(4, Integer.valueOf(rInfo.getKenkyuSyokei5()));
        	if(StringUtil.isDigit(rInfo.getKenkyuSyokei5())){
        		if(rInfo.getKenkyuSyokei5().length()<10){
                    kenkyuSyo5 = Integer.parseInt(rInfo.getKenkyuSyokei5());
                    kenkyuSyokei.add(4, Integer.valueOf(rInfo.getKenkyuSyokei5()));
        		}else{
        			kenkyuSyokei.add(4, new Integer(0));
        		}
        	}else{
        		kenkyuSyokei.add(4, new Integer(0));
        	}
//        	<!-- UPDATE　END　 2007/07/25 BIS 張楠 -->
        }
        if (StringUtil.isBlank(rInfo.getKenkyuUtiwake5())) {
            String kenkyuUtiwake5 = getKouboUtiwakeInfo(result, "KENKYU_UTIWAKE_5");
            rInfo.setKenkyuUtiwake5(kenkyuUtiwake5);
        }

        //6年目
        int kenkyuSyo6 = 0;
        if (StringUtil.isBlank(rInfo.getKenkyuSyokei6())) {
            kenkyuSyo6 = getKouboSubtotalInfo(result, "KENKYU_SYOKEI_6");
            rInfo.setKenkyuSyokei6(new Integer(kenkyuSyo6).toString());
            kenkyuSyokei.add(5, new Integer(kenkyuSyo6));
        } else {
//        	<!-- UPDATE　START 2007/07/25 BIS 張楠 -->
// 			数字変換のチック
//        	<!-- 古いコード -->
            //kenkyuSyo6 = Integer.parseInt(rInfo.getKenkyuSyokei6());
            //kenkyuSyokei.add(5, Integer.valueOf(rInfo.getKenkyuSyokei6()));
        	if(StringUtil.isDigit(rInfo.getKenkyuSyokei6())){
        		if(rInfo.getKenkyuSyokei6().length()<10){
                    kenkyuSyo6 = Integer.parseInt(rInfo.getKenkyuSyokei6());
                    kenkyuSyokei.add(5, Integer.valueOf(rInfo.getKenkyuSyokei6()));
        		}else{
        			kenkyuSyokei.add(5, new Integer(0));
        		}
        	}else{
        		kenkyuSyokei.add(5, new Integer(0));
        	}
//        	<!-- UPDATE　END　 2007/07/25 BIS 張楠 -->
        }
        if (StringUtil.isBlank(rInfo.getKenkyuUtiwake6())) {
            String kenkyuUtiwake6 = getKouboUtiwakeInfo(result, "KENKYU_UTIWAKE_6");
            rInfo.setKenkyuUtiwake6(kenkyuUtiwake6);
        }

        ryoikiGaiyoForm.setRyoikikeikakushoInfo(rInfo);

        // 公募研究の各年目合計値（右方）
// 2006/08/25 dyh updade start
//        int kenkyuSyoTotal = kenkyuSyo1 + kenkyuSyo2 + kenkyuSyo3 + kenkyuSyo4
//                           + kenkyuSyo5 + kenkyuSyo6;
        int kenkyuSyoTotal = 0 + kenkyuSyo2 + kenkyuSyo3 + kenkyuSyo4
                           + kenkyuSyo5 + kenkyuSyo6;
// 2006/08/25 dyh updade end
        rInfo.setKenkyuTotal(new Integer(kenkyuSyoTotal).toString());
        kenkyuSyokei.add(6, new Integer(kenkyuSyoTotal));

        // 公募研究の各年目合計値（下方）
        List kenkyuSyokeiTotal = new ArrayList();
        int intKeihiTotal = 0;
        int intKeikyuSyokeiTotal = 0;
        int total = 0;

        for (int i = 0; i <= 6; i++) {
            // 研究組織経費の研究項目年目小計値
            Integer intKenkyuTotal = (Integer)subtotalKeikaku.get(i);
            intKeihiTotal = intKenkyuTotal.intValue();
            // 公募研究の各年目値
            Integer intKenkyuSyokei = (Integer)kenkyuSyokei.get(i);
            intKeikyuSyokeiTotal = intKenkyuSyokei.intValue();
            total = intKeihiTotal + intKeikyuSyokeiTotal;
            kenkyuSyokeiTotal.add(new Integer(total));
        }
        ryoikiGaiyoForm.setKenkyuSyokeiTotal(kenkyuSyokeiTotal);

        // -----画面遷移（定型処理）-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // 検索結果をフォームにセットする。
        updateFormBean(mapping, request, ryoikiGaiyoForm);

        return forwardSuccess(mapping);
    }

    /**
     * 計画研究の小計の値で返する。
     * 
     * @param result 検索結果リスト(HashMapのリスト)
     * @param keihiName 検索された名前(String)
     * @return int 計画研究の小計の値した検索結果
     */
    private int getKeikakuSubtotalInfo(List result, String keikakuName) {
        if (result == null || result.size() == 0) {
            return 0;
        }
        int subtotal = 0;
        int keihi = 0;
        for (int i = 0; i < result.size(); i++) {
            BigDecimal keihiDecimal = (BigDecimal)((HashMap)result.get(i)).get(keikakuName);
            keihi = keihiDecimal.intValue();
            subtotal += keihi;
        }
        return subtotal;
    }

    /**
     * 公募研究の小計の値で返する。
     * 
     * @param result 検索結果リスト(HashMapのリスト)
     * @param kenkyuSyokeiName 検索された名前(String)
     * @return int 公募研究の各年度小計の値した検索結果
     */
    private int getKouboSubtotalInfo(List result, String kouboSyokeiName) {
        if (result == null || result.size() == 0) {
            return 0;
        }
        int kenkyuSyokei = 0;
        if (((HashMap)result.get(0)).get(kouboSyokeiName) == null) {
            return 0;
        }
        BigDecimal kenkyuSyokeiDecimal = (BigDecimal)((HashMap)result.get(0)).get(kouboSyokeiName);
        kenkyuSyokei = kenkyuSyokeiDecimal.intValue();

        return kenkyuSyokei;
    }

    /**
     * 公募研究の内訳の値で返する。
     * 
     * @param result 検索結果リスト(HashMapのリスト)
     * @param kenkyuUtiwakeName 検索された名前(String)
     * @return int 公募研究の各年度内訳の値した検索結果
     */
    private String getKouboUtiwakeInfo(List result, String kouboUtiwakeName) {
        if (result == null || result.size() == 0) {
            return null;
        }
        String kenkyuUtiwake = (String)((HashMap)result.get(0)).get(kouboUtiwakeName);
        return kenkyuUtiwake;
    }
}