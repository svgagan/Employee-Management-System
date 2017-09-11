package com.EMS.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.EMS.entity.LeaveDetails;
import com.EMS.entity.Registration;
import com.EMS.service.EmployeeService;
import com.EMS.utility.DayChecker;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
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
			System.out.println("Login Controller - Session value");
			//request.setAttribute("loginerror","Session Terminated");
	    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
		    rs.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String loginId = request.getParameter("username");
		//List<MediaDTO> filesList = new ArrayList<MediaDTO>();
		com.EMS.utility.FunctionResponse fresponse;
		try{
			fresponse = EmployeeService.validateLogin(password, loginId);
			Boolean dayCheck = DayChecker.firstDayChecker();
			if(dayCheck){
				//List<LeaveDetails> employeeLeaves = new ArrayList<LeaveDetails>();
				Boolean employeeLeaves = EmployeeService.getAllEmployeeLeavesDetails();
				Boolean payments = EmployeeService.updatePayments();
			}
			if(fresponse.getStatus()){
				HttpSession session = request.getSession();
				session.setAttribute("username", loginId);
				session.setAttribute("password", password);
				//filesList = MediaFilesService.getMediaFiles(fresponse.getId());
				//request.setAttribute("filesList", filesList);
				session.setAttribute("firstname",fresponse.getResponse());
				session.setAttribute("role",fresponse.getRole());
				session.setAttribute("userId", fresponse.getEmployeeId());
				String activityStatus = "FALSE";
				if(fresponse.getActivityStatus()){
					activityStatus = "TRUE";
				}else{
					activityStatus = "FALSE";
				}
				session.setAttribute("activityStatus", activityStatus);
				Registration register = EmployeeService.getUserDetails(fresponse.getEmployeeId());
				if(fresponse.getRole() == 1){
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("adminDashboard.jsp");
					rs.forward(request, response);
				}else if(fresponse.getRole() == 2){
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
					rs.forward(request, response);
				}else{
					request.setAttribute("register", register);
					RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
					rs.forward(request, response);
				}
			}else{
				request.setAttribute("loginerror",fresponse.getResponse());
	        	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
			    rs.forward(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
