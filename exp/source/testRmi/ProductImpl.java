/*
 * 创建日期 2005-1-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testRmi;

/**
 * @author 赵一非
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.rmi.*;
import java.rmi.server.*;
public class ProductImpl extends UnicastRemoteObject
implements Test
{

	private String name;
	public ProductImpl(String s)throws RemoteException
	{
		name=s;
	}
	public static void main(String[] args) {
	}
	/* （非 Javadoc）
	 * @see testRmi.Test#getD()
	 */
	public String getD() throws RemoteException {
		// TODO 自动生成方法存根
		return "class is " +this.getClass().getName();
	}
}
