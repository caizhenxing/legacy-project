/**
 * 	@(#)EmailReportTask.java   2007-5-21 ����03:27:08
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
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
		System.out.println("�����Ϣ��........." + new Date());
	}

}
