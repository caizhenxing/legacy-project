package et.bo.forum.common.io.basic;

/**
 * <p>Title: </p>
 * <p>Description: My com.guxf function</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author ¹¼Ð¡·å
 * @version 1.0
 */

import java.io.*;

public class InFile extends DataInputStream {
    public InFile(String filename)
            throws FileNotFoundException {
        super(
                new BufferedInputStream(
                new FileInputStream(filename)));
    }
    public InFile(File file)
            throws FileNotFoundException {
        this(file.getPath());
    }
} ///:~