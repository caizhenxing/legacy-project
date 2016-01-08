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
		// System.out.println("文件重命名处理完毕");
	}

	/**
   * 检查上传文件的后缀名是否合法
   */
	public int checkFileSuffix(String fileName)
	{
		/**
     * 禁止上传的文件类型
     */
		String[] errorType =
		{
				".exe", ".com", ".cgi", ".jsp"
		};
		/**
     * 应该上传的文件后缀名".jpg", ".gif", ".pnf", ".bmp"
     */
		String[] okSuffix =
		{
				".jpg", ".gif", ".pnf", ".bmp"
		};
		for(int temp = 0; temp < errorType.length; temp++)
		{
			//System.out.println("文件的后缀名1是：" + fileName);
			/**
       * 如果文件名的后缀为".exe", ".com",
       * ".cgi",".jsp"4者之一则抛出异常，
       */
			if (fileName.equalsIgnoreCase(errorType[temp]))// 如果上传的文件名的后缀是错误的后缀名
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
	// 保存上传的图片在服务器磁盘上的目标位置
	// String filePath = "";// 上传图片文件的绝对路径
	//
	// if (videoPath != null &&
	// !"".equals(videoPath))// 如果数据库确实保存了上传图片的路径
	// {
	// filePath = uploadVideoPath + videoPath;
	// String[] arry = filePath.split("/");
	// String fileName = arry[arry.length - 1];//
	// 上传图片的文件名
	// filePath = uploadVideoPath + "/" +
	// fileName;// 上传图片文件的绝对路径
	// try
	// {
	// java.io.File myFilePath = new
	// java.io.File(filePath);
	// if (myFilePath.exists() == true) //
	// 如果上传图片文件的绝对路径确实存在
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
