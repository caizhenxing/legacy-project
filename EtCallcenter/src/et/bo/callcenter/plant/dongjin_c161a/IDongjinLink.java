/**
 * 	@(#)IDongjinLink.java   2006-12-22 ����10:40:27
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinLink {

	/**
	 * 
	 * ��ͨ����
	 * 
	 */
	
	/**
	 * ��·��ͨ
	 * ��·ͨ��������ͨʱ�������ĳһͨ��������ֹͣ�����������ɵ�����ͨ�����ñ�����֮ǰ����ֹͣ������
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	public abstract int setLink(int wOne,int wAnother);
	
	/**
	 * ��·�������
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	public abstract int clearLink(int wOne,int wAnother);
	
	/**
	 * ��·������ͨ�����øú�����ʵ�ֵ�����ͨ��ͨ��one��������another��������another������one��������
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	public abstract int linkOneToAnother(int wOne,int wAnother);
	
	/**
	 * ���������ͨ
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 */
	public abstract int clearOneFromAnother(int wOne,int wAnother);
	
	/**
	 * ������ͨ���ɵ�����ͨʵ�֣����������Ƚ�С��
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @param wThree ��·
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 * @return -6 ��·������Χ
	 * @return -7 ��·���ڷ���
	 */
	public abstract int linkThree(int wOne,int wTwo,int wThree);
	
	/**
	 * ���������ͨ
	 * @param wOne һ·��0��127��
	 * @param wAnother ��·��0��127��
	 * @param wThree ��·
	 * @version 2006-11-22
	 * @return 0 �ɹ�
	 * @return -1 һ·������Χ
	 * @return -2 ��·������Χ
	 * @return -3 һ·��·����һ�����ϣ���֮��û������
	 * @return -4 һ·���ڷ���
	 * @return -5 ��·���ڷ���
	 * @return -6 ��·������Χ
	 * @return -7 ��·���ڷ���
	 */
	public abstract int clearThree(int wOne,int wTwo,int wThree);
	
}
