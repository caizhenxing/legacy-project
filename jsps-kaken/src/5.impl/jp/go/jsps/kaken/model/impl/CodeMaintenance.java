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
package jp.go.jsps.kaken.model.impl;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.ICodeMaintenance;
import jp.go.jsps.kaken.model.dao.impl.MasterBukyokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKeywordInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterRyouikiInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterSaimokuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;

import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * �R�[�h�\���Ǘ����s���N���X�B
 * 
 * ID RCSfile="$RCSfile: CodeMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class CodeMaintenance implements ICodeMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	
	/** ���O */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);
	
		
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public CodeMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// implement IShinsaKekkaMaintenance
	//---------------------------------------------------------------------
	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanInfoList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
//	public List getKikanInfoList(UserInfo userInfo, String shubetuCd) throws ApplicationException {
//		
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//			
//			//�����@�֏����擾����
//			return MasterKikanInfoDao.selectKikanList(connection, shubetuCd);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public Map getKikanInfoList(UserInfo userInfo) throws ApplicationException {
//
//		Connection   connection  = null;
//		Map codeMap = new HashMap();
//		try {	
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//						
//			//------�����f�[�^
//			//�J�e�S�����X�g�i�����p�j���擾
//			List kategoriList = MasterKikanInfoDao.selectKikanShubetuList(connection);;			
//				
//			//------�ꗗ�f�[�^
//			//�@�֎�ʁi�敪�j���Ƃɏ����@�փ��X�g���쐬����
//			List ichiranList = new ArrayList();
//			if(kategoriList != null){
//				Iterator iterator = kategoriList.iterator();			
//				while(iterator.hasNext()){
//					Map shubetuMap = (Map)iterator.next();
//					String shubetuCd = (String)shubetuMap.get("SHUBETU_CD");		//��ʃR�[�h
//					String shubetuName = (String)shubetuMap.get("SHUBETU_NAME");	//�@�֎�ʖ�
//
//					//�@�֎�ʃR�[�h�ɊY�����鏊���@�փ��X�g���擾
//					List kikanList = MasterKikanInfoDao.selectKikanList(connection, shubetuCd);
//					
//					//�ꗗ�p�̃}�b�v���쐬�i�P�̋@�֎�ʂɑ΂��Ă̏����@�փ��X�g�j
//					Map ichiranMap = new HashMap();
//					ichiranMap.put("KIKAN_LIST", kikanList);			//�����@�փ��X�g
//					ichiranMap.put("SHUBETU_NAME", shubetuName);		//�����@�֎�ʖ�
//					ichiranMap.put("SHUBETU_CD", shubetuCd);			//�����@�֎�ʃR�[�h			
//					
//					//�ꗗ�p���X�g�ɒǉ�
//					ichiranList.add(ichiranMap);
//				}
//			}	
//			
//			//�R�[�h�\�p�̃}�b�v���쐬
//			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, kategoriList);		//�����p���X�g
//			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//�ꗗ�p���X�g
//					
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//					
//		return codeMap;
//	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Map getKikanInfoList(UserInfo userInfo) throws ApplicationException {

		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {	
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
						
			//------�����f�[�^
			List indexList = MasterKikanInfoDao.selectKikanCodeIndex(connection);		
				
			//------�ꗗ�f�[�^
			List ichiranList = MasterKikanInfoDao.selectKikanCodeList(connection);;		
			
			//�R�[�h�\�p�̃}�b�v���쐬
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, indexList);		//�����p���X�g
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//�ꗗ�p���X�g
					
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
					
		return codeMap;
	}




	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanShubetuInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getKikanShubetuInfoList(UserInfo userInfo) throws ApplicationException {
//
//		//��ʃR�[�h�̈ꗗ���擾����
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//				
//			//�����@�֎�ʏ����擾����
//			return MasterKikanInfoDao.selectKikanShubetuList(connection);			
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBunkaNameList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getBunkaNameList(UserInfo userInfo, String buName) throws ApplicationException {
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//			
//			//���Ȗ����擾����
//			return MasterSaimokuInfoDao.selectBunkaNameList(connection, buName);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getSaimokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
//	public List getSaimokuInfoList(UserInfo userInfo, String bunkaName) throws ApplicationException {
//
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//			
//			//���Ȗ����擾����
//			return MasterSaimokuInfoDao.selectSaimokuInfoList(connection, bunkaName);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}
	
	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBuNameList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getBuNameList(UserInfo userInfo) throws ApplicationException {
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//			
//			//���얼���擾����
//			return MasterSaimokuInfoDao.selectBunyaNameList(connection);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}


	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getSaimokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Map getSaimokuInfoList(UserInfo userInfo) throws ApplicationException {
		
		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//------�����f�[�^
			//���얼���X�g�i�����p�j���擾
			List bunyaNameList = MasterSaimokuInfoDao.selectBunyaNameList(connection);
						

			List ichiranList = new ArrayList();
			
			//------�ꗗ�f�[�^
			//���삲�Ƃɕ��ȃ��X�g���쐬����
			if(bunyaNameList != null){		
				
				Iterator iterator = bunyaNameList.iterator();	
				while(iterator.hasNext()){
					Map bunyaMap = (Map)iterator.next();
					String bunyaName = (String)bunyaMap.get("BUNYA_NAME");			//���얼
					String bunyaCd = (String)(bunyaMap.get("BUNYA_CD")).toString();	//����R�[�h
					
					//����R�[�h�ɊY�����镪�ȃ��X�g���擾
					List bunyaBunkaList =MasterSaimokuInfoDao.selectBunkaNameList(connection, bunyaCd);
				
					//���ȃR�[�h�ɊY������זڃ��X�g���擾
					List bunkaList = new ArrayList();
					if(bunyaBunkaList != null){
						Iterator ite = bunyaBunkaList.iterator();			
						while(ite.hasNext()){
							Map saimokuMap = (Map)ite.next();
							String bunkaName = (String)saimokuMap.get("BUNKA_NAME");	//���Ȗ�
							String bunkaCd = (String)saimokuMap.get("BUNKA_CD");		//���ȃR�[�h
							
							//���Ȃ��Ƃ̍זڃ��X�g���擾
							List saimokuList = MasterSaimokuInfoDao.selectSaimokuInfoList(connection, bunkaCd);
							
							//���Ȃ��Ƃ̃}�b�v���쐬�i�P�̕��Ȃɑ΂��Ă̍זڃ��X�g�j
							Map bunkaMap = new HashMap();
							
							bunkaMap.put("SAIMOKU_LIST", saimokuList);	//���Ȃ��Ƃ̍זڃ��X�g
							bunkaMap.put("BUNKA_NAME", bunkaName);		//���Ȗ�						
							
							//���ȃ��X�g�ɒǉ�
							bunkaList.add(bunkaMap);
						}
					}
					
					//�ꗗ�p�̃}�b�v���쐬�i�P�̕���ɑ΂��Ă̕��ȃ��X�g�j
					Map ichiranMap = new HashMap();
					ichiranMap.put("BUNYA_NAME", bunyaName);		//���얼
					ichiranMap.put("BUNYA_CD", bunyaCd);			//���얼�R�[�h
					ichiranMap.put("BUNKA_LIST", bunkaList);		//���얼���̕��ȃ��X�g
					
					//�ꗗ�p���X�g�ɒǉ�
					ichiranList.add(ichiranMap);			
				}
			}
			
			//�R�[�h�\�p�̃}�b�v���쐬
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, bunyaNameList);	//�����p���X�g
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//�ꗗ�p���X�g
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return codeMap;

	}


	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBukyokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
//	public List getBukyokuInfoList(UserInfo userInfo, String index) throws ApplicationException {
//
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//			
//			//���Ǐ����擾����
//			return MasterBukyokuInfoDao.selectBukyokuInfoList(connection, index);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//
//	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBukyokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Map getBukyokuInfoList(UserInfo userInfo) throws ApplicationException {

		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
						
			//------�����f�[�^
			//�J�e�S�����X�g�i�����p�j���擾
			List indexList = MasterBukyokuInfoDao.selectKategoriInfoList(connection);;
				
			//------�ꗗ�f�[�^
			//�J�e�S�����Ƃɕ��ǃ��X�g���쐬����
			List ichiranList = new ArrayList();
			if(indexList != null){
				Iterator iterator = indexList.iterator();			
				while(iterator.hasNext()){
					Map kategoriMap = (Map)iterator.next();
					String bukaKategori = (String)kategoriMap.get("BUKA_KATEGORI");
					String kategoriName = (String)kategoriMap.get("KATEGORI_NAME");

					//�J�e�S���ɊY�����镔�ǃ��X�g���擾
					List bukyokuList = MasterBukyokuInfoDao.selectBukyokuInfoList(connection, bukaKategori);
					
					//�ꗗ�p�̃}�b�v���쐬�i�P�̃J�e�S���ɑ΂��Ă̕��ǃ��X�g�j
					Map ichiranMap = new HashMap();
					ichiranMap.put("BUKA_KATEGORI", bukaKategori);		//�J�e�S��
					ichiranMap.put("KATEGORI_NAME", kategoriName);		//�J�e�S������
					ichiranMap.put("BUKYOKU_LIST", bukyokuList);		//���ǃ��X�g
					ichiranList.add(ichiranMap);
				}
			}
			
			//�R�[�h�\�p�̃}�b�v���쐬
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, indexList);		//�����p���X�g
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//�ꗗ�p���X�g
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}			
			
		return codeMap;
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getSakuinList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getSakuinList(UserInfo userInfo) throws ApplicationException {
//		
//		Connection   connection  = null;
//		try {
//			//DB�R�l�N�V�����̎擾
//			connection = DatabaseUtil.getConnection();
//			
//			//�������X�g���擾����
//			return MasterBukyokuInfoDao.selectSakuinList(connection);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}


	/**
	 * �L�[���[�h�ꗗ�\�������擾����
	 * @param userInfo
	 * @return�@�L�[���[�h�ꗗ�\�����
	 * @throws ApplicationException
	 */
	public Map getKeywordInfoList(UserInfo userInfo) throws ApplicationException {
		
		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//------�����f�[�^
			//���얼���X�g�i�����p�j���擾
			List bunyaNameList = MasterKeywordInfoDao.selectBunyaNameList(connection);
						

			List ichiranList = new ArrayList();
			
			//------�ꗗ�f�[�^
			//���삲�Ƃɕ��ȃ��X�g���쐬����
			if(bunyaNameList != null){		
				
				Iterator iterator = bunyaNameList.iterator();	
				while(iterator.hasNext()){
					Map bunyaMap = (Map)iterator.next();
					String bunyaName = (String)bunyaMap.get("BUNYA_NAME");			//���얼
					String bunyaCd = (String)(bunyaMap.get("BUNYA_CD")).toString();	//����R�[�h
					
					//����R�[�h�ɊY�����镪�ȃ��X�g���擾
					List bunyaBunkaList =MasterKeywordInfoDao.selectBunkaNameList(connection, bunyaCd);
				
					//���ȃR�[�h�ɊY������זڃ��X�g���擾
					List bunkaList = new ArrayList();
					if(bunyaBunkaList != null){
						Iterator ite = bunyaBunkaList.iterator();			
						while(ite.hasNext()){
							Map saimokuMap = (Map)ite.next();
							String bunkaName = (String)saimokuMap.get("BUNKA_NAME");	//���Ȗ�
							String bunkaCd = (String)saimokuMap.get("BUNKA_CD");		//���ȃR�[�h
							
							//���Ȃ��Ƃ̍זڃ��X�g���擾
							List saimokuList = MasterKeywordInfoDao.selectKeywordInfoList(connection, bunkaCd);
							
							//���Ȃ��Ƃ̃}�b�v���쐬�i�P�̕��Ȃɑ΂��Ă̍זڃ��X�g�j
							Map bunkaMap = new HashMap();
							
							bunkaMap.put("SAIMOKU_LIST", saimokuList);	//���Ȃ��Ƃ̍זڃ��X�g
							bunkaMap.put("BUNKA_NAME", bunkaName);		//���Ȗ�						
							
							//���ȃ��X�g�ɒǉ�
							bunkaList.add(bunkaMap);
						}
					}
					
					//�ꗗ�p�̃}�b�v���쐬�i�P�̕���ɑ΂��Ă̕��ȃ��X�g�j
					Map ichiranMap = new HashMap();
					ichiranMap.put("BUNYA_NAME", bunyaName);		//���얼
					ichiranMap.put("BUNYA_CD", bunyaCd);			//���얼�R�[�h
					ichiranMap.put("BUNKA_LIST", bunkaList);		//���얼���̕��ȃ��X�g
					
					//�ꗗ�p���X�g�ɒǉ�
					ichiranList.add(ichiranMap);			
				}
			}
			
			//�R�[�h�\�p�̃}�b�v���쐬
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, bunyaNameList);	//�����p���X�g
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//�ꗗ�p���X�g
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return codeMap;

	}

	/**
	 * �̈�}�X�^�ꗗ���擾
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Map getRyoikiInfoList(UserInfo userInfo) throws ApplicationException {

		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
						
			//------�����f�[�^
			//�J�e�S�����X�g�i�����p�j���쐬
			List indexList = new ArrayList(2);
			Map map = new HashMap();
			map.put("KUBUN", "1");
			map.put("NAME",  "�u�v�挤���v�ɌW�錤���ۑ�̉��发�ނ��o���鎞���ɓ����錤���̈�");
			indexList.add(map);
			map = new HashMap();
			map.put("KUBUN", "2");
			map.put("NAME",  "�u���匤���v�ɌW�錤���ۑ�̉��发�ނ��o���鎞���ɓ����錤���̈�");
			indexList.add(map);
				
			//------�ꗗ�f�[�^
			//�J�e�S�����ƂɌ����̈惊�X�g���쐬����
			List ichiranList = new ArrayList();
			if(indexList != null){
				Iterator iterator = indexList.iterator();			
				while(iterator.hasNext()){
					Map kategoriMap = (Map)iterator.next();
					String bukaKategori = (String)kategoriMap.get("KUBUN");
					String kategoriName = (String)kategoriMap.get("NAME");

					//�J�e�S���ɊY�����镔�ǃ��X�g���擾
					List ryoikiList = MasterRyouikiInfoDao.selectRyoikiInfoList(connection, bukaKategori);
					
					//�ꗗ�p�̃}�b�v���쐬�i�P�̃J�e�S���ɑ΂��Ă̕��ǃ��X�g�j
					Map ichiranMap = new HashMap();
					ichiranMap.put("KUBUN", bukaKategori);		//�J�e�S��
					ichiranMap.put("NAME",  kategoriName);		//�J�e�S������
					ichiranMap.put("RYOIKI_LIST", ryoikiList);		//�̈惊�X�g
					ichiranList.add(ichiranMap);
				}
			}
			
			//�R�[�h�\�p�̃}�b�v���쐬
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, indexList);		//�����p���X�g
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//�ꗗ�p���X�g
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}			
			
		return codeMap;
	}

//2006/07/24�@�c�@�ǉ���������
    /**
     * �̈�}�X�^�i�V�K�̈�j�ꗗ���擾
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
    public Map getRyoikiSinnkiInfoList(UserInfo userInfo) throws ApplicationException {

        Connection   connection  = null;
        Map codeMap = new HashMap();
        try {
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
                        
            //------�ꗗ�f�[�^
            //�J�e�S�����ƂɌ����̈惊�X�g���쐬����
            List ichiranList = new ArrayList();

            //�J�e�S���ɊY�����镔�ǃ��X�g���擾
            List ryoikiList = MasterRyouikiInfoDao.selectRyoikiSinnkiInfoList(connection);
            
            //�ꗗ�p�̃}�b�v���쐬�i�P�̃J�e�S���ɑ΂��Ă̕��ǃ��X�g�j
            Map ichiranMap = new HashMap();
            ichiranMap.put("RYOIKI_LIST", ryoikiList);      //�̈惊�X�g
            ichiranList.add(ichiranMap);
            
            //�R�[�h�\�p�̃}�b�v���쐬
            codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);     //�ꗗ�p���X�g
        
        } finally {
            DatabaseUtil.closeConnection(connection);
        }           
            
        return codeMap;
    }
//2006/07/24 �c�@�ǉ������܂�    
}
