/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 
package jp.go.jsps.kaken.model.vo;

import java.util.*;

import jp.go.jsps.kaken.util.*;

/**
 * �g�ݍ��킹�X�e�[�^�X����������ێ�����N���X�B
 * �\���󋵂P�ɑ΂��āA�����̍Đ\���t���O�󋵂����������Ƃ����ꍇ�Ɏg�p����B<br>
 * <p>
 * ���̂悤��SQL���g�p�������Ƃ��ɗp����B<br>
 * ex. A.JOKYO_ID = 'jokyoID' AND A.SAISHINSEI_FLG IN ('flg1','flg2',...)<br>
 * </p>
 * <p>
 * ��LSQL���𕡐��������邱�Ƃ��ł���B<br>
 * addAndQuery()���Ăяo�����ꍇ��AND�����AaddOrQuery()���Ăяo�����ꍇ��OR�����Ō�������B<br>
 * �������鏇�Ԃ͒ǉ��������ԂƂȂ�B<br> 
 * </p>
 * 
 * ID RCSfile="$RCSfile: CombinedStatusSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class CombinedStatusSearchInfo extends ValueObject{
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	/** ������������ */
	private static final String[] LOGICAL_OPERATOR = new String[]{" AND"," OR"};
	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** ��������SQL�̃��X�g */
	private List queryList = new ArrayList();
	
	/** �������������̃��X�g�iAND,OR�j*/
	private List andOrList = new ArrayList();
	
	//...

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public CombinedStatusSearchInfo() {
		super();
		clearQuery();
	}


	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	
	/**
	 * SQL�𐶐�����B������null�̏ꍇ�͂��̏��������O����SQL���쐬����B
	 * �����̈�����null�i�܂��͒l�������j�ꍇ�͗�O�𔭐�����B
	 * @param jokyoId       �\����
	 * @param saishinseiFlg �Đ\���t���O�̔z��
	 * @throws IllegalArgumentException �\���󋵁A�Đ\���t���O�̂ǂ�����l�����������ꍇ
	 * @return
	 */
	private String createSQL(String jokyoId, String[] saishinseiFlg)
		throws IllegalArgumentException
	{
		boolean     exist = false;
		StringBuffer query = new StringBuffer(" (");
		//�\����
		if(jokyoId != null && jokyoId.length() != 0){
			query.append(" A.JOKYO_ID = '")
			     .append(jokyoId)
			     .append("'");
			exist = true;
		}
		//�Đ\���t���O
		if(saishinseiFlg != null && saishinseiFlg.length != 0){
			if(exist){
				query.append(" AND");	//�\���󋵂̏������Z�b�g����Ă����ꍇ��AND�Ō���
			}
			query.append(" A.SAISHINSEI_FLG IN (")
				 .append(StringUtil.changeArray2CSV(saishinseiFlg, true))
				 .append(")");
			exist = true;
		}
		
		//�����������w�肳��Ă��Ȃ������ꍇ�̓G���[
		if(exist){
			return query.append(")").toString();
		}else{
			String msg = "���������̈����������ł��BjokyoId="+jokyoId+"saishinseiFlg="+Arrays.asList(saishinseiFlg);
			throw new IllegalArgumentException(msg);
		}
		
	}
	

	
	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * �����������N���A����B 
	 */
	public void clearQuery(){
		queryList.clear();
		andOrList.clear();
	}
	
	
	/**
	 * �����������Z�b�g����Ă����true��Ԃ��B
	 * @return
	 */
	public boolean hasQuery(){
		return !(queryList.isEmpty());
	}
	
	
	/**
	 * �n���ꂽ�\����(jokyoId)�ŁA���A�n���ꂽ�Đ\���t���O(saishinseiFlg)�̂����ꂩ
	 * �̌����������AAND�����Œǉ�����B
	 * �ŏ��ɒǉ����������̏ꍇ�́AAND�����������B
	 * getQuery()�Ŏ󂯎����SQL��AND�Ō������邩�AOR�Ō������邩�͏�ʑ��Ɉˑ��B
	 * @param jokyoId       �\����
	 * @param saishinseiFlg �Đ\���t���O�̔z��
	 * @throws IllegalArgumentException �\���󋵁A�Đ\���t���O�̂ǂ�����l�����������ꍇ
	 */
	public void addAndQuery(String jokyoId, String[] saishinseiFlg) 
		throws IllegalArgumentException{
		queryList.add(createSQL(jokyoId, saishinseiFlg));
		andOrList.add(LOGICAL_OPERATOR[0]);
	}
	
	/**
	 * �n���ꂽ�\����(jokyoId)�ŁA���A�n���ꂽ�Đ\���t���O(saishinseiFlg)�̂����ꂩ
	 * �̌����������AOR�����Œǉ�����B
	 * �ŏ��ɒǉ����������̏ꍇ�́AOR�����������B
	 * getQuery()�Ŏ󂯎����SQL��AND�Ō������邩�AOR�Ō������邩�͏�ʑ��Ɉˑ��B
	 * @param jokyoId       �\����
	 * @param saishinseiFlg �Đ\���t���O�̔z��
	 * @throws IllegalArgumentException �\���󋵁A�Đ\���t���O�̂ǂ�����l�����������ꍇ
	 */
	public void addOrQuery(String jokyoId, String[] saishinseiFlg) 
		throws IllegalArgumentException{
		queryList.add(createSQL(jokyoId, saishinseiFlg));
		andOrList.add(LOGICAL_OPERATOR[1]);
	}
	
	/**
	 * �n���ꂽ�\����(jokyoId)�ŁA���A�n���ꂽ�Đ\���t���O(saishinseiFlg)�̂����ꂩ
	 * �̌����������AAND�����Ŏw��C���f�b�N�X�ɒǉ�����B
	 * �ŏ��ɒǉ����������̏ꍇ�́AAND�����������B
	 * getQuery()�Ŏ󂯎����SQL��AND�Ō������邩�AOR�Ō������邩�͏�ʑ��Ɉˑ��B
	 * @param  jokyoId       �\����
	 * @param  saishinseiFlg �Đ\���t���O�̔z��
	 * @param  index         ������}������C���f�b�N�X
	 * @throws IndexOutOfBoundsException �C���f�b�N�X���͈͊O�̏ꍇ
	 * @throws IllegalArgumentException �\���󋵁A�Đ\���t���O�̂ǂ�����l�����������ꍇ
	 */
	public void addAndQuery(String jokyoId, String[] saishinseiFlg, int index)
		throws IndexOutOfBoundsException, IllegalArgumentException{
		queryList.add(index, createSQL(jokyoId, saishinseiFlg));
		andOrList.add(index, LOGICAL_OPERATOR[0]);
	}
	
	/**
	 * �n���ꂽ�\����(jokyoId)�ŁA���A�n���ꂽ�Đ\���t���O(saishinseiFlg)�̂����ꂩ
	 * �̌����������AOR�����Ŏw��C���f�b�N�X�ɒǉ�����B
	 * �ŏ��ɒǉ����������̏ꍇ�́AOR�����������B
	 * getQuery()�Ŏ󂯎����SQL��AND�Ō������邩�AOR�Ō������邩�͏�ʑ��Ɉˑ��B
	 * @param  jokyoId       �\����
	 * @param  saishinseiFlg �Đ\���t���O�̔z��
	 * @param  index         ������}������C���f�b�N�X
	 * @throws IndexOutOfBoundsException �C���f�b�N�X���͈͊O�̏ꍇ
	 * @throws IllegalArgumentException �\���󋵁A�Đ\���t���O�̂ǂ�����l�����������ꍇ
	 */
	public void addOrQuery(String jokyoId, String[] saishinseiFlg, int index)
		throws IndexOutOfBoundsException, IllegalArgumentException{
		queryList.add(index, createSQL(jokyoId, saishinseiFlg));
		andOrList.add(index, LOGICAL_OPERATOR[1]);
	}
	
	
	/**
	 * ��������SQL����Ԃ��B<br>
	 * �\���f�[�^�e�[�u���ɑ΂��āuA�v�Ƃ����ʖ������Ă��邱�ƁB<br>
	 * �����������w�肳��Ă��Ȃ������ꍇ�́Anull���Ԃ�B
	 * <p>
	 * ex. ���̂悤�ȕ����񂪕Ԃ�B<br>
	 * ( A.JOKYO_ID = 'jokyoID' AND A.SAISHINSEI_FLG IN ('flg1','flg2',...) )<br>
	 *  [AND/OR]<br>
	 * ( A.JOKYO_ID = 'jokyoID2' AND A.SAISHINSEI_FLG IN ('flg1','flg2',...) )<br>
	 *  [AND/OR]<br>
	 * �@�@�F<br>
	 * </p>
	 * @return
	 */
	public String getQuery(){
		
		//�������������̏ꍇ
		if(!hasQuery()){
			return null;
		}
		
		//����SQL���\�z
		StringBuffer query = new StringBuffer(" (");
		
		//�ŏ���AND/OR�����͏��O���邽�߁A1�ڂ����͌ʂɃZ�b�g����
		query.append(StringUtil.defaultString(queryList.get(0)));
		
		//2�ڈȍ~�͌J��Ԃ�
		for(int i=1; i<queryList.size(); i++){
			query.append(StringUtil.defaultString(andOrList.get(i)))
			     .append(StringUtil.defaultString(queryList.get(i)));
		}
		
		return query.append(")").toString();
		
	}	
	
	
}
