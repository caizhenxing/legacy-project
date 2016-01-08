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
package jp.go.jsps.kaken.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * �y�[�W����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: Page.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class Page implements Serializable {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -4173912581720244628L;
	
	/** ��̃y�[�W�������I�u�W�F�N�g */
	public static final Page EMPTY_PAGE =
		new Page(Collections.EMPTY_LIST, 0, 0, 0);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �擾�f�[�^���X�g */
	private List list;

	/** �擾�f�[�^�J�n�ʒu�ԍ�*/
	int start;

	/** �y�[�W������̕\������ */
	int pageSize;

	/** �Ώۃf�[�^������*/
	int totalSize;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param list		�擾�f�[�^���X�g
	 * @param start		�擾�f�[�^�J�n�ʒu�ԍ�
	 * @param pageSize	�y�[�W������̕\������
	 * @param totalSize	�Ώۃf�[�^������
	 */
	public Page(List list, int start, int pageSize, int totalSize) {
		super();
		this.list = new ArrayList(list);
		this.start = start;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * ���y�[�W�����݂��邩�ǂ������`�F�b�N����B
	 * @return	���݂���ꍇ true �ȊO false
	 */
	public boolean hasNextPage() {
		return (start + list.size()) < totalSize;
	}

	/**
	 * �O�y�[�W�����݂��邩�ǂ������`�F�b�N����B
	 * @return	���݂���ꍇ true �ȊO false
	 */
	public boolean hasPreviousPage() {
		return start > 0;
	}

	/**
	 * ���y�[�W�̊J�n�ʒu�ԍ����擾����B
	 * @return	�J�n�ʒu�ԍ�
	 */
	public int getStartOfNextPage() {
		return start + list.size();
	}

	/**
	 * �O�y�[�W�̊J�n�ʒu�ԍ����擾����B
	 * @return
	 */
	public int getStartOfPreviousPage() {
		if (hasNextPage()) {
			return Math.max(start - list.size(), 0);
		}
		return start - pageSize;
	}

	/**
	 * ����擾�����f�[�^�������擾����B
	 * @return
	 */
	public int getSize() {
		return list.size();
	}

	/* (�� Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer results = new StringBuffer();
		results.append("\tstart='" + start + "'\n");
		results.append("\ttotalSize='" + totalSize + "'\n");
		results.append("\tpageSize='" + pageSize + "'\n");
		results.append("\ttotalPages='" + getTotalPages() + "'\n");
		results.append("\tcurrentPages='" + getCurrentPages() + "'\n");
		return results.toString();
	}

	/**
	 * �������ʃ��X�g���擾����B
	 * @return	�������ʂ��܂ރ��X�g�I�u�W�F�N�g�B
	 */
	public List getList() {
		return list;
	}

	/**
	 * �������ʃ��X�g��ݒ肷��B
	 * @param	�������ʂ��܂ރ��X�g�I�u�W�F�N�g�B
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * ���y�[�W�����擾����B
	 * @return	�������ʂ��܂ރ��X�g�I�u�W�F�N�g�B
	 */
	public int getTotalPages() {
		//�y�[�W�T�C�Y��0�̂Ƃ��͂��ׂẴf�[�^��1�y�[�W�ŕ\������B
		if (pageSize == 0)
			return 1;
		return (int) Math.ceil((double) totalSize / pageSize);
	}

	/**
	 * ���݂̃y�[�W�����擾����B
	 * @return	�\�����̃y�[�W
	 */
	public Integer getCurrentPages() {
		//�y�[�W�T�C�Y��0�̂Ƃ��͂��ׂẴf�[�^��1�y�[�W�ŕ\������B
		if (pageSize == 0) {
			return new Integer(1);
		} else {
			return new Integer((start / pageSize) + 1);
		}
	}

	/**
	 * �y�[�W�J�ڂ̃C���f�b�N�X���擾����B
	 * @return	�擾�J�n�ʒu�ԍ���ێ����郊�X�g
	 */
	public List getPageIndexs() {
		int totalPages = getTotalPages();
		ArrayList index = new ArrayList(totalPages);
		//�y�[�W������ԃX�^�[�g�|�W�V�������Z�b�g���Ă����B
		for (int i = 0; i < totalPages; i++) {
			index.add(Integer.toString((i * pageSize)));
		}
		return index;
	}
	
	/**
	 * ���������擾����B
	 * @return	���������ɍ������f�[�^�����B
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * �y�[�W������̕\���������擾����B
	 * @return	���������ɍ������f�[�^�����B
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * �y�[�W�̊J�n�ʒu�ԍ����擾����B
	 * @return
	 */
	public int getStart() {
		return start;
	}

}
