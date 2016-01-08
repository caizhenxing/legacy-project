package et.bo.oa.message.addrList.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.message.addrList.service.AddrListServiceI;
import et.po.EmployeeInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p> 通讯录实现 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-11
 * 
 */
public class AddrListServiceImpl implements AddrListServiceI {

	private Log logger = LogFactory.getLog(AddrListServiceImpl.class);
	
	private BaseDAO dao = null;
	
	private int sizeNum = 0;
	
	public AddrListServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 获得通讯录集合 实现 </p>
	 * @param departId：部门Id
	 * @param pageInfo：分页信息类实例
	 * @return：通讯录集合
	 */
	public Object[] getAddrList(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		Object[] employeeObject = new Object[0];
		try {
			SreachService sreachService = new SreachService();

			Object[] objs = this.dao.findEntity(sreachService.getAddrListMyQuery(dto, pageInfo));
			sizeNum = this.dao.findEntitySize(sreachService.getAddrListMyQuery(dto, pageInfo));

			if (null != objs && 0 < objs.length) {
				employeeObject = new Object[objs.length];
				for (int i = 0; i < objs.length; i++) {
					EmployeeInfo Object = (EmployeeInfo) objs[i];
					employeeObject[i] = createEmployee(Object);
				}
			}else{
				
				employeeObject = new Object[0];
			}
		} catch (Exception e) {
			logger.debug(e);
			
			e.printStackTrace();
		}
		return employeeObject;
	}

	/**
	 * <p> PO to DTO </p>
	 * @param employeeInfo
	 * @return
	 */
	private DynaBeanDTO createEmployee(EmployeeInfo employeeInfo){
		DynaBeanDTO beanDTO = new DynaBeanDTO();
		beanDTO.set("id",employeeInfo.getId());
		beanDTO.set("name",employeeInfo.getName());
		beanDTO.set("mobile",employeeInfo.getMobile());
		beanDTO.set("homePhone",employeeInfo.getHomePhone());
		beanDTO.set("companyPhone",employeeInfo.getCompanyPhone());
		beanDTO.set("otherPhone",employeeInfo.getOtherPhone());
		beanDTO.set("ownEmail",employeeInfo.getOwnEmail());
		beanDTO.set("otherEmail",employeeInfo.getOtherEmail());
		beanDTO.set("homeAddr",employeeInfo.getHomeAddr());
		beanDTO.set("companyAddr",employeeInfo.getCompanyAddr());
		beanDTO.set("postCode",employeeInfo.getPostCode());
		beanDTO.set("homePost",employeeInfo.getHomePost());
		beanDTO.set("department",employeeInfo.getDepartment());
		beanDTO.set("station",employeeInfo.getStation());
		return beanDTO;
	}
	
	/**
	 * <p> 获得记录数 </p>
	 * @return：查询记录数
	 */
	public int AddrListSize() {
		// TODO Auto-generated method stub
		return sizeNum;
	}

	/**
	 * <p> 获得通讯录详细信息 实现 </p>
	 * @param employeeId：员工Id
	 * @return：详细信息集合
	 */
	public Object[] getAddrInfo(String employeeId) {
		// TODO Auto-generated method stub
		Object[] employeeObject = new Object[1];
		
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
			dc.add(Restrictions.eq("id",employeeId));
			
			MyQuery myQuery = new MyQueryImpl();
			myQuery.setDetachedCriteria(dc);
			Object[] objs = this.dao.findEntity(myQuery);
			
			if (null != objs && 0 < objs.length) {
				EmployeeInfo Object = (EmployeeInfo) objs[0];
				employeeObject[0] = createEmployee(Object);
			}
		} catch (Exception e) {
			logger.debug(e);
			
			e.printStackTrace();
		}
		return employeeObject;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
}
