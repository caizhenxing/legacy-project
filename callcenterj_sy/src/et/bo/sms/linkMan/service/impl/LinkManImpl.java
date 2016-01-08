package et.bo.sms.linkMan.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sms.linkMan.service.LinkManService;
import et.po.LinkGroup;
import et.po.LinkMan;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class LinkManImpl implements LinkManService {
	
	private KeyService ks = null;
	
	private BaseDAO dao = null;
	
	private ClassTreeService depTree=null;
	
	private int num = 0;

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public boolean addLinkMan(IBaseDTO dto) {
		boolean flag = false;
		
		try {
			LinkMan lg = new LinkMan();
			
			lg.setId(ks.getNext("LinkMan"));
			lg.setName(dto.get("name").toString());
			lg.setMobile(dto.get("mobile").toString());
			lg.setBranch(dto.get("branch").toString());
			lg.setEmail(dto.get("email").toString());
			lg.setPhonenum(dto.get("phonenum").toString());
			lg.setIdcard(dto.get("idcard").toString());
			lg.setSex(dto.get("sex").toString());
			
			String groupId = dto.get("groupId").toString();
			if(!"".equals(groupId)){
				lg.setLinkGroup(linkGroupQuery(groupId));
			}
			dao.saveEntity(lg);
			flag = true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	private LinkGroup linkGroupQuery(String id) {
		LinkGroup lg = new LinkGroup();
		LinkManHelp lmh = new LinkManHelp();
		Object[] o = (Object[])dao.findEntity(lmh.linkGroupQueryById(id));
		if(o != null && o.length > 0)
			lg = (LinkGroup)o[0];
		
		return lg;
	}

	public boolean delLinkMan(String[] selectIt) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delLinkManForever(String[] selectIt) {
		// TODO Auto-generated method stub
		return false;
	}

	public IBaseDTO getLinkManInfo(String id) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		LinkManHelp lgh = new LinkManHelp();
		Object[] result = (Object[]) dao.findEntity(lgh.linkManQueryById(id));
		if(result != null && result.length > 0){
			LinkMan lg = (LinkMan)result[0];
			dbd = linkManToDynaBeanDTO2(lg);
		}
		return dbd;
	}

	public int getLinkManSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List linkManQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		LinkManHelp lgh = new LinkManHelp();
		Object[] result = (Object[]) dao.findEntity(lgh.linkManQuery(dto, pi));
		num = dao.findEntitySize(lgh.linkManQuery(dto, pi));
		if(result != null && result.length > 0) {
			for (int i = 0, size = result.length; i < size; i++) {
				LinkMan lg = (LinkMan) result[i];			
				list.add(linkManToDynaBeanDTO(lg));
			}
		}
		return list;
	}
	
	private DynaBeanDTO linkManToDynaBeanDTO(LinkMan lg) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		
		dbd.set("id", lg.getId());
		dbd.set("name", lg.getName());
		
		String groupId = lg.getLinkGroup().getId();
		if(!"".equals(groupId)){
			LinkGroup l = linkGroupQuery(groupId);
			if(l != null)
				dbd.set("groupId", l.getGroupName());
			else
				dbd.set("groupId", "");
		}else
			dbd.set("groupId", "");
		dbd.set("mobile", lg.getMobile());
		dbd.set("phonenum", lg.getPhonenum());
		dbd.set("sex", lg.getSex());
		dbd.set("idcard", lg.getIdcard());
		
		String branch = lg.getBranch();
		if(!"".equals(branch)){
//			dbd.set("branch", depTree.getvaluebyId(branch));
		}else
			dbd.set("branch", "");
		dbd.set("email", lg.getEmail());
		return dbd;
	}
	
	private DynaBeanDTO linkManToDynaBeanDTO2(LinkMan lg) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		
		dbd.set("id", lg.getId());
		dbd.set("name", lg.getName());
		dbd.set("groupId", lg.getLinkGroup().getId());
		dbd.set("mobile", lg.getMobile());
		dbd.set("phonenum", lg.getPhonenum());
		dbd.set("sex", lg.getSex());
		dbd.set("idcard", lg.getIdcard());
		dbd.set("branch", lg.getBranch());
		dbd.set("email", lg.getEmail());
		return dbd;
	}

	public boolean updateLinkMan(IBaseDTO dto, String id) {
		boolean flag = false;
		try {
//			System.out.println("id is "+id);
			LinkMan lg = (LinkMan)dao.loadEntity(LinkMan.class, id);
			lg.setName(dto.get("name").toString());
			lg.setMobile(dto.get("mobile").toString());
			lg.setBranch(dto.get("branch").toString());
			lg.setEmail(dto.get("email").toString());
			lg.setPhonenum(dto.get("phonenum").toString());
			lg.setIdcard(dto.get("idcard").toString());
			lg.setSex(dto.get("sex").toString());
			
			String groupId = dto.get("groupId").toString();
			if(!"".equals(groupId)){
				lg.setLinkGroup(linkGroupQuery(groupId));
			}
			
			dao.updateEntity(lg);
			flag = true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	public boolean delLinkMan(String id) {
		boolean flag = false;
		try {
			System.out.println("id is "+id);
			LinkMan lg = (LinkMan)dao.loadEntity(LinkMan.class, id);
			
			dao.removeEntity(lg);
			flag = true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	public List getLinkGroupList() {
		List groupList = new ArrayList();
		LinkManHelp lgh = new LinkManHelp();
		
		Object[] result = dao.findEntity(lgh.linkGroupQuery());
		if(result != null && result.length > 0){
			for(int i=0, size=result.length; i<size; i++){
				LinkGroup lg = (LinkGroup)result[i];
				LabelValueBean lvb = new LabelValueBean();
				lvb.setValue(lg.getId());
				lvb.setLabel(lg.getGroupName());
				groupList.add(lvb);
			}
		}
		
		return groupList;
	}

}
