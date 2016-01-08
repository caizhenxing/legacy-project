/**
 * 
 * 
 */
package base.zyf.common.util.string;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhangfeng �ַ���������
 */
public class StringUtil {

	/**
	 * �ж��Ƿ�Ϊ��
	 * 
	 * @param str
	 *            �ж�ĳ�ַ����Ƿ�Ϊ�գ�Ϊ�յı�׼�� str==null �� str.length()==0 ������ StringUtils
	 *            �ж��Ƿ�Ϊ�յ�ʾ����
	 * 
	 * StringUtils.isEmpty(null) = true StringUtils.isEmpty("") = true
	 * StringUtils.isEmpty(" ") = false //ע���� StringUtils �пո����ǿմ���
	 * StringUtils.isEmpty(" ") = false StringUtils.isEmpty("bob") = false
	 * StringUtils.isEmpty(" bob ") = false
	 * 
	 * 
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * �ж��Ƿ�ǿ�
	 * 
	 * @param str
	 *            �ж�ĳ�ַ����Ƿ�ǿգ����� !isEmpty(String str) ������ʾ����
	 * 
	 * StringUtils.isNotEmpty(null) = false StringUtils.isNotEmpty("") = false
	 * StringUtils.isNotEmpty(" ") = true StringUtils.isNotEmpty(" ") = true
	 * StringUtils.isNotEmpty("bob") = true StringUtils.isNotEmpty(" bob ") =
	 * true
	 * 
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}

	/**
	 * �ж�ĳ�ַ����Ƿ�Ϊ�ջ򳤶�Ϊ0���ɿհ׷�(whitespace) ���� ������ʾ���� StringUtils.isBlank(null) =
	 * true StringUtils.isBlank("") = true StringUtils.isBlank(" ") = true
	 * StringUtils.isBlank(" ") = true StringUtils.isBlank("\t \n \f \r") = true
	 * //�����Ʊ�������з�����ҳ���ͻس���
	 * 
	 * StringUtils.isBlank() //��ʶΪ�հ׷� StringUtils.isBlank("\b") = false
	 * //"\b"Ϊ���ʱ߽�� StringUtils.isBlank("bob") = false StringUtils.isBlank(" bob ") =
	 * false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * �ж�ĳ�ַ����Ƿ�Ϊ���ҳ��Ȳ�Ϊ0�Ҳ��ɿհ׷�(whitespace) ���ɣ����� !isBlank(String str) ������ʾ����
	 * 
	 * StringUtils.isNotBlank(null) = false StringUtils.isNotBlank("") = false
	 * StringUtils.isNotBlank(" ") = false StringUtils.isNotBlank(" ") = false
	 * StringUtils.isNotBlank("\t \n \f \r") = false
	 * StringUtils.isNotBlank("\b") = true StringUtils.isNotBlank("bob") = true
	 * StringUtils.isNotBlank(" bob ") = true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * ȥ���ַ������˵Ŀ��Ʒ�(control characters, char <= 32) , �������Ϊ null �򷵻�null ������ʾ����
	 * StringUtils.trim(null) = null StringUtils.trim("") = ""
	 * StringUtils.trim(" ") = "" StringUtils.trim(" \b \t \n \f \r ") = ""
	 * StringUtils.trim(" \n\tss \b") = "ss" StringUtils.trim(" d d dd ") = "d d
	 * dd" StringUtils.trim("dd ") = "dd" StringUtils.trim(" dd ") = "dd"
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return StringUtils.trim(str);
	}

	/**
	 * ȥ���ַ������˵Ŀ��Ʒ�(control characters, char <= 32) ,�����Ϊ null ��""���򷵻� null
	 * ������ʾ���� StringUtils.trimToNull(null) = null StringUtils.trimToNull("") =
	 * null StringUtils.trimToNull(" ") = null StringUtils.trimToNull(" \b \t \n
	 * \f \r ") = null StringUtils.trimToNull(" \n\tss \b") = "ss"
	 * StringUtils.trimToNull(" d d dd ") = "d d dd" StringUtils.trimToNull("dd ") =
	 * "dd" StringUtils.trimToNull(" dd ") = "dd"
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToNull(String str) {
		return StringUtils.trimToNull(str);
	}

	/**
	 * ȥ���ַ������˵Ŀ��Ʒ�(control characters, char <= 32) ,�����Ϊ null �� "" ���򷵻� ""
	 * ������ʾ���� StringUtils.trimToEmpty(null) = "" StringUtils.trimToEmpty("") = ""
	 * StringUtils.trimToEmpty(" ") = "" StringUtils.trimToEmpty(" \b \t \n \f
	 * \r ") = "" StringUtils.trimToEmpty(" \n\tss \b") = "ss"
	 * StringUtils.trimToEmpty(" d d dd ") = "d d dd"
	 * StringUtils.trimToEmpty("dd ") = "dd" StringUtils.trimToEmpty(" dd ") =
	 * "dd"
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
		return StringUtils.trimToEmpty(str);
	}
	
	/**
	 * ȥ���ַ������˵Ŀհ׷�(whitespace) ���������Ϊ null �򷵻� null 
   ������ʾ��(ע��� trim() ������)��
      StringUtils.strip(null) = null
      StringUtils.strip("") = ""
      StringUtils.strip(" ") = ""
      StringUtils.strip("     \b \t \n \f \r    ") = "\b"
      StringUtils.strip("     \n\tss   \b") = "ss   \b"
      StringUtils.strip(" d   d dd     ") = "d   d dd"
      StringUtils.strip("dd     ") = "dd"
      StringUtils.strip("     dd       ") = "dd" 
	 * @param str
	 * @return
	 */
	public static String strip(String str) {
		return StringUtils.strip(str);
	}
	
	/**
	 * ȥ���ַ������˵Ŀհ׷�(whitespace) �������Ϊ null ��""���򷵻� null 
   ������ʾ��(ע��� trimToNull() ������)��
      StringUtils.stripToNull(null) = null
      StringUtils.stripToNull("") = null
      StringUtils.stripToNull(" ") = null
      StringUtils.stripToNull("     \b \t \n \f \r    ") = "\b"
      StringUtils.stripToNull("     \n\tss   \b") = "ss   \b"
      StringUtils.stripToNull(" d   d dd     ") = "d   d dd"
      StringUtils.stripToNull("dd     ") = "dd"
      StringUtils.stripToNull("     dd       ") = "dd" 
	 * @param str
	 * @return
	 */
	public static String stripToNull(String str){
		return StringUtils.stripToNull(str);
	}
	
	/**
	 * ȥ���ַ������˵Ŀհ׷�(whitespace) �������Ϊ null ��"" ���򷵻�"" 
    ������ʾ��(ע��� trimToEmpty() ������)��
      StringUtils.stripToNull(null) = ""
      StringUtils.stripToNull("") = ""
      StringUtils.stripToNull(" ") = ""
      StringUtils.stripToNull("     \b \t \n \f \r    ") = "\b"
      StringUtils.stripToNull("     \n\tss   \b") = "ss   \b"
      StringUtils.stripToNull(" d   d dd     ") = "d   d dd"
      StringUtils.stripToNull("dd     ") = "dd"
      StringUtils.stripToNull("     dd       ") = "dd" 
	 * @param str
	 * @return
	 */
	public static String stripToEmpty(String str){
		return StringUtils.stripToEmpty(str);
	}
	
	/**
	 * ȥ�� str ���˵��� stripChars �е��ַ���
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String strip(String str, String stripChars) {
		return StringUtils.strip(str, stripChars);
	}
	
	/**
	 * ȥ�� str ǰ�˵��� stripChars �е��ַ���
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripStart(String str, String stripChars){
		return StringUtils.stripStart(str, stripChars);
	}
	
	/**
	 * ȥ�� str ĩ�˵��� stripChars �е��ַ�
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripEnd(String str, String stripChars) {
		return StringUtils.stripEnd(str, stripChars);
	}
	
	/**
	 * ���ַ��������е�ÿ���ַ������� strip(String str) ��Ȼ�󷵻ء�
    ��� strs Ϊ null �� strs ����Ϊ0���򷵻� strs ����
	 * @param strs
	 * @return
	 */
	public static String[] stripAll(String[] strs) {
		return StringUtils.stripAll(strs);
	}
	
	/**
	 * ���ַ��������е�ÿ���ַ������� strip(String str, String stripChars) ��Ȼ�󷵻ء�
    ��� strs Ϊ null �� strs ����Ϊ0���򷵻� strs ����
	 * @param strs
	 * @param stripChars
	 * @return
	 */
	public static String[] stripAll(String[] strs, String stripChars) {
		return StringUtils.stripAll(strs, stripChars);
	}
	
	/**
	 * �Ƚ������ַ����Ƿ���ȣ����������Ϊ����Ҳ��Ϊ��ȡ�
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}
	
	/**
	 * �Ƚ������ַ����Ƿ���ȣ������ִ�Сд�����������Ϊ����Ҳ��Ϊ��ȡ�
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str1, String str2){
		return StringUtils.equalsIgnoreCase(str1, str2);
	}
	
	/**
	 * �����ַ� searchChar ���ַ��� str �е�һ�γ��ֵ�λ�á�
    ��� searchChar û���� str �г����򷵻�-1��
    ��� str Ϊ null �� "" ����Ҳ����-1
	 * @param str
	 * @param searchChar
	 * @return
	 */
	public static int indexOf(String str, char searchChar) {
		return StringUtils.indexOf(str, searchChar);
	}
	
	/**
	 * �����ַ� searchChar �� startPos ��ʼ���ַ��� str �е�һ�γ��ֵ�λ�á�
    ����� startPos ��ʼ searchChar û���� str �г����򷵻�-1��
    ��� str Ϊ null �� "" ����Ҳ����-1
	 * @param str
	 * @param searchChar
	 * @param startPos
	 * @return
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		return StringUtils.indexOf(str, searchChar, startPos);
	}
	
	/**
	 * �����ַ��� searchStr ���ַ��� str �е�һ�γ��ֵ�λ�á�
    ��� str Ϊ null �� searchStr Ϊ null �򷵻�-1��
    ��� searchStr Ϊ "" ,�� str Ϊ��Ϊ null ���򷵻�0��
    ��� searchStr ���� str �У��򷵻�-1
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int indexOf(String str, String searchStr){
		return StringUtils.indexOf(str, searchStr);
	}
	
	/**
	 * �����ַ��� searchStr ���ַ��� str �е� ordinal �γ��ֵ�λ�á�
    ��� str=null �� searchStr=null �� ordinal<=0 �򷵻�-1
    ����(*���������ַ���)��
      StringUtils.ordinalIndexOf(null, *, *) = -1
      StringUtils.ordinalIndexOf(*, null, *) = -1
      StringUtils.ordinalIndexOf("", "", *) = 0
      StringUtils.ordinalIndexOf("aabaabaa", "a", 1) = 0
      StringUtils.ordinalIndexOf("aabaabaa", "a", 2) = 1
      StringUtils.ordinalIndexOf("aabaabaa", "b", 1) = 2
      StringUtils.ordinalIndexOf("aabaabaa", "b", 2) = 5
      StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
      StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
      StringUtils.ordinalIndexOf("aabaabaa", "bc", 1) = -1
      StringUtils.ordinalIndexOf("aabaabaa", "", 1) = 0
      StringUtils.ordinalIndexOf("aabaabaa", "", 2) = 0 
	 * @param str
	 * @param searchStr
	 * @param ordinal
	 * @return
	 */
	public static int ordinalIndexOf(String str, String searchStr, int ordinal){
		return StringUtils.ordinalIndexOf(str, searchStr, ordinal);
	}
	
	/**
	 * �����ַ��� searchStr �� startPos ��ʼ���ַ��� str �е�һ�γ��ֵ�λ�á�
    ����(*���������ַ���)��
      StringUtils.indexOf(null, *, *) = -1
      StringUtils.indexOf(*, null, *) = -1
      StringUtils.indexOf("", "", 0) = 0
      StringUtils.indexOf("aabaabaa", "a", 0) = 0
      StringUtils.indexOf("aabaabaa", "b", 0) = 2
      StringUtils.indexOf("aabaabaa", "ab", 0) = 1
      StringUtils.indexOf("aabaabaa", "b", 3) = 5
      StringUtils.indexOf("aabaabaa", "b", 9) = -1
      StringUtils.indexOf("aabaabaa", "b", -1) = 2
      StringUtils.indexOf("aabaabaa", "", 2) = 2
      StringUtils.indexOf("abc", "", 9) = 3 
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public static int indexOf(String str, String searchStr, int startPos){
		return StringUtils.indexOf(str, searchStr, startPos);
	}
	
	/**
	 * �ַ����ַ��������һ�γ��ֵ�λ��
	 * @param str
	 * @param searchChar
	 * @return
	 */
	public static int lastIndexOf(String str, char searchChar){
		return StringUtils.lastIndexOf(str, searchChar);
	}
	
	/**
	 * �ַ���startPos��ʼ���һ�γ��ֵ�λ��
	 * @param str
	 * @param searchChar
	 * @param startPos
	 * @return
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos){
		return StringUtils.lastIndexOf(str, searchChar, startPos);
	}
	
	/**
	 * �ַ�����ǰ���ַ��������һ�γ��ֵ�λ��
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int lastIndexOf(String str, String searchStr){
		return StringUtils.lastIndexOf(str, searchStr);
	}
	
	/**
	 * �ַ�����startPos��ʼ���һ�γ��ֵ�λ��
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		return StringUtils.lastIndexOf(str, searchStr, startPos);
	}
	
	/**
	 * ���ݿո�ָ�ָ�����ַ���
	 * StringUtils.split(null)       = null
 StringUtils.split("")         = []
 StringUtils.split("abc def")  = ["abc", "def"]
 StringUtils.split("abc  def") = ["abc", "def"]
 StringUtils.split(" abc ")    = ["abc"]
	 * @param str
	 * @return
	 */
	public static String[] split(String str){
		return StringUtils.split(str);
	}
	
	/**
	 * ��ָ�����ַ��ָ��ַ���
	 * StringUtils.split(null, *)         = null
 StringUtils.split("", *)           = []
 StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
 StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
 StringUtils.split("a:b:c", '.')    = ["a:b:c"]
 StringUtils.split("a\tb\nc", null) = ["a", "b", "c"]
 StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
	 * @param str
	 * @param separatorChar
	 * @return
	 */
	public static String[] split(String str,char separatorChar){
		return StringUtils.split(str, separatorChar);
	}
	
	/**
	 * ��ָ��λ��֮ǰ��ʼ�ָ��ַ�����֮��Ĳ����д���
	 * StringUtils.split(null, *, *)            = null
 StringUtils.split("", *, *)              = []
 StringUtils.split("ab de fg", null, 0)   = ["ab", "cd", "ef"]
 StringUtils.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]
 StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
 StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
	 * @param str
	 * @param separatorChars
	 * @param max
	 * @return
	 */
	public static String[] split(String str,String separatorChars,int max){
		return StringUtils.split(str, separatorChars, max);
	}
	
	/**
	 * ����ָ���ַ����ָ�
	 * StringUtils.splitByWholeSeparator(null, *)               = null
 StringUtils.splitByWholeSeparator("", *)                 = []
 StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
 StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
 StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
 StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String[] splitByWholeSeparator(String str,String separator){
		return StringUtils.splitByWholeSeparator(str, separator);
	}
	
	/**
	 * ��ָ����λ�ÿ�ʼ���ָ�֮ǰ���ַ��У�֮��Ĳ�����
	 * StringUtils.splitByWholeSeparator(null, *, *)               = null
 StringUtils.splitByWholeSeparator("", *, *)                 = []
 StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
 StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
 StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
 StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
 StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
	 * @param str
	 * @param separator
	 * @param max
	 * @return
	 */
	public static String[] splitByWholeSeparator(String str,String separator,int max){
		return StringUtils.splitByWholeSeparator(str, separator, max);
	}
	
	/**
	 * �Կշָ��ַ���
	 * StringUtils.splitPreserveAllTokens(null)       = null
 StringUtils.splitPreserveAllTokens("")         = []
 StringUtils.splitPreserveAllTokens("abc def")  = ["abc", "def"]
 StringUtils.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
 StringUtils.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
	 * @param str
	 * @return
	 */
	public static String[] splitPreserveAllTokens(String str){
		return StringUtils.splitPreserveAllTokens(str);
	}
	
	/**
	 * ��ָ�����ַ��ָ��ַ���
	 * StringUtils.splitPreserveAllTokens(null, *)         = null
 StringUtils.splitPreserveAllTokens("", *)           = []
 StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
 StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
 StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
 StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
 StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
 StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
 StringUtils.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
 StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
 StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
 StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
	 * @param str
	 * @param separatorChar
	 * @return
	 */
	public static String[] splitPreserveAllTokens(String str,char separatorChar){
		return StringUtils.splitPreserveAllTokens(str, separatorChar);
	}
	
	/**
	 * ��ָ�����ַ����ָ����ȥ����
	 * StringUtils.splitPreserveAllTokens(null, *)           = null
 StringUtils.splitPreserveAllTokens("", *)             = []
 StringUtils.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
 StringUtils.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
 StringUtils.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
 StringUtils.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
 StringUtils.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
 StringUtils.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
 StringUtils.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
 StringUtils.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
 StringUtils.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
 StringUtils.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static String[] splitPreserveAllTokens(String str,String separatorChars){
		return StringUtils.splitPreserveAllTokens(str, separatorChars);
	}
	
	/**
	 * ��ָ��λ�ÿ�ʼ�ָ��ַ�������ȥ����
	 * StringUtils.splitPreserveAllTokens(null, *, *)            = null
 StringUtils.splitPreserveAllTokens("", *, *)              = []
 StringUtils.splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "cd", "ef"]
 StringUtils.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "cd", "ef"]
 StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
 StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
 StringUtils.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
 StringUtils.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
 StringUtils.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
	 * @param str
	 * @param separatorChars
	 * @param max
	 * @return
	 */
	public static String[] splitPreserveAllTokens(String str,String separatorChars,int max){
		return StringUtils.splitPreserveAllTokens(str, separatorChars, max);
	}
	
	/**
	 * ���ַ�����ת�����ַ���
	 * StringUtils.concatenate(null)            = null
 StringUtils.concatenate([])              = ""
 StringUtils.concatenate([null])          = ""
 StringUtils.concatenate(["a", "b", "c"]) = "abc"
 StringUtils.concatenate([null, "", "a"]) = "a"
	 * @param array
	 * @return
	 */
	public static String concatenate(Object[] array){
		return StringUtils.concatenate(array);
	}
	
	/**
	 * ���ַ�����ת�����ַ���
	 * StringUtils.join(null)            = null
 StringUtils.join([])              = ""
 StringUtils.join([null])          = ""
 StringUtils.join(["a", "b", "c"]) = "abc"
 StringUtils.join([null, "", "a"]) = "a"
	 * @param array
	 * @return
	 */
	public static String join(Object[] array){
		return StringUtils.join(array);
	}
	
	/**
	 * ��ָ�����ַ�����ת������ָ���ַ��ָ���ַ���
	 *  StringUtils.join(null, *)               = null
 StringUtils.join([], *)                 = ""
 StringUtils.join([null], *)             = ""
 StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
 StringUtils.join(["a", "b", "c"], null) = "abc"
 StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array,char separator){
		return StringUtils.join(array, separator);
	}
	
	/**
	 * ��ָ�����ַ����ϲ����ҷָ�
	 *  StringUtils.join(null, *)                = null
 StringUtils.join([], *)                  = ""
 StringUtils.join([null], *)              = ""
 StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
 StringUtils.join(["a", "b", "c"], null)  = "abc"
 StringUtils.join(["a", "b", "c"], "")    = "abc"
 StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array,String separator){
		return StringUtils.join(array, separator);
	}
	

}
