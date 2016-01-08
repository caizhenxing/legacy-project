/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.pdf.autoConverter;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.util.FileResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �ϊ��������ʂ�ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ConvertResult.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:55 $"
 */
public class ConvertResult {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(ConvertResult.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * �ϊ����ꂽ�t�@�C���A�܂��͗�O�B
	 */
	private Object realData = null;

	/**
	 * �������ʂ̏������ł������ǂ����̃t���O�B
	 */
	private boolean ready = false;

	/**
	 * �ϊ��Ώۃt�@�C�����ێ��}�b�v�B
	 */
	private AutoConverter converter;


	/** �^�C���A�E�g�l�i�b�j */
	private final int timeOut;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * 	�R���X�g���N�^�B
	 * @param converter		�ϊ��N���X�B
	 */
	public ConvertResult(AutoConverter converter) {	
		super();
		this.converter = converter;
		this.timeOut	= ApplicationSettings.getInteger(ISettingKeys.PDF_TIMEOUT).intValue() * 1000;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * ���ۂɕϊ����ꂽ�������ʃt�@�C�����Z�b�g����B
	 * @param realdata
	 */
	public synchronized void setRealData(Object realData) {
		if (ready) {
			return;
		}
		this.realData = realData;
		this.ready = true;
		//�ʒm
		notifyAll();
	}


	/**
	 * �ϊ����ʂ��擾����B
	 * �ω��I���܂őҋ@����B
	 * @return	�������ʃt�@�C���B
	 * @throws ConvertException	�ϊ����ɗ�O�����������ꍇ�B
	 */
	public synchronized FileResource getResult() throws ConvertException {

		//�ϊ����ʂ��Z�b�g�����܂őҋ@����B
		long start = System.currentTimeMillis();

		while(!ready){
			//�^�C���A�E�g���Ȃ��B
			if(timeOut == 0){
				try{
					wait();
				}catch(InterruptedException e){
					if(log.isDebugEnabled()){
						log.debug(e);
					}
				}
			//�����b��Ƀ^�C���A�E�g����B	
			}else{
				long now 	= System.currentTimeMillis();
				long rest	= (timeOut == 0) ? 0 : timeOut  - (now - start);	//�҂�����
				if(timeOut !=0 && rest <= 0){
					log.debug("�^�C���A�E�g���������܂����Bnow - start = " + ( now - start ) + " >= timeout = " + timeOut );
					converter.removeFileInfo(this);
					throw new ConvertException("�ϊ��^�C���A�E�g���������܂����B",new ErrorInfo("errors.8001"));
				}
				try{
					wait(rest);
				}catch(InterruptedException e){
					if(log.isDebugEnabled()){
						log.debug(e);
					}
				}
			}
		}
		
		//��O���������Ă����ꍇ�́A��O���X���[����B
		if (realData instanceof SystemException) {
			throw (SystemException) realData;
		} else {
			return (FileResource) realData;
		}
	}
}
