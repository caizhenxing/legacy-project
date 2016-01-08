/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.util.FileResource;


/**
 * �t�@�C���̕ϊ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IConverter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IConverter {

	/**
	 * �ݒ���Ɋ�Â�IOD�t�@�C�����쐬����B
	 * @param iodSettingInfo		�ݒ��񃊃X�g
	 * @return						�쐬����IOD�t�@�C�����\�[�X
	 * @throws ConvertException		�쐬�����t�@�C���̎擾�Ɏ��s�����Ƃ��B
	 */
	public FileResource iodFileCreation(List iodSettingInfo) throws ConvertException;

	/**
	 * �w��t�@�C����IOD�t�@�C���ɕϊ�����B
	 * @param attachedResource		�Y�t�t�@�C�����\�[�X
	 * @return						�ϊ�����IOD�t�@�C�����\�[�X
	 * @throws ConvertException	�ϊ����ɗ�O�����������Ƃ��B
	 */
	public FileResource iodFileCreation(FileResource attachedResource) throws ConvertException;

	/**
	 * iod�t�@�C�����\�[�X���������A�p�X���[�h�tPDF���\�[�X���擾����B
	 * @param iodResources			��������t�@�C�����\�[�X(iod�t�@�C��)�B
	 * @param password				PDF���J�����߂̃p�X���[�h�B	
	 * @param jokyoId				�\����ID
	 * @param jigyoKbn				���Ƌ敪
	 * @return						��������PDF�t�@�C�����\�[�X
	 * @throws ConvertException	�ϊ����ɗ�O�����������Ƃ��B
	 */
	public FileResource iodToPdf(List iodFileResources, String password, String jokyoId, String jigyoKbn) throws ConvertException;


	//2005.07.15 iso PDF�Y�t�@�\�ǉ�
	/**
	 * PDF�t�@�C�����L�����𒲂ׂ�B
	 * @param fileRes					��������t�@�C�����\�[�X�B
	 * @return							�L���F0�@����:�G���[�R�[�h
	 * @throws ConvertException		�ϊ����ɗ�O�����������Ƃ��B
	 */
	public int checkPdf(FileResource fileRes) throws ConvertException;
}