/**
 * 	@(#)IDongjinRecordChangeImpl.java   2006-12-22 下午03:54:46
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinRecordChange {
	/**
	 * 
	 * 语音格式转换函数
	 * DJCVT.dll
	 * 
	 */
	
	/**
	 * 把A-law PCm文件转换为wave文件
	 * @param PcmFileName A-law PCm文件
	 * @param WaveFileName 转换后文件
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	public abstract int pcmtoWave(String pcmFileName,String waveFileName);
	
	/**
	 * 把wave文件 转换为A-law PCm文件
	 * @param PcmFileName A-law PCm文件
	 * @param WaveFileName 转换后文件
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	public abstract int wavetoPcm(String waveFileName,String pcmFileName);
	
	/**
	 * 把4位8K采样的ADPCM文件 转换为A-law PCm文件
	 * @param PcmFileName A-law PCm文件
	 * @param AdpcmFileName 4位8K采样的ADPCM文件 
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	public abstract int adtoPcm(String adpcmFileName,String pcmFileName);
	
	/**
	 * 把A-law PCm文件 转换为4位8K采样的ADPCM文件
	 * @param PcmFileName A-law PCm文件
	 * @param AdpcmFileName 4位8K采样的ADPCM文件 
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	public abstract int pcmtoAd(String pcmFileName,String adpcmFileName);
	
	/**
	 * 把4位6K采样的Dialogic文件 转换为A-law PCm文件
	 * @param PcmFileName A-law PCm文件
	 * @param AdpcmFileName 4位6K采样的Dialogic文件
	 * @version 2006-11-22
	 * @return -1 打开源文件错误
	 * @return -2 打开目标文件错误
	 * @return 1 成功
	 */
	public abstract int ad6ktoPcm(String adpcmFileName,String pcmFileName);
	
}
