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
import com.EMS.entity.Employeedocs;
import com.EMS.service.EmployeeService;
import com.EMS.service.ManagerService;

/**
 * Servlet implementation class ManagerDirectories
 */
@WebServlet("/ManagerDirectories")
public class ManagerDirectories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerDirectories() {
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
				int managerId = (int) session.getAttribute("userId");
				List<DirectoryDetails> directories = ManagerService.getSubManagersDirectories(managerId);
				request.setAttribute("directories", directories);
				RequestDispatcher rs = request.getRequestDispatcher("managerEmployeeDirectory.jsp");
				rs.forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			/*request.setAttribute("message", "Network Error");
			RequestDispatcher rs = request.getRequestDispatcher("managerEmployeeDirectory.jsp");
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
			int eId = (int) session.getAttribute("userId");
			String dirId = request.getParameter("dirId");
			try {
				List<Employeedocs> docs = ManagerService.getAllDocuments(Integer.parseInt(dirId));
				request.setAttribute("docs", docs);
				RequestDispatcher rs = request.getRequestDispatcher("managerEmployeeFiles.jsp");
				rs.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "Network Error");
				RequestDispatcher rs = request.getRequestDispatcher("managerEmployeeDirectory.jsp");
				rs.forward(request, response);
			}
		}else{
			request.setAttribute("loginerror","Invalid Accessing");
        	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
		    rs.forward(request, response);
		}
	}

}
