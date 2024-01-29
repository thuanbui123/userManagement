package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.GetCookie;
import model.UserModel;

@WebServlet("/")
public class UserServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		userDAO = new UserDAO();
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			if(action.equals("/new")) {
				showNewForm(request, response);
			} else if (action.equals("/insert")) {
				insertUser(request, response);
			} else if (action.equals("/edit")) {
				showEditForm(request, response);
			} else if (action.equals("/update")) {
				editUser(request, response);
			} else if (action.equals("/delete")) {
				deleteUser(request, response);
			} else {
				listUser(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void listUser (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserModel> list = userDAO.selectAllUsers();
		request.setAttribute("listUser", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertUser (HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		UserModel user = new UserModel(name, email, country);
		userDAO.insert(user);
		response.sendRedirect("list");
	}
	
	private void showEditForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		UserModel user = userDAO.selectUser(id);
		request.setAttribute("user", user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	public void editUser (HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		UserModel user = new UserModel(id, name, email, country);
		userDAO.update(user);
		response.sendRedirect("list");
	}
	
	public void deleteUser (HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.delete(id);
		response.sendRedirect("list");
	}
}
