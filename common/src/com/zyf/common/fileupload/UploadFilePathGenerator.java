/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.fileupload;

import com.power.vfs.FileObject;
import com.zyf.framework.persistent.Entity;

/**
 * �ϴ��ļ�·��������, ������{@link FileObject}���Ե�ҵ��ʵ����ϴ�ʱ��ʱ�����ɱ����ļ�
 * ��·��, ������һ��Ŀ¼����̫����ļ�, ����һ�������м����ȱ�ݵ�, ���һ��ȱ�ݵ�ʹ����
 * �ϴ��ļ�, ��ô�������һ��Ŀ¼���м�����ļ�
 * @author zhangli
 * @version $Id: UploadFilePathGenerator.java,v 1.1 2007/07/27 03:13:55 zhuxb Exp $
 * @since 2007-4-25
 */
public interface UploadFilePathGenerator {
    
    /** ����·���ĸ�Ŀ¼ */
    String FIRST_LEVEL_PATH = "entity";
    
    /**
     * ����ҵ��ʵ�����ɱ����ϴ��ļ���·��, ���ɵ�·��û��ǰ���ͺ�׺��"/"
     * @param entity Ҫ�����ϴ��ļ���ҵ��ʵ��
     * @return ���ɵı���{@link FileObject}��·����, �����·���¿��Ա������ҵ��ʵ���
     * ���е��ϴ��ļ�, ������<code>null</code>
     * @throws NullPointerException ���������<code>null</code>
     * @throws IllegalArgumentException ������ݲ����޷�����·����
     */
    String path(Entity entity);
    
}
