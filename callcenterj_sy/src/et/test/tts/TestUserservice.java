/**
 * 
 */
package et.test.tts;

import java.util.Iterator;
import java.util.List;

import et.bo.sys.user.service.UserService;
import excellence.framework.base.container.SpringContainer;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author Administrator
 *
 */
public class TestUserservice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserService us = (UserService)SpringContainer.getInstance().getBean("UserService");
		List<IBaseDTO> dto = us.getUserList();
		Iterator it = dto.iterator();
		while(it.hasNext()){
			DynaBeanDTO db = (DynaBeanDTO)it.next();
			System.out.println(db.get("impower").toString());
		}
	}

}
