package et.bo.callcenter.plant.dongjin_c161a.test;

public interface IInitDongjin {

	//***************************************
	//初始化设置测试
	public abstract void init() throws Exception;

	public abstract void exit();
	
	public void getInitInfo();
	//*******************************
	
	//*************************************
	//连通测试
	public void connect(int i,int j);
	
	public void link(int i,int j);
	
	public void linkthree(int i,int j,int k);
	//**********************************
	
	//********************************
	
	public void ring(int i);//内线振铃
//	********************************
	public void trunkRingAndHook();//外线打入，文件放音，
	public void playIndex();//索引放音
//	********************************
	//录音，
	public void recordAndPlay(int i);//
	
//	********************************
	public void receiveAndSendDtmf(int i,int to);//dtmf 
	
//	********************************
	public void checkSig(int i);//外线打电话
//	********************************
	public void inline(int i);//内线
	
//	********************************
	public void receiveFsk(int i);//fsk收号,由于交换机限制，无法测试fsk
	
//	********************************
	public void changewavFile();
	
//	********************************
	public void recordAndPlayMemory(int i);//不好用，内存录音第二次出现问题。
	
	public void memoryPlay(int i);//不好用
	
//	********************************
	public void conf();//会议
}