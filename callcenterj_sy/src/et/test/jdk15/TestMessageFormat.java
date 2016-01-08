/**
 * 	@(#)TestMessageFormat.java   Apr 29, 2007 9:54:33 AM
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.test.jdk15;

import java.text.ChoiceFormat;
import java.text.MessageFormat;

/**
 * @author 
 * @version Apr 29, 2007
 * @see
 */
public class TestMessageFormat {

	/**
	 * @param
	 * @version Apr 29, 2007
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int planet = 7;
//		 String event = "a disturbance in the Force";
//
//		 String result = MessageFormat.format(
//		     "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
//		     planet, new Date(), event);
//		 
//		 //System.out.println(result);
		
//		int fileCount = 1273;
//		 String diskName = "MyDisk";
//		 Object[] testArgs = {new Long(fileCount), diskName};
//
//		 MessageFormat form = new MessageFormat(
//		     "The disk \"{1}\" contains {0} file(s).");
//		 //The disk "MyDisk" contains 1,273 file(s).
//		 //System.out.println(form.format(testArgs));

		
		 MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
		 double[] filelimits = {0,1,2};
		 String[] filepart = {"no files","one file","{0,number} files"};
		 ChoiceFormat fileform = new ChoiceFormat(filelimits, filepart);
		 form.setFormatByArgumentIndex(0, fileform);

		 int fileCount = 1273;
		 String diskName = "MyDisk";
		 Object[] testArgs = {new Long(fileCount), diskName};
		 //
		 //System.out.println(form.format(testArgs));


	}

}
