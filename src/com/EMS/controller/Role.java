package com.EMS.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.EMS.entity.Registration;
import com.EMS.service.EmployeeService;

/**
 * Servlet implementation class Role
 */
@WebServlet("/Role")
public class Role extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Role() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*HttpSession session=request.getSession();  
		session.invalidate();  
		response.sendRedirect("login.jsp");
		session.invalidate();
		request.setAttribute("loginerror","Invalid Accessing");
    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
	    rs.forward(request, response);*/
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null){
			int role = (int) session.getAttribute("role");
			int id = (int) session.getAttribute("userId");
			try{
				if(role == 1){
					Registration register = EmployeeService.getUserDetails(id);
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("adminDashboard.jsp");
					rs.forward(request, response);
				}else if(role == 2){
					Registration register = EmployeeService.getUserDetails(id);
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
					rs.forward(request, response);
				}else if(role == 3){
					Registration register = EmployeeService.getUserDetails(id);
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
					rs.forward(request, response);
				}else{
					System.out.println("Coming to Role GET controller");
					response.sendRedirect("login.jsp");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			session.invalidate();
			request.setAttribute("loginerror","Invalid Accessing");
	    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
		    rs.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null){
			int role = (int) session.getAttribute("role");
			int id = (int) session.getAttribute("userId");
			try{
				if(role == 1){
					Registration register = EmployeeService.getUserDetails(id);
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("adminDashboard.jsp");
					rs.forward(request, response);
				}else if(role == 2){
					Registration register = EmployeeService.getUserDetails(id);
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
					rs.forward(request, response);
				}else if(role == 3){
					Registration register = EmployeeService.getUserDetails(id);
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
					rs.forward(request, response);
				}else{
					System.out.println("Coming to Role POST controller");
					response.sendRedirect("login.jsp");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			session.invalidate();
			request.setAttribute("loginerror","Invalid Accessing");
	    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
		    rs.forward(request, response);
		}
	}

}
