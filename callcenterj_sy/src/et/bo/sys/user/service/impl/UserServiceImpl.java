/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     沈阳卓越科技有限公司</p>
 */
package et.bo.sys.user.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.RowSet;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.servlet.StaticServlet;
import et.bo.sys.group.service.GroupService;
import et.bo.sys.role.service.RoleService;
import et.bo.sys.user.action.Password_encrypt;
import et.bo.sys.user.service.UserService;
import et.po.StaffBasic;
import et.po.SysDepartment;
import et.po.SysGroup;
import et.po.SysRole;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class UserServiceImpl implements UserService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private GroupService groupService = null;

	private RoleService roleService = null;
	
	private static List<IBaseDTO> l = new ArrayList<IBaseDTO>();
	
	int Dnum = 0;
	int Rnum = 0;
	int Gnum = 0;
	/**
	 * @return Returns the dao.
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            The dao to set.
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks
	 *            The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	


	public int listUserSize(IBaseDTO dto) {
		// TODO 需要写出方法的具体实现
		UserHelp uh = new UserHelp();
		MyQuery mq = uh.ListUserMQ(dto);
		int i = dao.findEntitySize(mq);
		return i;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 需要写出方法的具体实现

	}

	/**
	 * @return Returns the groupService.
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * @param groupService
	 *            The groupService to set.
	 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * @return Returns the roleService.
	 */
	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * @param roleService
	 *            The roleService to set.
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	
	
	public IBaseDTO getUserInfo(String id)
	{
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, id);
		
		 IBaseDTO dto = new DynaBeanDTO();

		 
		 
		 dto.set("userId", su.getUserId());
		 dto.set("id", su.getUserId());
		 
			if(su.getSysGroup()!=null)
			{
				dto.set("groupName",su.getSysGroup().getName());
			}
			if(su.getSysRole()!=null)
			{
				dto.set("roleName",su.getSysRole().getName());
			}
		 
		
		 try
		 {
			 dto.set("sysRole", su.getSysRole().getId());
			 dto.set("sysGroup", su.getSysGroup().getId());
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 dto.set("skillGroup", su.getSkill());
		 
		 Password_encrypt pe = new Password_encrypt();
	        dto.set("password", pe.pw_encrypt(su.getPassword()
					.toString()));
	        dto.set("repassword", pe.pw_encrypt(su.getPassword()
					.toString()));
		 	 
		 
		 if(su.getSysDepartment()!=null)
		 {
			 dto.set("departmentId", su.getSysDepartment().getId());
		 }
		 
		
		 
		 dto.set("userName", su.getUserName());
		 dto.set("isFreeze", su.getIsFreeze());
	
		 dto.set("auditing", su.getAuditing());
		 
		 return dto;
	}
	
	
	public void addUser(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createUser(dto));
	}

	
	private SysUser createUser(IBaseDTO dto) {
		SysUser su = new SysUser();

		
		su.setUserId(dto.get("userId").toString());
		su.setUserName(dto.get("userName").toString());
		
		
		SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, dto.get("sysGroup").toString());
		SysRole sr = (SysRole)dao.loadEntity(SysRole.class, dto.get("sysRole").toString());
		SysDepartment sd = (SysDepartment)dao.loadEntity(SysDepartment.class, dto.get("departmentId").toString());
		
		
	
		
		su.setSysGroup(sg);
		su.setSysRole(sr);
		
		
		if(sd!=null)
		{
			su.setSysDepartment(sd);
		}
		su.setStaffSign("0");
		su.setSkill(dto.get("skillGroup").toString());
		su.setPassword(dto.get("password").toString());
		su.setSysDepartment(sd);
		su.setIsFreeze("0");
		
		su.setStaffSign("0");
		su.setCreateTime(new java.util.Date());
		su.setAuditing((String)dto.get("auditing"));
		return su;
	}
	
	
	 public void delUser(String id) {
		 // TODO Auto-generated method stub
		 SysUser su = (SysUser)dao.loadEntity(SysUser.class, id);
			 dao.removeEntity(su);
		 }
	 
	 
	 public boolean userUpdate(IBaseDTO dto) {
		 // TODO Auto-generated method stub
		 dao.saveEntity(modifyuser(dto));
		 addPowerInStaticMap();
		 return false;
		 }
			
	private SysUser modifyuser(IBaseDTO dto){
			SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
			su.setUserName(dto.get("userName").toString());
			su.setUserId(dto.get("userId").toString());
			su.setStaffSign("0");
			su.setPassword(dto.get("password").toString());
			su.setIsFreeze(dto.get("isFreeze").toString());
			SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, dto.get("sysGroup").toString());
			SysRole sr = (SysRole)dao.loadEntity(SysRole.class, dto.get("sysRole").toString());
			
			
			
			SysDepartment sd = (SysDepartment)dao.loadEntity(SysDepartment.class, dto.get("departmentId").toString());
			su.setSkill(dto.get("skillGroup").toString());
			su.setSysGroup(sg);
			su.setSysRole(sr);
			
			if(sd!=null)
			{
				su.setSysDepartment(sd);
			}
			
			su.setAuditing((String)dto.get("auditing"));
		 return su;
		 }
	
	
	public List<IBaseDTO> userList(IBaseDTO dto, PageInfo pi) {
		// TODO 需要写出方法的具体实现
		UserHelp uh = new UserHelp();
		Object[] o = dao.findEntity(uh.listUserMQ(dto, pi));
		ArrayList l = new ArrayList();
		if (null != o && o.length > 0) {		
			for (int i = 0, size = o.length; i < size; i++) {
				SysUser su = (SysUser) o[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				
				
				dbd.set("id",su.getUserId());
				dbd.set("userName",su.getUserName());
				
				if(su.getSysGroup()!=null)
				{
					dbd.set("groupName",su.getSysGroup().getName());
				}
				if(su.getSysRole()!=null)
				{
					dbd.set("roleName",su.getSysRole().getName());
				}
				
				
				
				if(su.getIsFreeze()!=null)
				{
					if(su.getIsFreeze().equals("0"))
					{
						dbd.set("isFreeze","正常");
					}
					else
					{
						dbd.set("isFreeze","冻结");
					}
				}
				l.add(dbd);
			}
			return l;
		}
		return l;
	}
	
	
	
	public List<LabelValueBean> getUsers(String src) {
		// TODO Auto-generated method stub
		List<LabelValueBean> ul2 = new ArrayList<LabelValueBean>();
		StringTokenizer st = new StringTokenizer(src, ",");
		// List<String> users=new ArrayList<String>();
		while (st.hasMoreElements()) {
			String s = (String) st.nextElement();
			// users.add(s);
			SysUser su = (SysUser) dao.loadEntity(SysUser.class, s);
			if (su != null) {
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(su.getUserName());
				lvb.setValue(su.getUserId());
				ul2.add(lvb);
			}
		}

		return ul2;
	}
	
	
	
	public boolean addUserAll(IBaseDTO dto,String str)
	{
		// TODO Auto-generated method stub

		int i = 0;
		boolean bool = false;
		String pingyin = null;//名字的拼音
		String py = null;//名字的拼音
		String name  = null;//名字汉字
		String[] username = str.split(",");
		
		for(i=0; i<username.length; i++)
		{
			
			
			py = username[i];
			
			
			String hql = "select su from SysUser su where su.userId = '"+py+"'";
			MyQuery mq = new MyQueryImpl();
			
			mq.setHql(hql);
			Object[] result = dao.findEntity(mq);
			
			if(result.length>0)
			{
				bool = true;
			}
			
		}
		
		if(bool==true)
		{
			bool = true;
		}
		else if(bool==false)
		{
		
			for(i=0; i<username.length; i++)
			{
				pingyin = username[i];
				
				String hql = "select sb from StaffBasic sb where sb.staffId = sb.staffId and sb.BStaffNickname='"+pingyin+"'";
				MyQuery mq = new MyQueryImpl();
				
				mq.setHql(hql);
				Object[] o = dao.findEntity(mq);
				
				for(int j=0; j<o.length; j++)
				{
					StaffBasic sb = (StaffBasic) o[j];
					name = sb.getBStaffName();
					
				}
	
				dao.saveEntity(createUserALL(dto,pingyin,name));
			}
		}
		
		return bool;
	}
	
	private SysUser createUserALL(IBaseDTO dto,String pingyin,String name)
	{
		SysUser su = new SysUser();

		Password_encrypt pe = new Password_encrypt();
	    String Password = pe.pw_encrypt("123456");
		
		
		su.setUserId(pingyin);

		su.setUserName(name);
		su.setStaffSign("0");
		su.setPassword(Password);
		su.setIsFreeze("0");
		su.setStaffSign("1");
		
		return su;
	}
	
	public List getGroupList()
	{
		List list = new ArrayList();

		String id = null;
		String value = null;
		
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysGroup.class);
		dc.add(Restrictions.eq("delMark","N"));
		mq.setDetachedCriteria(dc);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0, size = result.length; i < size; i++) {
			SysGroup sg = (SysGroup) result[i];
			
			id = sg.getId();
			value = sg.getName();

			list.add(new LabelValueBean(value,id));
			
		}
		return list;
	}

	public List getRoleList()
	{
		List list = new ArrayList();

		String id = null;
		String value = null;
		
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysRole.class);
		
		mq.setDetachedCriteria(dc);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0, size = result.length; i < size; i++) {
			SysRole sg = (SysRole) result[i];
			String isDelete = sg.getDeleteMark()==null?"":sg.getDeleteMark().trim();
			if("Y".equals(isDelete)==false)
			{
				id = sg.getId();
				value = sg.getName();
				list.add(new LabelValueBean(value,id));
			}
			
		}
		return list;
		
	}

	public List<IBaseDTO> getUserList() {
		// TODO Auto-generated method stub
		List<IBaseDTO> l = getL();
		if (l.isEmpty()) {
			MyQuery mq=new MyQueryImpl();
			DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
			mq.setDetachedCriteria(dc);
			Object[] result = (Object[]) dao.findEntity(mq);
			for (int i = 0, size = result.length; i < size; i++) {
				SysUser su = (SysUser)result[i];
				String sgName = su.getSysGroup()==null?"":su.getSysGroup().getName();
				if (sgName.equals("operator")||sgName.equals("opseating")) {
					IBaseDTO dto = new DynaBeanDTO();
					dto.set("worknum", su.getUserId());
					dto.set("workname", su.getUserName());
					String sgImpower = "";
					if (sgName.equals("operator")) {
						sgImpower = "座席员";
					}
					if (sgName.equals("opseating")) {
						sgImpower = "座席长";
					}
					dto.set("impower", sgImpower);
					dto.set("state", "0");
					l.add(dto);
				}
			}
		}
		return l;
	}

	public List<IBaseDTO> getL() {
		return l;
	}
	
	//将所有具有权限的人员及列表的信息放到内存中
	public void addPowerInStaticMap() {
		// TODO Auto-generated method stub
		StaticServlet.userPowerMap.put("普通案例库",powerUserList("普通案例库"));
		StaticServlet.userPowerMap.put("焦点案例库",powerUserList("焦点案例库"));
		StaticServlet.userPowerMap.put("会诊案例库",powerUserList("会诊案例库"));
		StaticServlet.userPowerMap.put("效果案例库",powerUserList("效果案例库"));
		StaticServlet.userPowerMap.put("农产品供求库",powerUserList("农产品供求库"));
		StaticServlet.userPowerMap.put("农产品价格库",powerUserList("农产品价格库"));
		StaticServlet.userPowerMap.put("焦点追踪库一审",powerUserList("焦点追踪库一审"));
		StaticServlet.userPowerMap.put("焦点追踪库二审",powerUserList("焦点追踪库二审"));
		StaticServlet.userPowerMap.put("焦点追踪库三审",powerUserList("焦点追踪库三审"));
		StaticServlet.userPowerMap.put("市场分析库一审",powerUserList("市场分析库一审"));
		StaticServlet.userPowerMap.put("市场分析库二审",powerUserList("市场分析库二审"));
		StaticServlet.userPowerMap.put("市场分析库三审",powerUserList("市场分析库三审"));
		StaticServlet.userPowerMap.put("企业信息库",powerUserList("企业信息库"));
		StaticServlet.userPowerMap.put("普通医疗服务信息库",powerUserList("普通医疗服务信息库"));
		StaticServlet.userPowerMap.put("预约医疗服务信息库",powerUserList("预约医疗服务信息库"));
		StaticServlet.userPowerMap.put("调查问卷设计库",powerUserList("调查问卷设计库"));
		StaticServlet.userPowerMap.put("调查信息分析库",powerUserList("调查信息分析库"));
	}
	
	//权限人员的列表的信息
	private List<String> powerUserList(String powerName){
		List<String> l = new ArrayList<String>();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.like("auditing","%"+powerName+"%"));
		mq.setDetachedCriteria(dc);
		Object[] result = (Object[]) dao.findEntity(mq);
		for (int i = 0, size = result.length; i < size; i++) {
			SysUser sysuser = (SysUser) result[i];
			if(!sysuser.getSysGroup().getName().equals("administrator")){
				l.add(sysuser.getUserId());
			}
		}
		return l;
	}
	
}
