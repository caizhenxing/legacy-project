/**
 * 	@(#)TestPage.java   2006-12-5 ÏÂÎç12:51:22
 *	 ¡£ 
 *	 
 */
package test;

 /**
 * @author ddddd
 * @version 2006-12-5
 * @see
 */
public class TestPage {

	/**
	 * @param
	 * @version 2006-12-5
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int begin=10;
		int end=10;
		int last=10;
		while(end<begin+6)
 	   {
 		   if(begin>1)
 			   begin--;
 		   if(end<last)
 			   end++;
 		   if(begin==1&&end==last)
 			   break;
 	   }
		System.out.println(begin);
		System.out.println(end);
		System.out.println(last);
		for(int i=begin;i<=end;i++)
		{
			System.out.println(i);
		}
	}

}
