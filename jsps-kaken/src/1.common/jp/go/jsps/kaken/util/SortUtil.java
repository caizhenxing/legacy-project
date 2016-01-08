/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SortUtil.java
 *    Description : ソート用ユーティリティクラス。
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/22
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/22    V1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ソート用ユーティリティクラス。
 * ID RCSfile="$RCSfile: SortUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class SortUtil {

    /**
     * 検索結果リストをソートする
     * @param inList 検索結果リスト(HashMapのリスト)
     * @return List ソートした検索結果リスト
     */
    public static List sortByKomokuNo(List inList) {
        if(inList == null || inList.size() == 0){
            return inList;
        }
        List outList = new ArrayList();
        List x00List = new ArrayList();
        List y00List = new ArrayList();
        List otherList = new ArrayList();
        for (int i = 0; i < inList.size(); i++) {
            String fieldValue = (String)((HashMap)inList.get(i)).get("KOMOKU_NO");
            if("X00".equalsIgnoreCase(fieldValue)){
                x00List.add(inList.get(i));
            }else if("Y00".equalsIgnoreCase(fieldValue)){
                y00List.add(inList.get(i));
            }else{
                otherList.add(inList.get(i));
            }
        }
        outList.addAll(x00List);
        outList.addAll(y00List);
        outList.addAll(otherList);
        return outList;
    }
}