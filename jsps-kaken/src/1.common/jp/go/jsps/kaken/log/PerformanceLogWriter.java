/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/12/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 
package jp.go.jsps.kaken.log;
import java.text.*;

import org.apache.commons.logging.*;


/**
 * �p�t�H�[�}���X���O�o�̓N���X�B
 * ID RCSfile="$RCSfile: PerformanceLogWriter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class PerformanceLogWriter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** �p�t�H�[�}���X���O�N���X */
	protected static final Log log = LogFactory.getLog("performance");
	
	/** �������g�p������ */
	private static final DecimalFormat df = new DecimalFormat("#0.00");
	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �����J�n���� */
	private long startTime  = 0L;
	
	/** �������g�p�󋵏o�̓t���O�i�f�t�H���gtrue�j */
	private boolean memFlag = true;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^
	 * �C���X�^���X�������̎��Ԃ������J�n���ԂƂ���B
	 * ���̃C���X�^���X���g�p���ă��O���o�͂����ꍇ�A
	 * �o�͓��e�ɂ͏������ԁi���O�o�̓��\�b�h�̌Ăяo�����ԁ|�����J�n���ԁj��
	 * �������g�p�󋵁i�󂫁^�g�[�^���^�g�p�T�C�Y�^�g�p���j���o�͂����B
	 */
	public PerformanceLogWriter(){
		startTime = System.currentTimeMillis();
	}
	
	
	/**
	 * �R���X�g���N�^
	 * @param startTime  �����J�n����
	 * @param memFlag    �������g�p�󋵏o�̓t���O�ifalse�̏ꍇ�͕\������Ȃ��j
	 */
	public PerformanceLogWriter(long startTime, boolean memFlag){
		this.startTime = startTime;
		this.memFlag   = memFlag;
	}
	
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * �����J�n���Ԃ����ݎ����Ƀ��Z�b�g����B
	 */
	public void resetTime(){
		this.startTime = System.currentTimeMillis();
	}
	
	
	/**
	 * ���O�o�̓��\�b�h�B
	 * �R���X�g���N�^�Ăяo�����̎��ԁi�܂��͑O��resetTime���\�b�h�Ăяo�����ԁj���J�n���ԁA
	 * ���Y���\�b�h���Ăяo�������Ԃ��I�����ԂƂ��āA���̍����u�������ԁv�Ƃ��ďo�͂���B
	 * @param moji			�o�͕�����i��{�I�ɏ������j
	 */
	public void out(String moji){
		long entTime = System.currentTimeMillis();
		out(moji, this.startTime, entTime, this.memFlag);
	}
	
	
	/**
	 * ���O�o�̓��\�b�h�B
	 * �R���X�g���N�^�Ăяo�����̎��ԁi�܂��͑O��resetTime���\�b�h�Ăяo�����ԁj���J�n���ԁA
	 * ���Y���\�b�h���Ăяo�������Ԃ��I�����ԂƂ��āA���̍����u�������ԁv�Ƃ��ďo�͂���B
	 * ���O�o�͌�A�����J�n���Ԃ����ݎ����Ƀ��Z�b�g����B
	 * @param moji			�o�͕�����i��{�I�ɏ������j
	 */
	public void outWithRstTime(String moji){
		long entTime = System.currentTimeMillis();
		out(moji, this.startTime, entTime, this.memFlag);
		resetTime();
	}
	
	
	/**
	 * ���O�o�̓��\�b�h�B
	 * �������s�I�����ԂƏ������s�J�n���ԂƂ̍����u�������ԁv�Ƃ��ďo�͂���B
	 * @param moji			�o�͕�����i��{�I�ɏ������j
	 * @param startTime    �������s�J�n����
	 * @param endTime      �������s�I������
	 * @param memFlag		�������g�p�󋵏o�̓t���O
	 */
	public static void out(String moji,
							 long startTime, 
							 long endTime, 
							 boolean memFlag)
	{
		//�o�͕�����
		StringBuffer buffer = new StringBuffer();
		buffer.append(moji);
		
		//�������Ԃ̎Z�o(ms)
		long processingTime = endTime - startTime;
		buffer.append(",��������(ms)�F").append(processingTime);
		
		//�������g�p�󋵂̎Z�o(KB)
		if(memFlag){
			Runtime rt       = Runtime.getRuntime();
			long   freeMem   = rt.freeMemory()  / 1024;
			long   totalMem  = rt.totalMemory() / 1024;
			long   usedMem   = totalMem - freeMem;
			double usageRate = ((double)usedMem  / (double)totalMem) * 100;
			buffer.append(",�󂫃������T�C�Y(KB)�F")
				  .append(freeMem)
				  .append(",�����������T�C�Y(KB)�F")
				  .append(totalMem)
				  .append(",�g�p�������T�C�Y(KB)�F")
				  .append(usedMem)
				  .append(",�������g�p��(%)�F")
				  .append(df.format(usageRate))
				  ;
		}
		
		//���O�o��
		log.debug(buffer);
		
	}
	
	
	
	/**
	 * �������g�p���i�p�[�Z���e�[�W�j���擾����B
	 * ���g�p���͐����Ɋۂ߂���B
	 * @return �������g�p��
	 */
	public static int checkUsedMemRate(){
		Runtime rt       = Runtime.getRuntime();
		long   freeMem   = rt.freeMemory();
		long   totalMem  = rt.totalMemory();
		long   usedMem   = totalMem - freeMem;
		double usageRate = ((double)usedMem  / (double)totalMem) * 100;
		return (int)usageRate;
	}
	
	
	
}


