package com.microfun.yuiaragaki.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.microfun.yuiaragaki.entity.HttpResponseEntity;
import com.microfun.yuiaragaki.entity.Scholl;
import com.microfun.yuiaragaki.entity.Student;
import com.microfun.yuiaragaki.entity.User;
import com.microfun.yuiaragaki.util.HibernateUtil;

/**
 * 自己摸索好几天写出的第一个接口，注册接口
 * 参数 用户名，密码，手机号
 * 
 * @author kevinchen
 *
 */
public class RegisterServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = null;
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		
		System.out.println(userName);
		System.out.println(password);
		System.out.println(phone);
		
		User user = new User();
		user.setUserName(userName);
		user.setPhone(phone);
		user.setPassword(password);
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
//		User temp = session.get(User.class, phone);
		String hql = "from User as user where user.phone=:phone";
		Query query = session.createQuery(hql);
		query.setString("phone", phone);
		List<User> temp = query.list();
		tx.commit();
		PrintWriter out = response.getWriter();
		HttpResponseEntity<User> responseData = new HttpResponseEntity<User>();
		if(temp != null && temp.size() != 0)
		{
			responseData.setData(temp.get(0));
			responseData.setResultCode(HttpResponseEntity.RESULT_OK);
			responseData.setResultMessage("OK");
//			response.setContentType("application/json; charset=utf-8");
		}
		else
		{
			responseData.setData(null);
			responseData.setResultCode(HttpResponseEntity.RESULT_ERROR);
			responseData.setResultMessage("ERROR");
		}
		String result = JSONArray.fromObject(responseData).toString();
		out.print(result);
		out.flush();
		System.out.println(result);
		if(out != null)
		{
			out.close();
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
