package et.bo.callcenter.connection.voicemail;

import java.util.List;

public interface ManagerVoiceMailI {

	public abstract void destroy();

	public abstract List<String> getVoiceList(String line);
	public abstract int getNewVoice(String line);
	public abstract void subVoiceSize(String line);

	public abstract void addVoice(String line, String voice);

	public abstract void delVoice(String line, String voice);

	public String getClewVoice();
	public boolean hasNewVoice(String line);
}