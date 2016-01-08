package et.bo.schema.fixedContact.upload.service.impl;

public interface CheckUploadService
{
	/**
   * �ж��ϴ��ļ��Ƿ�����ڷ�������ʵ�����������
   * @param videoPath
   * �ϴ�����Ƶ�ļ��ڷ�������ʵ����������ϵ�·����
   * @return true, if check upload video
   */
	// public boolean checkUploadVideo(String
	// videoPath);
	/**
   * �ļ�������
   * @param path
   * @param oldname
   * @param newname
   */
	public void renameFile(String path, String oldname, String newname);

	/**
   * ����ϴ����ļ�����׺�Ƿ�Ϸ�:
   * @param fileName
   * @return 0���Ϸ���1�����Ϸ�(ԭ���Ǻ�׺��Ϊ".exe", ".com",
   * ".cgi",
   * ".jsp")��2�����Ϸ�(ԭ���Ǻ�׺����Ϊ��jpg����gif����pnf��)
   */
	public int checkFileSuffix(String fileName);
}
