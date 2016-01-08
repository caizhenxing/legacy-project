package et.bo.sys.login.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import et.po.StaffBasic;
import et.po.WorkUnitInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class loginHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery staffBasicQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(StaffBasic.class);
		
		String BStaffName =(String)dto.get("BStaffName");
		String BStaffNickname = (String)dto.get("BStaffNickname");
		String BStaffNum = (String)dto.get("BStaffNum");
		String linkMobileNum = (String)dto.get("linkMobileNum");
		String linkHomeNum = (String)dto.get("linkHomeNum");
		String dictIsBeginwork = (String)dto.get("dictIsBeginwork");
		String BDictPaperType = (String)dto.get("BDictPaperType");
		String BPaperNum = (String)dto.get("BPaperNum");
		
		
		if(!BStaffName.equals("")){
			dc.add(Restrictions.like("BStaffName", BStaffName));
		}
		if(!BStaffNickname.equals("")){
			dc.add(Restrictions.like("BStaffNickname", BStaffNickname));
		}
		if(!BStaffNum.equals("")){
			dc.add(Restrictions.like("BStaffNum", BStaffNum));
		}
		if(!linkMobileNum.equals("")){
			dc.add(Restrictions.like("linkMobileNum", linkMobileNum));
		}
		if(!linkHomeNum.equals("")){
			dc.add(Restrictions.like("linkHomeNum", linkHomeNum));
		}
		if(!dictIsBeginwork.equals("")){
			dc.add(Restrictions.like("dictIsBeginwork", dictIsBeginwork));
		}
		if(!BDictPaperType.equals("")){
			dc.add(Restrictions.like("BDictPaperType", BDictPaperType));
		}
		if(!BPaperNum.equals("")){
			dc.add(Restrictions.like("BPaperNum", BPaperNum));
		}
	
		
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}
	
	public MyQuery getWorkUnitInfo(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(WorkUnitInfo.class);
	
		mq.setDetachedCriteria(dc);
		return mq; 
	}
	
}
