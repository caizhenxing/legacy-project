/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.document.config;

/**
 * 
 * =========================================== 标识位为1 第一位 1 存在图片 第二位 1 存在rm 第三位 1
 * 存在wmv 第三位 0 其它
 * 
 * ===========================================
 * 
 * @return
 * 
 * @throws
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetUBBImage {

    /**
     * 将标签转换
     * 
     * @param
     * 
     * @return
     * 
     * @throws
     */

    public static String changeIdentifier(List l) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < l.size(); i++) {
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "gif")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("jpg")) {
                sb.append(changeImg(l.get(i).toString()));
            }
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "rm")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("ram")) {
                sb.append(changeRm(l.get(i).toString()));
            }
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "wmv")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("asf")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("avi")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("mp3")) {
                sb.append(changeWmv(l.get(i).toString()));
            }
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "swf")) {
                sb.append(changeFlash(l.get(i).toString()));
            }
        }
        return sb.toString();
    }

    /**
     * 获得状态标识
     * 
     * @param
     * 
     * @return
     * 
     * @throws
     */

    public static String getState(List l) {
        String[] sb = new String[4];
        sb[0] = "0";
        sb[1] = "0";
        sb[2] = "0";
        sb[3] = "0";
        for (int i = 0; i < l.size(); i++) {
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "gif[/img]")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("jpg[/img]")) {
                sb[0] = "1";
            }
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "rm[/rm]")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("ram[/rm]")) {
                sb[1] = "1";
            }
            if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
                    "wmv[/mp]")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("asf[/mp]")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("avi[/mp]")
                    || GetFileName.getExtension(l.get(i).toString())
                            .equalsIgnoreCase("mp3[/mp]")) {
                sb[2] = "1";
            }
        }
        // StringBuffer temp = new StringBuffer();
        String result = "";
        for (int j = 0; j < sb.length; j++) {
            result = result + sb[j];
        }
        return result;
    }

    // 图片转换
    private static String changeImg(String temp) {
        String result = "";
        result = "[align=center][img]" + temp + "[/img][/align]";
        return result;
    }

    // 转换rm类型的
    private static String changeRm(String temp) {
        String result = "";
        result = "[align=center][rm=380,350]" + temp + "[/rm][/align]";
        return result;
    }

    // 替换wmv格式的
    private static String changeWmv(String temp) {
        String result = "";
        result = "[align=center][mp=380,350]" + temp + "[/mp][/align]";
        return result;
    }

    // 替换swf格式的
    private static String changeFlash(String temp) {
        String result = "";
        result = "[align=center][swf=380,350]" + temp + "[/swf][/align]";
        return result;
    }

    public static void main(String[] args) {
        List l = new ArrayList();
        String s1 = "[img]http://disco.9i5i.com/img/upload/200503041102.GIF[/img]";
        String s2 = "[rm]http://disco.9i5i.com/img/upload/200503041103.rm[/rm]";
        String s3 = "[rm]http://disco.9i5i.com/img/upload/200503041104.rm[/rm]";
        String s4 = "[mp]http://disco.9i5i.com/img/upload/200503041102.wmv[/mp]";
        l.add(s1);
        l.add(s2);
        l.add(s3);
        l.add(s4);
        // changeIdentifier(l);
        // String[] sb = new String[4];
        // sb[0] = "0";
        // sb[1] = "0";
        // sb[2] = "0";
        // sb[3] = "0";
        // for (int i = 0; i < l.size(); i++) {
        // if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
        // "gif")
        // || GetFileName.getExtension(l.get(i).toString())
        // .equalsIgnoreCase("jpg")) {
        // sb[0] = "1";
        // }
        // if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
        // "rm")
        // || GetFileName.getExtension(l.get(i).toString())
        // .equalsIgnoreCase("ram")) {
        // sb[1] = "1";
        // }
        // if (GetFileName.getExtension(l.get(i).toString()).equalsIgnoreCase(
        // "wmv")
        // || GetFileName.getExtension(l.get(i).toString())
        // .equalsIgnoreCase("asf")
        // || GetFileName.getExtension(l.get(i).toString())
        // .equalsIgnoreCase("avi")
        // || GetFileName.getExtension(l.get(i).toString())
        // .equalsIgnoreCase("mp3")) {
        // sb[2] = "1";
        // }
        // }
        // //StringBuffer temp = new StringBuffer();
        // String result = "";
        // for (int j = 0; j < sb.length; j++) {
        // result = result + sb[j];
        // }
        // 

        
        // List l1 = new ArrayList();
        // l1.add("4");
        // l.addAll(l1);
        // Iterator it = l.iterator();
        // while (it.hasNext()) {
        // 
        // }
    }

}
