/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.common;
/**
 * 列表显示用
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class MathUtil {
	int initArr[]   =   null; 
	public MathUtil()
	{
		initArr = new int[100];
	}
	
	public void addBits(int[] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			initArr[i] = arr[i]+initArr[i];
		}
	}
	public int[] getSumArr()
	{
		return initArr;
	}
}
