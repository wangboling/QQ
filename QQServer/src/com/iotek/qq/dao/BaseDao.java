package com.iotek.qq.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.iotek.qq.servlet.LoginServlet;


public class BaseDao {
	protected Connection conn;// 数据库链接对象
	/**
	 * 
	 * 与数据库建立连接
	 * 
	 * @return 连接对象
	 */

	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(LoginServlet.DRIVER);
			conn = DriverManager.getConnection(LoginServlet.URL);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}

	/**
	 * 关闭相应的资源
	 * 
	 * @param rs
	 * @param ps
	 * @param conn
	 */

	public void colseAll(ResultSet rs, PreparedStatement ps, Connection conn) {

		try {
			if (rs != null) {

				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {

				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/***
	 * 完成对数据库的增删改的操作
	 * 
	 * @param sql
	 * @param params
	 * @return true false
	 */

	public boolean operUpdate(String sql, List<Object> params) {
		int res = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			if (params != null) {
				// 如果有占位符(?)在执行前将占位符(?)替换
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));

				}

			}
			res = ps.executeUpdate();// 执行增删改所有的操作，返回操作影响的行数

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			colseAll(rs, ps, conn);// 释放所有的资源
		}

		return res > 0 ? true : false;// 如果返回数大于0就影响成功，返回true否则返回false

	}

	/**
	 * 通过反射机制将class类构造出来一个具体的对象 使用泛型方法和反射机制进行封装
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return data//数据集合
	 * 
	 */
	public <T> List<T> operQuery(String sql, List<Object> params, Class<T> cls)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> data = new ArrayList<T>();
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			if (params != null) {
				// 如果有占位符(?)在执行前将占位符(?)替换
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}

			}
			rs = ps.executeQuery();// 查询出来的记录封装成对应的实体类对象
			ResultSetMetaData rsd = rs.getMetaData();
			// 得到记录集元数据对象，通过此对象可以得到表的结构包括列名和数据类型
			while (rs.next()) {
				T m = cls.newInstance();
				for (int i = 0; i < rsd.getColumnCount(); i++) {
					// 获得列名
					String col_name = rsd.getColumnName(i + 1);
					// 获得列所对应的值
					Object values = rs.getObject(col_name);
					Field field = cls.getDeclaredField(col_name);
					field.setAccessible(true);// 给私有属性设置可访问权
					field.set(m, values);// 给对象的私有属性赋值

				}
				data.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			colseAll(rs, ps, conn);// 释放所有的资源
		}

		return data;// 返回数据集合
	}
}