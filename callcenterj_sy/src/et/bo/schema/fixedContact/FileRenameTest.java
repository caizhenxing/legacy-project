package et.bo.schema.fixedContact;

import java.io.File;

public class FileRenameTest
{
	public void renameFile(String path, String oldname, String newname)
	{
		System.out.println("##################################");
		System.out.println("���ļ�����������·���ǣ�" + path);
		System.out.println("���ļ����ǣ�" + oldname);
		System.out.println("���ļ����ǣ�" + newname);
		// �µ��ļ�������ǰ�ļ�����ͬʱ,���б�Ҫ����������
		if (!oldname.equals(newname))
		{
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists()) // ���ڸ�Ŀ¼���Ѿ���һ���ļ������ļ�����ͬ��������������
			System.out.println(newname + "�Ѿ����ڣ�");
			else
			{
				oldfile.renameTo(newfile);
			}
		}
		System.out.println("�ļ��������������");
	}

	public static void main(String[] args)
	{
		File myFile = new File(
				"E:/myPrograme/Tomcate5/Tomcat5.5/webapps/callcenterj_sy/schema/fixedContact/user_images/aaa.bmp");
		myFile
				.renameTo(new File(
						"E:/myPrograme/Tomcate5/Tomcat5.5/webapps/callcenterj_sy/schema/fixedContact/user_images/2008-6-11_18_27_48.bmp"));
//		String path = "E:/myPrograme/Tomcate5/Tomcat 5.5/webapps/callcenterj_sy/schema/fixedContact/user_images";
//		String oldname = "���¼����.bmp";
//		String newname = "2008-6-11_18:27:48.bmp";
//		new FileRenameTest().renameFile(path, oldname, newname);
		System.out.println("ok");
	}
}
