/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jp.go.jsps.kaken.model.IConverter;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.pdf.autoConverter.AutoConverter;
import jp.go.jsps.kaken.model.pdf.autoConverter.ConvertResult;
import jp.go.jsps.kaken.model.pdf.webdoc.WebdocUtil;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �f�[�^�̕ϊ�����������N���X�B
 * 
 * ID RCSfile="$RCSfile: Converter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class Converter implements IConverter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(Converter.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public Converter() {
		super();
	}

	//---------------------------------------------------------------------
	// implements IConverter
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IConverter#iodFileCreation(java.util.List)
	 */
	public FileResource iodFileCreation(List iodSettingInfo) throws ConvertException{
		
		//-------------------------------------
		// �����󋵔���t���O
		//-------------------------------------
		boolean success = false;
		
		//-------------------------------------
		// �t�@�C���쐬�B
		//-------------------------------------
		File outIodFile = WebdocUtil.iodFileCreation(iodSettingInfo);
		
		//-------------------------------------
		//�쐬�����t�@�C����ǂݍ��ށB
		//-------------------------------------
		try {
			//PDF�t�@�C�����擾����B
			FileResource iodFileResource = FileUtil.readFile(outIodFile);
			success = true;
			//PDF�t�@�C���̃��^�[��
			return iodFileResource;
		} catch (IOException e) {
			throw new ConvertException(
				"�쐬�t�@�C��'" + outIodFile + "'���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.8005"),
				e);
		}finally{
			if (success) {
				//-------------------------------------
				//��ƃt�@�C���̍폜
				//-------------------------------------
				FileUtil.delete(outIodFile.getParentFile());
			}
		}
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IConverter#iodFileCreation(jp.go.jsps.kaken.util.FileResource)
	 */
	public synchronized FileResource iodFileCreation(FileResource attachedResource) throws ConvertException {

		//-------------------------------------
		// �Y�t�t�@�C����PDF�ɂ���B
		//-------------------------------------
		ConvertResult convertResult = AutoConverter.getConverter().setFileResource(attachedResource);
		
		//-------------------------------------
		// PDF�ɕϊ������f�[�^���擾����B
		//-------------------------------------
		FileResource result = convertResult.getResult();
		//2005.07.14 iso �Y�t�t�@�C����PDF�ɕϊ�����悤�ɕύX
//		result.setPath(attachedResource.getName()+ WebdocUtil.IOD);
		result.setPath(attachedResource.getName()+ WebdocUtil.PDF);

		//-------------------------------------
		// �ϊ������f�[�^�����^�[��
		//-------------------------------------
		return result;
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IConverter#iodToPdf(java.util.List, java.lang.String)
	 */
	public FileResource iodToPdf(List iodFileResources, String password,
									String jokyoId, 	String jigyoKbn)
	throws ConvertException {

		//-------------------------------------
		// �����󋵔���t���O
		//-------------------------------------
		boolean success = false;

		//-------------------------------------
		// IOD�t�@�C����PDF�ɂ���B
		//-------------------------------------
		File outPdfFile = WebdocUtil.iodToPdf(iodFileResources, password);

        //2007/06/15 �ǉ�
		// ���Ƌ敪��4:��ՁA���\����ID��02�F���m�F�̏ꍇ�A������������\������
        if (!StringUtil.isBlank(jokyoId) && !StringUtil.isBlank(jigyoKbn)) {
    		if ("4".equals(jigyoKbn)) {
    			if (StatusCode.STATUS_SHINSEISHO_MIKAKUNIN.equals(jokyoId)) {
    				File pdfFile = WebdocUtil.addWaterMark(outPdfFile, password, "��o�m�F�p");
    				// �w�i�ǉ�����
    				if(pdfFile != null){
    					outPdfFile = pdfFile;
    				}
    			}
    		}
        }
		// 2007/06/15 �ǉ������܂�
		
		try {
			//-------------------------------------
			// PDF�ɕϊ������f�[�^���擾����B
			//-------------------------------------
			FileResource result = FileUtil.readFile(outPdfFile);

			//-------------------------------------
			//�t�@�C�����͐擪�̂��̂ɂ���B		
			//-------------------------------------
			FileResource outPdfFileName = (FileResource) iodFileResources.get(0);
			result.setPath(outPdfFileName.getName());
			
			success = true;
		
			//-------------------------------------
			// �ϊ������f�[�^�����^�[��
			//-------------------------------------
			return result;
		
		} catch (IOException e) {
			throw new ConvertException(
				"�ϊ�����pdf�t�@�C��'" + outPdfFile + "'�̓Ǎ��݂Ɏ��s���܂����B",
					new ErrorInfo("errors.8003"),
					e);
		}finally{
			if (success) {
				//-------------------------------------
				//��ƃt�@�C���̍폜
				//-------------------------------------
				FileUtil.delete(outPdfFile.getParentFile());
			}
		}
	}

	//2005.07.15 iso PDF�Y�t�@�\�ǉ�
	/* (�� Javadoc)
	 * @see jp.go.jsps.model.IConverter#checkPdf(java.lang.String)
	 */
	public int checkPdf(FileResource fileRes) throws ConvertException {
		return WebdocUtil.checkPdf(fileRes);
	}

}
