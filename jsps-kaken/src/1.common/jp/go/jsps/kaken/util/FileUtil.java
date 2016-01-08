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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �t�@�C��������s���N���X�B
 * 
 * ID RCSfile="$RCSfile: FileUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class FileUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ���O
	 */
	private static Log log = LogFactory.getLog(FileUtil.class);
	
	/** �R���e���g�^�C�v�iPDF�j */
//	public static String CONTENT_TYPE_PDF = "application/pdf;charset=WINDOWS-31J";
	/** �R���e���g�^�C�v�iPDF�j */
	public static String CONTENT_TYPE_PDF = "application/pdf";
		
	/** �R���e���g�^�C�v�iPDF�j */
	public static String CONTENT_TYPE_MS_WORD = "application/ms-word";
	
	/** �g���q�iPDF�j */
	public static String EXTENSION_PDF = "pdf";
	
	/** �g���q�iWORD�j */
	public static String EXTENSION_MS_WORD = "doc";
	
	/** �f���~�^ */
	public static String DELIM = ".";	

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * FileUtil���쐬����B
	 */
	public FileUtil() {
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * �e�f�B���N�g���Ɋi�[����Ă���t�@�C���̈ꗗ��Ԃ��B
	 * �q�Ƀf�B���N�g�������݂���ꍇ�A���̎q�̃t�@�C�����擾����B
	 * @param dir	�f�B���N�g��
	 * @return �q�̃t�@�C�����i�p�X���j
	 */
	public static String[] getFileList(File parent) {
		if (parent == null) {
			return null;
		}
		String[] list = parent.list();
		if (list == null) {
			return null;
		}
		File file;
		Vector vec = new Vector();
		String[] grandchild;
		for (int i = 0; i < list.length; i++) {
			file = new File(parent, list[i]);
			if (file.isDirectory()) {
				grandchild = getFileList(file);
				if (grandchild == null || grandchild.length == 0) {
					continue;
				}
				for (int j = 0; j < grandchild.length; j++) {
					vec.addElement(
						(new File(list[i], grandchild[j])).getPath());
				}
			} else {
				vec.addElement(list[i]);
			}
		}
		// �x�N�^�[����t�@�C�����X�g�̍쐬
		list = new String[vec.size()];
		for (int i = 0; i < vec.size(); i++) {
			list[i] = (String) vec.get(i);
		}
		return list;
	}

	/**
	 * �t�@�C���ǂݍ��݃t�@�C�����\�[�X��Ԃ��B
	 * filePath�̈�����FileResource�̃p�X�ɐݒ肳��܂��B
	 * @param file	�ǂݍ��ރt�@�C��
	 * @return �t�@�C�����\�[�X
	 * @throws FileNotFoundException �t�@�C�������݂��Ȃ����A���ʂ̃t�@�C���ł͂Ȃ��f�B���N�g���ł��邩�A�܂��͂Ȃ�炩�̗��R�ŊJ�����Ƃ��ł��Ȃ��ꍇ
	 * @throws IOException ���o�̓G���[�����������ꍇ
	 */
	public static FileResource readFile(File file)
		throws FileNotFoundException, IOException {
		return readFile(file.getParentFile(), file.getName());
	}

	/**
	 * �t�@�C���ǂݍ��݃t�@�C�����\�[�X��Ԃ��B
	 * filePath�̈�����FileResource�̃p�X�ɐݒ肳��܂��B
	 * @param parent	�ǂݍ��ރt�@�C���̐e�f�B���N�g��
	 * @param filePath	�ǂݍ��ރt�@�C�����i�܂��͐e����̑��΃p�X�j
	 * @return �t�@�C�����\�[�X
	 * @throws FileNotFoundException �t�@�C�������݂��Ȃ����A���ʂ̃t�@�C���ł͂Ȃ��f�B���N�g���ł��邩�A�܂��͂Ȃ�炩�̗��R�ŊJ�����Ƃ��ł��Ȃ��ꍇ
	 * @throws IOException ���o�̓G���[�����������ꍇ
	 */
	public static FileResource readFile(File parent, String filePath)
		throws FileNotFoundException, IOException {
		File file = new File(parent, filePath);
		FileInputStream fis = new FileInputStream(file);
		byte[] binary = new byte[fis.available()];
		fis.read(binary);
		fis.close();
		FileResource fileRes = new FileResource();
		fileRes.setPath(filePath);
		fileRes.setBinary(binary);
		fileRes.setLastModified(file.lastModified());
		return fileRes;
	}

	/**
	 * �t�@�C���ǂݍ��݃t�@�C�����\�[�X�̔z���Ԃ��B
	 * filePath�̈�����FileResource�̃p�X�ɐݒ肷��B
	 * @param parent	�ǂݍ��ރt�@�C���̐e�f�B���N�g��
	 * @return �t�@�C�����\�[�X�̔z��
	 * @throws FileNotFoundException �t�@�C�������݂��Ȃ����A���ʂ̃t�@�C���ł͂Ȃ��f�B���N�g���ł��邩�A�܂��͂Ȃ�炩�̗��R�ŊJ�����Ƃ��ł��Ȃ��ꍇ
	 * @throws IOException ���o�̓G���[�����������ꍇ
	 */
	public static FileResource[] readFiles(File parent)
		throws FileNotFoundException, IOException {
		String[] list = getFileList(parent);
		FileResource[] fileRes = new FileResource[list.length];
		for (int i = 0; i < list.length; i++) {
			fileRes[i] = FileUtil.readFile(parent, list[i]);
		}
		return fileRes;
	}

	/**
	 * �t�@�C���Ƀo�C�g�̔z����������ށB
	 * @param parent �������ގq��fileRes�̐e�f�B���N�g��
	 * @param fileRes �������ރ��\�[�X
	 * @return ����ɏ������܂ꂽ�ꍇ�� true�A�����łȂ��ꍇ�� false
	 * @throws FileNotFoundException �t�@�C���͑��݂��邪�A���ʂ̃t�@�C���ł͂Ȃ��f�B���N�g���ł���ꍇ�A�t�@�C���͑��݂����쐬���ł��Ȃ��ꍇ�A�܂��͂Ȃ�炩�̗��R�ŊJ�����Ƃ��ł��Ȃ��ꍇ
	 * @throws IOException ���o�̓G���[�����������ꍇ
	 */
	public static boolean writeFile(File parent, FileResource[] fileRes)
		throws FileNotFoundException, IOException {
		String path;
		File file;
		if (fileRes == null) {
			return false;
		}
		for (int i = 0; i < fileRes.length; i++) {
			path = fileRes[i].getPath();
			if (path == null || path.length() == 0) {
				continue;
			}
			file = new File(parent, path);
			writeFile(file, fileRes[i].getBinary());
			if (fileRes[i].lastModified() > 0) {
				file.setLastModified(fileRes[i].lastModified());
			}
		}
		return true;
	}

	/**
	 * �t�@�C���Ƀo�C�g�̔z����������ށB
	 * @param parent �������ގq��fileRes�̐e�f�B���N�g��
	 * @param fileRes �������ރ��\�[�X
	 * @return ����ɏ������܂ꂽ�ꍇ�� true�A�����łȂ��ꍇ�� false
	 * @throws FileNotFoundException �t�@�C���͑��݂��邪�A���ʂ̃t�@�C���ł͂Ȃ��f�B���N�g���ł���ꍇ�A�t�@�C���͑��݂����쐬���ł��Ȃ��ꍇ�A�܂��͂Ȃ�炩�̗��R�ŊJ�����Ƃ��ł��Ȃ��ꍇ
	 * @throws IOException ���o�̓G���[�����������ꍇ
	 */
	public static boolean writeFile(File parent, FileResource fileRes)
		throws FileNotFoundException, IOException {
		String path;
		File file;
		if (fileRes == null) {
			return false;
		}
		path = fileRes.getPath();
		if (path == null || path.length() == 0) {
			return false;
		}
		file = new File(parent, path);
		writeFile(file, fileRes.getBinary());
		if (fileRes.lastModified() > 0) {
			file.setLastModified(fileRes.lastModified());
		}
		return true;
	}

	/**
	 * �t�@�C���Ƀo�C�g�̔z����������ށB
	 * @param file �t�@�C��
	 * @param binary �o�C�g�̔z��
	 * @return ����ɏ������܂ꂽ�ꍇ�� true�A�����łȂ��ꍇ�� false
	 * @throws FileNotFoundException �t�@�C���͑��݂��邪�A���ʂ̃t�@�C���ł͂Ȃ��f�B���N�g���ł���ꍇ�A�t�@�C���͑��݂����쐬���ł��Ȃ��ꍇ�A�܂��͂Ȃ�炩�̗��R�ŊJ�����Ƃ��ł��Ȃ��ꍇ
	 * @throws IOException ���o�̓G���[�����������ꍇ
	 */
	public static boolean writeFile(File file, byte[] binary)
		throws FileNotFoundException, IOException {

		File parent = file.getParentFile();
		if (parent != null) {
			parent.mkdirs();
		}
		if (binary == null) {
			return false;
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(binary);
		fos.close();
		return true;
	}

	/**
	 * �t�@�C���܂��̓f�B���N�g�����폜����B
	 * file���f�B���N�g���̏ꍇ���̃f�B���N�g�����̂��ׂĂ�
	 * �t�@�C���ƃf�B���N�g�����폜����B
	 * �폜�Ɏ��s�����ꍇ�ł��A�������̃t�@�C���i�܂��̓f�B���N�g���j��
	 * �폜�����ꍇ������܂��B
	 * @param file �t�@�C��or�f�B���N�g��
	 * @return �t�@�C���܂��̓f�B���N�g��������ɍ폜���ꂽ�ꍇ�� true�A�����łȂ��ꍇ�� false
	 */
	public static boolean delete(File file) {
		if (file == null) {
			return false;
		}
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			if (list != null && list.length > 0) {
				for (int i = 0; i < list.length; i++) {
					delete(list[i]);
				}
			}
		}
		return file.delete();
	}

	/**
	 * �f�B���N�g�����R�s�[����B
	 * @param fromDir �R�s�[��
	 * @param toDir �R�s�[��
	 * @return �f�B���N�g��������ɃR�s�[���ꂽ�ꍇ�� true�A�����łȂ��ꍇ�� false
	 */
	public static boolean directoryCopy(File fromDir, File toDir) {
		delete(toDir);
		toDir.mkdirs();
		String[] list = getFileList(fromDir);
		if (list == null) {
			return false;
		}
		FileResource res;
		for (int i = 0; i < list.length; i++) {
			try {
				res = readFile(fromDir, list[i]);
				writeFile(new File(toDir, list[i]), res.getBinary());
			} catch (Exception e) {
				if(log.isInfoEnabled()){
					log.info("�f�B���N�g���R�s�[�@fromDir='" + fromDir + "' toDir='" + toDir + "�Ɏ��s���܂����B", e);
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * �t�@�C�����R�s�[����B
	 * @param fromFile �R�s�[���t�@�C��
	 * @param toFile �R�s�[��t�@�C��
	 * @return �t�@�C��������ɃR�s�[���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean fileCopy(File fromFile, File toFile) {
		try {
			FileResource fileRes =
				readFile((File) null, fromFile.getAbsolutePath());
			writeFile(toFile, fileRes.getBinary());
			if (fileRes.lastModified() > 0) {
				toFile.setLastModified(fileRes.lastModified());
			}
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.info("�t�@�C���R�s�[�@fromFile='" + fromFile + "' toFile='" + toFile + "�Ɏ��s���܂����B", e);
			}
			return false;
		}
		return true;
	}

	/**
	 * �t�@�C�������ȉ𓀌`���ň��k����B
	 * @param fromFile ���k���t�@�C���p�X
	 * @param toFile ���k��t�@�C���p�X
	 * @param filename ���k�t�@�C�����i�g���q�����j
	 * @return �t�@�C��������Ɉ��k���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean fileCompress(String fromFilepath, String toFilepath, String filename) {
		try{
			//TODO�@�v���O�����̃p�X�͂ǂ����܂��傤�H
			//�w��t�@�C����lzh�Ɉ��k����R�}���h
			String[] command = {"Lha32",										//���s�v���O����
								"u",											//���k����
								"-a1",											//�I�v�V����
								"-n1",
								"-o2",
								"-jyo1",
				                "\"" + toFilepath + filename + ".lzh\"",		//���k�t�@�C���o�͐�
								"\"\"",											//��f�B���N�g��
								"\"" + fromFilepath + "*\""};					//���k�Ώۃt�@�C���i�t�H���_�j

			//�w��lzh�����ȉ𓀂ɕϊ�����R�}���h
			String[] command2 = {"Lha32",
								 "s",
								 "-gw2",
								 "-n1",
								 "-jyo1",
								 "\"" + toFilepath + filename + "\"",			//���k�t�@�C���̃p�X����уt�@�C�����i�g���q�͏����j
								 "\"" + toFilepath + "\""};						//���ȉ𓀃t�@�C���̏o�͐�i�t�@�C������lzh�t�@�C���Ɠ�����.EXE�ƂȂ�j

			try{
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();

				p = null;

				p = Runtime.getRuntime().exec(command2);
				p.waitFor();

			}catch(IOException e){
				if(log.isInfoEnabled()){
					log.info("�t�@�C�����k�@fromFilepath='" + fromFilepath + "' toFilepath='" + toFilepath + "' filename='" + filename + "'�@�Ɏ��s���܂����B", e);
				}
				return false;
			}

		}catch(InterruptedException e){
			if(log.isInfoEnabled()){
				log.info("�t�@�C�����k�@fromFilepath='" + fromFilepath + "' toFilepath='" + toFilepath + "' filename='" + filename + "'�@�Ɏ��s���܂����B", e);
			}
			return false;
		}
		return true;

	}

	/**
	 * �w�肳�ꂽ�R�}���h�����s������s���B
	 * @param command ���s����R�}���h
	 * @return ����ɃR�}���h�����s���ꂽ�ꍇ��true�A�����łȂ��ꍇ��false�B
	 */
	public static boolean execCommand(String command) {
		try{

			try{
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();

			}catch(IOException e){
				if(log.isInfoEnabled()){
					log.info("���s�R�}���h�@command='" + command + "'�@�����s���܂����B", e);
				}
				return false;
			}

		}catch(InterruptedException e){
			if(log.isInfoEnabled()){
				log.info("���s�R�}���h�@command='" + command + "'�@�����s���܂����B", e);
			}
			return false;
		}
		return true;

	}
	
	/**
	  * �t�@�C��������g���q�����o���B
	  * @param filename �t�@�C����
	  * @return �g���q
	  */
	public static String getExtention(String fileName) {
		int idx = fileName.lastIndexOf('.');
		if (idx!=-1) return (fileName.substring(idx+1, fileName.length())).toLowerCase();
		return "";
	}

	/**
	  * �t�@�C���p�X����t�@�C���������o���B�p�X�̋�؂蕶����'\'��'/'�ɒu������B
	  * @param filepath �t�@�C���p�X
	  * @return �t�@�C����
	  */
	public static String getFileName(String filepath) {
		String filename = null;
		if(filepath != null && filepath.length() != 0){
			//'\'��'/'�ɒu��
			filepath = filepath.replace('\\', '/');
			filename = new File(filepath).getName();
		}
		return filename;
	}

}
