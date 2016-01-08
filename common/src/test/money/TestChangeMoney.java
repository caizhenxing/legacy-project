/**
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：common
 * 制作时间：Dec 5, 20075:56:27 PM
 * 包名：test.money
 * 文件名：TestChangeMoney.java
 * 制作者：zhaoyf
 * @version 1.0
 */
package test.money;

/**
 * 
 * @author zhaoyf
 * @version 1.0
 */
public class TestChangeMoney {

	/**
	 * 功能描述
	 * @param args
	 * Dec 5, 2007 5:56:28 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s=changeMoney(10200102);
		System.out.println(s);
//		System.out.println("ddddddd");
//		s=getChineseMoney("234002.213");
//		System.out.println(s);
//		s=numtochinese("234002.12");
//		System.out.println(s);
//		s=convertTo("234002.12");
//		System.out.println(s);
	}
	public static String changeMoney(double smallmoney) {
		String value = String.valueOf(smallmoney);
		if (null == value || "".equals(value.trim()))
			return "零";

		String strCheck, strArr, strFen, strDW, strNum, strBig, strNow;
		double d = 0;
		try {
			d = Double.parseDouble(value);
		} catch (Exception e) {
			return "数据" + value + "非法！";
		}

		strCheck = value + ".";
		int dot = strCheck.indexOf(".");
		if (dot > 12) {
			return "数据" + value + "过大，无法处理！";
		}

		try {
			int i = 0;
			strBig = "";
			strDW = "";
			strNum = "";
			long intFen = (long) (d * 100);
			strFen = String.valueOf(intFen);
			int lenIntFen = strFen.length();
			while (lenIntFen != 0) {
				i++;
				switch (i) {
				case 1:
					strDW = "分";
					break;
				case 2:
					strDW = "角";
					break;
				case 3:
					strDW = "元";
					break;
				case 4:
					strDW = "拾";
					break;
				case 5:
					strDW = "佰";
					break;
				case 6:
					strDW = "仟";
					break;
				case 7:
					strDW = "万";
					break;
				case 8:
					strDW = "拾";
					break;
				case 9:
					strDW = "佰";
					break;
				case 10:
					strDW = "仟";
					break;
				case 11:
					strDW = "亿";
					break;
				case 12:
					strDW = "拾";
					break;
				case 13:
					strDW = "佰";
					break;
				case 14:
					strDW = "仟";
					break;
				}
				switch (strFen.charAt(lenIntFen - 1)) // 选择数字
				{
				case '1':
					strNum = "壹";
					break;
				case '2':
					strNum = "贰";
					break;
				case '3':
					strNum = "叁";
					break;
				case '4':
					strNum = "肆";
					break;
				case '5':
					strNum = "伍";
					break;
				case '6':
					strNum = "陆";
					break;
				case '7':
					strNum = "柒";
					break;
				case '8':
					strNum = "捌";
					break;
				case '9':
					strNum = "玖";
					break;
				case '0':
					strNum = "零";
					break;
				}
				// 处理特殊情况
				strNow = strBig;
				// 分为零时的情况
				if ((i == 1) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "整";
				// 角为零时的情况
				else if ((i == 2) && (strFen.charAt(lenIntFen - 1) == '0')) { // 角分同时为零时的情况
					if (!strBig.equals("整"))
						strBig = "零" + strBig;
				}
				// 元为零的情况
				else if ((i == 3) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "元" + strBig;
				// 拾－仟中一位为零且其前一位（元以上）不为零的情况时补零
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '元'))
					strBig = "零" + strBig;
				// 拾－仟中一位为零且其前一位（元以上）也为零的情况时跨过
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				// 拾－仟中一位为零且其前一位是元且为零的情况时跨过
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '元')) {
				}
				// 当万为零时必须补上万字
				else if ((i == 7) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "万" + strBig;
				// 拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '万'))
					strBig = "零" + strBig;
				// 拾万－仟万中一位为零且其前一位（万以上）也为零的情况时跨过
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '万')) {
				}
				// 拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				// 万位为零且存在仟位和十万以上时，在万仟间补零
				else if ((i < 11) && (i > 8)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '万')
						&& (strNow.charAt(2) == '仟'))
					strBig = strNum + strDW + "万零"
							+ strBig.substring(1, strBig.length());
				// 单独处理亿位
				else if (i == 11) {
					// 亿位为零且万全为零存在仟位时，去掉万补为零
					if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '万')
							&& (strNow.charAt(2) == '仟'))
						strBig = "亿" + "零"
								+ strBig.substring(1, strBig.length());
					// 亿位为零且万全为零不存在仟位时，去掉万
					else if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '万')
							&& (strNow.charAt(2) != '仟'))
						strBig = "亿" + strBig.substring(1, strBig.length());
					// 亿位不为零且万全为零存在仟位时，去掉万补为零
					else if ((strNow.charAt(0) == '万')
							&& (strNow.charAt(2) == '仟'))
						strBig = strNum + strDW + "零"
								+ strBig.substring(1, strBig.length());
					// 亿位不为零且万全为零不存在仟位时，去掉万
					else if ((strNow.charAt(0) == '万')
							&& (strNow.charAt(2) != '仟'))
						strBig = strNum + strDW
								+ strBig.substring(1, strBig.length());
					// 其他正常情况
					else
						strBig = strNum + strDW + strBig;
				}
				// 拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '亿'))
					strBig = "零" + strBig;
				// 拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '亿')) {
				}
				// 拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				// 亿位为零且不存在仟万位和十亿以上时去掉上次写入的零
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '零')
						&& (strNow.charAt(1) == '亿')
						&& (strNow.charAt(3) != '仟'))
					strBig = strNum + strDW
							+ strBig.substring(1, strBig.length());
				// 亿位为零且存在仟万位和十亿以上时，在亿仟万间补零
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '零')
						&& (strNow.charAt(1) == '亿')
						&& (strNow.charAt(3) == '仟'))
					strBig = strNum + strDW + "亿零"
							+ strBig.substring(2, strBig.length());
				else
					strBig = strNum + strDW + strBig;
				strFen = strFen.substring(0, lenIntFen - 1);
				lenIntFen--;
			}
			return strBig;
		} catch (Exception e) {
			return "";
		}
	}
	/**
	 * description 根据人民币的数字转化成中文的大写形式
	 * 
	 * @param String
	 *            type 人民币数字
	 * @return String 大写形式的字符串
	 * @author date: 2002-10-20
	 */ 
	public static String getChineseMoney(String moneyNum){ 
	// 存放汉字的数组
	String[] chinese = new String[17]; 
	String[] money = new String[2]; 
	chinese[0]="零"; 
	chinese[1]="壹"; 
	chinese[2]="贰"; 
	chinese[3]="叁"; 
	chinese[4]="肆"; 
	chinese[5]="伍"; 
	chinese[6]="陆"; 
	chinese[7]="柒"; 
	chinese[8]="捌"; 
	chinese[9]="玖"; 
	chinese[10]="拾"; 
	chinese[11]="佰"; 
	chinese[12]="仟"; 
	chinese[13]="万"; 
	chinese[14]="亿"; 
	chinese[15]="元" ; 
	chinese[16]="整"; 
	money[0]="角"; 
	money[1]="分"; 
	// 输出的大写形式字符串
	String str_out=""; 
	// 整数部分字符串
	String str_left=""; 
	// 小数部分字符串
	String str_right=""; 
	// 小数点
	String str_point="."; 
	// 小数点位置
	int ponitLocation=moneyNum.indexOf("."); 
	// 小数点后位数
	int pointLater=moneyNum.length() - ponitLocation - 1; 
	// 分离字符串为两部分
	if(pointLater>1) 
	{ 
	str_left=moneyNum.substring(0,ponitLocation); 
	str_right=moneyNum.substring(ponitLocation+1,ponitLocation+3); 
	} 
	// 整数部分字符串的长度
	int str_left_length; 
	str_left_length=str_left.length() ; 
	// 小数部分字符串的长度
	int str_right_length; 
	str_right_length=str_right.length(); 
	// 整数部分的零字符标识位
	int flag=0; 
	// 小数部分的零字符标识位
	int flag_zreo=0; 
	// 开始转换整数部分
	for(int i=0;i<=str_left.length()-1;i++) 
	{ 

	String str_l; 
	str_l=str_left.substring(i,i+1); 
	int temp = Integer.parseInt(str_l); 
	switch(temp) 
	{ 
	case 1: 
	str_out=str_out+chinese[1]; 
	break; 
	case 2: 
	str_out=str_out+chinese[2]; 
	break; 
	case 3: 
	str_out=str_out+chinese[3]; 
	break; 
	case 4: 
	str_out=str_out+chinese[4]; 
	break; 
	case 5: 
	str_out=str_out+chinese[5]; 
	break; 
	case 6: 
	str_out=str_out+chinese[6]; 
	break; 
	case 7: 
	str_out=str_out+chinese[7]; 
	break; 
	case 8: 
	str_out=str_out+chinese[8]; 
	break; 
	case 9: 
	str_out=str_out+chinese[9]; 
	break; 
	} 
	//对整数部分的零的处理 
	if(temp==0) 
	{ 
	flag++; 
	if(str_left_length==1) 
	{ 
	str_out=str_out+chinese[15]; 
	} 
	if(str_left_length==9) 
	{ 
	flag=0; 
	str_out=str_out+chinese[14]; 
	} 
	if(str_left_length==5) 
	{ 
	flag=0; 
	str_out=str_out+chinese[13]; 
	} 
	//判断是否输出零，根据它的下一位来决定：是零则不输出，否则输出 
	if(str_left_length>=2) 
	{ 
	String str_le=str_left.substring 
	(i+1,i+2); 
	int tem=Integer.parseInt(str_le); 
	if((flag==1)&&(tem!=0)) 
	str_out=str_out+chinese[0]; 
	else 
	flag=0; 
	} 
	str_left_length--; 
	} 
	else 
	{ 
	flag=0; 
	//添加整数部分的单位：拾, 佰,仟 ,万,亿 
	switch(str_left_length) 
	{ 

	case 1: 
	str_out=str_out+chinese[15]; 
	str_left_length--; 
	break; 
	case 2: 
	str_out=str_out+chinese[10]; 
	str_left_length--; 
	break; 
	case 3: 
	str_out=str_out+chinese[11]; 
	str_left_length--; 
	break; 
	case 4: 
	str_out=str_out+chinese[12]; 
	str_left_length--; 
	break; 
	case 5: 
	str_out=str_out+chinese[13]; 
	str_left_length--; 
	break; 
	case 6: 
	str_out=str_out+chinese[10]; 
	str_left_length--; 
	break; 
	case 7: 
	str_out=str_out+chinese[11]; 
	str_left_length--; 
	break; 
	case 8: 
	str_out=str_out+chinese[12]; 
	str_left_length--; 
	break; 
	case 9: 
	str_out=str_out+chinese[14]; 
	str_left_length--; 
	break; 
	case 10: 
	str_out=str_out+chinese[10]; 
	str_left_length--; 
	break; 
	case 11: 
	str_out=str_out+chinese[11]; 
	str_left_length--; 
	break; 
	case 12: 
	str_out=str_out+chinese[12]; 
	str_left_length--; 
	break; 
	case 13: 
	str_out=str_out+chinese[13]; 
	str_left_length--; 
	break; 
	} 

	} 
	} 
	//处理小数部分的字符串 
	for(int i=0;i<=str_right.length()-1;i++) 
	{ 
	String str_r; 
	str_r=str_right.substring(i,i+1); 
	int temp1 = Integer.parseInt(str_r); 
	switch(temp1) 
	{ 
	case 1: 
	str_out=str_out+chinese[1]; 
	break; 
	case 2: 
	str_out=str_out+chinese[2]; 
	break; 
	case 3: 
	str_out=str_out+chinese[3]; 
	break; 
	case 4: 
	str_out=str_out+chinese[4]; 
	break; 
	case 5: 
	str_out=str_out+chinese[5]; 
	break; 
	case 6: 
	str_out=str_out+chinese[6]; 
	break; 
	case 7: 
	str_out=str_out+chinese[7]; 
	break; 
	case 8: 
	str_out=str_out+chinese[8]; 
	break; 
	case 9: 
	str_out=str_out+chinese[9]; 
	break; 

	} 
	//对小数部分的零的处理 
	if(temp1==0) 
	{ 
	flag_zreo++; 
	if(str_right_length==2) 
	{ 
	//判断是否输出零，根据它的下一位来决定：是零则不输出，否则输出 
	String str_ri=str_right.substring(i+1,i+2); 
	int temp=Integer.parseInt(str_ri); 
	if((flag_zreo==1)&&(temp!=0)) 
	{ 
	str_out=str_out+chinese[0]; 
	} 
	} 
	else 
	{ 
	str_out=str_out+chinese[16]; 
	} 
	str_right_length--; 
	} 
	else 
	{ 
	//添加小数部分的角、分 
	switch(str_right_length) 
	{ 
	case 1: 
	str_out=str_out+money[1]; 
	str_right_length--; 
	break; 
	case 2: 
	str_out=str_out+money[0]; 
	str_right_length--; 
	break; 
	} 
	} 
	} 
	return str_out; 
	} 
	public   static   String   numtochinese(String   input){   
        String   s1="零壹贰叁肆伍陆柒捌玖";   
        String   s4="分角整元拾佰仟万拾佰仟亿拾佰仟";   
        String   temp="";   
        String   result="";   
        if   (input==null)   return   "输入字串不是数字串只能包括以下字符（'0'～'9'，'.')，输入字串最大只能精确到仟亿，小数点只能两位！";   
        temp=input.trim();   
        float   f;   
        try{   
                f=Float.parseFloat(temp);   
    
        }catch(Exception   e){return   "输入字串不是数字串只能包括以下字符（'0'～'9'，'.')，输入字串最大只能精确到仟亿，小数点只能两位！";}   
        int   len=0;   
        if   (temp.indexOf(".")==-1)   len=temp.length();   
        else   len=temp.indexOf(".");   
        if(len>s4.length()-3)   return("输入字串最大只能精确到仟亿，小数点只能两位！");   
        int   n1,n2=0;   
        String   num="";   
        String   unit="";   
    
        for(int   i=0;i<temp.length();i++){   
        if(i>len+2){break;}   
        if(i==len)   {continue;}   
        n1=Integer.parseInt(String.valueOf(temp.charAt(i)));   
        num=s1.substring(n1,n1+1);   
        n1=len-i+2;   
        unit=s4.substring(n1,n1+1);   
        result=result.concat(num).concat(unit);   
        }   
        if   ((len==temp.length())||(len==temp.length()-1))   result=result.concat("整");   
        if   (len==temp.length()-2)   result=result.concat("零分");   
        return   result;   
    }
	public static String convertTo(String number) 
	{ 
	String hanStr = "整"; 
	try{ 

	String[] danwei = { 
	"分", "角", "元", "拾", "佰", "扦", "万", "拾", "佰", "扦","亿","拾", "佰", "扦"}; 
	String[] numHan = { 
	"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"}; 
	int point = number.indexOf("."); 
	if(point==-1) 
	{ 
	String numStr = number; 
	int len = numStr.length(); 
	for (int i = 0; i < len; i++) { 
	int j = Integer.parseInt(numStr.substring(len - 1 - i, len - i)); 

	hanStr = numHan[j] + danwei[ (2 + i)]+hanStr; 
	numStr = numStr.substring(0, len - 1 - i); 
	} 
	} 
	else 
	{ 
	String numStr = number.substring(0,point); 
	String last=number.substring(point+1); 
	int len = numStr.length(); 
	int lenlast = last.length(); 
	//-使小数点后面只精确到角分 
	if(lenlast>2) 
	{ 
	lenlast=2; 
	last=last.substring(0,2); 
	} 
	if(lenlast<2) 
	{ 
	lenlast=2; 
	last=last+"0"; 
	} 

	//-------得到角分 
	for (int k = 0; k <lenlast; k++) { 
	int p = Integer.parseInt(last.substring(lenlast - 1 - k, lenlast - k)); 
	hanStr = numHan[p] + danwei[k]+hanStr; 
	last = last.substring(0, lenlast- 1 - k); 
	} 
	//-------得到整数 
	for (int i = 0; i < len; i++) { 
	int j = Integer.parseInt(numStr.substring(len - 1 - i, len - i)); 

	hanStr = numHan[j] + danwei[ (2 + i)]+hanStr; 
	numStr = numStr.substring(0, len - 1 - i); 
	} 
	} 
	}catch(Exception e) 
	{ 
	e.printStackTrace() ; 
	} 
	return hanStr; 
	} 
}
