/**
 * 
 * ����ʱ�䣺Oct 29, 20079:55:21 AM
 * �ļ�����PoemBeana.java
 * �����ߣ�zhaoyf
 * 
 */
package base.zyf.tools;

/**
 * @author zhaoyf
 *
 */
import java.util.ArrayList;
import java.util.List;

public class PoemBeana {

    private List lines = new ArrayList();
    
    public List getLines() {
        return lines;
    }
    
    public void addLine(String line) {
        lines.add(line);
    }
}

