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
 * Servlet implementation class Directories
 */
@WebServlet("/Directories")
public class Directories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Directories() {
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
		int eId = (int) session.getAttribute("userId");
		String directory = request.getParameter("directory");
		String permission = request.getParameter("permission");
		int perm = Integer.parseInt(permission);System.out.println(eId+" Dir-"+directory+" Per"+perm+" Lenght"+directory.length());
		if(perm != -1 && directory.length()>0){
			try {
				FunctionResponse fresponse = ManagerService.saveDirectory(eId,directory,perm);
				request.setAttribute("message", fresponse.getResponse());
				doGet(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "Network Error");
				doGet(request, response);
			}
		}else{
			request.setAttribute("message", "DirectoryName and Permission are required");
			doGet(request, response);
		}
	}

}
