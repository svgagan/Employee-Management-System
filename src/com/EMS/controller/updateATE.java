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

import com.EMS.entity.ATEDetails;
import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Registration;
import com.EMS.service.EmployeeService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class updateATE
 */
@WebServlet("/updateATE")
public class updateATE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateATE() {
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
				int mId = (int)session.getAttribute("userId");
				Registration register = EmployeeService.getUserDetails(mId);
				List<ATEDetails> ATE = EmployeeService.getATEList(mId);
				request.setAttribute("ATE", ATE);
				RequestDispatcher rs = request.getRequestDispatcher("managerATE.jsp");
				rs.forward(request, response);
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("error", "Network Error");
				RequestDispatcher rs = request.getRequestDispatcher("managerATE.jsp");
				rs.forward(request, response);
			}
		}else{
			/*request.setAttribute("message", "Details Not Available");
			RequestDispatcher rs = request.getRequestDispatcher("managerATE.jsp");
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
			try{
				int Id = (int)session.getAttribute("userId");
				Registration register = EmployeeService.getUserDetails(Id);
				String ateId = request.getParameter("ateId");
				String permission = request.getParameter("permission");
				FunctionResponse fresResponse = EmployeeService.updateATE(Id,Integer.parseInt(ateId),Integer.parseInt(permission));
				request.setAttribute("success", fresResponse.getResponse());
				doGet(request, response);
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("message", "Network Error");
				doGet(request, response);
			}
		}else{
			request.setAttribute("message", "Failed");
			doGet(request, response);
		}
	}

}
