/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Sep 26, 20073:58:09 PM
 * �ļ�����TestObject.java
 * �����ߣ�zhaoyf
 * 
 */
package test.tools;

/**
 * @author zhaoyf
 *
 */
public class TestObject {

	String a;
	String b;
	String c;
	
	AA aa=new AA();
	public AA getAa() {
		return aa;
	}
	public void setAa(AA aa) {
		this.aa = aa;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public void setC(Integer c)
	{
		this.c=c.toString();
	}
}
