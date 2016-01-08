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
public class ReaderFile extends BufferedReader{

    public ReaderFile(String fileName)
            throws FileNotFoundException{
        super(new FileReader(fileName));
    }
    public ReaderFile(File file)
            throws FileNotFoundException{
        this(file.getPath());
    }
    public static void main(String[] args) {
        //ReaderFile rf = new ReaderFile();
    }
}