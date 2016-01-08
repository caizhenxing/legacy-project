package et.bo.schema.fixedContact.upload.service.impl;

import java.io.File;

public class CheckUploadServiceImpl implements CheckUploadService
{
	public void renameFile(String path, String oldname, String newname)
	{
		if (!oldname.equals(newname))
		{
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			oldfile.renameTo(newfile);
		}
		// System.out.println("�ļ��������������");
	}

	/**
   * ����ϴ��ļ��ĺ�׺���Ƿ�Ϸ�
   */
	public int checkFileSuffix(String fileName)
	{
		/**
     * ��ֹ�ϴ����ļ�����
     */
		String[] errorType =
		{
				".exe", ".com", ".cgi", ".jsp"
		};
		/**
     * Ӧ���ϴ����ļ���׺��".jpg", ".gif", ".pnf", ".bmp"
     */
		String[] okSuffix =
		{
				".jpg", ".gif", ".pnf", ".bmp"
		};
		for(int temp = 0; temp < errorType.length; temp++)
		{
			//System.out.println("�ļ��ĺ�׺��1�ǣ�" + fileName);
			/**
       * ����ļ����ĺ�׺Ϊ".exe", ".com",
       * ".cgi",".jsp"4��֮һ���׳��쳣��
       */
			if (fileName.equalsIgnoreCase(errorType[temp]))// ����ϴ����ļ����ĺ�׺�Ǵ���ĺ�׺��
			{
				//System.out.println("a=" + fileName.equalsIgnoreCase(errorType[temp]));
				return 1;
			}
		}
		return 0;
	}

	// public boolean checkUploadVideo(String
	// videoPath)
	// {
	// String uploadVideoPath =
	// Constants.getProperty("fixedContact_pic_realpath");//
	// �����ϴ���ͼƬ�ڷ����������ϵ�Ŀ��λ��
	// String filePath = "";// �ϴ�ͼƬ�ļ��ľ���·��
	//
	// if (videoPath != null &&
	// !"".equals(videoPath))// ������ݿ�ȷʵ�������ϴ�ͼƬ��·��
	// {
	// filePath = uploadVideoPath + videoPath;
	// String[] arry = filePath.split("/");
	// String fileName = arry[arry.length - 1];//
	// �ϴ�ͼƬ���ļ���
	// filePath = uploadVideoPath + "/" +
	// fileName;// �ϴ�ͼƬ�ļ��ľ���·��
	// try
	// {
	// java.io.File myFilePath = new
	// java.io.File(filePath);
	// if (myFilePath.exists() == true) //
	// ����ϴ�ͼƬ�ļ��ľ���·��ȷʵ����
	// {
	// return true;
	// }
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// return false;
	// }
}
