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

import com.EMS.entity.Leave;
import com.EMS.entity.LeaveDetails;
import com.EMS.entity.Payment;
import com.EMS.entity.Registration;
import com.EMS.service.AdminService;
import com.EMS.service.ManagerService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class Leaves
 */
@WebServlet("/Leaves")
public class Leaves extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Leaves() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null){
			int eId = (int) session.getAttribute("userId");
			Registration employee = null;
			List<LeaveDetails> leave = new ArrayList<LeaveDetails>();
			try {
				//employee = AdminService.getRegistrationDetails(eId);
				leave = ManagerService.getLeaveDetails(eId);
				Leave num = ManagerService.getLeaveNum(eId);
				request.setAttribute("leave", leave);
				request.setAttribute("leaveNum", num);
				//request.setAttribute("employee", employee);
				RequestDispatcher rs = request.getRequestDispatcher("employeeLeaveRequest.jsp");
				rs.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "Network Error");
				RequestDispatcher rs = request.getRequestDispatcher("employeeLeaveRequest.jsp");
				rs.forward(request, response);
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
		int eId = (int) session.getAttribute("userId");
		String details = request.getParameter("leave");
		Registration employee = null;
		try {
			Leave num = ManagerService.getLeaveNum(eId);
			if(num.getLeaveNum() != 0){
				employee = AdminService.getRegistrationDetails(eId);
				FunctionResponse fresponse  = ManagerService.saveLeaveRequest(eId,employee.getManagerId(),details);
				request.setAttribute("message", fresponse.getResponse());
				doGet(request, response);
			}else{
				request.setAttribute("message", "No Leaves available to request");
				doGet(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "Network Error");
			RequestDispatcher rs = request.getRequestDispatcher("employeeLeaveRequest.jsp");
			rs.forward(request, response);
		}
	}

}
