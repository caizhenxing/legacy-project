package et.bo.callcenter.plant.dongjin_c161a.test;

public interface IInitDongjin {

	//***************************************
	//��ʼ�����ò���
	public abstract void init() throws Exception;

	public abstract void exit();
	
	public void getInitInfo();
	//*******************************
	
	//*************************************
	//��ͨ����
	public void connect(int i,int j);
	
	public void link(int i,int j);
	
	public void linkthree(int i,int j,int k);
	//**********************************
	
	//********************************
	
	public void ring(int i);//��������
//	********************************
	public void trunkRingAndHook();//���ߴ��룬�ļ�������
	public void playIndex();//��������
//	********************************
	//¼����
	public void recordAndPlay(int i);//
	
//	********************************
	public void receiveAndSendDtmf(int i,int to);//dtmf 
	
//	********************************
	public void checkSig(int i);//���ߴ�绰
//	********************************
	public void inline(int i);//����
	
//	********************************
	public void receiveFsk(int i);//fsk�պ�,���ڽ��������ƣ��޷�����fsk
	
//	********************************
	public void changewavFile();
	
//	********************************
	public void recordAndPlayMemory(int i);//�����ã��ڴ�¼���ڶ��γ������⡣
	
	public void memoryPlay(int i);//������
	
//	********************************
	public void conf();//����
}