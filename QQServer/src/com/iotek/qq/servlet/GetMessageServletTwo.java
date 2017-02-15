package com.iotek.qq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.iotek.qq.bean.Message;
import com.iotek.qq.db.DAOFactory;
import com.iotek.qq.db.Messagedb;

public class GetMessageServletTwo extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = null;
		Gson gson = new Gson();
		Message message = new Message();		
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
			e.printStackTrace();
		}
		//���ܿͻ��˲����Ҹ�ֵ�����
		message.setMessage_senderId(Integer.parseInt(req.getParameter(Messagedb.MESSAGE_SENDERID)));
		message.setMessage_recipientId(Integer.parseInt(req.getParameter(Messagedb.MESSAGE_RECIPIENTID)));
		
		//������ݿⷽ�����в��������ܷ���ֵ
		Map map = DAOFactory.getMessageDao().selectMessageTwo(message);
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
