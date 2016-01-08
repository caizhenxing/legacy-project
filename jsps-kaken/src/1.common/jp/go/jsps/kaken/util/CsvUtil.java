package jp.go.jsps.kaken.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;

/**
 * CSV�t�@�C���𐶐�����N���X�B
 *
 * ID RCSfile="$RCSfile: CsvUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class CsvUtil {

	//---------------------------------------------------------------------
	// Static Methods
	//---------------------------------------------------------------------	
	/**
	 * List�̕�����z��v�f��CSV�t�@�C���֏o�͂���B
	 *
	 * @param list CSV�ɏo�͂��郊�X�g
	 * @param filepath �t�@�C���o�͐�p�X
	 * @param filename �t�@�C�����̐ړ����B
	 * @return �������ʁBtrue�̏ꍇ�͐����B
     * @throws ApplicationException
	 */
	public static boolean output(List list, String filepath, String filename)
            throws ApplicationException {

		return outputCSV(list, filepath, filename);	
	}

	/**
	 * List�̕�����z��v�f��CSV�t�@�C���֏o�͂���B
	 * ���ݐR���˗��ʒm���o�͐�p��ԁi�t�@�C�������Œ�̂��߁j
	 * @param list CSV�ɏo�͂��郊�X�g
     * @param filepath �t�@�C���p�X
	 * @param filename �t�@�C����
	 * @return �������ʁBtrue�̏ꍇ�͐����B
     * @throws FileIOException
	 */
	public static boolean outputCSV(List list, String filepath, String filename)
            throws FileIOException {

		ServletOutputStream so = null;
				
		try{	
			//�o�̓t�H���_�̍쐬
			//TODO�@�b��o�͐�
			File file = new File(filepath);

			//�t�H���_�������ꍇ�͍쐬����(�z���͕K���쐬����)
			if(!file.exists()){
				file.mkdirs();
			}
			String file_path = file.getPath();

			//�t�@�C���o��
			PrintWriter writer = new PrintWriter(
                                 new BufferedWriter(
                                 new FileWriter(file_path + "/" + filename + ".csv")));
			
			//CSV�f�[�^�𐶐�����
			for(int i = 0;i < list.size(); i++){
				List line = (List)list.get(i);
				Iterator iterator = line.iterator();
				String row ="";

				//1�s����CSV�f�[�^���쐬����
				CSVLine csvline = new CSVLine();

				//1�s���̃f�[�^���ڐ����肩����			
				while(iterator.hasNext()){
					String col = (String)iterator.next();
					csvline.addItem(col);
				}

				//1�s�����t�@�C���֏�������
				writer.println(csvline.getLine());
			}

			writer.close();

		}catch(IOException e){
			throw new FileIOException(
				"�t�@�C���̓��o�͒��ɃG���[���������܂����B", 
				e);
		}
					
		return true;
	}
}