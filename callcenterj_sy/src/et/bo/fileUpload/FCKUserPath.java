package et.bo.fileUpload;
import javax.servlet.http.HttpServletRequest;
import net.fckeditor.requestcycle.UserPathBuilder;

public class FCKUserPath implements UserPathBuilder{

	public String getUserFilesPath(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
        // TODO �Զ����ɷ������
        String path = "/file/fckuploads/";
        return path;
	}

}
