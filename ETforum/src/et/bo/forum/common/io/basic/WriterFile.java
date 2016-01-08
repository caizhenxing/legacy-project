package et.bo.forum.common.io.basic;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: My com.guxf function</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: forecast</p>
 * @author ¹¼Ð¡·å
 * @version 1.0
 */

public class WriterFile extends BufferedWriter {

    public WriterFile(String fileName)
            throws java.io.IOException{
        super(new FileWriter(fileName));
    }
    public WriterFile(String fileName,boolean append)
            throws java.io.IOException{
        super(new FileWriter(fileName,append));
    }
    public WriterFile(File file)
            throws java.io.IOException{
        super(new FileWriter(file.getPath()));
    }
    public WriterFile(File file,boolean append)
            throws java.io.IOException{
        super(new FileWriter(file.getPath(),append));
    }
    public static void main(String[] args) {
        //WriterFile writerFile1 = new WriterFile();
    }
}