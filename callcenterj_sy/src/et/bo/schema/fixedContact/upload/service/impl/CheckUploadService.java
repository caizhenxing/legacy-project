package et.bo.schema.fixedContact.upload.service.impl;

public interface CheckUploadService
{
	/**
   * 判断上传文件是否存在于服务器的实际物理磁盘上
   * @param videoPath
   * 上传的视频文件在服务器的实际物理磁盘上的路径名
   * @return true, if check upload video
   */
	// public boolean checkUploadVideo(String
	// videoPath);
	/**
   * 文件重命名
   * @param path
   * @param oldname
   * @param newname
   */
	public void renameFile(String path, String oldname, String newname);

	/**
   * 检查上传的文件名后缀是否合法:
   * @param fileName
   * @return 0：合法；1：不合法(原因是后缀名为".exe", ".com",
   * ".cgi",
   * ".jsp")；2：不合法(原因是后缀名不为‘jpg’或‘gif’或‘pnf’)
   */
	public int checkFileSuffix(String fileName);
}
