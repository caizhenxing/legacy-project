package et.bo.sys.ccIvrTreeVoiceDetail.service.impl;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class CcIvrTreeVDtlHelp extends MyQueryImpl {
	// /**
	// * @describe 取得端口对照表所有记录
	// */
	/*
	 * 排序查询的
	 */
	public MyQuery operCcIvrInfoQuery(IBaseDTO dto, PageInfo pi) {
		String voicePath = (String) dto.get("voicePath");
		String ivrtype = (String) dto.get("ivrtype");
		// System.out.println("*********: "+ivrtype);
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		sb.append("select vi from CcIvrVoiceinfo vi where 1 = 1 ");
		if (!ivrtype.equals("")) {
			sb.append(" and vi.ivrType like '%" + ivrtype + "%'");
		}
		if (!voicePath.equals("")) {
			sb.append(" and vi.path like '%" + voicePath + "%'");
		}
		// sb.append(" desc vi.id ");
		mq.setHql(sb.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * 统计总页数的
	 */
	public MyQuery operCcIvrInfoSizeQuery(IBaseDTO dto, PageInfo pi) {
		String voicePath = (String) dto.get("voicePath");
		String ivrtype = (String) dto.get("ivrtype");
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		sb.append("select vi from CcIvrVoiceinfo vi where 1 = 1 ");
		if (!ivrtype.equals("")) {
			sb.append(" and vi.ivrType like '%" + ivrtype + "%'");
		}
		if (!voicePath.equals("")) {
			sb.append(" and vi.path like '%" + voicePath + "%'");
		}
		// sb.append(" desc vi.id ");
		mq.setHql(sb.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		// MyQuery mq=new MyQueryImpl();
		// DetachedCriteria dc=DetachedCriteria.forClass(CcIvrVoiceinfo.class);
		//		
		// String id = (String)dto.get("id");
		// if(id!=null&&"".equals(id.trim())==false)
		// {
		// dc.add(Restrictions.like("id", id+"%"));
		// }
		// String voicePath = (String)dto.get("voicePath");
		// if(voicePath != null &&"".equals(voicePath.trim())==false)
		// {
		// dc.add(Restrictions.like("path", voicePath+"%"));
		// }
		// String treeId = (String)dto.get("treeId");
		//		
		//		
		// if(treeId != null &&"".equals(treeId.trim())==false )
		// {
		// dc.add(Restrictions.like("ccIvrTreeInfo.id", treeId));
		// }
		//		
		// dc.add(Restrictions.eq("isUse", "1"));
		// dc.addOrder(Order.desc("layerOrder"));
		// mq.setDetachedCriteria(dc);
		// mq.setFirst(pi.getBegin());
		//		
		//		
		// mq.setFetch(pi.getPageSize());
		return mq;
	}

}
