package et.bo.linkman.service.impl;




import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class LinkmanHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public String  linkmanQuery(IBaseDTO dto, PageInfo pi){
		String begintime=dto.get("begintime").toString();
		String endtime=dto.get("endtime").toString();
		String dictQuestionType1=dto.get("dictQuestionType1").toString();
		String sql="select  c.cust_name as custName,c.cust_tel_mob as custTelMob,c.cust_tel_home as custTelHome,c.cust_tel_work as custTelWork,c.cust_addr as custAddr,"
		+" q.dict_question_type1 as dictQuestionType1,q.is_out as isOut,q.addtime as addtime"
		+" from oper_custinfo c left outer join oper_question q"
		+" on c.cust_id=q.cust_id"
		+  " where c.dict_cust_type='SYS_TREE_0000002108'";
		if(begintime!=null&&!begintime.equals("")){
			sql+=" and convert(varchar(10),q.addtime,120) >convert(varchar(10),'"+begintime+"',120)";
		}
		if(endtime!=null&&!endtime.equals("")){
			sql+=" and convert(varchar(10),q.addtime,120) <convert(varchar(10),'"+endtime+"',120)";
		}
		if(dictQuestionType1!=null&&!dictQuestionType1.equals("")){
			sql+=" and q.dict_question_type1='"+dictQuestionType1+"'";
			sql+=" order by c.cust_name asc";
		}else {
			sql+=" order by q.addtime desc, q.dict_question_type1 desc ";
		}
		return sql;       
	}   
}
