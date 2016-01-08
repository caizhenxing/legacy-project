/**
 * 
 * 
 */
package base.zyf.common.util.string;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhangfeng 字符串处理类
 */
public class StringUtil {

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 *            判断某字符串是否为空，为空的标准是 str==null 或 str.length()==0 下面是 StringUtils
	 *            判断是否为空的示例：
	 * 
	 * StringUtils.isEmpty(null) = true StringUtils.isEmpty("") = true
	 * StringUtils.isEmpty(" ") = false //注意在 StringUtils 中空格作非空处理
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
	 * 判断是否非空
	 * 
	 * @param str
	 *            判断某字符串是否非空，等于 !isEmpty(String str) 下面是示例：
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
	 * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成 下面是示例： StringUtils.isBlank(null) =
	 * true StringUtils.isBlank("") = true StringUtils.isBlank(" ") = true
	 * StringUtils.isBlank(" ") = true StringUtils.isBlank("\t \n \f \r") = true
	 * //对于制表符、换行符、换页符和回车符
	 * 
	 * StringUtils.isBlank() //均识为空白符 StringUtils.isBlank("\b") = false
	 * //"\b"为单词边界符 StringUtils.isBlank("bob") = false StringUtils.isBlank(" bob ") =
	 * false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * 判断某字符串是否不为空且长度不为0且不由空白符(whitespace) 构成，等于 !isBlank(String str) 下面是示例：
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
	 * 去掉字符串两端的控制符(control characters, char <= 32) , 如果输入为 null 则返回null 下面是示例：
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
	 * 去掉字符串两端的控制符(control characters, char <= 32) ,如果变为 null 或""，则返回 null
	 * 下面是示例： StringUtils.trimToNull(null) = null StringUtils.trimToNull("") =
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
	 * 去掉字符串两端的控制符(control characters, char <= 32) ,如果变为 null 或 "" ，则返回 ""
	 * 下面是示例： StringUtils.trimToEmpty(null) = "" StringUtils.trimToEmpty("") = ""
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
	 * 去掉字符串两端的空白符(whitespace) ，如果输入为 null 则返回 null 
   下面是示例(注意和 trim() 的区别)：
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
	 * 去掉字符串两端的空白符(whitespace) ，如果变为 null 或""，则返回 null 
   下面是示例(注意和 trimToNull() 的区别)：
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
	 * 去掉字符串两端的空白符(whitespace) ，如果变为 null 或"" ，则返回"" 
    下面是示例(注意和 trimToEmpty() 的区别)：
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
	 * 去掉 str 两端的在 stripChars 中的字符。
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String strip(String str, String stripChars) {
		return StringUtils.strip(str, stripChars);
	}
	
	/**
	 * 去掉 str 前端的在 stripChars 中的字符。
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripStart(String str, String stripChars){
		return StringUtils.stripStart(str, stripChars);
	}
	
	/**
	 * 去掉 str 末端的在 stripChars 中的字符
	 * @param str
	 * @param stripChars
	 * @return
	 */
	public static String stripEnd(String str, String stripChars) {
		return StringUtils.stripEnd(str, stripChars);
	}
	
	/**
	 * 对字符串数组中的每个字符串进行 strip(String str) ，然后返回。
    如果 strs 为 null 或 strs 长度为0，则返回 strs 本身
	 * @param strs
	 * @return
	 */
	public static String[] stripAll(String[] strs) {
		return StringUtils.stripAll(strs);
	}
	
	/**
	 * 对字符串数组中的每个字符串进行 strip(String str, String stripChars) ，然后返回。
    如果 strs 为 null 或 strs 长度为0，则返回 strs 本身
	 * @param strs
	 * @param stripChars
	 * @return
	 */
	public static String[] stripAll(String[] strs, String stripChars) {
		return StringUtils.stripAll(strs, stripChars);
	}
	
	/**
	 * 比较两个字符串是否相等，如果两个均为空则也认为相等。
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}
	
	/**
	 * 比较两个字符串是否相等，不区分大小写，如果两个均为空则也认为相等。
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str1, String str2){
		return StringUtils.equalsIgnoreCase(str1, str2);
	}
	
	/**
	 * 返回字符 searchChar 在字符串 str 中第一次出现的位置。
    如果 searchChar 没有在 str 中出现则返回-1，
    如果 str 为 null 或 "" ，则也返回-1
	 * @param str
	 * @param searchChar
	 * @return
	 */
	public static int indexOf(String str, char searchChar) {
		return StringUtils.indexOf(str, searchChar);
	}
	
	/**
	 * 返回字符 searchChar 从 startPos 开始在字符串 str 中第一次出现的位置。
    如果从 startPos 开始 searchChar 没有在 str 中出现则返回-1，
    如果 str 为 null 或 "" ，则也返回-1
	 * @param str
	 * @param searchChar
	 * @param startPos
	 * @return
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		return StringUtils.indexOf(str, searchChar, startPos);
	}
	
	/**
	 * 返回字符串 searchStr 在字符串 str 中第一次出现的位置。
    如果 str 为 null 或 searchStr 为 null 则返回-1，
    如果 searchStr 为 "" ,且 str 为不为 null ，则返回0，
    如果 searchStr 不在 str 中，则返回-1
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int indexOf(String str, String searchStr){
		return StringUtils.indexOf(str, searchStr);
	}
	
	/**
	 * 返回字符串 searchStr 在字符串 str 中第 ordinal 次出现的位置。
    如果 str=null 或 searchStr=null 或 ordinal<=0 则返回-1
    举例(*代表任意字符串)：
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
	 * 返回字符串 searchStr 从 startPos 开始在字符串 str 中第一次出现的位置。
    举例(*代表任意字符串)：
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
	 * 字符在字符串中最后一次出现的位置
	 * @param str
	 * @param searchChar
	 * @return
	 */
	public static int lastIndexOf(String str, char searchChar){
		return StringUtils.lastIndexOf(str, searchChar);
	}
	
	/**
	 * 字符从startPos开始最后一次出现的位置
	 * @param str
	 * @param searchChar
	 * @param startPos
	 * @return
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos){
		return StringUtils.lastIndexOf(str, searchChar, startPos);
	}
	
	/**
	 * 字符串在前面字符串中最后一次出现的位置
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int lastIndexOf(String str, String searchStr){
		return StringUtils.lastIndexOf(str, searchStr);
	}
	
	/**
	 * 字符串从startPos开始最后一次出现的位置
	 * @param str
	 * @param searchStr
	 * @param startPos
	 * @return
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		return StringUtils.lastIndexOf(str, searchStr, startPos);
	}
	
	/**
	 * 根据空格分割指定的字符串
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
	 * 以指定的字符分割字符串
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
	 * 从指定位置之前开始分割字符串，之后的不进行处理
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
	 * 根据指定字符串分割
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
	 * 从指定的位置开始，分割之前的字符中，之后的不处理
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
	 * 以空分割字符串
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
	 * 以指定的字符分割字符串
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
	 * 以指定的字符串分割，并且去掉空
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
	 * 从指定位置开始分割字符串并且去掉空
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
	 * 将字符数组转化成字符串
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
	 * 将字符数组转化成字符串
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
	 * 将指定的字符数组转化成以指定字符分割的字符串
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
	 * 以指定的字符串合并后并且分割
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
