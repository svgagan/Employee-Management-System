package com.EMS.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.EMS.service.EmployeeService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String uid = request.getParameter("uid");
		String password = request.getParameter("password");
		FunctionResponse fResponse;
		try{
			fResponse = EmployeeService.registration(firstname, lastname, email, address,phone,uid, password);
			if(fResponse.getStatus()){
				response.sendRedirect("login.jsp");
			}else{
				request.setAttribute("error",fResponse.getResponse());
	        	RequestDispatcher rs = request.getRequestDispatcher("register.jsp");
			    rs.forward(request, response);
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}

}
