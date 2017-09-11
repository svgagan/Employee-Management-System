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
import com.EMS.entity.Registration;
import com.EMS.service.AdminService;

/**
 * Servlet implementation class Profiles
 */
@WebServlet("/Profiles")
public class Profiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profiles() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null){
			int eId = (int) session.getAttribute("userId");
			if(eId > 0){
				Registration employee = null;
				OrganizationProfile profile = null;
				try {
					employee = AdminService.getRegistrationDetails(eId);
					profile = AdminService.getEmployeeProfileDetails(eId);
					if(profile == null){
						profile.setDivisionName("NA");
						profile.setRole("NA");
						profile.setSupervisor(employee.getUsername()+" "+employee.getPassword());
					}
					request.setAttribute("profile", profile);
					request.setAttribute("employee", employee);
					RequestDispatcher rs = request.getRequestDispatcher("userOrganizationProfile.jsp");
					rs.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("message", "Network Error");
					RequestDispatcher rs = request.getRequestDispatcher("userOrganizationProfile.jsp");
					rs.forward(request, response);
				}
			}else{
				request.setAttribute("message", "Netowrk Error");
				RequestDispatcher rs = request.getRequestDispatcher("userOrganizationProfile.jsp");
				rs.forward(request, response);
			}
		}else{
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
	}

}
