/*
 * <p>Title:       �򵥵ı���</p>
 * <p>Description: ����ϸ��˵��</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.common;


public interface SequenceService {
    
    //�õ���һ��������ֵ
    public long getNext(String keyName, int poolSize);
    
}
