package com.iotek.qq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.iotek.qq.bean.Friends;
import com.iotek.qq.db.DAOFactory;
import com.iotek.qq.db.Friendsdb;


public class DelFriendsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = null;
		Gson gson = new Gson();
		Friends friends = new Friends();		
		try {
			out = resp.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//����ֵ����
		resp.setContentType("text/html,UTF-8");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//���ܿͻ��˲����Ҹ�ֵ�����
		friends.setFriends_uid(Integer.parseInt(req.getParameter(Friendsdb.FRIENDS_UID)));
		friends.setFriends_toUid(Integer.parseInt(req.getParameter(Friendsdb.FRIENDS_TOUID)));
		
		
		//������ݿⷽ�����в��������ܷ���ֵ
		Map map = DAOFactory.getFriendsDao().delFriends(friends);
		//�ѷ���ֵת����json��ʽ���ҷ��ؿͻ���
		out.print(gson.toJson(map));
		out.flush();
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
