/*===========================================================================
 * 版权：IOTEK Software 版权所有(c) 2015-2016
 * 文件：DAOFactory.java
 * 修改记录：
 * 日期   	         作者  	     内容
 * ==========================================================================
 * 2015-12-15		李川		创建DAOFactory类
 * ==========================================================================*/
package com.iotek.qq.db;

import com.iotek.qq.dao.FriendsDao;
import com.iotek.qq.dao.MessageDao;
import com.iotek.qq.dao.NearestContactDao;
import com.iotek.qq.dao.UserDao;

/**
 * <p>
 * 功能描述:[DAOFactory]
 * </p>
 * @author <a href="c.li@haitongwangxiao.com">李川</a>
 * @version Revision: 1.0.0
 */
public class DAOFactory {
	/**
	 * <p>
	 * 功能描述:构造方法
	 * </p>
	 * 
	 * @coustructor 方法.
	 */
	public DAOFactory() {
	}

	/**
	 * <p>
	 * 功能描述:获取UserDAO
	 * </p>
	 * 
	 * @return UserDAO
	 * @author:孟晓
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static UserDao getUserDAO() {
		return new UserDao();
	}
	/**
	 * 获取MessageDao
	 */
	public static MessageDao getMessageDao(){
		return new MessageDao();
	}
	
	/**
	 * 获取FriendsDao
	 */
	public static FriendsDao getFriendsDao(){
		return new FriendsDao();
	}
	
	/**
	 * 获取NearestContactDao
	 */
	public static NearestContactDao getNearestContactDao(){
		return new NearestContactDao();
	}

}
