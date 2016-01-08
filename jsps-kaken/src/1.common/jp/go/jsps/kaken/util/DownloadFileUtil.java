/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

import org.apache.commons.logging.*;

/**
 * �t�@�C��������s���N���X�B
 * 
 * ID RCSfile="$RCSfile: DownloadFileUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class DownloadFileUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ���O
	 */
	private static Log log = LogFactory.getLog(DownloadFileUtil.class);
	
	/** �R���e���g�^�C�v�iPDF�j */
	public static final String CONTENT_TYPE_PDF      = "application/pdf";
		
	/** �R���e���g�^�C�v�iPDF�j */
	public static final String CONTENT_TYPE_MS_WORD  = "application/msword";
	
	/** �g���q�iPDF�j */
	private static final String EXTENSION_PDF        = ".pdf";
	
	/** �g���q�iWORD�j */
	private static final String EXTENSION_MS_WORD    = ".doc";
	
	/** �g���q�iCSV�j */
	private static final String EXTENSION_CSV        = ".csv";	
	
	/** �f���~�^ */
	private static final String DELIM                = "_";
	
	/** �g���qcontentType�Ή��e�[�u�� */
	private final static String contentTypeTable[][] = {
															{"doc", "application/msword"},
															{"pdf", "application/pdf"},
															{"txt", "text/plain"},
															{"xls", "application/vnd.ms-excel"},
															{"csv", "application/csv"},
															{"zip", "application/zip"}
														};
	private final static int EXTENTION=0;
	private final static int CONTENT_TYPE=1;

	

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * �t�@�C�����_�E�����[�h����B
	 * �w�肳�ꂽ�t�@�C�������[�J���f�B�X�N���擾���A���X�|���X�ɕԂ��B
	 * contentType�͊g���q�����Ɏ����I�ɔ��f����B
	 * @param response
	 * @param file �_�E�����[�h����t�@�C��
	 * @throws FileIOException
	 * @return �t�@�C��������Ƀ_�E�����[�h���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean downloadFile(
			HttpServletResponse response,
			File file) 
		throws FileIOException
	{
		//�g���q����contentType���l��
		String contentType = getContentType(file.getName());
		return downloadFile(response, file, contentType);
	}
	
	
	
	/**
	 * �t�@�C�����_�E�����[�h����B
	 * �w�肳�ꂽ�t�@�C�������[�J���f�B�X�N���擾���A���X�|���X�ɕԂ��B
	 * @param response
	 * @param file �_�E�����[�h����t�@�C��
	 * @param contentType �R���e���g�^�C�v
	 * @throws FileIOException
	 * @return �t�@�C��������Ƀ_�E�����[�h���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean downloadFile(
			HttpServletResponse response,
			File file,
			String contentType) 
		throws FileIOException
	{
		//�t�@�C�������݂��Ă��Ȃ��ꍇ
		if(file == null || !file.exists()){
			return false;
		}
		FileResource fileRes = null;
		try{
			fileRes = FileUtil.readFile(file);
		}catch(IOException e){
			throw new FileIOException(
				"�t�@�C���̓��͒��ɃG���[���������܂����B",
				e);
		}
		return downloadFile(response, fileRes, contentType);
	}
	
	
	
	/**
	 * �t�@�C�����_�E�����[�h����B
	 * �w�肳�ꂽ�t�@�C�����\�[�X�����X�|���X�ɕԂ��B
	 * contentType�͊g���q�����Ɏ����I�ɔ��f����B
	 * @param response
	 * @param fileRes �_�E�����[�h����t�@�C�����\�[�X
	 * @throws FileIOException 
	 * @return �t�@�C��������Ƀ_�E�����[�h���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean downloadFile(
			HttpServletResponse response, 
			FileResource fileRes) 
		throws FileIOException
	{
		//�g���q����contentType���l��
		String contentType = getContentType(fileRes.getName());
		return downloadFile(response, fileRes, contentType);		
	}
	
	
	
	/**
	 * �t�@�C�����_�E�����[�h����B
	 * �w�肳�ꂽ�t�@�C�����\�[�X�����X�|���X�ɕԂ��B
	 * @param response
	 * @param fileRes �_�E�����[�h����t�@�C�����\�[�X
	 * @param contentType �R���e���g�^�C�v
	 * @throws FileIOException 
	 * @return �t�@�C��������Ƀ_�E�����[�h���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean downloadFile(
			HttpServletResponse response, 
			FileResource fileRes, 
			String contentType) 
		throws FileIOException
	{
		ServletOutputStream so = null;
		InputStream input = null;
		
		//�t�@�C�����\�[�X�܂��̓t�@�C�����\�[�X�ɃZ�b�g����Ă���t�@�C���������݂��Ă��Ȃ��ꍇ
		if(fileRes == null || fileRes.getPath() == null){
			return false;
		}
		
		try{	
			//�R���e�L�X�g�Ƀ_�E�����[�h�t�@�C������ݒ肷��
			response.setContentType(contentType);
			String filename = fileRes.getPath();
			
			//�t�@�C�����ɑS�p�������܂܂�Ă����ꍇ�́A�f�t�H���g�̃t�@�C�������g�p�B
			if(!StringUtil.isHankaku(filename)){				
				filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "." + FileUtil.getExtention(filename); 
			}
			response.setContentLength(fileRes.getBinary().length);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);

			//�o�͗p�̃X�g���[���𐶐�����
			try{
				so = response.getOutputStream();
			}catch(IOException e){
				throw new FileIOException(
					"�t�@�C���o�͗p�̃X�g���[���̐����Ɏ��s���܂����B",
					new ErrorInfo("errors.3000"),
					e);
			}
			
			//�t�@�C������͂��Ȃ���o�̓X�g���[���ɗ���
			try{
				input = new BufferedInputStream(new ByteArrayInputStream(fileRes.getBinary()));				
				int i=0;
				while ((i = input.read()) != -1) {
					so.write(i);
				}		
			}catch(IOException e){
				throw new FileIOException(
					"�t�@�C���̓��o�͒��ɃG���[���������܂����B",
					e);
			}
			
		}finally{
			if(input != null){
				try{
					input.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(so != null){
				try{
					so.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}				
		return true;
	}

	/**
	  * �g���q����contentType�����o���B
	  * @param fileName �t�@�C����
	  * @return contentType
	  */
	public static String getContentType(String fileName) {
		String extention = FileUtil.getExtention(fileName);
		for (int j=0; j < contentTypeTable.length; j++) {
			if (contentTypeTable[j][EXTENTION].equalsIgnoreCase(extention)) {
			return contentTypeTable[j][CONTENT_TYPE];
			}
		}
		return "application/octet-stream";
	}

    /**
     * List�̕�����z��v�f��CSV�t�@�C���֏o�͂���B
     * �u"�v ���u ,�v ���܂�ł���ꍇ�ɃG���N�H�[�g����B
     * �t�@�C�����́A�ufilename_���ݓ��t.csv�v�Ƃ���B
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param list CSV�ɏo�͂��郊�X�g
     * @param filename �t�@�C����
     * @return �������ʁBtrue�̏ꍇ�͐����B
     * @throws FileIOException
     */
    public static boolean downloadCSV(
            HttpServletRequest request,
            HttpServletResponse response,
            List list, String filename)
            throws FileIOException {

        return downloadCSV(request, response, list, filename, true, true);
    }

	/**
	 * List�̕�����z��v�f��CSV�t�@�C���֏o�͂���B
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param list CSV�ɏo�͂��郊�X�g
	 * @param filename �t�@�C����
	 * @param suffixFlg �ڔ���t���O�i�ufilename_���ݓ��t.csv�v�Ƃ���ꍇ�́Atrue�Ƃ���j
	 * @param enquoteFlg �u"�v ���u ,�v ���܂�ł���ꍇ�ɃG���N�H�[�g����ꍇ��true�A�G���N�H�[�g���Ȃ��ꍇ��false�Ƃ���
	 * @return �������ʁBtrue�̏ꍇ�͐����B
     * @throws FileIOException
	 */
	public static boolean downloadCSV(
            HttpServletRequest request,
            HttpServletResponse response,
            List list,
            String filename,
            boolean suffixFlg,
            boolean enquoteFlg)
            throws FileIOException {
		
		//ServletOutputStream so = null;
		PrintWriter pr = null;
				
		try{	
			//�R���e�L�X�g�Ƀ_�E�����[�h�t�@�C������ݒ肷��
			response.setContentType("application/csv;charset=WINDOWS-31J");
			if(suffixFlg){
				response.setHeader("Content-disposition", "attachment;filename=" + getTempFileName(filename, true));
			}else{
				response.setHeader("Content-disposition", "attachment;filename=" + getTempFileName(filename, false));				
			}
			
			//�o�͗p�̃X�g���[���𐶐�����
			try{
				//so = response.getOutputStream();
				pr = response.getWriter();
			}catch(IOException e){
				throw new FileIOException(
						"�t�@�C���o�͗p�̃X�g���[���̐����Ɏ��s���܂����B", 
						e);
			}
			
			//CSV�f�[�^�𐶐�����
			for(int i = 0;i < list.size(); i++){
				List line = (List)list.get(i);
				Iterator iterator = line.iterator();
				//1�s���̃f�[�^���ڐ����肩����
				if(enquoteFlg){
					//�G���N�H�[�g����ꍇ
					//1�s����CSV�f�[�^���쐬����
					CSVLine csvline = new CSVLine();
					while(iterator.hasNext()){
						String col = (String)iterator.next();
						csvline.addItem(col);
					}
					
					//2005/08/11 takano PrintWriter�֕ύX
					//try{
					//	//1�s�����t�@�C���֏�������
					//	so.println(csvline.getLine());
					//}catch(IOException e){
					//	throw new FileIOException(
					//		"�t�@�C���̓��o�͒��ɃG���[���������܂����B", 
					//		e);
					//}
					
					//1�s�����t�@�C���֏�������
					pr.println(csvline.getLine());
					
				}else{
					//�G���N�H�[�g���Ȃ��ꍇ
					StringBuffer buffer = new StringBuffer();
					int count = 0;
					while(iterator.hasNext()){
						String col = (String)iterator.next();
						buffer.append(col);
						count++;
						if(line.size() != count){
							buffer.append(",");
						}			
					}
					
					//2005/08/11 takano PrintWriter�֕ύX
					//try{
					//	//1�s�����t�@�C���֏�������
					//	so.println(buffer.toString());
					//}catch(IOException e){
					//	throw new FileIOException(
					//		"�t�@�C���̓��o�͒��ɃG���[���������܂����B", 
					//		e);
					//}
					
					//1�s�����t�@�C���֏�������
					pr.println(buffer.toString());
					
				}
			}
		}finally{
			//2005/08/11 takano PrintWriter�֕ύX
			//if (so != null) {
			//	try{
			//		so.close();
			//	}catch(IOException e){
			//		e.printStackTrace();
			//	}
			//}
			
			//���\�[�X�N���[�Y
			if(pr != null){
				pr.close();
			}
			
		}
		return true;
	}

	/**
	 * �e���|�����[�t�@�C�������擾����B
	 * @param filename �t�@�C����
	 * @param suffixFlg �ڔ���t���O�i�ufilename_���ݓ��t.csv�v�Ƃ���ꍇ�́Atrue�j
	 * @return �e���|�����[�t�@�C����
	 */
	private static synchronized String getTempFileName(String filename, boolean suffixFlg) {
		StringBuffer sb = new StringBuffer(filename);
		if(suffixFlg){
			sb.append(DELIM);
//2006/11/06 �c�@�C����������            
//			sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            sb.append(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
//2006/11/06�@�c�@�C�������܂�            
		}
		sb.append(EXTENSION_CSV);
		return sb.toString();
	}
}