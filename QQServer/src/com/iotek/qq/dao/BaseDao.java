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
	protected Connection conn;// ���ݿ����Ӷ���
	/**
	 * 
	 * �����ݿ⽨������
	 * 
	 * @return ���Ӷ���
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
	 * �ر���Ӧ����Դ
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
	 * ��ɶ����ݿ����ɾ�ĵĲ���
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
				// �����ռλ��(?)��ִ��ǰ��ռλ��(?)�滻
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));

				}

			}
			res = ps.executeUpdate();// ִ����ɾ�����еĲ��������ز���Ӱ�������

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			colseAll(rs, ps, conn);// �ͷ����е���Դ
		}

		return res > 0 ? true : false;// �������������0��Ӱ��ɹ�������true���򷵻�false

	}

	/**
	 * ͨ��������ƽ�class�๹�����һ������Ķ��� ʹ�÷��ͷ����ͷ�����ƽ��з�װ
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return data//���ݼ���
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
				// �����ռλ��(?)��ִ��ǰ��ռλ��(?)�滻
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}

			}
			rs = ps.executeQuery();// ��ѯ�����ļ�¼��װ�ɶ�Ӧ��ʵ�������
			ResultSetMetaData rsd = rs.getMetaData();
			// �õ���¼��Ԫ���ݶ���ͨ���˶�����Եõ���Ľṹ������������������
			while (rs.next()) {
				T m = cls.newInstance();
				for (int i = 0; i < rsd.getColumnCount(); i++) {
					// �������
					String col_name = rsd.getColumnName(i + 1);
					// ���������Ӧ��ֵ
					Object values = rs.getObject(col_name);
					Field field = cls.getDeclaredField(col_name);
					field.setAccessible(true);// ��˽���������ÿɷ���Ȩ
					field.set(m, values);// �������˽�����Ը�ֵ

				}
				data.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			colseAll(rs, ps, conn);// �ͷ����е���Դ
		}

		return data;// �������ݼ���
	}
}