/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.select;

import java.sql.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �y�[�W���Ɋ�Â��A�f�[�^���擾����B
 * 
 * ID RCSfile="$RCSfile: PageReading.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class PageReading{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	/** ���O */
	protected static Log log = LogFactory.getLog(PageReading.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���������I�u�W�F�N�g */
	private SearchInfo info;

	/** �����擾�p����SQL�X�e�[�g�����g*/
	private PreparedStatementCreator countCerater;

	/** �����pSQL�X�e�[�g�����g*/
	private PreparedStatementCreator dataCreator;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^
	 * �Y������MAX�l�𒴂������O�����B
	 * @param info		���������I�u�W�F�N�g
	 * @param countCerater		
	 * @param dataCreator		
	 */
	public PageReading(SearchInfo info,PreparedStatementCreator countCerater, PreparedStatementCreator dataCreator) {
		super();
		this.info = info;
		this.countCerater = countCerater;
		this.dataCreator = dataCreator;
	}

	
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * �������������s����B
	 * @return	�������ʃy�[�W���B
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws RecordCountOutOfBoundsException
	 */
	public final Page search(Connection connection) 
		throws DataAccessException,NoDataFoundException, RecordCountOutOfBoundsException{

		//�����e���v���[�g
		JDBCReading searchUtil = new JDBCReading();
		try {
			CountFieldCallbackHandler handler = new CountFieldCallbackHandler();
			searchUtil.query(connection, countCerater, handler);
			//�������̎擾
			int totalCount = handler.getCount();
			//�f�[�^�����`�F�b�N
			if(totalCount == 0){
				throw new NoDataFoundException("�����Ɉ�v����f�[�^��������܂���ł����B");
			}else if(info.getMaxSize()>0 && totalCount>info.getMaxSize()){
				String msg = "�Y��������MAX�l�𒴂��܂����BtotalCount="+totalCount+", maxSize="+info.getMaxSize();
				throw new RecordCountOutOfBoundsException(msg);
			}else if(totalCount <= info.getStartPosition()){
				//�Y������y�[�W�����݂��Ȃ��̂ŁA�O�̃y�[�W��\���B�i�J�n�s�f�t�H���g��0�j
				setBeforeStartPosition(totalCount, info.getStartPosition());
			}
			
			//�y�[�W�̎擾
			ListCallbackHandler pageHandler = new ListCallbackHandler();
			searchUtil.query(
				connection,
				dataCreator,
				pageHandler,
				info.getStartPosition(),
				info.getPageSize());

			//�y�[�W���̐ݒ�
			Page pageInfo =
				new Page(
					pageHandler.getResult(),
					info.getStartPosition(),
					info.getPageSize(),
					(int)totalCount);
			return pageInfo;

		} catch (DataAccessException dae) {
			if (log.isDebugEnabled()) {
				log.debug("�����R�}���h���s���ɗ�O���������܂����B", dae);
			}
			throw dae;
		}
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * ���������I�u�W�F�N�g�ɑO�̃y�[�W�̊J�n�s��ݒ肷��B
	 * �O�̃y�[�W
	 * @throws NoDataFoundException
	 */
	private void setBeforeStartPosition(int totalCount ,int startPosition)
		 throws NoDataFoundException {
		
		//���������J�n�s���傫���Ȃ�܂ŁA�J��Ԃ��B
		while(totalCount <= startPosition){
			if(startPosition >= 0){
				this.info.setStartPosition(startPosition - info.getPageSize());
				startPosition = this.info.getStartPosition();
			}else{
				throw new NoDataFoundException("�J�n�s���s���Ȓl�ł��B�J�n�s'" + startPosition+ "'");
			}
		}
	}	
}
