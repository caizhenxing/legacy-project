/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.pdf.webdoc;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * Pdf�t�@�C�����쐬���邽�߂̐ݒ�����擾����N���X�B
 * ID RCSfile="$RCSfile: IodSettings.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */
public class IodSettings {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O�N���X�B */
	private static final Log log = LogFactory.getLog(IodSettings.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** pdf�t�@�C���ݒ��� */
	private List contents = new ArrayList();

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	public IodSettings(Reader reader) throws SystemException {
		// Construct a digester to use for parsing
		Digester digester = new Digester();
		digester.push(this);
		digester.setValidating(false);

		digester.addObjectCreate("contents/page", PageInfo.class);
		digester.addSetNext("contents/page", "addPageInfo");
		digester.addSetProperties("contents/page");
		digester.addObjectCreate("contents/page/field", FieldInfo.class);
		digester.addSetNext("contents/page/field", "addFieldInfo");
		digester.addSetProperties("contents/page/field");

		// Parse the input stream to initialize our database
		try {
			digester.parse(reader);
		} catch (IOException e) {
			throw new SystemException("(�ݒ���t�@�C��):�Ǎ��Ɏ��s���܂����B", e);
		} catch (SAXException e) {
			throw new SystemException("(�ݒ���t�@�C��):��͂Ɏ��s���܂����B", e);
		}
	}
	
	/**
	 * �ŏ���ǉ�����B
	 * @param info	�ŏ����I�u�W�F�N�g
	 */
	public void addPageInfo(PageInfo pageInfo) {
		contents.add(pageInfo);
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public List getContents() {
		return contents;
	}
}
