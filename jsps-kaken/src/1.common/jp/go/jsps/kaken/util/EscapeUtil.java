/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

/**
 * ������ɃG�X�P�[�v�������s���N���X�B
 *
 * ID RCSfile="$RCSfile: EscapeUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class EscapeUtil {

	//---------------------------------------------------------------------
	// Static Methods
	//---------------------------------------------------------------------

    /**
     * ������Ɋ܂܂��HTML���ꕶ�����G�X�P�[�v�ϊ����郁�\�b�h
     *
     * @param p �ϊ���������
     * @return �ϊ��㕶����
     */
    public static String toHtmlString(String p) {
    	if(p==null){
    		return p;
    	}
        String str = "";
        for (int i = 0; i < p.length(); i++) {
            Character c = new Character(p.charAt(i));
            switch (c.charValue()) {
                case '&' :
                    str = str.concat("&amp;");
                    break;
                case '<' :
                    str = str.concat("&lt;");
                    break;
                case '>' :
                    str = str.concat("&gt;");
                    break;
                case '"' :
                    str = str.concat("&quot;");
                    break;
                case '\'' :
                    str = str.concat("&#39;");
                    break;
                default :
                    str = str.concat(c.toString());
                    break;
            }
        }
        return str;
    }

    /**
     * ������Ɋ܂܂��SQL���ꕶ�����G�X�P�[�v�������郁�\�b�h
     * 
     * @param p �ϊ���������
     * @return �ϊ��㕶����
     */
    public static String toSqlString(String p) {
    	if(p==null){
    		return p;
    	}
        String str = "";
        for (int i = 0; i < p.length(); i++) {
            Character c = new Character(p.charAt(i));
            switch (c.charValue()) {
                case '\'' :
                    str = str.concat("''");
                    break;
                case '\\' :
                    str = str.concat("\\\\");
                    break;
                default :
                    str = str.concat(c.toString());
                    break;
            }
        }
        return str;
    }

	/**
	 * ������Ɋ܂܂��CSV���ꕶ�����G�X�P�[�v�������郁�\�b�h
	 * 
	 * @param p �ϊ���������
	 * @return �ϊ��㕶����
	 */
	public static String toCsvString(String p) {
    	if(p==null){
    		return p;
    	}
		String str = "";
		for (int i = 0; i < p.length(); i++) {
			Character c = new Character(p.charAt(i));
			switch (c.charValue()) {
				case '\"' :
					str = str.concat("\"\"");
					break;
				default :
					str = str.concat(c.toString());
					break;
			}
		}
		return str;
	}
}
