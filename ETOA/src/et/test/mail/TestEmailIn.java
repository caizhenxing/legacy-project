/**
 * 	@(#)TestEmailIn.java   Jan 30, 2007 1:49:28 PM
 *	 ¡£ 
 *	 
 */
package et.test.mail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author 
 * @version Jan 30, 2007
 * @see
 */
public class TestEmailIn {

	/**
	 * @param
	 * @version Jan 30, 2007
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//EmailInServiceImpl es = new EmailInServiceImpl();
		String a = "zf";
		String b = "";
		List l = getUserList(a,b);
		Iterator it = l.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	private static List getUserList(String copylist, String searctlist) {
		List finalList = new ArrayList();

		List copyList = new ArrayList();
		List searctList = new ArrayList();
		StringTokenizer strToken1 = new StringTokenizer(copylist, ",");
		String tmpCopyList = "";
		while (strToken1.hasMoreTokens()) {
			tmpCopyList = strToken1.nextToken();
			copyList.add(tmpCopyList);
		}
		StringTokenizer strToken2 = new StringTokenizer(searctlist, ",");
		String tmpSearctList = "";
		while (strToken2.hasMoreTokens()) {
			tmpSearctList = strToken2.nextToken();
			searctList.add(tmpSearctList);
		}
		List cosList = new ArrayList();
		cosList.addAll(copyList);
		cosList.addAll(searctList);
		for (int i = 0; i < copyList.size(); i++) {
			String tmp = copyList.get(i).toString();
			for (int j = 0; j < searctList.size(); j++) {
				if (tmp.equals(searctList.get(j).toString())) {
					finalList.add(tmp);
				}
			}
		}
		cosList.removeAll(finalList);
		// copyList.removeAll(finalList);
		cosList.addAll(finalList);
		return cosList;
	}

}
