package com.EMS.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.EMS.entity.DirectoryDetails;
import com.EMS.entity.Employeedocs;
import com.EMS.service.EmployeeService;
import com.EMS.utility.FunctionResponse;

/**
 * Servlet implementation class UploadDocs
 */
@MultipartConfig(maxFileSize=16177215)//Upload file upto 16Mb
@WebServlet("/UploadDocs")
public class UploadDocs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDocs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//request.setAttribute("message", "Welcome...");
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null){
			int eId = (int) session.getAttribute("userId");
			try {
				List<Employeedocs> docs = EmployeeService.getAllDocuments(eId);
				List<DirectoryDetails> directory = EmployeeService.getAllDirectoriesFor(eId);
				request.setAttribute("docs", docs);
				request.setAttribute("directory", directory);
				RequestDispatcher rs = request.getRequestDispatcher("employeeFiles.jsp");
				rs.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "Network Error");
				RequestDispatcher rs = request.getRequestDispatcher("employeeFiles.jsp");
				rs.forward(request, response);
			}
		}else{
			/*request.setAttribute("loginerror","Invalid Accessing");
        	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
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
		int eId = (int) session.getAttribute("userId");
		String docName = request.getParameter("docName");
		String docComment = request.getParameter("docComment");
		int directoryId = Integer.parseInt(request.getParameter("directory"));
		Part doc = request.getPart("docFile");
		InputStream inputstream = null;
		if(directoryId > 0 && docName.length() > 0 && doc.getSize() > 0){
			inputstream = doc.getInputStream();
			FunctionResponse fresponse = null;
			try {
				fresponse = EmployeeService.saveDocuments(eId,directoryId,docName,docComment,inputstream,doc.getContentType());
				request.setAttribute("message", fresponse.getResponse());
				doGet(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", "Network Error");
				doGet(request, response);
			}
		}else{
			request.setAttribute("error", "Mandatory fields are required to proceed");
			doGet(request, response);
		}
	}

}
