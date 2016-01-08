/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SortUtil.java
 *    Description : �\�[�g�p���[�e�B���e�B�N���X�B
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/22
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/22    V1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * �\�[�g�p���[�e�B���e�B�N���X�B
 * ID RCSfile="$RCSfile: SortUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class SortUtil {

    /**
     * �������ʃ��X�g���\�[�g����
     * @param inList �������ʃ��X�g(HashMap�̃��X�g)
     * @return List �\�[�g�����������ʃ��X�g
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