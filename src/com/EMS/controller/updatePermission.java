package com.EMS.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Permission;
import com.EMS.entity.Registration;
import com.EMS.service.EmployeeService;
import com.EMS.service.ManagerService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class updatePermission
 */
@WebServlet("/updatePermission")
public class updatePermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatePermission() {
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
			int eId = (int) session.getAttribute("userId");//	System.out.println(eId);
			List<DirectoryDetails> directories = null;
			List<Permission>  permission = null;
			try {
				directories = ManagerService.getAllDirectories(eId);
				permission = ManagerService.getAllPermission();
				request.setAttribute("permission", permission);
				request.setAttribute("directories", directories);
				RequestDispatcher rs = request.getRequestDispatcher("managerDirectory.jsp");
				rs.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Registration register = null;
				try {
					register = EmployeeService.getUserDetails(eId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				request.setAttribute("register", register);
				RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
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
		if(session.getAttribute("userId") != null){
			int eId = (int) session.getAttribute("userId");
			String directory = request.getParameter("pubDir");
			String permission = request.getParameter("newPermission");
			if(Integer.parseInt(permission) != -1 && Integer.parseInt(directory) != -1){
				FunctionResponse fresResponse;
				try {
					fresResponse = ManagerService.updatePermission(Integer.parseInt(directory),Integer.parseInt(permission),eId);
					request.setAttribute("message", fresResponse.getResponse());
					doGet(request, response);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("message", "Failed to Update");
					doGet(request, response);
				}
			}else{
				request.setAttribute("message", "Directory and Permission must be selected");
				doGet(request, response);
			}
		}else{
			session.invalidate();
			request.setAttribute("loginerror","Network Error");
	    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
		    rs.forward(request, response);
		}
	}

}
