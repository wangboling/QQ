package com.iotek.qq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.iotek.qq.bean.User;
import com.iotek.qq.db.DAOFactory;
import com.iotek.qq.db.Userdb;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String URL = null;
	public static String DRIVER = null;

	// ��ȡ�����ļ���ݿ�·��
	static {
		Properties properties = new Properties();
		try {
			properties.load(LoginServlet.class.getResourceAsStream("/DB.properties"));
			DRIVER = properties.getProperty("driver");
			URL = properties.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ���տͻ��˴����Ĳ���
		// ��¼����������һ��user����
		// ��user����ת����json��ʽ
		// DAOFactory.getUserDAO().MovieLogin(userName, userPwd);
		// ��json���󷵻ظ�ͻ��� 
		//����ֵ���룻����servlet���롾���ֻ�˲���������룬��ɾ�������д��롿
		resp.setContentType("text/html; charset=UTF-8"); 
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		User user = new User();
		out = resp.getWriter();
		//���ܿͻ��˵�����
		user.setUser_id(Integer.parseInt(req.getParameter(Userdb.USER_ID)));
		System.out.println(Integer.parseInt(req.getParameter(Userdb.USER_ID)));
		user.setUser_passWord(req.getParameter(Userdb.USER_PASSWORD));
		//������ݿⷽ�����в��������ܷ���ֵ
		Map map = null;
		map= DAOFactory.getUserDAO().loginUser(user);
		out.print(gson.toJson(map));
		out.flush();
		out.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

}
