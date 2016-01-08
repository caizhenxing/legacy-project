/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.servlet;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import jp.go.jsps.kaken.log.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;

import org.apache.commons.logging.*;

/**
 * CallServletHandler�ŌĂ΂ꂽ���������s���邽�߂̃T�[�u���b�g�B
 * 
 * ID RCSfile="$RCSfile: InterfaceServlet.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:54 $"
 */
public class InterfaceServlet extends HttpServlet {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * �N���X���B 
	 */
	private static final String CLASS_NAME = InterfaceServlet.class.getName();

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(CLASS_NAME);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * �����n���h���}�b�v
	 */
	private Map commands = new HashMap();

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * ����������
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {

		//�T�[�r�X�����N���X�̓o�^
		commands = (Map)getServletContext().getAttribute(IServiceName.APPLICATION_SERVICE);

	}

	/* 
	 * �Ă΂ꂽ�Ƃ��Ƀ��b�Z�[�W��\������B
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		System.out.println(" Hello! " + getClass().getName() + ".");

		//�p�����[�^�̊m�F (4 DEBUG)
		for (Enumeration e = request.getParameterNames();
			e.hasMoreElements();
			) {
			String sParamName = (String) e.nextElement();
			String[] sParamValues = request.getParameterValues(sParamName);
			System.out.println(
				"�p�����[�^::'"
					+ sParamName
					+ "'::'"
					+ Arrays.asList(sParamValues)
					+ "'");
		}

		//�w�b�_�[���̊m�F (4 DEBUG)
		for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();) {
			String sHeaderName = (String) e.nextElement();
			String sHeaderValue = request.getHeader(sHeaderName);
			System.out.println(
				"�w�b�_�[::'" + sHeaderName + "'::'" + sHeaderValue + "'");
		}

		return;
	}

	/* 
	 * �R�}���h���ƃI�u�W�F�N�g���󂯎��A����������ŁA�ԋp���ꂽ�I�u�W�F�N�g��Ԃ��B
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		if (log.isDebugEnabled()) {
			log.debug(getClass().getName() + " ���X�N�G�X�g�������J�n���܂��B");
		}

		//�N���C�A���g�֕Ԃ��I�u�W�F�N�g
		Object objReturn = new Object();

		try {
			//�Ăяo��������̈������p�X�g���[�����쐬����
			ObjectInputStream dis =
				new ObjectInputStream(
					new BufferedInputStream(request.getInputStream()));

			//�߂�l��MIME�^�C�v��ݒ肷��
			response.setContentType("application/octet-stream");

			//�߂�l�p�̃X�g���[�����쐬����
			ObjectOutputStream dos =
				new ObjectOutputStream(
					new BufferedOutputStream(response.getOutputStream()));

			//�Ăяo��������̌Ăяo���T�[�r�X�����擾����
			String serveceName = dis.readUTF();

			//�Ăяo��������̌Ăяo�����\�b�h�����擾����
			String strMethod = dis.readUTF();

			//�Ăяo��������̌Ăяo�����\�b�h�̃p�����[�^���擾����
			Class[] paramTypes = (Class[]) dis.readObject();

			//�Ăяo��������̌Ăяo�����\�b�h�̃p�����[�^���擾����
			Object[] params = (Object[]) dis.readObject();

			//���s���\�b�h				
			Method method = null;
			try {
				if (!commands.containsKey(serveceName)) {
					if (log.isInfoEnabled()) {
						log.info(
							"�T�[�r�X�� '" + serveceName + "':���s�Ώۂ̃T�[�r�X��������܂���B");
					}
					dos.writeObject(
						new NoSuchMethodException(
							"�T�[�r�X�� '" + serveceName + "':���s�Ώۂ̃T�[�r�X��������܂���B"));
				} else {
					//���\�b�h�̎擾
					method =findPublicMethod(commands.get(serveceName),
							strMethod,
							paramTypes);

					//invoke�Ώۃ��\�b�h���݂���Ȃ��Ƃ��B
					if (method == null) {
						if (log.isInfoEnabled()) {
							log.info(
								"������ '" + strMethod + "':���s�Ώۂ̃��\�b�h��������܂���B");
						}
						dos.writeObject(
							new NoSuchMethodException(
								"������ '" + strMethod + "':���s�Ώۂ̃��\�b�h��������܂���B"));
					}

					//�����p�t�H�[�}���X���O
					PerformanceLogWriter pw = null;
					if (log.isDebugEnabled()) {
						pw = new PerformanceLogWriter();
					}
					
					//���s
					objReturn =
						method.invoke(commands.get(serveceName), params);
					
					//�����p�t�H�[�}���X���O
					if(pw != null){
						pw.out("�������F" + serveceName + "." + strMethod);
					}

					//���\�b�h����̖߂�l���Ăяo�����A�v���b�g�ɓn��
					dos.writeObject(objReturn);
				}
			} catch (InvocationTargetException e) {
				//�\�������O
				if (e.getCause() instanceof ApplicationException) {
					//��O���Ăяo�����ɓn��
					if (log.isDebugEnabled()) {
						log.debug(
								"������ '"
									+ method.getDeclaringClass().getName()
									+ "#"
									+ strMethod
									+ "()':�Ăяo���ŗ�O���������܂����B\n"
									, e.getCause());
					}
				//�\�����ʗ�O
				} else {
					//��O���Ăяo�����ɓn��
					if (log.isInfoEnabled()) {
						log.info(
							"������ '"
								+ method.getDeclaringClass().getName()
								+ "#"
								+ strMethod
								+ "()':�Ăяo���ŗ�O���������܂����B\n"
								, e.getCause());
					}
				}
				dos.writeObject(e.getTargetException());

			} catch (Throwable e) {
				log.info("���X�N�G�X�g�����ŗ�O���������܂����B", e);
				//��O���Ăяo�����A�v���b�g�ɓn��
				dos.writeObject(e);
			} finally {
				//�������I��������������p�X�g���[���̌㏈�����s��
				dis.close();
				//�������I��������߂�l�p�X�g���[���̌㏈�����s��
				dos.flush();
				dos.close();
			}
		} catch (Throwable e) {
			log.info("�N���C�A���g�Ƃ̒ʐM�����Ɏ��s���܂����B", e);
			throw new ServletException("�N���C�A���g�Ƃ̒ʐM�����Ɏ��s���܂����B", e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug(getClass().getName() + "���X�N�G�X�g�������I�����܂��B");
			}
		}
	}

	/**
	 * �R�}���h�C���^�[�t�F�[�X���������N���X���\�b�h���A�Y�����郁�\�b�h���擾����B
	 * @param declaringClass	�Ăяo�����\�b�h��錾�����N���X�B
	 * @param methodName		�Ăяo�����\�b�h��
	 * @param argClasses		���\�b�h����
	 * @return
	 */
	private static Method findPublicMethod(
		Object declaringClass,
		String methodName,
		Class[] argClasses) {

		Method[] methods = declaringClass.getClass().getMethods();
		ArrayList list = new ArrayList();
		for (int i = 0; i < methods.length; i++) {
			// Collect all the methods which match the signature.
			Method method = methods[i];
			if (method.getName().equals(methodName)) {
				if (matchArguments(argClasses, method.getParameterTypes())) {
					list.add(method);
				}
			}
		}
		if (list.size() > 0) {
			if (list.size() == 1) {
				return (Method) list.get(0);
			} else {
				ListIterator iterator = list.listIterator();
				Method method;
				while (iterator.hasNext()) {
					method = (Method) iterator.next();
					if (matchExplicitArguments(argClasses,
						method.getParameterTypes())) {
						return method;
					}
				}
				// This list is valid. Should return something.
				return (Method) list.get(0);
			}
		}
		return null;
	}

	/**
	 * �p�����[�^�����ƃ��\�b�h�̈����z�񂪓����ł��邩���m�F����B
	 * @param argClasses		�p�����[�^�����z��
	 * @param argTypes			���\�b�h�̈����z��
	 * @return  �p�����[�^�����z��ƃ��\�b�h�̈����z�񂪓������Ƃ� true �ȊO false
	 */
	private static boolean matchExplicitArguments(
		Class[] argClasses,
		Class[] argTypes) {
		boolean match = (argClasses.length == argTypes.length);
		for (int j = 0; j < argClasses.length && match; j++) {
			Class argType = argTypes[j];
			if (argClasses[j] != argType) {
				match = false;
			}
		}
		return match;
	}

	/**
	 * �p�����[�^�����ƃ��\�b�h�̈����z�񂪑���\(�C���^�[�t�F�[�X�j�ł��邩���m�F����B
	 * @param argClasses		�p�����[�^�����z��
	 * @param argTypes			���\�b�h�̈����z��
	 * @return  �p�����[�^�����z��ƃ��\�b�h�̈����z�񂪈�v����Ƃ� true �ȊO false
	 */
	private static boolean matchArguments(
		Class[] argClasses,
		Class[] argTypes) {
		boolean match = (argClasses.length == argTypes.length);
		for (int j = 0; j < argClasses.length && match; j++) {
			Class argType = argTypes[j];
			// Consider null an instance of all classes.
			if (argClasses[j] != null
				&& !(argType.isAssignableFrom(argClasses[j]))) {
				match = false;
			}
		}
		return match;
	}
}
