package com.iotek.qq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.iotek.qq.bean.User;
import com.iotek.qq.db.DAOFactory;
import com.iotek.qq.db.Userdb;

public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = null;
		Gson gson = new Gson();
		User user = new User();
		try {
			out = resp.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ����ֵ����
		resp.setContentType("text/html,UTF-8");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���ܿͻ��˲������Ҹ�ֵ������
		user.setUser_passWord(req.getParameter(Userdb.USER_PASSWORD));
		user.setUser_nickName(req.getParameter(Userdb.USER_NICKNAME));
		user.setUser_email(req.getParameter(Userdb.USER_EMAIL));

		// �������ݿⷽ�����в��������ܷ���ֵ
		Boolean boo = DAOFactory.getUserDAO().insertUser(user);
		Map map = null;
		if (boo) {
			map = DAOFactory.getUserDAO().maxUserId(user);
			out.print(gson.toJson(map));
		} else {
			map = new HashMap<String, String>();
			map.put("result", boo);
			out.print(gson.toJson(map));
		}
		// �ѷ���ֵת����json��ʽ���ҷ��ؿͻ���
		// out.print(gson.toJson(map));
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
