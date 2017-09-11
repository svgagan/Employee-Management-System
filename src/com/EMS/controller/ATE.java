package com.EMS.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.EMS.service.AdminService;
import com.EMS.service.EmployeeService;
import com.EMS.service.ManagerService;
import com.EMS.utility.FunctionResponse;
import com.EMS.dao.EmployeeDao;
import com.EMS.entity.ATEDetails;
import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Registration;
import com.sun.jndi.toolkit.dir.DirSearch;

/**
 * Servlet implementation class ATE
 */
@WebServlet("/ATE")
public class ATE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ATE() {
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
			try{
				int eId = (int)session.getAttribute("userId");
				Registration register = EmployeeService.getUserDetails(eId);
				List<ATEDetails> ATE = EmployeeService.getATEDetails(eId);
				List<DirectoryDetails> directory = EmployeeService.getProtectedDirectoryDetails(eId,register.getManagerId());
				request.setAttribute("directory", directory);
				request.setAttribute("ATE", ATE);
				RequestDispatcher rs = request.getRequestDispatcher("employeeATE.jsp");
				rs.forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("error", "Network Error");
				RequestDispatcher rs = request.getRequestDispatcher("employeeATE.jsp");
				rs.forward(request, response);
			}
		}else{
			/*request.setAttribute("message", "Details Not Available");
			RequestDispatcher rs = request.getRequestDispatcher("employeeATE.jsp");
			rs.forward(request, response);*/
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
			int empId = (int)session.getAttribute("userId");
			String dirId = request.getParameter("directory");
			int dir = Integer.parseInt(dirId);
			if(dir > 0){
				try{
					FunctionResponse responses = EmployeeService.getATEDetailsFor(dir,empId);
					if(!responses.getStatus()){
						Registration register = EmployeeService.getUserDetails(empId);
						DirectoryDetails dirc = EmployeeService.getDirectoryDetails(dir);
						FunctionResponse fresponse = EmployeeService.saveATERequest(empId,dirc.getManagerId(),dir);
						request.setAttribute("success", fresponse.getResponse());
						doGet(request, response);
					}else{
						request.setAttribute("error", "Duplicate Requests");
						doGet(request, response);
					}
				}catch(Exception e){
					e.printStackTrace();
					request.setAttribute("message", "Network Error");
					doGet(request, response);
				}
			}else{
				request.setAttribute("message", "Select Directory To Request Access");
				doGet(request, response);
			}
		}else{
			request.setAttribute("error", "Invalid Access");
			RequestDispatcher rs = request.getRequestDispatcher("employeeATE.jsp");
			rs.forward(request, response);
		}
	}

}
