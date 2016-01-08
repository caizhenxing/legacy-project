package et.bo.fileUpload;
import javax.servlet.http.HttpServletRequest;
import net.fckeditor.requestcycle.UserPathBuilder;

public class FCKUserPath implements UserPathBuilder{

	public String getUserFilesPath(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
        // TODO 自动生成方法存根
        String path = "/file/fckuploads/";
        return path;
	}

}
