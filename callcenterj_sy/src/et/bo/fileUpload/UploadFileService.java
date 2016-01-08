package et.bo.fileUpload;

public interface UploadFileService {
	String addFile(String oldName, String filePath, String newName, String type);
}
