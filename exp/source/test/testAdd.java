/*
 * �������� 2004-12-16
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testAdd {
private int addNum;
private int addN;
private int[] num;

	public static void main(String[] args) {
		testAdd ta=new testAdd();
		ta.setAddN(2);
		ta.setAddNum(4);
		int a=ta.add();
		int n[]={1,2,3,4,5,6};
		ta.setNum(n);
		ta.addNum();
		System.out.println(a);
		int b[]=ta.getNum();
		for(int i=0;i<b.length;i++)
		System.out.println(b[i]);
	}
/**
 * @return
 */
public int getAddN() {
	return addN;
}

/**
 * @return
 */
public int getAddNum() {
	return addNum;
}

/**
 * @param i
 */
public void setAddN(int i) {
	addN = i;
}

/**
 * @param i
 */
public void setAddNum(int i) {
	addNum = i;
}
public int add()
{
	int result;
	result=this.addN+this.addNum;
	return result;
}
/**
 * @return
 */
public int[] getNum() {
	return num;
}

/**
 * @param is
 */
public void setNum(int[] is) {
	num = is;
}
public void addNum()
{
	int count=num.length;
	for(int i=0;i<count;i++)
	num[i]++;
}
}
