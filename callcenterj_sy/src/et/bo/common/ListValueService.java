package et.bo.common;

import java.util.List;

public interface ListValueService {

	/**
	 * 
	 * @param tablename
	 * @param key
	 * @param value
	 * @return
	 */
	public List getLabelValue(String tablename, String key, String value, String fk);
	/**
	 * 
	 * @param tablename
	 * @param key
	 * @param value
	 * @param flag
	 * @param fvalue
	 * @return
	 */
	public List getLabelValue(String tablename, String key, String value, String flag, String fvalue);
	
	public boolean checkDup(Class c, String f, String value);
	
}
