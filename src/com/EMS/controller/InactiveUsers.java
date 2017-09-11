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

import com.EMS.entity.Registration;
import com.EMS.service.AdminService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class InactiveUsers
 */
@WebServlet("/InactiveUsers")
public class InactiveUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InactiveUsers() {
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
			try {
				List<Registration> inActiveEmployees = new ArrayList<Registration>();
				inActiveEmployees = AdminService.getAllInActiveEmployees();//Inactive Employees List
				List<Registration> mangersList = new ArrayList<Registration>();
				mangersList = AdminService.getManagers();//Managers List
				request.setAttribute("mangersList", mangersList);
				request.setAttribute("inActiveEmployees", inActiveEmployees);
				RequestDispatcher rs = request.getRequestDispatcher("adminInactive.jsp");
				rs.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
		String eId = request.getParameter("employeeId");
		String mId = request.getParameter("managerId");
		String role = request.getParameter("roleType");
		//System.out.println("Emp:"+eId+" Man:"+mId+" Role:"+role);
		int mid = Integer.parseInt(mId);
		int roleId = Integer.parseInt(role);
		int eid = Integer.parseInt(eId);
		if(roleId != -1 && mid != -1){
			try {
				FunctionResponse fresponse = AdminService.saveInactiveUserDetails(eid,mid,roleId);
				request.setAttribute("success", fresponse.getResponse());
				doGet(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("failed", "Network Error");
				doGet(request, response);
			}
		}else{
			request.setAttribute("failed", "Select Manager & RoleType");
			doGet(request, response);
		}
	}

}
