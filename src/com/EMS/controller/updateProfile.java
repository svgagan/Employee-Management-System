package com.EMS.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.EMS.entity.Registration;
import com.EMS.service.EmployeeService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class updateProfile
 */
@WebServlet("/updateProfile")
public class updateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();  
        /*session.invalidate();  
		response.sendRedirect("login.jsp");*/
		if(session.getAttribute("userId") != null){
			int id = (int)session.getAttribute("userId");
			try {
				Registration register = EmployeeService.getUserDetails(id);
				if(register.getRole() == 1){
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("adminDashboard.jsp");
					rs.forward(request, response);
				}else if(register.getRole() == 2){
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
					rs.forward(request, response);
				}else{
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
					rs.forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				session.invalidate();
				request.setAttribute("loginerror","Technical Error In Sessions, Contact System Administrator");
		    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
			    rs.forward(request, response);
			}
		}else{
			session.invalidate();
			System.out.println("Dashboard Controller - Session value");
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
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String userId = request.getParameter("id");
		HttpSession session = request.getSession();
		//System.out.println(phone+"- Email-"+email+"- Address-"+address+" = Session value ->"+session.getAttribute("userId"));
		int id = (int) session.getAttribute("userId");
		int role = (int) session.getAttribute("role");
		Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
		Matcher matcher = pattern.matcher(email);
		if(id > 0 && !email.isEmpty() && matcher.matches() && !(email == null)){
			try {
				FunctionResponse fresponse = EmployeeService.updateUserDetails(email,address,phone,id);
				Registration register = EmployeeService.getUserDetails(id);
				request.setAttribute("register", register);
				request.setAttribute("updateSuccess",fresponse.getResponse());
				if(role == 1){
					RequestDispatcher rs = request.getRequestDispatcher("adminDashboard.jsp");
					rs.forward(request, response);
				}else if(role == 2){
					RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
					rs.forward(request, response);
				}else{
					RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
					rs.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			try {
				request.setAttribute("updateError","Invalid Details");
				Registration register = EmployeeService.getUserDetails(id);
				request.setAttribute("register", register);
				if(role == 1){
					RequestDispatcher rs = request.getRequestDispatcher("adminDashboard.jsp");
					rs.forward(request, response);
				}else if(role == 2){
					RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
					rs.forward(request, response);
				}else{
					RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
					rs.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
