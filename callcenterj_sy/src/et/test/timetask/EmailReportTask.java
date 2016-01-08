/**
 * 	@(#)EmailReportTask.java   2007-5-21 下午03:27:08
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.test.timetask;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author
 * @version 2007-5-21
 * @see
 */
public class EmailReportTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("输出信息在........." + new Date());
	}

}
