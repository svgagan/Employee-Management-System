package com.EMS.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.commons.io.IOUtils;

import com.EMS.entity.Employeedocs;
import com.EMS.service.EmployeeService;

/**
 * Servlet implementation class DownloadDoc
 */
@WebServlet("/DownloadDoc")
public class DownloadDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadDoc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();  
        session.invalidate();
		request.setAttribute("loginerror","Invalid Accessing");
    	RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
	    rs.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null){
			int docId = Integer.parseInt(request.getParameter("docId"));
			try {
				Employeedocs doc = EmployeeService.getDocument(docId);
				if(doc !=null)
		        {
			        try {
			        	response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getDocName()+ "\"");
			            OutputStream out = response.getOutputStream();
			            response.setContentType(doc.getDocType());
			            Blob testfile = doc.getDocFile();
			            if(testfile != null){
				            org.apache.tomcat.util.http.fileupload.IOUtils.copy(testfile.getBinaryStream(), out);
			            }else{
			            	out.write(0);
			            }
			            out.flush();
			            out.close();
			        } catch (Exception e) {
			            e.printStackTrace();
			            request.setAttribute("message", "Document Not Available");
						RequestDispatcher rs = request.getRequestDispatcher("employeeFiles.jsp");
						rs.forward(request, response);
			        }
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("message", "Document Not Available");
				RequestDispatcher rs = request.getRequestDispatcher("employeeFiles.jsp");
				rs.forward(request, response);
			}
		}else{
			request.setAttribute("message", "Document Not Available");
			RequestDispatcher rs = request.getRequestDispatcher("employeeFiles.jsp");
			rs.forward(request, response);
		}
	}

}
