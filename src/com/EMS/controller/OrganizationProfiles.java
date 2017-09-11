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

import com.EMS.entity.OrganizationProfile;
import com.EMS.entity.Payment;
import com.EMS.entity.Registration;
import com.EMS.service.AdminService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class OrganizationProfile
 */
@WebServlet("/OrganizationProfiles")
public class OrganizationProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrganizationProfiles() {
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
			String eId = request.getParameter("employeeId");
			int eid= Integer.parseInt(eId);
			if(eId != null){
				Registration employee = null;
				OrganizationProfile profile = null;
				try {
					employee = AdminService.getRegistrationDetails(eid);
					profile = AdminService.getEmployeeProfileDetails(eid);
					if(profile == null){
						profile.setDivisionName("NA");
						profile.setRole("NA");
						profile.setSupervisor(employee.getUsername()+" "+employee.getPassword());
					}
					request.setAttribute("profile", profile);
					request.setAttribute("employee", employee);
					RequestDispatcher rs = request.getRequestDispatcher("adminOrganizationProfile.jsp");
					rs.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("error", "Network Error");
					RequestDispatcher rs = request.getRequestDispatcher("adminActive.jsp");
					rs.forward(request, response);
				}
			}else{
				request.setAttribute("error", "Click Profile to continue");
				RequestDispatcher rs = request.getRequestDispatcher("adminActive.jsp");
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
		String pId = request.getParameter("profileId");
		String supervisor = request.getParameter("supervisor");
		String role = request.getParameter("role");
		String division = request.getParameter("division");
		int eid= Integer.parseInt(eId);
		int profileid= Integer.parseInt(pId);
		Registration employee = null;
		OrganizationProfile profile = null;
		HttpSession session = request.getSession();
		try {
			FunctionResponse fresponse = AdminService.saveProfileDetails(eid,profileid,role,division,supervisor);
			employee = AdminService.getRegistrationDetails(eid);
			profile = AdminService.getEmployeeProfileDetails(eid);
			request.setAttribute("profile", profile);
			request.setAttribute("employee", employee);
			request.setAttribute("message", fresponse.getResponse());
			RequestDispatcher rs = request.getRequestDispatcher("adminOrganizationProfile.jsp");
			rs.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				employee = AdminService.getRegistrationDetails(eid);
				profile = AdminService.getEmployeeProfileDetails(eid);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("profile", profile);
			request.setAttribute("employee", employee);
			request.setAttribute("message", "Network Error");
			RequestDispatcher rs = request.getRequestDispatcher("adminRunPayroll.jsp");
			rs.forward(request, response);
		}	
	}

}
