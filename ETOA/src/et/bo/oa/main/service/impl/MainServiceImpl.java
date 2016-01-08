package et.bo.oa.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.oa.communicate.email.service.impl.EmailSearch;
import et.bo.oa.main.service.MainSerivce;
import et.po.InemailInfo;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * 首页Service 实现
 * </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-15
 * 
 */
public class MainServiceImpl implements MainSerivce {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private int NUM_EMAIL = 0;

	public MainServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List newList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List waitingWork() {
		// TODO Auto-generated method stub
		return null;
	}

	public List afficheList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List workList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List inemailList(String username) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MainSearch mainSearch = new MainSearch();
		Object[] result = null;
		try {
			result = (Object[]) dao.findEntity(mainSearch
					.searchEmailInfo(username));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0, size = result.length; i < size; i++) {
			InemailInfo inemailInfo = (InemailInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", inemailInfo.getId());
			// dbd.set("takeUser",inemailInfo.getTakeUser());
			String title = inemailInfo.getEmailTitle();
			if (title.length() >= 15) {
				title = title.substring(0, 15) + "...";
			}
			dbd.set("emailTitle", title);
			dbd.set("time", inemailInfo.getCreateTime());
			l.add(dbd);
		}
		return l;
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

	public List emaliListIndex(IBaseDTO dto, PageInfo pi) {
		// TODO 需要写出方法的具体实现
		List l = new ArrayList();
		MainSearch mainSearch = new MainSearch();
		Object[] result = (Object[]) dao.findEntity(mainSearch
				.searchIndexEmailList(dto, pi));
		int s = dao.findEntitySize(mainSearch.searchIndexEmailList(dto, pi));
		NUM_EMAIL = s;
		for (int i = 0, size = result.length; i < size; i++) {
			InemailInfo inemailInfo = (InemailInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", inemailInfo.getId());
			dbd.set("takeUser", inemailInfo.getTakeUser());
			dbd.set("emailTitle", inemailInfo.getEmailTitle());
			dbd.set("time", inemailInfo.getCreateTime());
			l.add(dbd);
		}
		return l;
	}

	public int getEmailIndexSize() {
		// TODO Auto-generated method stub
		return NUM_EMAIL;
	}

	public List getEmailInfo(String id) {
		// TODO 需要写出方法的具体实现
		List l = new ArrayList();
		InemailInfo inemailInfo = (InemailInfo) dao.loadEntity(
				InemailInfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("sendUser", inemailInfo.getSendUser());
		dto.set("takeList", inemailInfo.getTakeList());
		dto.set("copyList", inemailInfo.getCopyList());
		dto.set("emailTitle", inemailInfo.getEmailTitle());
		dto.set("emailInfo", inemailInfo.getEmailInfo());
		dto.set("chk", inemailInfo.getSendType());
		l.add(dto);
		return l;
	}

}
