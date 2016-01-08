/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 29, 20094:52:10 PM
 * ������base.zyf.hibernate
 * �ļ�����ReformCodeWrapperCallbackHandler.java
 * �����ߣ���һ��
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import base.zyf.common.tree.classtree.ClassTreeService;
import base.zyf.hibernate.usertype.CodeWrapperType.CodeWrapperCallbackHanler;
import base.zyf.spring.SpringRunningContainer;



/**
 * This is a spi class
 * @since 2006-9-20
 * @author java2enterprise
 * @version 1.0
 */
public class ReformCodeWrapperCallbackHandler implements CodeWrapperCallbackHanler  {

	
	
	public ReformCodeWrapperCallbackHandler() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void afterNullSafeGet(CodeWrapper codeWrapperLoaded)
		throws Throwable {
			
		codeWrapperLoaded.setName(service().getLabelById(codeWrapperLoaded.getCode()));
	}
	static ClassTreeService service() {
		return (ClassTreeService) SpringRunningContainer.getInstance().getBean(ClassTreeService.SERVICE_NAME);
	}
}
