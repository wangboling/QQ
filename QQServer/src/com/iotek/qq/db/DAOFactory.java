/*===========================================================================
 * ��Ȩ��IOTEK Software ��Ȩ����(c) 2015-2016
 * �ļ���DAOFactory.java
 * �޸ļ�¼��
 * ����   	         ����  	     ����
 * ==========================================================================
 * 2015-12-15		�		����DAOFactory��
 * ==========================================================================*/
package com.iotek.qq.db;

import com.iotek.qq.dao.FriendsDao;
import com.iotek.qq.dao.MessageDao;
import com.iotek.qq.dao.NearestContactDao;
import com.iotek.qq.dao.UserDao;

/**
 * <p>
 * ��������:[DAOFactory]
 * </p>
 * @author <a href="c.li@haitongwangxiao.com">�</a>
 * @version Revision: 1.0.0
 */
public class DAOFactory {
	/**
	 * <p>
	 * ��������:���췽��
	 * </p>
	 * 
	 * @coustructor ����.
	 */
	public DAOFactory() {
	}

	/**
	 * <p>
	 * ��������:��ȡUserDAO
	 * </p>
	 * 
	 * @return UserDAO
	 * @author:����
	 * @update:[����YYYY-MM-DD] [����������][�������]
	 */
	public static UserDao getUserDAO() {
		return new UserDao();
	}
	/**
	 * ��ȡMessageDao
	 */
	public static MessageDao getMessageDao(){
		return new MessageDao();
	}
	
	/**
	 * ��ȡFriendsDao
	 */
	public static FriendsDao getFriendsDao(){
		return new FriendsDao();
	}
	
	/**
	 * ��ȡNearestContactDao
	 */
	public static NearestContactDao getNearestContactDao(){
		return new NearestContactDao();
	}

}
