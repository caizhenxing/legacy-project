package com.zyf.web.support;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
/**
 * ����ת��ҳ��<code>String</code>���͵Ĳ���ֵ
 * @author scott
 * @since 2006-4-5
 * @version $Id: StringConverter.java,v 1.1 2007/11/05 03:16:26 yushn Exp $
 *
 */
public class StringConverter implements Converter {
//    private Log logger = LogFactory.getLog(StringConverter.class);
    
    public Object convert(Class type, Object value) {
        String ret = null;
        if (value == null) {
            return (String) null;
        } else if (value instanceof String) {
            ret = (String) value;
            /*
            try {
                ret = new String(((String) value).getBytes(null));
            } catch (UnsupportedEncodingException e) {
                ret = (String) value;
                if (logger.isInfoEnabled()) {
                    logger.info("ת�������������,����ֵ["
                        + value
                        + "]", e);
                }
            }
            */
        } else {
            ret = value.toString();
        }
        /* ������ڵ�����'\'�ͱ������������'\\',����ҳ��ͳ����е����� */
        if (ret.indexOf('\\') > -1) {
            boolean single = false;
            char[] c = new char[ret.length() * 2];
            int cursor = 0;
            for (int i = 0; i < ret.length(); i++) {
                c[cursor++] = ret.charAt(i);
                if (ret.charAt(i) == '\\') {
                    single = !single;
                } else if (single) {
                    c[cursor++] = '\\';
                    single = !single;
                }
            }
            
            if (single) {
                c[cursor++] = '\\';
            }
            
            ret = new String(c, 0, cursor);
        }
        
        if (type.equals(String[].class)) {
            return StringUtils.split(ret, ',');
        }

        return ret;
    }
}
