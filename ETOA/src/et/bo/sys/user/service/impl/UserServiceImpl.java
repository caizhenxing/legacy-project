/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.sys.user.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.struts.util.LabelValueBean;

import et.bo.sys.group.service.GroupService;
import et.bo.sys.role.service.RoleService;
import et.bo.sys.user.service.UserService;
import et.po.SysDepartment;
import et.po.SysGroup;
import et.po.SysRole;
import et.po.SysUser;
import et.po.SysUserInfo;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class UserServiceImpl implements UserService {

private BaseDAO dao=null;
	
	private KeyService ks = null;
	
	private GroupService groupService =null;
	
	private RoleService roleService =null;
	/**
	 * @return Returns the dao.
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao The dao to set.
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	private SysUser createPoByDTO(IBaseDTO dto)
	{
		SysUser su =new SysUser();
		su.setUserId(null !=dto.get("userId") && !"".equals("userId")? dto.get("userId").toString():"");
		SysRole sr =new SysRole();
		sr.setId(dto.get("sysRole").toString());
		su.setSysRole(sr);
		SysGroup sg =new SysGroup();
		sg.setId(dto.get("sysGroup").toString());		
		su.setSysGroup(sg);
		su.setPassword(null !=dto.get("password") && !"".equals("password")? dto.get("password").toString():"");
		SysDepartment sd =new SysDepartment();
		if(null !=dto.get("departmentId") && !"".equals("departmentId"))
			sd.setId(dto.get("departmentId").toString());
		su.setSysDepartment(sd);
		su.setUserName(null !=dto.get("userName") && !"".equals("userName")? dto.get("userName").toString():"");
		su.setDeleteMark(null !=dto.get("freezeMark") && !"".equals("freezeMark")? dto.get("freezeMark").toString():"");
		su.setRemark(null !=dto.get("remark") && !"".equals("remark")? dto.get("remark").toString():"");
		su.setIsSys("0");
		return su;
	}
	
	private SysUser modifyPoByDTO(IBaseDTO dto)
	{
		SysUser su =(SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
//		su.setUserId(null !=dto.get("userId") && !"".equals("userId")? dto.get("userId").toString():"");
		SysRole sr =new SysRole();
		sr.setId(dto.get("sysRole").toString());
		su.setSysRole(sr);
		SysGroup sg =new SysGroup();
		sg.setId(dto.get("sysGroup").toString());		
		su.setSysGroup(sg);
//		System.out.println(dto.get("password").toString());
//		su.setPassword(null !=dto.get("password") && !"".equals("password")? dto.get("password").toString():"");
		SysDepartment sd =new SysDepartment();
		if(null !=dto.get("departmentId") && !"".equals("departmentId"))
			sd.setId(dto.get("departmentId").toString());
		su.setSysDepartment(sd);
		su.setUserName(null !=dto.get("userName") && !"".equals("userName")? dto.get("userName").toString():"");
		su.setDeleteMark(null !=dto.get("freezeMark") && !"".equals("freezeMark")? dto.get("freezeMark").toString():"");
		su.setRemark(null !=dto.get("remark") && !"".equals("remark")? dto.get("remark").toString():"");
//		su.setIsSys("0");
		return su;
	}
	
	private SysUserInfo createSUIByDTO(IBaseDTO dto)
	{
		SysUserInfo sui =new SysUserInfo();
		sui.setUserId(null !=dto.get("userId") && !"".equals("userId")? dto.get("userId").toString():"");
		sui.setRealName(null !=dto.get("realName")?dto.get("realName").toString():"");
		sui.setSexId(null !=dto.get("sexId")?dto.get("sexId").toString():"1");
		sui.setIdentityKind(null !=dto.get("identityKind")?dto.get("identityKind").toString():"");
		sui.setIdentityCard(null !=dto.get("identityCard")?dto.get("identityCard").toString():"");
		TimeUtil a;
		sui.setBirthday(null !=dto.get("birthday") && !"".equals(dto.get("birthday").toString())?TimeUtil.getTimeByStr(dto.get("birthday").toString(),"yyyy-MM-dd"):new Date());
		sui.setCountryId(null !=dto.get("countryId")?dto.get("countryId").toString():"");
		sui.setProvinceId(null !=dto.get("provinceId")?dto.get("provinceId").toString():"");
		sui.setQq(null !=dto.get("qq")?dto.get("qq").toString():"");
		sui.setBloodType(null !=dto.get("bloodType")?dto.get("bloodType").toString():"");
		sui.setAddress(null !=dto.get("address")?dto.get("address").toString():"");
		sui.setPostalcode(null !=dto.get("postalcode")?dto.get("postalcode").toString():"");
		sui.setMobile(null !=dto.get("mobile")?dto.get("mobile").toString():"");
		sui.setFinishSchool(null !=dto.get("finishSchool")?dto.get("finishSchool").toString():"");
		sui.setSpeciality(null !=dto.get("speciality")?dto.get("speciality").toString():"");
		sui.setWorkId(null !=dto.get("workId")?dto.get("workId").toString():"");
		sui.setHomepage(null !=dto.get("homepage")?dto.get("homepage").toString():"");
		return sui;
	}
	
	private IBaseDTO createDTOByPo(SysUser su)
	{
		DynaBeanDTO dto =new DynaBeanDTO();
		dto.set("password",su.getPassword());
		dto.set("userId",su.getUserId());
		dto.set("sysRole",su.getSysRole().getId());
		dto.set("sysGroup",su.getSysGroup().getId());
		dto.set("userName",su.getUserName());
		dto.set("departmentId",su.getSysDepartment().getId());
		dto.set("freezeMark",su.getDeleteMark());
		dto.set("remark",su.getRemark());
		return dto;
	}
	
	private IBaseDTO createDTOByPo(SysUserInfo sui, IBaseDTO dto)
	{
		if(null !=sui)
		{	
			dto.set("realName",sui.getRealName());
			dto.set("sexId",sui.getSexId());
			dto.set("identityKind",sui.getIdentityKind());
			dto.set("identityCard",sui.getIdentityCard());
			dto.set("birthday",null !=sui.getBirthday()?TimeUtil.getTheTimeStr(sui.getBirthday(),"yyyy-MM-dd"):"0000-00-00");
			dto.set("countryId",sui.getCountryId());
			dto.set("provinceId",sui.getProvinceId());
			dto.set("qq",sui.getQq());
			dto.set("bloodType",sui.getBloodType());
			dto.set("address",sui.getAddress());
			dto.set("postalcode",sui.getPostalcode());
			dto.set("mobile",sui.getMobile());
			dto.set("finishSchool",sui.getFinishSchool());
			dto.set("speciality",sui.getSpeciality());
			dto.set("workId",sui.getWorkId());
			dto.set("homepage",sui.getHomepage());
		}		
		return dto;
	}
	
	private IBaseDTO createDTOByPo(SysUserInfo sui)
	{
		DynaBeanDTO dto =new DynaBeanDTO();
		return dto;
	}
	
	public boolean check(String userId, String password) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,userId);
		if(null !=su && password.equals(su.getPassword()))
		{
			return true;
		}
		return false;
	}
	/**
	 * 添加用户
	 * 用户标识：	2	-系统用户
	 * 			1	-一般用户
	 *			0	-冻结用户
	 *			-1	-删除用户
	 *			系统用户和删除用户不在查询中显示其信息
	 * @param dto
	 */
	public void insertUser(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)createPoByDTO(dto);
		SysUserInfo sui =(SysUserInfo)createSUIByDTO(dto);
		sui.setSysUser(su);
		dao.saveEntity(su);
		dao.saveEntity(sui);
	}

	public boolean exist(String userId) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,userId);
		if(null !=su)
		{
			return true;
		}
		return false;
	}

	public boolean deleteUser(String id) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,id);
		
		if(null !=su)
		{
			if(su.getIsSys().equals("1")){
				return false;
			}
			su.setDeleteMark("-1");
			return true;
		}
		return false;     
	}

	public void freezeUser(String id) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,id);
		if(null !=su)
		{
			su.setDeleteMark("0");
		}
	}

	public void thawUser(String id) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,id);
		if(null !=su)
		{
			su.setDeleteMark("1");
		}
	}

	public void updateUser(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)modifyPoByDTO(dto);
		SysUserInfo sui =(SysUserInfo)createSUIByDTO(dto);
//		sui.setSysUser(su);
		dao.saveEntity(su);
//		sui.setSysUser(su);
//		dao.updateEntity(sui);
		dao.saveEntity(sui);
	}

	public List<IBaseDTO> listUser(IBaseDTO dto, PageInfo pi) {
		//TODO 需要写出方法的具体实现
		UserHelp uh =new UserHelp();
		Object [] o =dao.findEntity(uh.listUserMQ(dto,pi));
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				SysUser su =((SysUser)oo);
				IBaseDTO tdto =createDTOByPo(su);
				
				IBaseDTO gdto =this.groupService.uniqueGroup(su.getSysGroup().getId());
				IBaseDTO rdto =this.roleService.getRoleInfo(su.getSysRole().getId());
				tdto.set("groupName",gdto.get("name"));
				tdto.set("roleName",rdto.get("name"));
				l.add(tdto);
			}
			return l;
		}
		return l;
	}

	public int listUserSize(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		UserHelp uh =new UserHelp();
		MyQuery mq =uh.ListUserMQ(dto);
		int i =dao.findEntitySize(mq);
		return i;
	}

	public IBaseDTO uniqueUser(String id) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,id);
		SysUserInfo sui =(SysUserInfo)dao.loadEntity(SysUserInfo.class,id);
		if(null !=su )
		{
			IBaseDTO dto =createDTOByPo(su);
			dto =createDTOByPo(sui,dto);
			return dto;
		}
		return null;
	}

	public Object uniqueUserPo(String id) {
		//TODO 需要写出方法的具体实现
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,id);
		if(null !=su)
		{
			return su;
		}
		return null;
	}
	
	public void updatePwd(IBaseDTO dto) {
		// TODO Auto-generated method stub
        dao.saveEntity(modifyPwd(dto));
	}
	
	private SysUser modifyPwd(IBaseDTO dto){
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("userId").toString());
		su.setPassword(dto.get("repassword").toString());
		return su;
	}
	
	public boolean judgementSameUer(IBaseDTO dto){
		SysUser su =(SysUser)dao.loadEntity(SysUser.class,dto.get("userId").toString());
		if(null!=su)
		{
			return true;
		}else{
		    return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

	/**
	 * @return Returns the groupService.
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * @param groupService The groupService to set.
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
	 * @param roleService The roleService to set.
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<LabelValueBean> getAll() {
		// TODO Auto-generated method stub
		UserHelp uh =new UserHelp();
		List<LabelValueBean> re=new ArrayList<LabelValueBean>();
		Object[] r=dao.findEntity(uh.all());
		for(int i=0,size=r.length;i<size;i++)
		{
			LabelValueBean lvb=new LabelValueBean();
			SysUser su=(SysUser)r[i];
			lvb.setLabel(su.getUserName());
			lvb.setValue(su.getUserId());
			re.add(lvb);
		}
		return re;
	}
	public List<LabelValueBean> getUsers(String src) {
		// TODO Auto-generated method stub
		List<LabelValueBean> ul2=new ArrayList<LabelValueBean>();
		StringTokenizer st=new StringTokenizer(src,",");
		//List<String> users=new ArrayList<String>();
		while(st.hasMoreElements())
		{
			String s=(String)st.nextElement();
			//users.add(s);
			SysUser su=(SysUser)dao.loadEntity(SysUser.class,s);
			if(su!=null)
			{
				LabelValueBean lvb=new LabelValueBean();
				lvb.setLabel(su.getUserName());
				lvb.setValue(su.getUserId());
				ul2.add(lvb);
			}
		}
		
		return ul2;
	}
	public List getLabelValue(String tablename, String key, String value, String fk)
	{
		List list =new ArrayList();
		MyQuery mq =new MyQueryImpl();
		String hql ="select "+key+","+value+" from "+tablename;				
		mq.setHql(hql);
		Object [] temp =this.dao.findEntity(mq);
		if(!"-1".equals(fk))
		{
			LabelValueBean lv =new LabelValueBean(fk,null);
			list.add(lv);
		}
		if(null !=temp && temp.length >0)
		{
			for(int i=0;i<temp.length;i++)
			{				
				Object[] kv =(Object [])temp[i];
				String k =kv[0]!=null?kv[0].toString():"";
				String v =kv[1]!=null?kv[1].toString():"";
				LabelValueBean lv =new LabelValueBean(k,v);
				list.add(lv);

			}
		}	
//		
		return list;
	}
	
	public List getLabelValue(String tablename, String key, String value, String flag, String fvalue)
	{
		List list =new ArrayList();
		MyQuery mq =new MyQueryImpl();
		String hql ="select "+key+","+value+" from "+tablename +" where "+flag +" = "+"'"+fvalue+"'";
		mq.setHql(hql);
		Object [] temp =this.dao.findEntity(mq);		
		if(null !=temp && temp.length >0)
		{
			for(int i=0;i<temp.length;i++)
			{				
				Object[] kv =(Object [])temp[i];
				String k =kv[0]!=null?kv[0].toString():"";
				String v =kv[1]!=null?kv[1].toString():"";
				LabelValueBean lv =new LabelValueBean(k,v);
				list.add(lv);
//				
			}
		}	
//		
		return list;
	}
}
