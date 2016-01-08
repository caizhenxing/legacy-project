package et.bo.callcenter.plant.dongjin_c161a.test;

public interface IAcceptMessage {

	public abstract void accept();

	public abstract char numToDtmf(int ch);

	public abstract void getVoicePath();

}