/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�common
 * ����ʱ�䣺Dec 5, 20075:56:27 PM
 * ������test.money
 * �ļ�����TestChangeMoney.java
 * �����ߣ�zhaoyf
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
	 * ��������
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
			return "��";

		String strCheck, strArr, strFen, strDW, strNum, strBig, strNow;
		double d = 0;
		try {
			d = Double.parseDouble(value);
		} catch (Exception e) {
			return "����" + value + "�Ƿ���";
		}

		strCheck = value + ".";
		int dot = strCheck.indexOf(".");
		if (dot > 12) {
			return "����" + value + "�����޷�����";
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
					strDW = "��";
					break;
				case 2:
					strDW = "��";
					break;
				case 3:
					strDW = "Ԫ";
					break;
				case 4:
					strDW = "ʰ";
					break;
				case 5:
					strDW = "��";
					break;
				case 6:
					strDW = "Ǫ";
					break;
				case 7:
					strDW = "��";
					break;
				case 8:
					strDW = "ʰ";
					break;
				case 9:
					strDW = "��";
					break;
				case 10:
					strDW = "Ǫ";
					break;
				case 11:
					strDW = "��";
					break;
				case 12:
					strDW = "ʰ";
					break;
				case 13:
					strDW = "��";
					break;
				case 14:
					strDW = "Ǫ";
					break;
				}
				switch (strFen.charAt(lenIntFen - 1)) // ѡ������
				{
				case '1':
					strNum = "Ҽ";
					break;
				case '2':
					strNum = "��";
					break;
				case '3':
					strNum = "��";
					break;
				case '4':
					strNum = "��";
					break;
				case '5':
					strNum = "��";
					break;
				case '6':
					strNum = "½";
					break;
				case '7':
					strNum = "��";
					break;
				case '8':
					strNum = "��";
					break;
				case '9':
					strNum = "��";
					break;
				case '0':
					strNum = "��";
					break;
				}
				// �����������
				strNow = strBig;
				// ��Ϊ��ʱ�����
				if ((i == 1) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "��";
				// ��Ϊ��ʱ�����
				else if ((i == 2) && (strFen.charAt(lenIntFen - 1) == '0')) { // �Ƿ�ͬʱΪ��ʱ�����
					if (!strBig.equals("��"))
						strBig = "��" + strBig;
				}
				// ԪΪ������
				else if ((i == 3) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "Ԫ" + strBig;
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ���Ϊ������ʱ����
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '��')
						&& (strNow.charAt(0) != 'Ԫ'))
					strBig = "��" + strBig;
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ�ҲΪ������ʱ���
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')) {
				}
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ��Ϊ������ʱ���
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == 'Ԫ')) {
				}
				// ����Ϊ��ʱ���벹������
				else if ((i == 7) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "��" + strBig;
				// ʰ��Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '��')
						&& (strNow.charAt(0) != '��'))
					strBig = "��" + strBig;
				// ʰ��Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')) {
				}
				// ʰ��Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')) {
				}
				// ��λΪ���Ҵ���Ǫλ��ʮ������ʱ������Ǫ�䲹��
				else if ((i < 11) && (i > 8)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')
						&& (strNow.charAt(2) == 'Ǫ'))
					strBig = strNum + strDW + "����"
							+ strBig.substring(1, strBig.length());
				// ����������λ
				else if (i == 11) {
					// ��λΪ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
					if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '��')
							&& (strNow.charAt(2) == 'Ǫ'))
						strBig = "��" + "��"
								+ strBig.substring(1, strBig.length());
					// ��λΪ������ȫΪ�㲻����Ǫλʱ��ȥ����
					else if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '��')
							&& (strNow.charAt(2) != 'Ǫ'))
						strBig = "��" + strBig.substring(1, strBig.length());
					// ��λ��Ϊ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
					else if ((strNow.charAt(0) == '��')
							&& (strNow.charAt(2) == 'Ǫ'))
						strBig = strNum + strDW + "��"
								+ strBig.substring(1, strBig.length());
					// ��λ��Ϊ������ȫΪ�㲻����Ǫλʱ��ȥ����
					else if ((strNow.charAt(0) == '��')
							&& (strNow.charAt(2) != 'Ǫ'))
						strBig = strNum + strDW
								+ strBig.substring(1, strBig.length());
					// �����������
					else
						strBig = strNum + strDW + strBig;
				}
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '��')
						&& (strNow.charAt(0) != '��'))
					strBig = "��" + strBig;
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')) {
				}
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')) {
				}
				// ��λΪ���Ҳ�����Ǫ��λ��ʮ������ʱȥ���ϴ�д�����
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '��')
						&& (strNow.charAt(1) == '��')
						&& (strNow.charAt(3) != 'Ǫ'))
					strBig = strNum + strDW
							+ strBig.substring(1, strBig.length());
				// ��λΪ���Ҵ���Ǫ��λ��ʮ������ʱ������Ǫ��䲹��
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '��')
						&& (strNow.charAt(1) == '��')
						&& (strNow.charAt(3) == 'Ǫ'))
					strBig = strNum + strDW + "����"
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
	 * description ��������ҵ�����ת�������ĵĴ�д��ʽ
	 * 
	 * @param String
	 *            type ���������
	 * @return String ��д��ʽ���ַ���
	 * @author date: 2002-10-20
	 */ 
	public static String getChineseMoney(String moneyNum){ 
	// ��ź��ֵ�����
	String[] chinese = new String[17]; 
	String[] money = new String[2]; 
	chinese[0]="��"; 
	chinese[1]="Ҽ"; 
	chinese[2]="��"; 
	chinese[3]="��"; 
	chinese[4]="��"; 
	chinese[5]="��"; 
	chinese[6]="½"; 
	chinese[7]="��"; 
	chinese[8]="��"; 
	chinese[9]="��"; 
	chinese[10]="ʰ"; 
	chinese[11]="��"; 
	chinese[12]="Ǫ"; 
	chinese[13]="��"; 
	chinese[14]="��"; 
	chinese[15]="Ԫ" ; 
	chinese[16]="��"; 
	money[0]="��"; 
	money[1]="��"; 
	// ����Ĵ�д��ʽ�ַ���
	String str_out=""; 
	// ���������ַ���
	String str_left=""; 
	// С�������ַ���
	String str_right=""; 
	// С����
	String str_point="."; 
	// С����λ��
	int ponitLocation=moneyNum.indexOf("."); 
	// С�����λ��
	int pointLater=moneyNum.length() - ponitLocation - 1; 
	// �����ַ���Ϊ������
	if(pointLater>1) 
	{ 
	str_left=moneyNum.substring(0,ponitLocation); 
	str_right=moneyNum.substring(ponitLocation+1,ponitLocation+3); 
	} 
	// ���������ַ����ĳ���
	int str_left_length; 
	str_left_length=str_left.length() ; 
	// С�������ַ����ĳ���
	int str_right_length; 
	str_right_length=str_right.length(); 
	// �������ֵ����ַ���ʶλ
	int flag=0; 
	// С�����ֵ����ַ���ʶλ
	int flag_zreo=0; 
	// ��ʼת����������
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
	//���������ֵ���Ĵ��� 
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
	//�ж��Ƿ�����㣬����������һλ�������������������������� 
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
	//����������ֵĵ�λ��ʰ, ��,Ǫ ,��,�� 
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
	//����С�����ֵ��ַ��� 
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
	//��С�����ֵ���Ĵ��� 
	if(temp1==0) 
	{ 
	flag_zreo++; 
	if(str_right_length==2) 
	{ 
	//�ж��Ƿ�����㣬����������һλ�������������������������� 
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
	//���С�����ֵĽǡ��� 
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
        String   s1="��Ҽ��������½��ƾ�";   
        String   s4="�ֽ���Ԫʰ��Ǫ��ʰ��Ǫ��ʰ��Ǫ";   
        String   temp="";   
        String   result="";   
        if   (input==null)   return   "�����ִ��������ִ�ֻ�ܰ��������ַ���'0'��'9'��'.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";   
        temp=input.trim();   
        float   f;   
        try{   
                f=Float.parseFloat(temp);   
    
        }catch(Exception   e){return   "�����ִ��������ִ�ֻ�ܰ��������ַ���'0'��'9'��'.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";}   
        int   len=0;   
        if   (temp.indexOf(".")==-1)   len=temp.length();   
        else   len=temp.indexOf(".");   
        if(len>s4.length()-3)   return("�����ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��");   
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
        if   ((len==temp.length())||(len==temp.length()-1))   result=result.concat("��");   
        if   (len==temp.length()-2)   result=result.concat("���");   
        return   result;   
    }
	public static String convertTo(String number) 
	{ 
	String hanStr = "��"; 
	try{ 

	String[] danwei = { 
	"��", "��", "Ԫ", "ʰ", "��", "Ǥ", "��", "ʰ", "��", "Ǥ","��","ʰ", "��", "Ǥ"}; 
	String[] numHan = { 
	"��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��"}; 
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
	//-ʹС�������ֻ��ȷ���Ƿ� 
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

	//-------�õ��Ƿ� 
	for (int k = 0; k <lenlast; k++) { 
	int p = Integer.parseInt(last.substring(lenlast - 1 - k, lenlast - k)); 
	hanStr = numHan[p] + danwei[k]+hanStr; 
	last = last.substring(0, lenlast- 1 - k); 
	} 
	//-------�õ����� 
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
