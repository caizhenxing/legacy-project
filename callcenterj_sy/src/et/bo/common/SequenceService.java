/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     沈阳卓越科技有限公司</p>
 */
package et.bo.common;


public interface SequenceService {
    
    //得到下一个主健的值
    public long getNext(String keyName, int poolSize);
    
}
