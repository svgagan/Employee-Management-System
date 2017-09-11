package com.EMS.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.EMS.entity.Payment;
import com.EMS.entity.Registration;
import com.EMS.service.AdminService;
import com.EMS.service.ManagerService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class Bonus
 */
@WebServlet("/Bonus")
public class Bonus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bonus() {
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
			int id = (int) session.getAttribute("userId");
			String eId = request.getParameter("employeeId");
			int eid= Integer.parseInt(eId);
			Registration employee = null;
			Payment payment = null;
			try {
				employee = AdminService.getRegistrationDetails(eid);
				payment = AdminService.getPaymentDetails(eid);
				request.setAttribute("payment", payment);
				request.setAttribute("employee", employee);
				RequestDispatcher rs = request.getRequestDispatcher("managerBonus.jsp");
				rs.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "Network Error");
				RequestDispatcher rs = request.getRequestDispatcher("managerSupervisedUser.jsp");
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
		String eId = request.getParameter("employeeId");
		String pId = request.getParameter("paymentId");
		String bon = request.getParameter("bonus");
		int eid= Integer.parseInt(eId);
		int paymentid= Integer.parseInt(pId);
		double bonus = Double.parseDouble(bon);
		Registration employee = null;
		Payment payment = null;
		HttpSession session = request.getSession();
		try {
			FunctionResponse fresponse = ManagerService.saveBonusDetails(eid,paymentid,bonus);
			employee = AdminService.getRegistrationDetails(eid);
			payment = AdminService.getPaymentDetails(eid);
			request.setAttribute("payment", payment);
			request.setAttribute("employee", employee);
			request.setAttribute("message", fresponse.getResponse());
			RequestDispatcher rs = request.getRequestDispatcher("managerBonus.jsp");
			rs.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				employee = AdminService.getRegistrationDetails(eid);
				payment = AdminService.getPaymentDetails(eid);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("payment", payment);
			request.setAttribute("employee", employee);
			request.setAttribute("message", "Network Error");
			RequestDispatcher rs = request.getRequestDispatcher("managerBonus.jsp");
			rs.forward(request, response);
		}
	}

}
