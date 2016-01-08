/**
 * 	@(#)IDongjinConf.java   2006-12-22 下午04:24:19
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinConf {

	/**
	 * 
	 * 
	 * 会议实现
	 * 
	 * 
	 * 
	 */
	/**
	 * 将一个通道加入会议
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 */
	public abstract int addChnl(int confNo,int channelNo,int chnlAtte,int noiseSupp);
	
	/**
	 * 将一个通道从会议中去掉
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @version 2006-12-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 根据ChannelNo找到资源非法
	 */
	public abstract int subChnl(int confNo,int channekNo);
	
	/**
	 * 加入一个通道到会议，只是听，不能说
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 */
	public abstract int addListenChnl(int confNo,int channelNo);
	
	/**
	 * 去掉一个听会议的通道
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @version 2006-12-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 根据ChannelNo找到资源非法
	 */
	public abstract int subListenChnk(int confNo,int channelNo);
	
	/**
	 * 初始化
	 * @param
	 * @version 2006-12-22
	 * @return 0 成功
	 * @return 1 不是D161A
	 * @return 2 在INI中，Connect必须是1
	 * @return 3 已经使用模拟会议卡，初始化成功
	 */
	public abstract int dConfEnableConfCard();
	
	/**
	 * 禁止会议功能
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	public abstract void dConfDisableConfCard();
	
	/**
	 * 将一个通道的录音转变为对会议录音
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 * @return 4 不是使用D161A内置会议功能
	 */
	public abstract int dConfAddRecListenChnl(int confNo,int channelNo);
	
	/**
	 * 去掉一个对会议的录音，回复对通道录音
	 * @param ConfNo 会议组号，1~10
	 * @param ChannelNo 通道号
	 * @param ChnlAtte 增益调整，-20db~20db
	 * @param NoiseSupp 保留
	 * @version 2006-11-22
	 * @return 0 成功
	 * @return 1 ConfNo越界
	 * @return 2 ChannelNo越界
	 * @return 3 没有可用资源
	 * @return 4 不是使用D161A内置会议功能
	 */
	public abstract int dConfSubRecListenChnl(int confNo,int channelNo);
	
	/**
	 * 得到当前可以用会议资源数。
	 * @param
	 * @version 2006-12-22
	 * @return
	 */
	public abstract int dConfGetResNumber();
	
}
