package excellence.framework.base.query;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface MyQuery {

	static Logger log = Logger.getLogger(MyQuery.class);
	
	/**
	 * 设置是否有排序功能
	 * @param order
	 */
	void setOrder(Order order);
	
	Order getOrder();

	/**
	 * 得到dto内存放的值的信息
	 * 
	 * @return IBaseDTO
	 */
	public abstract IBaseDTO getDto();

	/**
	 * 向dto内设置值的信息
	 * 
	 * @param dto
	 */
	public abstract void setDto(IBaseDTO dto);

	/**
	 * 得到第一条记录
	 * 
	 * @return
	 */
	public abstract int getFirst();

	/**
	 * 设置第几条记录为第一条
	 * 
	 * @param first
	 *            设置起始条数
	 */
	public abstract void setFirst(int first);

	/**
	 * 
	 * @return
	 */
	public abstract int getFetch();

	public abstract void setFetch(int fetch);

	public abstract DetachedCriteria getDetachedCriteria();

	/**
	 * 得到hql语句
	 * 
	 * @return
	 */
	public abstract String getHql();

	/**
	 * 得到sql语句
	 * 
	 * @return
	 */
	public abstract String getSql();

	/**
	 * 
	 * @return
	 */
	public abstract Class getType();

	public abstract void setType(Class type);

	public abstract String getIdentifer();

	public abstract void setIdentifer(String identifer);

	public abstract void setDetachedCriteria(DetachedCriteria dc);

	public abstract void setHql(String hql);

	public abstract void setSql(String sql);

	public abstract void setParameter(int idx, Object para);

	public abstract void setParameter(String pname, Object para);

	public abstract Map getParameterM();

	public abstract List getParameterL();

	public abstract void setPageInfo(PageInfo pi);

	/**
	 * 设置排序顺序(正序)
	 * 
	 * @param asc
	 */
	public abstract void setAsc(String asc);

	/**
	 * 设置排序顺序(倒序)
	 * 
	 * @param desc
	 */
	public abstract void setDesc(String desc);

	/**
	 * 得到排序顺序(正序)
	 * 
	 * @return
	 */
	public abstract String getAsc();

	/**
	 * 得到排序顺序(倒序)
	 * 
	 * @return
	 */
	public abstract String getDesc();
}