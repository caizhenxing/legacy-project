package excellence.common.key;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */

 
public interface SequenceService {

	/**
	 * 得到id数值
	 * @param
	 * @version 2007-1-15
	 * @return
	 */
	public long getNext(String key,int poolSize);
}
