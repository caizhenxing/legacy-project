package et.bo.schema.fixedContact;

import java.io.File;

public class FileRenameTest
{
	public void renameFile(String path, String oldname, String newname)
	{
		System.out.println("##################################");
		System.out.println("旧文件的完整保存路径是：" + path);
		System.out.println("旧文件名是：" + oldname);
		System.out.println("新文件名是：" + newname);
		// 新的文件名和以前文件名不同时,才有必要进行重命名
		if (!oldname.equals(newname))
		{
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists()) // 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
			System.out.println(newname + "已经存在！");
			else
			{
				oldfile.renameTo(newfile);
			}
		}
		System.out.println("文件重命名处理完毕");
	}

	public static void main(String[] args)
	{
		File myFile = new File(
				"E:/myPrograme/Tomcate5/Tomcat5.5/webapps/callcenterj_sy/schema/fixedContact/user_images/aaa.bmp");
		myFile
				.renameTo(new File(
						"E:/myPrograme/Tomcate5/Tomcat5.5/webapps/callcenterj_sy/schema/fixedContact/user_images/2008-6-11_18_27_48.bmp"));
//		String path = "E:/myPrograme/Tomcate5/Tomcat 5.5/webapps/callcenterj_sy/schema/fixedContact/user_images";
//		String oldname = "按事件浏览.bmp";
//		String newname = "2008-6-11_18:27:48.bmp";
//		new FileRenameTest().renameFile(path, oldname, newname);
		System.out.println("ok");
	}
}
